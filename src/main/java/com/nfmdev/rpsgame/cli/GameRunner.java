package com.nfmdev.rpsgame.cli;

import java.util.Objects;
import java.util.Optional;

import com.nfmdev.rpsgame.config.GameConfig;
import com.nfmdev.rpsgame.game.Move;
import com.nfmdev.rpsgame.game.Result;
import com.nfmdev.rpsgame.game.Rules;
import com.nfmdev.rpsgame.game.Score;
import com.nfmdev.rpsgame.game.machine_move_generator.DefaultMoveGenerator;

public final class GameRunner {
    private static final long ROUND_RESULT_PAUSE_MILLIS = 2_000;

    private final InputReader reader;
    private final OutputWriter writer;
    private final Rules rules;
    private final DefaultMoveGenerator moveGenerator;
    private final MoveResolver resolver;

    public GameRunner(
        InputReader reader,
        OutputWriter writer,
        Rules rules,
        DefaultMoveGenerator moveGenerator,
        MoveResolver resolver
    ) {
        this.reader = Objects.requireNonNull(reader, "Reader cannot be null");
        this.writer = Objects.requireNonNull(writer, "Writer cannot be null");
        this.rules = Objects.requireNonNull(rules, "Rules cannot be null");
        this.moveGenerator = Objects.requireNonNull(moveGenerator, "Move generator cannot be null");
        this.resolver = Objects.requireNonNull(resolver, "Move resolver cannot be null");
    }

    public void run(Args args) {
        Objects.requireNonNull(args, "Arguments cannot be null");

        int rounds = getNumberOfRounds(args);
        GameConfig config = new GameConfig(rounds);
        Score score = new Score();
        
        for (int round = 1; round <= config.rounds(); round++) {
            playRound(round, config.rounds(), score);
        }

        renderGameOverScreen(score);
    }

    /* 
     Get number of rounds from arguments
     If not rounds arg not present ask for manual insert
    */
    private int getNumberOfRounds(Args args) {
        Optional<Integer> rounds = args.getRounds();
        if (rounds.isPresent()) return rounds.get();

        return insertRoundsManually();
    }

    /*
        Ask number of rounds, if empty value is inserted default fallback is 3 rounds
        Keeps asking until valid integer or empty
    */
    private int insertRoundsManually() {
        String errorMessage = null;
        while (true) {
            renderInsertRoundsScreen(errorMessage);

            String input = reader.readLine().trim();

            if (input.isEmpty()) return GameConfig.DEFAULT_ROUNDS;

            try {
                int rounds = Integer.parseInt(input);
                if (rounds > 0) return rounds;

                errorMessage = "Please enter a number greater than 0";
            } catch (NumberFormatException e) {
                errorMessage = "Please enter a valid number";
            }
        }
    }

    private void playRound(int currentRound, int totalRounds, Score score) {
        printRoundInfo(currentRound, totalRounds, score);

        Move playerMove = getPlayerMove(currentRound, totalRounds, score);
        Move machineMove = moveGenerator.generateMove();

        Result result = rules.resolveRound(playerMove, machineMove);

        if (result == Result.PLAYER_WINS) {
            score.setPlayerScore(score.getPlayerScore()+1);
        } else if (result == Result.MACHINE_WINS) {
            score.setMachineScore(score.getMachineScore()+1);
        }
        
        renderRoundResultScreen(playerMove, machineMove, result, score, currentRound, totalRounds);
        pauseAfterRoundResult();

    }

    private Move getPlayerMove(int currentRound, int totalRounds, Score score) {
        while (true) {
            renderMoveInsertScreen(currentRound, totalRounds, score);

            String input = reader.readLine();
            Optional<Move> move = resolver.parse(input);

            if (move.isPresent()) {
                return move.get();
            }
        }
    }

    private void renderInsertRoundsScreen(String errorMessage) {
        printHeader();
        writer.println("Enter number of rounds");
        writer.println();
        writer.println("Press Enter to use the default: " + GameConfig.DEFAULT_ROUNDS);
        writer.println();

        if (errorMessage != null) {
            writer.println(errorMessage);
            writer.println();
        }

        writer.print("> ");
    }

    private void renderMoveInsertScreen(int currentRound,int totalRounds, Score score) {
        printHeader();
        printRoundInfo(currentRound, totalRounds, score);
        printMoves();
    }

    private void renderRoundResultScreen(
        Move playerMove,
        Move machineMove,
        Result result,
        Score score,
        int currentRound,
        int totalRounds
    ) {
        writer.println();
        writer.println("Player move: " + playerMove);
        writer.println("Rival move: " + machineMove);
        writer.println("Result: " + formatRoundResult(result));
        writer.println("Score: Player " + score.getPlayerScore() + " | Rival " + score.getMachineScore());
        writer.println();
        if (currentRound < totalRounds) {
            writer.println("Next round will start soon...");
        }
    }

    private void renderGameOverScreen(Score score) {
        writer.clearScreen();
        writer.println("===============================================");
        writer.println("                 FINAL SCORE                   ");
        writer.println("===============================================");
        writer.println("Player Wins:\t" + score.getPlayerScore());
        writer.println("Rival Wins:\t" + score.getMachineScore());
        writer.println();
        writer.println(formatGameResult(score));
        writer.println();
    }

    private void printHeader() {
        writer.clearScreen();
        writer.println("===============================================");
        writer.println("              ROCK PAPER SCISSORS              ");
        writer.println("===============================================");
    }

    private void printRoundInfo(int currentRound, int totalRounds, Score score) {
        writer.println("Round " + currentRound + " / " + totalRounds + 
                    " — Player Score " + score.getPlayerScore() + " | Rival Score " + score.getMachineScore());
        writer.println("-----------------------------------------------");
    }

    private void printMoves() {
        writer.println("Choose your move:");
        writer.println("\t[r] ROCK");
        writer.println("\t[p] PAPER");
        writer.println("\t[s] SCISSORS");
    }

    private String formatRoundResult(Result result) {
        return switch (result) {
            case PLAYER_WINS -> "You win this round!";
            case MACHINE_WINS -> "Rival wins this round!";
            case DRAW -> "It's a draw!";
        };
    }

    private String formatGameResult(Score score) {
        if (score.getPlayerScore() > score.getMachineScore()) {
            return " YOU WIN ";
        } else if (score.getPlayerScore() < score.getMachineScore()) {
            return " RIVAL WINS ";
        } else {
            return " IT'S A DRAW";
        }
    }

    private void pauseAfterRoundResult() {
        try {
            Thread.sleep(ROUND_RESULT_PAUSE_MILLIS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
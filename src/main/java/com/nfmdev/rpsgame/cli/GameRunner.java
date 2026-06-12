package com.nfmdev.rpsgame.cli;

import java.util.Objects;
import java.util.Optional;

import com.nfmdev.rpsgame.config.GameConfig;
import com.nfmdev.rpsgame.game.MachineMoveGenerator;
import com.nfmdev.rpsgame.game.Move;
import com.nfmdev.rpsgame.game.Result;
import com.nfmdev.rpsgame.game.Rules;
import com.nfmdev.rpsgame.game.Score;

public final class GameRunner {
    private final InputReader reader;
    private final OutputWriter writer;
    private final Rules rules;
    private final MachineMoveGenerator moveGenerator;
    private final MoveResolver resolver;

    public GameRunner(
        InputReader reader,
        OutputWriter writer,
        Rules rules,
        MachineMoveGenerator moveGenerator,
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
        printHeader();

        int rounds = resolveRounds(args);
        GameConfig config = new GameConfig(rounds);

        Score score = new Score();
        
        for (int round = 1; round <= config.rounds(); round++) {
            playRound(round, config.rounds(), score);
        }

        printGameOver(score);
    }

    private int resolveRounds(Args args) {
        Optional<Integer> rounds = args.getRounds();
        if (rounds.isPresent()) return rounds.get();
        return insertRoundsManually();
    }

    private int insertRoundsManually() {
        while (true) {
            writer.println("Enter number of rounds (default: " + GameConfig.DEFAULT_ROUNDS + ")");
            writer.print("> ");

            String input = reader.readLine().trim();

            if (input.isEmpty()) return GameConfig.DEFAULT_ROUNDS;

            try {
                int rounds = Integer.parseInt(input);
                if (rounds > 0) return rounds;

                writer.println("Please enter a number greater than 0");
                writer.println();
            } catch (NumberFormatException e) {
                writer.println("Pelase enter a valid number");
                writer.println();
            }
        }
    }

    private void playRound(int currentRound, int totalRounds, Score score) {
        writer.println("Round " + currentRound + " / " + totalRounds);

        Move playerMove = getPlayerMove();
        Move machineMove = moveGenerator.generateMove();

        Result result = rules.resolveRound(playerMove, machineMove);

        if (result == Result.PLAYER_WINS) {
            score.setPlayerScore(score.getPlayerScore()+1);
        } else if (result == Result.MACHINE_WINS) {
            score.setMachineScore(score.getMachineScore()+1);
        }

        printRoundResult(playerMove, machineMove, result, score);
    }

    private Move getPlayerMove() {
        while (true) {
            writer.println("Choose your move:");
            writer.println("[r] ROCK");
            writer.println("[p] PAPER");
            writer.println("[s] SCISSORS");
            writer.print("> ");

            String input = reader.readLine();
            Optional<Move> move = resolver.parse(input);

            if (move.isPresent()) {
                return move.get();
            }
        }
    }

    private void printHeader() {
        writer.println("=========================================");
        writer.println(" ROCK PAPER SCISSORS");
        writer.println("=========================================");
    }

    private void printRoundResult(
        Move playerMove,
        Move machineMove,
        Result result,
        Score score
    ) {
        writer.println();
        writer.println("Player move: " + playerMove);
        writer.println("Rival move: " + machineMove);
        writer.println("Result: " + formatRoundResult(result));
        writer.println("Score: Player " + score.getPlayerScore() + " | Rival " + score.getMachineScore());
        writer.println();
    }

    private String formatRoundResult(Result result) {
        return switch (result) {
            case PLAYER_WINS -> "You win this round!";
            case MACHINE_WINS -> "Rival wins this round!";
            case DRAW -> "It's a draw!";
        };
    }

    private void printGameOver(Score score) {
        writer.println("=========================================");
        writer.println(" FINAL SCORE ");
        writer.println("=========================================");
        writer.println("Player:\t" + score.getPlayerScore());
        writer.println("Rival:\t" + score.getMachineScore());
        writer.println();
        writer.println(formatGameResult(score));
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
}
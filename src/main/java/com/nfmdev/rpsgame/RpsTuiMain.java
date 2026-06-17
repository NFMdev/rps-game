package com.nfmdev.rpsgame;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import com.nfmdev.rpsgame.cli.Args;
import com.nfmdev.rpsgame.cli.ArgsResolver;
import com.nfmdev.rpsgame.cli.OutputWriter;
import com.nfmdev.rpsgame.config.GameConfig;
import com.nfmdev.rpsgame.game.Rules;
import com.nfmdev.rpsgame.game.machine_move_generator.DefaultMoveGenerator;
import com.nfmdev.rpsgame.tui.model.GameModel;
import com.williamcallahan.tui4j.compat.bubbletea.Program;

public final class RpsTuiMain {
    public static void main(String[] args) {
        OutputWriter writer = new OutputWriter(System.out);
        OutputWriter errorWriter = new OutputWriter(System.err);

        try {
            Args parsedArgs = new ArgsResolver().parse(args);
            if (parsedArgs.roundsHelpRequested()) {
                writer.println(roundsUsage());
                return;
            } else if (parsedArgs.helpRequested()) {
                writer.println(usage());
                return;
            }
            run(parsedArgs);
        } catch (IllegalArgumentException e) {
        errorWriter.println("Error: " + e.getMessage());
        errorWriter.println("Run --help to see usage instructions");
    }
    }

    private static void run(Args args) {
        Objects.requireNonNull(args, "Arguments cannot be null");

        int rounds = getNumberOfRounds(args);
        Rules rules = new Rules();
        DefaultMoveGenerator generator = new DefaultMoveGenerator(new Random());
        GameConfig config = new GameConfig(rounds);

        new Program(new GameModel(rules, generator, config)).withAltScreen().run();
    }

    private static int getNumberOfRounds(Args args) {
        Optional<Integer> rounds = args.getRounds();
        if (rounds.isPresent()) return rounds.get();
        return 3;
        //throw new IllegalArgumentException("Rounds cannot be null");
    }

    private static String usage() {
        return """
                ROCK PAPER SCISSORS

                Usage:
                    java -jar rps-game.jar [rounds <number>]
                    java -jar rock-paper-scissors.jar [--rounds <number>]
                    java -jar rock-paper-scissors.jar [-r <number>]
                    java -jar rock-paper-scissors.jar [--help <option>]
                    java -jar rock-paper-scissors.jar [-h <option>]

                Options:
                    --rounds, r     Number of rounds to play (must be greater than zero)
                    --help, r       Show command help

                If no number of rounds is specified, the game asks for manual setup.
                If you press Enter without entering a value, game default is 3 rounds.
                """;
    }

    private static String roundsUsage() {
        return """
                usage: --rounds [number of rounds]

                Configures the number of rounds to play (must be greater than zero)
                """;
    }
}
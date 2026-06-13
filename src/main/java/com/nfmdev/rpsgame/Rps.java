package com.nfmdev.rpsgame;

import java.util.Random;
import java.util.Scanner;

import com.nfmdev.rpsgame.cli.Args;
import com.nfmdev.rpsgame.cli.ArgsResolver;
import com.nfmdev.rpsgame.cli.GameRunner;
import com.nfmdev.rpsgame.cli.InputReader;
import com.nfmdev.rpsgame.cli.MoveResolver;
import com.nfmdev.rpsgame.cli.OutputWriter;
import com.nfmdev.rpsgame.game.Rules;
import com.nfmdev.rpsgame.game.machine_move_generator.DefaultMoveGenerator;

public final class Rps {
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

            InputReader reader = new InputReader(new Scanner(System.in));

            GameRunner runner = new GameRunner(
                reader,
                writer,
                new Rules(),
                new DefaultMoveGenerator(new Random()),
                new MoveResolver()
            );
            runner.run(parsedArgs);
        } catch (IllegalArgumentException e) {
            errorWriter.println("Error: " + e.getMessage());
            errorWriter.println("Run --help to see usage instructions");
        }
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

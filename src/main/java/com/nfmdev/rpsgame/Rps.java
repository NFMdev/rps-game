package com.nfmdev.rpsgame;

import java.util.Random;
import java.util.Scanner;

import com.nfmdev.rpsgame.cli.Args;
import com.nfmdev.rpsgame.cli.ArgsResolver;
import com.nfmdev.rpsgame.cli.GameRunner;
import com.nfmdev.rpsgame.cli.InputReader;
import com.nfmdev.rpsgame.cli.MoveResolver;
import com.nfmdev.rpsgame.cli.OutputWriter;
import com.nfmdev.rpsgame.game.MachineMoveGenerator;
import com.nfmdev.rpsgame.game.Rules;

public final class Rps {
    public static void main(String[] args) {
        OutputWriter writer = new OutputWriter(System.out);

        try {
            Args parsedArgs = new ArgsResolver().parse(args);
            if (parsedArgs.helpRequested()) {
                writer.println(usage());
                return;
            }

            InputReader reader = new InputReader(new Scanner(System.in));

            GameRunner runner = new GameRunner(
                reader,
                writer,
                new Rules(),
                new MachineMoveGenerator(new Random()),
                new MoveResolver()
            );
            runner.run(parsedArgs);
        } catch (Exception e) {
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
}

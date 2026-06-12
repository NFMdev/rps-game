package com.nfmdev.rpsgame.cli;

import java.util.Objects;

public final class ArgsResolver {
    public Args parse(String[] args) {
        Objects.requireNonNull(args, "Arguments cannot be null");

        return switch (args.length) {
            case 0 -> Args.empty();
            case 1 -> parseSingleArgument(args[0]);
            case 2 -> parseCompoundArgument(args[0], args[1]);
            default -> throw new IllegalArgumentException("Invalid number of arguments — Use --help for usage");
        };
    }

    private Args parseSingleArgument(String argument) {
        if (isHelpArg(argument)) {
            return Args.help();
        } else if (isRoundsArg(argument)) {
            throw new IllegalArgumentException("Missing value for " + argument);
        } else {
            throw new IllegalArgumentException("Unknown argument: " + argument);
        }
    }

    private Args parseCompoundArgument(String option, String value) {
        if (isRoundsArg(option) && !isHelpArg(value)) {
            return Args.withRounds(parseRounds(value));
        } else if (isRoundsArg(option) && isHelpArg(value)) {
            return Args.roundsHelp();
        } else if (isHelpArg(option) && isRoundsArg(value)) {
            return Args.help();
        } else if (isHelpArg(option) && !isRoundsArg(value)) {
            throw new IllegalArgumentException("Unknown argument: " + value);
        } else if (isRoundsArg(option) && !isHelpArg(value)) {
            throw new IllegalArgumentException("Unknown argument: " + value);
        } else {
            throw new IllegalArgumentException("Unknown arguments: " + option + " | " + value);
        }
    }

    private boolean isRoundsArg(String argument) {
        return "--rounds".equals(argument) || "-r".equals(argument);
    }

    private boolean isHelpArg(String argument) {
        return "--help".equals(argument) || "-h".equals(argument);
    }

    private int parseRounds(String value) {
        try {
            int rounds = Integer.parseInt(value.trim());
            if (rounds <= 0) throw new IllegalArgumentException("Number of rounds must be > 0");
            return rounds;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Number of rounds must be a valid integer");
        }
    }
}
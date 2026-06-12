package com.nfmdev.rpsgame.cli;

import java.util.Optional;

public final class Args {
    private final Integer rounds;
    private final boolean helpArgPassed;
    private final boolean roundsArgPassed;

    private Args(Integer rounds, boolean helpArgPassed, boolean roundsArgPassed) {
        if (!helpArgPassed && rounds != null && rounds <= 0) {
            throw new IllegalArgumentException("Number of rounds must be > 0");
        }

        this.rounds = rounds;
        this.helpArgPassed = helpArgPassed;
        this.roundsArgPassed = roundsArgPassed;
    }

    public static Args empty() {
        return new Args(null, false, false);
    }

    public static Args withRounds(int rounds) {
        return new Args(rounds, false, true);
    }

    public static Args help() {
        return new Args(null, true, false);
    }

    public static Args roundsHelp() {
        return new Args(null, true, true);
    }

    public Optional<Integer> getRounds() {
        return Optional.ofNullable(rounds);
    }

    public boolean helpRequested() {
        return helpArgPassed;
    }

    public boolean roundsHelpRequested() {
        return helpArgPassed && roundsArgPassed;
    }
}
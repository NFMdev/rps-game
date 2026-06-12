package com.nfmdev.rpsgame.game;

import java.util.Objects;
import java.util.Random;

public final class MachineMoveGenerator {
    private final Random random;

    public MachineMoveGenerator(Random random) {
        this.random = Objects.requireNonNull(random, "Random cannot be null");
    }

    public Move generateMove() {
        Move[] moves = Move.values();
        return moves[random.nextInt(moves.length)];
    }
}
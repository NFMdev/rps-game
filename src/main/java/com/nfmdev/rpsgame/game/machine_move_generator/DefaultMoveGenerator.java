package com.nfmdev.rpsgame.game.machine_move_generator;

import java.util.Objects;
import java.util.Random;

import com.nfmdev.rpsgame.game.Move;

public final class DefaultMoveGenerator implements MachineMoveGenerator {
    private final Random random;

    public DefaultMoveGenerator(Random random) {
        this.random = Objects.requireNonNull(random, "Random cannot be null");
    }

    @Override
    public Move generateMove() {
        Move[] moves = Move.values();
        return moves[random.nextInt(moves.length)];
    }
}
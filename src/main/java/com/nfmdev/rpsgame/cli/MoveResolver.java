package com.nfmdev.rpsgame.cli;

import java.util.Optional;

import com.nfmdev.rpsgame.game.Move;

public final class MoveResolver {
    public Optional<Move> parse(String input) {
        if (input == null) {
            return Optional.empty();
        }

        return switch (input.toLowerCase()) {
            case "r" -> Optional.of(Move.ROCK);
            case "p" -> Optional.of(Move.PAPER);
            case "s" -> Optional.of(Move.SCISSORS);
            default -> Optional.empty();
        };
    }
}
package com.nfmdev.rpsgame.config;

public record GameConfig(int rounds) {
    public static final int DEFAULT_ROUNDS = 3;

    public GameConfig {
        if (rounds <= 0) {
            throw new IllegalArgumentException("Number of rounds must be > 0");
        }
    }

    public static GameConfig defaultConfig() {
        return new GameConfig(DEFAULT_ROUNDS);
    }
}
package com.nfmdev.rpsgame.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GameConfigTest {
    @ParameterizedTest
    @ValueSource(ints={1, 5, 99})
    void testCreateGameConfigWithValidNumberOfRounds(int rounds) {
        GameConfig config = new GameConfig(rounds);
        assertEquals(rounds, config.rounds());
    }

    @ParameterizedTest
    @ValueSource(ints={0, -1})
    void testCreateGameConfigWithInvalidNumberOfRounds(int rounds) {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new GameConfig(rounds)
        );

        assertEquals("Number of rounds must be > 0", exception.getMessage());
    }

    @Test
    void testCreateDefaultConfigReturnsThreeRounds() {
        GameConfig config = GameConfig.defaultConfig();
        assertEquals(3, config.rounds());
    }
}
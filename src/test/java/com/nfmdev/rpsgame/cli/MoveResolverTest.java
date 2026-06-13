package com.nfmdev.rpsgame.cli;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.nfmdev.rpsgame.game.Move;

class MoveResolverTest {

    private final MoveResolver resolver = new MoveResolver();

    @Test
    void testResolverParsesMoveInputs() {
        assertEquals(Optional.of(Move.ROCK), resolver.parse("r"));
        assertEquals(Optional.of(Move.PAPER), resolver.parse("p"));
        assertEquals(Optional.of(Move.SCISSORS), resolver.parse("s"));
    }

    @Test
    void testResolverReturnsEmptyWhenInputIsNull() {
        assertTrue(resolver.parse(null).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings={"test", "", "      ", " r "})
    void testResolverReturnsEmptyWhenInvalidInput(String input) {
        assertTrue(resolver.parse(input).isEmpty());
    }
}

package com.nfmdev.rpsgame.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class RulesTest {
    private final Rules rules = new Rules();
    
    @ParameterizedTest
    @CsvSource({
        "ROCK, ROCK, DRAW",
        "PAPER, PAPER, DRAW",
        "SCISSORS, SCISSORS, DRAW",
        "PAPER, ROCK, PLAYER_WINS",
        "SCISSORS, PAPER, PLAYER_WINS",
        "ROCK, SCISSORS, PLAYER_WINS",
        "ROCK, PAPER, MACHINE_WINS",
        "PAPER, SCISSORS, MACHINE_WINS",
        "SCISSORS, ROCK, MACHINE_WINS"
    })
    void testResolveRound(
        Move playerMove,
        Move machineMove,
        Result expectedResult
    ) {
        Result result = rules.resolveRound(playerMove, machineMove);
        assertEquals(expectedResult, result);
    }
}

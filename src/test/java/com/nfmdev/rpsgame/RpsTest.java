package com.nfmdev.rpsgame;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

class RpsTest {
    @Test
    void testApplicationHelp() {
        assertDoesNotThrow(() -> Rps.main(new String[]{"--help"}));
    }
}

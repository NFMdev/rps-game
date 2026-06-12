package com.nfmdev.rpsgame.cli;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class ArgsResolverTest {
    private final ArgsResolver resolver = new ArgsResolver();

    @Test
    void testResolverParsesEmptyArguments() {
        Args args = resolver.parse(new String[]{});
        assertTrue(args.getRounds().isEmpty());
        assertFalse(args.helpRequested());
    }

    @ParameterizedTest
    @CsvSource({
        "--rounds, 5",
        "-r, 7"
    })
    void testResolverParsesRoundsOption(String option, String value) {
        Args args = resolver.parse(new String[]{option, value});
        assertEquals(Optional.of(Integer.valueOf(value)), args.getRounds());
        assertFalse(args.helpRequested());
    }

    @ParameterizedTest
    @CsvSource({"--help", "-h"})
    void testResolverParsesHelpOption(String option) {
        Args args = resolver.parse(new String[]{option});
        assertTrue(args.helpRequested());
        assertTrue(args.getRounds().isEmpty());
        assertFalse(args.roundsHelpRequested());
    }

    @ParameterizedTest
    @CsvSource({
        "--rounds, --help",
        "--rounds, -h",
        "-r, --help",
        "-r, -h"
    })
    void testResolverParsesRoundsHelpOption(String option1,String option2) {
        Args args = resolver.parse(new String[]{option1, option2});
        assertTrue(args.roundsHelpRequested());
        assertTrue(args.getRounds().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
        "--help, --rounds",
        "-h, --rounds",
        "--help, -r",
        "-h, -r"
    })
    void testResolverParsesHelpOptionWhenFirst(String option1, String option2) {
        Args args = resolver.parse(new String[]{option1, option2});
        assertTrue(args.helpRequested());
        assertFalse(args.roundsHelpRequested());
    }

    @ParameterizedTest
    @CsvSource({
        "--rounds, Missing value for --rounds",
        "-r, Missing value for -r"
    })
    void testThrowsExceptionWhenNoValueForRoundsOption(String option, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> resolver.parse(new String[]{option})
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
        "test, Number of rounds must be a valid integer",
        "0, Number of rounds must be > 0",
        "-1, Number of rounds must be > 0"
    })
    void testThrowsExceptionWhenRoundsValueIsInvalid(String value, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> resolver.parse(new String[]{"--rounds", value})
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
        "--test, Unknown argument: --test",
        "test, Unknown argument: test",
        "rounds, Unknown argument: rounds"
    })
    void testThrowsExceptionWhenArgumentIsUnknown(String argument, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> resolver.parse(new String[]{argument})
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidArgumentCombinations")
    void throwsExceptionWhenArgumentCombinationIsInvalid(
        String[] args,
        String expectedMessage
    ) {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> resolver.parse(args)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Object[]> invalidArgumentCombinations() {
        return Stream.of(
            new Object[]{
                new String[]{"--help", "test"},
                "Unknown argument: test"
            },
            new Object[]{
                new String[]{"-h", "test"},
                "Unknown argument: test"
            },
            new Object[]{
                new String[]{"--test", "--unknown"},
                "Unknown arguments: --test | --unknown"
            },
            new Object[]{
                new String[]{"--rounds", "5", "--help"},
                "Invalid number of arguments — Use --help for usage"
            }
        );
    }
}
package com.aoc.year2023.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextProcessingTest {

    @ParameterizedTest
    @MethodSource("isIntegerDataSource")
    void testIsInteger(String possibleIntegerAsString, boolean expectedResult) {
        boolean actualResult = TextProcessing.isInteger(possibleIntegerAsString);

        assertEquals(expectedResult, actualResult);
    }

    private static Stream<Arguments> isIntegerDataSource() {
        return Stream.of(
                Arguments.of("42", true),
                Arguments.of("1234", true),
                Arguments.of("9999999", true),
                Arguments.of("420", true),
                Arguments.of("4.5", false),
                Arguments.of("asdf", false),
                Arguments.of("qwerty", false),
                Arguments.of("ABC", false),
                Arguments.of("Z", false),
                Arguments.of("49db23dcxz0", false)
        );
    }
}
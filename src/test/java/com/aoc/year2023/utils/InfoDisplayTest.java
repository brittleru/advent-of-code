package com.aoc.year2023.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfoDisplayTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @ParameterizedTest
    @MethodSource("displayWelcomeDataSource")
    void testDisplayWelcome(int dayNum, String challengeName) {
        String expectedOutput = String.format("\n--- Welcome to Day %2d of AoC 2023: %s ---\n", dayNum, challengeName);

        InfoDisplay.displayWelcome(dayNum, challengeName);

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }


    private static Stream<Arguments> displayWelcomeDataSource() {
        return Stream.of(
                Arguments.of(5, "Graph Theory"),
                Arguments.of(42, "Machine Learning"),
                Arguments.of(123, "Computer Vision"),
                Arguments.of(420, "Optical Flow"),
                Arguments.of(9999, "Monocular Depth Estimation")
        );
    }
}
package com.aoc.year2023.day04;

import com.aoc.year2023.utils.FileProcessing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {

    private static final String resourcesTestURI = "src/test/resources/";
    private static final String inputFileURI = "src/main/resources/year2023/day04/input.txt";
    private static final String testTask1URI = resourcesTestURI + "year2023/day04/test_1.txt";

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @ParameterizedTest
    @MethodSource("solveDataSource")
    void testSolve(String inputDataPath, int expectedTotalPoints) {
        String absolutePath = FileProcessing.getAbsolutePathFromUriString(inputDataPath);
        List<String> puzzleData = FileProcessing.readFile(absolutePath);

        int actualTotalPoints = solution.solve(puzzleData, false);

        assertEquals(expectedTotalPoints, actualTotalPoints);
    }

    @ParameterizedTest
    @MethodSource("getPointsFromWinningNumbersFoundDataSource")
    void testGetPointsFromWinningNumbersFound(int winningNumbersFound, int expectedPoints) {
        int actualPoints = solution.getPointsFromWinningNumbersFound(winningNumbersFound);

        assertEquals(expectedPoints, actualPoints);
    }

    private static Stream<Arguments> solveDataSource() {
        return Stream.of(
                Arguments.of(inputFileURI, 18519),
                Arguments.of(testTask1URI, 13)
        );
    }

    private static Stream<Arguments> getPointsFromWinningNumbersFoundDataSource() {
        return Stream.of(
                Arguments.of(5, 16),
                Arguments.of(4, 8),
                Arguments.of(3, 4),
                Arguments.of(2, 2),
                Arguments.of(1, 1),
                Arguments.of(10, 512)
        );
    }
}
package com.aoc.year2023.day03;

import com.aoc.year2023.utils.FileProcessing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolutionTest {

    private static final String resourcesTestURI = "src/test/resources/";
    private static final String inputFileURI = "src/main/resources/year2023/day03/input.txt";
    private static final String testTask1URI = resourcesTestURI + "year2023/day03/test_1.txt";
    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @ParameterizedTest
    @MethodSource("solvePartTwoDataSource")
    void testSolvePartTwo(String filePathURI, int expectedResult) {
        String absolutePath = FileProcessing.getAbsolutePathFromUriString(filePathURI);
        List<String> engineSchematic = FileProcessing.readFile(absolutePath);

        int actualResult = solution.solvePartTwo(engineSchematic, false);

        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("getNumberAdjacentToStarDataSource")
    void testGetNumberAdjacentToStar(String possiblePart, int starIndex, int expectedNumber) {
        String[] possiblePartTokens = possiblePart.split("");

        int actualNumber = solution.getNumberAdjacentToStar(possiblePartTokens, starIndex);

        assertEquals(expectedNumber, actualNumber);
    }

    @ParameterizedTest
    @MethodSource("getPossiblePartRowsForStarDataSource")
    void testGetPossiblePartRowsForStar(String filePathURI, Map<String, Integer> starData, int expectedSize) {
        String absolutePath = FileProcessing.getAbsolutePathFromUriString(filePathURI);
        List<String> engineSchematic = FileProcessing.readFile(absolutePath);

        List<String> result = solution.getPossiblePartRowsForStar(engineSchematic, starData);

        assertEquals(expectedSize, result.size());
    }

    @ParameterizedTest
    @MethodSource("getStarsFromTheEngineSchematicDataSource")
    void testGetStarsFromTheEngineSchematic(String filePathURI, int expectedSize) {
        String absolutePath = FileProcessing.getAbsolutePathFromUriString(filePathURI);
        List<String> engineSchematic = FileProcessing.readFile(absolutePath);

        List<Map<String, Integer>> result = solution.getStarsFromTheEngineSchematic(engineSchematic);

        for (Map<String, Integer> resultData : result) {
            assertTrue(resultData.containsKey("rowIndex"));
            assertTrue(resultData.containsKey("tokenIndex"));
            assertEquals(2, resultData.size());
        }

        assertEquals(expectedSize, result.size());
    }

    @ParameterizedTest
    @MethodSource("solveDataSource")
    void testSolve(String filePathURI, int expectedResult) {
        String absolutePath = FileProcessing.getAbsolutePathFromUriString(filePathURI);
        List<String> engineSchematic = FileProcessing.readFile(absolutePath);

        int actualResult = solution.solve(engineSchematic, false);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testIsNumberEngineSchematic() {
        String absolutePath = FileProcessing.getAbsolutePathFromUriString(testTask1URI);
        List<String> engineSchematic = FileProcessing.readFile(absolutePath);
        String row = "467..114..";
        String[] rowTokens = row.split("");
        int rowIndex = 0;
        int[] indices = new int[]{0, 1, 2};
        SortedSet<Integer> digitIndices = new TreeSet<>();
        for (int index : indices) {
            digitIndices.add(index);
        }

        boolean expectedResult = true;
        boolean actualResult = solution.isNumberEngineSchematic(
                engineSchematic,
                rowTokens,
                rowIndex,
                digitIndices
        );

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testConvertIndicesAndRowToInteger() {
        String row = "467..114..";
        String[] rowTokens = row.split("");
        int[] indices = new int[]{0, 1, 2};
        SortedSet<Integer> digitIndices = new TreeSet<>();
        for (int index : indices) {
            digitIndices.add(index);
        }

        int expectedResult = 467;
        int actualResult = solution.convertIndicesAndRowToInteger(rowTokens, digitIndices);

        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("processEngineSchematicRowForDigitDataSource")
    void testProcessEngineSchematicRowForDigit(String row, int[] indices, boolean isCurrent, boolean expectedResult) {
        String[] rowTokens = row.split("");
        SortedSet<Integer> digitIndices = new TreeSet<>();
        for (int index : indices) {
            digitIndices.add(index);
        }

        boolean actualResult = solution.processEngineSchematicRowForDigit(rowTokens, digitIndices, isCurrent);

        assertEquals(expectedResult, actualResult);
    }

    private static Stream<Arguments> solvePartTwoDataSource() {
        return Stream.of(
                Arguments.of(inputFileURI, 84495585),
                Arguments.of(testTask1URI, 467835)
        );
    }

    private static Stream<Arguments> getNumberAdjacentToStarDataSource() {
        return Stream.of(
                Arguments.of("467..114..", 3, 467),
                Arguments.of("..35..633.", 3, 35),
                Arguments.of("617*......", 3, 617),
                Arguments.of("......755.", 5, 755),
                Arguments.of(".664.598..", 5, 598)
        );
    }

    private static Stream<Arguments> getPossiblePartRowsForStarDataSource() {
        return Stream.of(
                Arguments.of(
                        inputFileURI,
                        Map.ofEntries(Map.entry("rowIndex", 1), Map.entry("tokenIndex", 13)),
                        3
                ),
                Arguments.of(
                        inputFileURI,
                        Map.ofEntries(Map.entry("rowIndex", 139), Map.entry("tokenIndex", 41)),
                        2
                ),
                Arguments.of(
                        testTask1URI,
                        Map.ofEntries(Map.entry("rowIndex", 1), Map.entry("tokenIndex", 4)),
                        3
                ),
                Arguments.of(
                        testTask1URI,
                        Map.ofEntries(Map.entry("rowIndex", 4), Map.entry("tokenIndex", 3)),
                        3
                ),
                Arguments.of(
                        testTask1URI,
                        Map.ofEntries(Map.entry("rowIndex", 8), Map.entry("tokenIndex", 5)),
                        3
                )
        );
    }

    private static Stream<Arguments> getStarsFromTheEngineSchematicDataSource() {
        return Stream.of(
                Arguments.of(inputFileURI, 386),
                Arguments.of(testTask1URI, 3)
        );
    }

    private static Stream<Arguments> solveDataSource() {
        return Stream.of(
                Arguments.of(inputFileURI, 544664),
                Arguments.of(testTask1URI, 4361)
        );
    }

    private static Stream<Arguments> processEngineSchematicRowForDigitDataSource() {
        return Stream.of(
                Arguments.of("467..114..", new int[]{0, 1, 2}, true, false),
                Arguments.of("467..114..", new int[]{5, 6, 7}, true, false),
                Arguments.of("617*......", new int[]{0, 1, 2}, true, true),
                Arguments.of("%664.598^.", new int[]{1, 2, 3}, true, true),
                Arguments.of("%664.598^.", new int[]{5, 6, 7}, true, true)
        );
    }

}
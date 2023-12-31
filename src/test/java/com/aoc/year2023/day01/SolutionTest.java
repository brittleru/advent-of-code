package com.aoc.year2023.day01;

import com.aoc.year2023.utils.FileProcessing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SolutionTest {
    private static final String resourcesTestURI = "src/test/resources/";
    private static final String inputFileURI = "src/main/resources/year2023/day01/input.txt";
    private static final String testTask1URI = resourcesTestURI + "year2023/day01/test_1.txt";
    private static final String testTask2URI = resourcesTestURI + "year2023/day01/test_2.txt";

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @Test
    void testSolverThrows() {
        String absolutePath = FileProcessing.getAbsolutePathFromUriString(inputFileURI);
        List<String> calibrationDocument = FileProcessing.readFile(absolutePath);

        assertThrows(RuntimeException.class, () -> solution.solve("NOT EXISTENT PART", calibrationDocument));
    }

    @ParameterizedTest
    @MethodSource("solverDataSource")
    void testSolver(String partNum, String inputFilePath, int expectedAnswer) {
        String absolutePath = FileProcessing.getAbsolutePathFromUriString(inputFilePath);
        List<String> calibrationDocument = FileProcessing.readFile(absolutePath);

        int result = solution.solve(partNum, calibrationDocument);

        assertEquals(expectedAnswer, result);
    }

    @ParameterizedTest
    @MethodSource("calibrationValueWithTextDataSource")
    void testGetCalibrationValueWithText(String calibrationLine, int expectedCalibrationValue) {
        int calibrationValue = solution.getCalibrationValueWithText(calibrationLine);

        assertEquals(expectedCalibrationValue, calibrationValue);
    }

    @ParameterizedTest
    @MethodSource("calibrationValueDataSource")
    void testGetCalibrationValue(String calibrationLine, int expectedCalibrationValue) {
        int calibrationValue = solution.getCalibrationValue(calibrationLine);

        assertEquals(expectedCalibrationValue, calibrationValue);
    }

    private static Stream<Arguments> solverDataSource() {
        return Stream.of(
                Arguments.of("One", inputFileURI, 55477),
                Arguments.of("Two", inputFileURI, 54431),
                Arguments.of("One", testTask1URI, 142),
                Arguments.of("Two", testTask1URI, 142),
                Arguments.of("One", testTask2URI, 209),
                Arguments.of("Two", testTask2URI, 281)
        );
    }

    private static Stream<Arguments> calibrationValueWithTextDataSource() {
        return Stream.of(
                Arguments.of("two1nine", 29),
                Arguments.of("eightwothree", 83),
                Arguments.of("abcone2threexyz", 13),
                Arguments.of("xtwone3four", 24),
                Arguments.of("4nineeightseven2", 42),
                Arguments.of("zoneight234", 14),
                Arguments.of("7pqrstsixteen", 76),
                Arguments.of("not_valid_input", 0)
        );
    }

    private static Stream<Arguments> calibrationValueDataSource() {
        return Stream.of(
                Arguments.of("1abc2", 12),
                Arguments.of("pqr3stu8vwx", 38),
                Arguments.of("a1b2c3d4e5f", 15),
                Arguments.of("treb7uchet", 77),
                Arguments.of("not_valid_input", 0)
        );
    }


}
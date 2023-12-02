package com.aoc.year2023.day02;

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
    private static final String inputFileURI = "src/main/resources/year2023/day02/input.txt";
    private static final String testTask1URI = resourcesTestURI + "year2023/day02/test_1.txt";


    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @ParameterizedTest
    @MethodSource("solverDataSource")
    void testSolve(int expectedSumResult, String inputFilePath) {
        String absolutePath = FileProcessing.getAbsolutePathFromUriString(inputFilePath);
        List<String> puzzleData = FileProcessing.readFile(absolutePath);

        int actualSumResult = solution.solve(puzzleData, false);

        assertEquals(expectedSumResult, actualSumResult);
    }

    @ParameterizedTest
    @MethodSource("getImpossibilityConfigurationsNumDataSource")
    void testGetImpossibilityConfigurationsNum(String[] data, int expectedImpossibleConfigs) {
        int actualImpossibleConfigs = solution.getImpossibilityConfigurationsNum(data, false);

        assertEquals(expectedImpossibleConfigs, actualImpossibleConfigs);
    }

    @ParameterizedTest
    @MethodSource("checkNumberThresholdDataSource")
    void testCheckNumberThreshold(String[] data, boolean expectedPossibility) {
        boolean actualPossibility = solution.checkNumberThreshold(data);

        assertEquals(expectedPossibility, actualPossibility);
    }

    @ParameterizedTest
    @MethodSource("convertBoolToPossibilityDataSource")
    void testConvertBoolToPossibility(String expectedResult, boolean isPossible) {
        String actualResult = solution.convertBoolToPossibility(isPossible);

        assertEquals(expectedResult, actualResult);
    }

    private static Stream<Arguments> solverDataSource() {
        return Stream.of(
                Arguments.of(2377, inputFileURI),
                Arguments.of(8, testTask1URI)
        );
    }

    private static Stream<Arguments> getImpossibilityConfigurationsNumDataSource() {
        return Stream.of(
                Arguments.of(new String[]{
                        "10 blue, 2 red", "7 green, 20 blue, 9 red", " 8 red, 6 green, 2 blue"}, 1),
                Arguments.of(new String[]{
                        "10 blue, 2 red", "22 green, 20 blue, 999 red", " 8 red, 6 green, 2 blue"}, 1),
                Arguments.of(new String[]{
                        "10 blue, 2 red", "22 green, 20 blue, 999 red", " 13 red, 6 green, 2 blue"}, 2),
                Arguments.of(new String[]{
                        "15 blue, 2 red", "7 green, 20 blue, 9 red", " 99 red, 6 green, 2 blue"}, 3),
                Arguments.of(new String[]{
                        "10 blue, 2 red", "7 green, 3 blue, 9 red", " 8 red, 6 green, 2 blue"}, 0)
        );
    }

    private static Stream<Arguments> checkNumberThresholdDataSource() {
        return Stream.of(
                Arguments.of(new String[]{" 1 red", " 2 green", " 99 blue"}, false),
                Arguments.of(new String[]{" 42 red", " 2 green", " 99 blue"}, false),
                Arguments.of(new String[]{" 1 red", " 420 green", " 99 blue"}, false),
                Arguments.of(new String[]{" 42 red    ", " 420 green", " 99 blue"}, false),
                Arguments.of(new String[]{" 1 red", " 2 green", " 3 blue   "}, true),
                Arguments.of(new String[]{"10 red", "11 green", "12 blue"}, true),
                Arguments.of(new String[]{"12 red", "13 green", "14 blue"}, true),
                Arguments.of(new String[]{"7 blue", "5 green", "7 red"}, true),
                Arguments.of(new String[]{"7 blue", "5 red", "7 green"}, true),
                Arguments.of(new String[]{"7 green", "5 blue", "7 red"}, true),
                Arguments.of(new String[]{"7 green", "5 red", "7 blue"}, true)
        );
    }

    private static Stream<Arguments> convertBoolToPossibilityDataSource() {
        return Stream.of(
                Arguments.of("possible", true),
                Arguments.of("impossible", false)
        );
    }
}
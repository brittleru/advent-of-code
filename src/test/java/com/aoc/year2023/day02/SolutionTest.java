package com.aoc.year2023.day02;

import com.aoc.year2023.utils.FileProcessing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
    @MethodSource("solveMinimumPowerDataSource")
    void testSolveMinimumPower(int expectedPowerResult, String inputFilePath) {
        String absolutePath = FileProcessing.getAbsolutePathFromUriString(inputFilePath);
        List<String> puzzleData = FileProcessing.readFile(absolutePath);

        int actualPowerResult = solution.solveMinimumPower(puzzleData, false);

        assertEquals(expectedPowerResult, actualPowerResult);
    }

    @ParameterizedTest
    @MethodSource("solvePossibleGamesDataSource")
    void testSolvePossibleGames(int expectedSumResult, String inputFilePath) {
        String absolutePath = FileProcessing.getAbsolutePathFromUriString(inputFilePath);
        List<String> puzzleData = FileProcessing.readFile(absolutePath);

        int actualSumResult = solution.solvePossibleGames(puzzleData, false);

        assertEquals(expectedSumResult, actualSumResult);
    }

    @ParameterizedTest
    @MethodSource("processCubeDataFromGameDataSource")
    void testProcessCubeDataFromGame(String inputs, String[] expectedResult) {
        String[] actualResult = solution.processCubeDataFromGame(inputs, false);

        assertArrayEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("computePowerForCubesDataSource")
    void testComputePowerForCubes(Map<String, Integer> inputs, int expectedResult) {
        int actualResult = solution.computePowerForCubes(inputs);

        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("computeMinimumCubesPerColorDataSource")
    void testComputeMinimumCubesPerColor(String[] inputs, Map<String, Integer> expectedResult) {
        Map<String, Integer> actualResult = solution.computeMinimumCubesPerColor(inputs);

        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("getNumCubesOfSetDataSource")
    void testGetNumCubesOfSet(String[] inputs, Map<String, Integer> expectedResult) {
        Map<String, Integer> actualResult = solution.getNumCubesOfSet(inputs);

        assertEquals(expectedResult, actualResult);
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

    private static Stream<Arguments> solveMinimumPowerDataSource() {
        return Stream.of(
                Arguments.of(71220, inputFileURI),
                Arguments.of(2286, testTask1URI)
        );
    }

    private static Stream<Arguments> solvePossibleGamesDataSource() {
        return Stream.of(
                Arguments.of(2377, inputFileURI),
                Arguments.of(8, testTask1URI)
        );
    }

    private static Stream<Arguments> processCubeDataFromGameDataSource() {
        return Stream.of(
                Arguments.of(
                        "Game 1: 1 green, 1 blue, 1 red; 3 green, 1 blue, 1 red; 4 green, 3 blue, 1 red; 4 green, 2 blue, 1 red; 3 blue, 3 green",
                        new String[]{
                                " 1 green, 1 blue, 1 red",
                                " 3 green, 1 blue, 1 red",
                                " 4 green, 3 blue, 1 red",
                                " 4 green, 2 blue, 1 red",
                                " 3 blue, 3 green"
                        }
                ),
                Arguments.of(
                        "Game 20: 7 blue; 1 blue, 6 green, 1 red; 1 red, 3 blue, 10 green; 7 green, 4 blue, 1 red; 6 green, 6 blue, 1 red; 1 red, 5 blue, 17 green",
                        new String[]{
                                " 7 blue",
                                " 1 blue, 6 green, 1 red",
                                " 1 red, 3 blue, 10 green",
                                " 7 green, 4 blue, 1 red",
                                " 6 green, 6 blue, 1 red",
                                " 1 red, 5 blue, 17 green"
                        }
                )
        );
    }

    private static Stream<Arguments> computePowerForCubesDataSource() {
        return Stream.of(
                Arguments.of(Map.ofEntries(
                        Map.entry("red", 53),
                        Map.entry("green", 4),
                        Map.entry("blue", 3)
                ), 636),
                Arguments.of(Map.ofEntries(
                        Map.entry("red", 43),
                        Map.entry("green", 4),
                        Map.entry("blue", 3)
                ), 516),
                Arguments.of(Map.ofEntries(
                        Map.entry("red", 1),
                        Map.entry("green", 99),
                        Map.entry("blue", 3)
                ), 297),
                Arguments.of(Map.ofEntries(
                        Map.entry("red", 420),
                        Map.entry("green", 420),
                        Map.entry("blue", 420)
                ), 74088000)
        );
    }

    private static Stream<Arguments> computeMinimumCubesPerColorDataSource() {
        return Stream.of(
                Arguments.of(new String[]{
                        " 1 green, 1 blue, 1 red",
                        " 3 green, 1 blue, 1 red",
                        " 4 green, 3 blue, 53 red",
                        " 4 green, 2 blue, 1 red",
                        " 3 blue, 3 green"}, Map.ofEntries(
                        Map.entry("red", 53),
                        Map.entry("green", 4),
                        Map.entry("blue", 3)
                )),
                Arguments.of(new String[]{
                        " 1 green, 1 blue, 1 red",
                        " 3 green, 1 blue, 42 red",
                        " 4 green, 3 blue, 42 red",
                        " 4 green, 2 blue, 43 red",
                        " 3 blue, 3 green"}, Map.ofEntries(
                        Map.entry("red", 43),
                        Map.entry("green", 4),
                        Map.entry("blue", 3)
                )),
                Arguments.of(new String[]{
                        " 1 green, 1 blue, 1 red",
                        " 3 green, 1 blue, 1 red",
                        " 4 green, 3 blue, 1 red",
                        " 4 green, 2 blue, 1 red",
                        " 3 blue, 99 green"}, Map.ofEntries(
                        Map.entry("red", 1),
                        Map.entry("green", 99),
                        Map.entry("blue", 3)
                )),
                Arguments.of(new String[]{
                        " 1 green, 420 blue, 1 red",
                        " 3 green, 1 blue, 420 red",
                        " 420 green, 3 blue, 1 red",
                        " 4 green, 2 blue, 1 red",
                        " 420 blue, 420 green"}, Map.ofEntries(
                        Map.entry("red", 420),
                        Map.entry("green", 420),
                        Map.entry("blue", 420)
                ))
        );
    }

    private static Stream<Arguments> getNumCubesOfSetDataSource() {
        return Stream.of(
                Arguments.of(new String[]{" 1 green", " 1 blue", " 1 red"}, Map.ofEntries(
                        Map.entry("red", 1),
                        Map.entry("green", 1),
                        Map.entry("blue", 1)
                )),
                Arguments.of(new String[]{" 45 green", " 81 blue", " 23 red"}, Map.ofEntries(
                        Map.entry("red", 23),
                        Map.entry("green", 45),
                        Map.entry("blue", 81)
                )),
                Arguments.of(new String[]{" 5 green", " 5 blue", " 3 red"}, Map.ofEntries(
                        Map.entry("green", 5),
                        Map.entry("red", 3),
                        Map.entry("blue", 5)
                )),
                Arguments.of(new String[]{" 42 green", " 42 blue", " 42 red"}, Map.ofEntries(
                        Map.entry("green", 42),
                        Map.entry("blue", 42),
                        Map.entry("red", 42)
                ))
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
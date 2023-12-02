package com.aoc.year2023.day02;

import com.aoc.year2023.utils.FileProcessing;
import com.aoc.year2023.utils.TextProcessing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    private static final String puzzleResourceURI = "src/main/resources/year2023/day02/input.txt";
    private static final Map<String, Integer> CUBES_THRESHOLD = Map.ofEntries(
            Map.entry("red", 12),
            Map.entry("green", 13),
            Map.entry("blue", 14)
    );

    public static void main(String[] args) {
        Solution solution = new Solution();

        String absolutePath = FileProcessing.getAbsolutePathFromUriString(puzzleResourceURI);
        List<String> puzzleData = FileProcessing.readFile(absolutePath);

        int possibleGamesIdsSum = solution.solvePossibleGames(puzzleData, true);
        int minimumCubesPower = solution.solveMinimumPower(puzzleData, true);
        System.out.println("\n\nThe sum of the IDs of the possible games is: " + possibleGamesIdsSum);
        System.out.println("The sum of the minimum powers of each game is: " + minimumCubesPower);
    }

    public int solveMinimumPower(List<String> gamesData, boolean isVerbose) {
        int totalPower = 0;
        if (isVerbose) {
            System.out.println("\n--- Computing minimum possible power of Cubes ---");
        }

        for (String gameDataRow : gamesData) {
            String[] cubeDataFromGame = processCubeDataFromGame(gameDataRow, isVerbose);
            Map<String, Integer> minimumCubesPerColor = computeMinimumCubesPerColor(cubeDataFromGame);
            int powerForCubes = computePowerForCubes(minimumCubesPerColor);
            if (isVerbose) {
                System.out.println("\t" + minimumCubesPerColor + " --- power: " + powerForCubes);
            }
            totalPower += powerForCubes;
        }

        return totalPower;
    }


    public int solvePossibleGames(List<String> gamesData, boolean isVerbose) {
        int possibleGamesIdsSum = 0;
        if (isVerbose) {
            System.out.println("\n--- Counting possible configurations of Cubes ---");
        }

        for (String gameDataRow : gamesData) {
            String gameId = gameDataRow.split(":")[0].split(" ")[1];

            String[] cubeDataFromGame = processCubeDataFromGame(gameDataRow, isVerbose);
            int impossibleConfigurationsCounter = getImpossibilityConfigurationsNum(cubeDataFromGame, isVerbose);
            if (impossibleConfigurationsCounter == 0) {
                possibleGamesIdsSum += Integer.parseInt(gameId);
            }
        }

        return possibleGamesIdsSum;
    }

    public String[] processCubeDataFromGame(String gameDataRow, boolean isVerbose) {
        String[] gameData = gameDataRow.split(":");
        String gameId = gameData[0].split(" ")[1];
        if (!TextProcessing.isInteger(gameId)) {
            throw new RuntimeException("Invalid data format... Game ID must be placed after Game string...");
        }
        if (isVerbose) {
            System.out.println(gameData[0] + " with ID -> " + gameId);
        }

        return gameData[1].split(";");
    }

    public int computePowerForCubes(Map<String, Integer> minimumCubesPerColor) {
        int power = 1;
        for (Integer value : minimumCubesPerColor.values()) {
            power *= value;
        }

        return power;
    }

    public Map<String, Integer> computeMinimumCubesPerColor(String[] cubeDataFromGame) {
        Map<String, Integer> minimumNumbersOfCubesOfEachColor = new HashMap<>(Map.ofEntries(
                Map.entry("red", 0),
                Map.entry("green", 0),
                Map.entry("blue", 0)
        ));

        for (String cubeData : cubeDataFromGame) {
            String[] setsOfCubes = cubeData.split(",");
            Map<String, Integer> setOfCubesData = getNumCubesOfSet(setsOfCubes);
            for (Map.Entry<String, Integer> entry : minimumNumbersOfCubesOfEachColor.entrySet()) {
                if (setOfCubesData.containsKey(entry.getKey())) {
                    int setOfCubesNumber = setOfCubesData.get(entry.getKey());
                    if (entry.getValue() < setOfCubesNumber) {
                        minimumNumbersOfCubesOfEachColor.replace(entry.getKey(), setOfCubesNumber);
                    }
                }
            }
        }

        return minimumNumbersOfCubesOfEachColor;
    }

    public Map<String, Integer> getNumCubesOfSet(String[] setOfCubes) {
        Map<String, Integer> setOfCubesData = new HashMap<>();
        for (String cubeData : setOfCubes) {
            String[] cubeDataSplit = cubeData.strip().split(" ");

            if (!TextProcessing.isInteger(cubeDataSplit[0])) {
                throw new RuntimeException(
                        "Invalid data format... Cube number must be first and then followed by Cube name");
            }
            int cubeNum = Integer.parseInt(cubeDataSplit[0]);
            String cubeName = cubeDataSplit[1];

            for (Map.Entry<String, Integer> entry : CUBES_THRESHOLD.entrySet()) {
                if (cubeName.equals(entry.getKey())) {
                    setOfCubesData.put(cubeName, cubeNum);
                }
            }
        }
        return setOfCubesData;
    }

    public int getImpossibilityConfigurationsNum(String[] cubeDataFromGame, boolean isVerbose) {
        int impossibleConfigurationsCounter = 0;
        for (String cubeData : cubeDataFromGame) {
            String[] setsOfCubes = cubeData.split(",");
            boolean isSetPossible = checkNumberThreshold(setsOfCubes);
            if (!isSetPossible) {
                impossibleConfigurationsCounter += 1;
            }
            if (isVerbose) {
                System.out.println("\t" + cubeData + " ---> " + convertBoolToPossibility(isSetPossible));
            }
        }
        return impossibleConfigurationsCounter;
    }

    public boolean checkNumberThreshold(String[] setOfCubes) {
        for (String cubeData : setOfCubes) {
            String[] cubeDataSplit = cubeData.strip().split(" ");

            if (!TextProcessing.isInteger(cubeDataSplit[0])) {
                throw new RuntimeException(
                        "Invalid data format... Cube number must be first and then followed by Cube name");
            }
            int cubeNum = Integer.parseInt(cubeDataSplit[0]);
            String cubeName = cubeDataSplit[1];

            for (Map.Entry<String, Integer> entry : CUBES_THRESHOLD.entrySet()) {
                if (cubeName.equals(entry.getKey())) {
                    if (cubeNum > entry.getValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public String convertBoolToPossibility(boolean isPossible) {
        return isPossible ? "possible" : "impossible";
    }

}

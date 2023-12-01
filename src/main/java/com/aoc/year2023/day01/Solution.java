package com.aoc.year2023.day01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    private static final String resourcesURI = "src/main/resources/";
    private static final Map<String, Integer> strNumToNum = Map.ofEntries(
            Map.entry("one", 1),
            Map.entry("two", 2),
            Map.entry("three", 3),
            Map.entry("four", 4),
            Map.entry("five", 5),
            Map.entry("six", 6),
            Map.entry("seven", 7),
            Map.entry("eight", 8),
            Map.entry("nine", 9)
    );

    public static void main(String[] args) {
        Solution solution = new Solution();

        String path = resourcesURI + "year2023/day01/input.txt";
        String absolutePath = solution.getAbsolutePathFromUriString(path);
        System.out.println("Reading the input file from: " + absolutePath);

        List<String> calibrationDocument = solution.readFile(absolutePath);
        System.out.printf("Done reading %d lines.\n", calibrationDocument.size());

        solution.displayWelcome(1, "Trebuchet?!");

        int resultTask1 = solution.solve("One", calibrationDocument);
        System.out.println("The sum of all the calibration values is: " + resultTask1);

        int resultTask2 = solution.solve("Two", calibrationDocument);
        System.out.println("The sum of all the calibration values is: " + resultTask2);
    }

    public int solve(String partNum, List<String> calibrationDocument) {
        System.out.println("\n--- Solving Part " + partNum + " ---");

        int sum = 0;
        for (String line : calibrationDocument) {
            int calibrationValue;
            if (partNum.equals("One")) {
                calibrationValue = getCalibrationValue(line);
            } else if (partNum.equals("Two")) {
                calibrationValue = getCalibrationValueWithText(line);
            } else {
                throw new RuntimeException("Part " + partNum + " not implemented...");
            }
            sum += calibrationValue;
        }

        return sum;
    }

    public int getCalibrationValueWithText(String calibrationDocumentLine) {
        Map<Integer, String> indicesOfNumberOccurrence = new HashMap<>();
        for (Map.Entry<String, Integer> entry : strNumToNum.entrySet()) {
            if (calibrationDocumentLine.contains(entry.getKey())) {
                int index = calibrationDocumentLine.indexOf(entry.getKey());
                indicesOfNumberOccurrence.put(index, entry.getKey());
            }
        }

        for (Map.Entry<Integer, String> entry : indicesOfNumberOccurrence.entrySet()) {
            String valueToReplace = strNumToNum.get(entry.getValue()) + entry.getValue();
            calibrationDocumentLine = calibrationDocumentLine.replace(entry.getValue(), valueToReplace);
        }

        return getCalibrationValue(calibrationDocumentLine);
    }

    public int getCalibrationValue(String calibrationDocumentLine) {
        StringBuilder calibrationValue = new StringBuilder();
        String[] lineCharacters = calibrationDocumentLine.split("");

        for (String character : lineCharacters) {
            if (isInteger(character)) {
                calibrationValue.append(character);
            }
        }

        if (calibrationValue.length() == 1) {
            return Integer.parseInt(calibrationValue.append(calibrationValue).toString());
        } else if (calibrationValue.length() == 2) {
            return Integer.parseInt(calibrationValue.toString());
        }
        else if (calibrationValue.length() == 0) {
            return 0;
        }


        String result = calibrationValue.substring(0, 1) + calibrationValue.substring(
                calibrationValue.length() - 1, calibrationValue.length());
        return Integer.parseInt(result);
    }

    public List<String> readFile(final String filePath) {
        List<String> fileContents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.add(line);
            }
        } catch (IOException e) {
            System.out.println("Cannot read file...");
            e.printStackTrace();
        }
        return fileContents;
    }

    public String getAbsolutePathFromUriString(String uri) {
        return new File(uri).getAbsolutePath();
    }

    private void displayWelcome(int dayNumber, String challengeName) {
        System.out.printf("\n--- Welcome to Day %2d of AoC 2023: %s ---\n", dayNumber, challengeName);
    }

    private boolean isInteger(String possibleIntegerAsString) {
        try {
            Integer.parseInt(possibleIntegerAsString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

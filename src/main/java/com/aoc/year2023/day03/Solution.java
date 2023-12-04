package com.aoc.year2023.day03;

import com.aoc.year2023.utils.FileProcessing;
import com.aoc.year2023.utils.TextProcessing;

import java.util.*;

public class Solution {

    private static final String puzzleResourceURI = "src/main/resources/year2023/day03/input.txt";

    public static void main(String[] args) {
        Solution solution = new Solution();

        String absolutePath = FileProcessing.getAbsolutePathFromUriString(puzzleResourceURI);
        List<String> puzzleData = FileProcessing.readFile(absolutePath);

        int sumOfAllPartsInEngineSchematic = solution.solve(puzzleData, true);
        System.out.println("Sum of all engine schematic part numbers: " + sumOfAllPartsInEngineSchematic);
    }

    public int solve(List<String> engineSchematic, boolean areEngineSchematicNumbersDisplayed) {
        int sum = 0;
        List<Integer> allSchematicNumbers = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < engineSchematic.size(); ++rowIndex) {
            String[] rowTokens = engineSchematic.get(rowIndex).split("");
            List<SortedSet<Integer>> digitsIndicesPerRow = new ArrayList<>();
            for (int currentTokenIndex = 0; currentTokenIndex < rowTokens.length; ++currentTokenIndex) {
                boolean isCurrentTokenNum = TextProcessing.isInteger(rowTokens[currentTokenIndex]);
                if (isCurrentTokenNum) {
                    boolean isAlreadyPresent = isElementPresentInHashSetOfLists(digitsIndicesPerRow, currentTokenIndex);
                    if (isAlreadyPresent) {
                        continue;
                    }
                    SortedSet<Integer> digitIndices = new TreeSet<>();
                    digitIndices.add(currentTokenIndex);
                    int nextIndex = currentTokenIndex + 1;
                    while (TextProcessing.isInteger(rowTokens[nextIndex])) {
                        digitIndices.add(nextIndex);
                        if (nextIndex + 1 == rowTokens.length) {
                            break;
                        }
                        ++nextIndex;
                    }

                    digitsIndicesPerRow.add(digitIndices);
                }
            }

            if (!digitsIndicesPerRow.isEmpty()) {
                for (SortedSet<Integer> digitIndices : digitsIndicesPerRow) {
                    int currentNumber = convertIndicesAndRowToInteger(rowTokens, digitIndices);
                    boolean isCurrentNumberEngineSchematic = isNumberEngineSchematic(
                            engineSchematic, rowTokens, rowIndex, digitIndices);
                    if (isCurrentNumberEngineSchematic) {
                        allSchematicNumbers.add(currentNumber);
                    }
                }
            }
        }

        if (areEngineSchematicNumbersDisplayed) {
            System.out.println("Engine schematic part numbers:\n" + allSchematicNumbers);
            System.out.println("Found a total of " + allSchematicNumbers.size() + " number parts.");
        }

        for (int num : allSchematicNumbers) {
            sum += num;
        }

        return sum;
    }

    public boolean isNumberEngineSchematic(List<String> engineSchematic, String[] rowTokens, int rowIndex,
                                           SortedSet<Integer> digitIndices) {
        boolean isEngineSchematicPreviousRow = false;
        boolean isEngineSchematicNextRow = false;
        boolean isEngineSchematicCurrentRow = processEngineSchematicRowForDigit(
                rowTokens,
                digitIndices,
                true
        );

        if (rowIndex != 0) {
            String[] previousRowTokens = engineSchematic.get(rowIndex - 1).split("");
            isEngineSchematicPreviousRow = processEngineSchematicRowForDigit(
                    previousRowTokens,
                    digitIndices,
                    false
            );
        }
        if (rowIndex != engineSchematic.size() - 1) {
            String[] nextRowTokens = engineSchematic.get(rowIndex + 1).split("");
            isEngineSchematicNextRow = processEngineSchematicRowForDigit(
                    nextRowTokens,
                    digitIndices,
                    false
            );
        }

        return isEngineSchematicPreviousRow || isEngineSchematicCurrentRow || isEngineSchematicNextRow;
    }

    public int convertIndicesAndRowToInteger(String[] row, SortedSet<Integer> digitIndices) {
        StringBuilder number = new StringBuilder();
        for (int digitIndex : digitIndices) {
            number.append(row[digitIndex]);
        }

        return Integer.parseInt(number.toString());
    }


    /**
     * Process the given row to check if the elements on the given number indices are not a number and not a period.
     * If those conditions are met then it will return true and false otherwise.
     * If the row is the corresponded row of the digitIndices then it will not parse it to check the numbers.
     */
    public boolean processEngineSchematicRowForDigit(String[] row, SortedSet<Integer> digitIndices, boolean isCurrent) {
        List<Integer> digitIndicesList = new ArrayList<>(digitIndices);

        if (digitIndicesList.get(0) != 0) {
            String elementAdjacentLeft = row[digitIndicesList.get(0) - 1];
            if (isEngineSchematicChar(elementAdjacentLeft)) {
                return true;
            }
        }
        if (digitIndicesList.get(digitIndicesList.size() - 1) != row.length - 1) {
            String elementAdjacentRight = row[digitIndicesList.get(digitIndicesList.size() - 1) + 1];
            if (isEngineSchematicChar(elementAdjacentRight)) {
                return true;
            }
        }

        if (isCurrent) {
            return false;
        }

        for (int digitIndex : digitIndicesList) {
            String currentElementAdjacent = row[digitIndex];
            if (isEngineSchematicChar(currentElementAdjacent)) {
                return true;
            }
        }

        return false;
    }


    private boolean isElementPresentInHashSetOfLists(List<SortedSet<Integer>> listOfSets, int elementToCheck) {
        for (Set<Integer> setElements : listOfSets) {
            if (setElements.contains(elementToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * If the element is not a period or a number than is an engine schematic
     */
    private boolean isEngineSchematicChar(String character) {
        return !(isPeriod(character) || TextProcessing.isInteger(character));
    }

    private boolean isPeriod(String character) {
        return character.equals(".");
    }

}

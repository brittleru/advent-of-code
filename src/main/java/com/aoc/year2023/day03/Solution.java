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
        int sumOfAllPartsInEngineSchematicStar = solution.solvePartTwo(puzzleData, true);

        System.out.println("\n\nSum of all engine schematic part numbers (Part 1): " + sumOfAllPartsInEngineSchematic);
        System.out.println("Sum of all engine schematic star part numbers (Part 2): " + sumOfAllPartsInEngineSchematicStar);
    }

    public int solvePartTwo(List<String> engineSchematic, boolean areEngineSchematicNumbersDisplayed) {
        int sum = 0;
        List<Set<Integer>> allSchematicNumbersPairs = new ArrayList<>();
        List<Map<String, Integer>> starsData = getStarsFromTheEngineSchematic(engineSchematic);

        for (Map<String, Integer> starData : starsData) {
            List<String> starPossiblePartRows = getPossiblePartRowsForStar(engineSchematic, starData);
            int starIndex = starData.get("tokenIndex");
            Set<Integer> gearNumbers = new HashSet<>(); // might need duplicates

            for (String possiblePartRow : starPossiblePartRows) {
                String[] possiblePartTokens = possiblePartRow.split("");
                if (TextProcessing.isInteger(possiblePartTokens[starIndex])) {
                    int number = getNumberAdjacentToStar(possiblePartTokens, starIndex);
                    gearNumbers.add(number);
                }
                if (starIndex > 0) {
                    if (TextProcessing.isInteger(possiblePartTokens[starIndex - 1])) {
                        int number = getNumberAdjacentToStar(possiblePartTokens, starIndex - 1);
                        gearNumbers.add(number);

                    }
                }
                if (starIndex < possiblePartTokens.length - 1) {
                    if (TextProcessing.isInteger(possiblePartTokens[starIndex + 1])) {
                        int number = getNumberAdjacentToStar(possiblePartTokens, starIndex + 1);
                        gearNumbers.add(number);

                    }
                }

            }
            allSchematicNumbersPairs.add(gearNumbers);
        }

        if (areEngineSchematicNumbersDisplayed) {
            System.out.println("\nEngine schematic part numbers (two gears):" + allSchematicNumbersPairs);
            System.out.println("Found a total of " + allSchematicNumbersPairs.size() + " gear parts.");
        }

        for (Set<Integer> gearParts : allSchematicNumbersPairs) {
            if (gearParts.size() == 2) {
                int product = gearParts.stream().reduce(1, (part1, part2) -> part1 * part2);
                sum += product;
            }
        }

        return sum;
    }

    public int getNumberAdjacentToStar(String[] possiblePartTokens, int starIndex) {
        SortedSet<Integer> digitIndices = new TreeSet<>();
        if (TextProcessing.isInteger(possiblePartTokens[starIndex])) {
            digitIndices.add(starIndex);
        }

        int searchLeftIndex = starIndex - 1;
        while (searchLeftIndex != -1) {
            if (TextProcessing.isInteger(possiblePartTokens[searchLeftIndex])) {
                digitIndices.add(searchLeftIndex);
                searchLeftIndex -= 1;
            } else {
                break;
            }
        }

        int searchRightIndex = starIndex + 1;
        while (searchRightIndex != possiblePartTokens.length) {
            if (TextProcessing.isInteger(possiblePartTokens[searchRightIndex])) {
                digitIndices.add(searchRightIndex);
                searchRightIndex += 1;
            } else {
                break;
            }
        }

        return convertIndicesAndRowToInteger(possiblePartTokens, digitIndices);
    }

    public List<String> getPossiblePartRowsForStar(List<String> engineSchematic, Map<String, Integer> starData) {
        List<String> starPossiblePartRows = new ArrayList<>();
        int rowIndex = starData.get("rowIndex");
        if (rowIndex > 0) {
            starPossiblePartRows.add(engineSchematic.get(rowIndex - 1));
        }
        starPossiblePartRows.add(engineSchematic.get(rowIndex));
        if (rowIndex < engineSchematic.size() - 1) {
            starPossiblePartRows.add(engineSchematic.get(rowIndex + 1));
        }
        return starPossiblePartRows;
    }

    public List<Map<String, Integer>> getStarsFromTheEngineSchematic(List<String> engineSchematic) {
        List<Map<String, Integer>> starsData = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < engineSchematic.size(); ++rowIndex) {
            String[] schematicRowTokens = engineSchematic.get(rowIndex).split("");

            for (int tokenIndex = 0; tokenIndex < schematicRowTokens.length; ++tokenIndex) {
                if (isElementStar(schematicRowTokens[tokenIndex])) {
                    Map<String, Integer> starData = new HashMap<>();
                    starData.put("rowIndex", rowIndex);
                    starData.put("tokenIndex", tokenIndex);
                    starsData.add(starData);
                }
            }
        }

        return starsData;
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

    private boolean isElementStar(String character) {
        return character.equals("*");
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

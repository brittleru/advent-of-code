package com.aoc.year2023.day04;


import com.aoc.year2023.utils.FileProcessing;
import com.aoc.year2023.utils.TextProcessing;

import java.util.*;

public class Solution {
    private static final String puzzleResourceURI = "src/main/resources/year2023/day04/input.txt";
    // private static final String puzzleResourceTestURI = "src/test/resources/year2023/day04/test_1.txt";

    public static void main(String[] args) {
        Solution solution = new Solution();

        String absolutePath = FileProcessing.getAbsolutePathFromUriString(puzzleResourceURI);
        List<String> puzzleData = FileProcessing.readFile(absolutePath);

        int totalWinningPoints = solution.solve(puzzleData, false);
        System.out.println("Total of winning points: " + totalWinningPoints);

        int totalScratchCards = solution.solvePartTwo(puzzleData);
        System.out.println("Total scratchcards: " + totalScratchCards);
    }


    /**
     * This works, but it takes 5 hours to compute :-)
     */
    public int solvePartTwo(List<String> scratchcardsData) {
        long startTime = System.nanoTime();

        int totalScratchCards = 0;
        Map<String, Integer> totalCopiesNumbersPerCard = new TreeMap<>(this::customCompare);
        Map<String, List<String>> totalCopiesPerCard = new TreeMap<>(this::customCompare);

        for (String cardData : scratchcardsData) {
            String[] cardDataTokens = cardData.split(": ");
            String cardName = processCardName(cardDataTokens[0]);
            String[] winningAndUserNumbers = cardDataTokens[1].split(" \\| ");
            String[] winningNumbers = winningAndUserNumbers[0].strip().split(" ");
            String[] userNumbers = winningAndUserNumbers[1].strip().split(" ");

            Set<Integer> winningNumbersSet = new HashSet<>();
            for (String winningNumber : winningNumbers) {
                if (TextProcessing.isInteger(winningNumber.strip())) {
                    winningNumbersSet.add(Integer.parseInt(winningNumber));
                }
            }

            int foundWinningNumbers = 0;
            for (String userNumber : userNumbers) {
                if (TextProcessing.isInteger(userNumber.strip())) {
                    if (winningNumbersSet.contains(Integer.parseInt(userNumber.strip()))) {
                        foundWinningNumbers += 1;
                    }
                }
            }
            List<String> nextCardNames = getNextCardNamesBasedOnPoints(cardName, foundWinningNumbers);
            totalCopiesPerCard.put(cardName, nextCardNames);
            totalCopiesNumbersPerCard.put(cardName, 1);
        }

        Map<String, List<String>> additionalCopiesPerCard = new TreeMap<>(this::customCompare);
        additionalCopiesPerCard.putAll(totalCopiesPerCard);

        for (Map.Entry<String, List<String>> copiesPerCard : additionalCopiesPerCard.entrySet()) {
            for (String cardNameCopy : copiesPerCard.getValue()) {
                List<String> originalArray = totalCopiesPerCard.get(cardNameCopy);
                if (originalArray.isEmpty()) {
                    continue;
                }
                List<String> tempCardNameCopy = new ArrayList<>(originalArray);

                if (additionalCopiesPerCard.containsKey(cardNameCopy)) {
                    List<String> existingCopies = additionalCopiesPerCard.get(cardNameCopy);
                    tempCardNameCopy.addAll(existingCopies);
                    additionalCopiesPerCard.put(cardNameCopy, tempCardNameCopy);
                }
            }
        }

        for (Map.Entry<String, Integer> instancesPerCard : totalCopiesNumbersPerCard.entrySet()) {
            List<String> instancesNamesPerCard = additionalCopiesPerCard.get(instancesPerCard.getKey());

            if (!instancesNamesPerCard.isEmpty()) {
                for (String instanceNamePerCard : instancesNamesPerCard) {
                    int previousValuePerCard = totalCopiesNumbersPerCard.get(instanceNamePerCard);
                    totalCopiesNumbersPerCard.put(instanceNamePerCard, previousValuePerCard + 1);
                }
            }
        }

        System.out.println(totalCopiesNumbersPerCard);

        for (Map.Entry<String, Integer> scratchCardsInstances : totalCopiesNumbersPerCard.entrySet()) {
            totalScratchCards += scratchCardsInstances.getValue();
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed time in nanoseconds: " + elapsedTime / 1_000_000_000.0 + " seconds");

        return totalScratchCards;
    }

    public int customCompare(String o1, String o2) {
        String[] split1 = o1.split(" ");
        String[] split2 = o2.split(" ");

        if (split1.length > 1 && split2.length == 1) {
            return 1;
        } else if (split1.length == 1 && split2.length > 1) {
            return -1;
        } else {
            if (TextProcessing.isInteger(split1[1]) && TextProcessing.isInteger(split2[1])) {
                return Integer.compare(Integer.parseInt(split1[1]), Integer.parseInt(split2[1]));
            }
            return o1.compareTo(o2);
        }
    }

    public String processCardName(String currentCardName) {
        String[] currentCardNumberData = currentCardName.strip().split(" ");
        return currentCardNumberData[0] + " " + currentCardNumberData[currentCardNumberData.length - 1];
    }

    public List<String> getNextCardNamesBasedOnPoints(String currentCardName, int pointsNum) {
        String[] currentCardNumberData = currentCardName.strip().split(" ");
        String currentCardNumber = currentCardNumberData[currentCardNumberData.length - 1];
        List<String> nextCardNames = new ArrayList<>();
        int tempCardName = Integer.parseInt(currentCardNumber.strip());
        for (int i = 0; i < pointsNum; ++i) {
            tempCardName += 1;
            nextCardNames.add("Card " + tempCardName);
        }
        return nextCardNames;
    }

    public int solve(List<String> scratchcardsData, boolean areWinningNumbersPerCardPrinted) {
        int totalPoints = 0;
        Map<String, Integer> winningNumbersFoundPerCard = new HashMap<>();

        for (String cardData : scratchcardsData) {
            String[] cardDataTokens = cardData.split(": ");
            String cardName = cardDataTokens[0];
            String[] winningAndUserNumbers = cardDataTokens[1].split(" \\| ");
            String[] winningNumbers = winningAndUserNumbers[0].strip().split(" ");
            String[] userNumbers = winningAndUserNumbers[1].strip().split(" ");

            Set<Integer> winningNumbersSet = new HashSet<>();
            for (String winningNumber : winningNumbers) {
                if (TextProcessing.isInteger(winningNumber.strip())) {
                    winningNumbersSet.add(Integer.parseInt(winningNumber));
                }
            }

            int foundWinningNumbers = 0;
            for (String userNumber : userNumbers) {
                if (TextProcessing.isInteger(userNumber.strip())) {
                    if (winningNumbersSet.contains(Integer.parseInt(userNumber.strip()))) {
                        foundWinningNumbers += 1;
                    }
                }
            }
            winningNumbersFoundPerCard.put(cardName, foundWinningNumbers);
        }

        if (areWinningNumbersPerCardPrinted) {
            System.out.println(winningNumbersFoundPerCard);
        }

        for (Map.Entry<String, Integer> winningNumbersFoundData : winningNumbersFoundPerCard.entrySet()) {
            totalPoints += getPointsFromWinningNumbersFound(winningNumbersFoundData.getValue());
        }

        return totalPoints;
    }

    public int getPointsFromWinningNumbersFound(int winningNumbersFound) {
        int points = 0;
        if (winningNumbersFound >= 1) {
            points = 1;
            for (int i = 1; i < winningNumbersFound; ++i) {
                points *= 2;
            }
        }
        return points;
    }
}

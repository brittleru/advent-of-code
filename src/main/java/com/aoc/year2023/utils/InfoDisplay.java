package com.aoc.year2023.utils;

public final class InfoDisplay {

    private InfoDisplay() {
    }

    public static void displayWelcome(int dayNumber, String challengeName) {
        System.out.printf("\n--- Welcome to Day %2d of AoC 2023: %s ---\n", dayNumber, challengeName);
    }

}

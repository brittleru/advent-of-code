package com.aoc.year2023.utils;

public final class TextProcessing {

    private TextProcessing() {
    }

    public static boolean isInteger(String possibleIntegerAsString) {
        try {
            Integer.parseInt(possibleIntegerAsString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

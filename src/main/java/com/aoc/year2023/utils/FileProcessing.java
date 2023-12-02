package com.aoc.year2023.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FileProcessing {

    private FileProcessing() {
    }

    public static List<String> readFile(final String filePath) {
        List<String> fileContents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.add(line);
            }
        } catch (IOException e) {
            System.out.println("Cannot read file...\n" + e);
        }
        return fileContents;
    }

    public static String getAbsolutePathFromUriString(String uri) {
        return new File(uri).getAbsolutePath();
    }

}

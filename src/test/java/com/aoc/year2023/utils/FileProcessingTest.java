package com.aoc.year2023.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileProcessingTest {

    private static final String resourcesTestURI = "src/test/resources/";
    private static final String inputFileDay1URI = "src/main/resources/year2023/day01/input.txt";
    private static final String inputFileDay2URI = "src/main/resources/year2023/day02/input.txt";
    private static final String testTask1URI = resourcesTestURI + "year2023/day01/test_1.txt";
    private static final String testTask2URI = resourcesTestURI + "year2023/day01/test_2.txt";


    @ParameterizedTest
    @MethodSource("readFileDataSource")
    void testReadFile(String filePath, int expectedLinesNumber) {
        String absolutePath = FileProcessing.getAbsolutePathFromUriString(filePath);

        List<String> contents = FileProcessing.readFile(absolutePath);

        assertEquals(expectedLinesNumber, contents.size());
    }

    @ParameterizedTest
    @MethodSource("getAbsolutePathFromUriStringDataSource")
    public void testGetAbsolutePathFromUriString(String uri) {
        String expectedAbsolutePath = new File(uri).getAbsolutePath();

        File mockedFile = mock(File.class);
        when(mockedFile.getAbsolutePath()).thenReturn(expectedAbsolutePath);

        String actualAbsolutePath = FileProcessing.getAbsolutePathFromUriString(uri);

        assertEquals(expectedAbsolutePath, actualAbsolutePath);
    }

    private static Stream<Arguments> readFileDataSource() {
        return Stream.of(
                Arguments.of(testTask1URI, 4),
                Arguments.of(testTask2URI, 7),
                Arguments.of(inputFileDay1URI, 1000),
                Arguments.of(inputFileDay2URI, 100)
        );
    }

    private static Stream<Arguments> getAbsolutePathFromUriStringDataSource() {
        return Stream.of(
                Arguments.of(inputFileDay1URI),
                Arguments.of(inputFileDay2URI),
                Arguments.of(testTask1URI),
                Arguments.of(testTask2URI)
        );
    }


}
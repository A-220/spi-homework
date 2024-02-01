package org.example.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class FileReaderUtil {
    public static String readAllLinesFromFile(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            Files.readAllLines(Paths.get(file.getAbsolutePath()))
                    .forEach(sb::append);
            return sb.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        return "";
    }

    public static List<String> readLinesFromFile(File file) {
        try {
            return Files.readAllLines(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
        return Collections.emptyList();
    }
}

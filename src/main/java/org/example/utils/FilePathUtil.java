package org.example.utils;

import java.io.File;

public class FilePathUtil {
    public static final String BASE_PATH = System.getProperty("user.dir");

    public static File getFile(String type) {
        return new File(getFilesPath() + type);
    }

    public static String getFilesPath() {
        return BASE_PATH +
                File.separator + "src" +
                File.separator + "main" +
                File.separator + "resources" +
                File.separator + "files" +
                File.separator + "peoples";
    }
}

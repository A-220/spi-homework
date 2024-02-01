package org.example;

import org.example.interfaces.FileParserServices;
import org.example.utils.FilePathUtil;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class App {

    public static void main(String[] args) {

        File xmlFile = FilePathUtil.getFile(".xml");
        File jsonFile = FilePathUtil.getFile(".json");
        File csvFile = FilePathUtil.getFile(".csv");

        FileParserServices fpp = getInstance(csvFile);
        if (fpp == null) {
            throw new UnsupportedOperationException("File with this type not provided right now!");
        }
        List<Map<String, String>> res = fpp.parse(csvFile);
        System.out.println(res);
    }

    public static FileParserServices getInstance(File file) {
        ServiceLoader<FileParserServices> providers = ServiceLoader.load(FileParserServices.class);
        for (FileParserServices provider : providers) {
            if (provider.isProvided(file)) {
                return provider;
            }
        }
        return null;
    }
}

package org.example.impl;

import org.example.interfaces.FileParserServices;
import org.example.utils.FileReaderUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvParserProvider implements FileParserServices {
    public static final String CSV = ".csv";

    @Override
    public List<Map<String, String>> parse(File file) {
        List<Map<String, String>> peopleList = new ArrayList<>();
        List<String> allLines = FileReaderUtil.readLinesFromFile(file);

        for (int i = 1; i < allLines.size(); i++) {
            String[] args = allLines.get(i).split(",");
            Map<String, String> personMap = new HashMap<>();
            personMap.put("name", args[0]);
            personMap.put("age", args[1]);
            personMap.put("email", args[2]);
            peopleList.add(personMap);
        }

        return peopleList;
    }

    @Override
    public Boolean isProvided(File file) {
        return file.getName().toLowerCase().endsWith(CSV);
    }
}

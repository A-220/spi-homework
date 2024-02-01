package org.example.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.interfaces.FileParserServices;
import org.example.utils.FileReaderUtil;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class JsonParserProvider implements FileParserServices {
    public static final String JSON = ".json";

    @Override
    public List<Map<String, String>> parse(File file) {
        String jsonInput = FileReaderUtil.readAllLinesFromFile(file);

        Type listType = new TypeToken<List<Map<String, String>>>() {
        }.getType();

        return new Gson().fromJson(jsonInput, listType);
    }

    @Override
    public Boolean isProvided(File file) {
        return file.getName().toLowerCase().endsWith(JSON);
    }
}

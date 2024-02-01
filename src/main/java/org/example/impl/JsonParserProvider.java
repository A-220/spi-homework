package org.example.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.interfaces.FileParserServices;
import org.example.utils.FileReaderUtil;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

public class JsonParserProvider<T> implements FileParserServices<T> {
    public static final String JSON = ".json";

    @Override
    public List<T> parse(File file, Class<T> clazz) {
        String jsonInput = FileReaderUtil.readAllLinesFromFile(file);

        Type listType = TypeToken.getParameterized(List.class, clazz).getType();

        return new Gson().fromJson(jsonInput, listType);
    }

    @Override
    public Boolean isProvided(File file) {
        return file.getName().toLowerCase().endsWith(JSON);
    }
}
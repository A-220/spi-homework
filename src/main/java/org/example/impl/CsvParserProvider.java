package org.example.impl;

import org.example.interfaces.FileParserServices;
import org.example.utils.FileReaderUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CsvParserProvider<T> implements FileParserServices<T> {
    public static final String CSV = ".csv";

    @Override
    public List<T> parse(File file, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        List<String> allLines = FileReaderUtil.readLinesFromFile(file);

        for (int i = 1; i < allLines.size(); i++) {
            String[] args = allLines.get(i).split(",");
            try {
                T obj = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {
                    Field field = fields[j];
                    field.setAccessible(true);
                    try {
                        field.set(obj, args[j]);
                        // TODO: 2/1/2024 add method to set any type of fields
                    } catch (IllegalArgumentException e) {
                        throw new UnsupportedOperationException("Type " + field.getType().getName() + " is not provided right now !");
                    }
                }
                result.add(obj);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    @Override
    public Boolean isProvided(File file) {
        return file.getName().toLowerCase().endsWith(CSV);
    }
}

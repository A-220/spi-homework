package org.example.interfaces;

import java.io.File;
import java.util.List;

public interface FileParserServices<T> {
    List<T> parse(File file, Class<T> clazz);
    Boolean isProvided(File file);

}

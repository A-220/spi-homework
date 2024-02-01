package org.example.interfaces;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface FileParserServices {
    List<Map<String, String>> parse(File file);
    Boolean isProvided(File file);

}

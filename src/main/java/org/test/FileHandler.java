package org.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {

    public List<String> loadWords(String filePath) {

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {

            return lines.collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}

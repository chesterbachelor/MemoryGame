package org.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        FileHandler fileHandler = new FileHandler();
        List<String> dictionary = fileHandler.loadWords("src/main/resources/Words.txt");
        if(dictionary == null || dictionary.size() == 0)
            System.exit(0);

        new MemoryGame(dictionary).playGame();
    }

    public List<HighScore> loadHighScores(String filePath){
        try(Stream<String> line = Files.lines(Paths.get(filePath))){

            return  line.map(currentLine -> {
                String[] columns = currentLine.split("\\|");
                return new HighScore(columns[0], columns[1], columns[2], columns[3]);
            }).collect(Collectors.toList());

        }catch(IOException e){
            System.out.println(e);
        }
        return null;
    }
}
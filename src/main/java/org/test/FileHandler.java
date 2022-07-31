package org.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {

    public List<String> loadWords(String filePath) {

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {

            return lines.collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<HighScore> loadHighScores(String filePath) {
        boolean fileExists = new File(filePath).exists();
        if (!fileExists)
            return new ArrayList<>();

        try (Stream<String> line = Files.lines(Paths.get(filePath))) {

            return line.map(currentLine -> {
                String[] columns = currentLine.split("\\|");
                return new HighScore(columns[0], columns[1], columns[2], columns[3]);
            }).collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    public void saveHighScores(String filePath, List<HighScore> highScores) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                boolean fileCreated = file.createNewFile();
                if (!fileCreated) {
                    System.out.println("Error while creating new file");
                    return;
                }
            } catch (IOException e) {
                System.out.println(e);
                return;
            }
        }

      try{
          FileWriter fileWriter = new FileWriter(filePath,false);
          for(HighScore score:highScores)
              fileWriter.write(score.toFileFormat());

          fileWriter.flush();
          fileWriter.close();
      }catch(IOException e){
          System.out.println(e);
      }

    }

}

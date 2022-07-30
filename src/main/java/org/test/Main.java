package org.test;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        FileHandler fileHandler = new FileHandler();
        List<String> dictionary = fileHandler.loadWords("src/main/resources/Words.txt");
        if(dictionary == null || dictionary.size() == 0)
            System.exit(0);

        new MemoryGame(dictionary).playGame();
    }
}
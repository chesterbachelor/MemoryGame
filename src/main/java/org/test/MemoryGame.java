package org.test;

import java.util.List;
import java.util.Scanner;

public class MemoryGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameEngine engine;

        FileHandler fileHandler = new FileHandler();
        List<String> dictionary = fileHandler.loadWords("x");
        Painter painter = new Painter();
        painter.printStartingMessage();

        engine = chooseLevel(dictionary);


        painter.paintBoard(engine.getWords(), engine.getShuffledWords());
        painter.printGameLost();


    }

    private static GameEngine chooseLevel( List<String> dictionary) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String level = scanner.nextLine().toLowerCase().trim();

            switch (level) {
                case "easy":
                    return new GameEngine(4, 10, dictionary);
                case "hard":
                    return new GameEngine(8, 15, dictionary);
                default:
                    System.out.println("UNEXPECTED TOKEN");
            }
        }
    }


}
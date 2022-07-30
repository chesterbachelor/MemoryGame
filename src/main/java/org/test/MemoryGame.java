package org.test;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MemoryGame {
    public static void main(String[] args) {

        FileHandler fileHandler = new FileHandler();
        List<String> dictionary = fileHandler.loadWords("src/main/resources/Words.txt");

        playGame(dictionary);

    }

    private static GameEngine chooseLevel(List<String> dictionary) {
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

    private static boolean doYouWantToRetry() {
        var scanner = new Scanner(System.in);

        while (true) {
            String retryChoice = scanner.nextLine().trim().toLowerCase();

            switch (retryChoice) {
                case "yes":
                    return true;
                case "no":
                    return false;
                default:
                    System.out.println("Unknown answer, type again");
            }

        }
    }

    private static void playGame(List<String> dictionary) {
        Painter painter = new Painter();
        System.out.println("Hello !!!!");
        System.out.println("Welcome to Memory Game");

        while (true) {
            painter.paintStartingMessage();
            GameEngine engine = chooseLevel(dictionary);
            gameLoop(engine, painter);
            System.out.println("Do you want to try again ? ");
            System.out.println("Yes    /    No");

            if (!doYouWantToRetry()) {
                painter.paintGoodbyeMessage();
                return;
            }
        }

    }

    private static void gameLoop(GameEngine engine, Painter painter) {

        int numOfGuesses = 0;
        while (!engine.isGameOver()) {
            if (numOfGuesses > engine.getNumOfTries()) {
                painter.paintGameLost();
                return;
            }

            clearScreen();
            painter.paintBoard(engine.getWords(), engine.getShuffledWords());

            System.out.println("Choose word to flip");
            String userChoice = getUserChoice(engine.getNumOfWords());
            while(!engine.peek(userChoice)) {
                System.out.println("Wrong choice. Choose again");
                userChoice = getUserChoice(engine.getNumOfWords());
            }

            clearScreen();
            painter.paintBoard(engine.getWords(), engine.getShuffledWords());

            System.out.println("Choose second word to flip");
            userChoice = getUserChoice(engine.getNumOfWords());
            while(!engine.peek(userChoice)) {
                System.out.println("Wrong choice. Choose again");
                userChoice = getUserChoice(engine.getNumOfWords());
            }

            clearScreen();
            painter.paintBoard(engine.getWords(), engine.getShuffledWords());

            engine.compareWords();
            numOfGuesses++;

        }
        painter.printGameWon();
    }

    public static void clearScreen() {
        for (int i = 0; i < 40; i++)
            System.out.println();
    }

    private static String getUserChoice(int maxNumber) {
        Scanner scanner = new Scanner(System.in);
        String userChoice = scanner.nextLine().trim().toLowerCase();

        while (!validateUserChoice(userChoice, maxNumber)){
            System.out.println("Enter correct postion");
            userChoice = scanner.nextLine().trim().toLowerCase();
        }
        return userChoice;
    }

    private static boolean validateUserChoice(String userChoice, int maxNumber) {

        Pattern pattern = Pattern.compile("[a-b][1-" + maxNumber + "]");

        return pattern.matcher(userChoice).matches();
    }


}
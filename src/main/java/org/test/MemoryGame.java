package org.test;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MemoryGame {
    Painter painter = new Painter();
    GameEngine engine;
    List<String> dictionary;

    public MemoryGame(List<String> dictionary) {
        this.dictionary = dictionary;
    }

    public void playGame() {
        painter.paintStartingMessage();

        while (true) {
            painter.paintChooseLevel();
            engine = chooseLevel(dictionary);
            gameLoop();

            if (!doYouWantToRetry()) {
                painter.paintGoodbyeMessage();
                return;
            }
        }

    }

    private GameEngine chooseLevel(List<String> dictionary) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String level = scanner.nextLine().toLowerCase().trim();

            switch (level) {
                case "easy":
                    return new GameEngine(4, 10, dictionary);
                case "hard":
                    return new GameEngine(8, 15, dictionary);
                default:
                    painter.paintInvalidChoice();
            }
        }
    }

    private boolean doYouWantToRetry() {
        var scanner = new Scanner(System.in);

        while (true) {
            painter.paintTryAgainMessage();
            String retryChoice = scanner.nextLine().trim().toLowerCase();

            switch (retryChoice) {
                case "yes":
                    return true;
                case "no":
                    return false;
                default:
                    painter.paintInvalidChoice();
            }
        }
    }

    private void gameLoop() {
        while (!engine.isGameOver()) {

            if (engine.isNumberOfGuessesExceeded()) {
                painter.paintGameLost();
                return;
            }

            painter.paintBoard(engine.getWords(), engine.getShuffledWords());
            guessWordsLocations();

            engine.uncoverWordsIfGuessed();
            engine.endRound();
        }
        painter.printGameWon();
    }

    private void guessWordsLocations() {
        while (!(engine.secondRowChosen && engine.firstRowChosen)) {
            painter.paintWordToFlip();

            while (!engine.peek(getUserChoice())) {
                painter.paintInvalidChoice();
            }
            painter.paintBoard(engine.getWords(), engine.getShuffledWords());
        }
    }

    private String getUserChoice() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userChoice = scanner.nextLine().trim().toLowerCase();
            if (!validateUserChoicePattern(userChoice)) {
                painter.paintInvalidChoice();
                continue;
            }
            if (!validateRowChoice(userChoice.charAt(0))) {
                painter.paintInvalidRowChoice();
                continue;
            }
            return userChoice;
        }
    }

    private boolean validateUserChoicePattern(String userChoice) {

        Pattern pattern = Pattern.compile("[a-b][1-" + engine.getNumOfWords() + "]");

        return pattern.matcher(userChoice).matches();
    }

    private boolean validateRowChoice(char row) {
        if (row == 'a' && engine.firstRowChosen) {
            return false;
        }
        if (row == 'b' && engine.secondRowChosen) {
            return false;
        }
        return true;
    }

}

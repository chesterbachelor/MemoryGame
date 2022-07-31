package org.test;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MemoryGame {
    Printer printer = new Printer();
    GameEngine engine;
    List<String> dictionary;

    public MemoryGame(List<String> dictionary) {
        this.dictionary = dictionary;
    }

    public void playGame() {
        printer.printWelcomeMessage();

        while (true) {
            printer.printChooseLevelMessage();
            engine = chooseLevel(dictionary);
            gameLoop();

            if (!doYouWantToRetry()) {
                printer.printGoodbyeMessage();
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
                    return new GameEngine(4, 10, dictionary, "Easy");
                case "hard":
                    return new GameEngine(8, 15, dictionary, "Hard");
                default:
                    printer.printIncorrectInputMessage();
            }
        }
    }

    private boolean doYouWantToRetry() {
        var scanner = new Scanner(System.in);

        while (true) {
            printer.printTryAgainMessage();
            String retryChoice = scanner.nextLine().trim().toLowerCase();

            switch (retryChoice) {
                case "yes":
                    return true;
                case "no":
                    return false;
                default:
                    printer.printIncorrectInputMessage();
            }
        }
    }

    private void gameLoop() {
        while (!engine.isGameOver()) {

            if (engine.isNumberOfTriesExceeded()) {
                printer.printGameLostMessage();
                return;
            }

            printer.printBoard(engine);
            guessWordsLocations();

            engine.uncoverWordsIfGuessed();
            engine.endRound();
        }
        printer.printGameWonMessage();
    }

    private void guessWordsLocations() {
        while (!(engine.secondRowChosen && engine.firstRowChosen)) {
            printer.printWordToFlipMessage();

            while (!engine.peek(getUserChoice())) {
                printer.printIncorrectInputMessage();
            }
            printer.printBoard(engine);
        }
    }

    private String getUserChoice() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userChoice = scanner.nextLine().trim().toLowerCase();
            if (!validateUserChoicePattern(userChoice)) {
                printer.printIncorrectInputMessage();
                continue;
            }
            if (!validateRowChoice(userChoice.charAt(0))) {
                printer.printInvalidRowChoice();
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

package org.test;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MemoryGame {
    private final Printer printer = new Printer();
    private GameEngine engine;
    private final FileHandler fileHandler = new FileHandler();
    private final List<String> dictionary;

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
        var scanner = new Scanner(System.in);
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
        engine.startTimer();
        while (!engine.isGameOver()) {

            if (engine.isNumberOfTriesExceeded()) {
                gameLost();
                return;
            }
            printer.printBoard(engine);
            guessWordsLocations();

            engine.uncoverWordsIfGuessed();
            engine.endRound();
        }
        engine.endTimer();
        gameWon();
    }
    private void gameLost(){
        printer.printGameLostMessage();
        List<HighScore> highScores = loadHighScores(fileHandler);
        printer.printHighScore(highScores);
    }

    private void gameWon() {
        printer.printGameWonMessage(engine.getGameTime(), engine.getNumOfTriesToFinishGame());
        HighScore newScore = createNewScore();

        List<HighScore> updatedHighScores = updateHighScores(newScore);
        printer.printHighScore(updatedHighScores);
        saveHighScores(fileHandler, updatedHighScores);
    }

    private List<HighScore> updateHighScores(HighScore newScore) {
        List<HighScore> highScores = loadHighScores(fileHandler);
        highScores.add(newScore);
        return highScores.stream()
                .sorted()
                .limit(10)
                .collect(Collectors.toList());
    }

    private HighScore createNewScore() {
        printer.printAskUserForNameMessage();
        var scanner = new Scanner(System.in);
        String userName = scanner.nextLine().trim();
        return new HighScore(userName, LocalDate.now(), engine.getGameTime(), engine.getNumOfTriesToFinishGame());
    }

    private List<HighScore> loadHighScores(FileHandler fileHandler) {
        String filePath = chooseHighScoreFilePath(engine.getLevel());
        return fileHandler.loadHighScores(filePath);
    }

    private void saveHighScores(FileHandler fileHandler, List<HighScore> highScores) {
        String filePath = chooseHighScoreFilePath(engine.getLevel());
        fileHandler.saveHighScores(filePath, highScores);
    }

    private String chooseHighScoreFilePath(String level) {
        if (level.equalsIgnoreCase("easy"))
            return "src/main/resources/HighScoresEasy.txt";

        return "src/main/resources/HighScoresHard.txt";
    }

    private void guessWordsLocations() {
        while (!(engine.isFirstRowChosen() && engine.isSecondRowChosen())) {
            printer.printWordToFlipMessage();

            while (!engine.peek(getUserChoice())) {
                printer.printIncorrectInputMessage();
            }
            printer.printBoard(engine);
        }
    }

    private String getUserChoice() {
        var scanner = new Scanner(System.in);

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
        if (row == 'a' && engine.isFirstRowChosen()) {
            return false;
        }
        if (row == 'b' && engine.isSecondRowChosen()) {
            return false;
        }
        return true;
    }

}

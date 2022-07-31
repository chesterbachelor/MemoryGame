package org.test;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
            engine.startTimer();
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
        engine.endTimer();
        printer.printGameWonMessage(engine.getGameTime(),engine.getNumOfTriesToFinishGame());
        HighScore newScore = createNewScore();
        FileHandler fileHandler = new FileHandler();
        List<HighScore> highScores = loadHighScores(fileHandler);

        highScores.add(newScore);
        List<HighScore> updatedHighScores = highScores.stream()
                .sorted()
                .limit(10)
                .collect(Collectors.toList());

        printer.printHighScore(updatedHighScores);
        saveHighScores(fileHandler,updatedHighScores);

    }

    private HighScore createNewScore(){
        printer.printAskUserForNameMessage();
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine().trim();
        return new HighScore(userName, LocalDate.now(), engine.getGameTime(),engine.getNumOfTriesToFinishGame());
    }
    private List<HighScore> loadHighScores(FileHandler fileHandler){

        String filePath;
        if(engine.getLevel().equalsIgnoreCase("easy")) {
            filePath = "src/main/resources/HighScoresEasy.txt";
        }
        else {
            filePath = "src/main/resources/HighScoresHard.txt";
        }
        return fileHandler.loadHighScores(filePath);
    }
    private void saveHighScores(FileHandler fileHandler, List<HighScore> highScores){
        String filePath;
        if(engine.getLevel().equalsIgnoreCase("easy")) {
            filePath = "src/main/resources/HighScoresEasy.txt";
        }
        else {
            filePath = "src/main/resources/HighScoresHard.txt";
        }
        fileHandler.saveHighScores(filePath,highScores);

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

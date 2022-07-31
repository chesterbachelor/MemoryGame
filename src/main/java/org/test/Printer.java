package org.test;

import java.util.List;

public class Printer {

    public void printBoard(GameEngine engine) {
        clearConsole();
        System.out.println("***********************");
        System.out.println("Level: " + engine.getLevel());
        System.out.println("Guess chances remaining: " + engine.getNumOfTriesRemaining());
        for(int i = 1; i<=engine.getNumOfWords();i++)
            System.out.print("      " + i);
        System.out.println();

        System.out.print("A: ");
        engine.getWords().forEach(this::printWord);

        System.out.println();

        System.out.print("B: ");
        engine.getShuffledWords().forEach(this::printWord);

        System.out.println("\n***********************");
    }

    private void printWord(Word word) {
        if (word.state == WordState.COVERED)
            System.out.print("   X");
        else
            System.out.print("   " + word.word);
    }

    public void printChooseLevelMessage() {
        System.out.println("Choose level - EASY/HARD");
    }

    public void printGameLostMessage() {
        System.out.println("0 guess chances remaining");
        System.out.println("You've lost !!!");
    }

    public void printGameWonMessage(long gameTime, int numOfTriesToFinish){
        System.out.println("Congratulations !!");
        System.out.print("You solved the memory game after " + numOfTriesToFinish + " chances. " );
        System.out.println("It took you " + gameTime + " seconds.");
    }

    public void printGoodbyeMessage(){
        System.out.println("Thanks for playing!");
    }
    public void printWelcomeMessage(){
        System.out.println("Hello !!!!");
        System.out.println("Welcome to Memory Game");
    }

    public void printTryAgainMessage(){
        System.out.println("Do you want to try again ? ");
        System.out.println("Yes    /    No");
    }
    public void printAskUserForNameMessage(){
        System.out.println("Please enter your name !");
    }
    public void printHighScore(List<HighScore> scores){
        scores.forEach(System.out::println);
    }

    public void printIncorrectInputMessage(){
        System.out.println("Incorrect input");
    }

    public void printWordToFlipMessage(){
        System.out.println("Choose word to flip");
    }

    public void printInvalidRowChoice(){
        System.out.println("Row already chosen");
    }
    public void clearConsole(){
        for (int i = 0; i < 2; i++)
            System.out.println();
    }
}

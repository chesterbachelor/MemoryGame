package org.test;

import java.util.List;

public class Painter {

    public void paintBoard(List<Word> firstRow, List<Word> secondRow) {
        clearScreen();
        System.out.println("***********************");

        System.out.print("A: ");
        firstRow.forEach(this::paintRow);

        System.out.println();

        System.out.print("B: ");
        secondRow.forEach(this::paintRow);

        System.out.println("\n***********************");
    }

    private void paintRow(Word word) {
        if (word.state == WordState.COVERED)
            System.out.print("X ");
        else
            System.out.print(word.word + " ");
    }

    public void paintChooseLevel() {
        System.out.println("Choose level - EASY/HARD");

    }

    public void paintGameLost() {
        System.out.println("***********************");
        System.out.println("You've lost !!!");

    }

    public void printGameWon(){
        System.out.println("***********************");
        System.out.println("You've won !!!");

    }
    public void paintGoodbyeMessage(){
        System.out.println("Thanks for playing!");
    }
    public void paintStartingMessage(){
        System.out.println("Hello !!!!");
        System.out.println("Welcome to Memory Game");
    }

    public void paintTryAgainMessage(){
        System.out.println("Do you want to try again ? ");
        System.out.println("Yes    /    No");
    }

    public void paintInvalidChoice(){
        System.out.println("Incorrect input");
    }

    public void paintWordToFlip(){

    }
    public void clearScreen(){
        for (int i = 0; i < 2; i++)
            System.out.println();
    }
    public void paintInvalidRowChoice(){
        System.out.println("Row already chosen");
    }
}

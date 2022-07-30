package org.test;

import java.util.List;

public class Painter {

    public void paintBoard(List<Word> firstRow, List<Word> secondRow) {
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

    public void paintStartingMessage() {
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




}

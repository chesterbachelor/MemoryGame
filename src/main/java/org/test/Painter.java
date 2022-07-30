package org.test;

import java.util.List;

public class Painter {

    public void paintBoard(List<Word> firstRow, List<Word> secondRow) {
        System.out.println("***********************");

        System.out.print("A: ");
        firstRow.forEach(this::print);

        System.out.println();

        System.out.print("B: ");
        secondRow.forEach(this::print);

        System.out.println("\n***********************");
    }

    private void print(Word word) {
        if (word.state == WordState.COVERED)
            System.out.print("X ");
        else
            System.out.print(word.word + " ");
    }

    public void printStartingMessage() {
        System.out.println("Hello !!!!");
        System.out.println("Welcome to Memory Game");
        System.out.println("Choose level - EASY/HARD");

    }

    public void printGameLost() {
        System.out.println("***********************");
        System.out.println("You've lost !!!");
        System.out.println("Do you want to try again ?");
    }

    public void printGameWon(){
        System.out.println("***********************");
        System.out.println("You've won !!!");
        System.out.println("Do you want to try again ?");
    }


}

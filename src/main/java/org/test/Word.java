package org.test;

public class Word {
    public String word;
    public WordState state = WordState.COVERED;

    public Word(String word){
        this.word = word;
    }
}

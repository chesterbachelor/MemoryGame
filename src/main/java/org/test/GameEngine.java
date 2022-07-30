package org.test;

import java.util.*;
import java.util.stream.Collectors;

public class GameEngine {
    private int numOfWords;
    private int numOfTries;
    private List<String> dictionary;
    private List<Word> words;
    private List<Word> shuffledWords;
    Word peekedWordFromFirstRow;
    Word peekedWordFromSecondRow;


    public GameEngine(int numOfWords, int numOfTries, List<String> dictionary) {
        this.numOfWords = numOfWords;
        this.numOfTries = numOfTries;
        this.dictionary = dictionary;
        words = drawWords(dictionary, numOfWords);
        shuffledWords = shuffleWords(words);
    }

    private List<Word> shuffleWords(List<Word> words) {
        List<Word> shuffle = words.stream()
                .map(word -> new Word(word.word) )
                .collect(Collectors.toList());

        Collections.shuffle(shuffle);
        return shuffle;
    }

    private List<Word> drawWords(List<String> dictionary, int numOfWordsToDraw) {
        Random random = new Random();
        Set<Integer> drawnNumbers = new HashSet<>();
        while (drawnNumbers.size() < numOfWordsToDraw) {
            int number = random.nextInt(0, this.dictionary.size());
            drawnNumbers.add(number);
        }
        return drawnNumbers.stream()
                .map(dictionary::get)
                .map(Word::new)
                .collect(Collectors.toList());

    }

    public boolean peek(String position) {
        char row = position.charAt(0);
        String sub = position.substring(1,2);
        int column = Integer.parseInt(sub) - 1;

        if (row == 'a')
            return peekFirstRow(column);
        else if (row == 'b')
            return peekSecondRow(column);

        return false;
    }

    private boolean peekFirstRow(int column) {
        if (words.get(column).state == WordState.COVERED) {
            words.get(column).state = WordState.UNCOVERED;
            peekedWordFromFirstRow = words.get(column);
            return true;
        }
        return false;

    }

    private boolean peekSecondRow(int column) {
        if (shuffledWords.get(column).state == WordState.COVERED) {
            shuffledWords.get(column).state = WordState.UNCOVERED;
            peekedWordFromSecondRow = shuffledWords.get(column);
            return true;
        }
        return false;
    }

    public void compareWords() {
        if(peekedWordFromFirstRow.word.equals(peekedWordFromSecondRow.word)){
            peekedWordFromFirstRow.state = WordState.GUESSED;
            peekedWordFromSecondRow.state = WordState.GUESSED;
        }
        else{
            peekedWordFromFirstRow.state = WordState.COVERED;
            peekedWordFromSecondRow.state = WordState.COVERED;
        }
        peekedWordFromFirstRow = null;
        peekedWordFromSecondRow = null;
    }

    public boolean isGameOver(){

        return words.stream()
                .allMatch(word -> word.state == WordState.GUESSED);
    }






    public List<Word> getWords() {
        return words;
    }

    public List<Word> getShuffledWords() {
        return shuffledWords;
    }
    public int getNumOfTries(){
        return numOfTries;
    }
    public int getNumOfWords(){
        return numOfWords;
    }

}

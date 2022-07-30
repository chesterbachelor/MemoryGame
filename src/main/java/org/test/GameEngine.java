package org.test;

import java.util.*;
import java.util.stream.Collectors;

public class GameEngine {
    private final int numOfWords;
    private final int numOfTries;
    public int numOfGuesses = 0;
    private final List<Word> words;
    private final List<Word> shuffledWords;
    Word peekedWordFromFirstRow;
    Word peekedWordFromSecondRow;
    public boolean firstRowChosen = false;
    public boolean secondRowChosen = false;

    public GameEngine(int numOfWords, int numOfTries, List<String> dictionary) {
        this.numOfWords = numOfWords;
        this.numOfTries = numOfTries;
        words = chooseRandomWords(dictionary, numOfWords);
        shuffledWords = shuffleWords(words);
    }

    private List<Word> shuffleWords(List<Word> words) {
        List<Word> shuffledList = words.stream()
                .map(word -> new Word(word.word) )
                .collect(Collectors.toList());

        Collections.shuffle(shuffledList);
        return shuffledList;
    }

    private List<Word> chooseRandomWords(List<String> dictionary, int numOfWordsToDraw) {
        Random random = new Random();
        Set<Integer> drawnNumbers = new HashSet<>();
        while (drawnNumbers.size() < numOfWordsToDraw) {
            int number = random.nextInt(0, dictionary.size());
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
            firstRowChosen = true;
            return true;
        }
        return false;

    }
    private boolean peekSecondRow(int column) {
        if (shuffledWords.get(column).state == WordState.COVERED) {
            shuffledWords.get(column).state = WordState.UNCOVERED;
            peekedWordFromSecondRow = shuffledWords.get(column);
            secondRowChosen = true;
            return true;
        }
        return false;
    }

    public void uncoverWordsIfGuessed() {
        if(peekedWordFromFirstRow.word.equals(peekedWordFromSecondRow.word)){
            peekedWordFromFirstRow.state = WordState.GUESSED;
            peekedWordFromSecondRow.state = WordState.GUESSED;
        }
        else{
            peekedWordFromFirstRow.state = WordState.COVERED;
            peekedWordFromSecondRow.state = WordState.COVERED;
        }
    }

    public void endRound(){
        numOfGuesses++;
        firstRowChosen = false;
        secondRowChosen = false;
        peekedWordFromFirstRow = null;
        peekedWordFromSecondRow = null;
    }

    public boolean isGameOver(){
        return words.stream()
                .allMatch(word -> word.state == WordState.GUESSED);
    }
    public boolean isNumberOfGuessesExceeded(){
        return numOfGuesses > numOfTries;
    }

    public List<Word> getWords() {
        return words;
    }

    public List<Word> getShuffledWords() {
        return shuffledWords;
    }

    public int getNumOfWords(){
        return numOfWords;
    }

}

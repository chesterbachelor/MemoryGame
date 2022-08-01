package org.test;

import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class Printer {

    public void printBoard(GameEngine engine) {
        clearConsole();
        System.out.println("***********************");
        System.out.println("Level: " + engine.getLevel());
        System.out.println("Guess chances remaining: " + engine.getNumOfTriesRemaining());

        List<Integer> paddingWidthForEachColumn = determineColumnWidths(engine);

        printColumnNumbers(engine, paddingWidthForEachColumn);
        printWordsRow(engine.getWords(), paddingWidthForEachColumn, 'A');
        printWordsRow(engine.getShuffledWords(), paddingWidthForEachColumn, 'B');

        System.out.println("\n***********************");
    }
    private void printColumnNumbers(GameEngine engine, List<Integer> paddingWidthForEachColumn){
        System.out.print("   ");
        for(int i = 1; i<=engine.getNumOfWords(); i++){
            System.out.print(StringUtils.rightPad("" +i,
                    paddingWidthForEachColumn.get(i-1),
                    " "));
            System.out.print(" ");
        }
        System.out.println();
    }
    private void printWordsRow(List<Word> words, List<Integer> paddingWidthForEachColumn, char row){
        System.out.print(row +": ");
        for(int i = 0;i<words.size();i++){
            printWord(words.get(i), paddingWidthForEachColumn.get(i));
        }
        System.out.println();
    }
    private List<Integer> determineColumnWidths(GameEngine engine){
        List<Integer> paddings = new ArrayList<>();
        for(int i = 0; i<engine.getNumOfWords();i++) {
            int width = determineColumnWidth(engine.getWords().get(i), engine.getShuffledWords().get(i));
            paddings.add(width);
        }
        return paddings;
    }
    private int determineColumnWidth(Word upper, Word lower){
        if(upper.state == WordState.COVERED && lower.state == WordState.COVERED){
            return 4;
        }
        return Math.max(upper.word.length(),lower.word.length());
    }

    private void printWord(Word word, int padding) {
        if (word.state == WordState.COVERED)
            System.out.print(StringUtils.rightPad("X" , padding, " "));
        else
            System.out.print(StringUtils.rightPad(word.word,padding," "));
        System.out.print(" ");
    }


    public void printChooseLevelMessage() {
        System.out.println("Choose level - EASY/HARD");
    }

    public void printGameLostMessage() {
        String laugh = "⣿⣿⣿⣿⣿⣿⡿⠛⢉⣿⣿⠿⠋⠄⢴⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣄⠄⠄\n" +
                "⣿⣿⣿⣿⣿⠏⣠⣶⣿⠟⠁⣠⣶⣶⣤⣄⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⠄\n" +
                "⣿⣿⣿⠟⠁⣴⡿⠛⠁⢠⣾⣯⣉⡉⠛⢿⣿⣿⣿⣿⣯⣍⡉⠙⢿⣿⣿⣿⣿⡇\n" +
                "⣿⡟⠁⠄⣾⠋⠄⠄⣴⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⠿⣷⣤⣽⣿⣿⣿⠄\n" +
                "⠟⠄⠄⣠⣴⠋⣰⣿⣿⣿⣿⣿⣯⣹⣟⡉⢻⣿⣿⣿⣿⣷⣤⣈⠻⣿⣿⣿⣿⡀\n" +
                "⠄⠄⣴⡟⠁⣰⣿⣿⣿⣿⣤⣤⣌⡙⠿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣿⣿⣿⣿⣿\n" +
                "⠄⢰⣿⠁⣰⣿⣿⣿⡟⢁⣤⣤⣭⡙⠳⢦⣄⠙⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟\n" +
                "⠄⣾⡇⢸⣿⣿⣿⣿⡇⢻⡏⠉⠻⠿⣷⣆⠹⡆⢸⣿⣿⣿⣿⣿⣿⣿⠋⠁⠄⠄\n" +
                "⠄⡿⠄⣡⣬⠉⣻⣿⣷⣄⣀⠄⠄⠄⠈⠛⠂⠿⢿⣿⣿⣿⣿⣿⡿⠃⠄⠄⢠⣾\n" +
                "⠄⣡⣾⡿⢇⣼⣿⠿⠿⠟⢉⣉⣠⣶⣾⣿⣿⣿⠈⣿⣿⣿⣿⡿⠁⠄⠄⣴⣿⠏\n" +
                "⣾⣿⣟⣁⣈⣩⣴⣶⣾⣿⣿⣿⠿⠟⠛⠛⠋⠡⣾⣿⣿⣿⣿⠇⠄⢀⣾⡟⠁⠄\n" +
                "⣿⣿⣿⠿⠿⠿⠛⣛⣉⣁⣀⣤⣤⣤⣤⣤⣀⣀⠘⠻⣿⣿⠟⠄⠄⣾⠟⠄⠄⠄\n" +
                "⣿⣿⣿⣷⣶⣿⣿⣿⣿⣿⡿⠿⠿⠿⠿⣿⣿⣿⣿⡆⢸⡿⠄⠄⣼⡏⠄⠄⠄⠄\n" +
                "⣿⣿⣿⣿⠿⠛⣉⣁⣤⣤⣴⣶⣶⣶⣦⣤⣌⣙⠛⢀⣈⠃⠄⠰⣿⠁⠄⠄⠄⢠\n" +
                "⣿⣿⣿⣿⣶⣿⣿⣿⣿⠿⠟⠛⣋⣉⣩⣭⠉⢋⣴⣿⣿⣿⣿⣷⣶⣦⣤⣤⣴⣿";
        System.out.println(laugh);
        System.out.println("0 guess chances remaining");
        System.out.println("HAHA !! You've lost !!! Better luck next time.");
    }

    public void printGameWonMessage(long gameTime, int numOfTriesToFinish){
        String win =
                "⠄⣠⣤⣤⣄⠄⢀⣤⣤⣤⡓⠦⢤⣤⣤⣤⣔⠦⢤⣀⣀⣤⣤⢤⣀⣤⣤⣤⡀⠄\n" +
                "⡿⠿⠗⣽⣿⠊⢸⣿⡇⣿⣿⠄⢸⣿⡇⢸⣿⠄⠄⠄⠄⣿⣿⠈⣾⣿⠁⣿⣿⠄\n" +
                "⠄⠄⣱⣿⡟⠄⢸⣿⡇⣿⣿⠄⢸⣿⡇⢸⣿⠄⠄⠄⠄⣿⣿⠄⣿⣿⠄⣿⣿⠄\n" +
                "⠄⣼⣿⠋⠈⠄⢸⣿⡇⣿⣿⢠⢸⣿⡇⢸⣿⠄⢠⠄⠄⣿⣿⢰⢸⣿⠄⣿⣿⠄\n" +
                "⠞⢿⡿⠿⠿⠄⠘⠻⠷⠿⣋⣚⣌⡻⠷⠿⢟⣘⡆⢚⡄⠿⠟⢸⣬⡛⢷⣿⣋⠄\n" +
                "⠄⠄⡇⠄⠄⠄⢀⣿⣿⣿⣿⣿⠟⡋⠉⢐⣒⣒⡒⠎⠻⡿⣛⣩⠭⠭⢤⣀⣀⠄\n" +
                "⢸⡍⠄⠄⢠⣾⣿⣿⣿⣿⣿⡓⠕⣊⣭⠥⠄⠄⢩⣍⢿⡏⣴⣶⠡⠄⠐⣮⣝⠳\n" +
                "⠸⢲⠄⢰⣿⣿⣿⣿⣿⣿⣿⣦⣉⡻⠿⠆⠄⠄⠜⣋⣼⣷⣉⠿⠦⠤⣂⣡⡶⠂\n" +
                "⠄⠸⣠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⢟⣫⣿⣿⣟⠻⠿⠿⢟⣛⠋⠄⠄\n" +
                "⠄⢀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⠄⠄\n" +
                "⠄⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⠉⢉⣉⠉⣠⣄⠄⣠⣤⡀⣠⣤⣀⣤⣀⠄⠄⠄\n" +
                "⠄⠈⢿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣄⠘⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠁⠄⠄⠄\n" +
                "⠄⢀⣀⡁⠭⣛⠿⠿⣿⣿⣿⣿⣿⣿⣦⣤⣀⣉⠄⠉⠙⠉⠋⠉⠉⠄⠄⠄⠄⠄\n" +
                "⣤⣿⣿⣿⣷⣤⣝⣛⡒⠤⠭⠉⢩⣭⠍⣿⢛⣛⣛⡛⢛⣃⠄⠄⠄⠄⠄⠄⠄⠄\n" +
                "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣾⡜⢿⠌⣵⣶⣶⣿⣿⣿⣦⠄⠄⠄⠄⠄⠄";
        System.out.println(win);
        System.out.println("Congratulations !!");
        System.out.print("You solved the memory game after " + numOfTriesToFinish + " chances. " );
        System.out.println("It took you " + gameTime + " seconds.");
    }

    public void printGoodbyeMessage(){
        System.out.println("Thanks for playing!");
    }
    public void printWelcomeMessage(){
        String welcome = "" +
                "███╗   ███╗███████╗███╗   ███╗ ██████╗ ██████╗ ██╗   ██╗     ██████╗  █████╗ ███╗   ███╗███████╗\n" +
                "████╗ ████║██╔════╝████╗ ████║██╔═══██╗██╔══██╗╚██╗ ██╔╝    ██╔════╝ ██╔══██╗████╗ ████║██╔════╝\n" +
                "██╔████╔██║█████╗  ██╔████╔██║██║   ██║██████╔╝ ╚████╔╝     ██║  ███╗███████║██╔████╔██║█████╗  \n" +
                "██║╚██╔╝██║██╔══╝  ██║╚██╔╝██║██║   ██║██╔══██╗  ╚██╔╝      ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝  \n" +
                "██║ ╚═╝ ██║███████╗██║ ╚═╝ ██║╚██████╔╝██║  ██║   ██║       ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗\n" +
                "╚═╝     ╚═╝╚══════╝╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═╝   ╚═╝        ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝\n";
        System.out.println(welcome);
        System.out.println("Hello !!!!");
    }

    public void printTryAgainMessage(){
        System.out.println("Do you want to try again ? ");
        System.out.println("Yes    /    No");
    }
    public void printAskUserForNameMessage(){
        System.out.println("Please enter your name: ");
    }
    public void printHighScore(List<HighScore> scores){
        if(scores.isEmpty()){
            System.out.println("Currently no high scores !");
            return;
        }
        System.out.println("High Scores :");
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

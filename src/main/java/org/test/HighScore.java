package org.test;

import java.time.LocalDate;

public class HighScore implements Comparable<HighScore>{
    String playerName;
    String date;
    int gameTime;
    int guessingTries;

    HighScore(String name,String date,String time,String tries){
        playerName = name;
        this.date = date;
        gameTime = Integer.parseInt(time);
        guessingTries = Integer.parseInt(tries);
    }
    HighScore(String name, LocalDate date, long gameTime, int numofTries){
        playerName = name;
        this.date = date.toString();
        this.gameTime = (int) gameTime;
        guessingTries = numofTries;
    }
    public String toFileFormat(){
        return playerName + "|" +
                date + "|" +
                gameTime + "|" +
                guessingTries + "|\n";
    }


    @Override
    public String toString() {
        return "HighScore{" +
                "playerName='" + playerName + '\'' +
                ", date='" + date + '\'' +
                ", gameTime=" + gameTime +
                ", guessingTries=" + guessingTries +
                '}';
    }
    @Override
    public int compareTo(HighScore score) {
            int gameTimeCompare = Integer.compare(this.gameTime,score.gameTime);
            if(gameTimeCompare != 0)
                return gameTimeCompare;

            return Integer.compare(this.guessingTries,score.guessingTries);
        }
    }


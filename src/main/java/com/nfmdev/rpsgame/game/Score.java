package com.nfmdev.rpsgame.game;

public class Score {
    private int playerScore = 0;
    private int machineScore = 0;
    private int currentRound = 1;

    public Score() {}

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int score) {
        playerScore = score;
    }

    public int getMachineScore() {
        return machineScore;
    }

    public void setMachineScore(int score) {
        machineScore = score;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void nextRound() {
        currentRound++;
    }
}
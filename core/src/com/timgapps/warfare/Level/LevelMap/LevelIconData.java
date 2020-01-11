package com.timgapps.warfare.Level.LevelMap;

public class LevelIconData {
    private int id, coinsCount, scorCount;
    private String levelOfDifficulty;

    public LevelIconData(int id, int coinsCount, int scoreCount, String levelOfDifficulty) {
        this.id = id;
        this.coinsCount = coinsCount;
        this.scorCount = scoreCount;
        this.levelOfDifficulty = levelOfDifficulty;
    }

    public int getId() {
        return id;
    }

    public int getCoinsCount() {
        return coinsCount;
    }

    public int getScorCount() {
        return scorCount;
    }

    public String getLevelOfDifficulty() {
        return levelOfDifficulty;
    }
}

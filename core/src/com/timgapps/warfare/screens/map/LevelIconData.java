package com.timgapps.warfare.screens.map;

import java.io.Serializable;

/**
 * класс данных о характеристиках уровня:
 * coinsCount - кол-во монет за уровень
 * scoreCount - кол-во очков за уровень
 * id - номер уровня
 * levelOfDifficulty - уровень сложности
 */
public class LevelIconData implements Serializable {
    private int id, coinsCount, scoreCount;
    private String levelOfDifficulty;
    private boolean isActive;
    private boolean isFinished;
    private int starsCount = 0;

    public LevelIconData(int id, int coinsCount, int scoreCount, String levelOfDifficulty, boolean isActive, boolean isFinished) {
        this.id = id;
        this.coinsCount = coinsCount;
        this.scoreCount = scoreCount;
        this.levelOfDifficulty = levelOfDifficulty;
        this.isActive = isActive;
        this.isFinished = isFinished;
    }

    public void setStarsCount(int count) {
        if (count > starsCount)
            starsCount = count;
    }

    public int getStarsCount() {
//        System.out.println("data.getStars count = " + starsCount);
        return starsCount;
    }

    public int getId() {
        return id;
    }

    public void setActive() {
        isActive = true;
    }

    public void setFinished() {
        this.isFinished = true;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isActiveIcon() {
        return isActive;
    }

    public int getCoinsCount() {
        return coinsCount;
    }

    public int getScoreCount() {
        return scoreCount;
    }

    public String getLevelOfDifficulty() {
        return levelOfDifficulty;
    }
}

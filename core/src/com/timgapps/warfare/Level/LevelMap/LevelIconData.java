package com.timgapps.warfare.Level.LevelMap;

import org.w3c.dom.ls.LSOutput;

import static com.timgapps.warfare.Level.LevelMap.LevelIcon.EASY;

/** класс данных о характеристиках уровня:
 * coinsCount - кол-во монет за уровень
 * scoreCount - кол-во очков за уровень
 * id - номер уровня
 * levelOfDifficulty - уровень сложности
 */
public class LevelIconData {
    private int id, coinsCount, scoreCount;
    private String levelOfDifficulty;
    private  boolean isActive;
    private int starsCount = 0;

    public LevelIconData(int id, int coinsCount, int scoreCount, String levelOfDifficulty, boolean isActive) {
        this.id = id;
        this.coinsCount = coinsCount;
        this.scoreCount = scoreCount;
        this.levelOfDifficulty = levelOfDifficulty;
        this.isActive = isActive;

    }

    public void setStarsCount(int count) {
        if (count > starsCount)
        starsCount = count;
//        System.out.println("setStarsCount = " + count);
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

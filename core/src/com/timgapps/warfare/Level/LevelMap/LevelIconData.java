package com.timgapps.warfare.Level.LevelMap;

/** класс данных о характеристиках уровня:
 * coinsCount - кол-во монет за уровень
 * scoreCount - кол-во очков за уровень
 * id - номер уровня
 * levelOfDifficulty - уровень сложности
 */
public class LevelIconData {
    private int id, coinsCount, scoreCount;
    private String levelOfDifficulty;

    public LevelIconData(int id, int coinsCount, int scoreCount, String levelOfDifficulty) {
        this.id = id;
        this.coinsCount = coinsCount;
        this.scoreCount = scoreCount;
        this.levelOfDifficulty = levelOfDifficulty;
    }

    public int getId() {
        return id;
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

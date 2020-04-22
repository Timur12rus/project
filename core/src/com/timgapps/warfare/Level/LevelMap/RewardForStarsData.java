package com.timgapps.warfare.Level.LevelMap;

public class RewardForStarsData {
    public static final int REWARD_STONE = 1;
    public static final int REWARD_ARCHER = 2;
    public static final int REWARD_BOX = 3;

    private int typeOfReward;
    private int starsCount;

    public RewardForStarsData(int typeOfReward, int starsCount) {
        this.typeOfReward = typeOfReward;
        this.starsCount = starsCount;
    }
}

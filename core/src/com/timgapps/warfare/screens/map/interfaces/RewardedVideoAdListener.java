package com.timgapps.warfare.screens.map.interfaces;

public interface RewardedVideoAdListener {
    boolean isErnedReward();

    void resetIsErnedReward();

    void resetIsLoaded();

    boolean isLoaded();

    boolean isOnline();

    boolean isError();

    boolean isInitializationComplete();

    void initializeAdmob();

    void loadRewardedVideoAd();

}

package com.timgapps.warfare.video_reward;

/*Здесь не так много деталей.
       VideoEventListener - это еще один интерфейс, который обрабатывает только события видеообъявлений (VideoEventListener.java):
       */
public interface VideoEventListener {
    void onRewardedEvent(String type, int amount);

    void onRewardedVideoAdLoadedEvent();

    void onRewardedVideoAdClosedEvent();
}
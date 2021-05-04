package com.timgapps.warfare.video_reward;

public interface GoogleServices {
    boolean hasVideoLoaded();

    void loadRewardedVideoAd();

    void showRewardedVideoAd();

    boolean isLoadedVideo();


    /*Здесь не так много деталей.
     VideoEventListener - это еще один интерфейс, который обрабатывает только события видеообъявлений (VideoEventListener.java): */

    void setVideoEventListener(VideoEventListener listener);
}

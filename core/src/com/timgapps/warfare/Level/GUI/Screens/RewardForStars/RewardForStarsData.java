package com.timgapps.warfare.Level.GUI.Screens.RewardForStars;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Warfare;

import java.io.Serializable;

public class RewardForStarsData implements Serializable {
    public static final int REWARD_STONE = 1;
    public static final int REWARD_ARCHER = 2;
    public static final int REWARD_GNOME = 3;
    public static final int REWARD_BOX = 4;
    public static final int REWARD_KNIGHT = 5;

    private int typeOfReward;
    private int starsCount;     // кол-во звезд необходимое для получения награды
    private boolean isChecked;  // флаг, если true - значит награда доступна
    private boolean isReceived;  // флаг, если true - значит награда получена
    private String imageString;

    /**
     * объект "ДАННЫЕ" для объекта "Награда за звезды"
     **/
    public RewardForStarsData(int typeOfReward, int starsCount, String imageString) {
        this.typeOfReward = typeOfReward;
        this.starsCount = starsCount;
        this.imageString = imageString;
        switch (typeOfReward) {
        }
    }

    /**
     * метод устанавливает статус - доступен или нет
     **/
    public void setChecked() {
        isChecked = true;
    }

    /**
     * получен или нет
     **/
    public void setReceived() {
        isReceived = true;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    // получена ли награда
    public boolean getIsReceived() {
        return isReceived;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public int getTypeOfReward() {
//        return RewardForStarsData.REWARD_BOX;
        return typeOfReward;
    }

    public String getImageString() {
        return imageString;
    }
}

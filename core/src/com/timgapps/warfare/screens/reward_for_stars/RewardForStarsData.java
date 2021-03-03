package com.timgapps.warfare.screens.reward_for_stars;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Warfare;

import java.io.Serializable;

public class RewardForStarsData implements Serializable {
    public static final int REWARD_STONE = 1;
    public static final int REWARD_ARCHER = 2;
    public static final int REWARD_GNOME = 3;
    public static final int REWARD_BOX = 4;
    public static final int REWARD_KNIGHT = 5;
    public static final int REWARD_SHOOTER = 6;
    public static final int REWARD_VIKING = 7;
    public static final int REWARD_FIREBOOSTER = 8;

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
    }

    public String getName() {
        switch (typeOfReward) {
            case REWARD_STONE:
                return "Stone";
            case REWARD_ARCHER:
                return "Archer";
            case REWARD_GNOME:
                return "Gnome";
            case REWARD_KNIGHT:
                return "Knight";
            case REWARD_SHOOTER:
                return "Shooter";
            case REWARD_VIKING:
                return "Viking";
            case REWARD_FIREBOOSTER:
                return "Fire";
            case REWARD_BOX:
                return "Box";
        }
        return null;
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

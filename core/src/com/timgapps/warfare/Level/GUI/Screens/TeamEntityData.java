package com.timgapps.warfare.Level.GUI.Screens;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.timgapps.warfare.Warfare;

import java.io.Serializable;

public class TeamEntityData implements Serializable {

    public static final int NONE = 0;
    public static final int GNOME = 1;
    public static final int ARCHER = 2;
    public static final int THOR = 3;
    public static final int STONE = 4;

    private int unitType;
    private int DAMAGE;
    private int HEALTH;
    private int SPEED;
    private String NAME;
    private int unitLevel;
    private boolean isUnlock = false;
    private int starsCount;

    public TeamEntityData(int unitType) {
        this.unitType = unitType;
    }

    public void setDefaultData(int unitType) {
        switch (unitType) {
            case GNOME:
                DAMAGE = 14;
                HEALTH = 30;
                starsCount = 15;
//                this.unitType = GNOME;

                // TODO: 23.01.2020 Исправить ПОЛУЧИТЬ ЗНАЧЕНИЕ УРОВНЯ ЮНИТА
                unitLevel = 1;
                break;
            case ARCHER:
                DAMAGE = 10;
                HEALTH = 30;
                starsCount = 4;
//                unitType = ARCHER;
                unitLevel = 1;
                break;

            case THOR:
                DAMAGE = 12;
                HEALTH = 20;
                unitLevel = 1;
//                unitType = THOR;
                break;

            case STONE:
                DAMAGE = 10;
                HEALTH = 50;
                starsCount = 1;
                unitLevel = 1;
                break;

            case NONE:
                DAMAGE = 10;
                HEALTH = 50;
                starsCount = 0;
                break;
        }
        if (unitType == THOR) {
            setUnlock();        // разблокируем юнита "THOR" , первый доступный юнит
        }
    }

    /**
     * метод меняет статус и картинку на разблокирован
     */
    public void setUnlock() {
        isUnlock = true;
    }

    /** метод для получения кол-ва здвёзд, необходимых для разблокировки юнита */
    public int getStarsCount() {
        return starsCount;
    }

    /** метод возвращает значение, что юнит и его картинка разблокированы ли (false - заблокирована)*/
    public boolean isUnlock() {
        return isUnlock;
    }

    public void setDAMAGE(int DAMAGE) {
        this.DAMAGE = DAMAGE;
    }

    public void setHEALTH(int HEALTH) {
        this.HEALTH = HEALTH;
    }

    public void setUnitLevel(int unitLevel) {
        this.unitLevel = unitLevel;
    }

    public int getUnitType() {
        return unitType;
    }

    public int getDAMAGE() {
        return DAMAGE;
    }

    public int getHEALTH() {
        return HEALTH;
    }

    public int getUnitLevel() {
        return unitLevel;
    }

}

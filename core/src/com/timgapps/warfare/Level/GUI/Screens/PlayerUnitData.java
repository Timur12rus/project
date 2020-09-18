package com.timgapps.warfare.Level.GUI.Screens;

import com.timgapps.warfare.Units.GameUnits.Player.units.Thor;
import com.timgapps.warfare.Units.GameUnits.UnitData;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;

import java.io.Serializable;

public class PlayerUnitData extends UnitData {
    private PlayerUnits unitId;
    private int damage;
    private int health;
    private float speed;
    private String name;
    private int unitLevel;
    private boolean isUnlock = false;
    private int starsCount;
    private int energyPrice;

    public PlayerUnitData(PlayerUnits unitId) {
        this.unitId = unitId;
    }

    // метод задает начальные значения параметров юнита
    public void setDefaultData(PlayerUnits unitId) {
        switch (unitId) {
            case Gnome:
                damage = 14;
                health = 30;
                speed = 0.7f;
                energyPrice = 15;
                deltaX = -124;
                deltaY = 0;
                barDeltaX = -84;
                barDeltaY = 0;
                starsCount = 15;
                name = "Gnome";
                // TODO: 23.01.2020 Исправить ПОЛУЧИТЬ ЗНАЧЕНИЕ УРОВНЯ ЮНИТА
                unitLevel = 1;
                break;
            case Archer:
                damage = 10;
                health = 30;
                energyPrice = 20;
                deltaX = -174;
                deltaY = 0;
                barDeltaX = -64;        // смещение healthBar по оси х
                barDeltaY = 8;
                starsCount = 4;
                speed = 0.5f;
                name = "Archer";
                unitLevel = 1;
                break;
            case Thor:
                damage = 12;
                health = 20;
                energyPrice = 15;
                deltaX = -100;
//                deltaX = -124;
                deltaY = -8;
                barDeltaX = -84;        // смещение healthBar по оси х
                barDeltaY = 0;
                unitLevel = 1;
                speed = 0.8f;
                name = "Thor";
                System.out.println("HAHAHAHAHAHAH");
                break;
            case Knight:
                damage = 15;
                health = 20;
                energyPrice = 25;
                deltaX = -142;
                deltaY = -24;
                barDeltaX = -84;        // смещение healthBar по оси х
                barDeltaY = 0;
                unitLevel = 1;
                starsCount = 50;
                speed = 0.4f;
                name = "Knight";
                break;
            case Stone:
                damage = 10;
                health = 50;
                starsCount = 1;
                energyPrice = 8;
                unitLevel = 1;
                speed = 0;
                name = "Stone";
                break;
            case None:
                energyPrice = 0;
                damage = 0;
                health = 0;
                starsCount = 0;
                speed = 0;
                name = "None";
                break;
        }
        if (unitId.equals(PlayerUnits.Thor)) {
            setUnlock();        // разблокируем юнита "THOR" , первый доступный юнит
        }
    }

    // метод возвращает значение скорость юнита
    public float getSpeed() {
        return speed;
    }

    // метод возвращает имя юнита
    public String getName() {
        return name;
    }

    /**
     * метод меняет статус и картинку на разблокирован
     */
    public void setUnlock() {
        isUnlock = true;
    }

    /**
     * метод для получения кол-ва здвёзд, необходимых для разблокировки юнита
     */
    public int getStarsCount() {
        return starsCount;
    }

    /**
     * метод возвращает значение, что юнит и его картинка разблокированы ли (false - заблокирована)
     */
    public boolean isUnlock() {
        return isUnlock;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setUnitLevel(int unitLevel) {
        this.unitLevel = unitLevel;
    }

    public PlayerUnits getUnitId() {
        return unitId;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public int getUnitLevel() {
        return unitLevel;
    }

    public int getEnergyPrice() {
        return energyPrice;
    }

    public float getDeltaX() {
        return deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }

    public float getBarDeltaX() {
        return barDeltaX;
    }

    public float getBarDeltaY() {
        return barDeltaY;
    }
}

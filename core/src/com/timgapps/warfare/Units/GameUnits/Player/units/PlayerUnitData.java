package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.timgapps.warfare.Units.GameUnits.UnitData;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;

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
    private int prepareTime;        // время приготовления
    private boolean isHired;           // призван ли юнит (т.е. доступен ли он)
    private int unitPrice;              // цена юнита, чтобы его купить

    public PlayerUnitData(PlayerUnits unitId) {
        this.unitId = unitId;
    }

    // метод задает начальные значения параметров юнита
    public void setDefaultData(PlayerUnits unitId) {
        switch (unitId) {
            case Gnome:
                name = "Gnome";
                damage = 12;
                health = 34;
                speed = 1.3f;
                energyPrice = 10;
                prepareTime = 20;
                deltaX = -124;          // смещение изображения юнита относительно тела юнита (прямоугольника)
                deltaY = 0;
                barDeltaX = -84;
                barDeltaY = 0;
                starsCount = 15;
                unitLevel = 1;
                isHired = true;     // куплен
                break;
            case Barbarian:
                name = "Barbarian";
                damage = 20;
                health = 25;
                speed = 1.2f;
                energyPrice = 20;
                prepareTime = 12;
                deltaX = -124;          // смещение изображения юнита относительно тела юнита (прямоугольника)
                deltaY = 0;
                barDeltaX = -84;
                barDeltaY = 0;
                starsCount = 15;
                unitLevel = 1;
                isUnlock = true;
                unitPrice = 250;             // стоимость юнита
                break;
            case Viking:
                name = "Viking";
                damage = 14;
                health = 30;
                speed = 1.2f;
                energyPrice = 15;
                prepareTime = 10;
                deltaX = -124;          // смещение изображения юнита относительно тела юнита (прямоугольника)
                deltaY = 0;
                barDeltaX = -84;
                barDeltaY = 0;
                starsCount = 15;
                unitLevel = 1;
                unitPrice = 200;               // стоимость юнита
                break;
            case Archer:
                name = "Archer";
                damage = 5;
                health = 18;
                speed = 0.8f;
                energyPrice = 20;
                prepareTime = 25;
                deltaX = -124;
//                deltaX = -100;
//                deltaX = -174;
                deltaY = 0;
                barDeltaX = -64;        // смещение healthBar по оси х
                barDeltaY = 8;
                starsCount = 4;
                unitLevel = 1;
                isHired = true;     // куплен
                break;
            case Shooter:
                name = "Shooter";
                damage = 10;
                health = 30;
                speed = 0.5f;
                energyPrice = 20;
                prepareTime = 25;
                deltaX = -100;
//                deltaX = -174;
                deltaY = 0;
                barDeltaX = -64;        // смещение healthBar по оси х
                barDeltaY = 8;
                starsCount = 4;
                unitLevel = 1;
                unitPrice = 200;               // стоимость юнита
                break;
            case Knight:
                name = "Knight";
                damage = 15;
                health = 20;
                speed = 0.4f;
                energyPrice = 25;
                prepareTime = 18;
                deltaX = -142;
                deltaY = -24;
                barDeltaX = -84;        // смещение healthBar по оси х
                barDeltaY = 0;
                starsCount = 50;
                unitLevel = 1;
                isHired = true;     // куплен
                break;
            case Thor:
                name = "Thor";
                damage = 10;
                health = 28;
                speed = 1.6f;
//                speed = 0.9f;
                energyPrice = 10;
                prepareTime = 3;
                deltaX = -100;
//                deltaX = -124;
                deltaY = -8;
                barDeltaX = -84;        // смещение healthBar по оси х
                barDeltaY = 0;
                unitLevel = 1;
                isUnlock = true;
                isHired = true;     // куплен
                break;
            case Rock:
                name = "Stone";
                damage = 10;
                health = 50;
                speed = 0;
                starsCount = 1;
                energyPrice = 8;
                prepareTime = 30;
                unitLevel = 1;
                isHired = true;     // куплен
                break;
            case None:
                name = "None";
                damage = 0;
                health = 0;
                speed = 0;
                energyPrice = 0;
                prepareTime = 0;
                starsCount = 0;
                break;
        }
        if (unitId.equals(PlayerUnits.Thor)) {
            setUnlock();        // разблокируем юнита "THOR" , первый доступный юнит
        }
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    // устанавливает куплен ли юнит (призван ли)
    public void setIsHired(boolean isHired) {
        this.isHired = isHired;
    }

    // возвращает, куплен ли юнит
    public boolean isHired() {
        return isHired;
    }

    public int getPrepareTime() {
        return prepareTime;
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

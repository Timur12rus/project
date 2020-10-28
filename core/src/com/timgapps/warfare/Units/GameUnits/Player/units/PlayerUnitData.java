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
    private boolean isCalled;           // призван ли юнит (т.е. доступен ли он)
    private int unitPrice;              // цена юнита, чтобы его купить

    public PlayerUnitData(PlayerUnits unitId) {
        this.unitId = unitId;
    }

    // метод задает начальные значения параметров юнита
    public void setDefaultData(PlayerUnits unitId) {
        switch (unitId) {
            case Gnome:
                name = "Gnome";
                damage = 14;
                health = 30;
                speed = 0.8f;
                energyPrice = 15;
                prepareTime = 10;
                deltaX = -124;          // смещение изображения юнита относительно тела юнита (прямоугольника)
                deltaY = 0;
                barDeltaX = -84;
                barDeltaY = 0;
                starsCount = 15;
                unitLevel = 1;
                isCalled = true;
                break;
            case Barbarian:
                name = "Barbarian";
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
                isCalled = false;
                isUnlock = true;
                unitPrice = 100;             // стоимость юнита
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
                isCalled = false;
                unitPrice = 200;               // стоимость юнита
                break;
            case Archer:
                name = "Archer";
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
                isCalled = false;
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
                isCalled = false;
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
                isCalled = false;
                break;
            case Thor:
                name = "Thor";
                damage = 12;
                health = 20;
                speed = 1.1f;
//                speed = 0.9f;
                energyPrice = 15;
                prepareTime = 18;
                deltaX = -100;
//                deltaX = -124;
                deltaY = -8;
                barDeltaX = -84;        // смещение healthBar по оси х
                barDeltaY = 0;
                unitLevel = 1;
                isUnlock = true;
                isCalled = true;
                break;
            case Rock:
                name = "Stone";
                damage = 10;
                health = 50;
                speed = 0;
                starsCount = 1;
                energyPrice = 8;
                prepareTime = 10;
                unitLevel = 1;
                isCalled = true;
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
    public void setIsCalled(boolean isCalled) {
        this.isCalled = isCalled;
    }

    // возвращает, куплен ли юнит
    public boolean isCalled() {
        return isCalled;
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

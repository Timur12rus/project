package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.timgapps.warfare.Units.GameUnits.UnitData;
import com.timgapps.warfare.Units.GameUnits.unitTypes.EnemyUnits;

public class EnemyUnitData extends UnitData {
    private EnemyUnits unitId;
    private float damage;
    private float health;
    private float speed;
    private String name;

    public EnemyUnitData(EnemyUnits unitId) {
        this.unitId = unitId;
        init();
    }

    private void init() {
        switch (unitId) {
            case Zombie1:
                damage = 6;
                health = 50;
                speed = -0.35f;
                name = "Zombie1";
                deltaX = -72;
                deltaY = 0;
                barDeltaX = 4;
                barDeltaY = 6;
                break;
            case Zombie2:
                damage = 6;
                health = 50;
                speed = -0.25f;
                name = "Zombie2";
                deltaX = -72;
                deltaY = 0;
                barDeltaX = -84;
                barDeltaY = 0;
                break;
            case Zombie3:
                damage = 6;
                health = 50;
                speed = -0.25f;
                name = "Zombie3";
                deltaX = -78;
                deltaY = 0;
                barDeltaX = -84;
                barDeltaY = 0;
                break;
        }
    }

    public EnemyUnits getUnitId() {
        return unitId;
    }

    // метод возвращает значение скорость юнита
    public float getSpeed() {
        return speed;
    }

    // метод возвращает имя юнита
    public String getName() {
        return name;
    }

    // возвращает кол-во здоровья
    public float getHealth() {
        return health;
    }

    // метод возвращает значение урона
    public float getDamage() {
        return damage;
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

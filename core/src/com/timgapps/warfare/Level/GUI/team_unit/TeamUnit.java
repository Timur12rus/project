package com.timgapps.warfare.Level.GUI.team_unit;

import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.Level.GUI.Screens.upgrade_window.UnitImage;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;

// Сущность юнит из команды (сущность)
public class TeamUnit {
    private PlayerUnitData data;
    private UnitImageButton unitImageButton;
    private CreateUnitButton createUnitButton;
    private UnitImage unitImage;
    private PlayerUnits unitId;
    private int damage;
    private int health;
    private int speed;
    private String name;
    private int unitLevel;
    private int addHealthValue;
    private int addDamageValue;
    private int timePrepare;
    private int energyCost;
    private final int MAX_UNIT_LEVEL = 10;
    private boolean isCalled;           // призван ли юнит (т.е. куплени ли он, нужно ли будет его покупать)

    @Override
    public String toString() {
        return name;
    }

    /**
     * Объект СУЩНОСТЬ КОМАНДЫ (ЮНИТ) в массиве команды или коллекции
     *
     * @param data - data, данные: параметры юнита
     **/
    public TeamUnit(PlayerUnitData data) {
        this.unitId = data.getUnitId();
        this.data = data;
        name = data.getName();
        unitImageButton = new UnitImageButton(data);
        speed = (int) (data.getSpeed() * 10 * 2);           // скорость юнита отображаемая в характеристиках в таблице апгрейда
        addHealthValue = 2;
        addDamageValue = 2;
        timePrepare = 10;
        energyCost = 15;
        damage = data.getDamage();
        health = data.getHealth();
        unitLevel = data.getUnitLevel();
        unitImage = new UnitImage(unitId, unitLevel, energyCost);
        isCalled = data.isCalled();     // призван ли юнит (т.е. куплен ли он)
    }

    public int getMaxUnitLevel() {
        return MAX_UNIT_LEVEL;
    }

    public void unlock() {
        unitImageButton.unlock();
    }

    /**
     * метод для добавления количества здоровья
     **/
    public void addHEALTH(int health) {
        this.health += health;
    }

    /**
     * метод для добавления количества урона
     **/
    public void addDamage(int damage) {
        this.damage += damage;
    }

    // возвращает ширину кнопки юнита
    public float getImageButtonWidth() {
        return unitImageButton.getWidth();
    }

    // возвращает высоту кнопки юнита
    public float getImageButtonHeight() {
        return unitImageButton.getHeight();
    }

    public UnitImage getUnitImage() {
        return unitImage;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAddHealthValue() {
        return addHealthValue;
    }

    public int getAddDamageValue() {
        return addDamageValue;
    }

    public int getTimePrepare() {
        return timePrepare;
    }

    public int getUnitLevel() {
        return unitLevel;
    }

    public void setUnitLevel(int unitLevel) {
        this.unitLevel = unitLevel;
    }

    public UnitImageButton getUnitImageButton() {
        return unitImageButton;
    }

    public String getName() {
        return name;
    }

    public PlayerUnits getUnitId() {
        return unitId;
    }

    public PlayerUnitData getUnitData() {
        return data;
    }

    public void updateTeamEntityData() {
        data.setUnitLevel(unitLevel);
        data.setHealth(health);
        data.setDamage(damage);
    }
}


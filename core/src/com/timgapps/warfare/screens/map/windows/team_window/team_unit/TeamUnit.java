package com.timgapps.warfare.screens.map.windows.team_window.team_unit;

import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.screens.map.windows.upgrade_window.UpgradeWindow;
import com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements.UnitImage;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;

// Сущность юнит из команды или коллекции (сущность)
public class TeamUnit {
    private PlayerUnitData data;
    private UnitImageButton unitImageButton;
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
    private boolean isHired;           // призван ли юнит (т.е. куплени ли он, нужно ли будет его покупать)
    private boolean isCanUpgrade;       // может ли быть улучшен юнит
    private int upgradeCost;            // кол-во монет, необходимых для апгрейда

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
        this.name = data.getNameLabel();
        unitImageButton = new UnitImageButton(data);
        speed = (int) (data.getSpeed() * 10 * 2);           // скорость юнита отображаемая в характеристиках в таблице апгрейда
        addHealthValue = 2;
        addDamageValue = 2;
        timePrepare = data.getPrepareTime();
        energyCost = data.getEnergyPrice();
        damage = data.getDamage();
        health = data.getHealth();
        unitLevel = data.getUnitLevel();
        unitImage = new UnitImage(unitId, unitLevel, energyCost);
        isHired = data.isHired();     // призван ли юнит (т.е. куплен ли он)
        upgradeCost = unitLevel * UpgradeWindow.COST_UPGRADE;
    }

    public boolean isHired() {
        return isHired;
    }

    public void setIsHired(boolean isHired) {
        data.setIsHired(isHired);
        this.isHired = isHired;
    }

    public int getUnitPrice() {
        return data.getUnitPrice();
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
    public void addHealth(int health) {
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

    // изменяет значение уровня юнита и значение уровня юнита в значке
    public void setUnitLevel(int unitLevel) {
        this.unitLevel = unitLevel;
//        unitImage.setLevelValue(unitLevel);
        unitImageButton.setLevelValue(unitLevel);
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

    // метод устанавливает может ли юинт быть улучшенным
    public void setCanUpgrade(boolean isCanUpgrade) {
        this.isCanUpgrade = isCanUpgrade;
    }

    // устанавливает кол-во монет для апгрейда
    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public boolean isCanUpgrade() {
        return isCanUpgrade;
    }

    // метод запускает действие движения значка уровня юнита вверх-вниз
    public void startCanUpgradeAction() {
        if (isCanUpgrade) {
            unitImageButton.startLevelIconAction(); // запускаем действие движения значка уровня юнита, если юнит может быть улучшен
        }
    }

    // метод очищает действие движения значка уровня юнита вверх-вниз
    public void clearCanUpgradeAction() {
        unitImageButton.clearLevelIconAction();
    }
}


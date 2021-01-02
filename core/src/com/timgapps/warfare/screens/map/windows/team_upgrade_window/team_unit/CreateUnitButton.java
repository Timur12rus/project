package com.timgapps.warfare.screens.map.windows.team_upgrade_window.team_unit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Warfare;

// кнопка для создания юинтов на сцене
public class CreateUnitButton extends UnitImageButton {
    protected TextureRegion darkLayer;
    private boolean isUnlock;
    private float appearanceTime;
    protected float interpolation;
    private int energyPrice;
    protected float percentage = 0;
    private LevelScreen levelScreen;
    private boolean isActive = false;

    public CreateUnitButton(LevelScreen levelScreen, PlayerUnitData playerUnitData) {
        super(playerUnitData);
        this.levelScreen = levelScreen;
        // это для теста energyPrice = 1
//        appearanceTime = 1;       // время появления юнита
//        energyPrice = 1;          // кол-во энергии для появления юнита

        appearanceTime = playerUnitData.getPrepareTime();    // время появления юнита
        energyPrice = playerUnitData.getEnergyPrice();          // кол-во энергии для появления юнита
        interpolation = (height / appearanceTime) / 60;

        darkLayer = new TextureRegion(Warfare.atlas.findRegion("unitButtonDark"));
        height = darkLayer.getRegionHeight();
        lockImage.setVisible(false);
        setInActive();
        unitLevelIcon.setIsActiveIcon(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (levelScreen.getState() == LevelScreen.PLAY) {
            if (!isReadyUnitButton) {                         // если юнит не готов к появлению
                if (percentage < height) {                   // проверяем, прошло ли достаточно времени, чтобы родился юнит
                    percentage += interpolation;
                } else {
                    isReadyUnitButton = true;                             // если энергии хватает, делаем кнопку активной
                }
            } else if (checkEnergyCount(energyPrice)) {
                setActive();
            } else {
                setInActive();
            }
        }
    }

    @Override
    public void touchedDown() {
        if (isReadyUnitButton) {
            super.touchedDown();
        }
    }

    @Override
    public void touchedUp() {
        if (isReadyUnitButton) {
            super.touchedUp();
        }
    }

    @Override
    public void buttonClicked() {
        super.buttonClicked();
        if ((isReadyUnitButton) && (checkEnergyCount(energyPrice))) {
            isReadyUnitButton = false;
            setInActive();
            System.out.println("Add new Player unit " + playerUnitData.getUnitId());
            levelScreen.subEnergyCount(energyPrice);
            levelScreen.createPlayerUnit(playerUnitData.getUnitId());
        }
    }

    @Override
    public void setInActive() {
        super.setInActive();
        // что то не так!!!!!!
        if (isActive == true) {
            unitLevelIcon.setIsActiveIcon(false);
            isActive = false;
        }
    }

    public void setActive() {
        percentage = 0;
        if (isActive == false) {
            activeImage.setVisible(true);
            inactiveImage.setVisible(false);
            unitLevelIcon.setIsActiveIcon(true);
            isActive = true;
        }
    }

    protected boolean checkEnergyCount(int energyPrice) {
        if (levelScreen.getEnergyCount() >= energyPrice)
            return true;
        else return false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!isReadyUnitButton) {
            batch.setColor(Color.BLACK);
            batch.draw(darkLayer, getX(), getY() - 10, darkLayer.getRegionWidth(), height - percentage);
            batch.setColor(Color.WHITE);
        }
    }
}

package com.timgapps.warfare.Level.GUI.team_unit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

// кнопка для созждания юинтов на сцене
public class CreateUnitButton extends UnitImageButton {
    protected TextureRegion darkLayer;
    private boolean isUnlock;
    private float appearanceTime;
    protected float interpolation;
    private int energyPrice;
    protected float percentage = 0;
    private Level level;

    public CreateUnitButton(Level level, PlayerUnitData playerUnitData) {
        super(playerUnitData);
        this.level = level;
        // это для теста
        appearanceTime = 1;       // время появления юнита
        energyPrice = 1;          // кол-во энергии для появления юнита

//        appearanceTime = playerUnitData.getAppearanceTime();    // время появления юнита
//        energyPrice = playerUnitData.getEnergyPrice();          // кол-во энергии для появления юнита
        interpolation = (height / appearanceTime) / 60;

        darkLayer = new TextureRegion(Warfare.atlas.findRegion("unitButtonDark"));
        height = darkLayer.getRegionHeight();
        lockImage.setVisible(false);
        setInActive();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (level.getState() == Level.PLAY) {
            if (!isReadyUnitButton) {
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
    public void buttonClicked() {
        super.buttonClicked();
        if ((isReadyUnitButton) && (checkEnergyCount(energyPrice))) {
            isReadyUnitButton = false;
            setInActive();
            System.out.println("Add new Player unit " + playerUnitData.getUnitId());
            level.subEnergyCount(energyPrice);
            level.createPlayerUnit(playerUnitData.getUnitId());
        }
    }

    public void setActive() {
        percentage = 0;
        activeImage.setVisible(true);
        inactiveImage.setVisible(false);
    }

    protected boolean checkEnergyCount(int energyPrice) {
        if (level.getEnergyCount() >= energyPrice)
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

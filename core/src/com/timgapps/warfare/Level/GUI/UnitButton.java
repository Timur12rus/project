package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.GUI.Screens.TeamUnit;
import com.timgapps.warfare.Level.GUI.Screens.PlayerUnitData;
import com.timgapps.warfare.Level.Level;
//import com.timgapps.warfare.Units.GameUnits.Player.units.Archer1;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Stone;
import com.timgapps.warfare.Units.GameUnits.Player.units.Gnome;
import com.timgapps.warfare.Units.GameUnits.Player.units.Knight;
import com.timgapps.warfare.Units.GameUnits.Player.units.Thor;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;
import com.timgapps.warfare.Warfare;

/**
 * класс - кнопки на ЭКРАНЕ БИТВЫ для появления юнитов
 **/
public class UnitButton extends Group {
    protected Image activeImage;
    protected Image inactiveImage;
    protected boolean isReadyUnitButton = false;
    private Level level;
    protected TextureRegion darkLayer;
    protected float height;
    protected float interpolation;
    protected float appearanceTime;
    protected float percentage = 0;
    protected int energyPrice;
    protected PlayerUnitData data;
    protected PlayerUnits unitType;
    //    protected int typeOfUnit;
    protected int damage;
    protected int health;

//    public enum TypeOfUnit {GNOME, ARCHER1, THOR, STONE}

    /**
     * класс UnitButton - кнопки юнитов для их поялвнеия
     **/
    public UnitButton(final Level level, Image activeImage, Image inactiveImage, PlayerUnitData data) {
        this.data = data;
        damage = data.getDamage();
        health = data.getHealth();
        unitType = data.getUnitId();
        this.appearanceTime = setAppearanceTime(unitType);    // время необходимое для рождения юнита
        this.energyPrice = setEnergyPrice(data.getEnergyPrice());          // количество энергии, необходимое для рождения юнита
        this.activeImage = activeImage;
        this.inactiveImage = inactiveImage;
        this.level = level;

//        System.out.println("energyPrice = " + energyPrice);

        addActor(activeImage);
        addActor(inactiveImage);
        darkLayer = new TextureRegion(Warfare.atlas.findRegion("unitButtonDark"));
        setInActive();

        height = darkLayer.getRegionHeight();
        interpolation = (height / appearanceTime) / 60;

        if (unitType != PlayerUnits.Stone) {
            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if ((isReadyUnitButton) && (checkEnergyCount(energyPrice))) {
                        isReadyUnitButton = false;
                        setInActive();
//                    level.addGnome();
                        addPlayerUnit(unitType);
                    }
                }
            });
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!isReadyUnitButton) {
            batch.setColor(Color.BLACK);
            batch.draw(darkLayer, getX(), getY(), darkLayer.getRegionWidth(), height - percentage);
            batch.setColor(Color.WHITE);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (level.getState() == Level.PLAY) {
            if (!isReadyUnitButton) {
                if (percentage < height) {                   // проверяем, прошло ли достаточно времени, чтобы родился юнит
                    percentage += interpolation;
                } else
//                if (checkEnergyCount(energyPrice)) {  // если времени достаточно для рождения юнита, проверяем хватает ли энергии
                    isReadyUnitButton = true;                             // если энергии хватает, делаем кнопку активной
//                setActive();                             // если энергии хватает, делаем кнопку активной
//            }
            } else
//            (isReadyUnitButton) {                     //
                if (checkEnergyCount(energyPrice))
                    setActive();
                else {
                    setInActive();
                }
        }
    }

    /**
     * метод выполняет появление юнита на экране, вычитает из текущего кол-ва энергии кол-во энергии необходимое для появления юнита
     **/
    private void addPlayerUnit(PlayerUnits unitType) {
//        level.removeFinger();
        switch (unitType) {
            case Gnome:
                level.addGnome(health, damage);
                level.setEnergyCount(Gnome.getEnergyPrice());    // установим количество энергии, вычтем стоимость энергии для появления юнита
                break;
            case Archer:
                level.addArcher1(health, damage);
//                level.setEnergyCount(Archer1.getEnergyPrice());    // установим количество энергии, вычтем стоимость энергии для появления юнита
                break;
            case Thor:
                level.addThor(health, damage);
                level.setEnergyCount(Thor.getEnergyPrice());    // установим количество энергии, вычтем стоимость энергии для появления юнита
                break;
            case Knight:
                level.addKnight(health, damage);
                level.setEnergyCount(Knight.getEnergyPrice());  // установим количество энергии, вычтем стоимость энергии для появления юнита
                break;
        }
    }

    public void setActive() {
        percentage = 0;
        activeImage.setVisible(true);
        inactiveImage.setVisible(false);
//        isReadyUnitButton = true;
    }

    public void setInActive() {
        activeImage.setVisible(false);
        inactiveImage.setVisible(true);
//        isReadyUnitButton = false;
    }

    public boolean getUnitButtonStatus() {
        return isReadyUnitButton;
    }

    private float setAppearanceTime(PlayerUnits unitType) {
        float appearanceTime = 0;
        switch (unitType) {
            case Gnome:
                appearanceTime = Gnome.getAppearanceTime();
                break;
            case Archer:
//                appearanceTime = Archer1.getAppearanceTime();
                break;
            case Thor:
                appearanceTime = Thor.getAppearanceTime();
                break;
            case Knight:
                appearanceTime = Thor.getAppearanceTime();
                break;
            case Stone:
                appearanceTime = Stone.getAppearanceTime();
        }

        return appearanceTime;
    }

    protected int setEnergyPrice(int typeOfUnit) {
        int energyPrice = 0;
        switch (typeOfUnit) {
            case TeamUnit.GNOME:
                energyPrice = Gnome.getEnergyPrice();
                break;
            case TeamUnit.ARCHER:
//                energyPrice = Archer1.getEnergyPrice();
                break;
            case TeamUnit.THOR:
                energyPrice = Thor.getEnergyPrice();
                break;
            case TeamUnit.STONE:
                energyPrice = Stone.getEnergyPrice();
        }

        return energyPrice;
    }

    protected boolean checkEnergyCount(int energyPrice) {
        if (level.getEnergyCount() >= energyPrice)
            return true;
        else return false;
    }

    public boolean getIsUnitButtonReady() {
        return isReadyUnitButton;
    }
}

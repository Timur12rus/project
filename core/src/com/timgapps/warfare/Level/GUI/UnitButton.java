package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntityData;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Player.Archer1;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Stone;
import com.timgapps.warfare.Units.GameUnits.Player.Gnome;
import com.timgapps.warfare.Units.GameUnits.Player.Thor;
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
    protected TeamEntityData data;
    protected int typeOfUnit;
    protected int damage;
    protected int health;

//    public enum TypeOfUnit {GNOME, ARCHER1, THOR, STONE}

    /**
     * класс UnitButton - кнопки юнитов для их поялвнеия
     **/
    public UnitButton(final Level level, Image activeImage, Image inactiveImage, TeamEntityData data) {
        this.data = data;
        damage = data.getDAMAGE();
        health = data.getHEALTH();
        typeOfUnit = data.getUnitType();
        this.appearanceTime = setAppearanceTime(typeOfUnit);    // время необходимое для рождения юнита
        this.energyPrice = setEnergyPrice(typeOfUnit);          // количество энергии, необходимое для рождения юнита
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

        if (typeOfUnit != TeamEntity.STONE) {
            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if ((isReadyUnitButton) && (checkEnergyCount(energyPrice))) {
                        isReadyUnitButton = false;
                        setInActive();
//                    level.addGnome();
                        addPlayerUnit(typeOfUnit);
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
    private void addPlayerUnit(int typeOfUnit) {
//        level.removeFinger();
        switch (typeOfUnit) {
            case TeamEntity.GNOME:
                level.addGnome(health, damage);
                level.setEnergyCount(Gnome.getEnergyPrice());    // установим количество энергии, вычтем стоимость энергии для появления юнита
                break;
            case TeamEntity.ARCHER:
                level.addArcher1(health, damage);
                level.setEnergyCount(Archer1.getEnergyPrice());    // установим количество энергии, вычтем стоимость энергии для появления юнита
                break;
            case TeamEntity.THOR:
                level.addThor(health, damage);
                level.setEnergyCount(Thor.getEnergyPrice());    // установим количество энергии, вычтем стоимость энергии для появления юнита
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

    private float setAppearanceTime(int typeOfUnit) {
        float appearanceTime = 0;
        switch (typeOfUnit) {
            case TeamEntity.GNOME:
                appearanceTime = Gnome.getAppearanceTime();
                break;
            case TeamEntity.ARCHER:
                appearanceTime = Archer1.getAppearanceTime();
                break;
            case TeamEntity.THOR:
                appearanceTime = Thor.getAppearanceTime();
                break;
            case TeamEntity.STONE:
                appearanceTime = Stone.getAppearanceTime();
        }

        return appearanceTime;
    }

    protected int setEnergyPrice(int typeOfUnit) {
        int energyPrice = 0;
        switch (typeOfUnit) {
            case TeamEntity.GNOME:
                energyPrice = Gnome.getEnergyPrice();
                break;
            case TeamEntity.ARCHER:
                energyPrice = Archer1.getEnergyPrice();
                break;
            case TeamEntity.THOR:
                energyPrice = Thor.getEnergyPrice();
                break;
            case TeamEntity.STONE:
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

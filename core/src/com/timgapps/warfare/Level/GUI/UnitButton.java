package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Player.Archer1;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Stone;
import com.timgapps.warfare.Units.GameUnits.Player.Gnome;
import com.timgapps.warfare.Warfare;

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

    public enum TypeOfUnit {GNOME, ARCHER1, STONE}

    public UnitButton(final Level level, Image activeImage, Image inactiveImage, final TypeOfUnit typeOfUnit) {
        this.appearanceTime = setAppearanceTime(typeOfUnit);    // время необходимое для рождения юнита
        this.energyPrice = setEnergyPrice(typeOfUnit);          // количество энергии, необходимое для рождения юнита
        this.activeImage = activeImage;
        this.inactiveImage = inactiveImage;
        this.level = level;

        System.out.println("energyPrice = " + energyPrice);

        addActor(activeImage);
        addActor(inactiveImage);
        darkLayer = new TextureRegion(Warfare.atlas.findRegion("unitButtonDark"));
        setInActive();

        height = darkLayer.getRegionHeight();
        interpolation = (height / appearanceTime) / 60;

        if (!typeOfUnit.equals(TypeOfUnit.STONE)) {
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

//}

    private void addPlayerUnit(TypeOfUnit typeOfUnit) {
        switch (typeOfUnit) {
            case GNOME:
                level.addGnome();
                level.setEnergyCount(Gnome.getEnergyPrice());
                break;
            case ARCHER1:
                level.addArcher1();
                level.setEnergyCount(Archer1.getEnergyPrice());
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

    private float setAppearanceTime(TypeOfUnit typeOfUnit) {
        float appearanceTime = 0;
        switch (typeOfUnit) {
            case GNOME:
                appearanceTime = Gnome.getAppearanceTime();
                break;
            case ARCHER1:
                appearanceTime = Archer1.getAppearanceTime();
                break;
            case STONE:
                appearanceTime = Stone.getAppearanceTime();
        }

        return appearanceTime;
    }

    protected int setEnergyPrice(TypeOfUnit typeOfUnit) {
        int energyPrice = 0;
        switch (typeOfUnit) {
            case GNOME:
                energyPrice = Gnome.getEnergyPrice();
                break;
            case ARCHER1:
                energyPrice = Archer1.getEnergyPrice();
                break;
            case STONE:
                energyPrice = Stone.getEnergyPrice();
        }

        return energyPrice;
    }

    protected boolean checkEnergyCount(int energyPrice) {
        if (level.getEnergyCount() >= energyPrice)
            return true;
        else return false;
    }
}

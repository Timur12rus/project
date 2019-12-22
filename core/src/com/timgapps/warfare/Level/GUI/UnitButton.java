package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.Player.Archer1;
import com.timgapps.warfare.Units.Player.Gnome;
import com.timgapps.warfare.Warfare;

public class UnitButton extends Group {
    private Image activeImage;
    private Image inactiveImage;
    private boolean isActiveUnitButton = false;
    private Level level;
    private TextureRegion darkLayer;
    private float height;
    private float interpolation;
    private float appearanceTime;
    private float percentage = 0;

    public enum TypeOfUnit {GNOME, ARCHER1}

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!isActiveUnitButton) {
            batch.setColor(Color.BLACK);
            batch.draw(darkLayer, getX(), getY(), darkLayer.getRegionWidth(), height - percentage);
            batch.setColor(Color.WHITE);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!isActiveUnitButton) {
            if (percentage < height) {
                percentage += interpolation;
            } else {
                setActive();
            }
        }
    }

    public UnitButton(final Level level, Image activeImage, Image inactiveImage, final TypeOfUnit typeOfUnit) {
        this.appearanceTime = setAppearanceTime(typeOfUnit);
        this.activeImage = activeImage;
        this.inactiveImage = inactiveImage;
        this.level = level;

        addActor(activeImage);
        addActor(inactiveImage);
        darkLayer = new TextureRegion(Warfare.atlas.findRegion("unitButtonDark"));
        setInActive();


        height = darkLayer.getRegionHeight();
        interpolation = (height / appearanceTime) / 60;

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (isActiveUnitButton) {
                    setInActive();
//                    level.addGnome();
                    addPlayerUnit(typeOfUnit);
                }
            }
        });
    }

    private void addPlayerUnit(TypeOfUnit typeOfUnit) {
        switch (typeOfUnit) {
            case GNOME:
                level.addGnome();
                break;
            case ARCHER1:
                level.addArcher1();
                break;
        }
    }

    public void setActive() {
        percentage = 0;
        activeImage.setVisible(true);
        inactiveImage.setVisible(false);
        isActiveUnitButton = true;
    }

    public void setInActive() {
        activeImage.setVisible(false);
        inactiveImage.setVisible(true);
        isActiveUnitButton = false;
    }

    public boolean getUnitButtonStatus() {
        return isActiveUnitButton;
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
        }

        return appearanceTime;
    }


}

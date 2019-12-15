package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class UnitButton extends Group {
    private Image activeImage;
    private Image inactiveImage;
    private boolean isActiveUnitButton = false;
    private Level level;
    private TextureRegion darkLayer;
    private float height;
    private float interpolation;
    private float time = 15;
    private float percentage = 0;

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

    public UnitButton(final Level level, Image activeImage, Image inactiveImage) {
        this.activeImage = activeImage;
        this.inactiveImage = inactiveImage;
        this.level = level;

        addActor(activeImage);
        addActor(inactiveImage);
        darkLayer = new TextureRegion(Warfare.atlas.findRegion("unitButtonDark"));
        setInActive();


        height = darkLayer.getRegionHeight();
        interpolation = (height / time) / 60;

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (isActiveUnitButton) {
                    setInActive();
                    level.addGnome();
                }
            }
        });

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


}

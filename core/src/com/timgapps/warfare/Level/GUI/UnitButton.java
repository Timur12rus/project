package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.Level;

public class UnitButton extends Group {
    private Image activeImage;
    private Image inactiveImage;
    private boolean isActiveUnitButton = false;
    private Level level;

    public UnitButton(final Level level, Image activeImage, Image inactiveImage) {
        this.activeImage = activeImage;
        this.inactiveImage = inactiveImage;
        this.level = level;
        addActor(activeImage);
        addActor(inactiveImage);
        setInActive();

        activeImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setInActive();
                level.addGnome();
            }
        });
    }

    public void setActive() {
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

package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.Player.Bullets.Stone;
import com.timgapps.warfare.Warfare;

public class StoneButton extends UnitButton {


    private Image greenTarget, redTarget;
    private float posX, posY;

    public StoneButton(final Level level, Image activeImage, Image inactiveImage, TypeOfUnit typeOfUnit) {
        super(level, activeImage, inactiveImage, typeOfUnit);
        greenTarget = new Image(Warfare.atlas.findRegion("targetGreen"));
        redTarget = new Image(Warfare.atlas.findRegion("targetRed"));
//        posX = x;
//        posY = y;
        addActor(greenTarget);
        addActor(redTarget);
        inactiveTargetImages();


        this.addListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                greenTarget.setVisible(true);
                greenTarget.setPosition(x, y);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
//                greenTarget.setVisible(false);
//                greenTarget.setPosition(0, 0);
                throwStone(level, x + getX() + greenTarget.getWidth() / 2, y, 10);
            }
        });

    }

    private void throwStone(Level level, float x, float y, float damage) {
        new Stone(level, x, y + 600, damage, y + greenTarget.getHeight() / 2);
    }

    private void inactiveTargetImages() {
        greenTarget.setVisible(false);
        redTarget.setVisible(false);
    }

    @Override
    public void setInActive() {
        super.setInActive();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}

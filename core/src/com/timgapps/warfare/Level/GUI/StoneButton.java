package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Stone;
import com.timgapps.warfare.Warfare;

public class StoneButton extends UnitButton {


    private Image greenTarget, redTarget;
    private float posX, posY;
    private final float Y_MIN = 100;
    private final float Y_MAX = 280;
    private final float X_MIN = -680;
    private final float X_MAX = 220;
//    protected int energyPrice;

    public StoneButton(final Level level, Image activeImage, Image inactiveImage, TypeOfUnit typeOfUnit) {
        super(level, activeImage, inactiveImage, typeOfUnit);
        greenTarget = new Image(Warfare.atlas.findRegion("targetGreen"));
        redTarget = new Image(Warfare.atlas.findRegion("targetRed"));
//        this.energyPrice = setEnergyPrice(typeOfUnit);
//        posX = x;
//        posY = y;
        addActor(greenTarget);
        addActor(redTarget);
        inactiveTargetImages();

        this.addListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                x -= greenTarget.getWidth();
                if (isReadyUnitButton) {
                    greenTarget.setVisible(true);
                    greenTarget.setPosition(x, y);
                    redTarget.setPosition(greenTarget.getX(), greenTarget.getY());
                    checkTargetCoordinates(x, y);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                x -= greenTarget.getWidth();
                if (isReadyUnitButton) {
                    if (greenTarget.isVisible()) {
                        throwStone(level, x + getX() + greenTarget.getWidth() / 2, y, 5);
                        isReadyUnitButton = false;
                    }
                    greenTarget.setVisible(false);
                    redTarget.setVisible(false);
                    greenTarget.setPosition(0, 0);
                    redTarget.setPosition(0, 0);
                }
            }
        });
    }

    private void checkTargetCoordinates(float x, float y) {
        if (x < X_MIN || x > X_MAX || y < Y_MIN || y > Y_MAX) {
//            System.out.println("x = " + x);
            greenTarget.setVisible(false);
            redTarget.setVisible(true);
        } else {
            greenTarget.setVisible(true);
            redTarget.setVisible(false);
        }
    }

    private void throwStone(Level level, float x, float y, float damage) {
        setInActive();
        level.setEnergyCount(Stone.getEnergyPrice());
        new Stone(level, x, y + 600, damage, 14 + y + greenTarget.getHeight() / 2);
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
//        System.out.println("isReadyUnitButton StoneButton" + isReadyUnitButton);
    }
}

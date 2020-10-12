package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.GUI.Screens.PlayerUnitData;
import com.timgapps.warfare.Level.GUI.team_unit.UnitButton;
import com.timgapps.warfare.Level.GUI.team_unit.UnitImageButton;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Stone;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;
import com.timgapps.warfare.Warfare;

public class StoneButton extends UnitImageButton {
    private Image greenTarget, redTarget;
    private final float Y_MIN = 100;
    private final float Y_MAX = 280;
    private final float X_MIN = -680;
    private final float X_MAX = 300;

    private float unitButtonTablePosX = 0;
    private int damage;
    private int health;
    private Level level;
    private PlayerUnitData data;

    //     if (stoneButton != null) stoneButton.setUnitButtonTablePosX(tableUnitButtons.getX());
    public StoneButton(final Level level, PlayerUnitData data) {
        super(data.getUnitId(), data.isUnlock());
        this.level = level;
        this.unitButtonTablePosX = unitButtonTablePosX;
        greenTarget = new Image(Warfare.atlas.findRegion("targetGreen"));
        redTarget = new Image(Warfare.atlas.findRegion("targetRed"));
        damage = data.getDamage();
        health = data.getHealth();
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
                        throwStone(level, getX() + unitButtonTablePosX + x + greenTarget.getWidth() / 2, y, damage, health);
//                        throwStone(level, x + getX() + greenTarget.getWidth() / 2, y, 5);
//                        System.out.println("GetX = " + getX());
//                        System.out.println("x = " + x);
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

    public void setPosX(float posX) {
        unitButtonTablePosX = posX;
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

    private void throwStone(Level level, float x, float y, float damage, float health) {
        setInActive();
        level.setEnergyCount(Stone.getEnergyPrice());
        new Stone(level, x, y + 600, damage, health, 32 + y + greenTarget.getHeight() / 2);
//        new Stone(level, x, y + 600, damage, 14 + y + greenTarget.getHeight() / 2);
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

    public void setUnitButtonTablePosX(float posX) {
        unitButtonTablePosX = posX;
    }
}

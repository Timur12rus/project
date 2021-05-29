package com.timgapps.warfare.screens.level;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.screens.map.windows.team_window.team_unit.CreateUnitButton;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Stone;
import com.timgapps.warfare.Warfare;

public class StoneButton extends CreateUnitButton {
    //public class StoneButton extends UnitImageButton {
    protected Image greenTarget, redTarget;
    protected final float Y_MIN = 100;
    //    protected final float Y_MIN = 100;
    protected final float Y_MAX = 280;

    protected float deltaPosX = 0;
    private int damage;
    private int health;
    protected float xMin, xMax, yMin, yMax;
    protected final float DELTA_Y_TARGET_ICON = 64;
    //    protected final float DELTA_Y_TARGET_ICON = 32;
    protected float greenTargetWidth;
    protected float greenTargetHeight;

    //     if (stoneButton != null) stoneButton.setUnitButtonTablePosX(tableUnitButtons.getX());
    public StoneButton(final LevelScreen levelScreen, PlayerUnitData data) {
        super(levelScreen, data);
        this.levelScreen = levelScreen;
        greenTarget = new Image(Warfare.atlas.findRegion("targetGreen"));
        redTarget = new Image(Warfare.atlas.findRegion("targetRed"));
        greenTargetWidth = greenTarget.getWidth();
        greenTargetHeight = greenTarget.getHeight();
        greenTarget.debug();
        redTarget.debug();
        damage = data.getDamage();
        health = data.getHealth();
        addActor(greenTarget);
        addActor(redTarget);
        inactiveTargetImages();
        yMin = Y_MIN;
        yMax = Y_MAX;
    }

    @Override
    public void touchedDragged(float x, float y) {
        super.touchedDragged(x, y);
        if (isTouchedDown) {
            x -= greenTargetWidth / 2;
            y += DELTA_Y_TARGET_ICON;
            if (isReadyUnitButton) {               // если камень "готов" к запуску
                greenTarget.setVisible(true);
                greenTarget.setPosition(x, y);
                redTarget.setPosition(greenTarget.getX(), greenTarget.getY());
                checkTargetCoordinates(x + deltaPosX, y);
            }
        }
    }

    @Override
    public void buttonClicked(float x, float y) {

    }

    @Override
    public void touchedDown() {
        super.touchedDown();
    }

    @Override
    public void touchedUp(float x, float y) {
        super.touchedUp(x, y);
        if (isTouchedDown && greenTarget.isVisible() && levelScreen.getState() == LevelScreen.PLAY) {
//        if ((isReadyUnitButton) && (checkEnergyCount(energyPrice))) {
            isReadyUnitButton = false;
            setInActive();
            levelScreen.subEnergyCount(energyPrice);
            throwBullet(levelScreen, x + deltaPosX, y + DELTA_Y_TARGET_ICON, damage, health);
//            throwBullet(levelScreen, x + deltaPosX, y + 24, damage, health);
            System.out.println("x + deltaPosX  = " + x + deltaPosX);
        }
        greenTarget.setVisible(false);
        redTarget.setVisible(false);
        greenTarget.setPosition(0, 0);
        redTarget.setPosition(0, 0);
        isTouchedDown = false;
    }


    public void setPosX(float posX) {
        deltaPosX = posX;
        System.out.println("PosX = " + posX);
        System.out.println("towerX = " + levelScreen.getSiegeTower().getX());
        System.out.println("barricadeX = " + levelScreen.getBarricade().getX());
        xMin = (levelScreen.getSiegeTower().getBodyPosition().x);
        System.out.println("levelScreen.getSiegeTower().getBodyPosition().x = " + levelScreen.getSiegeTower().getBodyPosition().x);
//        xMin = unitButtonTablePosX - (levelScreen.getSiegeTower().getX() + levelScreen.getSiegeTower().getWidth());
//        xMax = levelScreen.getBarricade().getX() - unitButtonTablePosX;
        xMax = levelScreen.getBarricade().getX();
    }

    private void checkTargetCoordinates(float x, float y) {
        x -= greenTargetWidth / 2;
//        y += greenTargetHeight / 2;
        System.out.println("X MIN === " + xMin);
        System.out.println("X MAX === " + xMax);
        System.out.println("current X = " + x);
//        if (x < X_MIN || x > X_MAX || y < Y_MIN || y > Y_MAX) {
        if (x < xMin || x > xMax || y < yMin || y > yMax) {
//            System.out.println("x = " + x);
            greenTarget.setVisible(false);
            redTarget.setVisible(true);
        } else {
            greenTarget.setVisible(true);
            redTarget.setVisible(false);
        }
    }


    protected void throwBullet(LevelScreen levelScreen, float x, float y, float damage, float health) {
        new Stone(levelScreen, new Vector2(x, y + greenTarget.getHeight()), playerUnitData);
//        new Stone(levelScreen, new Vector2(x, y + DELTA_Y_TARGET_ICON + greenTarget.getHeight() / 2), playerUnitData);
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
    public void act(float delta) {
        super.act(delta);
//        System.out.println("isReadyUnitButton StoneButton" + isReadyUnitButton);
    }
}

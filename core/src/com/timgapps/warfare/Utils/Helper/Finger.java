package com.timgapps.warfare.Utils.Helper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;

public class Finger extends Image {
    private float x, y;
    private Image fingerImage;
    public static final float WIDTH = 72;
    public static final float HEIGHT = 84;
    public static final int DOWN = 1;
    public static final int UP = 2;
    public static final int RIGHT = 3;
    public static final int LEFT = 4;
    private int orientation;
    private RepeatAction repeatAction;
    private boolean isStarted = false;
    private LevelScreen levelScreen;

    public Finger(LevelScreen levelScreen, float x, float y, int orientation, TextureRegion textureRegion) {
        super(textureRegion);
        this.levelScreen = levelScreen;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        float angleRotation = 0;

        MoveByAction actionOne = new MoveByAction();
        actionOne.setDuration(0.5f);
        actionOne.setInterpolation(Interpolation.smooth);

//        MoveToAction actionTwo = new MoveToAction();
        MoveByAction actionTwo = new MoveByAction();
        actionTwo.setDuration(0.5f);
        actionTwo.setInterpolation(Interpolation.smooth);

        switch (orientation) {
            case DOWN:
                angleRotation = 180;
//                actionOne.setAmount(x, y + 16);
//                actionTwo.setAmount(x, y);
                actionOne.setAmount(0, 16);
                actionTwo.setAmount(0, -16);
                break;
            case UP:
                angleRotation = 0;
//                actionOne.setAmount(x, y + 16);
//                actionTwo.setAmount(x, y);
                actionOne.setAmount(0, 16);
                actionTwo.setAmount(0, -16);
                break;
            case LEFT:
                angleRotation = 90;
                actionOne.setAmount(16, 0);
                actionTwo.setAmount(-16, 0);
                break;
            case RIGHT:
                angleRotation = -90;
                actionOne.setAmount(-16, y);
                actionTwo.setAmount(16, y);
                break;
            default:
                angleRotation = 0;
                actionOne.setAmount(0, 0);
                actionTwo.setAmount(- 16, y);
        }
        setRotation(angleRotation);

        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(actionOne);
        sequenceAction.addAction(actionTwo);

        repeatAction = new RepeatAction();

        repeatAction.setCount(RepeatAction.FOREVER);
        repeatAction.setAction(sequenceAction);

//        addAction(repeatAction);

    }

    // метод для отображения "пальца-указателя"
    public void show() {
        if (!isVisible()) {
            setVisible(true);
        }
        if (!isStarted) {
            isStarted = true;
            addAction(repeatAction);
        }
    }

    // метод для скрытия "пальца-указателя"
    public void hide() {
        if (isVisible()) {
            setVisible(false);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (levelScreen.getState() != LevelScreen.PLAY) {
            if (isVisible()) {
                hide();
            }
        } else {
            show();
        }
    }
}

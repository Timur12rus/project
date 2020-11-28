package com.timgapps.warfare.screens.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Warfare;

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

    public Finger(float x, float y, int orientation, TextureRegion textureRegion) {
        super(textureRegion);

        this.x = x;
        this.y = y;
        this.orientation = orientation;
        float angleRotation = 0;

        MoveToAction actionOne = new MoveToAction();
        actionOne.setDuration(0.5f);
        actionOne.setInterpolation(Interpolation.smooth);

        MoveToAction actionTwo = new MoveToAction();
        actionTwo.setDuration(0.5f);
        actionTwo.setInterpolation(Interpolation.smooth);

        switch (orientation) {
            case DOWN:
                angleRotation = 180;
                actionOne.setPosition(x, y + 16);
                actionTwo.setPosition(x, y);
                break;
            case UP:
                angleRotation = 0;
                actionOne.setPosition(x, y + 16);
                actionTwo.setPosition(x, y);
                break;
            case LEFT:
                angleRotation = 90;
                actionOne.setPosition(x, y);
                actionTwo.setPosition(x + 16, y);
                break;
            case RIGHT:
                angleRotation = -90;
                actionOne.setPosition(x, y);
                actionTwo.setPosition(x + 16, y);
                break;
            default:
                angleRotation = 0;
                actionOne.setPosition(x, y);
                actionTwo.setPosition(x - 16, y);
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
        if (!isVisible()) setVisible(true);
        if (!isStarted) {
            isStarted = true;
            addAction(repeatAction);
        }
    }

    // метод для скрытия "пальца-указателя"
    public void hide() {
        if (isVisible()) setVisible(false);
    }

}

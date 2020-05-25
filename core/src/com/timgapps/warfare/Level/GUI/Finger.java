package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
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

    public Finger(float x, float y, int orientation) {
        super(Warfare.atlas.findRegion("finger"));

        this.x = x;
        this.y = y;
        this.orientation = orientation;
        float angleRotation = 0;
        switch (orientation) {
            case DOWN:
                angleRotation = 180;
                break;
            case UP:
                angleRotation = 0;
                break;
            case LEFT:
                angleRotation = 90;
                break;
            case RIGHT:
                angleRotation = -90;
                break;
        }
        setRotation(angleRotation);

        MoveToAction actionOne = new MoveToAction();
        actionOne.setDuration(0.5f);
        actionOne.setPosition(x, y + 16);
        actionOne.setInterpolation(Interpolation.smooth);

        MoveToAction actionTwo = new MoveToAction();
        actionTwo.setDuration(0.5f);
        actionTwo.setPosition(x, y);
        actionTwo.setInterpolation(Interpolation.smooth);

        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(actionOne);
        sequenceAction.addAction(actionTwo);

        repeatAction = new RepeatAction();

        repeatAction.setCount(RepeatAction.FOREVER);
        repeatAction.setAction(sequenceAction);

//        addAction(repeatAction);

    }

    public void show() {
        if (!isVisible()) setVisible(true);
        if (!isStarted) {
            isStarted = true;
            addAction(repeatAction);
        }
    }

    public void hide() {
        if (isVisible()) setVisible(false);
    }

}
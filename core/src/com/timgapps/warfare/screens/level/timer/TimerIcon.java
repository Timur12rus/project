package com.timgapps.warfare.screens.level.timer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;

public class TimerIcon extends Group {
    private Image icon, bg;
    private Label textLabel;
    private final float TIMER_COUNT = 30;
    private boolean isStarted;
    private boolean isStop;
    private boolean actionIsStarted;
    private LevelScreen levelScreen;
    private String text;
    private float textLabelX, textLabelY;

    public TimerIcon(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
        icon = new Image(Warfare.atlas.findRegion("timerIcon_head"));
        bg = new Image(Warfare.atlas.findRegion("timerIcon_bg"));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.WHITE;
        labelStyle.font = Warfare.font20;
        textLabel = new Label("00 : 00", labelStyle);
//        textLabel.setText("00 : 00");
        setSize(bg.getWidth(), bg.getHeight());
//        setSize(textLabel.getWidth(), textLabel.getHeight() + icon.getHeight() + 24);
        icon.setPosition(16, 16);
        setVisible(false);
        addActor(bg);
        addActor(icon);
        addActor(textLabel);
        setPosition(levelScreen.getWidth() - getWidth() - 32,
                levelScreen.getHeight() - 128 - getHeight());
        levelScreen.addChild(this);
        textLabelX = (bg.getWidth() - textLabel.getWidth()) / 2;
        textLabelY = -textLabel.getHeight() - 6;
        textLabel.setPosition(textLabelX, textLabelY);
//        textLabel.setPosition((icon.getWidth() - textLabel.getWidth()) / 2, -textLabel.getHeight() - 12);
        addAction(Actions.fadeOut(0));
    }

    public void update(float count) {
        if ((TIMER_COUNT - count) >= 0) {
            if ((int) (TIMER_COUNT - count) < 10) {
                if (!actionIsStarted) {
                    startAction();
                }
                text = "0" + (int) (TIMER_COUNT - count);
            } else {
                text = "" + (int) (TIMER_COUNT - count);
            }
            textLabel.setText("00 : " + text);
        } else if (!isStop) {
            stop();
        }
//        textLabel.setPosition((icon.getWidth() - textLabel.getWidth()) / 2, -textLabel.getHeight() - 24);
    }

    @Override
    public void act(float delta) {
        if (levelScreen.getState() != LevelScreen.PAUSED) {
            super.act(delta);
        }
    }

    public void stop() {
        isStop = true;
        icon.clearActions();
        addAction(Actions.fadeOut(1));
    }

    public void start() {
        setVisible(true);
        isStarted = true;
        addAction(Actions.fadeIn(1.6f));
    }

    public void startAction() {
        actionIsStarted = true;
        MoveToAction moveToActionOne = new MoveToAction();
        moveToActionOne.setPosition(10, 10);
        moveToActionOne.setDuration(0.5f);
        moveToActionOne.setInterpolation(Interpolation.smooth);

        SizeToAction sizeToActionOne = new SizeToAction();
        sizeToActionOne.setSize(icon.getWidth() + 12, icon.getHeight() + 12);
        sizeToActionOne.setDuration(0.5f);
        sizeToActionOne.setInterpolation(Interpolation.smooth);

        ParallelAction parallelActionOne = new ParallelAction();
        parallelActionOne.addAction(moveToActionOne);
        parallelActionOne.addAction(sizeToActionOne);

        MoveToAction moveToActionTwo = new MoveToAction();
        moveToActionTwo.setPosition(16, 16);
        moveToActionTwo.setDuration(0.5f);
        moveToActionTwo.setInterpolation(Interpolation.smooth);

        SizeToAction sizeToActionTwo = new SizeToAction();
        sizeToActionTwo.setSize(icon.getWidth(), icon.getHeight());
        sizeToActionTwo.setDuration(0.5f);
        sizeToActionTwo.setInterpolation(Interpolation.smooth);

        ParallelAction parallelActionTwo = new ParallelAction();
        parallelActionTwo.addAction(moveToActionTwo);
        parallelActionTwo.addAction(sizeToActionTwo);

        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(parallelActionOne);
        sequenceAction.addAction(parallelActionTwo);

        RepeatAction repeatAction = new RepeatAction();
        repeatAction.setCount(RepeatAction.FOREVER);
        repeatAction.setAction(sequenceAction);
        icon.addAction(repeatAction);
    }

    public void reset() {
        setVisible(false);
        isStarted = false;
        isStop = false;
        actionIsStarted = false;
        icon.clearActions();
        clearActions();
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isStop() {
        return isStop;
    }
}

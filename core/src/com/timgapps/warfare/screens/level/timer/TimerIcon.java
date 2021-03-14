package com.timgapps.warfare.screens.level.timer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
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
    private final float TIME_TO_WAVE = 30;
    //    private final float TIMER_COUNT = 30;
    private boolean isStarted;
    private boolean isStop;
    private boolean actionIsStarted;
    private LevelScreen levelScreen;
    private String text;
    private float textLabelX, textLabelY;
    private float iconPosX, iconPosY;
    private float time;
    private boolean isEnd;

    public TimerIcon(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
        icon = new Image(Warfare.atlas.findRegion("monstersIcon"));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.WHITE;
        labelStyle.font = Warfare.font20;
        textLabel = new Label("00 : 00", labelStyle);
        setSize(icon.getWidth(), icon.getHeight());
//        setVisible(false);
        addActor(icon);
        addActor(textLabel);
//        setPosition(levelScreen.getWidth() - getWidth() - 32,
//                levelScreen.getHeight() - 128 - getHeight());

        setPosition(levelScreen.getWidth() + icon.getWidth() + 100, levelScreen.getHeight() - 300);
//        setPosition(levelScreen.getWidth() - 200, levelScreen.getHeight() - 300);
        levelScreen.addChild(this);
        textLabelX = (icon.getWidth() - textLabel.getWidth()) / 2;
        textLabelY = -textLabel.getHeight();
        textLabel.setPosition(textLabelX, textLabelY);
//        addAction(Actions.fadeOut(0));
    }

    public void update(float delta) {
        if (isStarted) {
            time -= delta;
            System.out.println("Timer = " + time);
            if (time >= 0) {
                if (time < 10) {
                    text = "0" + (int) (time);
                } else {
                    text = "" + (int) (time);
                }
                System.out.println("Text = " + text);
                textLabel.setText("00 : " + text);
            } else {
//                textLabel.setVisible(false);    // делаем цифры счетчика невидимыми
                isStarted = false;
                isEnd = true;
            }
        }
    }

    public void startAppearanceAction() {
//        addAction(Actions.moveBy(-icon.getWidth() - 100 - 200, 0, 2));
        addAction(Actions.moveBy(-icon.getWidth() - 100 - 200, 0, 1, new Interpolation.Elastic(5, 0.8f, 2, 0.4f)));
//        addAction(Actions.moveBy(-200, 0, 1, new Interpolation.Elastic(16, 1, 8, 0)));
    }

    public boolean isEnd() {
        return isEnd;
    }

    // удалим актера с уровня
    public void clear() {
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                clearActions();
                remove();
                return true;
            }
        };
        SequenceAction sequenceAction = new SequenceAction(
                Actions.fadeOut(1f),
                checkEndOfAction);
        addAction(sequenceAction);
    }

    public void stop() {
        isStop = true;
        isStarted = false;
        icon.clearActions();
//        addAction(Actions.fadeOut(1));
    }

    public void start() {
        setVisible(true);
        isStarted = true;
        time = TIME_TO_WAVE;
        startAppearanceAction();
//        addAction(Actions.fadeIn(1.6f));

    }


    public void startAction() {
        actionIsStarted = true;
        MoveToAction moveToActionOne = new MoveToAction();
        moveToActionOne.setPosition(iconPosX - 6, iconPosY - 6);
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
        moveToActionTwo.setPosition(iconPosX, iconPosY);
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
//        setVisible(false);
        isStarted = false;
        isStop = false;
        isEnd = false;
        actionIsStarted = false;
        clearActions();
        this.toFront();
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isStop() {
        return isStop;
    }
}

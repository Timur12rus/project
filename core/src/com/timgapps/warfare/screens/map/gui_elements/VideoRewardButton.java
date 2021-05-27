package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Warfare;

public class VideoRewardButton extends IconOnMap {
    private boolean isEndAction;
    private boolean isShown;

    public VideoRewardButton() {
        this.buttonIcon = new Image(Warfare.atlas.findRegion("videoBtn"));
        this.buttonIconDown = new Image(Warfare.atlas.findRegion("videoBtn_dwn"));
        buttonIconDown.setVisible(false);
        setSize();
        addClickListener();
        addActor(buttonIcon);
        addActor(buttonIconDown);
    }

    @Override
    protected void touchedDown() {
        if (isEndAction) {
            super.touchedDown();
        }
    }

    @Override
    protected void touchedUp() {
        if (isEndAction) {
            super.touchedUp();
        }
    }

    // метод показывает действие появление кнопки
    public void show() {
        isShown = true;     // значок показан на экране карты
        buttonIcon.addAction(Actions.fadeOut(0));
        buttonIcon.addAction(Actions.sizeTo(buttonIcon.getWidth() / 2, buttonIcon.getHeight() / 2));
        buttonIcon.addAction(Actions.moveBy(buttonIcon.getWidth() / 4, buttonIcon.getHeight() / 4));
        ParallelAction parallelAction = new ParallelAction(
                Actions.moveBy(-buttonIcon.getWidth() / 4, -buttonIcon.getHeight() / 4, 0.6f, Interpolation.swingOut),
                Actions.sizeTo(buttonIcon.getWidth(), buttonIcon.getHeight(), 0.6f, Interpolation.swingOut)
        );

        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                isEndAction = true;
                return true;
            }
        };

        SequenceAction sequenceAction = new SequenceAction(
                Actions.delay(2f),
                Actions.fadeIn(0),
                parallelAction,
                checkEndOfAction
        );
        buttonIcon.addAction(sequenceAction);
    }

    public boolean isEndAction() {
        return isEndAction;
    }

    public boolean isShown() {
        return isShown;
    }

    // метод сбрасывает действие кнопки
    public void resetAction() {
        isShown = false;
        isEndAction = false;
        buttonIcon.clearActions();
    }

    public void clearAction() {
        isEndAction = true;
//        buttonIcon.clearActions();
    }
}

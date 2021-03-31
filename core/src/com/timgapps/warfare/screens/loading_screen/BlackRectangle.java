package com.timgapps.warfare.screens.loading_screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.timgapps.warfare.screens.level.level_windows.ColorRectangle;

public class BlackRectangle extends ColorRectangle {
    private boolean isEndAction;

    public BlackRectangle(float x, float y, float width, float height, Color color) {
        super(x, y, width, height, color);
        setPosition(x, y);
        addAction(Actions.fadeOut(0));
    }

    public void starAction() {
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                isEndAction = true;
                return true;
            }
        };
        SequenceAction fadeInAction = new SequenceAction(Actions.fadeIn(1.5f),
                Actions.delay(0.2f),
                checkEndOfAction
        );
        addAction(fadeInAction);
    }

    public boolean isEndAction() {
        return isEndAction;
    }
}

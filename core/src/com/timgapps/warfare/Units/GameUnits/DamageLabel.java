package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Warfare;

public class DamageLabel extends Actor {
    private float x, y;

    public DamageLabel(LevelScreen levelScreen, float x, float y, int damage) {
        this.x = x;
        this.y = y;
        Label.LabelStyle greenLabelStyle = new Label.LabelStyle();
        greenLabelStyle.fontColor = Color.RED;
        greenLabelStyle.font = Warfare.font20;

        Label damageLabel = new Label("" + damage, greenLabelStyle);
        damageLabel.setPosition(x, y);
        SequenceAction ma = new SequenceAction(Actions.moveBy(0, 30, 0.2f),
                Actions.fadeOut(0.3f));
        damageLabel.addAction(ma);

        levelScreen.addChild(damageLabel);
    }
}

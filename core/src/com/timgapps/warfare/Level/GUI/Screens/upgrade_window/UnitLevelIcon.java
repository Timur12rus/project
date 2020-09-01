package com.timgapps.warfare.Level.GUI.Screens.upgrade_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.timgapps.warfare.Warfare;

public class UnitLevelIcon extends Group {

    private Label levelLabel;
    private Image levelImage;

    public UnitLevelIcon(int unitLevel) {
        levelImage = new Image(Warfare.atlas.findRegion("levelIcon"));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;

        levelLabel = new Label("" + unitLevel, labelStyle);

        levelLabel.setPosition((levelImage.getWidth() - levelLabel.getWidth()) / 2,
                (levelImage.getHeight() - levelLabel.getHeight()) / 2);

        addActor(levelImage);
        addActor(levelLabel);
    }

    public void setLevelValue(int levelValue) {
        levelLabel.setText("" + levelValue);
    }

    @Override
    public float getWidth() {
        return levelImage.getWidth();
    }

    @Override
    public float getHeight() {
        return levelImage.getHeight();
    }
}


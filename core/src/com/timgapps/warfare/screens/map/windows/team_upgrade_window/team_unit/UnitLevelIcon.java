package com.timgapps.warfare.screens.map.windows.team_upgrade_window.team_unit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.timgapps.warfare.Warfare;

public class UnitLevelIcon extends Group {
    private Label levelLabel;
    private Image levelImage;
    private boolean isReady;
    private boolean isActiveIcon;     // акативный значок

    public UnitLevelIcon(int unitLevel) {
        levelImage = new Image(Warfare.atlas.findRegion("levelIcon"));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;
        levelLabel = new Label("", labelStyle);
        levelLabel.setText("" + unitLevel);
        if (unitLevel < 10) {
            levelLabel.setWidth(13);
        } else {
            levelLabel.setWidth(25);
        }
        levelLabel.setPosition((levelImage.getWidth() - levelLabel.getWidth()) / 2,
                (levelImage.getHeight() - levelLabel.getHeight()) / 2);
        setSize(levelImage.getImageWidth(), levelImage.getImageHeight());
        addActor(levelImage);
        addActor(levelLabel);
    }

    public void setIsActiveIcon(boolean isActiveIcon) {
        this.isActiveIcon = isActiveIcon;
        if (isActiveIcon == false) {
            this.setColor(1, 1, 1, 0.7f);
            this.setY(this.getY() - 10);
        } else {
            this.setColor(1, 1, 1, 1);
            this.setY(this.getY() + 10);
        }
    }

    public boolean isActiveIcon() {
        return isActiveIcon;
    }

    public void setIsReady(boolean isReady) {
        this.isReady = isReady;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setLevelValue(int levelValue) {
//        if (levelValue >= 10) {
//            levelLabel.setX(levelLabel.getX() - 4);
//        }
        if (levelValue < 10) {
            levelLabel.setWidth(13);
        } else {
            levelLabel.setWidth(25);
        }
        levelLabel.setText("" + levelValue);
        levelLabel.setPosition((levelImage.getWidth() - levelLabel.getWidth()) / 2,
                (levelImage.getHeight() - levelLabel.getHeight()) / 2);
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


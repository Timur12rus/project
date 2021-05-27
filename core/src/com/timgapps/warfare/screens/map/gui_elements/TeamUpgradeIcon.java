package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Utils.StringHolder;
import com.timgapps.warfare.Warfare;


public class TeamUpgradeIcon extends IconOnMap {
    private Label teamLabel;

    public TeamUpgradeIcon() {
        buttonIcon = new Image(Warfare.atlas.findRegion("teamButton"));
        buttonIconDown = new Image(Warfare.atlas.findRegion("teamButtonDwn"));
        buttonIconDown.setVisible(false);
        setSize();
        addClickListener();
        roundCircle.setPosition(getWidth() - roundCircle.getWidth(), getHeight() - roundCircle.getHeight());

        Label.LabelStyle teamLabelStyle = new Label.LabelStyle();
        teamLabelStyle.fontColor = Color.WHITE;
        teamLabelStyle.font = Warfare.font30;
        teamLabel = new Label(Warfare.stringHolder.getString(StringHolder.TEAM), teamLabelStyle);
        teamLabel.setPosition((getWidth() - teamLabel.getWidth()) / 2, 14);
        addActor(buttonIcon);
        addActor(buttonIconDown);
        addActor(teamLabel);
    }
}

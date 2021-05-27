package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Utils.StringHolder;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Warfare;

public class GiftIcon extends IconOnMap {
    private GameManager gameManager;
    private Label giftsLabel;

    public GiftIcon(GameManager gameManager) {
        this.gameManager = gameManager;
        buttonIcon = new Image(Warfare.atlas.findRegion("rewardButton"));
        buttonIconDown = new Image(Warfare.atlas.findRegion("rewardButton_dwn"));
        buttonIconDown.setVisible(false);
        setSize();
        addClickListener();
        roundCircle.setPosition(getWidth() - roundCircle.getWidth(), getHeight() - roundCircle.getHeight());

        Label.LabelStyle teamLabelStyle = new Label.LabelStyle();
        teamLabelStyle.fontColor = Color.WHITE;
        teamLabelStyle.font = Warfare.font30;
        giftsLabel = new Label(Warfare.stringHolder.getString(StringHolder.GIFTS), teamLabelStyle);
        giftsLabel.setPosition((getWidth() - giftsLabel.getWidth()) / 2,
                14);

        /** добавим неактивный значок и активный **/
        addActor(buttonIcon);
        addActor(buttonIconDown);
        addActor(giftsLabel);
    }
}

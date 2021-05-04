package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.timgapps.warfare.Warfare;

class RoundCircle extends Group {
    private Label label;    // надпись (кол-во доступных наград)
    private Image bg;

    public RoundCircle() {
        Label.LabelStyle countLabelStyle = new Label.LabelStyle();
        countLabelStyle.fontColor = Color.WHITE;
        countLabelStyle.font = Warfare.font18;
        label = new Label("!", countLabelStyle);
        bg = new Image(Warfare.atlas.findRegion("redCircle"));
        setSize(bg.getWidth(), bg.getHeight());
        label.setPosition((getWidth() - label.getWidth()) / 2, (getHeight() - label.getHeight()) / 2);
        addActor(bg);
        addActor(label);
    }
}

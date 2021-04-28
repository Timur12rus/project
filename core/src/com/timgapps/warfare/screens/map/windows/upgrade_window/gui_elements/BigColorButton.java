package com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Warfare;

public class BigColorButton extends ColorButton {
    public BigColorButton(String text, int buttonColor) {
        super(text, buttonColor);
    }

    @Override
    protected void createButtonImage() {
        switch (buttonColor) {
            case GREEN_BUTTON:
                bg = new Image(Warfare.atlas.findRegion("button_ok_big"));
                bgDown = new Image(Warfare.atlas.findRegion("button_ok_big_dwn"));
                break;
            case YELLOW_BUTTON:
                bg = new Image(Warfare.atlas.findRegion("yellowButton"));
                bgDown = new Image(Warfare.atlas.findRegion("yellowButton_dwn"));
                break;
            case RED_BUTTON:

                break;
        }
    }
}

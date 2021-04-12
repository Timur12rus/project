package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.scenes.scene2d.Group;

public class IconOnMap extends Group {
    protected RoundCircle roundCircle;

    public IconOnMap() {
        roundCircle = new RoundCircle();
//        roundCircle.setPosition(getWidth() - roundCircle.getWidth(), getHeight() - roundCircle.getHeight());
        roundCircle.setVisible(false);
        addActor(roundCircle);
    }

    public void showRoundCircle() {
        roundCircle.toFront();
        roundCircle.setVisible(true);
    }

    public void hideRoundCircle() {
        roundCircle.setVisible(false);
    }

    public boolean roundCircleIsVisible() {
        return roundCircle.isVisible();
    }
}

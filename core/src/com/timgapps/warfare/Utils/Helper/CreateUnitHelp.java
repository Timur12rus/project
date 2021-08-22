package com.timgapps.warfare.Utils.Helper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;

public class CreateUnitHelp {
    private LevelScreen levelScreen;
    private Finger finger;

    public CreateUnitHelp(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
        float x = levelScreen.getUnitButtons().getX() + (levelScreen.getUnitButtons().getWidth() - Finger.WIDTH) / 2 + 48 + 36;
        float y = levelScreen.getUnitButtons().getY() + levelScreen.getUnitButtons().getHeight() + 16 + Finger.HEIGHT;
        finger = new Finger(levelScreen, x, y,
                Finger.DOWN, new TextureRegion(Warfare.atlas.findRegion("fingerUpRight")));
        levelScreen.getUnitButtons().getUnitButton(0).addActor(finger);
//        finger.setPosition(x, y);
//        levelScreen.addOverlayChild(finger);
    }

    public void hide() {
        finger.hide();
    }

    public void show() {
        finger.show();
        finger.setVisible(true);
    }

    public void clear() {
        finger.clear();
        finger.remove();
    }
}

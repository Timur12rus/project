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
        levelScreen.getUnitButtons().getImageUnitButton(0).addActor(finger);
        float posX = 84;
//        float posX =  (levelScreen.getUnitButtons().getImageUnitButton(0).getWidth() - finger.getWidth()) / 2;
        float posY = (levelScreen.getUnitButtons().getImageUnitButton(0).getHeight() + 96);

        finger.setPosition(posX,
                posY);

//        finger.setPosition(levelScreen.getUnitButtons().getImageUnitButton(0).getWidth(),
//                levelScreen.getUnitButtons().getImageUnitButton(0).getHeight());

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

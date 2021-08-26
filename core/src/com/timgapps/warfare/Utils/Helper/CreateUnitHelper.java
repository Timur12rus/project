package com.timgapps.warfare.Utils.Helper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.screens.map.windows.team_window.team_unit.CreateUnitButton;
import com.timgapps.warfare.screens.map.windows.team_window.team_unit.UnitImageButton;

public class CreateUnitHelper {
    private LevelScreen levelScreen;
    private Finger finger;
    private CreateUnitButton createUnitButton;
//    private UnitImageButton unitImageButton;

    public CreateUnitHelper(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
//        float x = levelScreen.getUnitButtons().getX() + (levelScreen.getUnitButtons().getWidth() - Finger.WIDTH) / 2 + 48 + 36;
//        float y = levelScreen.getUnitButtons().getY() + levelScreen.getUnitButtons().getHeight() + 16 + Finger.HEIGHT;
        createUnitButton = (CreateUnitButton) levelScreen.getUnitButtons().getImageUnitButton(0);
        finger = new Finger(levelScreen, Finger.DOWN, new TextureRegion(Warfare.atlas.findRegion("fingerUpRight")));
        createUnitButton.addActor(finger);
        createUnitButton.setCreateUnitHelper(this);
        float posX = 84;
        float posY = (createUnitButton.getHeight() + 96);

        finger.setPosition(posX, posY);
        finger.hide();
    }

    public void hideFinger() {
        finger.setIsShown(false);
        finger.hide();
    }

    // показывает указатель (палец)
    public void showFinger() {
        finger.setIsShown(true);
        finger.show();
    }

    public void clear() {
        finger.clear();
        finger.remove();
    }
}

package com.timgapps.warfare.screens.level.gui_elements;

import com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock.FireRockShoot;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.screens.level.StoneButton;

public class FireBoosterButton extends StoneButton {
    public FireBoosterButton(LevelScreen levelScreen, PlayerUnitData data) {
        super(levelScreen, data);
//        greenTarget.scaleBy(1);
//        redTarget.scaleBy(1);
        greenTarget.scaleBy(1, 0.5f);
        redTarget.scaleBy(1, 0.5f);
        greenTargetWidth = greenTarget.getWidth() * 2;
        greenTargetHeight = greenTarget.getHeight();
        unitLevelIcon.remove();
        yMax -= greenTarget.getHeight() / 2;
//        yMin += greenTarget.getHeight();
    }

    @Override
    public void redraw() {
        super.redraw();
        setInActive();
        percentage = 0;
        isReadyUnitButton = false;
    }

    @Override
    public void setPosX(float posX) {
        super.setPosX(posX);
        xMax -= greenTargetWidth / 2;
    }

    @Override
    protected void throwBullet(LevelScreen levelScreen, float x, float y, float damage, float health) {
        new FireRockShoot(levelScreen, x, y + DELTA_Y_TARGET_ICON);
    }
}

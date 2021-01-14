package com.timgapps.warfare.screens.level.gui_elements;

import com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock.FireRockShoot;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.screens.level.StoneButton;

public class FireBoosterButton extends StoneButton {
    public FireBoosterButton(LevelScreen levelScreen, PlayerUnitData data) {
        super(levelScreen, data);
        unitLevelIcon.remove();
    }

    @Override
    protected void throwBullet(LevelScreen levelScreen, float x, float y, float damage, float health) {
        new FireRockShoot(levelScreen);
    }
}

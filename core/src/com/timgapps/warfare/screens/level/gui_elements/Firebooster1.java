package com.timgapps.warfare.screens.level.gui_elements;

import com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock.FireRockShoot1;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.screens.map.windows.team_upgrade_window.team_unit.CreateUnitButton;

public class Firebooster1 extends CreateUnitButton {
    private LevelScreen levelScreen;

    public Firebooster1(LevelScreen levelScreen, PlayerUnitData playerUnitData) {
        super(levelScreen, playerUnitData);
        this.levelScreen = levelScreen;
        unitLevelIcon.remove();
    }


    @Override
    public void buttonClicked(float x, float y) {
        if (isReadyUnitButton) {
            isReadyUnitButton = false;
            new FireRockShoot1(levelScreen);
            setInActive();
        }
    }
}
package com.timgapps.warfare.screens.level.gui_elements;

import com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock.FireRockShoot;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.screens.map.windows.team_upgrade_window.team_unit.CreateUnitButton;

public class Firebooster extends CreateUnitButton {
    private LevelScreen levelScreen;

    public Firebooster(LevelScreen levelScreen, PlayerUnitData playerUnitData) {
        super(levelScreen, playerUnitData);
        this.levelScreen = levelScreen;
    }

    @Override
    public void buttonClicked() {
//        super.buttonClicked();
        new FireRockShoot(levelScreen);
    }
}

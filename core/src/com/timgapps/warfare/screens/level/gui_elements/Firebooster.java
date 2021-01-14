package com.timgapps.warfare.screens.level.gui_elements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock.FireRockShoot;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.screens.map.windows.team_upgrade_window.team_unit.CreateUnitButton;

public class Firebooster extends CreateUnitButton {
    private LevelScreen levelScreen;

    public Firebooster(LevelScreen levelScreen, PlayerUnitData playerUnitData) {
        super(levelScreen, playerUnitData);
        this.levelScreen = levelScreen;
        unitLevelIcon.remove();
    }


    @Override
    public void buttonClicked(float x, float y) {
        if (isReadyUnitButton) {
            isReadyUnitButton = false;
            new FireRockShoot(levelScreen);
            setInActive();
        }
    }
}

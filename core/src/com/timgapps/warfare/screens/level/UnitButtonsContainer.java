package com.timgapps.warfare.screens.level;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.timgapps.warfare.screens.map.windows.team_window.team_unit.UnitImageButton;

public class UnitButtonsContainer extends Group {
    private UnitImageButton unitImageButton;

    public UnitButtonsContainer(UnitImageButton unitImageButton) {
        this.unitImageButton = unitImageButton;
        addActor(unitImageButton);
    }
}

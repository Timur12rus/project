package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.timgapps.warfare.Level.GUI.team_unit.UnitImageButton;

public class UnitContainer extends Group {
    private UnitImageButton unitImageButton;

    public UnitContainer(UnitImageButton unitImageButton) {
        this.unitImageButton = unitImageButton;
        addActor(unitImageButton);
    }
}

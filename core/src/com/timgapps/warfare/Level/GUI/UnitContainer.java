package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.timgapps.warfare.Level.GUI.team_unit.UnitButton;

public class UnitContainer extends Group {

    private com.timgapps.warfare.Level.GUI.team_unit.UnitButton unitButton;

    public UnitContainer(UnitButton unitButton) {
        this.unitButton = unitButton;
        addActor(unitButton);
    }
}

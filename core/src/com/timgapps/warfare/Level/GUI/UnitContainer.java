package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class UnitContainer extends Group {

    private UnitButton unitButton;

    public UnitContainer(UnitButton unitButton) {
        this.unitButton = unitButton;
        addActor(unitButton);
    }
}

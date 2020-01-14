package com.timgapps.warfare.Level.GUI.Screens;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.timgapps.warfare.Warfare;


public class TeamEntity extends Group {

    public static final int GNOME = 1;
    public static final int ARCHER = 2;
    public static final int STONE = 3;

    private int unitType;
    private ImageButton unitImage;
    public float width, height;

    public TeamEntity(int unitType) {
        this.unitType = unitType;
        switch (unitType) {
            case GNOME:
                unitImage = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("gnomeActive")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("gnomeInactive")));
                break;
            case ARCHER:
                unitImage = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("archer1Active")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("archer1Inactive")));
                break;
        }

        width = unitImage.getWidth();
        height = unitImage.getHeight();
        addActor(unitImage);
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
}
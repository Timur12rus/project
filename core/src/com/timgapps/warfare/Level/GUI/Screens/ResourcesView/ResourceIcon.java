package com.timgapps.warfare.Level.GUI.Screens.ResourcesView;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.timgapps.warfare.Warfare;

import javafx.scene.Group;

public class ResourceIcon extends Group {
    private Image image;
    private Label name;
    private Label count;

    public static final int FOOD = 1;
    public static final int IRON = 2;
    public static final int WOOD = 3;

    private int resourceType;

    public ResourceIcon(int resourceType) {
        this.resourceType = resourceType;
        switch (resourceType) {
            case 1:
                this.image = new Image(Warfare.atlas.findRegion("food_icon"));
                break;
            case 2:
                this.image = new Image(Warfare.atlas.findRegion("iron_icon"));
                break;
            case 3:
                this.image = new Image(Warfare.atlas.findRegion("wood_icon"));
                break;
        }

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;


    }
}

package com.timgapps.warfare.screens.level.background;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;

public class BackgroundBuilder {
    private static final int MODEL_1 = 1;
    private static final int MODEL_2 = 2;
    private static final int MODEL_3 = 3;
    private Image backgroundGrass;          // трава (горизонт) на фоне
    private Image backgroundBack;           // свмый задний фон либо с деревьями либо с горами
    private Image road;                     // изображение дороги
    private LevelScreen levelScreen;

    public BackgroundBuilder() {
    }

    public Group build(int number) {
        int model;
        if (number % 2 == 0) {
            model = MODEL_1;
        } else {
            model = MODEL_2;
        }
        System.out.println("BackGround model = " + model);
        Group backgroundGroup = new Group();
        switch (model) {
            case MODEL_1:
                backgroundBack = new Image(Warfare.atlas.findRegion("back_forest_image"));
                break;
            case MODEL_2:
                backgroundBack = new Image(Warfare.atlas.findRegion("back_mount_image"));
                break;
        }
        backgroundGrass = new Image(Warfare.atlas.findRegion("land_grass_image"));
        road = new Image(Warfare.atlas.findRegion("road_image"));
        backgroundBack.setPosition(0, 410);
        road.setPosition(0, 0);
        backgroundGrass.setPosition(0, 365);
        backgroundGroup.addActor(backgroundBack);
        backgroundGroup.addActor(backgroundGrass);
        backgroundGroup.addActor(road);
        backgroundGroup.setSize(1280, 720);
        return backgroundGroup;
    }
}

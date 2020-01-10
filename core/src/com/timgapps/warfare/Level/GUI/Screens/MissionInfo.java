package com.timgapps.warfare.Level.GUI.Screens;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.timgapps.warfare.Warfare;


public class MissionInfo extends Group {
    public static final int ON_START = 1;
    public static final int ON_QUIT = 2;

    // объявим заголовок и кнопку

    private ImageButton startButton;
    private Image background;

    public MissionInfo() {
        background = new Image(Warfare.atlas.findRegion("mission_start_bg"));
        background.setX((Warfare.V_WIDTH - background.getWidth()) / 2); // устанавливаем позицию заголовка
        background.setY(((Warfare.V_HEIGHT - background.getWidth())) / 2);

        addActor(background);

        startButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("button_ok")),
                new TextureRegionDrawable(Warfare.atlas.findRegion("button_ok_dwn")));

        startButton.setX((background.getWidth() - startButton.getWidth()) / 2);
        startButton.setY(background.getY() + 30);
        addActor(startButton);

    }
}

package com.timgapps.warfare.Level.LevelScreens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.Level.GUI.Screens.upgrade_window.ColorButton;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class PauseScreen extends Group {

    private ColorButton mapButton;
    private ColorButton continueButton;

    private Label pauseLabel;
    private Label missionLabel;

    public static final int ON_MAP = 1;
    public static final int ON_CONTINUE = 2;

    public PauseScreen(Level level) {
        debug();

        int levelNumber = level.getLevelNumber();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.GOLD;
        labelStyle.font = Warfare.font40;

        Label.LabelStyle pauseLabelStyle = new Label.LabelStyle();
        pauseLabelStyle.fontColor = Color.WHITE;
        pauseLabelStyle.font = Warfare.font20;

        missionLabel = new Label("Mission " + levelNumber, labelStyle);
        pauseLabel = new Label("Pause", pauseLabelStyle);

        mapButton = new ColorButton("Map", ColorButton.GREEN_BUTTON);
        continueButton = new ColorButton("Continue", ColorButton.GREEN_BUTTON);

        setSize(mapButton.getWidth() * 2 + 32, missionLabel.getHeight() + pauseLabel.getHeight() + 196);

        pauseLabel.setPosition(getWidth() / 2 - pauseLabel.getWidth() / 2, getHeight() - pauseLabel.getHeight());
        missionLabel.setPosition(getWidth() / 2 - missionLabel.getWidth() / 2, getHeight() / 2 + 32);

        mapButton.setPosition(getWidth() / 2 - mapButton.getWidth() - 16, 0);
        continueButton.setPosition(getWidth() / 2 + 16, 0);

        // присваиваем обработчик нажатия, который отправляет сообщение с кодом
        mapButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new MessageEvent(ON_MAP));
            }
        });

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new MessageEvent(ON_CONTINUE));
            }
        });

        addActor(missionLabel);
        addActor(pauseLabel);
        addActor(mapButton);
        addActor(continueButton);
    }
}

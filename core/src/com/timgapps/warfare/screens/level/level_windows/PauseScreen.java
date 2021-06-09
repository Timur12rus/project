package com.timgapps.warfare.screens.level.level_windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.Utils.StringHolder;
import com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements.BigColorButton;
import com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements.ColorButton;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Warfare;

public class PauseScreen extends Group {

    private ColorButton mapButton;
    private ColorButton continueButton;

    private Label pauseLabel;
    private Label missionLabel;

    public static final int ON_MAP = 1;
    public static final int ON_CONTINUE = 2;
    private LevelScreen levelScreen;

    public PauseScreen(LevelScreen levelScreen) {
        debug();
        this.levelScreen = levelScreen;
//        final int levelNumber = levelScreen.getLevelNumber();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.GOLD;
        labelStyle.font = Warfare.font40;

        Label.LabelStyle pauseLabelStyle = new Label.LabelStyle();
        pauseLabelStyle.fontColor = Color.WHITE;
        pauseLabelStyle.font = Warfare.font30;

        missionLabel = new Label("", labelStyle);
        missionLabel = new Label("Mission 10", labelStyle);
        pauseLabel = new Label(Warfare.stringHolder.getString(StringHolder.PAUSE), pauseLabelStyle);

        mapButton = new BigColorButton(Warfare.stringHolder.getString(StringHolder.MAP), ColorButton.GREEN_BUTTON);
//        mapButton = new ColorButton(Warfare.stringHolder.getString(StringHolder.MAP), ColorButton.GREEN_BUTTON);
        continueButton = new BigColorButton(Warfare.stringHolder.getString(StringHolder.CONTINUE), ColorButton.GREEN_BUTTON);
//        continueButton = new ColorButton(Warfare.stringHolder.getString(StringHolder.CONTINUE), ColorButton.GREEN_BUTTON);
//        continueButton.setButtonWidth(1.4f);

        setSize(mapButton.getWidth() * 2 + 32, missionLabel.getHeight() + pauseLabel.getHeight() + 196);

        pauseLabel.setPosition(getWidth() / 2 - pauseLabel.getWidth() / 2, getHeight() - pauseLabel.getHeight());
        missionLabel.setPosition(getWidth() / 2 - missionLabel.getWidth() / 2, getHeight() / 2 + 32);

        mapButton.setPosition(getWidth() / 2 - mapButton.getWidth() - 16, 0);
        continueButton.setPosition(getWidth() / 2 + 16, 0);

        // присваиваем обработчик нажатия, который отправляет сообщение с кодом
        mapButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideScreen();
                Warfare.media.playSound("clickButton.ogg");
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

    public void redraw() {
        missionLabel.setText(Warfare.stringHolder.getString(StringHolder.MISSION) + " " + levelScreen.getLevelNumber());
        missionLabel.setPosition(getWidth() / 2 - missionLabel.getWidth() / 2, getHeight() / 2 + 32);
    }

    private void hideScreen() {
        levelScreen.resumeLevel();
    }


}

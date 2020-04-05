package com.timgapps.warfare.Level.LevelScreens;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow.ColorButton;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class GameOverScreen extends Group {
    public static final int ON_MAP = 1;
    public static final int ON_RETRY = 2;

    private int levelNumber;

    private Label defeatLabel;
    private Label missionLabel;
    private Label retryLabel;
    private Table buttonsTable;

    private ColorButton mapButton, retryButton;

    public GameOverScreen(Level level) {
        levelNumber = level.getLevelNumber();

        Label.LabelStyle defeatLabelStyle = new Label.LabelStyle();
        defeatLabelStyle.fontColor = Color.RED;
        defeatLabelStyle.font = Warfare.font40;

        Label.LabelStyle missionLabelStyle = new Label.LabelStyle();
        missionLabelStyle.fontColor = Color.GOLD;
        missionLabelStyle.font = Warfare.font40;

        Label.LabelStyle retryLabelStyle = new Label.LabelStyle();
        retryLabelStyle.fontColor = Color.DARK_GRAY;
        retryLabelStyle.font = Warfare.font20;

        defeatLabel = new Label("Defeat!", defeatLabelStyle);
        missionLabel = new Label("Mission " + levelNumber, missionLabelStyle);
        retryLabel = new Label("Retry:", retryLabelStyle);

        mapButton = new ColorButton("Map", ColorButton.GREEN_BUTTON);
        retryButton = new ColorButton("2 Steps", ColorButton.GREEN_BUTTON);

        buttonsTable = new Table().debug();

        buttonsTable.add(retryLabel).colspan(2).align(Align.right).padRight((mapButton.getWidth() - retryLabel.getWidth()) / 2);
        buttonsTable.row();
        buttonsTable.add(mapButton);
        buttonsTable.add(retryButton).padLeft(32);
        buttonsTable.setWidth(mapButton.getWidth() + retryButton.getWidth() + 32);

        defeatLabel.setPosition(-defeatLabel.getWidth() / 2, 0);

        missionLabel.setPosition(-missionLabel.getWidth() / 2, -64);
        buttonsTable.setPosition(-buttonsTable.getWidth() / 2, -216);

        addActor(defeatLabel);
        addActor(missionLabel);
        addActor(buttonsTable);
    }
}

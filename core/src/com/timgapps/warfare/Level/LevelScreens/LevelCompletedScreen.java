package com.timgapps.warfare.Level.LevelScreens;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow.ColorButton;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class LevelCompletedScreen extends Group {
    public static final int ON_DONE = 1;

    private Label victoryLabel;
    private Label towerSavedLevel;
    private Label missionLabel;
    private Label rewardLabel;

    private Stars stars;
    private int levelNumber;

    private ColorButton okButton;

    public LevelCompletedScreen(Level level) {
        levelNumber = level.getLevelNumber();

        stars = new Stars();
        stars.setPosition(0, 0);
        addActor(stars);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.GOLD;
        labelStyle.font = Warfare.font40;

        Label.LabelStyle victoryLabelStyle = new Label.LabelStyle();
        victoryLabelStyle.fontColor = Color.WHITE;
        victoryLabelStyle.font = Warfare.font20;

        Label.LabelStyle towerSavedLevelStyle = new Label.LabelStyle();
        towerSavedLevelStyle.fontColor = Color.GRAY;
        towerSavedLevelStyle.font = Warfare.font20;

        Label.LabelStyle rewardLabelStyle = new Label.LabelStyle();
        rewardLabelStyle.fontColor = Color.GRAY;
        rewardLabelStyle.font = Warfare.font20;

        missionLabel = new Label("Mission " + levelNumber, labelStyle);
        victoryLabel = new Label("Victory!", victoryLabelStyle);
        towerSavedLevel = new Label("Tower saved of: " + "100%", towerSavedLevelStyle);
        rewardLabel = new Label("Reward:", rewardLabelStyle);

        missionLabel.setPosition(stars.getX() + (stars.getWidth() - missionLabel.getWidth()) / 2, getY() - 36 - missionLabel.getHeight());
        victoryLabel.setPosition(stars.getX() + (stars.getWidth() - victoryLabel.getWidth()) / 2, stars.getY() + stars.getHeight() + 16);
        towerSavedLevel.setPosition(stars.getX() + (stars.getWidth() - towerSavedLevel.getWidth()) / 2, stars.getY() - towerSavedLevel.getHeight() - 6);
        rewardLabel.setPosition(stars.getX() + (stars.getWidth() - rewardLabel.getWidth()) / 2, missionLabel.getY() - rewardLabel.getHeight() - 48);

        okButton = new ColorButton("OK", ColorButton.GREEN_BUTTON);

        setWidth(towerSavedLevel.getWidth());

        addActor(missionLabel);
        addActor(victoryLabel);
        addActor(towerSavedLevel);
//        addActor(rewardLabel);
        RewardTable rewardTable = new RewardTable(100, 10);
        rewardTable.setPosition(stars.getX() + (stars.getWidth() - rewardTable.getWidth()) / 2, missionLabel.getY() - 56);
        okButton.setPosition(stars.getX()  + (stars.getWidth() - okButton.getWidth()) / 2,
                rewardTable.getY() - okButton.getHeight() - 88);
        addActor(rewardTable);


        addActor(okButton);
    }

    public void start() {
        stars.startStarsActions();
    }
}

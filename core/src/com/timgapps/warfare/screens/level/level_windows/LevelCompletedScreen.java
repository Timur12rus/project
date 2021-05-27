package com.timgapps.warfare.screens.level.level_windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.Utils.StringHolder;
import com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements.ColorButton;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Warfare;

public class LevelCompletedScreen extends Group {
    public static final int ON_OK = 1;

    private Label victoryLabel;
    private Label towerSavedLabel;
    private Label missionLabel;
    private Label rewardLabel;

    private Stars stars;
    private int levelNumber;

    private ColorButton okButton;
    private RewardTable rewardTable;
    private int rewardCoinForLevel, rewardScoreForLevel;
    private int starsCount = 0;
    private boolean isStarted;   // флаг, запущен ли экран завершения уровня
    private LevelScreen levelScreen;

    public LevelCompletedScreen(LevelScreen levelScreen, int rewardCoinForLevel, int rewardScoreForLevel) {
        this.levelScreen = levelScreen;
        levelNumber = levelScreen.getLevelNumber();
        this.rewardCoinForLevel = rewardCoinForLevel;
        this.rewardScoreForLevel = rewardScoreForLevel;

//        stars = new Stars(40, level.getSiegeTower().getFullHealth());
        stars = new Stars();
        stars.setPosition(0, 0);
        addActor(stars);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.GOLD;
        labelStyle.font = Warfare.font40;

        Label.LabelStyle victoryLabelStyle = new Label.LabelStyle();
        victoryLabelStyle.fontColor = Color.WHITE;
        victoryLabelStyle.font = Warfare.font30;

        Label.LabelStyle towerSavedLevelStyle = new Label.LabelStyle();
        towerSavedLevelStyle.fontColor = Color.GRAY;
        towerSavedLevelStyle.font = Warfare.font30;

        Label.LabelStyle rewardLabelStyle = new Label.LabelStyle();
        rewardLabelStyle.fontColor = Color.GRAY;
        rewardLabelStyle.font = Warfare.font30;

        missionLabel = new Label(Warfare.stringHolder.getString(StringHolder.MISSION) + " " + levelNumber, labelStyle);
        victoryLabel = new Label(Warfare.stringHolder.getString(StringHolder.VICTORY) + "!", victoryLabelStyle);
        towerSavedLabel = new Label(Warfare.stringHolder.getString(StringHolder.TOWER_SAVED) + ": ", towerSavedLevelStyle);
        rewardLabel = new Label(Warfare.stringHolder.getString(StringHolder.REWARD) + ": ", rewardLabelStyle);

        missionLabel.setPosition(stars.getX() + (stars.getWidth() - missionLabel.getWidth()) / 2, getY() - 36 - missionLabel.getHeight());
        victoryLabel.setPosition(stars.getX() + (stars.getWidth() - victoryLabel.getWidth()) / 2, stars.getY() + stars.getHeight() + 16);
        towerSavedLabel.setPosition(stars.getX() + (stars.getWidth() - towerSavedLabel.getWidth()) / 2, stars.getY() - towerSavedLabel.getHeight() - 6);
        rewardLabel.setPosition(stars.getX() + (stars.getWidth() - rewardLabel.getWidth()) / 2, missionLabel.getY() - rewardLabel.getHeight() - 48);

        okButton = new ColorButton("OK", ColorButton.GREEN_BUTTON);

        setWidth(towerSavedLabel.getWidth());

        addActor(missionLabel);
        addActor(victoryLabel);
        addActor(towerSavedLabel);
//        addActor(rewardLabel);

        /** создаем таблицу со значениями награды (кол-во монет и очков) **/
        rewardTable = new RewardTable(rewardCoinForLevel, rewardScoreForLevel);

        rewardTable.setPosition(stars.getX() + (stars.getWidth() - rewardTable.getWidth()) / 2, missionLabel.getY() - 56);
        okButton.setPosition(stars.getX() + (stars.getWidth() - okButton.getWidth()) / 2,
                rewardTable.getY() - okButton.getHeight() - 88);

        okButton.setVisible(false);
        rewardTable.setVisible(false);

        addActor(rewardTable);

        addActor(okButton);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new MessageEvent(ON_OK));
//                if (CrazyCatapult.soundEnabled)
//                    CrazyCatapult.media.playSound("click.ogg");
            }
        });
        //TODO Сделать слушатель на кнопку okButton, для выхода на экарна "КАРТА"
    }

    public void redrawTowerSavedLabel(int percentage) {
        towerSavedLabel.setText(towerSavedLabel.getText() + "" + percentage + "%");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (levelScreen.getState() != LevelScreen.PAUSED) {
            if (stars.getIsEndActions() && !rewardTable.isVisible()) {
                rewardTable.setVisible(true);
                okButton.setVisible(true);
            }
        }
    }

    /**
     * метод для запуска action'a для звезд
     **/
    public void start(int starsCount) {
        levelScreen.setState(LevelScreen.LEVEL_COMPLETED);
        isStarted = true;
        stars.startStarsActions(starsCount);
    }

    public boolean isStarted() {        // запущено ли действие для звёзд (движение)
        return isStarted;
    }
}

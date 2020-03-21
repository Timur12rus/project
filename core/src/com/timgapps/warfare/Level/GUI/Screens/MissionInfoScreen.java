package com.timgapps.warfare.Level.GUI.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow.ColorButton;
import com.timgapps.warfare.Level.LevelMap.LevelIconData;
import com.timgapps.warfare.Level.LevelScreens.RewardTable;
import com.timgapps.warfare.Warfare;


public class MissionInfoScreen extends Group {
    public static final int ON_START = 1;
    public static final int ON_RESUME = 2;

    // объявим заголовок и кнопку

    private ImageButton closeButton;
    //    private ImageButton startButton, closeButton;
    private ColorButton startButton;
    private Image background;

    public Label missionTitle; // отображаем текст заголовка
    public Label difficulty; // отображаем текст для уровня сложности
    public Label rewardText; // отображаем текст для вознаграждения
    public Label coinsCountText; // отображаем текст для вознаграждения
    public Label scoreCountText; // отображаем текст для вознаграждения

    private int id, coinsCount, scoreCount;
    private String levelOfDifficulty;

    private Image coinImage;

    private RewardTable rewardTable;

    public MissionInfoScreen() {
        background = new Image(Warfare.atlas.findRegion("mission_start_bg"));
        background.setX((Warfare.V_WIDTH - background.getWidth()) / 2); // устанавливаем позицию заголовка
        background.setY(((Warfare.V_HEIGHT / 2 - background.getHeight() / 2)));

        addActor(background);

        initializeLabels();

        rewardTable = new RewardTable(100, 10);
        rewardTable.debug();

        addActor(rewardTable);
        rewardTable.setPosition(background.getX() + (background.getWidth() - rewardTable.getWidth()) / 2,
                difficulty.getY() - rewardTable.getHeight() + 24);

        startButton = new ColorButton("start", ColorButton.GREEN_BUTTON);

        startButton.setX((background.getX() + (background.getWidth() - startButton.getWidth()) / 2));
        startButton.setY(background.getY() + 42);
        addActor(startButton);

        closeButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("button_close")),
                new TextureRegionDrawable(Warfare.atlas.findRegion("button_close_dwn")));

        closeButton.setX(background.getX() + background.getWidth() - closeButton.getWidth() - 28);
        closeButton.setY(background.getY() + background.getHeight() - closeButton.getHeight() - 12);
        addActor(closeButton);

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new MessageEvent(ON_RESUME));
            }
        });

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new MessageEvent(ON_START));
            }
        });
    }

    public void setData(LevelIconData data) {
        this.id = data.getId();
        this.coinsCount = data.getCoinsCount();
        this.scoreCount = data.getScoreCount();
        this.levelOfDifficulty = data.getLevelOfDifficulty();

        updateData();
    }

    /** метод получает кол-во монет (награду за уровень) **/
    public int getRewardCoinsForLevel() {
        return coinsCount;
    }

    /** метод получает кол-во очков (награду за уровень) **/
    public int getRewardScoreForLevel() {
        return scoreCount;
    }

    private void updateData() {
        missionTitle.setText("Mission " + id);
        difficulty.setText(levelOfDifficulty);
        rewardTable.setCoinsCount(coinsCount);
//        coinsCountText.setText("" + coinsCount);
    }

    /**
     * метод для отображения экрана MissionInfoScreen
     **/
    public void show() {
        this.setVisible(true);
    }

    /**
     * метод для скрытия экрана MissionInfoScreen
     **/
    public void hide() {
//        levelOfDifficulty = " ";
        this.setVisible(false);
    }

    private void initializeLabels() {

        Label.LabelStyle missionTitleLabelStyle = new Label.LabelStyle();

        missionTitleLabelStyle.fontColor = Color.DARK_GRAY;
        missionTitleLabelStyle.font = Warfare.font40;
        missionTitle = new Label("Mission " + id, missionTitleLabelStyle);
        missionTitle.setAlignment(Align.center);
        missionTitle.setPosition(background.getX() + background.getWidth() / 2 - missionTitle.getWidth() / 2,
                background.getY() + background.getHeight() - missionTitle.getHeight() - 8);
        addActor(missionTitle);

        Label.LabelStyle difficultlyLabelStyle = new Label.LabelStyle();
        difficultlyLabelStyle.fontColor = Color.FOREST;
        difficultlyLabelStyle.font = Warfare.font20;
        difficulty = new Label("" + levelOfDifficulty, difficultlyLabelStyle);

        difficulty.setPosition(background.getX() + (background.getWidth() - difficulty.getWidth()) / 2, missionTitle.getY() - difficulty.getHeight() - 16);
        addActor(difficulty);
    }
}

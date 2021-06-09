package com.timgapps.warfare.screens.map.windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.boontaran.MessageEvent;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Utils.StringHolder;
import com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements.ColorButton;
import com.timgapps.warfare.screens.map.win_creator.ConstructedWindow;
import com.timgapps.warfare.screens.map.LevelIconData;
import com.timgapps.warfare.screens.level.level_windows.RewardTable;
import com.timgapps.warfare.Warfare;

public class MissionInfoWindow extends Group {
    public static final int ON_START = 1;
    public static final int ON_RESUME = 2;

    // объявим заголовок и кнопку
    private ImageButton closeButton;
    private ColorButton startButton;

    private ConstructedWindow constructedWindow;

    public Label missionTitle; // отображаем текст заголовка
    public Label difficulty; // отображаем текст для уровня сложности
    public Label rewardText; // отображаем текст для вознаграждения
    public Label coinsCountText; // отображаем текст для вознаграждения
    public Label scoreCountText; // отображаем текст для вознаграждения

    private int id, coinsCount, scoreCount;
    private String levelOfDifficulty;
    private Image coinImage;
    private RewardTable rewardTable;
    Label.LabelStyle difficultlyLabelStyle;

    public MissionInfoWindow(StageGame stageGame) {
        constructedWindow = new ConstructedWindow(610, 350, "Mission");
        constructedWindow.setX((stageGame.getWidth() - constructedWindow.getWidth()) / 2); // устанавливаем позицию заголовка
        constructedWindow.setY(((stageGame.getHeight() - constructedWindow.getHeight()) / 2));
        addActor(constructedWindow);

        // создадим надписи "миссия" и "уровень сложности"
        initializeLabels();

        rewardTable = new RewardTable(100, 10);
        rewardTable.debug();
        addActor(rewardTable);
        rewardTable.setPosition(constructedWindow.getX() + (constructedWindow.getWidth() - rewardTable.getWidth()) / 2,
                difficulty.getY() - rewardTable.getHeight() + 24);

        // кнопка "СТАРТ" для начала миссии
        startButton = new ColorButton(Warfare.stringHolder.getString(StringHolder.START), ColorButton.GREEN_BUTTON);
        startButton.setX((constructedWindow.getX() + (constructedWindow.getWidth() - startButton.getWidth()) / 2));
        startButton.setY(constructedWindow.getY() + 42);
        addActor(startButton);

        // кнопка "ЗАКРЫТЬ" для закрытия окна с информацией о миссии
        closeButton = constructedWindow.getCloseButton();
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Warfare.media.playSound("clickButton.ogg");
                fire(new MessageEvent(ON_RESUME));
            }
        });

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Warfare.media.playSound("clickButton.ogg");
                hide();
                fire(new MessageEvent(ON_START));
            }
        });
    }

    /**
     * метод для задания характеристик миссии
     **/
    public void setData(LevelIconData data) {
        this.id = data.getId();                                    // номер миссии
        this.coinsCount = data.getCoinsCount();                    // количество монет
        this.scoreCount = data.getScoreCount();                  // количество очков
        this.levelOfDifficulty = data.getLevelOfDifficulty();    // уровень сложност
        // обновим данные о миссии
        updateData();
    }

    /**
     * метод получает кол-во монет (награду за уровень)
     **/
    public int getRewardCoinsForLevel() {
        return coinsCount;
    }

    /**
     * метод получает кол-во очков (награду за уровень)
     **/
    public int getRewardScoreForLevel() {
        return scoreCount;
    }

    private void updateData() {
        missionTitle.setText(Warfare.stringHolder.getString(StringHolder.MISSION) + " " + id);
        difficulty.remove();
        difficulty = new Label("" + levelOfDifficulty, difficultlyLabelStyle);
        addActor(difficulty);
//        difficulty.setText(levelOfDifficulty);
//        difficulty.setPosition(constructedWindow.getX() + (constructedWindow.getWidth() / 2 - difficulty.getWidth() / 2), missionTitle.getY() - difficulty.getHeight() - 16);
        difficulty.setPosition(constructedWindow.getX() + (constructedWindow.getWidth() - difficulty.getWidth()) / 2, missionTitle.getY() - difficulty.getHeight() - 32);
        rewardTable.setCoinsCount(coinsCount);

        rewardTable.setPosition(constructedWindow.getX() + (constructedWindow.getWidth() - rewardTable.getWidth()) / 2,
                difficulty.getY() - rewardTable.getHeight() + 24);
    }

    @Override
    public void clear() {
        super.clear();
        constructedWindow.clear();
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
        this.setVisible(false);
    }

    private void initializeLabels() {

        Label.LabelStyle missionTitleLabelStyle = new Label.LabelStyle();
        missionTitleLabelStyle.fontColor = Color.DARK_GRAY;
        missionTitleLabelStyle.font = Warfare.font40;
        missionTitle = new Label("Mission " + id, missionTitleLabelStyle);
        missionTitle.setAlignment(Align.center);
        missionTitle.setPosition(constructedWindow.getX() + constructedWindow.getWidth() / 2 - missionTitle.getWidth() / 2,
                constructedWindow.getY() + constructedWindow.getHeight() - missionTitle.getHeight() - 8);
        addActor(missionTitle);

        difficultlyLabelStyle = new Label.LabelStyle();
        difficultlyLabelStyle.fontColor = Color.FOREST;
        difficultlyLabelStyle.font = Warfare.font30;
        difficulty = new Label("" + levelOfDifficulty, difficultlyLabelStyle);

        difficulty.setPosition(constructedWindow.getX() + (constructedWindow.getWidth() - difficulty.getWidth()) / 2, missionTitle.getY() - difficulty.getHeight() - 16);
        addActor(difficulty);
    }
}

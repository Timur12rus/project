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
import com.timgapps.warfare.Level.LevelMap.LevelIconData;
import com.timgapps.warfare.Warfare;


public class MissionInfoScreen extends Group {
    public static final int ON_START = 1;
    public static final int ON_RESUME = 2;

    // объявим заголовок и кнопку

    private ImageButton startButton, closeButton;
    private Image background;

    public Label missionTitle; // отображаем текст заголовка
    public Label difficulty; // отображаем текст для уровня сложности
    public Label rewardText; // отображаем текст для вознаграждения
    public Label coinsCountText; // отображаем текст для вознаграждения
    public Label scoreCountText; // отображаем текст для вознаграждения

    private int id, coinsCount, scoreCount;
    private String levelOfDifficulty;

    private Image coinImage;

    private Table rewardTable;

    public MissionInfoScreen() {
        background = new Image(Warfare.atlas.findRegion("mission_start_bg"));
        background.setX((Warfare.V_WIDTH - background.getWidth()) / 2); // устанавливаем позицию заголовка
        background.setY(((Warfare.V_HEIGHT / 2 - background.getHeight() / 2)));

        addActor(background);

        initializeLabels();

        startButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("button_ok")),
                new TextureRegionDrawable(Warfare.atlas.findRegion("button_ok_dwn")));

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

    private void updateData() {
        missionTitle.setText("Mission " + id);
        difficulty.setText(levelOfDifficulty);
        coinsCountText.setText("" + coinsCount);

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

        difficulty.setPosition(missionTitle.getX(), missionTitle.getY() - difficulty.getHeight() - 16);
        addActor(difficulty);

        /** Label для надписи НАГРАДЫ **/
        Label.LabelStyle rewardsLabelStyle = new Label.LabelStyle();
        rewardsLabelStyle.fontColor = Color.DARK_GRAY;
        rewardsLabelStyle.font = Warfare.font20;
        rewardText = new Label("", rewardsLabelStyle);
        rewardText.setText("Rewards:");
        rewardText.setPosition(difficulty.getX(), difficulty.getY() - rewardText.getHeight() - 32);
        addActor(rewardText);

        /** Таблица для размещения информации о награде за уровень **/
        rewardTable = new Table();
//        rewardTable = new Table().debug();


        /** Label для надписи КОЛИЧЕСТВА МОНЕТ **/
        Label.LabelStyle coinsCountLabelStyle = new Label.LabelStyle();
        coinsCountLabelStyle.fontColor = Color.ORANGE;
        coinsCountLabelStyle.font = Warfare.font20;
        coinsCountText = new Label("", coinsCountLabelStyle);
//        coinsCountText.debug();
        coinsCountText.setText("" + coinsCount);
//        coinsCountText.setPosition(rewardText.getX(), rewardText.getY() - coinsCountText.getHeight() - 16);
//        addActor(coinsCountText);


        coinImage = new Image(Warfare.atlas.findRegion("coin_icon"));
//        coinImage.debug();
//        coinImage.setPosition(coinsCountText.getX() + coinsCountText.getWidth(), coinsCountText.getY());
//        addActor(coinImage);


        rewardTable.add(coinsCountText);
        rewardTable.add(coinImage).width(48).height(48);
        rewardTable.setPosition(background.getX() + background.getWidth() / 2, rewardText.getY() - rewardTable.getHeight() - 48);
//        rewardTable.setPosition(missionTitle.getX(), rewardText.getY() - rewardTable.getHeight() - 48);
        addActor(rewardTable);


//        difficulty = new BitmapFont();
//        rewardText = new BitmapFont();
//        coinsCountText = new BitmapFont();
//        scoreCountText = new BitmapFont();


//        missionTitle.setColor(Color.GREEN);
//        difficulty.setColor(Color.YELLOW);
//        rewardText.setColor(Color.GRAY);
//        scoreCountText.setColor(Color.BLUE);
//        coinsCountText.setColor(Color.YELLOW);

//        scoreCountText.getData().setScale(1.6f, 2.2f);
//        coinsCountText.getData().setScale(1.6f, 2.2f);
//        missionTitle.getData().setScale(2, 3);

//        glyphLayout = new GlyphLayout();
    }

    private void displayMessage(Batch batch) {

        // объект класса GlyphLayout хранит в себе информацию о шрифте и содержании текста
//        glyphLayout.setText(missionTitle, "Mission " + id);

        // отображаем результат в левом верхнем углу
//        fontEnergy.setColor(Color.BLUE);
//        missionTitle.draw(batch, glyphLayout, background.getX() + background.getWidth() / 2 - missionTitle.getData().spaceWidth,
//                background.getY() + background.getHeight() - 24);
//
//        glyphLayout.setText(coinsCountText, "" + coinsCount);
//        missionTitle.draw(batch, glyphLayout, background.getX() + background.getWidth() / 2 - missionTitle.getData().spaceWidth,
//                background.getY() + background.getHeight() - 100);
    }

//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
////        displayMessage(batch);
//    }


}

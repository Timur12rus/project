package com.timgapps.warfare.Level.GUI.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.Level.LevelMap.LevelIconData;
import com.timgapps.warfare.Warfare;


public class MissionInfoScreen extends Group {
    public static final int ON_START = 1;
    public static final int ON_RESUME = 2;

    // объявим заголовок и кнопку

    private ImageButton startButton, closeButton;
    private Image background;

    public BitmapFont missionTitle; // отображаем текст заголовка
    public BitmapFont difficulty; // отображаем текст для уровня сложности
    public BitmapFont rewardText; // отображаем текст для вознаграждения
    public BitmapFont coinsCountText; // отображаем текст для вознаграждения
    public BitmapFont scoreCountText; // отображаем текст для вознаграждения
    private GlyphLayout glyphLayout;

    private int id, coinsCount, scoreCount;
    private String levelOfDifficulty;


    public MissionInfoScreen() {

        initializeBitmapFonts();


        background = new Image(Warfare.atlas.findRegion("mission_start_bg"));
        background.setX((Warfare.V_WIDTH - background.getWidth()) / 2); // устанавливаем позицию заголовка
        background.setY(((Warfare.V_HEIGHT / 2 - background.getHeight() / 2)));

        addActor(background);

        startButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("button_ok")),
                new TextureRegionDrawable(Warfare.atlas.findRegion("button_ok_dwn")));

        startButton.setX((background.getX() + (background.getWidth() - startButton.getWidth()) / 2));
        startButton.setY(background.getY() + 42);
        addActor(startButton);

        closeButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("button_close")),
                new TextureRegionDrawable(Warfare.atlas.findRegion("button_close_dwn")));

        closeButton.setX(background.getX() + background.getWidth() - closeButton.getWidth() - 16);
        closeButton.setY(background.getY() + background.getHeight() - closeButton.getHeight());
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
        this.scoreCount = data.getScorCount();
        this.levelOfDifficulty = data.getLevelOfDifficulty();
    }

    private void initializeBitmapFonts() {
        missionTitle = new BitmapFont();
        difficulty = new BitmapFont();
        rewardText = new BitmapFont();
        coinsCountText = new BitmapFont();
        scoreCountText = new BitmapFont();


        missionTitle.setColor(Color.GREEN);
        difficulty.setColor(Color.YELLOW);
        rewardText.setColor(Color.GRAY);
        scoreCountText.setColor(Color.BLUE);
        coinsCountText.setColor(Color.YELLOW);

        scoreCountText.getData().setScale(1.6f, 2.2f);
        coinsCountText.getData().setScale(1.6f, 2.2f);
        missionTitle.getData().setScale(2, 3);

        glyphLayout = new GlyphLayout();
    }

    private void displayMessage(Batch batch) {

        // объект класса GlyphLayout хранит в себе информацию о шрифте и содержании текста
        glyphLayout.setText(missionTitle, "Mission " + id);

        // отображаем результат в левом верхнем углу
//        fontEnergy.setColor(Color.BLUE);
        missionTitle.draw(batch, glyphLayout, background.getX() + background.getWidth() / 2 - missionTitle.getData().spaceWidth,
                background.getY() + background.getHeight() - 24);

        glyphLayout.setText(coinsCountText, "" + coinsCount);
        missionTitle.draw(batch, glyphLayout, background.getX() + background.getWidth() / 2 - missionTitle.getData().spaceWidth,
                background.getY() + background.getHeight() - 100);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        displayMessage(batch);
    }
}

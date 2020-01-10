package com.timgapps.warfare.Level.LevelMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Level.GUI.Screens.MissionInfo;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class LevelMap extends StageGame {
    // создаем несколько констант для создания callBack сообщений, которые будут передаваться в зависимости от нажатия кнопок
    public static final int ON_BACK = 1;
    public static final int ON_LEVEL_SELECTED = 2;
    public static final int ON_OPEN_MARKET = 3;
    public static final int ON_SHARE = 4;

    private Group container;
    private int selectedLevelId = 1;
    private ArrayList<LevelIcon> levelIcons;

    private Viewport mViewport;
    private OrthographicCamera mOrthographicCamera;

    private MissionInfo missionInfo;

    private Group parent;

    public LevelMap() {
        setBackGround("map");

        mOrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mViewport = new StretchViewport(Warfare.V_WIDTH, Warfare.V_HEIGHT, mOrthographicCamera); // The width and height do not need to be in pixels
        Warfare.batch.setProjectionMatrix(mOrthographicCamera.combined);

        levelIcons = new ArrayList<LevelIcon>();
        levelIcons.add(new LevelIcon(1, true));
        levelIcons.get(0).addListener(iconListener);

        for (int i = 2; i < 11; i++) {
            levelIcons.add(new LevelIcon(2, false));
        }

        missionInfo = new MissionInfo();

        addChild(levelIcons.get(0), 244, 56);
        addChild(levelIcons.get(1), 320, 210);
        addChild(levelIcons.get(2), 484, 280);
        addChild(levelIcons.get(3), 640, 210);
        addChild(levelIcons.get(4), 800, 130);
//        addChild(levelIcons.get(6), 244, 56);
//        addChild(levelIcons.get(7), 244, 56);
//        addChild(levelIcons.get(8), 244, 56);
    }

    private void setBackGround(String region) {
        clearBackground();
        Image bg = new Image(Warfare.atlas.findRegion(region));
        addBackground(bg, true, false);
    }

    private ClickListener iconListener = new ClickListener() {
        // при клике мы будем сохранять в переменную полученный id нажатого уровня

        @Override
        public void clicked(InputEvent event, float x, float y) {
            //будем воспроизводить звук щелчка и передавать в callBack сообщенме onLevelSelected
            selectedLevelId = ((LevelIcon) event.getTarget()).getId();
            System.out.println("Clicked");
            call(ON_LEVEL_SELECTED);
        }
    };

    public int getSelectedLevelId() {  // этот метод будет возварщать Id выбранного уровня
        return selectedLevelId;
    }
}

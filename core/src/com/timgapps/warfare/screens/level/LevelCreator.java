package com.timgapps.warfare.screens.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boontaran.MessageListener;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.screens.level.level_windows.GameOverScreen;
import com.timgapps.warfare.screens.level.level_windows.LevelCompletedScreen;
import com.timgapps.warfare.screens.level.level_windows.PauseScreen;
//import com.timgapps.warfare.Units.GameUnits.Enemy.Skeleton;
//import com.timgapps.warfare.Units.GameUnits.Enemy.Skeleton3;
//import com.timgapps.warfare.Units.GameUnits.Enemy.Zombie;
//import com.timgapps.warfare.Units.GameUnits.Enemy.Zombie1;
//import com.timgapps.warfare.Units.GameUnits.Enemy.Zombie3;

import static java.lang.Integer.parseInt;

public class LevelCreator {
    private String levelNumber;
    private LevelScreen levelScreen;
    private TiledMap map;
    private int mapWidth, mapHeight, tilePixelWidth, tilePixelHeight, levelWidth, levelHeight;
    private LevelCompletedScreen levelCompletedScreen;
    private GameOverScreen gameOverScreen;
    private PauseScreen pausedScreen;
    private GameManager gameManager;

    public LevelCreator(LevelScreen levelScreen, GameManager gameManager) {
//    public LevelCreator(LevelScreen lnevelScreen, int levelNumber) {
        this.levelScreen = levelScreen;
        this.gameManager = gameManager;
//        loadLevel(levelNumber);
//        loadMap("tiled/" + directory + "/map.tmx");
    }

    // метод созаёт экран паузы, победы и проигрыша
    public void createScreens() {
        // экран победы
        levelCompletedScreen = new LevelCompletedScreen(levelScreen, gameManager.getCoinsRewardForLevel(), gameManager.getScoreRewardForLevel());
        levelCompletedScreen.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == LevelCompletedScreen.ON_OK) {   // у нас только одна кнопка,
                    clear();                        // освобождаем ресурсы
                    levelScreen.onCompleted();
                }
            }
        });

        // экран проигрыша
        gameOverScreen = new GameOverScreen(levelScreen);
        gameOverScreen.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == gameOverScreen.ON_MAP) {
//                    savePlayerData();
                    clear();
                    levelScreen.onFailed();
//                    call(ON_FAILED);                       // при получении сообщений от которой мы передаем сообщение ON_FAILED

                }
                if (message == GameOverScreen.ON_RETRY) {
                    levelScreen.onRetry();
//                    call(ON_RETRY);
                }
            }
        });

    }

    // метод для показа экрана завершения уровня
    public void showLevelCompletedScreen(int starsCount, float percentage) {
        levelCompletedScreen.setPosition((levelScreen.getWidth() - levelCompletedScreen.getWidth()) / 2, levelScreen.getHeight() * 2 / 3);
        levelCompletedScreen.redrawTowerSavedLabel((int)percentage);
        levelScreen.addScreen(levelCompletedScreen);
        // после того как разрушилась баррикада, вызываем метод запуска экрана завершения уровня
        levelCompletedScreen.start(starsCount);   // запускаем экран завершения уровня, запускаем звезды
    }

    public void showGameOverScreen() {
        gameOverScreen.setPosition((levelScreen.getWidth() - gameOverScreen.getWidth()) / 2, levelScreen.getHeight() * 2 / 3);
        levelScreen.addScreen(gameOverScreen);
    }

    // загружаем уровень (из tmx файла (расположение вражеских юнитов)
    public void loadLevel(int levelNumber) {
//    private void loadLevel(String tmxFile) {
        String tmxFilePath = "levels/level" + levelNumber + ".tmx";
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters(); // здесь мы прописываем параметры обработки tmx-карты уровня
        params.generateMipMaps = true;
        params.textureMinFilter = Texture.TextureFilter.MipMapLinearNearest;
        params.textureMagFilter = Texture.TextureFilter.Linear;

        // загружаем карту
        map = new TmxMapLoader().load(tmxFilePath, params);

        MapProperties prop = map.getProperties();
        mapWidth = prop.get("width", Integer.class);  // получаем и рассчитываем размеры объектов
        mapHeight = prop.get("height", Integer.class);

        tilePixelWidth = prop.get("tilewidth", Integer.class);
        tilePixelHeight = prop.get("tileheight", Integer.class);
        levelWidth = mapWidth * tilePixelWidth;
        levelHeight = mapHeight * tilePixelHeight;
    }

    // метод освобождает ресурсы
    public void clear() {
        levelCompletedScreen.clearActions();
        levelCompletedScreen.remove();
        levelScreen.removeScreen(levelCompletedScreen);
        gameOverScreen.clearActions();
        gameOverScreen.remove();
        levelScreen.removeScreen(gameOverScreen);
        map.dispose();
    }
}



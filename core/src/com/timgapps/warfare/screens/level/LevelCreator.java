package com.timgapps.warfare.screens.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boontaran.MessageListener;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.screens.level.LevelWindows.GameOverScreen;
import com.timgapps.warfare.screens.level.LevelWindows.LevelCompletedScreen;
import com.timgapps.warfare.screens.level.LevelWindows.PauseScreen;
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
//    public LevelCreator(LevelScreen levelScreen, int levelNumber) {
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
                    clear();
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
    public void showLevelCompletedScreen(int starsCount) {
        levelCompletedScreen.setPosition((levelScreen.getWidth() - levelCompletedScreen.getWidth()) / 2, levelScreen.getHeight() * 2 / 3);
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

        // сканируем все слои и получаем объекты
//        for (MapLayer layer : map.getLayers()) {
//            String name = layer.getName();
//            createEnemyUnit(layer.getObjects(), name);
//        }
    }

    // мето освобождает ресурсы
    public void clear() {
        levelCompletedScreen.clearActions();
        levelScreen.removeScreen(levelCompletedScreen);
        gameOverScreen.clearActions();
        levelScreen.removeScreen(gameOverScreen);
        map.dispose();
    }

//    private void createEnemyUnit(MapObjects objects, String LayerName) {
//
////        for (MapObject object : objects) {
////            Rectangle rectangle;
////            float x = object.getProperties().get("x", Float.class);
////            float y = object.getProperties().get("y", Float.class);
////            rectangle = new Rectangle(x, y, 32, 32);
////            if (LayerName.equals("zombie")) {
////                Zombie zombie = new Zombie(level, rectangle.x, rectangle.y + 172, 100, 3);
//////                Skeleton skeleton = new Skeleton(level, rectangle.x + 60, rectangle.y + 172, 100, 5);
////                level.addEnemyUnitToEnemyArray(zombie);
////            }
////            if (LayerName.equals("zombie1")) {
////                Zombie1 zombie1 = new Zombie1(level, rectangle.x, rectangle.y + 172, 100, 3);
////                level.addEnemyUnitToEnemyArray(zombie1);
////            }
////            if (LayerName.equals("zombie3")) {
////                Zombie3 zombie3 = new Zombie3(level, rectangle.x, rectangle.y + 172, 100, 3);
////                level.addEnemyUnitToEnemyArray(zombie3);
////            }
//////            if (LayerName.equals("goblin1")) {
//////                Goblin1 goblin1 = new Goblin1(level, rectangle.x, rectangle.y + 172, 100, 3);
//////                level.addEnemyUnitToEnemyArray(goblin1);
//////            }
//////            if (LayerName.equals("skeleton1")) {
//////                Skeleton skeleton = new Skeleton(level, rectangle.x, rectangle.y + 172, 100, 3);
//////                level.addEnemyUnitToEnemyArray(skeleton);
//////            }
//////            if (LayerName.equals("skeleton3")) {
//////                Skeleton3 skeleton3 = new Skeleton3(level, rectangle.x, rectangle.y + 172, 100, 3);
//////                level.addEnemyUnitToEnemyArray(skeleton3);
//////            }
////        }
//    }
}



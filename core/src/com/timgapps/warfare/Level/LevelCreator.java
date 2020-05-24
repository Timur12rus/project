package com.timgapps.warfare.Level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.boontaran.games.tiled.TileLayer;
import com.timgapps.warfare.Units.GameUnits.Enemy.Zombie;

import static java.lang.Integer.parseInt;

public class LevelCreator {
    private String levelNumber;
    private Level level;

    private TiledMap map;
    private int mapWidth, mapHeight, tilePixelWidth, tilePixelHeight, levelWidth, levelHeight;

    public LevelCreator(Level level, int levelNumber) {
        this.level = level;
        loadLevel("levels/level" + levelNumber + ".tmx");
//        loadMap("tiled/" + directory + "/map.tmx");
    }

    private void loadLevel(String tmxFile) {
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters(); // здесь мы прописываем параметры обработки tmx-карты уровня
        params.generateMipMaps = true;
        params.textureMinFilter = Texture.TextureFilter.MipMapLinearNearest;
        params.textureMagFilter = Texture.TextureFilter.Linear;

        // загружаем карту
        map = new TmxMapLoader().load(tmxFile, params);

        MapProperties prop = map.getProperties();
        mapWidth = prop.get("width", Integer.class);  // получаем и рассчитываем размеры объектов
        mapHeight = prop.get("height", Integer.class);

        tilePixelWidth = prop.get("tilewidth", Integer.class);
        tilePixelHeight = prop.get("tileheight", Integer.class);
        levelWidth = mapWidth * tilePixelWidth;
        levelHeight = mapHeight * tilePixelHeight;

        // сканируем все слои и получаем объекты
        for (MapLayer layer : map.getLayers()) {
            String name = layer.getName();

            if (name.equals("zombie")) {
                createZombie(layer.getObjects(), name);
            }
//            else {
//                TileLayer tLayer = new TileLayer(camera, map, name, stage.getBatch());
//                addChild(tLayer);
//            }
        }
    }

    private void createZombie(MapObjects objects, String LayerName) {
        for (MapObject object : objects) {
            Rectangle rectangle;
            float x = object.getProperties().get("x", Float.class);
            float y = object.getProperties().get("y", Float.class);
            rectangle = new Rectangle(x, y, 32, 32);
            if (LayerName.equals("zombie")) {
                Zombie zombie = new Zombie(level, rectangle.x, rectangle.y + 172, 100, 3);
                level.addEnemyUnitToEnemyArray(zombie);
            }
        }
    }
}


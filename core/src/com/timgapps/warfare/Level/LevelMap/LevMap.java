package com.timgapps.warfare.Level.LevelMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.boontaran.games.StageGame;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.boontaran.games.tiled.TileLayer;
import com.timgapps.warfare.Warfare;

import static java.lang.Integer.parseInt;

public class LevMap extends StageGame {

    private TiledMap map;
    private int mapWidth, mapHeight, tilePixelWidth, tilePixelHeight, levelWidth, levelHeight;

    public LevMap(String directory) {
            loadMap("tiled/" + directory + "/map.tmx");   // метод загружает карту и создает объекты
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float x = Gdx.input.getDeltaX();
        float y = Gdx.input.getDeltaY();
        float cameraXpos = camera.position.x;
        float cameraYpos = camera.position.y;
//        System.out.println("camera.ViewportWidth = " + camera.viewportWidth);
//        System.out.println("xPos = " + xPos);
//        if (x - (camera.viewportWidth / 2) > 0) {
//            camera.translate(-x, y);
//        }

        if (cameraXpos - x < camera.viewportWidth / 2) {
            x = 0;
        }
        if (cameraXpos - x > camera.viewportWidth + 320) {
            x = 0;
        }

        if (cameraYpos + y < camera.viewportHeight / 2) {
            y = 0;
        }
        if (cameraYpos + y > camera.viewportHeight / 2 + 300) {
            y = 0;
        }

        System.out.println("cameraPosition.y + y = " + camera.position.y + y);
//        System.out.println("cameraPosition.x - x = " + cameraXpos - x);
//        if (cameraXpos + x )
        camera.translate(-x, y);

        return true;
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
//        updateCamera();
    }
//    private void updateCamera() {
//        if (camera.position.x > Warfare.V_WIDTH / 2)
//    }

    private void loadMap(String tmxFile) {
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters(); // здесь мы прописываем параметры обработки tmx-карты уровня
        params.generateMipMaps = true;
        params.textureMinFilter = TextureFilter.MipMapLinearNearest;
        params.textureMagFilter = TextureFilter.Linear;

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

            if (name.equals("locations")) {
                createLands(layer.getObjects(), name);
            } else if (name.equals("trolls")) {
                createTrolls(layer.getObjects(), name);
            } else {
                TileLayer tLayer = new TileLayer(camera, map, name, stage.getBatch());
                addChild(tLayer);
            }
        }
    }

    private void createLands(MapObjects objects, String LayerName) {
        for (MapObject object : objects) {
            Rectangle rectangle;
            float x = object.getProperties().get("x", Float.class);
            float y = object.getProperties().get("y", Float.class);
            rectangle = new Rectangle(x, y, 32, 32);
            if (LayerName.equals("locations")) {
//                new Troll(this, rectangle.x, rectangle.y);
//                new LevelIcon(parseInt(object.getName()), 25, 75, LevelIcon.EASY, true);
                addChild(new LevelIcon(parseInt(object.getName()),
                        25, 75, LevelIcon.EASY, true), rectangle.x - 32, rectangle.y);
//                new Troll(this, rectangle.x, rectangle.y);
//                trolls.add(new Troll(this, rectangle.x, rectangle.y));
//                System.out.println("asddsaa");
//                System.out.println("rect.x = " + rectangle.x + "rect.y = " + rectangle.y);
            }
        }
    }

    private void createTrolls(MapObjects objects, String name) {
    }

//    @Override
//    protected void render(float delta, float pauseTime) {
////        if (camera.position.x >= Warfare.V_WIDTH / 2)
////        camera.update();
////        System.out.println("cameraPosition.x = " + camera.position.x);
//        super.render(delta, pauseTime);
//
//    }
}

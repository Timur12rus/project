package com.timgapps.warfare;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.I18NBundle;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Level.Level;

import java.util.Locale;

public class Warfare extends Game {

    public static final int V_WIDTH = 1280;      // 800        //1280
    public static final int V_HEIGHT = 720;     // 480        //720
    private GameCallback gameCallback;

    public static SpriteBatch batch;
    private boolean loadingAssets = false; // будем присваивать true в процессе загрузки ресурсов
    private AssetManager assetManager;
    public static TextureAtlas atlas; // через переменную класса TextureAtlas мы будем работать с атласом текстур
    private I18NBundle bundle;   // для выбора ресурсов в зависимости от локализации использеются класс I18NBundle
    private String path_to_atlas; // в зависимости от локали в переменную path_to_atlas будет возвращаться путь к нужному нам атласу
    private Level level;

    public Warfare(GameCallback gameCallback) {    // это конструктор для класса CrazyCatapult с переменной класса GameCallback
        this.gameCallback = gameCallback;
        System.out.println("Create Game");
//
//        this.googleServices = googleServices;
//        this.googleServices.setVideoEventListener(this);
    }

    @Override
    public void create() {
        StageGame.setAppSize(V_WIDTH, V_HEIGHT);
        batch = new SpriteBatch();
        Gdx.input.setCatchBackKey(true);    // метод setCatchBackKey определяет перехватывать ли кнопку <-Back на устройстве

//        Locale locale = Locale.getDefault();
//        bundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale); // передаем методу createBundle() путь к  папке с файлами конфигурации, в
        // которых будут прописаны пути к ресурсам, а также текущую локаль
        path_to_atlas = "images/pack.atlas";

        loadingAssets = true; // присваиваем переменной значение true;
        assetManager = new AssetManager();  //Создаем объект класса AssetManager
        assetManager.load(path_to_atlas, TextureAtlas.class);  // методом load() выполняем сначала загрузку атласа(путь к атласу, и передаем класс TextureAtlas.class)

    }

    @Override
    public void render() {
        // этот метод render() выполняется каждый раз, когда должна быть выполнена визуализиция
        // обновление логики игры обычно пишут в этом методе

        // проверяем загружены ли ресурсы
        if (loadingAssets) {
            if (assetManager.update()) {
                loadingAssets = false;
                onAssetsLoaded();
            }
        }
        super.render();
    }

    @Override
    public void dispose() {
        // этот метод вызывается перед остановкой игры
        // в нем неоходимо освобождать занимаемую ресурсами память
        assetManager.dispose();
        super.dispose();
    }

    private void onAssetsLoaded() {  // получаем загруженный атлас текстур и шрифт
        atlas = assetManager.get(path_to_atlas, TextureAtlas.class);


//        catatlas = assetManager.get("catatlas.atlas", TextureAtlas.class);

//        font40 = assetManager.get("font40.ttf", BitmapFont.class);
//        font10 = assetManager.get("font40.ttf", BitmapFont.class);

        showLevel();
//        showIntro();
    }

    private void showLevel() {
        level = new Level();
        setScreen(level);
    }
}

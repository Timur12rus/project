package com.timgapps.warfare;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Level.LevelMap.LevelMap;

public class Warfare extends Game {

    public static final int V_WIDTH = 1280;      // 800        //1280
    public static final int V_HEIGHT = 720;     // 480        //720
    private GameCallback gameCallback;

    public static SpriteBatch batch;
    private boolean loadingAssets = false; // будем присваивать true в процессе загрузки ресурсов
    private AssetManager assetManager;
    public static TextureAtlas atlas; // через переменную класса TextureAtlas мы будем работать с атласом текстур
    public static BitmapFont font40;
    public static BitmapFont font20;
    public static BitmapFont font10;
    private I18NBundle bundle;   // для выбора ресурсов в зависимости от локализации использеются класс I18NBundle
    private String path_to_atlas; // в зависимости от локали в переменную path_to_atlas будет возвращаться путь к нужному нам атласу
    private Level level;
    private LevelMap levelMap;

    private Viewport mViewport;
    private OrthographicCamera mOrthographicCamera;

    private GameManager gameManager;

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

        mOrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mViewport = new StretchViewport(V_WIDTH, V_HEIGHT, mOrthographicCamera); // The width and height do not need to be in pixels
        batch.setProjectionMatrix(mOrthographicCamera.combined);


//        Locale locale = Locale.getDefault();
//        bundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale); // передаем методу createBundle() путь к  папке с файлами конфигурации, в
        // которых будут прописаны пути к ресурсам, а также текущую локаль
        path_to_atlas = "images/pack.atlas";

        loadingAssets = true; // присваиваем переменной значение true;
        assetManager = new AssetManager();  //Создаем объект класса AssetManager
        assetManager.load(path_to_atlas, TextureAtlas.class);  // методом load() выполняем сначала загрузку атласа(путь к атласу, и передаем класс TextureAtlas.class)


        // Подготовим шрифт для работы
        // Для начала создадим обработчик шрифта
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter sizeParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        sizeParams.fontFileName = "fonts/GROBOLD.ttf";
        sizeParams.fontParameters.size = 40;    // 40
        assetManager.load("font40.ttf", BitmapFont.class, sizeParams);

        FreetypeFontLoader.FreeTypeFontLoaderParameter sizeParams20 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        sizeParams20.fontFileName = "fonts/GROBOLD.ttf";
        sizeParams20.fontParameters.size = 30;    // 40
        assetManager.load("font20.ttf", BitmapFont.class, sizeParams20);
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

        font40 = assetManager.get("font40.ttf", BitmapFont.class);
        font20 = assetManager.get("font20.ttf", BitmapFont.class);
//        font10 = assetManager.get("font40.ttf", BitmapFont.class);

        /** Вызываем метод для запуска игрового уровня **/
//        showLevel();

        gameManager = new GameManager();

        /** Вызываем метод для запуска карты уровней **/
        showMap();
//        showIntro();
    }

    private void showMap() {
        levelMap = new LevelMap(gameManager);
        setScreen(levelMap);
        System.out.println("Show map");

        levelMap.setCallback(new StageGame.Callback() {

            @Override
            public void call(int code) {
                // отрабатываем действие в зависимости от полученных сообщений
                if (code == LevelMap.ON_BACK) {
                    showExit();
                    hideLevelMap();
                } else if (code == LevelMap.ON_LEVEL_SELECTED) {
                    // при получении кода ON_LEVEL_SELECTED вызываем метод открытия уровня
                    showLevel(levelMap.getSelectedLevelId());

                    hideLevelMap();
                }
            }
        });
    }

    private void showExit() {
        System.out.println("EXIT");
    }


    private void showLevel(int id) { // метод показа игрового уровня, передаем идентификатор уровня

        switch (id) {   // создаем уровень на основе идентификатора
            case 1:
                level = new Level(1);
                break;
            case 2:
                level = new Level(2);
                break;
            default:
                level = new Level(1);
                break;
        }

        setScreen(level);
    }

    public void hideLevelMap() {
        levelMap = null;
    }

    private void showLevel() {
        System.out.println("Show level");
        level = new Level(1);
        setScreen(level);
    }
}

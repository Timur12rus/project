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
import com.timgapps.warfare.screens.get_reward_screen.GetRewardScreen;
import com.timgapps.warfare.screens.reward_for_stars.RewardForStarsScreen;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.screens.map.MapScreen;

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
    private LevelScreen levelScreen;
    private MapScreen mapScreen;
    private RewardForStarsScreen rewardForStarsScreen;
    private GetRewardScreen getRewardScreen;

    private Viewport mViewport;
    private OrthographicCamera mOrthographicCamera;

    private GameManager gameManager;
    private int levelId;
//    private LevMap levMap;


    public Warfare(GameCallback gameCallback) {    // это конструктор для класса CrazyCatapult с переменной класса GameCallback
        this.gameCallback = gameCallback;
        System.out.println("Create Game");
//
//        this.googleServices = googleServices;
//        this.googleServices.setVideoEventListener(this);
    }

    @Override
    public void pause() {
        gameManager.saveGame();
        super.pause();
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
//        bundle = I18NBundle.createBundle(Gdx.files.inte rnal("MyBundle"), locale); // передаем методу createBundle() путь к  папке с файлами конфигурации, в
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
        sizeParams.fontFileName = "fonts/Nickname.ttf";
//        sizeParams.fontFileName = "fonts/GROBOLD.ttf";
        sizeParams.fontParameters.size = 40;    // 40
//        sizeParams.fontParameters.size = 40;    // 40
        assetManager.load("font40.ttf", BitmapFont.class, sizeParams);

        FreetypeFontLoader.FreeTypeFontLoaderParameter sizeParams20 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        sizeParams20.fontFileName = "fonts/Nickname.ttf";
//        sizeParams20.fontFileName = "fonts/GROBOLD.ttf";
        sizeParams20.fontParameters.size = 30;    // 40
//        sizeParams20.fontParameters.size = 30;    // 40
        assetManager.load("font20.ttf", BitmapFont.class, sizeParams20);

        FreetypeFontLoader.FreeTypeFontLoaderParameter sizeParams10 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        sizeParams10.fontFileName = "fonts/Nickname.ttf";
        sizeParams10.fontParameters.size = 18;
        assetManager.load("font10.ttf", BitmapFont.class, sizeParams10);
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
        atlas.dispose();
        assetManager.dispose();
        super.dispose();
    }

    private void onAssetsLoaded() {  // получаем загруженный атлас текстур и шрифт
        atlas = assetManager.get(path_to_atlas, TextureAtlas.class);

//        catatlas = assetManager.get("catatlas.atlas", TextureAtlas.class);

        font40 = assetManager.get("font40.ttf", BitmapFont.class);
        font20 = assetManager.get("font20.ttf", BitmapFont.class);
        font10 = assetManager.get("font10.ttf", BitmapFont.class);

        /** Вызываем метод для запуска игрового уровня **/
//        showLevel();

        gameManager = new GameManager();

        if (gameManager.isHaveReward()) {
//            gameManager.updateRewardsForStars();
            showGetRewardScreen();
        } else {

            /** Вызываем метод для запуска карты уровней **/
            showMap(0, 0);
        }
//        showIntro();
    }

    private void showMap(int coinsReward, int scoreReward) {
        if (mapScreen == null) {
            mapScreen = new MapScreen(gameManager, coinsReward, scoreReward);
        }
        setScreen(mapScreen);
        mapScreen.setCallback(new StageGame.Callback() {
            @Override
            public void call(int code) {
                // отрабатываем действие в зависимости от полученных сообщений
                if (code == MapScreen.ON_BACK) {
                    hideLevelMap();
                    showExit();
                } else if (code == MapScreen.ON_LEVEL_SELECTED) {
                    // при получении кода ON_LEVEL_SELECTED вызываем метод открытия уровня
                    // передаем в showLevel номер выбранного уровня
                    levelId = mapScreen.getSelectedLevelId();
                    hideLevelMap();
                    showLevel(levelId);
                } else if (code == MapScreen.ON_SHOW_GET_REWARD) {
                    hideLevelMap();
                    showGetRewardScreen();
                } else if (code == MapScreen.ON_SHOW_REWARD_FOR_STARS_SCREEN) {
//                    levelMap = null;
                    hideLevelMap();
                    showRewardForStarsScreen();
                }
            }
        });
    }

    private void showRewardForStarsScreen() {
        if (rewardForStarsScreen == null) {
            rewardForStarsScreen = new RewardForStarsScreen(gameManager);
        }
        System.out.println("rewardForStarsScreen = " + rewardForStarsScreen);
        setScreen(rewardForStarsScreen);
        rewardForStarsScreen.setCallback(new StageGame.Callback() {
            @Override
            public void call(int code) {
                if (code == rewardForStarsScreen.ON_BACK) {
                    hideRewardForStarsScreen();

                    // TODO для теста (нужно будет раскоментировать ниже)
//                    showGetRewardScreen();
                    showMap(0, 0);
                }
            }
        });
    }

    private void showGetRewardScreen() {
        getRewardScreen = new GetRewardScreen(gameManager);
        setScreen(getRewardScreen);
        getRewardScreen.setCallback(new StageGame.Callback() {
            @Override
            public void call(int code) {
                if (code == getRewardScreen.ON_BACK) {
                    hidGetReward();
                    showMap(0, 0);
                }
            }
        });
    }

    private void showExit() {
//        System.out.println("EXIT");
    }

    private void showLevel(int id) { // метод показа игрового уровня, передаем идентификатор уровня
        levelScreen = new LevelScreen(id, gameManager);
        setScreen(levelScreen);

        levelScreen.setCallback(new StageGame.Callback() {
            @Override
            public void call(int code) {
                if (code == LevelScreen.ON_COMPLETED) {
                    int rewardCoins = levelScreen.getRewardCoinsCount();
                    int rewardScore = levelScreen.getRewardScoreCount();
//                    level.unlockNextLevels();
                    hideLevel();
                    if (gameManager.checkStarsCountForReward()) {
                        showGetRewardScreen();          // показываем экран с анимацией получения нового юнита за звезды
                    } else {
                        // установим количество монет - награду за уровень
                        showMap(rewardCoins, rewardScore);      // показываем экран с картой
                    }
                }
                if (code == LevelScreen.ON_FAILED) {
                    hideLevel();
                    // установим количество монет - награду за уровень
                    showMap(0, 0);
                }

                if (code == LevelScreen.ON_RETRY) {
                    hideLevel();
                    showLevel(levelId);
                }

            }
        });
    }

    private void hideRewardForStarsScreen() {
        rewardForStarsScreen.hide();
//        rewardForStarsScreen = null;
    }

    private void hidGetReward() {
        getRewardScreen.hide();
//        getRewardScreen = null;
    }

    private void hideLevel() {
        levelScreen.hide();
//        level.dispose();
//        level = null;
    }

    public void hideLevelMap() {
        mapScreen.hide();
//        levelMap = null;
    }

    private void showLevel() {
        levelScreen = new LevelScreen(1, gameManager);
        setScreen(levelScreen);
    }
}

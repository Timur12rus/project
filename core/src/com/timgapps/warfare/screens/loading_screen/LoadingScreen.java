package com.timgapps.warfare.screens.loading_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.I18NBundle;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Warfare;

import java.util.Locale;

public class LoadingScreen extends StageGame {
    private LoadingBar loadingBar;
    private I18NBundle bundle;   // для выбора ресурсов в зависимости от локализации использеются класс I18NBundle
    private String path_to_atlas; // в зависимости от локали в переменную path_to_atlas будет возвращаться путь к нужному нам атласу
    private AssetManager assetManager;
    private boolean loadingAssets = false; // будем присваивать true в процессе загрузки ресурсов
    private MyAssetsLoader myAssetsLoader;
    private boolean isAssetsLoaded;
    private boolean isStartedGame;
    private float delayTime;
    private Label loadingLabel;

    public LoadingScreen(AssetManager assetManager, MyAssetsLoader myAssetsLoader) {
        this.assetManager = assetManager;
        this.myAssetsLoader = myAssetsLoader;
//        Locale locale = Locale.getDefault();
//        bundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale); // передаем методу createBundle() путь к  папке с файлами конфигурации, в
        path_to_atlas = "images/pack.atlas";

        Label.LabelStyle greenLabelStyle = new Label.LabelStyle();
        greenLabelStyle.fontColor = Color.RED;
        greenLabelStyle.font = Warfare.font20;

        addChild(new MyLoadingBar(assetManager));
        loadAssets(assetManager, myAssetsLoader);

    }

    private void loadAssets(AssetManager assetManager, MyAssetsLoader myAssetsLoader) {
        // load synchronously
//        assetManager.load("1.jpg", Texture.class);
//        assetManager.load("loadingbarbackground.png", Texture.class);
//        assetManager.load("loadingbarprogress.png", Texture.class);
//        assetManager.finishLoading();

        // load asynchronously
        assetManager.load(path_to_atlas, TextureAtlas.class);  // методом load() выполняем сначала загрузку атласа(путь к атласу, и передаем класс TextureAtlas.class)
        // Подготовим шрифт для работы
        // Для начала создадим обработчик шрифта
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter sizeParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        sizeParams.fontFileName = "fonts/Nickname.ttf";
        sizeParams.fontParameters.size = 40;    // 40
        assetManager.load("font40.ttf", BitmapFont.class, sizeParams);

        FreetypeFontLoader.FreeTypeFontLoaderParameter sizeParams20 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        sizeParams20.fontFileName = "fonts/Nickname.ttf";
        sizeParams20.fontParameters.size = 30;    // 30
        assetManager.load("font20.ttf", BitmapFont.class, sizeParams20);

        FreetypeFontLoader.FreeTypeFontLoaderParameter sizeParams10 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        sizeParams10.fontFileName = "fonts/Nickname.ttf";
        sizeParams10.fontParameters.size = 18;      // 18
//        sizeParams10.fontParameters.size = 18;      // 18
        assetManager.load("font10.ttf", BitmapFont.class, sizeParams10);

//        myAssetsLoader.onAssetsLoaded();
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        assetManager.update();
        System.out.println("AssetManager progress = " + assetManager.getProgress());
        if (assetManager.getProgress() == 1 && !isAssetsLoaded) {
            isAssetsLoaded = true;
//            myAssetsLoader.onAssetsLoaded();
        }
        if (isAssetsLoaded && !isStartedGame) {
            delayTime++;
            if (delayTime > 60) {
                isStartedGame = true;
                hide();
            }
        }
        if (isAssetsLoaded && isStartedGame) {
            myAssetsLoader.onAssetsLoaded();
        }
        System.out.println("Update!!!");
    }

    @Override
    public void hide() {
        super.hide();
        stage.getActors().clear();
    }

    @Override
    protected void render(float delta, float pauseTime) {
        super.render(delta, pauseTime);
    }

    public void dispose() {
    }
}

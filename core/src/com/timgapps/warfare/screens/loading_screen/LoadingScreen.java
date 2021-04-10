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
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.I18NBundle;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Warfare;

import java.util.Locale;

public class LoadingScreen extends StageGame {
    private I18NBundle bundle;   // для выбора ресурсов в зависимости от локализации использеются класс I18NBundle
    private String path_to_atlas; // в зависимости от локали в переменную path_to_atlas будет возвращаться путь к нужному нам атласу
    private AssetManager assetManager;
    private boolean loadingAssets = false; // будем присваивать true в процессе загрузки ресурсов
    private MyAssetsLoader myAssetsLoader;
    private boolean isAssetsLoaded;
    private boolean isStartedGame;
    private float delayTime;
    private MyLoadingBar loadingBar;
    private BlackRectangle blackRectangle;

    public LoadingScreen(AssetManager assetManager, MyAssetsLoader myAssetsLoader) {
        this.assetManager = assetManager;
        this.myAssetsLoader = myAssetsLoader;
//        Locale locale = Locale.getDefault();
//        bundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale); // передаем методу createBundle() путь к  папке с файлами конфигурации, в
        path_to_atlas = "images/pack.atlas";
        loadingBar = new MyLoadingBar(assetManager);
        blackRectangle = new BlackRectangle(loadingBar.getX(), loadingBar.getY(),
                loadingBar.getWidth() + 24, loadingBar.getHeight() + 60, Color.BLACK);
//        addChild(new MyLoadingBar(assetManager));
        addChild(loadingBar);
        addChild(blackRectangle);
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
//        sizeParams.fontParameters.borderWidth = 4;
//        sizeParams.fontParameters.borderColor = Color.BLACK;
        assetManager.load("font40.ttf", BitmapFont.class, sizeParams);

        FreetypeFontLoader.FreeTypeFontLoaderParameter sizeParams20 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        sizeParams20.fontFileName = "fonts/Nickname.ttf";
        sizeParams20.fontParameters.size = 30;    // 30
//        sizeParams20.fontParameters.borderColor = Color.GREEN;
//        sizeParams.fontParameters.borderWidth = 2;
        assetManager.load("font20.ttf", BitmapFont.class, sizeParams20);

        FreetypeFontLoader.FreeTypeFontLoaderParameter sizeParams10 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        sizeParams10.fontFileName = "fonts/Nickname.ttf";
        sizeParams10.fontParameters.size = 18;      // 18
//        sizeParams10.fontParameters.borderColor = Color.BLACK;
//        sizeParams.fontParameters.borderWidth = 4;
        assetManager.load("font10.ttf", BitmapFont.class, sizeParams10);
    }

//    public void starFadeOutAction() {
//        Action checkEndOfAction = new Action() {
//            @Override
//            public boolean act(float delta) {
//                isStartedGame = true;
//                return true;
//            }
//        };
//        SequenceAction fadeOutAction = new SequenceAction(Actions.fadeOut(2f),
//                checkEndOfAction
//        );
//        loadingBar.addAction(fadeOutAction);
//    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        assetManager.update();
        System.out.println("AssetManager progress = " + assetManager.getProgress());
        if (assetManager.getProgress() == 1 && !isAssetsLoaded) {
            isAssetsLoaded = true;
//            loadingBar.hide();
            blackRectangle.starAction();
//            starFadeOutAction();
//            myAssetsLoader.onAssetsLoaded();
        }

//        if (isAssetsLoaded && !isStartedGame) {
//            delayTime++;
//            if (delayTime > 60) {
//                isStartedGame = true;
//                hide();
//            }
//        }
        if (isAssetsLoaded && blackRectangle.isEndAction()) {
//        if (isAssetsLoaded && loadingBar.isHide()) {
            myAssetsLoader.onAssetsLoaded();
        }
    }

    @Override
    public void hide() {
        super.hide();
//        loadingBar.dispose();
        stage.getActors().clear();
        System.out.println("Hide loadingScreen");
    }

    public void dispose() {
        loadingBar.dispose();
    }
}
package com.timgapps.warfare.screens.loading_screen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import sun.font.TextRecord;

public class LoadingBar extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    AssetManager assetManager;
    Texture loadingBarBackground;
    Texture loadingBarProgress;

    TextureRegion loadingBarProgressStart;
    TextureRegion loadingBarProgressBody;
    TextureRegion loadingBarProgressEnd;

    private void loadAssets() {
        // load synchronously
//        assetManager.load("1.jpg", Texture.class);
        assetManager.load("loadingbarbackground.png", Texture.class);
        assetManager.load("loadingbarprogress.png", Texture.class);
        assetManager.finishLoading();

        // load asynchronously
        assetManager.load("1.jpg", Texture.class);
        assetManager.load("2.jpg", Texture.class);
        assetManager.load("3.jpg", Texture.class);
    }

    @Override
    public void create() {
        super.create();
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        loadAssets();
        img = assetManager.get("1", Texture.class);

        loadingBarBackground = assetManager.get("loadingbar/loadingbarbackground.png", Texture.class);
        loadingBarProgress = assetManager.get("loadingbar/loadingbarprogress.png", Texture.class);

        // сама текстура панели загрузки имеет ширину 498, мы используем 20 пикселей для старт
        // barWidth = Start=20px; Body 489 - 20 - 20 = 449; End=20px
        loadingBarProgressStart = new TextureRegion(loadingBarProgress, 0, 0, 20, loadingBarProgress.getHeight());
        loadingBarProgressBody = new TextureRegion(loadingBarProgress, 20, 0, 340, loadingBarProgress.getHeight());
        loadingBarProgressEnd = new TextureRegion(loadingBarProgress, 20 + 340, 0, 20, loadingBarProgress.getHeight());
    }

    @Override
    public void render() {
        super.render();
        int initialPosX = 100;
        int initialPosY = 50;
        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(loadingBarBackground, 0, 0);
        // batch.draw(loadingBarProgress, 0, 0);
        batch.draw(loadingBarProgressStart, initialPosX, initialPosY);
        batch.draw(loadingBarProgressBody, initialPosX + loadingBarProgressStart.getRegionWidth(), initialPosY,
                loadingBarProgressBody.getRegionWidth() * assetManager.getProgress(),
                loadingBarProgressBody.getRegionHeight());
        batch.draw(loadingBarProgressEnd, initialPosX + loadingBarProgressStart.getRegionWidth() + loadingBarProgressBody.getRegionWidth() * assetManager.getProgress(), initialPosY);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        img.dispose();
    }
}

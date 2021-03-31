package com.timgapps.warfare.screens.loading_screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.timgapps.warfare.Warfare;

public class MyLoadingBar extends Actor {
    private Texture loadingBarBackground;
    private Texture loadingBarProgress;
    private TextureRegion loadingBarProgressStart;
    private TextureRegion loadingBarProgressBody;
    private TextureRegion loadingBarProgressEnd;
    private int initialPosX;
    private int initialPosY;
    private AssetManager assetManager;

    public MyLoadingBar(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.load("loadingbarbackground.png", Texture.class);
        assetManager.load("loadingbarprogress.png", Texture.class);
        assetManager.finishLoading();

        loadingBarBackground = assetManager.get("loadingbarbackground.png", Texture.class);
        loadingBarProgress = assetManager.get("loadingbarprogress.png", Texture.class);

        loadingBarProgressStart = new TextureRegion(loadingBarProgress, 0, 0, 20, loadingBarProgress.getHeight());
        loadingBarProgressBody = new TextureRegion(loadingBarProgress, 20, 0, 340, loadingBarProgress.getHeight());
        loadingBarProgressEnd = new TextureRegion(loadingBarProgress, 20 + 340, 0, 20, loadingBarProgress.getHeight());
        initialPosX = (Warfare.V_WIDTH - loadingBarBackground.getWidth()) / 2;
        initialPosY = (Warfare.V_HEIGHT - loadingBarBackground.getHeight()) / 2;
        setPosition(initialPosX, initialPosY);
        setSize(loadingBarBackground.getWidth(), loadingBarBackground.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(loadingBarBackground, 0, 0);
        batch.draw(loadingBarProgressStart, initialPosX, initialPosY);
        batch.draw(loadingBarProgressBody, initialPosX + loadingBarProgressStart.getRegionWidth(), initialPosY,
                loadingBarProgressBody.getRegionWidth() * assetManager.getProgress(),
                loadingBarProgressBody.getRegionHeight());
        batch.draw(loadingBarProgressEnd, initialPosX + loadingBarProgressStart.getRegionWidth() + loadingBarProgressBody.getRegionWidth() * assetManager.getProgress(), initialPosY);
    }

}

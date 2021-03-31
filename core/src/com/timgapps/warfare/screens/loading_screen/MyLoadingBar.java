package com.timgapps.warfare.screens.loading_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
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
    private BitmapFont font;
    private boolean isHide;

    public MyLoadingBar(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.load("loadingbarbackground.png", Texture.class);
        assetManager.load("loadingbarprogress.png", Texture.class);
        assetManager.finishLoading();

        loadingBarBackground = assetManager.get("loadingbarbackground.png", Texture.class);
        loadingBarProgress = assetManager.get("loadingbarprogress.png", Texture.class);

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Nickname.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
        p.color = Color.GREEN;
        p.size = 30;
//        p.borderColor = Color.GOLD;
        p.characters = "0123456789%LOADINGЗАГРУЗКА...";

        loadingBarProgressStart = new TextureRegion(loadingBarProgress, 0, 0, 20, loadingBarProgress.getHeight());
        loadingBarProgressBody = new TextureRegion(loadingBarProgress, 20, 0, 356, loadingBarProgress.getHeight());
        loadingBarProgressEnd = new TextureRegion(loadingBarProgress, 20 + 340, 0, 20, loadingBarProgress.getHeight());
        initialPosX = (Warfare.V_WIDTH - loadingBarBackground.getWidth()) / 2;
        initialPosY = (Warfare.V_HEIGHT - loadingBarBackground.getHeight()) / 2;
        setPosition(initialPosX, initialPosY);
        setSize(loadingBarBackground.getWidth(), loadingBarBackground.getHeight());
        font = gen.generateFont(p);
    }

    public void dispose() {
        font.dispose();
    }

    public void hide() {
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                isHide = true;
                return true;
            }
        };
        SequenceAction fadeOutAction = new SequenceAction(Actions.fadeOut(2f),
                checkEndOfAction
        );
        this.addAction(fadeOutAction);
    }

    public boolean isHide() {
        return isHide;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(loadingBarBackground, initialPosX, initialPosY);
        batch.draw(loadingBarProgressStart, initialPosX, initialPosY);
        batch.draw(loadingBarProgressBody, initialPosX + loadingBarProgressStart.getRegionWidth(), initialPosY,
                loadingBarProgressBody.getRegionWidth() * assetManager.getProgress(),
                loadingBarProgressBody.getRegionHeight());
        batch.draw(loadingBarProgressEnd, initialPosX + loadingBarProgressStart.getRegionWidth() + loadingBarProgressBody.getRegionWidth() * assetManager.getProgress(), initialPosY);
        font.draw(batch, "ЗАГРУЗКА...", initialPosX,
                initialPosY + loadingBarBackground.getHeight() + 26);
        font.draw(batch, "" + (int) (assetManager.getProgress() * 100) + "%", initialPosX + 340,
                initialPosY + loadingBarBackground.getHeight() + 26);
    }
}

package com.timgapps.warfare.screens.map.gifts_panel.gui_elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Warfare;

public class BoxImage extends Actor {
    Animation openBoxAnimation;     // анимация отркырия ящика
    private boolean isStarted;
    private float stateTime = 0;
    private TextureRegion textureBox;

    public BoxImage() {
        textureBox = new TextureRegion(Warfare.atlas.findRegion("boxImage0"));
        setSize(textureBox.getRegionWidth(), textureBox.getRegionHeight());
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("boxImage" + i)));
        openBoxAnimation = new Animation(0.07f, frames);
        frames.clear();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (isStarted) {
            stateTime += Gdx.graphics.getDeltaTime();
            batch.draw((TextureRegion) openBoxAnimation.getKeyFrame(stateTime, false), getX(), getY());
        } else {
            batch.draw(textureBox, getX(), getY());
        }
    }

    public float getOpenAnimationDuration() {
        return openBoxAnimation.getAnimationDuration();
    }

    public void startAnimation() {
        isStarted = true;
    }

    public void closeBox() {
        stateTime = 0;
        isStarted = false;
    }
}
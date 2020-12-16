package com.timgapps.warfare.Units.GameUnits.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Warfare;

public class Explosion extends Actor {
    protected Animation explosionAnimation;
    protected float stateTime;
    protected boolean isEndAnimation = false;
    protected boolean isStarted = false;

    public Explosion() {
        setVisible(false);
        createAnimation();
        setSize(160, 140);
    }

    public void start() {
        setVisible(true);
        isStarted = true;
        isEndAnimation = false;

    }

    protected void createAnimation() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // получим кадры и добавим анимацию
        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("explosion" + i)));
        explosionAnimation = new Animation(0.11f, frames);
        frames.clear();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isStarted) {
            stateTime += Gdx.graphics.getDeltaTime();
            batch.draw((TextureRegion) explosionAnimation.getKeyFrame(stateTime, false), getX(), getY());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // возможно нуно исправить // 22.10.2020
        if (isEnd()) {
            this.remove();
        }
        ///////////////////////////////////////////////
        if (explosionAnimation.isAnimationFinished(stateTime)) {
            isEndAnimation = true;
        }
    }

    public boolean isEnd() {
        return isEndAnimation;
    }
}

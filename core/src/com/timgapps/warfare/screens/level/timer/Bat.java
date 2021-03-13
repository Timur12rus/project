package com.timgapps.warfare.screens.level.timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;

import java.util.Random;

public class Bat extends Actor {
    private LevelScreen levelScreen;
    private float stateTime;
    private Vector2 position;
    private double speed = 3.4f;
    private double speedV;
    private Animation flyAnimation;            // анимация для ходьбы

    public Bat(LevelScreen levelScreen, Vector2 position) {
        this.levelScreen = levelScreen;
        this.position = position;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(Warfare.atlas.findRegion("bat0")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("bat1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("bat2")));
        flyAnimation = new Animation(0.17f, frames);
        frames.clear();
        levelScreen.addChild(this, position.x, position.y);
        speedV = ((Math.random() * ((4f - 2f) + 1)) + 2f) * 0.1f;
        speed = ((Math.random() * ((4f - 2.8f) + 1)) + 2.8f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (levelScreen.getState() != LevelScreen.PAUSED) {
            if (position.x < 400) {
                speedV += delta * 2;
            }
            position.x -= speed;
            position.y += speedV;
//            position.y += 0.2;
            setPosition(position.x, position.y);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (levelScreen.getState() != LevelScreen.PAUSED) {
            stateTime += Gdx.graphics.getDeltaTime();
        }
        batch.setColor(1, 1, 1, 1f);
        batch.draw((TextureRegion) flyAnimation.getKeyFrame(stateTime, true), getX(), getY());
    }
}

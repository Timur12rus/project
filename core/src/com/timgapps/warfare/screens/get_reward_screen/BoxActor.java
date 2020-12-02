package com.timgapps.warfare.screens.get_reward_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Warfare;

public class BoxActor extends Actor {
    private Animation animation;
    private Vector2 size;
    private Array<TextureRegion> frames = new Array<TextureRegion>();
    private float stateTime;
    private boolean isStart;

    public BoxActor() {
        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("boxImage" + i)));
        animation = new Animation(0.06f, frames);
        setSize(new TextureRegion(Warfare.atlas.findRegion("boxImage0")).getRegionWidth(),
                new TextureRegion(Warfare.atlas.findRegion("boxImage0")).getRegionWidth());
        frames.clear();
    }

    public void startAnimation() {
        if (!isStart) {
            isStart = true;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isStart) {
            stateTime += Gdx.graphics.getDeltaTime();
        }
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw((TextureRegion) animation.getKeyFrame(stateTime, false), getX(), getY(), getWidth(), getHeight());
        batch.setColor(color.r, color.g, color.b, 1f);
    }
}

package com.timgapps.warfare.Units.GameUnits.Player.Bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;


// класс молния
public class Lightning extends Actor {
    private Vector2 position;
    private Animation animation;
    private Level level;
    protected float stateTime;

    public Lightning(Level level, Vector2 position, float deltaX) {
        this.level = level;
        this.position = position;
        createAnimation();
        setPosition(position.x - 18 + deltaX, position.y);
        level.addChild(this);
        this.debug();
    }

    private void createAnimation() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("tenorAnimation" + i)));
        animation = new Animation(0.11f, frames);
        frames.clear();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (level.getState() != Level.PAUSED) {
            stateTime += Gdx.graphics.getDeltaTime();
        }
        batch.draw((TextureRegion) animation.getKeyFrame(stateTime, false), getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (animation.isAnimationFinished(stateTime)) {
            this.remove();
        }
    }
}

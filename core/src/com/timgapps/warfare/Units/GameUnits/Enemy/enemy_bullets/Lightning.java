package com.timgapps.warfare.Units.GameUnits.Enemy.enemy_bullets;

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
//        for (int i = 0; i < 6; i++)
//            frames.add(new TextureRegion(Warfare.atlas.findRegion("tenorAnimation" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("tenorAnimation" + 0)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("tenorAnimation" + 1)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("tenorAnimation" + 2)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("tenorAnimation" + 2)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("tenorAnimation" + 3)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("tenorAnimation" + 4)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("tenorAnimation" + 4)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("tenorAnimation" + 5)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("tenorAnimation" + 5)));
        animation = new Animation(0.05f, frames);
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

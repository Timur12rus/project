package com.timgapps.warfare.Units.GameUnits.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class Fire extends Actor {

    private float x, y;
    private Level level;
    private Animation fireAnimation;
    private float stateTime;
    private boolean isStarted = false;

    public Fire(Level level) {
        this.level = level;
        createAnimation();
        setVisible(false);
    }

    /**
     * метод для создания анимации
     **/
    private void createAnimation() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // получим кадры и добавим анимацию
        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("fire" + i)));
        fireAnimation = new Animation(0.1f, frames);
        frames.clear();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isStarted) {
            if (level.getState() != Level.PAUSED) stateTime += Gdx.graphics.getDeltaTime();
            batch.draw((TextureRegion) fireAnimation.getKeyFrame(stateTime, true), getX(), getY());
        }
    }

    public void startFire() {
        if (!isStarted)
            isStarted = true;
        setVisible(true);

    }

    public boolean IsStarted() {
        return isStarted;
    }
}

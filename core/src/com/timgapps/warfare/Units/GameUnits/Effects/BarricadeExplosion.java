package com.timgapps.warfare.Units.GameUnits.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Units.GameUnits.Barricade;
import com.timgapps.warfare.Warfare;


public class BarricadeExplosion extends Actor {
    protected Animation explosionAnimation;
    protected float stateTime;
    protected boolean isStarted = false;
    private boolean isEnd = false;
    private Barricade barricade;

    public BarricadeExplosion(Barricade barricade) {
        this.barricade = barricade;
        setVisible(false);
        createAnimation();
    }

    public void start() {
        setVisible(true);
        isStarted = true;
    }

    protected void createAnimation() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // получим кадры и добавим анимацию
        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("explosion" + i)));
//            frames.add(new TextureRegion(Warfare.atlas.findRegion("barricadeExplosion" + i)));
        explosionAnimation = new Animation(0.08f, frames);
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
        // если анимация взрыва завершена и баррикада не разрушена, вызываем метод разрушения баррикады
        if (explosionAnimation.isAnimationFinished(stateTime) && (!isEnd)) {
            isEnd = true;
            barricade.destroy();
        }
    }

    public boolean isEnd() {
        return isEnd;
    }

    public boolean isStarted() {
        return isStarted;
    }
}


















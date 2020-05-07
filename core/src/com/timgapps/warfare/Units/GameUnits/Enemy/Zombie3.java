package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class Zombie3 extends Zombie {

    //    public enum State {WALKING, ATTACK, STAY, DIE, RUN, HART}
    protected final float VELOCITY = -0.25f;
    private final int STAY_COUNT = 0;
    private int walkAnimationIsFinishedCount = 0;

    public Zombie3(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        createAnimations();     // создадим анимации для различных состояний персонажа
        level.addChild(this, x, y);
        stateTime = 0;
        currentState = State.WALKING;
        level.arrayActors.add(this);
        stayCount = STAY_COUNT;
        deltaX = 78;        // смещение рисоания анимации относительно позиции актёра по оси Х
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    protected void createAnimations() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie3Walk" + i)));
        walkAnimation = new Animation(0.18f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию атаки персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie3Attack" + i)));
        attackAnimation = new Animation(0.12f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию атаки персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie3Die" + i)));
        dieAnimation = new Animation(0.12f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию стоянки персонажа
        for (int j = 0; j < 2; j++) {
            for (int i = 3; i > 0; i--)
                frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie3Stay" + i)));
        }
        stayAnimation = new Animation(0.35f, frames);
        frames.clear();
    }
}
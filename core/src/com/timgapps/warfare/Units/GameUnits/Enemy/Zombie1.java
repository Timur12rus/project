package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class Zombie1 extends Zombie {

//    public enum State {WALKING, ATTACK, STAY, DIE, RUN, HART}

    protected final float VELOCITY = -0.25f;
    private final  int STAY_COUNT = 1;

    public Zombie1(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        createAnimations();     // создадим анимации для различных состояний персонажа
        level.addChild(this, x, y);
        stateTime = 0;
        currentState = State.WALKING;
        level.arrayActors.add(this);
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
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Walk" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Walk1")));

        walkAnimation = new Animation(0.12f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию атаки персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Attack" + i)));
        attackAnimation = new Animation(0.12f, frames);
        frames.clear();


        //  получим кадры и добавим в анимацию атаки персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Die" + i)));
        dieAnimation = new Animation(0.12f, frames);
        frames.clear();


        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Hart" + i)));
        hartAnimation = new Animation(0.05f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию стоянки персонажа
        for (int j = 0; j < 2; j++) {
            for (int i = 4; i > 0; i--)
                frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Stay" + i)));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Stay1")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Stay2")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Stay3")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Stay4")));
        }
        stayAnimation = new Animation(0.35f, frames);
        frames.clear();
    }
}

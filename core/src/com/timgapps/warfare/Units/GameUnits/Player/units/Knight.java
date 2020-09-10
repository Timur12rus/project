package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class Knight extends Gnome {
    public Knight(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        velocity = 0.4f;
        debug();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
//        if (level.getState() == Level.PLAY) {
//        stateTime += Gdx.graphics.getDeltaTime();
//        }
//        batch.setColor(1, 1, 1, 1);

//        if (isDraw) {
        if (currentState == State.WALKING) {
            batch.draw((TextureRegion) walkAnimation.getKeyFrame(stateTime, true), getX() - 142, getY() - 24);
        }

        if (currentState == State.ATTACK) {
            batch.draw((TextureRegion) attackAnimation.getKeyFrame(stateTime, false), getX() - 142, getY() - 22);
        }

        if (currentState == State.STAY) {
            batch.draw((TextureRegion) stayAnimation.getKeyFrame(stateTime, false), getX() - 142, getY() - 24);
        }

        if (currentState == State.RUN) {
            batch.draw((TextureRegion) runAnimation.getKeyFrame(stateTime, true), getX() - 142, getY() - 24);
        }

        if (currentState == State.DIE) {
            batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, false), getX() - 142, getY() - 24);
        }

        if (isDrawHealthBar)
            drawHealthBar(batch, -84, getHeight());
    }

    @Override
    protected void createAnimations() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // получим кадры и добавим в анимацию ходьбы персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("knightWalk" + i)));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("knightWalk2")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("knightWalk1")));
//        for (int i = 4; i < 0; i--)
//            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeWalk" + i)));
        walkAnimation = new Animation(0.1f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию атаки персонажа
        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("knightAttack" + i)));
        attackAnimation = new Animation(0.1f, frames);
//        attackAnimation = new Animation(0.06f, frames);
//        attackAnimation = new Animation(0.12f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию стоянки персонажа
        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("knightStay" + i)));
//        for (int i = 4; i < 1; i--)
//            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeStay" + i)));
        stayAnimation = new Animation(0.25f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию бега персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("knightWalk" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("knightWalk2")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("knightWalk1")));
        runAnimation = new Animation(0.15f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию уничтожения персонажа
        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("knightDie" + i)));
        dieAnimation = new Animation(0.1f, frames);
        frames.clear();
    }
}

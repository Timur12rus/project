package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Warfare;

public class EnemyUnitView extends GameUnitView {
    private EnemyUnitController controller;
    private EnemyUnitModel model;
    private float deltaX, deltaY;       // значение в px на сколько нужно сдвигать изобажение юнита относительно его тела, при отрисовке
    private float healthBarDeltaX;
    private float healthBarDeltaY;
    private State currentState;
//    private ParticleEffect bloodSpray;      // эффект брызги

    public EnemyUnitView(Level level, EnemyUnitModel model, EnemyUnitController controller) {
        super(level, model, controller);
        this.model = model;
        this.controller = controller;
        deltaX = model.getUnitData().getDeltaX();
        deltaY = model.getUnitData().getDeltaY();
        healthBarDeltaX = model.getUnitData().getBarDeltaX();
        healthBarDeltaY = model.getUnitData().getBarDeltaY();
        createAnimations();
        currentState = State.STAY;
        setSize(Warfare.atlas.findRegion(model.getUnitData().getName().toLowerCase() + "Stay1").getRegionWidth(),
                Warfare.atlas.findRegion(model.getUnitData().getName().toLowerCase() + "Stay1").getRegionHeight());
//        bloodSpray = new ParticleEffect();
//        bloodSpray.load(Gdx.files.internal("effects/bloodSpray.paty"), Gdx.files.internal("effects/")); //file);
    }

    public String getName() {
        return model.getName();
    }

    // метод возвращает позицию тела юнита в пикселях
//    public Vector2 getPosition() {
//        return model.getPosition();
//    }

    public Vector2 getBodySize() {
        return model.getBodySize();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (currentState == State.WALKING) {
            batch.draw((TextureRegion) walkAnimation.getKeyFrame(stateTime, true), getX() + deltaX, getY() + deltaY);
        }
        if (currentState == State.ATTACK) {
            batch.draw((TextureRegion) attackAnimation.getKeyFrame(stateTime, false), getX() + deltaX, getY() + deltaY);
        }
        if (currentState == State.STAY) {
            batch.draw((TextureRegion) stayAnimation.getKeyFrame(stateTime, false), getX() + deltaX, getY() + deltaY);
        }
        if (currentState == State.RUN) {
            batch.draw((TextureRegion) runAnimation.getKeyFrame(stateTime, true), getX() + deltaX, getY() + deltaY);
        }
        if (currentState == State.DIE) {
            batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, false), getX() + deltaX, getY() + deltaY);
        }

        if (model.isDamaged()) {
//            System.out.println("Draw Blood Spray");
            model.getBloodSpray().draw(batch);
        }

        if (isDrawHealthBar) {
            healthBar.drawHealthBar(batch, healthBarDeltaX, getHeight() + healthBarDeltaY, model.getHealth());
        }


    }


    private void createAnimations() {
        String name = model.getUnitData().getUnitId().toString().toLowerCase();
        System.out.println("Name = " + name);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk1")));
        walkAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack" + i)));
        attackAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay" + i)));
        stayAnimation = new Animation(0.18f, frames);
        frames.clear();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run1")));
        runAnimation = new Animation(0.12f, frames);
        frames.clear();
    }
}


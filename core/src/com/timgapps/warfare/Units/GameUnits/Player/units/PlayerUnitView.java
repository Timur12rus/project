package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Utils.Setting;
import com.timgapps.warfare.Warfare;

public class PlayerUnitView extends GameUnitView {
    private PlayerUnitModel model;
    private PlayerUnitController controller;
    private Level level;
    private float deltaX, deltaY;       // значение в px на сколько нужно сдвигать изобажение юнита относительно его тела, при отрисовке
    private float getHealthBarDeltaX;
    private float healthBarDeltaX;
    private float healthBarDeltaY;

    public PlayerUnitView(Level level, PlayerUnitModel model, PlayerUnitController controller) {
        super(level, model, controller);
        this.model = model;
        this.controller = controller;
        deltaX = model.getUnitData().getDeltaX();
        deltaY = model.getUnitData().getDeltaY();
        healthBarDeltaX = model.getUnitData().getBarDeltaX();
        healthBarDeltaY = model.getUnitData().getBarDeltaY();
        createAnimations();
        setSize(Warfare.atlas.findRegion(model.getPlayerUnitData().getName().toLowerCase() + "Stay1").getRegionWidth(),
                Warfare.atlas.findRegion(model.getPlayerUnitData().getName().toLowerCase() + "Stay1").getRegionHeight());
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
        if (isDrawHealthBar) {
            healthBar.drawHealthBar(batch, healthBarDeltaX, getHeight() + healthBarDeltaY, model.getHealth());
        }
        batch.end();
        if (Setting.DEBUG_GAME) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            float x = getX() + model.getBodySize().x;
            float y = getY() + model.getBodySize().y / 2;
            Vector2 v1 = new Vector2(x, y);
            Vector2 vectorUp = new Vector2(v1);
            Vector2 vectorDown = new Vector2(v1);
            vectorUp.add(480, 2000);
            vectorDown.add(480, -2000);
            shapeRenderer.triangle(v1.x, v1.y, vectorDown.x, vectorDown.y, vectorUp.x, vectorUp.y);
            shapeRenderer.end();
        }
        batch.begin();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        currentState = model.getCurrentState();
        // если юнит в состоянии атакует цель(isAttack = true), но в д
        if (model.isMoveToTarget() == true) {
            if (currentState != State.RUN) {
                currentState = State.RUN;
                resetStateTime();
            }
        } else if (model.isAttack() || model.isAttackBarricade()) {
            if (model.isStay() == false) {
                if (currentState != State.ATTACK) {
                    currentState = State.ATTACK;
                    resetStateTime();
                } else {
                    if (attackAnimation.isAnimationFinished(stateTime)) {
                        if (model.isAttack()) {
                            controller.hit();
                        } else if (model.isAttackBarricade()) {
                            controller.hitBarricade();
                        }
                        currentState = State.STAY;
                        model.setIsStay(true);
                        resetStateTime();
                    }
                }
            } else {
                if (stayAnimation.isAnimationFinished(stateTime)) {
                    model.setIsStay(false);
                }
            }
        } else {
            if (currentState != State.RUN) {
                currentState = State.RUN;
                resetStateTime();
            }
            model.setIsMove(true);
        }
        model.setCurrentState(currentState);
    }

    private void createAnimations() {
        String name = model.getPlayerUnitData().getUnitId().toString().toLowerCase();
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

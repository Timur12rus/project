package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Warfare;

import java.util.Random;

public class EnemyUnitView extends GameUnitView {
    private EnemyUnitController controller;
    private EnemyUnitModel model;
    private float deltaX, deltaY;       // значение в px на сколько нужно сдвигать изобажение юнита относительно его тела, при отрисовке
    private float healthBarDeltaX;
    private float healthBarDeltaY;
    private State currentState;
    SequenceAction fadeOutAction;

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

        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
//                remove();
                return true;
            }
        };

        fadeOutAction = new SequenceAction(Actions.fadeOut(1.5f),
                checkEndOfAction
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        currentState = model.getCurrentState();
        if (model.isDestroyed()) {
            if (currentState != State.DIE) {
                currentState = State.DIE;
                System.out.println("!DIE");
                resetStateTime();
            } else {
                System.out.println("Else");
                if (dieAnimation.isAnimationFinished(stateTime)) {
                    level.removeEnemyUnitFromArray(model);
                    model.disposeBloodSpray();
                    this.remove();
//                    addAction(fadeOutAction);
                }
            }
        } else {
            if (model.isMove()) {
                if (!model.isStay()) {
                    if (currentState != State.WALKING) {
                        currentState = State.WALKING;
                        resetStateTime();
                    } else {
                        if (walkAnimation.isAnimationFinished(stateTime)) {
                            Random random = new Random();
                            if (random.nextBoolean()) {
                                currentState = State.STAY;
                                model.setIsStay(true);
                                resetStateTime();
                            } else {
                                currentState = State.WALKING;
                                model.setIsStay(false);
                                resetStateTime();
                            }
                        }
                    }
                } else if (currentState != State.STAY) {
                    currentState = State.STAY;
                    resetStateTime();
                } else {
                    if (stayAnimation.isAnimationFinished(stateTime)) {
                        model.setIsStay(false);
                    }
                }
//                else {
//                    if (stayAnimation.isAnimationFinished(stateTime)) {
//                        model.setIsStay(false);
//                    }
//                }
            } else if (model.isAttack() || model.isAttackTower()) {
                if (!model.isStay()) {
                    if (currentState != State.ATTACK) {
                        currentState = State.ATTACK;
                        resetStateTime();
                    } else {
                        if (attackAnimation.isAnimationFinished(stateTime)) {
                            if (model.isAttack()) {
                                controller.hit();
                            } else if (model.isAttackTower()) {
                                controller.hitTower();
                            }
                            currentState = State.STAY;
                            model.setIsStay(true);
                            resetStateTime();
                        }
                    }
                } else if (currentState != State.STAY) {
                    currentState = State.STAY;
                    resetStateTime();
                } else {
                    if (stayAnimation.isAnimationFinished(stateTime)) {
                        model.setIsStay(false);
                    }
                }
//                else {
//                    if (stayAnimation.isAnimationFinished(stateTime)) {
//                        model.setIsStay(false);
//                    }
//                }

            } else if (model.isStay()) {
                if (currentState != State.STAY) {
                    currentState = State.STAY;
                    resetStateTime();
                } else {
                    if (stayAnimation.isAnimationFinished(stateTime)) {
                        model.setIsStay(false);
                        resetStateTime();
                    }
                }
            } else {
                if (currentState != State.WALKING) {
                    currentState = State.WALKING;
                    resetStateTime();
                }
                model.setIsMove(true);
            }
        }
        model.setCurrentState(currentState);
    }

    public String getName() {
        return model.getName();
    }

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
        walkAnimation = new Animation(0.15f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack" + i)));
        attackAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
        stayAnimation = new Animation(0.18f, frames);
        frames.clear();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run1")));
        runAnimation = new Animation(0.12f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Die" + i)));
        dieAnimation = new Animation(0.12f, frames);
        frames.clear();
    }
}


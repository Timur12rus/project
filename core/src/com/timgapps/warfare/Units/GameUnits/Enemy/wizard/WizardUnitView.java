package com.timgapps.warfare.Units.GameUnits.Enemy.wizard;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitView;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Warfare;

public class WizardUnitView extends EnemyUnitView {
    private WizardController controller;
    private float waitTime = 200;

    public WizardUnitView(Level level, EnemyUnitModel model, WizardController controller) {
        super(level, model, controller);
        this.controller = controller;
        createAnimations();
        currentState = State.STAY;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (level.getState() != Level.PAUSED) {
            waitTime--;
        }
        currentState = model.getCurrentState();
        if (model.isDestroyed()) {
            if (currentState != GameUnitView.State.DIE) {
                currentState = GameUnitView.State.DIE;
                System.out.println("!DIE");
                resetStateTime();
            } else {
//                System.out.println("Else");
                if (dieAnimation.isAnimationFinished(stateTime)) {
                    level.removeEnemyUnitFromArray(model);
                    model.disposeBloodSpray();
//                    this.remove();
                    if (!isAddAction) {
                        this.addAction(fadeOutAction);
                        isAddAction = true;
                    }
                }
            }
        } else {
            if (model.isMove()) {
                if (!model.isStay()) {
                    if (currentState != State.WALKING) {
                        currentState = State.WALKING;
                        resetStateTime();
                    }
                } else if (currentState != State.STAY) {
                    currentState = State.STAY;
                    resetStateTime();
                }
//            }
//            else {
//                if (stayAnimation.isAnimationFinished(stateTime)) {
//                    model.setIsStay(false);
//                }
//            }
            } else if (model.isAttack() || model.isAttackTower()) {
                if (!model.isStay()) {
                    if (currentState != GameUnitView.State.ATTACK) {
                        currentState = GameUnitView.State.ATTACK;
                        resetStateTime();
                    } else {
                        if (attackAnimation.isAnimationFinished(stateTime)) {
                            if (model.isAttack()) {
                                controller.hit();
                            } else if (model.isAttackTower()) {
//                                controller.hitTower();
                            }
                            currentState = State.STAY;
                            model.setIsStay(true);
                            resetStateTime();
                            waitTime = 200;
                        }
                    }
                } else if (currentState != State.STAY) {
                    currentState = GameUnitView.State.STAY;
                    resetStateTime();
                } else {
                    if (stayAnimation.isAnimationFinished(stateTime)) {
                        model.setIsStay(false);
                    }
                }
            } else if (model.isStay()) {
                if (currentState != State.STAY) {
                    currentState = State.STAY;
                    resetStateTime();
                    waitTime = 200;
                } else {
                    if (stayAnimation.isAnimationFinished(stateTime)) {
                        model.setIsStay(false);
                        resetStateTime();
                    }
                }
            } else {
                if (currentState != GameUnitView.State.WALKING) {
                    currentState = GameUnitView.State.WALKING;
                    resetStateTime();
                }
                model.setIsMove(true);
            }
        }
        model.setCurrentState(currentState);
    }

    protected void createAnimations() {
        String name = model.getUnitData().getUnitId().toString().toLowerCase();
        System.out.println("Name = " + name);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk2")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk1")));
        walkAnimation = new Animation(0.12f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack" + i)));
        attackAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay" + i)));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay2")));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay2")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay0")));
        stayAnimation = new Animation(1, frames);
//        stayAnimation = new Animation(0.18f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Die" + i)));
        dieAnimation = new Animation(0.12f, frames);
        frames.clear();
    }

}
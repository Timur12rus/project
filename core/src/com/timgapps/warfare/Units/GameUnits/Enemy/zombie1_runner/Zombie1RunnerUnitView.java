package com.timgapps.warfare.Units.GameUnits.Enemy.zombie1_runner;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitView;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;

import java.util.Random;


public class Zombie1RunnerUnitView extends EnemyUnitView {
    protected Zombie1RunnerController controller;

    public Zombie1RunnerUnitView(LevelScreen levelScreen, EnemyUnitModel model, Zombie1RunnerController controller) {
        super(levelScreen, model, controller);
        this.controller = controller;
        createAnimations();
        currentState = State.STAY;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        currentState = model.getCurrentState();
        if (model.isDestroyed()) {
            if (currentState != State.DIE) {
                currentState = State.DIE;
                resetStateTime();
            } else {
                if (dieAnimation.isAnimationFinished(stateTime)) {
                    if (!isAddAction) {
                        this.addAction(fadeOutAction);
                        isAddAction = true;
                    }
                }
            }
        } else {
            if (model.isMove()) {
                if (currentState != State.RUN) {
                    currentState = State.RUN;
                    resetStateTime();
                }
            } else
                // если юнит в состоянии атакует цель(isAttack = true), но в д
                if (model.isMoveToTarget()) {
                    if (!model.isStay()) {
                        if (currentState != State.RUN) {
                            currentState = State.RUN;
                            resetStateTime();
                        }
//                    else {
//                        if (currentState == State.RUN && runAnimation.isAnimationFinished(stateTime)) {
//                            currentState = State.STAY;
//                            model.setIsStay(true);
//                            resetStateTime();
//
//
////                            Random random = new Random();
////                            if (random.nextBoolean()) {
////                                currentState = State.STAY;
////                                model.setIsStay(true);
////                                resetStateTime();
////                            } else {
////                                currentState = State.RUN;
////                                model.setIsStay(false);
////                                resetStateTime();
////                            }
//                        }
//                    }
                    } else if (currentState != State.STAY) {
                        currentState = State.STAY;
//                    resetStateTime();
                    } else {
                        if (stayAnimation.isAnimationFinished(stateTime)) {
                            model.setIsStay(false);
                        }
                    }
                } else if (model.isAttack() || model.isAttackTower()) {
                    if (!model.isStay()) {              // TODO нжуно изменить, чтобы если currentState == STAY, то ждем
//                    if (currentState != State.STAY) {
//                        currentState = State.STAY;
//                        model.setIsStay(true);
//                        resetStateTime();
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
                            resetStateTime();
                        }
                    }
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
                    if (currentState != State.RUN) {
                        currentState = State.RUN;
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
        name = "zombie1";
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk2")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk1")));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk0")));
        walkAnimation = new Animation(0.12f, frames);
        frames.clear();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run2")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run0")));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk0")));
        runAnimation = new Animation(0.08f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack" + i)));
        attackAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay2")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay0")));
        stayAnimation = new Animation(0.18f, frames);
        frames.clear();


        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Die" + i)));
        dieAnimation = new Animation(0.12f, frames);
        frames.clear();
    }
}

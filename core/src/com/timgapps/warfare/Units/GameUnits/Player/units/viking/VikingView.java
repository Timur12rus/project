package com.timgapps.warfare.Units.GameUnits.Player.units.viking;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitController;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitView;
import com.timgapps.warfare.Warfare;

public class VikingView extends PlayerUnitView {
    private PlayerUnitController controller;
    private boolean firstAttackIsEnd = false;

    public VikingView(LevelScreen levelScreen, PlayerUnitModel model, PlayerUnitController controller) {
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
            // если юнит в состоянии атакует цель(isAttack = true), но в д
            if (model.isMoveToTarget() == true) {
//                if (!model.isHited()) {
                if (currentState != State.RUN) {
                    currentState = State.RUN;
                    firstAttackIsEnd = false;
                    resetStateTime();
                }
//                } else {
//                    if (currentState != State.WALKING) {
//                        currentState = State.WALKING;
//                        firstAttackIsEnd = false;
//                        resetStateTime();
//                    }
//                }
            } else if (model.isAttack() || model.isAttackBarricade()) {
                if (model.isStay() == false) {
                    if (currentState != State.ATTACK && !firstAttackIsEnd) {
                        currentState = State.ATTACK;
                        resetStateTime();
                    } else {
                        if (!firstAttackIsEnd) {
                            if (attackAnimation.isAnimationFinished(stateTime)) {
                                if (model.isAttack()) {
                                    controller.hit();
                                } else if (model.isAttackBarricade()) {
                                    controller.hitBarricade();
                                }
                                firstAttackIsEnd = true;
                                resetStateTime();
                            }
                        } else {
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
                                    firstAttackIsEnd = false;
                                    currentState = State.STAY;
                                    model.setIsStay(true);
                                    resetStateTime();
                                }
                            }
                        }
                    }
                } else {
                    if (stayAnimation.isAnimationFinished(stateTime)) {
                        model.setIsStay(false);
                        firstAttackIsEnd = false;
                    }
                }
            } else {
//                if (!model.isHited()) {
                if (currentState != State.RUN) {
                    currentState = State.RUN;
                    firstAttackIsEnd = false;
                    resetStateTime();
                }
//                } else {
//                    if (currentState != State.WALKING) {
//                        currentState = State.WALKING;
//                        resetStateTime();
//                    }
//                }
                model.setIsMove(true);
            }
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
        walkAnimation = new Animation(0.12f, frames);
        frames.clear();

            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack0")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack0")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack1")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack1")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack2")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack3")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack4")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack5")));
        attackAnimation = new Animation(0.05f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Die" + i)));
        dieAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay" + i)));
        stayAnimation = new Animation(0.18f, frames);
        frames.clear();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run1")));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run0")));
        runAnimation = new Animation(0.11f, frames);
//        runAnimation = new Animation(0.13f, frames);
        frames.clear();
    }
}

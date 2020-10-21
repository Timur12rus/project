package com.timgapps.warfare.Units.GameUnits.Player.units.viking;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitController;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitView;
import com.timgapps.warfare.Warfare;

public class VikingView extends PlayerUnitView {
    private PlayerUnitController controller;

    public VikingView(Level level, PlayerUnitModel model, PlayerUnitController controller) {
        super(level, model, controller);
        this.controller = controller;
        createAnimations();
        currentState = GameUnitView.State.STAY;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        currentState = model.getCurrentState();
        if (model.isDestroyed()) {
            if (currentState != GameUnitView.State.DIE) {
                currentState = GameUnitView.State.DIE;
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
                if (!model.isHited()) {
                    if (currentState != GameUnitView.State.RUN) {
                        currentState = GameUnitView.State.RUN;
                        resetStateTime();
                    }
                } else {
                    if (currentState != GameUnitView.State.WALKING) {
                        currentState = GameUnitView.State.WALKING;
                        resetStateTime();
                    }
                }
            } else if (model.isAttack() || model.isAttackBarricade()) {
                if (model.isStay() == false) {
                    if (currentState != GameUnitView.State.ATTACK) {
                        currentState = GameUnitView.State.ATTACK;
                        resetStateTime();
                    } else {
                        if (attackAnimation.isAnimationFinished(stateTime)) {
                            if (model.isAttack()) {
                                controller.hit();
                            } else if (model.isAttackBarricade()) {
                                controller.hitBarricade();
                            }
                            currentState = GameUnitView.State.STAY;
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
                if (!model.isHited()) {
                    if (currentState != GameUnitView.State.RUN) {
                        currentState = GameUnitView.State.RUN;
                        resetStateTime();
                    }
                } else {
                    if (currentState != GameUnitView.State.WALKING) {
                        currentState = GameUnitView.State.WALKING;
                        resetStateTime();
                    }
                }
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

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack" + i)));
        attackAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Die" + i)));
        dieAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay" + i)));
        stayAnimation = new Animation(0.18f, frames);
        frames.clear();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run1")));
        runAnimation = new Animation(0.11f, frames);
//        runAnimation = new Animation(0.13f, frames);
        frames.clear();
    }
}

package com.timgapps.warfare.Units.GameUnits.Enemy.goblin;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitView;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;

public class GoblinUnitView extends EnemyUnitView {
    private GoblinController controller;

    public GoblinUnitView(LevelScreen levelScreen, EnemyUnitModel model, GoblinController controller) {
        super(levelScreen, model, controller);
        this.controller = controller;
        createAnimations();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        currentState = model.getCurrentState();
        if (model.isDestroyed()) {
            if (currentState != GameUnitView.State.DIE) {
                currentState = GameUnitView.State.DIE;
                System.out.println("!DIE");
                resetStateTime();
            } else {
//                System.out.println("Else");
                if (dieAnimation.isAnimationFinished(stateTime)) {
                    levelScreen.removeEnemyUnitFromArray(model);
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
                    if (currentState != GameUnitView.State.WALKING) {
                        currentState = GameUnitView.State.WALKING;
                        resetStateTime();
                    }
                } else if (currentState != GameUnitView.State.STAY) {
                    currentState = GameUnitView.State.STAY;
                    resetStateTime();
                } else {
                    if (stayAnimation.isAnimationFinished(stateTime)) {
                        model.setIsStay(false);
                    }
                }
            }
//            else if (model.isAttack() || model.isAttackTower()) {
//                if (!model.isStay()) {
//                    if (currentState != GameUnitView.State.ATTACK) {
//                        currentState = GameUnitView.State.ATTACK;
//                        resetStateTime();
//                    } else {
//                        if (attackAnimation.isAnimationFinished(stateTime)) {
//                            if (model.isAttack()) {
//                                controller.hit();
//                            } else if (model.isAttackTower()) {
//                                controller.hitTower();
//                            }
//                            currentState = GameUnitView.State.STAY;
//                            model.setIsStay(true);
//                            resetStateTime();
//                        }
//                    }
//                } else if (currentState != GameUnitView.State.STAY) {
//                    currentState = GameUnitView.State.STAY;
//                    resetStateTime();
//                } else {
//                    if (stayAnimation.isAnimationFinished(stateTime)) {
//                        model.setIsStay(false);
//                    }
//                }
//            }
            else if (model.isStay()) {
                if (currentState != GameUnitView.State.STAY) {
                    currentState = GameUnitView.State.STAY;
                    resetStateTime();
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
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk0")));
        walkAnimation = new Animation(0.09f, frames);
        frames.clear();

        System.out.println(name + "Die");
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Die" + i)));
        dieAnimation = new Animation(0.12f, frames);
        frames.clear();
    }
}

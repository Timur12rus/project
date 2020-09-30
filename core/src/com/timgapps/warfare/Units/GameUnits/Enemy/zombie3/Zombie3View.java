package com.timgapps.warfare.Units.GameUnits.Enemy.zombie3;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie2.Zombie2Controller;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie2.Zombie2View;
import com.timgapps.warfare.Warfare;

import java.util.Random;

public class Zombie3View extends Zombie2View {

    public Zombie3View(Level level, EnemyUnitModel model, Zombie2Controller controller) {
        super(level, model, controller);
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
//                    else {
//                        if (walkAnimation.isAnimationFinished(stateTime)) {
//                            Random random = new Random();
//                            if (random.nextBoolean()) {
//                                currentState = State.STAY;
//                                model.setIsStay(true);
//                                resetStateTime();
//                            } else {
//                                currentState = State.WALKING;
//                                model.setIsStay(false);
//                                resetStateTime();
//                            }
//                        }
//                    }
                } else if (currentState != State.STAY) {
                    currentState = State.STAY;
                    resetStateTime();
                } else {
                    if (stayAnimation.isAnimationFinished(stateTime)) {
                        model.setIsStay(false);
                    }
                }
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

//}

    @Override
    protected void createAnimations() {
        String name = model.getUnitData().getUnitId().toString().toLowerCase();
        System.out.println("Name = " + name);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run2")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Run0")));
        walkAnimation = new Animation(0.09f, frames);
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

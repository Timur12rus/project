package com.timgapps.warfare.Units.GameUnits.Enemy.ork1;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitView;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;

import java.util.Random;

public class Ork1UnitView extends EnemyUnitView {
    protected Ork1Controller controller;
    protected Random random;
    public float STAY_COUNT = 2;
    protected float WALK_COUNT = 3;
    protected float WAIT_COUNT = 0.8f;     // счетчик ожидания, когда юнит атаковал -> стоит и ждет
    private float stayCount;
    private float waitCount;        // счетчик ожидания, когда юнит атаковал -> стоит и ждет
    private int walkCount;

    //public class Ork1UnitView extends Zombie2UnitView {
    //public class Ork1UnitView extends Skeleton1UnitView {
    public Ork1UnitView(LevelScreen levelScreen, EnemyUnitModel model, Ork1Controller controller) {
//    public Ork1UnitView(LevelScreen levelScreen, EnemyUnitModel model, Skeleton1Controller controller) {
        super(levelScreen, model, controller);
        this.controller = controller;
        random = new Random();
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
                    if (currentState != State.WALKING) {
                        currentState = State.WALKING;
                        resetStateTime();
                    } else {
                        if (walkAnimation.isAnimationFinished(stateTime)) {
                            // будет ли дивигаться вперед юнит или же будет стоять
                            if (walkCount < WALK_COUNT) {
                                walkCount++;
                                currentState = State.WALKING;
                                model.setIsStay(false);
                                resetStateTime();
                            } else if (random.nextBoolean()) {
                                walkCount = 0;
                                currentState = State.STAY;
                                model.setIsStay(true);
                                resetStateTime();
                            } else {
                                walkCount = 0;
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
                        stayCount += delta;
                        if (stayCount > STAY_COUNT) {
                            model.setIsStay(false);
                            stayCount = 0;
                        }
//                        else {
//                            model.setIsStay(true);
//                        }
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
                        waitCount += delta;
                        if (waitCount > WAIT_COUNT) {
                            waitCount = 0;
                            model.setIsStay(false);
                        }
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

    protected void createAnimations() {
        String name = model.getUnitData().getUnitId().toString().toLowerCase();
        System.out.println("Name = " + name);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk2")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk0")));
        walkAnimation = new Animation(0.09f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack" + i)));
        attackAnimation = new Animation(0.07f, frames);
        frames.clear();

//        for (int i = 0; i < 4; i++)
//            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay" + i)));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay2")));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay0")));

        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay2")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay0")));

//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay0")));
        stayAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Die" + i)));
        dieAnimation = new Animation(0.1f, frames);
        frames.clear();
    }
}

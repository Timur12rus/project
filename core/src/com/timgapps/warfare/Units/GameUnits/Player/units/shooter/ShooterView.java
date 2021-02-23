package com.timgapps.warfare.Units.GameUnits.Player.units.shooter;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitController;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitView;
import com.timgapps.warfare.Warfare;

public class ShooterView extends PlayerUnitView {
    private PlayerUnitController controller;
    private int numOfThrowsArrow = 0;
    private final int NUM_ARROWS = 2;
    private boolean firstAttackIsEnd = false;

    public ShooterView(LevelScreen levelScreen, PlayerUnitModel model, PlayerUnitController controller) {
        super(levelScreen, model, controller);
        this.controller = controller;
        createAnimations();
        currentState = State.STAY;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        currentState = model.getCurrentState();
        if (model.isDestroyed()) {                      // если юнит уничтожен, включаем анимацию уничтожения
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
            // Ai юнита
            if (model.isMoveToTarget() == true) {           // если юнит движется к цели
                // если юнит в состоянии, что видид баррикаду
                if (model.isBarricadeDetected()) {
                    if (!model.isHaveVerticalDirection()) {
                        if (currentState != State.STAY) {
                            currentState = State.STAY;
                            resetStateTime();
                        } else if (stayAnimation.isAnimationFinished(stateTime)) {
                            resetStateTime();
                        }
                    } else if (currentState != State.WALKING) {
                        currentState = State.WALKING;
                        resetStateTime();
                    }
                } else if (currentState != State.WALKING) {
                    currentState = State.WALKING;
                    resetStateTime();
                }

                /** АТАКА **/
            } else if (model.isAttack() || model.isShoot()) {       // если юнит в состоянии "атакует" или "стреляет"
                if (model.isStay() == false) {                      // если юнит не в состоянии "стоит"
//                    if (currentState != State.ATTACK) {
                    System.out.println("secondAttackAnimation is finished = " + secondAttackAnimation.isAnimationFinished(stateTime));
                    if (currentState != State.ATTACK && !firstAttackIsEnd) {
                        currentState = State.ATTACK;
                        System.out.println("Set state = Attack!!!");
                        resetStateTime();
                    } else {
                        System.out.println("secondAttackAnimation is finished = " + secondAttackAnimation.isAnimationFinished(stateTime));
                        // если кадры анимации стрельбы == 4 и юнит не выпустил стрелу, выпускаем стрелу
                        if (attackAnimation.getKeyFrameIndex(stateTime) == 3 && !model.isShooted() && model.isShoot()) {
                            controller.throwBullet();
                            model.setIsShooted(true);
                            System.out.println("KeyFrame = 3");
                            firstAttackIsEnd = true;
                        }
                        if (attackAnimation.isAnimationFinished(stateTime)) {
                            System.out.println("Current State = " + currentState);
//                            firstAttackIsEnd = true;
                            if (model.isAttack()) {
                                controller.hit();
                            }
                            if (model.isShoot()) {
                                model.setIsShooted(false);
                            }
//                            currentState = State.STAY;
//                            System.out.println("Set State = STAY");
//                            model.setIsStay(true);
//                            resetStateTime();
                            if (currentState != State.ATTACK2) {
                                currentState = State.ATTACK2;
                                System.out.println("Set State = ATTACK2");
                                resetStateTime();
                            } else {
                                if (secondAttackAnimation.getKeyFrameIndex(stateTime) == 3 && !model.isShooted() && model.isShoot()) {
                                    controller.throwBullet();
                                    model.setIsShooted(true);
                                    System.out.println("State = ATTACK2");
                                }
                            }
                        } else {
                            if (currentState == State.ATTACK2 && secondAttackAnimation.isAnimationFinished(stateTime)) {
                                if (model.isAttack()) {
                                    controller.hit();
                                }
                                if (model.isShooted()) {
                                    model.setIsShooted(false);
                                }
//                                numOfThrowsArrow = 0;
                                firstAttackIsEnd = false;
                                currentState = State.STAY;
                                System.out.println("Set State = STAY!!!!!");
                                model.setIsStay(true);
                                resetStateTime();
                            }
                        }
//                        }
                    }
                } else {
                    if (stayAnimation.isAnimationFinished(stateTime)) {
                        model.setIsStay(false);
                    }
                }
            } else if (model.isBarricadeDetected()) {
                if (currentState != State.STAY) {
                    currentState = State.STAY;
                    resetStateTime();
                } else if (stayAnimation.isAnimationFinished(stateTime)) {
                    resetStateTime();
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


    private void createAnimations() {
        String name = model.getPlayerUnitData().getUnitId().toString().toLowerCase();
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk1")));
        walkAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack3")));
        attackAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 1; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack3")));
        secondAttackAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Die" + i)));
        dieAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay0")));
        stayAnimation = new Animation(0.18f, frames);
        frames.clear();
    }
}

package com.timgapps.warfare.Units.GameUnits.Enemy.ent_1;

import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitController;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.interfacesAi.EnemyWarriorAi;

import java.util.Random;

public class Ent1Controller extends EnemyUnitController implements EnemyWarriorAi {
    private float waitTime;
    private Random random;

    public Ent1Controller(Level level, EnemyUnitModel model) {
        super(level, model);
        waitTime = 160;
        random = new Random();
    }

    // метод обновления логики игрового юнита
    @Override
    public void update(float delta) {
        super.update(delta);
        if (level.getState() != Level.PAUSED) {
            waitTime--;
        }
        if (!model.isDestroyed()) {
            checkCollisions();
            if (model.isTouchedPlayer()) {
                if (targetPlayer != null) {
                    attackPlayer();
                } else {
                    model.setIsTouchedPlayer(false);
                    model.setIsAttack(false);
                }
            } else if (level.getSiegeTower().getHealth() > 0) {
                model.setIsTouchedTower(checkCollision(body, level.getSiegeTower().getBody()));
                if (model.isTouchedTower()) {
                    attackTower();
                } else {
                    if (waitTime < 0) {
                        resetWaitTime();
                        boolean flag = random.nextBoolean();
                        if (flag == true) {
                            System.out.println("random.nextBoolean = " + flag);
                            model.setIsStay(false);

                        }
                    }
                    move();
                }
//            }
            } else {
                move();
            }
        } else {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        }
        if (model.isDamaged()) {
            model.getBloodSpray().setPosition(model.getX() + 54, model.getY() + 64);
            model.getBloodSpray().update(delta);
        }
        if (model.isDamaged() && model.getBloodSpray().isComplete()) {
            model.setIsDamaged(false);
        }
        System.out.println("waitTime = " + waitTime);
        System.out.println("Is Stay = " + model.isStay());
    }


    public void attackTower() {
        if (model.isAttackTower()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        } else {
            model.setIsAttackTower(true);
            model.setIsMove(false);
            model.setIsAttack(false);
            System.out.println("Attack tower!");
        }
    }

    public void attackPlayer() {
        if (model.isAttack()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        } else {
            System.out.println("attackPlayer");
            model.setIsAttack(true);
            model.setIsMove(false);
            model.setIsStay(false);
        }
    }

    public void stay() {
        if (model.isStay()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        } else {
            model.setIsStay(true);
        }
    }

    public void move() {
        model.setIsMove(true);
        if (!model.isStay()) {
            velocity.set(model.getSpeed(), 0);
        } else {
            velocity.set(0, 0);
        }
        model.setVelocity(velocity);
    }

    public void resetWaitTime() {
        waitTime = 160;
    }
}

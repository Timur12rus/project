package com.timgapps.warfare.Units.GameUnits.Enemy.wizard;

import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitController;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.interfacesAi.EnemyShooterAi;
import com.timgapps.warfare.Units.GameUnits.Enemy.enemy_bullets.Lightning;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;

import java.util.ArrayList;

public class WizardController extends EnemyUnitController implements EnemyShooterAi {
    //    private final float STOP_POSITION_X = 800;
    private final float STOP_POSITION_X = 1000;
    private final float ATTACK_DISTANCE_X = 400;
    private ArrayList<PlayerUnitModel> targetUnitsArray;
    private float waitTime = 100;
    private boolean isReachedAttackPosition;
    private final float LIGHTNING_DAMAGE = 15;

    public WizardController(LevelScreen levelScreen, EnemyUnitModel model) {
        super(levelScreen, model);
        targetUnitsArray = new ArrayList<PlayerUnitModel>();
    }

    // метод обновления логики игрового юнита
    @Override
    public void update(float delta) {
        super.update(delta);
        if (levelScreen.getState() != LevelScreen.PAUSED) {
            waitTime--;
        }
        // ai юнита
        if (!model.isDestroyed()) {
            checkCollisions();
            if (model.isTouchedPlayer()) {
                if (targetPlayer != null) {
                    attackPlayer();             // ударяем игрового юнита, с которым столкнулись
                } else {
                    model.setIsTouchedPlayer(false);
                    model.setIsAttack(false);
                }
            } else if (model.getX() < STOP_POSITION_X) {      // если дошел до позиции ожидания
                if (!isReachedAttackPosition) {
                    isReachedAttackPosition = true;             // достиг позиции атаки
                    stay();
                }
                if (waitTime <= 0 && !model.isShoot()) {
                    targetPlayer = findPlayerUnit();
                    if (targetPlayer != null) {
                        shootPlayer();   // стреляем по игровым юнитам
                    }
                }
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
    }

    public void resetTarget() {
        targetPlayer = null;
    }

    public PlayerUnitModel findPlayerUnit() {
        PlayerUnitModel targetPlayer = null;
        for (PlayerUnitModel playerUnit : levelScreen.getArrayPlayers()) {
            if (model.getX() - playerUnit.getX() < ATTACK_DISTANCE_X) {
                targetPlayer = playerUnit;
            }
            return targetPlayer;
        }
        return null;
    }

    // метод останавливает юнит, чтобы тот не двигался вперед (в этом состоянии анимация stateAnimation)
    public void stay() {
        if (model.isStay()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        } else {
            model.setIsStay(true);
            model.setIsMove(false);
            velocity.set(0, 0);
            model.setVelocity(velocity);
        }
    }

    public void move() {
        if (model.isMove()) {
            velocity.set(model.getSpeed(), 0);
            model.setVelocity(velocity);
        } else {
            model.setIsMove(true);
            model.setIsAttack(false);
            model.setIsShoot(false);
            model.setIsShooted(false);
        }
    }

    @Override
    public void shootPlayer() {
        if (model.isShoot()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        } else {
            model.setIsShoot(true);
            model.setIsAttack(false);
            model.setIsMove(false);
        }
    }

    // метод для запуска молний
    public void throwLightnings() {
        for (PlayerUnitModel playerUnit : levelScreen.getArrayPlayers()) {
            if (model.getX() - playerUnit.getX() < ATTACK_DISTANCE_X) {
                targetUnitsArray.add(playerUnit);
            }
        }
        if (targetUnitsArray.size() > 0 && !model.isAttack()) {
            throwLightning();
        }
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
//            model.setIsStay(false);
            model.setIsShoot(false);
//            isReachedAttackPosition = false;
        }
    }

    public void throwLightning() {
        for (PlayerUnitModel targetPlayerUnit : targetUnitsArray) {
            targetPlayerUnit.subHealth(LIGHTNING_DAMAGE);
//            targetPlayerUnit.subHealth(model.getUnitData().getDamage());
            new Lightning(levelScreen, targetPlayerUnit.getPosition(), targetPlayerUnit.getUnitData().getDeltaX());
            System.out.println("TARGET PLAYER DELTA X = " + targetPlayerUnit.getUnitData().getDeltaX());
            System.out.println("TARGET NAME = " + targetPlayerUnit.getUnitData().getName());
        }
        targetUnitsArray.clear();
    }

    public void resetWaitTime() {
        waitTime = 200;
    }
}
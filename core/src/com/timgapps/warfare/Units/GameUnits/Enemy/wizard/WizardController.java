package com.timgapps.warfare.Units.GameUnits.Enemy.wizard;

import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitController;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.interfacesAi.EnemyShooterAi;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Lightning;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;

import java.util.ArrayList;

public class WizardController extends EnemyUnitController implements EnemyShooterAi {
    private final float STOP_POSITION_X = 1000;
    private final float ATTACK_DISTANCE_X = 400;
    private ArrayList<PlayerUnitModel> targetUnitsArray;
    private float waitTime = 200;
    private boolean isReachedAttackPosition;

    public WizardController(Level level, EnemyUnitModel model) {
        super(level, model);
        targetUnitsArray = new ArrayList<PlayerUnitModel>();
    }

    // метод обновления логики игрового юнита
    @Override
    public void update(float delta) {
        super.update(delta);
        if (level.getState() != Level.PAUSED) {
            waitTime--;
        }
        // ai юнита
        if (!model.isDestroyed()) {
            checkCollisions();
            if (model.isTouchedPlayer()) {
                if (targetPlayer != null) {
                    attackPlayer();
                } else {
                    model.setIsTouchedPlayer(false);
                    model.setIsAttack(false);
                }
            } else if (model.getX() < STOP_POSITION_X) {      // если дошел до позиции ожидания
                isReachedAttackPosition = true;             // достиг позиции атаки
                stay();
                shootPlayer();              // стреляем по игровым юнитам
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

    @Override
    public EnemyUnitModel findPlayerUnit() {
        return null;
    }

    public void stay() {
        if (model.isStay()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        } else {
            model.setIsStay(true);
            model.setIsMove(false);
        }
    }

    @Override
    public void shootPlayer() {
        if (waitTime < 0) {
            for (PlayerUnitModel playerUnit : level.getArrayPlayers()) {
                if (model.getX() - playerUnit.getX() < ATTACK_DISTANCE_X) {
                    targetUnitsArray.add(playerUnit);
                }
            }
            if (targetUnitsArray.size() > 0 && !model.isAttack()) {
                throwLightning();
            }
            waitTime = 200;      // сбросим время ожидания на начальное значение
            stay();             // стоит ждет, пока не выйдет время ожидания
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
            model.setIsStay(false);
        }
    }

    public void throwLightning() {
        for (PlayerUnitModel targetPlayerUnit : targetUnitsArray) {
            targetPlayerUnit.subHealth(5);
            new Lightning(level, targetPlayerUnit.getPosition(), targetPlayerUnit.getUnitData().getDeltaX());
        }
        targetUnitsArray.clear();

    }

    public void move() {
        model.setIsMove(true);
        if (!model.isStay()) {
            velocity.set(model.getSpeed(), 0);
            model.setVelocity(velocity);
        } else {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        }
    }
}
package com.timgapps.warfare.Units.GameUnits.Enemy.zombie2;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitController;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.interfacesAi.EnemyWarriorAi;

public class Zombie2Controller extends EnemyUnitController implements EnemyWarriorAi {
    public Zombie2Controller(Level level, EnemyUnitModel model) {
        super(level, model);
    }

    // метод обновления логики игрового юнита
    @Override
    public void update(float delta) {
        super.update(delta);
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
                    move();
                }
            } else {
                move();
            }
        }
        if (model.isDamaged()) {
            model.getBloodSpray().setPosition(model.getX() + 54, model.getY() + 64);
            model.getBloodSpray().update(delta);
        }
        if (model.isDamaged() && model.getBloodSpray().isComplete()) {
            model.setIsDamaged(false);
        }
    }

    public void attackTower() {
        model.setIsAttackTower(true);
        model.setIsMove(false);
        model.setIsAttack(false);
        System.out.println("Attack tower!");
    }

    public void attackPlayer() {
        if (model.isAttack()) {
            Vector2 velocity = new Vector2(0, 0);
            model.setVelocity(velocity);
        } else {
            System.out.println("attackPlayer");
            model.setIsAttack(true);
            model.setIsMove(false);
            model.setIsStay(false);
        }
    }

    public void move() {
        System.out.println("Move");
        model.setIsMove(true);
        if (!model.isStay()) {
            model.getPosition().add(model.getSpeed(), 0);
        }
    }
}

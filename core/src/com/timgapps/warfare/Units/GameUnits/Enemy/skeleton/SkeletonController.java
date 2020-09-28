package com.timgapps.warfare.Units.GameUnits.Enemy.skeleton;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie.EnemyUnitController;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.units.EnemyWarriorAi;

public class SkeletonController extends EnemyUnitController implements EnemyWarriorAi {
    public SkeletonController(Level level, EnemyUnitModel model) {
        super(level, model);
    }

    public void update(float delta) {
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
            model.getBloodSpray().setPosition(model.getX(), model.getY());
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

//    public void checkCollisions() {
////        model.setIsTouchedPlayer(checkCollision(body, ((PlayerUnitModel) gameUnitModel).getBody()));
//        if (!model.isTouchedPlayer()) {               // проверяем коллизию
//            for (PlayerUnitModel playerUnit : level.getArrayPlayers()) {
//                if (playerUnit.isBodyActive()) {     // если тело активно
//                    model.setIsTouchedPlayer(checkCollision(body, playerUnit.getBody()));
//                    if (model.isTouchedPlayer()) {
//                        targetPlayer = playerUnit;
//                        break;
//                    } else {
//                        model.setIsTouchedPlayer(false);
//                    }
//                }
//            }
//        }
//    }

    // для проверки столкновения
//    public boolean checkCollision(Rectangle bodyA, Rectangle bodyB) {
//        if (Intersector.overlaps(bodyA, bodyB)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

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

    public void hit() {
        if (targetPlayer != null) {
            targetPlayer.subHealth(model.getDamage());
            if (targetPlayer.getHealth() <= 0) {
                targetPlayer = null;
            }
        }
    }

    public void hitTower() {
        if (level.getSiegeTower().getHealth() > 0) {
            level.getSiegeTower().setHealth(model.getDamage());
        }
    }
}

package com.timgapps.warfare.Units.GameUnits.Enemy.zombie;

import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.skeleton.SkeletonWarriorController;
import com.timgapps.warfare.Units.GameUnits.GameUnitController;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;

public class EnemyUnitController extends GameUnitController {
    //    private ParticleEffect bloodSpray;      // эффект брызги
//    protected EnemyUnitModel model;
    protected PlayerUnitModel targetPlayer;
    protected EnemyUnitModel model;
//    private SkeletonWarriorController controller;

    public EnemyUnitController(Level level, EnemyUnitModel model) {
        super(level, model);
//        this.model = model;
//        this.level = level;
//        controller = new SkeletonWarriorController(level, model);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
//        controller.update(delta);

//        if (!model.isDestroyed()) {
//            checkCollisions();
//            if (model.isTouchedPlayer()) {
//                if (targetPlayer != null) {
//                    attackPlayer();
//                } else {
//                    model.setIsTouchedPlayer(false);
//                    model.setIsAttack(false);
//                }
//            } else if (level.getSiegeTower().getHealth() > 0) {
//                model.setIsTouchedTower(checkCollision(body, level.getSiegeTower().getBody()));
//                if (model.isTouchedTower()) {
//                    attackTower();
//                } else {
//                    move();
//                }
//            } else {
//                move();
//            }
//        }
//        if (model.isDamaged()) {
//            model.getBloodSpray().setPosition(model.getX(), model.getY());
//            model.getBloodSpray().update(delta);
//        }
//        if (model.isDamaged() && model.getBloodSpray().isComplete()) {
//            model.setIsDamaged(false);
//        }
    }

//    public void attackTower() {
//        model.setIsAttackTower(true);
//        model.setIsMove(false);
//        model.setIsAttack(false);
//        System.out.println("Attack tower!");
//    }

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

//    public void attackPlayer() {
//        if (model.isAttack()) {
//            Vector2 velocity = new Vector2(0, 0);
//            model.setVelocity(velocity);
//        } else {
//            System.out.println("attackPlayer");
//            model.setIsAttack(true);
//            model.setIsMove(false);
//            model.setIsStay(false);
//        }
//    }

//    public void move() {
//        System.out.println("Move");
//        model.setIsMove(true);
//        if (!model.isStay()) {
//            model.getPosition().add(model.getSpeed(), 0);
//        }
//    }

//    @Override
//    public void hit() {
//        super.hit();
//        controller.hit();
//        System.out.println("Hit");
////        if (targetPlayer != null) {
////            targetPlayer.subHealth(model.getDamage());
////            System.out.println("Player health = " + targetPlayer.getHealth());
////            if (targetPlayer.getHealth() <= 0) {
////                targetPlayer = null;
////            }
////        }
//    }

//    public void hitTower() {
//        controller.hitTower();
////        if (level.getSiegeTower().getHealth() > 0) {
////            level.getSiegeTower().setHealth(model.getDamage());
////        }
//    }
}

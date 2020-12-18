package com.timgapps.warfare.Units.GameUnits.Enemy.goblin;

import com.timgapps.warfare.Units.GameUnits.Effects.Explosion;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitController;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.interfacesAi.EnemyWarriorAi;
import com.timgapps.warfare.screens.level.LevelScreen;

public class GoblinController extends EnemyUnitController implements EnemyWarriorAi {
    private Explosion explosion;
    private boolean exposionIsStarted;

    public GoblinController(LevelScreen levelScreen, EnemyUnitModel model) {
        super(levelScreen, model);
        explosion = new Explosion(levelScreen);
        levelScreen.addChild(explosion);
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
            } else if (levelScreen.getSiegeTower().getHealth() > 0) {
                model.setIsTouchedTower(checkCollision(body, levelScreen.getSiegeTower().getBody()));
                if (model.isTouchedTower()) {
                    attackTower();
                } else {
                    move();
                }
            } else {
                move();
            }
        } else {
            if (!exposionIsStarted) {
                exposionIsStarted = true;
                explosion.setPosition(model.getPosition().x - explosion.getWidth() / 2, model.getPosition().y);
                explosion.start();
            }
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

    public void attackTower() {
        if (model.isAttackTower()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        } else {
            model.setIsAttackTower(true);
            model.setIsMove(false);
            model.setIsAttack(false);
//            explosion.setPosition(model.getPosition().x - explosion.getWidth() / 2, model.getPosition().y - explosion.getHeight() / 2);
//            explosion.start();

//            if (!levelScreen.getSiegeTower().isMove()) {
//                hitTower();
//            }
            hitTower();
            model.subHealth(model.getHealth());
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
            hit();
            model.subHealth(model.getHealth());
        }
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

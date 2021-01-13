package com.timgapps.warfare.Units.GameUnits.Enemy.zombie1_runner;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitController;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.interfacesAi.EnemyWarriorAi;
import com.timgapps.warfare.Units.GameUnits.Enemy.interfacesAi.EnemyWarriorAiTarget;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;
import com.timgapps.warfare.screens.level.LevelScreen;

import java.util.ArrayList;

public class Zombie1RunnerController extends EnemyUnitController implements EnemyWarriorAi, EnemyWarriorAiTarget {
    protected PlayerUnitModel newTargetPlayer;
//    protected Vector2 velocity;
    protected boolean isHaveTargetPlayer;

    public Zombie1RunnerController(LevelScreen levelScreen, EnemyUnitModel model) {
        super(levelScreen, model);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (!model.isAttack()) {
            newTargetPlayer = findPlayerUnit();
            if (targetPlayer == null) {
                if (newTargetPlayer != null) {
                    targetPlayer = newTargetPlayer;
                }
            } else {
                if (!targetPlayer.equals(newTargetPlayer) && (newTargetPlayer != null)) {      // если новая цель не соответствет старой, то меняем цель на новую
                    // сравним расстояние от игрового юнита до вражеского
                    Vector2 v1 = new Vector2(model.getX(), model.getY());           // позиция текущего юнита
                    Vector2 newTargetEnemyPos = new Vector2(newTargetPlayer.getX(), newTargetPlayer.getY());
                    Vector2 targetEnemyPos = new Vector2(targetPlayer.getX(), targetPlayer.getY());
                    float distance1 = v1.sub(newTargetEnemyPos).len();      // расстояние до новой цели
//                    float distance1 = newTargetEnemyPos.sub(v1).len();      // расстояние до новой цели
                    float distance2 = v1.sub(targetEnemyPos).len();         // расстояние до предыдущей цели
                    if (distance1 < distance2)
                        targetPlayer = newTargetPlayer;
                }
            }
        }
        if (!model.isDestroyed()) {
            if (targetPlayer != null) {
                if (targetPlayer.isBodyActive()) {
                    System.out.println("!!!!!!IsBodyActive = " + targetPlayer.isBodyActive());
                    model.setIsTouchedPlayer(checkCollision(body, targetPlayer.getBody()));               // проверим столкновение тел юнитов
                    System.out.println("Touched Player = " + model.isTouchedPlayer());
                } else {
                    System.out.println("IsBodyActive = " + targetPlayer.isBodyActive());
                    model.setIsTouchedPlayer(false);
                    System.out.println("Touched Player = " + model.isTouchedPlayer());
                    model.setTargetPlayer(null);
                    targetPlayer = null;
                    model.setIsHaveTargetPlayer(false);
                    model.setIsAttack(false);
                }
//            System.out.println("Collision = " + checkCollision(body, targetEnemy.getBody()) + " /bodyA = " + body.toString() + "/ bodyB = " + targetEnemy.getBody().toString());
            }
            // AI юнита
            if (model.isTouchedPlayer()) {
                attackPlayer();
            } else if (model.isHaveTargetPlayer()) {
//                System.out.println("Target Player = " + targetPlayer.getName());
                if (model.isTouchedPlayer()) {
                    attackPlayer();
                } else if (levelScreen.getSiegeTower().getHealth() > 0) {
//                    System.out.println("towerHealth = " + levelScreen.getSiegeTower().getHealth());
                    model.setIsTouchedTower(checkCollision(body, levelScreen.getSiegeTower().getBody()));
                    if (model.isTouchedTower()) {
                        attackTower();
                    } else {
                        moveToTarget();
                    }
                } else {
                    moveToTarget();
                }
            } else if (levelScreen.getSiegeTower().getHealth() > 0) {
//                System.out.println("towerHealth = " + levelScreen.getSiegeTower().getHealth());
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
            velocity.set(0, 0);
            model.setVelocity(velocity);
        }
    }


    @Override
    public void attackPlayer() {
        if (model.isAttack()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        } else {
            System.out.println("attackPlayer");
            model.setIsAttack(true);
            model.setIsMoveToTarget(false);
            model.setIsMove(false);
            model.setIsAttackTower(false);
        }
    }

    @Override
    public void attackTower() {
        if (model.isAttackTower()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        } else {
            System.out.println("Attack Barricade!");
            model.setIsAttack(false);
            model.setIsMoveToTarget(false);
            model.setIsMove(false);
            model.setIsAttackTower(true);
        }
    }

    @Override
    public void move() {
//        System.out.println("move");
        model.setIsMove(true);
        model.setIsAttack(false);
        model.setIsStay(false);
        model.setIsAttackTower(false);
//        velocity.set(model.getSpeed(), 0);
//        model.setVelocity(velocity);
        if (!model.isStay()) {
            velocity.set(model.getSpeed(), 0);
            model.setVelocity(velocity);
        } else {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        }
    }

    @Override
    public void moveToTarget() {
//        velocity.set(model.getX(), model.getY()).sub(targetPlayer.getX(), targetPlayer.getY()).nor().scl(model.getSpeed());
//        model.setVelocity(velocity);
        if (!model.isMoveToTarget()) {
            System.out.println("moveToTarget");
            model.setIsMoveToTarget(true);
            model.setIsAttack(false);
            model.setIsMove(false);
            model.setIsStay(false);
        }

        if (!model.isStay()) {
            velocity.set(model.getX(), model.getY()).sub(targetPlayer.getX(), targetPlayer.getY()).nor().scl(model.getSpeed());
            model.setVelocity(velocity);
        } else {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        }
    }

    @Override
    public PlayerUnitModel findPlayerUnit() {
        /** массив вражеских юнитов **/
        ArrayList<PlayerUnitModel> players = levelScreen.getArrayPlayers();
        /** массив вражеских юнитов - "потенциальных целей" **/
        ArrayList<PlayerUnitModel> targetPlayers = new ArrayList<PlayerUnitModel>();
        /** выполним поиск ВРАЖЕСКОГО ЮНИТА-ЦЕЛЬ **/
//        for (int i = 0; i < enemies.size(); i++) {
        for (PlayerUnitModel player : players) {
            if (player.getHealth() > 0 && player.isBodyActive()) {
                /** проверим расстояние до юнита игрока, можем ли мы двигаться к нему (успеем ли..)
                 * если да, то добавим его в массив юнитов игрока, которых видит ВРАЖЕСКИЙ ЮНИТ
                 * **/
                Vector2 playerPosition = new Vector2();      // позиция юнита игрока (которого будем атаковать)
                float x = player.getPosition().x + 24;
                float y = player.getPosition().y;

//            System.out.println("EnemyX = " + x);
//            System.out.println("EnemyY = " + y);
                playerPosition.set(x, y);
                Vector2 enemyPosition = new Vector2();     // позиция юнита врага (которым управляем сейчас)
//                float x2 = model.getPosition().x + model.getBodySize().x;
                float x2 = model.getPosition().x - model.getBodySize().x / 2;
                float y2 = model.getPosition().y - model.getBodySize().y / 2;
//            System.out.println("PlayerX = " + x2);
//            System.out.println("PlayerY = " + y2);
                enemyPosition.set(x2, y2);
                float x3 = x2 - 480;
                float y3 = y2 + 2000;
                float x4 = x2 - 480;
                float y4 = y2 - 2000;
                Vector2 vectorUp = new Vector2();
                vectorUp.set(x3, y3);
                Vector2 vectorDown = new Vector2();
                vectorDown.set(x4, y4);
                if (Intersector.isPointInTriangle(playerPosition, enemyPosition, vectorUp, vectorDown)) {      // если вражеский юнит находится в пределах видимости, то добавляем его в массив
//                    if (!targetEnemies.equals(enemy))
                    targetPlayers.add(player);                                  // потенциальных целей
                }
            }
        }
        /** здесь определим самого ближнего ВРАЖЕСКОГО ЮНИТА к ИГРОВОМУ
         * т.е. найдем по расстоянию между ними, т.е. самое маленькое расстояние
         * **/
        float minDistance;
        Vector2 enemyPosition = new Vector2(model.getX(), model.getY());                  // позиция игрового юнита
        PlayerUnitModel target = null;
        if (targetPlayers.size() > 0) {
            Vector2 playerPosition = new Vector2(targetPlayers.get(0).getX(), targetPlayers.get(0).getY());   // позиция первого игрового юнита
            Vector2 distance = enemyPosition.sub(playerPosition);           // вектор расстояния между игровым и вражеским юнитом
            minDistance = distance.len();                                   // длина вектора расстояния между вражеским и игровым юнитом
            target = targetPlayers.get(0);                             // вражеский юнит - "цель", к которому будет двигаться игровой юнит
            // найдем самого близкого вражеского юнита к игровому
            for (PlayerUnitModel playerUnitModel : targetPlayers) {
                float distanceToEnemy = new Vector2(playerUnitModel.getX(), playerUnitModel.getY()).sub(enemyPosition).len();
                if (distanceToEnemy < minDistance) {
                    minDistance = distanceToEnemy;
                    target = playerUnitModel;
                }
            }
        }
        if (target != null) {
            if (target.getHealth() > 0) {
                isHaveTargetPlayer = true;
                model.setIsHaveTargetPlayer(true);
            } else {
                isHaveTargetPlayer = false;
                model.setIsHaveTargetPlayer(false);
            }
        } else {
            isHaveTargetPlayer = false;
            model.setIsHaveTargetPlayer(false);
        }
        return target;
    }
}

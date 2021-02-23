package com.timgapps.warfare.Units.GameUnits.Player.units.gnome;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.interfacesAi.PlayerWarriorAi;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitController;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;

import java.util.ArrayList;

public class GnomeController extends PlayerUnitController implements PlayerWarriorAi {
    public GnomeController(LevelScreen levelScreen, PlayerUnitModel model) {
        super(levelScreen, model);
    }

    // метод обновления логики игрового юнита
    @Override
    public void update(float delta) {
        super.update(delta);
//        System.out.println("isTouchedEnemy" + model.isTouchedEnemy());
        EnemyUnitModel newTargetEnemy = findEnemyUnit();
        if (targetEnemy == null) {
            if (newTargetEnemy != null) {
                targetEnemy = newTargetEnemy;
            }
        } else {
            if (!targetEnemy.equals(newTargetEnemy) && (newTargetEnemy != null)) {      // если новая цель не соответствет старой, то меняем цель на новую
                // сравним расстояние от игрового юнита до вражеского
                Vector2 v1 = new Vector2(model.getX(), model.getY());
                Vector2 newTargetEnemyPos = new Vector2(newTargetEnemy.getX(), newTargetEnemy.getY());
                Vector2 targetEnemyPos = new Vector2(targetEnemy.getX(), targetEnemy.getY());
                float distance1 = newTargetEnemyPos.sub(v1).len();      // расстояние до новой цели
                float distance2 = targetEnemyPos.sub(v1).len();         // расстояние до предыдущей цели
                if (distance1 < distance2)
                    targetEnemy = newTargetEnemy;
            }
        }
        if (!model.isDestroyed()) {
            if (targetEnemy != null) {
                if (targetEnemy.isBodyActive()) {
                    System.out.println("!!!!!!IsBodyActive = " + targetEnemy.isBodyActive());
                    model.setIsTouchedEnemy(checkCollision(body, targetEnemy.getBody()));               // проверим столкновение тел юнитов
                    System.out.println("Touched Enemy = " + model.isTouchedEnemy());
                } else {
                    System.out.println("IsBodyActive = " + targetEnemy.isBodyActive());
                    model.setIsTouchedEnemy(false);
                    System.out.println("Touched Enemy = " + model.isTouchedEnemy());
                    model.setTargetEnemy(null);
                    targetEnemy = null;
                    model.setIsHaveTargetEnemy(false);
                    model.setIsAttack(false);
                }
//            System.out.println("Collision = " + checkCollision(body, targetEnemy.getBody()) + " /bodyA = " + body.toString() + "/ bodyB = " + targetEnemy.getBody().toString());
            }

            // AI юнита
            if (model.isTouchedEnemy()) {
                attackEnemy();
            } else if (model.isHaveTargetEnemy()) {
                System.out.println("Target Enemy = " + targetEnemy.getName());
                if (model.isTouchedEnemy()) {
                    attackEnemy();
                } else if (barricade.getHealth() > 0) {
                    System.out.println("barricadeHealth = " + barricade.getHealth());
                    model.setIsTouchedBarricade(checkCollision(body, barricade.getBody()));
                    if (model.isTouchedBarricade()) {
                        attackBarricade();
                    } else {
                        moveToTarget();
                    }
                } else {
                    moveToTarget();
                }
            } else if (barricade.getHealth() > 0) {
                System.out.println("barricadeHealth = " + barricade.getHealth());
                model.setIsTouchedBarricade(checkCollision(body, barricade.getBody()));
                if (model.isTouchedBarricade()) {
                    attackBarricade();
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

    // метод для поиска вражеского юнита (юнит которого будем атаковать))

    @Override
    public EnemyUnitModel findEnemyUnit() {
        /** массив вражеских юнитов **/
        ArrayList<EnemyUnitModel> enemies = levelScreen.getArrayEnemies();
        /** массив вражеских юнитов - "потенциальных целей" **/
        ArrayList<EnemyUnitModel> targetEnemies = new ArrayList<EnemyUnitModel>();
        /** выполним поиск ВРАЖЕСКОГО ЮНИТА-ЦЕЛЬ **/
//        for (int i = 0; i < enemies.size(); i++) {
        for (EnemyUnitModel enemy : enemies) {
            if (enemy.getHealth() > 0 && enemy.isBodyActive()) {
                /** проверим расстояние до вражеского юнита, можем ли мы двигаться к нему (успеем ли..)
                 * если да, то добавим его в массив вражеских юнитов, которых видит ИГРОВОЙ ЮНИТ
                 * **/
                Vector2 enemyPosition = new Vector2();
                float x = enemy.getPosition().x + 24;
                float y = enemy.getPosition().y;

//            System.out.println("EnemyX = " + x);
//            System.out.println("EnemyY = " + y);
                enemyPosition.set(x, y);
                Vector2 playerPosition = new Vector2();
                float x2 = model.getPosition().x + model.getBodySize().x;
                float y2 = model.getPosition().y + model.getBodySize().y / 2;
//            System.out.println("PlayerX = " + x2);
//            System.out.println("PlayerY = " + y2);
                playerPosition.set(x2, y2);
                float x3 = x2 + 480;
                float y3 = y2 + 2000;
                float x4 = x2 + 480;
                float y4 = y2 - 2000;
                Vector2 vectorUp = new Vector2();
                vectorUp.set(x3, y3);
                Vector2 vectorDown = new Vector2();
                vectorDown.set(x4, y4);
                if (Intersector.isPointInTriangle(enemyPosition, playerPosition, vectorUp, vectorDown)) {      // если вражеский юнит находится в пределах видимости, то добавляем его в массив
                    if (!targetEnemies.equals(enemy))
                        targetEnemies.add(enemy);                                  // потенциальных целей
                }
            }
        }
        /** здесь определим самого ближнего ВРАЖЕСКОГО ЮНИТА к ИГРОВОМУ
         * т.е. найдем по расстоянию между ними, т.е. самое маленькое расстояние
         * **/
        float minDistance;
        Vector2 playerPosition = new Vector2(model.getX(), model.getY());                  // позиция игрового юнита
        EnemyUnitModel target = null;
        if (targetEnemies.size() > 0) {
            Vector2 enemyPosition = new Vector2(targetEnemies.get(0).getX(), targetEnemies.get(0).getY());   // позиция первого вражеского юнита
            Vector2 distance = enemyPosition.sub(playerPosition);           // вектор расстояния между игровым и вражеским юнитом
            minDistance = distance.len();                                   // длина вектора расстояния между вражеским и игровым юнитом
            target = targetEnemies.get(0);                             // вражеский юнит - "цель", к которому будет двигаться игровой юнит
            // найдем самого близкого вражеского юнита к игровому
            for (EnemyUnitModel enemyUnitModel : targetEnemies) {
                float distanceToEnemy = new Vector2(enemyUnitModel.getX(), enemyUnitModel.getY()).sub(playerPosition).len();
                if (distanceToEnemy < minDistance) {
                    minDistance = distanceToEnemy;
                    target = enemyUnitModel;
//                    targetEnemy = enemyUnitModel;
                }
            }
        }
        if (target != null) {
            if (target.getHealth() > 0) {
                isHaveTargetEnemy = true;
                model.setIsHaveTargetEnemy(true);
            } else {
                isHaveTargetEnemy = false;
                model.setIsHaveTargetEnemy(false);
            }
        } else {
            isHaveTargetEnemy = false;
            model.setIsHaveTargetEnemy(false);
        }
        return target;
    }

    // метод для атаки вражеского юнита
    @Override
    public void attackEnemy() {
        if (model.isAttack()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
            model.setIsHited(true);
        } else {
            System.out.println("attackEnemy");
            model.setIsAttack(true);
            model.setIsMoveToTarget(false);
            model.setIsMove(false);
            model.setIsAttackBarricade(false);
        }
    }

    // метод для атаки баррикады
    @Override
    public void attackBarricade() {
        if (model.isAttackBarricade()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        } else {
            System.out.println("Attack Barricade!");
            model.setIsAttack(false);
            model.setIsMoveToTarget(false);
            model.setIsMove(false);
            model.setIsAttackBarricade(true);
        }
    }

    // метод для движения к вражескому юниту
    @Override
    public void moveToTarget() {
        if (model.isHited()) {
            velocity.set(targetEnemy.getX(), targetEnemy.getY()).sub(new Vector2(model.getX(), model.getY())).nor().scl(model.getSpeed() * 0.7f);
        } else {
            velocity.set(targetEnemy.getX(), targetEnemy.getY()).sub(new Vector2(model.getX(), model.getY())).nor().scl(model.getSpeed());
        }
        model.setVelocity(velocity);
        if (!model.isMoveToTarget()) {
            System.out.println("moveToTarget");
            model.setIsMoveToTarget(true);
            model.setIsAttack(false);
            model.setIsMove(false);
            model.setIsStay(false);
        }
    }

    // метод для движения вправо
    @Override
    public void move() {
//        System.out.println("move");
        model.setIsMove(true);
        model.setIsAttack(false);
        model.setIsStay(false);
        model.setIsAttackBarricade(false);
        if (model.isHited()) {
            velocity.set(model.getSpeed() * 0.7f, 0);
        } else {
            velocity.set(model.getSpeed(), 0);
        }
//        velocity.set(model.getSpeed(), 0);
        model.setVelocity(velocity);
    }
}

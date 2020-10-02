package com.timgapps.warfare.Units.GameUnits.Player.units.archer;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.interfacesAi.PlayerShooterAi;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitController;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;

import java.util.ArrayList;

public class ArcherController extends PlayerUnitController implements PlayerShooterAi {
    private boolean isHaveTarget;
    private boolean isHaveVerticalDirection;
    private Direction verticalDirectionMovement = Direction.NONE;

    enum Direction {
        NONE,
        UP,
        DOWN
    }

    public ArcherController(Level level, PlayerUnitModel model) {
        super(level, model);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
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

                if (model.isTouchedEnemy()) {
                    shootEnemy();
                } else if (model.isHaveTargetEnemy()) {
                    System.out.println("Target Enemy = " + targetEnemy.getName());
                    if (model.isTouchedEnemy()) {
                        shootEnemy();
                    } else {
                        moveToTarget();
                    }
                } else if (barricade.getHealth() > 0) {
                    System.out.println("barricadeHealth = " + barricade.getHealth());
                    model.setIsTouchedBarricade(checkCollision(body, barricade.getBody()));
                    if (model.isTouchedBarricade()) {
                        shootEnemy();
                    } else {
                        move();
                    }
                } else {
                    move();
                }
            }
        }
    }

    @Override
    public void move() {
        System.out.println("move");
        model.setIsMove(true);
        model.setIsAttack(false);
        model.setIsStay(false);
        model.setIsAttackBarricade(false);
        model.getPosition().add(model.getSpeed(), 0);
    }

    @Override
    public void moveToTarget() {
        velocity = new Vector2(targetEnemy.getX(), targetEnemy.getY()).sub(new Vector2(model.getX(), model.getY())).nor().scl(model.getSpeed());
        model.setVelocity(velocity);
        if (!model.isMoveToTarget()) {
            System.out.println("moveToTarget");
            model.setIsMoveToTarget(true);
            model.setIsAttack(false);
            model.setIsMove(false);
            model.setIsStay(false);
        }
    }

    @Override
    public void shootEnemy() {
        if (model.isAttack()) {
            Vector2 velocity = new Vector2(0, 0);
            model.setVelocity(velocity);
        } else {
            System.out.println("attackEnemy");
            model.setIsAttack(true);
            model.setIsMoveToTarget(false);
            model.setIsMove(false);
            model.setIsAttackBarricade(false);
        }
    }

    private boolean checkDistanceToEnemy(EnemyUnitModel enemyUnit) {
        Vector2 bodyPosition = new Vector2();       // текущая позиция юнита
        Vector2 enemyBodyPosition = new Vector2();
        bodyPosition.set(model.getPosition().x + model.getBodyWidth(), model.getPosition().y);
        enemyBodyPosition.set(enemyUnit.getPosition().x, enemyUnit.getPosition().y);
        Vector2 distanceToEnemy = enemyBodyPosition.sub(bodyPosition);

        /** время необходимое для движения до вражеского юнита tф = S / V **/
        float time = distanceToEnemy.len() / model.getSpeed();

        /** рассчитанное время для движения до вражеского юнита tp = Sy / Vy**/
        float y1 = enemyBodyPosition.y;
        float y2 = bodyPosition.y;
        float timeCalculated = (float) Math.abs((y1 - y2) / (model.getSpeed() * Math.sin(MathUtils.degreesToRadians * distanceToEnemy.angle())));
        if (timeCalculated <= time)
            return true;        // игровой юнит успевает достигнуть варжеского = true
        else
            return false;       // игровой юнит не успевает достигнуть вражеского = false
    }


    @Override
    public EnemyUnitModel findEnemyUnit() {
        /** массив вражеских юнитов **/
        ArrayList<EnemyUnitModel> enemies = level.getArrayEnemies();
        /** массив вражеских юнитов - "потенциальных целей" **/
        ArrayList<EnemyUnitModel> targetEnemies = new ArrayList<EnemyUnitModel>();
        /** выполним поиск ВРАЖЕСКОГО ЮНИТА-ЦЕЛЬ **/
//        for (int i = 0; i < enemies.size(); i++) {
        /** выполним поиск ВРАЖЕСКОГО ЮНИТА-ЦЕЛЬ **/
        try {
            for (EnemyUnitModel enemy : enemies) {
                if (enemy.getHealth() > 0 && enemy.isBodyActive()) {
                    /** проверим расстояние до вражеского юнита, можем ли мы двигаться к нему (успеем ли..)
                     * если да, то добавим его в массив вражеских юнитов, которых видит ИГРОВОЙ ЮНИТ
                     * **/
                    // позиция вражеского юнита
                    Vector2 enemyPosition = new Vector2();
                    float x = enemy.getPosition().x + 24;
                    float y = enemy.getPosition().y;

//            System.out.println("EnemyX = " + x);
//            System.out.println("EnemyY = " + y);
                    enemyPosition.set(x, y);
                    // позиция игрового юнита
                    Vector2 playerPosition = new Vector2();
                    float x2 = model.getPosition().x + model.getBodySize().x + 60;
                    float y2 = model.getPosition().y + model.getBodySize().y / 2;
//            System.out.println("PlayerX = " + x2);
//            System.out.println("PlayerY = " + y2);
                    playerPosition.set(x2, y2);
//                float distance = new Vector2(enemyPosition.sub(playerPosition)).len();      // расстояние между юнитами (вражеским и игровым)
                    float x3 = x2 + 480;
                    float y3 = y2 + 2000;
                    float x4 = x2 + 480;
                    float y4 = y2 - 2000;
                    Vector2 vectorUp = new Vector2();
                    vectorUp.set(x3, y3);
                    Vector2 vectorDown = new Vector2();
                    vectorDown.set(x4, y4);
//                if (Intersector.isPointInTriangle(enemyPosition, playerPosition, vectorUp, vectorDown)) {      // если вражеский юнит находится в пределах видимости, то добавляем его в массив
//                    if (!targetEnemies.equals(enemy))
//                        targetEnemies.add(enemy);                                  // потенциальных целей
//                }

                    /** проверим расстояяние до вражеского юнита, можем ли мы двигаться к нему (успеем ли..)
                     * если да, то добавим его в массив вражеских юнитов, которых видит ИГРОВОЙ ЮНИТ
                     * **/

                    if (checkDistanceToEnemy(enemy)) {
                        System.out.println("checkDistance to enemy = TRUE");
                        targetEnemies.add(enemy);  // добавим вражеский юнита в массив потенциальных "целевых юнитов"
                    } else {
                        System.out.println("checkDistance to enemy = FALSE");
                    }
                }
            }
            /** здесь определим самого ближнего ВРАЖЕСКОГО ЮНИТА к ИГРОВОМУ
             * т.е. найдем по расстоянию между ними, т.е. самое маленькое расстояние
             * **/
            float minDistance;
            Vector2 bodyPosition = new Vector2();
            Vector2 enemyPosition = new Vector2();
            bodyPosition.set(model.getPosition().x, model.getPosition().y);
            enemyPosition.set(targetEnemies.get(0).getPosition().x, targetEnemies.get(0).getPosition().y);
            minDistance = enemyPosition.sub(bodyPosition).len();
            EnemyUnitModel newTargetEnemy = targetEnemies.get(0);
            for (EnemyUnitModel enemyUnit : targetEnemies) {
                Vector2 playerPos = new Vector2();
                playerPos.set(model.getPosition());
                Vector2 enemyPos = new Vector2();
                enemyPos.set(enemyUnit.getPosition());
                float distanceToEnemy = enemyPos.sub(playerPos).len();
                if (distanceToEnemy < minDistance) {
                    minDistance = distanceToEnemy;
                    newTargetEnemy = enemyUnit;
                }
            }
            minDistance = 0;
            if (targetEnemy != null) {
                // если текущий "враг-цель" находится дальше чем новый найденный, то новый "враг-цель"  - это новый найденный
                if (!targetEnemy.equals(newTargetEnemy)) {
//                isHaveTarget = true;        // изменим флаг на true, т.е. есть "враг-цель"
                    targetEnemy = newTargetEnemy;
//                calculateVerticalDirection();       // вычислим направление вертикального перемещения
//                model.setIsMoveToTarget(true);
//                currentState = State.WALKING;
                }
            } else {
                targetEnemy = newTargetEnemy;
            }
            if (targetEnemy != null) {
                if (targetEnemy.getHealth() > 0) {
                    calculateVerticalDirection();       // вычислим направление вертикального перемещения
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
            return targetEnemy;
        } catch (Exception e) {
            verticalDirectionMovement = Direction.NONE;
            return null;
        }
    }

    /**
     * Метод для вычисления направления вертикального перемещения
     **/
    private Direction calculateVerticalDirection() {
        float posY = model.getPosition().y;
        float posYTarget = targetEnemy.getPosition().y;
        if (posY < posYTarget) verticalDirectionMovement = Direction.UP;
        if (posY > posYTarget) verticalDirectionMovement = Direction.DOWN;
        if (posY == posYTarget) verticalDirectionMovement = Direction.NONE;
        isHaveVerticalDirection = true;
        return verticalDirectionMovement;
    }
}

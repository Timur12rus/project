package com.timgapps.warfare.Units.GameUnits.Player.units.shooter;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Arrow;
import com.timgapps.warfare.Units.GameUnits.Player.interfacesAi.PlayerShooterAi;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitController;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.units.archer.ArcherController;

import java.util.ArrayList;

public class ShooterController extends PlayerUnitController implements PlayerShooterAi {
    private boolean isHaveTarget;
    private boolean isHaveVerticalDirection;
    private ArcherController.Direction verticalDirectionMovement = ArcherController.Direction.NONE;
    private final float ATTACK_DISTANCE = 300;
    private final float DISTANCE_TO_BARRICADE = 300;
    private boolean isReachedEnemyYPos;
    private Vector2 barricadePosition;
    private float distanceToBarricade;

    enum Direction {
        NONE,
        UP,
        DOWN
    }

    public ShooterController(Level level, PlayerUnitModel model) {
        super(level, model);
        barricadePosition = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        EnemyUnitModel newTargetEnemy = findEnemyUnit();
//        System.out.println("New TARGET ENEMY = " + newTargetEnemy);
        if (targetEnemy == null) {
            if (newTargetEnemy != null) {
                isReachedEnemyYPos = false;
                targetEnemy = newTargetEnemy;
                calculateVerticalDirection(newTargetEnemy);       // вычислим направление вертикального перемещения
                isHaveTargetEnemy = true;
                model.setIsHaveTargetEnemy(true);
                System.out.println("TargetEnemy = " + targetEnemy.getName());
            }
        } else {
            if (!targetEnemy.equals(newTargetEnemy) && (newTargetEnemy != null)) {      // если новая цель не соответствет старой, то меняем цель на новую
                // сравним расстояние от игрового юнита до вражеского
                Vector2 v1 = new Vector2(model.getX(), model.getY());
                Vector2 newTargetEnemyPos = new Vector2(newTargetEnemy.getX(), newTargetEnemy.getY());
                Vector2 targetEnemyPos = new Vector2(targetEnemy.getX(), targetEnemy.getY());
                float distance1 = newTargetEnemyPos.sub(v1).len();      // расстояние до новой цели
                float distance2 = targetEnemyPos.sub(v1).len();         // расстояние до предыдущей цели
                if (distance1 < distance2) {
                    targetEnemy = newTargetEnemy;
                    calculateVerticalDirection(newTargetEnemy);       // вычислим направление вертикального перемещения
                    isHaveTargetEnemy = true;
                    model.setIsHaveTargetEnemy(true);
                }
            }
        }
        if (!model.isDestroyed()) {
            if (targetEnemy != null) {
//                System.out.println("TargEt ENEMy = " + targetEnemy.getName());
                if (targetEnemy.isBodyActive()) {
                    model.setIsTouchedEnemy(checkCollision(body, targetEnemy.getBody()));               // проверим столкновение тел юнитов
                } else {
                    model.setIsTouchedEnemy(false);
                    model.setTargetEnemy(null);
                    targetEnemy = null;
                    model.setIsHaveTargetEnemy(false);
                    model.setIsAttack(false);
                }
            }
//            System.out.println("Collision = " + checkCollision(body, targetEnemy.getBody()) + " /bodyA = " + body.toString() + "/ bodyB = " + targetEnemy.getBody().toString());

            // Ai логика
            if (model.isTouchedEnemy()) {
                attackEnemy();
//                shootEnemy();
            } else if (model.isHaveTargetEnemy()) {
                System.out.println("Target Enemy! = " + targetEnemy.getName());
                if (!model.isShoot()) {     // если юнит не в соятоянии атаки, то двигаемся к врагу
                    moveToTarget();
                }
            } else if (barricade.getHealth() > 0) {         // если баррикада существует
                Vector2 playerPosition = new Vector2(model.getX(), model.getY());
                distanceToBarricade = barricadePosition.x - playerPosition.x;
                if (distanceToBarricade <= DISTANCE_TO_BARRICADE) {
                    model.setBarricadeIsDetected(true);
                    stay();
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
//        System.out.println("isReachedEnemyYPos = " + isReachedEnemyYPos);
//        System.out.println("isHaveVerticalDirection = " + model.isHaveVerticalDirection());
//        System.out.println("isHaveVerticalDirection = " + isHaveVerticalDirection);
//        System.out.println("verticalDirectionMovement = " + verticalDirectionMovement);
//        System.out.println("velocity = " + velocity);
    }

    private void checkAttack(EnemyUnitModel target) {
        float distance = target.getX() - model.getX();
        if (distance <= ATTACK_DISTANCE) {
            shootEnemy();
//        } else {
//            move();
        }
    }

    // метод для выпуска стрелы
    public void throwBullet() {
        System.out.println("Throw Arrow!");
        new Arrow(level, new Vector2(model.getX(), model.getY()), model.getDamage(), new Vector2(10, 0));
    }

    @Override
    public void move() {
        System.out.println("move");
        model.setIsMove(true);
        model.setIsAttack(false);
        model.setIsStay(false);
        model.setIsShooted(false);
        model.setIsShoot(false);
        model.setIsAttackBarricade(false);
        model.setBarricadeIsDetected(false);
        velocity.set(0, 0);
        velocity.set(model.getUnitData().getSpeed(), 0);
        model.setVelocity(velocity);
    }

    @Override
    public void stay() {
        System.out.println("Stay");
        if (model.isStay()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        } else {
            model.setIsStay(true);
            model.setIsShooted(false);
            model.setIsShoot(false);
            model.setIsAttack(false);
//            model.setIsMoveToTarget(false);
            model.setIsMove(false);
//            model.setIsAttackBarricade(false);
        }
    }

    @Override
    public void moveToTarget() {
        float posY = model.getPosition().y;
        float posYTarget = targetEnemy.getPosition().y;
        Vector2 playerPosition = new Vector2(model.getX(), model.getY());
        Vector2 enemyPosition = new Vector2(targetEnemy.getX(), targetEnemy.getY());
        barricadePosition.set(level.getBarricade().getBody().getX(), level.getBarricade().getBody().getY());
        Vector2 distanceToTarget = enemyPosition.sub(playerPosition);       // расстояние до врежеского юнита
        distanceToBarricade = (barricadePosition.x - playerPosition.x);
        if (distanceToBarricade < DISTANCE_TO_BARRICADE) {
            model.setBarricadeIsDetected(true);
        }

        if (!model.isMoveToTarget()) {
            System.out.println("moveToTarget");
            model.setIsMoveToTarget(true);
            model.setIsAttack(false);
            model.setIsMove(false);
            model.setIsStay(false);
            model.setIsShooted(false);
            model.setIsShoot(false);
//            model.setBarricadeIsDetected(false);
        }
        if (model.isHaveVerticalDirection()) {
//        if (isHaveVerticalDirection) {
            if (verticalDirectionMovement == ArcherController.Direction.DOWN) {
                if (posY > posYTarget) {        // если координата Y при перемещении юнита вниз больше координаты Y врага
                    if (distanceToTarget.x <= ATTACK_DISTANCE || distanceToBarricade <= DISTANCE_TO_BARRICADE) {    // если расстояние по оси х меньше дистанции для атаки (т.е. на расстоянии видимости)
                        velocity.set(0, -model.getSpeed());
                    } else {
                        velocity.set(targetEnemy.getX(), targetEnemy.getY()).sub(new Vector2(model.getX(), model.getY())).nor().scl(model.getSpeed());
                    }
                    model.setVelocity(velocity);
                } else {
                    model.setIsHaveVerticalDirection(false);
//                    isHaveVerticalDirection = false;
                    verticalDirectionMovement = ArcherController.Direction.NONE;
                }
            } else if (verticalDirectionMovement == ArcherController.Direction.UP) {
                if (posY < posYTarget) {   // если координата Y при перемещении юнита вверх меньше координаты Y врага
                    if (distanceToTarget.x < ATTACK_DISTANCE || distanceToBarricade < DISTANCE_TO_BARRICADE) {  // / если расстояние по оси х меньше дистанции для атаки (т.е. на расстоянии видимости)
                        velocity.set(0, model.getSpeed());
                    } else {
                        velocity.set(targetEnemy.getX(), targetEnemy.getY()).sub(new Vector2(model.getX(), model.getY())).nor().scl(model.getSpeed());
                    }
                    model.setVelocity(velocity);
                } else {
                    model.setIsHaveVerticalDirection(false);
//                    isHaveVerticalDirection = false;
                    verticalDirectionMovement = ArcherController.Direction.NONE;
                }
            }
        } else if (distanceToTarget.x < ATTACK_DISTANCE) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
            shootEnemy();
        } else {
            velocity.set(0, 0);
            model.setVelocity(velocity);
            stay();
        }
    }

    /**
     * Метод для преверки вертикального перемещения, нужно ли еще перемещаться вверх или вниз
     **/
    private void checkVerticalMovement() {
        Vector2 playerPosition = new Vector2(model.getX(), model.getY());
        Vector2 enemyPosition = new Vector2(targetEnemy.getX(), targetEnemy.getY());
        if (model.isHaveVerticalDirection()) {
            if ((verticalDirectionMovement == ArcherController.Direction.UP) && (playerPosition.y > enemyPosition.y)) {
                verticalDirectionMovement = ArcherController.Direction.NONE;
                model.setIsHaveVerticalDirection(false);
//                isHaveVerticalDirection = false;
//                stay();
            } else if ((verticalDirectionMovement == ArcherController.Direction.DOWN) && (playerPosition.y < enemyPosition.y)) {
                verticalDirectionMovement = ArcherController.Direction.NONE;
                model.setIsHaveVerticalDirection(false);
//                isHaveVerticalDirection = false;
//                stay();
            }
        }
    }

    // метод для стрельбы по вражескому юниту
    @Override
    public void shootEnemy() {
//        if (model.isShoot()) {
        if (model.isShoot()) {
//        if (model.isAttack()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        } else {
            System.out.println("shootEnemy");
//            model.setIsAttack(true);
            model.setIsShoot(true);
            model.setIsMoveToTarget(false);
            model.setIsAttack(false);
            model.setIsMove(false);
            model.setIsAttackBarricade(false);
            model.setBarricadeIsDetected(false);
        }
    }

    // метод для атаки вражеского юнита
    @Override
    public void attackEnemy() {
        if (model.isAttack()) {
            velocity.set(0, 0);
            model.setVelocity(velocity);
        } else {
            System.out.println("attackEnemy");
            model.setIsAttack(true);
            model.setIsShoot(false);
            model.setIsShooted(false);
            model.setIsMoveToTarget(false);
            model.setIsMove(false);
            model.setIsAttackBarricade(false);
        }
    }

    private boolean checkDistanceToEnemy(EnemyUnitModel enemyUnit) {
        Vector2 bodyPosition = new Vector2();       // текущая позиция юнита
        Vector2 enemyBodyPosition = new Vector2();
        bodyPosition.set(model.getPosition().x, model.getPosition().y);
        enemyBodyPosition.set(enemyUnit.getPosition().x, enemyUnit.getPosition().y);
        float distanceToEnemy = new Vector2(enemyBodyPosition.x - bodyPosition.x, enemyBodyPosition.y - bodyPosition.y).len();

        /** время необходимое для движения до вражеского юнита tф = S / V **/
        float time = distanceToEnemy / model.getSpeed();

        /** рассчитанное время для движения до вражеского юнита tp = Sy / Vy**/
        float y1 = enemyBodyPosition.y;
        float y2 = model.getPosition().y;
        Vector2 distance = new Vector2(enemyBodyPosition.x - bodyPosition.x, enemyBodyPosition.y - bodyPosition.y);
        float v = ((float) Math.abs(y1 - y2) / (distance.len())) * model.getSpeed();      // скорость с которой должен двигаться юнит
        float v1 = (float) Math.sin(MathUtils.degreesToRadians * distance.angle() * model.getSpeed());      // скорость с которой должен двигаться юнит
        if ((v <= model.getSpeed()) && (enemyBodyPosition.x > (model.getPosition().x + model.getBodyWidth() / 2)))
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
        for (EnemyUnitModel enemy : enemies) {
            if (enemy.getHealth() > 0 && enemy.isBodyActive()) {
                /** проверим расстояние до вражеского юнита, можем ли мы двигаться к нему (успеем ли..)
                 * если да, то добавим его в массив вражеских юнитов, которых видит ИГРОВОЙ ЮНИТ
                 * **/
                // позиция вражеского юнита
                float x = enemy.getPosition().x + 24;
                float y = enemy.getPosition().y;
//                System.out.println("EnemyX = " + x);
//                System.out.println("EnemyY = " + y);
                Vector2 enemyPosition = new Vector2();
                enemyPosition.set(x, y);
                // позиция игрового юнита
                float x2 = model.getPosition().x + model.getBodySize().x;
                float y2 = model.getPosition().y + model.getBodySize().y / 2;
//                System.out.println("PlayerX = " + x2);
//                System.out.println("PlayerY = " + y2);
                Vector2 playerPosition = new Vector2();
                playerPosition.set(x2, y2);
                /** проверим расстояяние до вражеского юнита, можем ли мы двигаться к нему (успеем ли..)
                 * если да, то добавим его в массив вражеских юнитов, которых видит ИГРОВОЙ ЮНИТ
                 * **/
                if (checkDistanceToEnemy(enemy)) {
//                    System.out.println("checkDistance to enemy = TRUE");
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
        Vector2 playerPosition = new Vector2(model.getX(), model.getY());
//            Vector2 playerPosition = new Vector2(model.getX(), model.getY());
        Vector2 enemyPosition = new Vector2();
        EnemyUnitModel target = null;
        Vector2 playerPos = new Vector2();
        playerPos.set(model.getX(), model.getY());
        if (targetEnemies.size() > 0) {
            enemyPosition.set(targetEnemies.get(0).getX(), targetEnemies.get(0).getY());
            Vector2 distance = enemyPosition.sub(playerPosition);
//            System.out.println("DISTANCE = " + distance.len());
            minDistance = distance.len();
            target = targetEnemies.get(0);
            // найдем самого близкого вражеского юнита к игровому
            for (EnemyUnitModel enemyUnit : targetEnemies) {
                Vector2 enemyPos = new Vector2();
                enemyPos.set(enemyUnit.getX(), enemyUnit.getY());
                float distanceToEnemy = enemyPos.sub(playerPos).len();
                if (distanceToEnemy < minDistance) {
                    minDistance = distanceToEnemy;
                    target = enemyUnit;
//                    System.out.println("set TargetEnemy  = " + target.getName());
//                        targetEnemy = enemyUnit;
                }
            }
        }
//        System.out.println("TARGET ENEMY = " + target.getName());
        if (target != null) {
            if (target.getHealth() > 0) {
//                calculateVerticalDirection(target);       // вычислим направление вертикального перемещения
//                isHaveTargetEnemy = true;
//                model.setIsHaveTargetEnemy(true);
                return target;
            } else {
                model.setIsHaveVerticalDirection(false);
//                isHaveVerticalDirection = false;
                isHaveTargetEnemy = false;
                model.setIsHaveTargetEnemy(false);
                verticalDirectionMovement = ArcherController.Direction.NONE;
            }
        } else {
            model.setIsHaveVerticalDirection(false);
//            isHaveVerticalDirection = false;
            isHaveTargetEnemy = false;
            model.setIsHaveTargetEnemy(false);
            verticalDirectionMovement = ArcherController.Direction.NONE;
        }
        return target;
    }

    /**
     * Метод для вычисления направления вертикального перемещения
     **/
    private ArcherController.Direction calculateVerticalDirection(EnemyUnitModel target) {
        System.out.println("Calculate Vertical direction!");
        float posY = model.getY();
        float posYTarget = target.getY();
        if (posY < posYTarget) verticalDirectionMovement = ArcherController.Direction.UP;
        if (posY > posYTarget) verticalDirectionMovement = ArcherController.Direction.DOWN;
        if (posY == posYTarget) verticalDirectionMovement = ArcherController.Direction.NONE;
        model.setIsHaveVerticalDirection(true);
//        isHaveVerticalDirection = true;
        return verticalDirectionMovement;
    }
}
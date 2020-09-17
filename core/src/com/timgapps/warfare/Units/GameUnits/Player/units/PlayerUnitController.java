package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Barricade;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitController;

import java.util.ArrayList;

public class PlayerUnitController extends GameUnitController {
    private PlayerUnitModel model;
    private PlayerUnitView view;
    private boolean isTouchedEnemy;
    private boolean isTouchedBarricade;
    private boolean isAttackEnemy;
    private boolean isAttackBarricade;
    private Vector2 velocity;
    private EnemyUnitModel targetEnemy;
    private boolean isHaveTargetEnemy;
    private Barricade barricade;

    public PlayerUnitController(Level level, PlayerUnitModel model) {
        super(model);
        this.model = model;
        this.level = level;
        barricade = level.getBarricade();
    }

    // метод обновления логики игрового юнита
    @Override
    public void update() {
        super.update();
        targetEnemy = findEnemyUnit();
        if (model.isTouchedEnemy()) {
//        if (isTouchedEnemy) {
            attackEnemy();
        } else if (model.isHaveTargetEnemy()) {
            System.out.println("Target Enemy = " + targetEnemy.getName());
            if (isTouchedEnemy) {
                attackEnemy();
            } else {
                moveToTarget();
            }
        } else if (barricade.getHealth() > 0) {
            if (isTouchedBarricade) {
                attackBarricade();
            }
//            else moveRight();
        } else {
            moveRight();
        }
    }

    // метод для движения вправо
    public void moveRight() {
        System.out.println("moveRight");
        model.getBody().setLinearVelocity(model.getSpeed(), 0);
    }

    // метод для атаки вражеского юнита
    public void attackEnemy() {
        model.getBody().setLinearVelocity(0, 0);
        System.out.println("attackEnemy");
    }

    // метод для атаки баррикады
    public void attackBarricade() {
        System.out.println("Attack Barricade!");
    }

    // метод для поиска вражеского юнита (юнит которого будем атаковать)
    public EnemyUnitModel findEnemyUnit() {
        /** массив вражеских юнитов **/
        ArrayList<EnemyUnitModel> enemies = level.getArrayEnemies();
        /** массив вражеских юнитов - "потенциальных целей" **/
        ArrayList<EnemyUnitModel> targetEnemies = new ArrayList<EnemyUnitModel>();
        /** выполним поиск ВРАЖЕСКОГО ЮНИТА-ЦЕЛЬ **/
//        for (int i = 0; i < enemies.size(); i++) {
        for (EnemyUnitModel enemy : enemies) {
            /** проверим расстояяние до вражеского юнита, можем ли мы двигаться к нему (успеем ли..)
             * если да, то добавим его в массив вражеских юнитов, которых видит ИГРОВОЙ ЮНИТ
             * **/
            Vector2 v2 = enemy.getPosition();
            Vector2 v1 = model.getPosition();
            Vector2 bodySize = model.getBodySize();     // размер тела (w, h);
            Vector2 vectorUp = new Vector2(v1);
            Vector2 vectorDown = new Vector2(v1);
            v1.add(bodySize.x / 2, 0);
            v2.add(-enemy.getBodySize().x / 2, 0);
            vectorUp.add(bodySize.x, 200);
            vectorUp.add(bodySize.x, -200);
            if (Intersector.isPointInTriangle(v1, v2, vectorUp, vectorDown) && (v2.x > v1.x)) {      // если вражеский юнит находится в пределах видимости, то добавляем его в массив
                targetEnemies.add(enemy);                                  // потенциальных целей
            }
        }
        /** здесь определим самого ближнего ВРАЖЕСКОГО ЮНИТА к ИГРОВОМУ
         * т.е. найдем по расстоянию между ними, т.е. самое маленькое расстояние
         * **/
        float minDistance;
        Vector2 playerPosition = model.getPosition();                   // позиция игрового юнита
        EnemyUnitModel target = null;
        if (targetEnemies.size() > 0) {
            Vector2 enemyPosition = targetEnemies.get(0).getPosition();     // позиция первого вражеского юнита
            Vector2 distance = enemyPosition.sub(playerPosition);           // вектор расстояния между игровым и вражеским юнитом
            minDistance = distance.len();                                   // длина вектора расстояния между вражеским и игровым юнитом
            target = targetEnemies.get(0);                             // вражеский юнит - "цель", к которому будет двигаться игровой юнит
            // найдем самого близкого вражеского юнита к игровому
            for (EnemyUnitModel enemyUnitView : targetEnemies) {
                float distanceToEnemy = enemyUnitView.getPosition().sub(playerPosition).len();
                if (distanceToEnemy < minDistance) {
                    minDistance = distanceToEnemy;
                    targetEnemy = enemyUnitView;
                }
            }
        }
        if (target != null) {
            if (target.getHealth() > 0) {
//                isHaveTargetEnemy = true;
                model.setIsHaveTargetEnemy(true);
            } else {
//                isHaveTargetEnemy = false;
                model.setIsHaveTargetEnemy(false);
            }
        } else {
//            isHaveTargetEnemy = false;
            model.setIsHaveTargetEnemy(false);
        }
        return target;
    }


    // метод для движения к вражескому юниту
    public void moveToTarget() {
        System.out.println("moveToTarget");
        velocity = targetEnemy.getPosition().sub(model.getPosition()).nor().scl(model.getSpeed());
        model.getBody().setLinearVelocity(velocity);
    }

    public boolean isHaveTargetEnemy() {
        return isHaveTargetEnemy;
    }

    public void setTargetEnemy(EnemyUnitModel targetEnemy) {
//    public void setTargetEnemy(EnemyUnitView targetEnemy) {
        if (!this.targetEnemy.equals(targetEnemy))
            this.targetEnemy = targetEnemy;
    }

    // проверяет коснулся ли юнит врага
    public boolean isTouchedEnemy() {
        return isTouchedEnemy;
    }

    // устанавливает коснулся ли юнит врага
    public void setIsTouchedEnemy(boolean isTouchedEnemy) {
        System.out.println("isTouchedEnemy = " + isTouchedEnemy);
        this.isTouchedEnemy = isTouchedEnemy;
    }
}

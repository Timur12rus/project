package com.timgapps.warfare.Units.GameUnits.Player.interfacesAi;

import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;

public interface PlayerWarriorAi {
    void attackEnemy();

    void attackBarricade();

    void moveToTarget();

    void move();

    void update(float delta);

    EnemyUnitModel findEnemyUnit();
}

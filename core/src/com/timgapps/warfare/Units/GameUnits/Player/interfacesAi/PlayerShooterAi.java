package com.timgapps.warfare.Units.GameUnits.Player.interfacesAi;

import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;

public interface PlayerShooterAi {

    void move();

    void stay();

    void moveToTarget();

    void shootEnemy();

    void attackEnemy();

    void update(float delta);

    EnemyUnitModel findEnemyUnit();
}

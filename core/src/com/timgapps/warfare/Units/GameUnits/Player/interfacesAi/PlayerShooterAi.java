package com.timgapps.warfare.Units.GameUnits.Player.interfacesAi;

import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;

public interface PlayerShooterAi {

    void move();

    void moveToTarget();

    void shootEnemy();

    void update(float delta);

    EnemyUnitModel findEnemyUnit();
}

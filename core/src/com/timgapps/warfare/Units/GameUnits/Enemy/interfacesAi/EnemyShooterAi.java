package com.timgapps.warfare.Units.GameUnits.Enemy.interfacesAi;

import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;

public interface EnemyShooterAi {
    void move();

    void stay();

    void shootPlayer();

    void attackPlayer();

    void update(float delta);

    PlayerUnitModel findPlayerUnit();
}

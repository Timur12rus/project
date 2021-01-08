package com.timgapps.warfare.Units.GameUnits.Enemy.interfacesAi;

import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;

// интерфейс с поиском "игрока - цель"
public interface EnemyWarriorAiTarget {
    void moveToTarget();

    PlayerUnitModel findPlayerUnit();
}

package com.timgapps.warfare.Units.GameUnits.Player.units;

public interface EnemyWarriorAi {
    void attackTower();

    void checkCollisions();

    void update(float delta);
}

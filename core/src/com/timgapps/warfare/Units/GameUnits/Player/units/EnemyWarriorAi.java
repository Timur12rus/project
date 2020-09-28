package com.timgapps.warfare.Units.GameUnits.Player.units;

public interface EnemyWarriorAi {
    void attackTower();

    void attackPlayer();

    void update(float delta);

    void hit();

    void move();

    void hitTower();
}

package com.timgapps.warfare.Units.GameUnits.Enemy.interfacesAi;

// интерфейс поведения вражеского воина (не стреляет)
public interface EnemyWarriorAi {
    void attackTower();

    void attackPlayer();

    void update(float delta);

    void hit();

    void move();

    void hitTower();
}

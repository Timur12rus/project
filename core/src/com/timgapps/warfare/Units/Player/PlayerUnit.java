package com.timgapps.warfare.Units.Player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnit;

public class PlayerUnit extends GameUnit {

    Animation walkAnimation;            // анимация для ходьбы
    protected Animation attackAnimation;          // анимация для атаки
    protected Animation dieAnimation;             // анимация для уничтожения
    protected Animation stayAnimation;            // анимация для стоит
    protected Animation runAnimation;            // анимация для бежит
    protected Animation hartAnimation;            // анимация для получает урон

    protected enum Direction {UP, DOWN, NONE}

    public PlayerUnit(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
    }

    @Override
    public Vector2 getBodyPosition() {
        return null;
    }

    @Override
    public void setHealth(float health) {

    }

    @Override
    public void attack() {

    }

    @Override
    public void createBody(float x, float y) {

    }

    public void setTargetEnemy(EnemyUnit enemyUnit) {

    }
}

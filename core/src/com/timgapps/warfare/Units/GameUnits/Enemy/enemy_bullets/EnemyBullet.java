package com.timgapps.warfare.Units.GameUnits.Enemy.enemy_bullets;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Bullet;

public class EnemyBullet extends Bullet {
    public EnemyBullet(Level level, Vector2 position, float damage) {
        super(level, position, damage);
    }

    @Override
    protected Rectangle createBody() {
        return null;
    }
}

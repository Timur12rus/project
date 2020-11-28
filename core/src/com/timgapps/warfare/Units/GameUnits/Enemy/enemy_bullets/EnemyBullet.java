package com.timgapps.warfare.Units.GameUnits.Enemy.enemy_bullets;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Bullet;

public class EnemyBullet extends Bullet {
    public EnemyBullet(LevelScreen levelScreen, Vector2 position, float damage) {
        super(levelScreen, position, damage);
    }

    @Override
    protected Rectangle createBody() {
        return null;
    }
}

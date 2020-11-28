package com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Bullet;

public class SmallRock extends Bullet {
    public SmallRock(LevelScreen levelScreen, Vector2 position, Vector2 velocity, float damage) {
        super(levelScreen, position, damage);
        this.velocity = velocity;
        isDebug = true;
        levelScreen.addChild(this, position.x, position.y);
    }

    @Override
    protected Rectangle createBody() {
        Rectangle body = new Rectangle();
        body.set(position.x, position.y, 16, 16);
        return body;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }
}

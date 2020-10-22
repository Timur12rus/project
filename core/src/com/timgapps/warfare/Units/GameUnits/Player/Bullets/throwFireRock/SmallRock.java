package com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Bullet;

public class SmallRock extends Bullet {
    public SmallRock(Level level, Vector2 position, Vector2 velocity, float damage) {
        super(level, position, damage);
        this.velocity = velocity;
        isDebug = true;
        level.addChild(this, position.x, position.y);
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

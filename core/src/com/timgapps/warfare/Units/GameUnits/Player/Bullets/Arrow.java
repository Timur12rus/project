package com.timgapps.warfare.Units.GameUnits.Player.Bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class Arrow extends Bullet {
    public Arrow(Level level, Vector2 position, float damage, Vector2 velocity) {
        super(level, position, damage);
        image = new TextureRegion(Warfare.atlas.findRegion("arrow"));
        this.velocity.set(velocity);
//        velocity.set(10f, 0);
        level.addChild(this, position.x, position.y);
        this.toFront();
        isDebug = true;
        deltaX = 12;
        deltaY = 42;
    }

    @Override
    protected Rectangle createBody() {
        Rectangle body = new Rectangle();
        body.set(position.x, position.y, 16, 24);
        return body;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (EnemyUnitModel enemy : level.getArrayEnemies()) {
            if (enemy.isBodyActive()) {
                isTouchedEnemy = checkCollision(body, enemy.getBody());     // если коснулся вражеского юнита
            }
            if (isTouchedEnemy) {
                targetEnemy = enemy;            // задаем врага-цель
                break;
            }
        }

        if (isTouchedEnemy && !isDestroyed) {
            if (targetEnemy != null) {
                targetEnemy.subHealth(damage);
                System.out.println("TargetEnemy health = " + targetEnemy.getHealth());
                targetEnemy = null;
            }
            isTouchedEnemy = false;
            isDestroyed = true;
            remove();
        }
    }

    public boolean checkCollision(Rectangle bodyA, Rectangle bodyB) {
        if (Intersector.overlaps(bodyA, bodyB)) {
            return true;
        } else {
            return false;
        }
    }
}

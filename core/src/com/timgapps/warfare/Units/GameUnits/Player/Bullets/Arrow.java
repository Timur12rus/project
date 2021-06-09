package com.timgapps.warfare.Units.GameUnits.Player.Bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Warfare;

public class Arrow extends Bullet {
    public Arrow(LevelScreen levelScreen, Vector2 position, float damage, Vector2 velocity) {
        super(levelScreen, position, damage);
        image = new TextureRegion(Warfare.atlas.findRegion("arrow"));
        this.velocity.set(velocity);
        levelScreen.addChild(this, position.x, position.y);
        this.toFront();
        isDebug = true;
        deltaX = 12;
        deltaY = 42;
        Warfare.media.playSound("shootArrow.ogg");
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
        for (EnemyUnitModel enemy : levelScreen.getArrayEnemies()) {
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
                Warfare.media.playSound("hit1.ogg");
//                System.out.println("TargetEnemy health = " + targetEnemy.getHealth());
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

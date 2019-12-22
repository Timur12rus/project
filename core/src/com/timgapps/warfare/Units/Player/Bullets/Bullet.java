package com.timgapps.warfare.Units.Player.Bullets;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.Interfaces.IBody;

public class Bullet extends Actor implements IBody {

    protected Body body;
    protected Level level;
    protected World world;
    protected float damage;
    protected boolean isDamaged = false;
    protected boolean isDestroyed = false;

    public Bullet(Level level, float x, float y) {
        this.level = level;
        world = level.getWorld();

    }

    @Override
    public void createBody(float x, float y) {

    }

    public void inflictDamage(EnemyUnit enemyUnit) {
        if (!isDamaged) {
            enemyUnit.setHealth(damage);
            isDamaged = true;
            body.setLinearVelocity(0, 0);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!body.isActive() && isDestroyed) {
            world.destroyBody(body);
            this.remove();
        }

        if (isDestroyed) {
            body.setActive(false);
        }
        if (isDamaged) {
            destroy();
        }
    }

    protected void destroy() {
        if (!isDestroyed) {
            isDestroyed = true;
        }
    }
}

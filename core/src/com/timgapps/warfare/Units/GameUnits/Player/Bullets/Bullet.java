package com.timgapps.warfare.Units.GameUnits.Player.Bullets;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnits.GameUnit;
import com.timgapps.warfare.Units.GameUnits.Interfaces.IBody;

public abstract class Bullet extends Actor implements IBody {

    protected Body body;
    protected Level level;
    protected World world;
    protected float damage;
    protected boolean isDamaged = false;
    protected boolean isDestroyed = false;

    public Bullet(Level level, float x, float y) {
        this.level = level;
        world = level.getWorld();
//        body = createBody(x, y);
    }

//    @Override
//    public Body createBody(float x, float y) {
////        BodyDef def = new BodyDef();
////        def.type = BodyDef.BodyType.DynamicBody;
////        body = world.createBody(def);
////
////        PolygonShape shape = new PolygonShape();
////        shape.setAsBox(2 / Level.WORLD_SCALE, 2 / Level.WORLD_SCALE);
////
////        FixtureDef fDef = new FixtureDef();
////        fDef.shape = shape;
////        fDef.filter.categoryBits = GameUnit.BULLET_BIT;
////        fDef.filter.maskBits = GameUnit.ENEMY_BIT;
////
////        body.createFixture(fDef).setUserData(this);
////        shape.dispose();
////        body.setTransform((x + 24) / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);
////        return body;
//        return null;
//    }

    public void inflictDamage(EnemyUnit enemyUnit) {
        if (!isDamaged) {
            enemyUnit.setHealth(damage);
            isDamaged = true;
            destroy();
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
//        if (isDamaged) {
//            destroy();
//        }

        setPosition(body.getPosition().x * Level.WORLD_SCALE, body.getPosition().y * Level.WORLD_SCALE);
    }

    protected void destroy() {
        if (!isDestroyed) {
            isDestroyed = true;
        }
    }
}

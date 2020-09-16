package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;
import com.timgapps.warfare.Units.GameUnits.UnitData;

public class EnemyUnitModel extends GameUnitModel {
    private float damage;
    //    private float health;
//    private float speed;
    protected float bodyWidth = 48;
    protected float bodyHeight = 24;
    private EnemyUnitData enemyUnitData;
    private String name;

    public EnemyUnitModel(World world, Vector2 position, EnemyUnitData enemyUnitData) {
        super(world, position);
        this.enemyUnitData = enemyUnitData;
        damage = enemyUnitData.getDamage();
        health = enemyUnitData.getHealth();
        speed = enemyUnitData.getSpeed();
        name = enemyUnitData.getName();
        body = createBody(world);
    }

    public float getHealth() {
        return health;
    }

    public Vector2 getBodySize() {
        return new Vector2(bodyWidth, bodyHeight);
    }

    public String getName() {
        return name;
    }

    public EnemyUnitData getUnitData() {
        return enemyUnitData;
    }

    public float getDamage() {
        return damage;
    }

    @Override
    protected Body createBody(World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bodyWidth / 2) / Level.WORLD_SCALE, (bodyHeight / 2) / Level.WORLD_SCALE);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.filter.categoryBits = ENEMY_BIT;
        fDef.filter.maskBits = PLAYER_BIT;
        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        body.setTransform(position.x / Level.WORLD_SCALE, position.y / Level.WORLD_SCALE, 0);
        return body;
    }
}

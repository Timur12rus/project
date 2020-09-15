package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.timgapps.warfare.Level.GUI.Screens.PlayerUnitData;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;
import com.timgapps.warfare.Units.GameUnits.UnitData;

public class PlayerUnitModel extends GameUnitModel {
    private float speed;
    private String name;
    private float damage;
    private PlayerUnitData playerUnitData;      // данные юнита (тип, урон, здоровье
    private Vector2 position;
    protected float bodyWidth = 48;
    protected float bodyHeight = 24;
    private int energyPrice;

    public PlayerUnitModel(World world, Vector2 position, PlayerUnitData playerUnitData) {
        super(world, position);
        this.playerUnitData = playerUnitData;
        speed = playerUnitData.getSpeed();
        name = playerUnitData.getName();
        damage = playerUnitData.getDamage();
        energyPrice =  playerUnitData.getEnergyPrice();
        this.position = position;
        body = createBody(world);
    }

    public PlayerUnitData getPlayerUnitData() {
        return playerUnitData;
    }

    // возвращает значение урона, который наносит юнит
    public float getDamage() {
        return damage;
    }

    public Body createBody(World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bodyWidth / 2) / Level.WORLD_SCALE, (bodyHeight / 2) / Level.WORLD_SCALE);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.filter.categoryBits = PLAYER_BIT;
        fDef.filter.maskBits = ENEMY_BIT | BARRICADE_BIT;
        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        body.setTransform(position.x / Level.WORLD_SCALE, position.y / Level.WORLD_SCALE, 0);
        return body;
    }

    public UnitData getUnitData() {
        return playerUnitData;
    }
}

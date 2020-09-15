package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Interfaces.IBody;

public class GameUnitModel {
    protected Vector2 position;
    protected World world;
    protected Body body;
    private float health;
    protected float fullHealth;
    protected float speed;
    protected boolean isDraw;
    private boolean isDestroyed = false;
    private boolean isBodyInactive = false;
    public static final short PLAYER_BIT = 1;
    public static final short ENEMY_BIT = 2;
    public static final short BULLET_BIT = 4;
    public static final short STONE_BIT = 8;
    public static final short BARRICADE_BIT = 16;
    public static final short TOWER_BIT = 32;
    protected UnitData unitData;

    public GameUnitModel(World world, Vector2 position) {
        this.world = world;
        this.position = position;
        this.body = createBody(position);
    }

    // возвращает координаты тела в пикселях
    public Vector2 getPosition() {
        Vector2 position = body.getPosition();
        position.x = body.getPosition().x * Level.WORLD_SCALE;
        position.y = body.getPosition().y * Level.WORLD_SCALE;
        return new Vector2(body.getPosition().x * Level.WORLD_SCALE, body.getPosition().y * Level.WORLD_SCALE);
    }

    // метод задает позицию тела юнита
    public void setPosition(Vector2 position) {
        this.position.x = position.x;
        this.position.y = position.y;
        body.getPosition().x = position.x / Level.WORLD_SCALE;
        body.getPosition().y = position.y / Level.WORLD_SCALE;
    }

    // возвращает кол-во здоровья у юнита
    public float getHealth() {
        return health;
    }

    public UnitData getUnitData() {
        return unitData;
    }

    // возвращает значение скорости юнита
    public float getSpeed() {
        return speed;
    }

    public Body createBody(Vector2 position) {
        return body;
    }

    public Body getBody() {
        return body;
    }
}

package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

// абстрактный класс, модель игрового юнита
public abstract class GameUnitModel {
    protected Vector2 position;
    protected World world;
    protected Rectangle body;
    protected float health;
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
    protected float bodyWidth;
    protected float bodyHeight;
    private Vector2 velocity;

    public GameUnitModel(World world, Vector2 position) {
        this.world = world;
        this.position = position;
        this.velocity = new Vector2(0, 0);
    }

    // создает прямоугольник (тело - для обнаружения столкновений)
    protected abstract Rectangle createBody();

    // возвращает координаты тела в пикселях (центра прямоугольника)
    public Vector2 getPosition() {
        return position;
    }

//    // метод задает позицию тела юнита
//    public void setPosition(Vector2 position) {
//        this.position.x = position.x;
//        this.position.y = position.y;
//    }

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

    public Rectangle getBody() {
        return body;
    }

    // обновляет позицию тела в соответствии с позицией модели
    public void updateBodyPosition() {
        System.out.println("Position.x = " + position.x);
        System.out.println("Position.y = " + position.y);
        position.add(velocity);
        body.setPosition(position.x, position.y);
    }

    public void setVelocity(Vector2 vel) {
        velocity.set(vel);
    }

    public float getBodyWidth() {
        return bodyWidth;
    }

    public float getBodyHeight() {
        return bodyHeight;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }
}

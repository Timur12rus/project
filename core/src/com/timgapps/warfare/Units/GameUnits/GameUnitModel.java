package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.timgapps.warfare.Level.Level;

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
    protected GameUnitView.State currentState;
    private boolean isEndAttack, isEndStay;
    private boolean isAttack, isStay, isMove;
    protected Level level;
    protected boolean isDamaged;
    protected boolean isBodyActive;
    protected float xPosDamageLabel, yPosDamagelabel;

    public GameUnitModel(Level level, Vector2 position) {
        this.level = level;
        this.position = position;
        this.velocity = new Vector2(0, 0);
        currentState = GameUnitView.State.STAY;
        isBodyActive = true;
    }

    // возвращает активно ли тело
    public boolean isBodyActive() {
        return isBodyActive;
    }

    public void setBodyIsActive(boolean isBodyActive) {
        this.isBodyActive = isBodyActive;
    }

    // создает прямоугольник (тело - для обнаружения столкновений)
    protected abstract Rectangle createBody();

    // возвращает координаты тела в пикселях (центра прямоугольника)
    public Vector2 getPosition() {
        return position;
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

    public Rectangle getBody() {
        return body;
    }

    // обновляет позицию тела в соответствии с позицией модели
    public void updateBodyPosition() {
//        System.out.println("Position.x = " + position.x);
//        System.out.println("Position.y = " + position.y);
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

    public void isEndAttack(boolean isEndAttack) {
        this.isEndAttack = isEndAttack;
    }

    public boolean isAttack() {
        return isAttack;
    }

    public void setIsAttack(boolean isAttack) {
        this.isAttack = isAttack;
    }

    public boolean isStay() {
        return isStay;
    }

    public void setIsStay(boolean isStay) {
        this.isStay = isStay;
    }

    public void setIsMove(boolean isMove) {
        this.isMove = isMove;
    }

    public boolean isMove() {
        return isMove;
    }

    public GameUnitView.State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameUnitView.State currentState) {
        this.currentState = currentState;
    }

    // метод для получения урона от противника, уменьшает кол-во здоровья на значение "damage"
    public void subHealth(float damage) {
        health -= damage;
        if (health <= 0) {
            isBodyActive = false;
        }
    }

    public void setIsDamaged(boolean isDamaged) {
        this.isDamaged = isDamaged;
    }

    public boolean isDamaged() {
        return isDamaged;
    }
}

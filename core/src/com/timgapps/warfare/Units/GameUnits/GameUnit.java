package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.Zombie;
import com.timgapps.warfare.Units.GameUnits.Enemy.Zombie1;
import com.timgapps.warfare.Units.GameUnits.Interfaces.IBody;
import com.timgapps.warfare.Units.GameUnits.Player.Gnome;

public abstract class GameUnit extends Actor implements IBody {

    protected Rectangle bodyRectangle;
    protected Vector2 bodyRectanglePosition;

    protected World world;
    protected float health;
    protected float damage;
    protected Level level;
    protected boolean setToDestroyBody = false;
    protected boolean isDraw;                        //  переменная указывает рисовать данного актера или нет

    protected Vector2 velocity;

    protected ShapeRenderer shapeRenderer;
    /**
     * переменная отвечает за то, отрисовывать ли прямоугольник для определения коллизий с камнем
     **/
    protected boolean isDebug = true;

    public static final short PLAYER_BIT = 1;
    public static final short ENEMY_BIT = 2;
    public static final short BULLET_BIT = 4;
    public static final short STONE_BIT = 8;
    public static final short BARRICADE_BIT = 16;
    public static final short TOWER_BIT = 32;

    private boolean isDestroyed = false;

    protected float stateTime;

    protected Body body;

    public enum State {WALKING, ATTACK, STAY, DIE, RUN, HART}

    public State currentState;

    /**
     * конструктор
     **/
    public GameUnit(Level level, float x, float y, float health, float damage) {
        this.level = level;
        this.health = health;
        this.damage = damage;
        this.world = level.getWorld();

        body = createBody(x, y);
        isDraw = true;              // isDraw = true - значит отрисовывается актёр

        currentState = State.STAY;

        bodyRectangle = new Rectangle();
        bodyRectanglePosition = new Vector2(x, y);          // позиция прямоугольника "тела"


        if (isDebug) {                                      // проверим, если true отрисовываем прямоугольник "тело"
            shapeRenderer = new ShapeRenderer();
        }
    }

    public Vector2 getBodyPosition() {
        return body.getPosition();
    }

    /**
     * метод для установки занчения "здоровья" у персонажа, также используется при получении урона от персонажей игрока
     *
     * @param damage - урон
     **/
    public void setHealth(float damage) {
        health = -damage;
    }

    /**
     * метод устанавливает отрисовывать ли актера
     *
     * @param draw
     **/
    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    /**
     * метод для атаки
     **/
    public void attack() {

    }

    /**
     * Метод для получения текущего состояния
     **/
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Метод для установки текущего состояния
     **/
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public void inflictDamage(GameUnit unit, float damage) {
        if (unit instanceof Zombie) {
            ((Zombie) unit).setHealth(damage);
        }
        if (unit instanceof Zombie1) {
            ((Zombie1) unit).setHealth(damage);
        }
        if (unit instanceof Gnome) {
            ((Gnome) unit).setHealth(damage);
        }
    }

    /**
     * метод для получения значения здоровья
     **/
    public float getHealth() {
        return health;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();
        if (isDebug) {
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(bodyRectangle.getX(), bodyRectangle.getY(), bodyRectangle.getWidth(), bodyRectangle.getHeight());
            shapeRenderer.end();
            batch.begin();
        }
    }

    public float getDamage() {
        return damage;
    }

    public void setToDestroy() {
        isDestroyed = true;
    }

    public void checkToDestroy() {
        if (!body.isActive() && isDestroyed) {
            world.destroyBody(body);
            this.remove();
        }

        if (isDestroyed) {
            body.setActive(false);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        checkToDestroy();
    }
}



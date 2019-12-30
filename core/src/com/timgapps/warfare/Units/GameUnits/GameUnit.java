package com.timgapps.warfare.Units.GameUnits;

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
    protected World world;
    protected float health;
    protected float damage;
    protected Level level;
    protected boolean setToDestroyBody = false;
    protected boolean isDraw;                        //  переменная указывает рисовать данного актера или нет

    protected Vector2 velocity;

    public static final short PLAYER_BIT = 1;
    public static final short ENEMY_BIT = 2;
    public static final short BULLET_BIT = 4;

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

}



package com.timgapps.warfare.Units;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.Enemy.Zombie;
import com.timgapps.warfare.Units.Enemy.Zombie1;
import com.timgapps.warfare.Units.Interfaces.IBody;
import com.timgapps.warfare.Units.Player.Gnome;

public abstract class GameUnit extends Actor implements IBody {
    protected float health;
    protected float damage;
    protected Level level;
    protected boolean setToDestroyBody = false;
    public static final short PLAYER_BIT = 1;
    public static final short ENEMY_BIT = 2;

    public enum State {WALKING, ATTACK, STAY, DIE, RUN}
    public State currentState = State.STAY;

    /** конструктор **/
    public GameUnit(Level level, float x, float y, float health, float damage) {
        this.level = level;
        this.health = health;
        this.damage = damage;
    }

    public abstract Vector2 getBodyPosition();

    public abstract float getHealth();

    public abstract void setHealth(float health);

    public abstract void attack();

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

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}



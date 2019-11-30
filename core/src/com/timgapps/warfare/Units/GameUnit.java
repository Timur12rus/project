package com.timgapps.warfare.Units;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.Interfaces.IBody;

public abstract class GameUnit extends Actor implements IBody {
    protected float health;
    protected float damage;
    protected Level level;

    public GameUnit(Level level, float x, float y, float health, float damage) {
        this.level = level;
        this.health = health;
        this.damage = damage;
    }

    public abstract Vector2 getBodyPosition();

}

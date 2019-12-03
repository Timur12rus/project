package com.timgapps.warfare.Units.Player;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnit;

public class PlayerUnit extends GameUnit {
    public PlayerUnit(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
    }

    @Override
    public Vector2 getBodyPosition() {
        return null;
    }

    @Override
    public float getHealth() {
        return 0;
    }

    @Override
    public void setHealth(float health) {

    }

    @Override
    public void attack() {

    }

    @Override
    public void createBody(float x, float y) {

    }
}

package com.timgapps.warfare.Units.Enemy;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnit;
import com.timgapps.warfare.Units.Player.PlayerUnit;

public class EnemyUnit extends GameUnit {

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public boolean isDraw() {
        return isDraw;
    }

    private boolean isDraw = true;

    public EnemyUnit(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
    }

    @Override
    public Vector2 getBodyPosition() {
        return null;
    }

    @Override
    public void setHealth(float health) {

    }

    @Override
    public void attack() {

    }

    public void setTargetPlayer(PlayerUnit targetPlayer) {

    }

    @Override
    public void createBody(float x, float y) {

    }
}

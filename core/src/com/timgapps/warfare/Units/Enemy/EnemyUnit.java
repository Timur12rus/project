package com.timgapps.warfare.Units.Enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnit;
import com.timgapps.warfare.Units.Player.PlayerUnit;

public class EnemyUnit extends GameUnit {

    protected Rectangle rectangle;
    private ShapeRenderer shapeRenderer;

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    private boolean isDebug = true;

    public boolean isDraw() {
        return isDraw;
    }

    private boolean isDraw = true;

    public EnemyUnit(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);

        rectangle = new Rectangle();
        rectangle.setSize(40, 10);
        if (isDebug) {
            shapeRenderer = new ShapeRenderer();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        if (body != null) {
            rectangle.setPosition(getX(), getY() - 20);
//        }
    }

    @Override
    public Vector2 getBodyPosition() {
        return null;
    }

    @Override
    public void setHealth(float health) {
        System.out.println("setHealth");
    }


    @Override
    public void attack() {

    }

    public void setTargetPlayer(PlayerUnit targetPlayer) {

    }

    @Override
    public void createBody(float x, float y) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (isDebug) {
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(rectangle.getX(), rectangle.getY(), 60, 10);
//            shapeRenderer.rect(getX(), getY() - 20, 40, 10);
            shapeRenderer.end();
            batch.begin();
        }
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}

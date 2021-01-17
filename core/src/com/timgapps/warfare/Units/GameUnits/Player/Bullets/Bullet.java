package com.timgapps.warfare.Units.GameUnits.Player.Bullets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;

public abstract class Bullet extends Actor {
    protected LevelScreen levelScreen;
    protected Rectangle body;
    protected float damage;
    protected Vector2 position;
    protected Vector2 velocity;
    protected TextureRegion image;
    protected boolean isDebug;
    protected ShapeRenderer shapeRenderer;
    protected float deltaX, deltaY;
    protected boolean isTouchedEnemy;
    protected EnemyUnitModel targetEnemy;
    protected boolean isDestroyed;
    protected boolean isStarted;

    public Bullet(LevelScreen levelScreen, Vector2 position, float damage) {
        this.position = position;
        this.levelScreen = levelScreen;
        this.damage = damage;
        body = createBody();
        shapeRenderer = new ShapeRenderer();
        velocity = new Vector2();
        isStarted = true;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (levelScreen.getState() != LevelScreen.PAUSED && isStarted && !isDestroyed) {
            position.add(velocity);
            body.setX(position.x);
            body.setY(position.y);
            setPosition(position.x, position.y);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (image != null) {
            batch.draw(image, getX() + deltaX, getY() + deltaY, image.getRegionWidth() / 2, image.getRegionHeight() / 2,
                    image.getRegionWidth(), image.getRegionHeight(), 1, 1, getRotation());
        }
//        batch.draw(image, getX() + deltaX, getY() + deltaY);
        if (isDebug) {
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(getX(), getY(), body.getWidth(), body.getHeight());
            shapeRenderer.end();
            batch.begin();
        }
    }

    protected abstract Rectangle createBody();
}

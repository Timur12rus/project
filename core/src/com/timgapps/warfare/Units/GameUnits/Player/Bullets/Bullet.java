package com.timgapps.warfare.Units.GameUnits.Player.Bullets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.timgapps.warfare.Level.Level;

import org.lwjgl.opengl.NVTextureEnvCombine4;

public abstract class Bullet extends Actor {
    private Level level;
    protected Rectangle body;
    protected float damage;
    protected Vector2 position;
    protected Vector2 velocity;
    protected TextureRegion image;
    protected boolean isDebug;
    private ShapeRenderer shapeRenderer;
    protected float deltaX, deltaY;

    public Bullet(Level level, Vector2 position) {
        this.position = position;
        this.level = level;
        body = createBody();
        shapeRenderer = new ShapeRenderer();
        velocity = new Vector2();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (level.getState() != Level.PAUSED) {
            position.add(velocity);
            setPosition(position.x, position.y);
        }
        System.out.println("Body Width = " + body.getWidth());
        System.out.println("Body posX = " + body.getX());
        System.out.println("Body posY = " + body.getY());

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(image, getX() + deltaX, getY() + deltaY);
        if (isDebug) {
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(getX(), getY(), body.getWidth(), body.getHeight());
            shapeRenderer.end();
            batch.begin();
        }
    }

    abstract Rectangle createBody();
}

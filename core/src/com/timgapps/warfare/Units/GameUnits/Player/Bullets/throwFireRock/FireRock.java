package com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Effects.Explosion;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Bullet;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class FireRock extends Bullet {
    private Vector2 startPosition;
    private Vector2 endPosition;
    private boolean isStartSmallRocks;
    private Rectangle explosionRectangle;
    private final float EXPLOSION_WIDTH = 260;
    private final float EXPLOSION_HEIGHT = 280;
    private ArrayList<EnemyUnitModel> targetEnemies;
    private float speed = 10;
    private Level level;
    private ParticleEffect fireEffect;
    private float waitTimeToDestroy = 20;
    private Explosion explosion;


    public FireRock(Level level, Vector2 startPosition, Vector2 endPosition, float damage) {
        super(level, startPosition, damage);
//        Vector2 newEndPos = new Vector2();
//        newEndPos.set(endPosition.x - EXPLOSION_WIDTH / 2, endPosition.y - EXPLOSION_HEIGHT / 2);
//        this.endPosition = newEndPos;
        this.startPosition = startPosition;
//        endPosition.set(endPosition.x, endPosition.y);
        endPosition.set(endPosition.x, endPosition.y - 12);
        this.endPosition = endPosition;
        System.out.println("END POSITION = " + endPosition);
        image = new TextureRegion(Warfare.atlas.findRegion("fireRock"));
        Vector2 endPos = new Vector2(endPosition.x, endPosition.y);
        Vector2 velocityDirection = new Vector2(endPos.sub(startPosition)).nor();
        velocity.set(velocityDirection.scl(speed));
//        velocity.set(0, 0);
        level.addChild(this, position.x, position.y);
        this.level = level;
        this.toFront();
//        isDebug = true;
        setSize(image.getRegionWidth(), image.getRegionHeight());
        this.debug();
        explosionRectangle = new Rectangle();
        explosionRectangle.set(position.x - EXPLOSION_WIDTH / 2, position.y - EXPLOSION_HEIGHT / 2, EXPLOSION_WIDTH, EXPLOSION_HEIGHT);
        targetEnemies = new ArrayList<EnemyUnitModel>();
        fireEffect = new ParticleEffect();
        fireEffect.load(Gdx.files.internal("effects/artFire10.paty"), Gdx.files.internal("effects/")); //file);     //Air2.paty
        fireEffect.setPosition(position.x, position.y);
        fireEffect.start();
        explosion = new Explosion();
    }

    public void start() {
        Vector2 velocityDirection = new Vector2(endPosition.sub(startPosition)).nor();
        velocity.set(velocityDirection.scl(speed));
        level.addChild(this, position.x, position.y);
    }

    @Override
    protected Rectangle createBody() {
        Rectangle body = new Rectangle();
        body.set(position.x, position.y, 16, 16);
        return body;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isDestroyed) {
            super.draw(batch, parentAlpha);
        }
        if (isDebug) {
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(getX() - (EXPLOSION_WIDTH - image.getRegionWidth()) / 2,
                    getY() - (EXPLOSION_HEIGHT - image.getRegionHeight()) / 2,
                    explosionRectangle.getWidth(), explosionRectangle.getHeight());
            shapeRenderer.end();
            batch.begin();
        }
        fireEffect.draw(batch);
//        if (level.getState() != Level.PAUSED) {
//            fireEffect.draw(batch);
//        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        explosionRectangle.setX(position.x - (EXPLOSION_WIDTH - image.getRegionWidth()) / 2);
//        explosionRectangle.setY(position.y - (EXPLOSION_HEIGHT - image.getRegionHeight()) / 2);
        if (isDestroyed) {
            waitTimeToDestroy--;
            if (waitTimeToDestroy < 0) {
//            if (explosion.isEnd()) {
                fireEffect.dispose();
                this.remove();
            }
//            }
        } else {
            fireEffect.setPosition(position.x + getWidth() / 2, position.y + getHeight() / 2);
//            if (level.getState() != Level.PAUSED) {
//                fireEffect.update(delta);
//            }
//            fireEffect.draw(batch);
//        }

            System.out.println("POSITION Y = " + position.y);
            System.out.println("END POSITION = " + endPosition);
            if ((position.y < (endPosition.y + EXPLOSION_HEIGHT / 2)) && !isStartSmallRocks) {
                explosionRectangle.setX(position.x - (EXPLOSION_WIDTH - image.getRegionWidth()) / 2);
                explosionRectangle.setY(position.y - (EXPLOSION_HEIGHT - image.getRegionHeight()) / 2);
                System.out.println("POSITION Y = " + position.y);
                System.out.println("END POSITION = " + endPosition);
//            if ((position.x > (endPosition.x + EXPLOSION_WIDTH / 2)) && !isStartSmallRocks) {
//            startSmallRocks();
                isStartSmallRocks = true;

                // наносим урон юнитам в зоне поражения
                try {
                    for (EnemyUnitModel enemy : level.getArrayEnemies()) {
                        if (enemy.isBodyActive() && !isTouchedEnemy) {
                            isTouchedEnemy = checkCollision(explosionRectangle, enemy.getBody());     // если коснулся зоны поражения
                        }
                        if (isTouchedEnemy) {
                            enemy.subHealth(damage);
                        }
                        isTouchedEnemy = false;
                    }
                } catch (Exception e) {
                    System.out.println("Exception = " + e.toString());
                }
                isDestroyed = true;
                //TODO shakeCamera  !!!!!!!! 22.10.2020
//                level.shakeCamera();
//                Explosion explosion = new Explosion();
                explosion.setPosition(position.x - explosion.getWidth() / 2, position.y - explosion.getHeight() / 2);
                System.out.println("POSITION X = " + position.x);
                level.addChild(explosion);
                explosion.start();
            }
        }
        if (level.getState() != Level.PAUSED && waitTimeToDestroy > 0) {
            fireEffect.update(delta);
        }
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    public boolean checkCollision(Rectangle bodyA, Rectangle bodyB) {
        if (Intersector.overlaps(bodyA, bodyB)) {
            return true;
        } else {
            return false;
        }
    }
}
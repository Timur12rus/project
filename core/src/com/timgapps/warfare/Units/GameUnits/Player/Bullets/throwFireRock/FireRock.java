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
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Effects.Explosion;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Bullet;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class FireRock extends Bullet {
    private Vector2 endPosition;
    private boolean isStartSmallRocks;
    private Rectangle explosionRectangle;
//    private final float EXPLOSION_WIDTH = 260;
//    private final float EXPLOSION_HEIGHT = 280;
    private final float EXPLOSION_WIDTH = 160;
    private final float EXPLOSION_HEIGHT = 120;
    private ArrayList<EnemyUnitModel> targetEnemies;
//    private float speed = 5;
        private float speed = 14f;
    private LevelScreen levelScreen;
    private ParticleEffect fireEffect;
    private float waitTimeToDestroy = 20;
    private Explosion explosion;
    private static final float H = 500;
    private float gravity = -8;
    private float delayTime;

    public FireRock(LevelScreen levelScreen, Vector2 endPosition, float damage, float delayTime) {
        super(levelScreen,
                new Vector2(levelScreen.getSiegeTower().getBodyPosition().x,
                        levelScreen.getSiegeTower().getBodyPosition().y + 700), damage);
        this.delayTime = delayTime;
        image = new TextureRegion(Warfare.atlas.findRegion("fireRock"));
        setSize(image.getRegionWidth(), image.getRegionHeight());
        this.endPosition = endPosition;
        System.out.println("End POSITION = " + endPosition);
        float startX = position.x;
        float startY = position.y;
        float endX = endPosition.x;
//        float endX = endPosition.x + getWidth();
        float endY = endPosition.y;
        Vector2 startPos = new Vector2(startX, startY);
        Vector2 endPos = new Vector2(endX, endY);
        Vector2 velocityDirection = (endPos.sub(startPos)).nor();
//        Vector2 velocityDirection = (new Vector2(new Vector2(endPosition.x, endPosition.y)).sub(position)).nor();
//        Vector2 velocityDirection = (new Vector2(endPos).sub(startPos)).nor();
        velocity.set((velocityDirection).scl(speed));
//        Vector2 vel = new Vector2((velocityDirection).scl(speed));
//        velocity.set(vel.x, vel.y);
        System.out.println("start Position = " + startPos);
        System.out.println("end Position = " + endPos);
        System.out.println("VelDirection = " + velocityDirection);

//        velocity.set(speed, 5);

        levelScreen.addChild(this, position.x, position.y);
        this.levelScreen = levelScreen;
//        this.toFront();
//        isDebug = true;
        this.debug();
        explosionRectangle = new Rectangle();
        explosionRectangle.set(position.x - EXPLOSION_WIDTH / 2, position.y - EXPLOSION_HEIGHT / 2, EXPLOSION_WIDTH, EXPLOSION_HEIGHT);
        targetEnemies = new ArrayList<EnemyUnitModel>();
        fireEffect = new ParticleEffect();
        fireEffect.load(Gdx.files.internal("effects/artFire10.paty"), Gdx.files.internal("effects/")); //file);     //Air2.paty
        fireEffect.setPosition(position.x, position.y);
        fireEffect.start();
        explosion = new Explosion(levelScreen);
        isStarted = false;
    }

    public void start() {
//        Vector2 velocityDirection = new Vector2(endPosition.sub(startPosition)).nor();
//        velocity.set(velocityDirection.scl(speed));
//        levelScreen.addChild(this, position.x, position.y);
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
        fireEffect.draw(batch);
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
//        fireEffect.draw(batch);
//        if (level.getState() != Level.PAUSED) {
//            fireEffect.draw(batch);
//        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (levelScreen.getState() != LevelScreen.PAUSED && !isDestroyed) {
            if (!isStarted) {
                delayTime -= delta;
                if (delayTime < 0) {
                    isStarted = true;
                }
            }
        }
        if (levelScreen.getState() != LevelScreen.PAUSED && isDestroyed) {
            waitTimeToDestroy--;
            if (waitTimeToDestroy < 0) {
//            if (explosion.isEnd()) {
                fireEffect.dispose();
                levelScreen.removeChild(explosion);
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

//            System.out.println("POSITION Y = " + position.y);
//            System.out.println("END POSITION = " + endPosition);
            if ((position.x + getWidth() / 2 > (endPosition.x))
                    && (position.y < endPosition.y) && !isStartSmallRocks) {
//            if ((position.x > (endPosition.x + EXPLOSION_WIDTH / 2)) && !isStartSmallRocks) {
//            if ((position.y < (endPosition.y + EXPLOSION_HEIGHT / 2)) && !isStartSmallRocks) {
                explosionRectangle.setX(position.x - (EXPLOSION_WIDTH - image.getRegionWidth()) / 2);
                explosionRectangle.setY(position.y - (EXPLOSION_HEIGHT - image.getRegionHeight()) / 2);
//                System.out.println("POSITION Y = " + position.y);
//                System.out.println("END POSITION = " + endPosition);
//            if ((position.x > (endPosition.x + EXPLOSION_WIDTH / 2)) && !isStartSmallRocks) {
//            startSmallRocks();
                isStartSmallRocks = true;

                // наносим урон юнитам в зоне поражения
                try {
                    for (EnemyUnitModel enemy : levelScreen.getArrayEnemies()) {
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
//                levelScreen.shakeCamera();
//                Explosion explosion = new Explosion();
//                explosion.setPosition(position.x - explosion.getWidth() / 2, position.y - getHeight());
                explosion.setPosition(position.x - explosion.getWidth() / 2, position.y - getHeight());
//                System.out.println("POSITION X = " + position.x);
                levelScreen.addChild(explosion);
                explosion.start();
                Warfare.media.playSound("explosion_bomb.ogg");
            }
        }
        if (levelScreen.getState() != LevelScreen.PAUSED && waitTimeToDestroy > 0) {
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

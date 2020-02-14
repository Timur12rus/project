package com.timgapps.warfare.Units.GameUnits.Player.Bullets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnits.GameUnit;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;


public class Stone extends Bullet {

    private float targetY;
    private TextureRegion image;
    private final float VELOCITY = -14;
    private float damage;

    private Rectangle stoneRectangle;

    private ShapeRenderer shapeRenderer;
    private Vector2 velocity;
    private Vector2 position;
    private boolean isDebug = false;
//    private boolean isDebug = true;
    private boolean isDestroyed = false;

    private boolean moveIsEnd = false;
    private boolean isDamaged = false;
    private float health = 50;
//    private static final float APPEARANCE_TIME = 10;
    private static final float APPEARANCE_TIME = 1;

//    private static int ENERGY_PRICE = 6;
    private static int ENERGY_PRICE = 1;

    public Stone(Level level, float x, float y, float damage, float targetY) {
        super(level, x, y);
        this.targetY = targetY;
        this.damage = damage;

        stoneRectangle = new Rectangle(); // прямоугльник для проверки столкновения с игровыми юнитами

        image = new TextureRegion(Warfare.atlas.findRegion("block1"));


        stoneRectangle.setSize(image.getRegionWidth(), 12);
//        stoneRectangle.setSize(image.getRegionWidth(), 10);
        stoneRectangle.setPosition(x - image.getRegionWidth() / 2, y);

        shapeRenderer = new ShapeRenderer();

        velocity = new Vector2(0, VELOCITY);
        position = new Vector2(x - image.getRegionWidth() / 2, y);

        level.addChild(this);
        level.arrayActors.add(this);

        body = createBody(x, y - 300);

//        body.setLinearVelocity(0, VELOCITY);
    }

    @Override
    public void act(float delta) {
//        super.act(delta);


//        if (body.getPosition().y * Level.WORLD_SCALE < targetY) {
//            body.setLinearVelocity(0, 0);
//        }
//
//        body.setAngularVelocity(0);

        if (!body.isActive() && isDestroyed) {
            world.destroyBody(body);
            this.remove();
        }

        if (health <= 0) {
            isDestroyed = true;
            body.setActive(false);
        }


        /** изменим позицию нашего актреа **/
        if ((position.y < targetY)) {

            if (moveIsEnd && !isDamaged) {
                body.setTransform((getX() + 20) / Level.WORLD_SCALE, (getY() + 10) / Level.WORLD_SCALE, 0);
            }
            if (!moveIsEnd)
                checkCollisionEnemyUnit();

        } else {
            position.add(velocity);
        }

//        if (position.y < targetY) {
//            body.setTransform((getX() + 20) / Level.WORLD_SCALE, (getY() + 10) / Level.WORLD_SCALE, 0);
//            System.out.println("bodyY = " + body.getPosition().y * Level.WORLD_SCALE);
//        }

        /** изменим позицию нашего прямоугольника для определения коллизий **/
        stoneRectangle.setPosition(position.x, position.y);
        setPosition(position.x, position.y);
    }

    public void setHealth(float damage) {
//        System.out.println("damage = " + damage);
        health -= damage;
//        System.out.println("health = " + health);
    }

    private void checkCollisionEnemyUnit() {
        ArrayList<EnemyUnit> arrayEnemies = level.getArrayEnemies();
        for (int i = 0; i < arrayEnemies.size(); i++) {

            if (Intersector.overlaps(stoneRectangle, arrayEnemies.get(i).getRectangle())) {
                arrayEnemies.get(i).setHealth(damage);
                isDamaged = true;
                isDestroyed = true;
                body.setActive(false);


            } else {
                moveIsEnd = true;
            }

        }
    }

    public float getHealth() {
        return health;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

//        batch.draw(image, getX() - 20, getY() - 20);
        batch.draw(image, getX(), getY());

        if (isDebug) {
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(getX(), getY(), stoneRectangle.width, stoneRectangle.height);
            shapeRenderer.end();
            batch.begin();
        }
    }

    @Override
    public Body createBody(float x, float y) {
//        super.createBody(x, y);
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(def);

        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(20 / Level.WORLD_SCALE, 6 / Level.WORLD_SCALE);

        fDef.shape = shape;
        fDef.filter.categoryBits = GameUnit.STONE_BIT;
        fDef.filter.maskBits = GameUnit.ENEMY_BIT;
        fDef.density = 10f;

        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        body.setAngularDamping(0);
        body.setLinearDamping(0);
        body.setTransform(x / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);
        return body;
    }

    public static float getAppearanceTime() {
        return APPEARANCE_TIME;
    }

    public static int getEnergyPrice() {
        return ENERGY_PRICE;
    }
}

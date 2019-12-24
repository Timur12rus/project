package com.timgapps.warfare.Units.Player.Bullets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.Enemy.Zombie;
import com.timgapps.warfare.Units.Enemy.Zombie1;
import com.timgapps.warfare.Units.GameUnit;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;


public class Stone extends Bullet {

    private float targetY;
    private TextureRegion image;
    private final float VELOCITY = 5;
    private float damage;

    private Rectangle stoneRectangle;

    private ShapeRenderer shapeRenderer;
    private Vector2 velocity;
    private Vector2 position;
    private boolean isDebug = true;


    public Stone(Level level, float x, float y, float damage, float targetY) {
        super(level, x, y);
        this.targetY = targetY;
        this.damage = damage;
        createBody(x, y);
        stoneRectangle = new Rectangle(); // прямоугльник для проверки столкновения с игровыми юнитами

        image = new TextureRegion(Warfare.atlas.findRegion("block1"));

        stoneRectangle.setSize(image.getRegionWidth(), 10);

        level.addChild(this);
//        body.setLinearVelocity(0, -VELOCITY);
        stoneRectangle.setPosition(x, y);

        shapeRenderer = new ShapeRenderer();

        velocity = new Vector2(0, -5);
        position = new Vector2(x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        if ((body.getPosition().y * Level.WORLD_SCALE - 20) < targetY) {
//            body.setLinearVelocity(0, 0);
//            body.setAngularVelocity(0);
//        }


        /** изменим позицию нашего актреа **/
        if (position.y < targetY) {
            checkCollisionEnemyUnit();

        } else {
            position.add(velocity);
        }

        /** изменим позицию нашего прямоугольника для определения коллизий **/
        stoneRectangle.setPosition(position.x, position.y);

        setPosition(position.x, position.y);
    }

    private void checkCollisionEnemyUnit() {
        ArrayList<EnemyUnit> arrayEnemies = level.getArrayEnemies();
        for (int i = 0; i < arrayEnemies.size(); i++) {

            if (Intersector.overlaps(stoneRectangle, arrayEnemies.get(i).getRectangle())) {
                arrayEnemies.get(i).setHealth(damage);
                stoneRectangle.setSize(0,0);
//                this.remove();
            }

        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

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
    public void createBody(float x, float y) {
        super.createBody(x, y);
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(def);

        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(20 / Level.WORLD_SCALE, 20 / Level.WORLD_SCALE);

        fDef.shape = shape;
        fDef.filter.categoryBits = GameUnit.BULLET_BIT;
        fDef.filter.maskBits = GameUnit.ENEMY_BIT;
        fDef.density = 0.01f;

        body.createFixture(fDef);
        shape.dispose();
//        body.setAngularDamping(0);
//        body.setLinearDamping(0);
        body.setTransform(x / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);
    }
}

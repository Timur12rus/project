package com.timgapps.warfare.Units.GameUnits.Player.Bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnit;
import com.timgapps.warfare.Warfare;

public class Arrow extends Bullet {

    private TextureRegion image;
    private final float VELOCITY = 10;

    public Arrow(Level level, float x, float y, float damage) {
        super(level, x, y);
        this.damage = damage;
        image = new TextureRegion(Warfare.atlas.findRegion("arrow"));
        createBody(x, y);
        level.addChild(this);
//        level.addChild(this, x, y);
        body = createBody(x,y);
        body.setLinearVelocity(VELOCITY, 0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(body.getPosition().x * Level.WORLD_SCALE, body.getPosition().y * Level.WORLD_SCALE + 32);
    }

    @Override
    public Body createBody(float x, float y) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2 / Level.WORLD_SCALE, 2 / Level.WORLD_SCALE);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.filter.categoryBits = GameUnit.BULLET_BIT;
        fDef.filter.maskBits = GameUnit.ENEMY_BIT;

        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        body.setTransform((x  + 24) / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);
        return body;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(image, getX(), getY());
    }
}

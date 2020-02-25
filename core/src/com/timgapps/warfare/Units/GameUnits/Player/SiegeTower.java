package com.timgapps.warfare.Units.GameUnits.Player;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnit;
import com.timgapps.warfare.Warfare;

public class SiegeTower extends Group {
    private Image tower, frontWheel, backWheel;
    private World world;
    private Level level;
    private float health;

    private Body body;

    private boolean isDestroyed = false;


    public SiegeTower(Level level, float x, float y, float health, float damage) {
        this.level = level;
        world = level.getWorld();
        tower = new Image(Warfare.atlas.findRegion("tower"));
        frontWheel = new Image(Warfare.atlas.findRegion("wheel"));
        backWheel = new Image(Warfare.atlas.findRegion("wheel"));

        this.health = health;

        frontWheel.setOrigin(Align.center);
        frontWheel.setRotation(90);
        frontWheel.setPosition(135, 0);
        backWheel.setPosition(24, 0);

        addActor(tower);
        addActor(frontWheel);
        addActor(backWheel);

        body = createBody(x + tower.getWidth(), (y));

        level.addChild(this, x, y);
    }

    public void setToDestroy() {
        isDestroyed = true;
        body.setActive(false);
    }

    public void checkToDestroy() {
        if (!body.isActive() && isDestroyed) {
            world.destroyBody(body);
            this.remove();
        }

        if (isDestroyed) {
            body.setActive(false);
        }
    }

    public void setHealth(float damage) {
        health -= damage;
    }

    public float getHealth() {
        return health;
    }



    public Body createBody(float x, float y) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
//        def.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(24 / Level.WORLD_SCALE, 84 / Level.WORLD_SCALE);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.filter.categoryBits = GameUnit.TOWER_BIT;
        fDef.filter.maskBits = GameUnit.ENEMY_BIT;

        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        body.setTransform(x / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);

        return body;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        checkToDestroy();       // проверяем, нужно ли уничтожить актера
    }
}

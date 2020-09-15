package com.timgapps.warfare.Units.GameUnits.Player.Bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
//import com.timgapps.warfare.Units.GameUnits.Player.units.Archer1;
import com.timgapps.warfare.Warfare;
//
//public class Arrow extends Bullet {
//
//    private TextureRegion image;
//    private final float VELOCITY = 8;
//    private Archer1 archer;
//
//    public Arrow(Level level, Archer1 archer, float x, float y, float damage) {
//        super(level, x, y, 32);
//        this.damage = damage;
//        image = new TextureRegion(Warfare.atlas.findRegion("arrow"));
////        createBody(x, y);
//        level.addChild(this);
////        level.addChild(this, x, y);
//        body = createBody(x, y);
//        body.setLinearVelocity(VELOCITY, 0);
//        this.archer = archer;
//    }
//
//    @Override
//    public void inflictDamage(EnemyUnit enemyUnit) {
//        super.inflictDamage(enemyUnit);
//
//        /** если текщая анимация атаки завершена, т.е.currentState != State.Attack */
////        archer.resetIsFired();
//    }
//
//
//    @Override
//    public void act(float delta) {
//        super.act(delta);
////        setPosition(body.getPosition().x * Level.WORLD_SCALE, body.getPosition().y * Level.WORLD_SCALE + 32);
//
//        if (getX() > Warfare.V_WIDTH) {
//            isDamaged = true;
//            archer.resetIsFired();
//        }
//    }
//
//    @Override
//    public Body createBody(float x, float y) {
//        BodyDef def = new BodyDef();
//        def.type = BodyDef.BodyType.DynamicBody;
//        Body body = world.createBody(def);
//
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(2 / Level.WORLD_SCALE, 2 / Level.WORLD_SCALE);
//
//        FixtureDef fDef = new FixtureDef();
//        fDef.shape = shape;
//        fDef.density = 0.01f;
//        fDef.filter.categoryBits = GameUnitView.BULLET_BIT;
//        fDef.filter.maskBits = GameUnitView.ENEMY_BIT;
//
//        body.createFixture(fDef).setUserData(this);
//        shape.dispose();
//        body.setTransform((x + 24) / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);
//        return body;
//    }
//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
//        batch.draw(image, getX(), getY());
//    }
//}

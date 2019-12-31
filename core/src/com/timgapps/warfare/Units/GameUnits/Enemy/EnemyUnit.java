package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.timgapps.warfare.Game;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnit;
import com.timgapps.warfare.Units.GameUnits.Player.PlayerUnit;

public class EnemyUnit extends GameUnit {

    //    protected Rectangle rectangle;
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

        bodyRectangle.setSize(48, 16);
        if (isDebug) {
            shapeRenderer = new ShapeRenderer();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        /** обновим позицию текущего игрового объекта **/
        setPosition(body.getPosition().x * Level.WORLD_SCALE - 24, body.getPosition().y * Level.WORLD_SCALE - 8);

        /** Обновим позицию прямоугльника "тела", который служит для определения столкновений с камнем **/
        bodyRectangle.setPosition(getX(), getY());
    }

    @Override
    public Vector2 getBodyPosition() {
        return null;
    }


    public void setTargetPlayer(PlayerUnit targetPlayer) {
    }

    @Override
    public Body createBody(float x, float y) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(24 / Level.WORLD_SCALE, 8 / Level.WORLD_SCALE);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.filter.categoryBits = GameUnit.ENEMY_BIT;
        fDef.filter.maskBits = GameUnit.PLAYER_BIT | GameUnit.BULLET_BIT | GameUnit.STONE_BIT;

        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        body.setTransform((x) / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);

        return body;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public Rectangle getRectangle() {
        return bodyRectangle;
    }


}

package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Stone;
import com.timgapps.warfare.Units.GameUnits.Player.PlayerUnit;
import com.timgapps.warfare.Units.GameUnits.Player.SiegeTower;

public class EnemyUnit extends GameUnit {

    //    protected Rectangle rectangle;
    private ShapeRenderer shapeRenderer;
//    public enum State {WALKING, ATTACK, STAY, DIE, RUN, HART}

    protected Animation walkAnimation;            // анимация для ходьбы
    protected Animation attackAnimation;          // анимация для атаки
    protected Animation dieAnimation;             // анимация для уничтожения
    protected Animation stayAnimation;            // анимация для стоит
    protected Animation runAnimation;            // анимация для бежит
    protected Animation hartAnimation;            // анимация для получает урон

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    protected float deltaX, deltaY;

    protected float bodyWidth, bodyHeight;

    /**
     * переменная отвечает за то, отрисовывать ли прямоугольник для определения коллизий с камнем
     **/
//    private boolean isDebug = false;
    public boolean isDraw() {
        return isDraw;
    }
    protected boolean isHaveTargetPlayer;

    private boolean isDraw = true;
    protected boolean isAttackStone = false;
    protected boolean isAttackTower = false;
    protected Stone stone;
    protected PlayerUnit targetPlayer;

    public EnemyUnit(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);

        bodyWidth = 48;     //  ширина тела
        bodyHeight = 16;    // высота тела

        bodyRectangle.setSize(bodyWidth, bodyHeight);
        if (isDebug) {
            shapeRenderer = new ShapeRenderer();
        }
    }

    public void attackTower() {
        if (currentState == State.WALKING) {
            if (!isAttackTower) {
                isAttackTower = true;
                stateTime = 0;
                currentState = State.ATTACK;
            }
        }
    }

    /**
     * метод для проверки, атакует ли вражеский юнит ОСАДНУЮ БАШНЮ
     **/
    public boolean getIsAttackTower() {
        return isAttackTower;
    }


    public void setAttackStone(Stone stone) {
        if (!isAttackStone) {
            isAttackStone = true;
            currentState = State.ATTACK;
            this.stone = stone;
//            System.out.println("isAttackStone = " + isAttackStone);
//        stone.setHealth(damage);
        }
    }

    @Override
    public void setHealth(float value) {
        super.setHealth(value);
        addDamageLabel(getX() + xPosDamageLabel, getY() + getHeight() + 14, value);
    }

    @Override
    public void act(float delta) {
        super.act(delta);


        /** обновим позицию текущего игрового объекта **/
        setPosition(body.getPosition().x * Level.WORLD_SCALE - bodyWidth / 2, body.getPosition().y * Level.WORLD_SCALE - bodyHeight / 2);
//        setPosition(body.getPosition().x * Level.WORLD_SCALE - 24, body.getPosition().y * Level.WORLD_SCALE - 22);

        /** Обновим позицию прямоугльника "тела", который служит для определения столкновений с камнем **/
        bodyRectangle.setPosition(getX(), getY());
    }

    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }

    public void setTargetPlayer(PlayerUnit targetPlayer) {
            this.targetPlayer = targetPlayer;
            isHaveTargetPlayer = true;
    }

    public PlayerUnit getTargetPlayer() {
        return targetPlayer;
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
        fDef.filter.maskBits = GameUnit.PLAYER_BIT | GameUnit.BULLET_BIT | GameUnit.STONE_BIT | TOWER_BIT;

        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        body.setTransform(x / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);

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

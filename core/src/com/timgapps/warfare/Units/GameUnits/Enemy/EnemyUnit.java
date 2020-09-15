package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnit;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Stone;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnit;

public class EnemyUnit extends GameUnit {
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

    protected boolean isRemovedFromEnemiesArray = false;
    private boolean isDraw = true;
    protected boolean isAttackStone = false;    // флаг - атакует ли в данный момент камень, в состоянии ли атаки камня
    protected boolean isAttackTower = false;    // флаг - атакует ли в данный момент башню, в состоянии ли атаки башни
    protected boolean isAttack = false;         // флаг - атакует ли в данный момент юнит, в состоянии ли атаки
    protected Stone stone;
    protected PlayerUnit targetPlayer;    // юнит игрока - "ЦЕЛЕВОЙ ЮНИТ" или "ЮНИТ-ЦЕЛЬ"
//    protected Body body;

    public EnemyUnit(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
//        body = createBody(x, y);

        bodyWidth = 48;     //  ширина тела
        bodyHeight = 16;    // высота тела

        bodyRectangle.setSize(bodyWidth, bodyHeight);
        if (isDebug) {
            shapeRenderer = new ShapeRenderer();
        }
    }

    public void attackTower() {
        if (currentState == GameUnitView.State.WALKING || currentState == GameUnitView.State.RUN || currentState == GameUnitView.State.STAY) {
            // если не атакует игрового юнита, то установим флаг атакует башню = true
            if (!isAttack) {
                if (!isAttackTower) {
                    isAttackTower = true;
                    stateTime = 0;
                    currentState = GameUnitView.State.ATTACK;
                }
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
            currentState = GameUnitView.State.ATTACK;
            this.stone = stone;
        }
    }

    @Override
    public void subHealth(float value) {
        super.subHealth(value);
        if (health <= 0 && !isRemovedFromEnemiesArray) {   // если здоровье меньше или равно 0, то удаляем из массива вражеских юнитов
            removeFromEnemiesArray();                      // текущий юнит
        }
        addDamageLabel(getX() + xPosDamageLabel, getY() + getHeight() + 14, value);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        /** обновим позицию текущего игрового объекта **/
        setPosition(body.getPosition().x * Level.WORLD_SCALE - bodyWidth / 2, body.getPosition().y * Level.WORLD_SCALE - bodyHeight / 2);

        /** Обновим позицию прямоугльника "тела", который служит для определения столкновений с камнем **/
        bodyRectangle.setPosition(getX(), getY());
    }

    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }

    public void setTargetPlayer(PlayerUnit targetPlayer) {
        if (!isHaveTargetPlayer) {
            this.targetPlayer = targetPlayer;
            isHaveTargetPlayer = true;
        }
    }

    public PlayerUnit getTargetPlayer() {
        return targetPlayer;
    }

    public Body createBody(float x, float y) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(24 / Level.WORLD_SCALE, 8 / Level.WORLD_SCALE);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.filter.categoryBits = ENEMY_BIT;
        fDef.filter.maskBits = PLAYER_BIT | BULLET_BIT | STONE_BIT | TOWER_BIT;
        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        body.setTransform(x / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);

        return body;
    }

    protected void removeFromEnemiesArray() {
        level.removeEnemyUnitFromArray(this);
        isRemovedFromEnemiesArray = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public Rectangle getRectangle() {
        return bodyRectangle;
    }

}

package com.timgapps.warfare.Units.GameUnits.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Barricade;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnits.GameUnit;

public class PlayerUnit extends GameUnit {

    Animation walkAnimation;            // анимация для ходьбы
    protected Animation attackAnimation;          // анимация для атаки
    protected Animation dieAnimation;             // анимация для уничтожения
    protected Animation stayAnimation;            // анимация для стоит
    protected Animation runAnimation;            // анимация для бежит
    protected Animation hartAnimation;            // анимация для получает урон

    protected boolean isAttack = false;   // флаг, указывет на то, в состоянии ли атаки находится юнит
//    protected float stateTime;

    public static int energyPrice;     // количество энергии для рождения юнита

    public static final int GNOME = 1;
    public static final int ARCHER = 2;
    public static final int STONE = 3;

    protected boolean isAttackBarricade = false;
    protected Barricade barricade;

    protected float bodyWidth = 24;
    protected float bodyHeight = 24;

    protected float deltaX, deltaY;


//    protected TextureRegion lifeIndicator;

    public PlayerUnit(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        body = createBody(x, y);
        this.barricade = level.getBarricade();

//        healthBarWidth = 54;
//        healthBarHeight = 10;
    }

    @Override
    public Body createBody(float x, float y) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bodyWidth / 2) / Level.WORLD_SCALE, (bodyHeight / 2) / Level.WORLD_SCALE);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.filter.categoryBits = GameUnit.PLAYER_BIT;
        fDef.filter.maskBits = GameUnit.ENEMY_BIT | BARRICADE_BIT;

        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        body.setTransform(x / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);
        return body;
    }


    protected enum Direction {UP, DOWN, NONE}

    @Override
    public Vector2 getBodyPosition() {
        return null;
    }


    public void setTargetEnemy(EnemyUnit enemyUnit) {
//        resetTarget();
//        targetEnemy = enemyUnit;
//        isHaveTarget = true;
//        isAttack = true;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        /** обновим позицию текущего игрового объекта **/
        setPosition(body.getPosition().x * Level.WORLD_SCALE - bodyWidth / 2,
                body.getPosition().y * Level.WORLD_SCALE - bodyHeight / 2);
    }

    public static int getEnergyPrice() {
        return energyPrice;
    }


    @Override
    public void setHealth(float value) {
        super.setHealth(value);
        addDamageLabel(getX() + xPosDamageLabel, getY() + getHeight() + 8 + yPosDamageLabel, value);
    }


    /**
     * метод для атаки баррикады!
     **/
    public void attackBarricade() {
//    public void attackBarricade(Barricade barricade) {
        if (!isAttackBarricade && !isAttack) {
//        if (!isAttackBarricade && !isAttack) {
            isAttackBarricade = true;
//            this.barricade = barricade;
            stateTime = 0;
            currentState = State.ATTACK;
        }

//        if (isAttackBarricade && attackAnimation.isAnimationFinished(stateTime)) {
//            barricade.setHealth(damage);
//            System.out.println("DAMAGE");
//            if (barricade.getHealth() <= 0) {
//                isAttackBarricade = false;
//                stateTime = 0;
//                currentState = State.STAY;
//            }
//        }
    }


}

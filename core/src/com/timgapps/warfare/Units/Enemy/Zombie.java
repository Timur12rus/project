package com.timgapps.warfare.Units.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnit;
import com.timgapps.warfare.Warfare;

public class Zombie extends EnemyUnit {

    public enum State {WALKING, ATTACK, STAY, DIE, RUN}

    private final float VELOCITY = -1f;
    public State currentState = State.ATTACK;
    private float stateTime;

    private Animation walkAnimation;            // анимация для ходьбы
    private Animation attackAnimation;          // анимация для атаки
    private Animation dieAnimation;             // анимация для уничтожения
    private Animation stayAnimation;            // анимация для стоит
    private Animation runAnimation;            // анимация для бежит

    private World world;
    private Body body;
    private float x, y;

    public Zombie(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        this.level = level;
        this.world = level.getWorld();
        createAnimations();     // создадим анимации для различных состояний персонажа
        level.addChild(this, x, y);
        createBody(x, y);
        currentState = State.WALKING;
    }


    @Override
    public void createBody(float x, float y) {

//        BodyDef def = new BodyDef();
//        def.type = BodyDef.BodyType.DynamicBody;
//        body = world.createBody(def);
//
//        FixtureDef fDef = new FixtureDef();
//        CircleShape shape = new CircleShape();
//        shape.setRadius(38 / Level.WORLD_SCALE);
//
//        fDef.shape = shape;
//        fDef.density = 1;
//        fDef.restitution = 0.1f;
//        body.createFixture(fDef).setUserData(this);
//        shape.dispose();
//
//        body.setTransform((x + 16) / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        FixtureDef fDef = new FixtureDef();
//        CircleShape shape = new CircleShape();
//        shape.setRadius(38 / Level.WORLD_SCALE);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32 / Level.WORLD_SCALE, 12 / Level.WORLD_SCALE);
        fDef.filter.categoryBits = GameUnit.ENEMY_BIT;
        fDef.filter.maskBits = GameUnit.PLAYER_BIT;


        fDef.shape = shape;
//        fDef.density = 1;
//        fDef.restitution = 0.1f;
        body.createFixture(fDef).setUserData(this);
        shape.dispose();

        body.setTransform((x) / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);
    }


    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        if (level.getState() == Level.PLAY) {
        stateTime += Gdx.graphics.getDeltaTime();
//        }
//        batch.setColor(1, 1, 1, 1);

//        if (isDraw) {
        if (currentState == State.WALKING) {
            batch.draw((TextureRegion) walkAnimation.getKeyFrame(stateTime, true), getX() - 32, getY() - 26);
        }

        if (currentState == State.ATTACK) {
            batch.draw((TextureRegion) attackAnimation.getKeyFrame(stateTime, true), getX() - 32, getY() - 26);
        }

        if (currentState == State.STAY) {
            batch.draw((TextureRegion) stayAnimation.getKeyFrame(stateTime, true), getX() - 32, getY() - 46);
        }

        if (currentState == State.RUN) {
            batch.draw((TextureRegion) runAnimation.getKeyFrame(stateTime, true), getX() - 32, getY() - 46);
        }

        if (currentState == State.DIE) {
            batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, false), getX() - 32, getY() - 46);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!body.isActive()) {
            world.destroyBody(body);
            this.remove();
            level.removeEnemyUnitFromArray(this);
        }

        if (currentState == State.ATTACK) {
            stay();
            if (attackAnimation.isAnimationFinished(stateTime)) {
                stateTime = 0;
//                System.out.println("attackAnimationFinished!");
//                inflictDamage(targetEnemy, damage);

            }
//            System.out.println("currentState = " + currentState);
        }

        if (setToDestroy) {
            body.setActive(false);
        }

        if (health <= 0) {
            setToDestroy = true;
        }

        if (currentState == State.WALKING)
            if (body.getPosition().x * Level.WORLD_SCALE > 500)
                moveLeft(body);
            else
                stay();
        setPosition(body.getPosition().x * Level.WORLD_SCALE - 18, body.getPosition().y * Level.WORLD_SCALE);
    }

    private void stop(Body body) {
        body.setLinearVelocity(0, 0);
    }

    public void moveLeft(Body body) {
        Vector2 vel = body.getLinearVelocity();
        vel.x = VELOCITY;
        body.setLinearVelocity(vel);
//        System.out.println("body.Position.x = " + body.getPosition().x * Level.WORLD_SCALE);
    }

    private void createAnimations() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // получим кадры и добавим в анимацию ходьбы персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieWalk" + i)));
//        for (int i = 4; i < 0; i--)
//            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieWalk" + i)));
        walkAnimation = new Animation(0.15f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию атаки персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieAttack" + i)));
        attackAnimation = new Animation(0.12f, frames);
        frames.clear();
//
//        //  получим кадры и добавим в анимацию стоянки персонажа
//        for (int i = 0; i < 4; i++)
//            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeStay" + i)));
//        for (int i = 4; i < 1; i--)
//            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeStay" + i)));
//        stayAnimation = new Animation(0.2f, frames);
//        frames.clear();
//
//        //  получим кадры и добавим в анимацию бега персонажа
//        for (int i = 0; i < 4; i++)
//            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeRun" + i)));
//        runAnimation = new Animation(0.18f, frames);
//        frames.clear();
//
//        //  получим кадры и добавим в анимацию уничтожения персонажа
//        for (int i = 0; i < 4; i++)
//            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeDie" + i)));
//        dieAnimation = new Animation(0.1f, frames);
//        frames.clear();
    }

    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }

//    @Override
//    public void inflictDamage(GameUnit unit) {
//        unit.setHealth(unit.getHealth() - damage);
//    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public void setHealth(float health) {
        this.health -= health;
    }

    @Override
    public void attack() {
        if (currentState != State.ATTACK) {        // проверяем, в состоянии ли "атаки" юнит
            currentState = State.ATTACK;
            stateTime = 0;
        }
    }

    private void stay() {
        body.setLinearVelocity(0, 0);
    }
}

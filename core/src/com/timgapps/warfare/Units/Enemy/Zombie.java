package com.timgapps.warfare.Units.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnit;
import com.timgapps.warfare.Units.Player.PlayerUnit;
import com.timgapps.warfare.Warfare;

import java.util.Random;

public class Zombie extends EnemyUnit {

    public enum State {WALKING, ATTACK, STAY, DIE, RUN, HART}

    private final float VELOCITY = -0.35f;
    public State currentState = State.ATTACK;
    private float stateTime;

    private Animation walkAnimation;            // анимация для ходьбы
    private Animation attackAnimation;          // анимация для атаки
    private Animation dieAnimation;             // анимация для уничтожения
    private Animation stayAnimation;            // анимация для стоит
    private Animation runAnimation;            // анимация для бежит
    private Animation hartAnimation;            // анимация для получает урон

    private World world;
    private Body body;
    private float x, y;
    private PlayerUnit targetPlayer;

    private boolean isAttack = false;

    private boolean continueWalk = false;
    private boolean isDie = false;
    private boolean isDamaged = false;


    private ParticleEffect bloodSpray;

    public Zombie(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        this.level = level;
        this.world = level.getWorld();
        bloodSpray = new ParticleEffect();
        bloodSpray.load(Gdx.files.internal("effects/bloodSpray.paty"), Gdx.files.internal("effects/")); //file);     //Air2.paty
        createAnimations();     // создадим анимации для различных состояний персонажа
        level.addChild(this, x, y);
        createBody(x, y);
        stateTime = 0;
        currentState = State.WALKING;
    }


    @Override
    public void createBody(float x, float y) {

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(12 / Level.WORLD_SCALE, 24 / Level.WORLD_SCALE);
        fDef.filter.categoryBits = GameUnit.ENEMY_BIT;
        fDef.filter.maskBits = GameUnit.PLAYER_BIT;

        fDef.shape = shape;
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
            batch.draw((TextureRegion) walkAnimation.getKeyFrame(stateTime, true), getX() - 64, getY() - 26);
        }

        if (currentState == State.ATTACK) {
            batch.draw((TextureRegion) attackAnimation.getKeyFrame(stateTime, false), getX() - 64, getY() - 26);
        }

        if (currentState == State.STAY) {
            batch.draw((TextureRegion) stayAnimation.getKeyFrame(stateTime, true), getX() - 64, getY() - 26);
        }

        if (currentState == State.RUN) {
            batch.draw((TextureRegion) runAnimation.getKeyFrame(stateTime, true), getX() - 64, getY() - 26);
        }

        if (currentState == State.HART) {
            batch.draw((TextureRegion) hartAnimation.getKeyFrame(stateTime, false), getX() - 64, getY() - 26);
        }

        if (currentState == State.DIE) {
            batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, false), getX() - 64, getY() - 26);
        }

        if (isDamaged)
            bloodSpray.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

//        if (!body.isActive()) {
//            world.destroyBody(body);
//            this.remove();
//            level.removeEnemyUnitFromArray(this);
//        }

        if (isDamaged) {
            bloodSpray.setPosition(getX() + 30, getY() + 60);
            bloodSpray.update(delta);
        }
        if (isDamaged && bloodSpray.isComplete())
            isDamaged = false;

        if (currentState == State.ATTACK && targetPlayer != null && targetPlayer.getHealth() <= 0 && attackAnimation.isAnimationFinished(stateTime)) {
            isAttack = false;
            currentState = State.WALKING;
            stateTime = 0;

            targetPlayer = null;
        }

        if (targetPlayer != null && targetPlayer.getHealth() <= 0) {
            isAttack = false;
        }

        if (currentState == State.HART && hartAnimation.isAnimationFinished(stateTime)) {
            if (isAttack) {
                currentState = State.ATTACK;
            } else {
                currentState = State.STAY;
            }
            stateTime = 0;
        }

        if (currentState == State.STAY && stayAnimation.isAnimationFinished(stateTime)) {
            stateTime = 0;
            if (isAttack) {
                currentState = State.ATTACK;
            } else
                currentState = State.WALKING;
        }

//        if (currentState == State.HART && hartAnimation.isAnimationFinished(stateTime)) {
//            if (isAttack = true) {
//                currentState = State.ATTACK;
//            } else {
//                currentState = State.STAY;
//            }
//            stateTime = 0;
//        }

        if (currentState == State.WALKING && walkAnimation.isAnimationFinished(stateTime)) {
            Random random = new Random();
            continueWalk = random.nextBoolean();
            if (continueWalk) {
                stateTime = 0;
                currentState = State.WALKING;

            } else {
//                stateTime = 0;
                currentState = State.STAY;
//                stay();
            }
        }

        if (currentState == State.ATTACK) {
            stay();
            if (attackAnimation.isAnimationFinished(stateTime)) {
                stateTime = 0;
                inflictDamage(targetPlayer, damage);
                currentState = State.STAY;
            }
        }


        if (currentState == State.DIE && dieAnimation.isAnimationFinished(stateTime)) {
            destroy();
//            setToDestroyBody = true;
        }
//
//        if (setToDestroyBody) {
//            body.setActive(false);
//        }

        if (health <= 0 && body.isActive()) {
//            isDie = true;
            currentState = State.DIE;
            stateTime = 0;
            body.setActive(false);
        }

        if (currentState == State.WALKING)
            if (body.getPosition().x * Level.WORLD_SCALE > 500)
                moveLeft(body);
            else {
                stateTime = 0;
                currentState = State.STAY;
            }

        if (currentState == State.STAY) {
            stay();
        }
        setPosition(body.getPosition().x * Level.WORLD_SCALE - 18, body.getPosition().y * Level.WORLD_SCALE);
    }

    private void destroy() {
        if (!body.isActive()) {
//            world.destroyBody(body);
            this.remove();
            bloodSpray.dispose();
            level.removeEnemyUnitFromArray(this);
        }
    }

    public void moveLeft(Body body) {
        Vector2 vel = body.getLinearVelocity();
        vel.x = VELOCITY;
        body.setLinearVelocity(vel);
    }

    private void createAnimations() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // получим кадры и добавим в анимацию ходьбы персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieWalk" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieWalk0")));

        walkAnimation = new Animation(0.25f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию атаки персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieAttack" + i)));
        attackAnimation = new Animation(0.12f, frames);
        frames.clear();


        //  получим кадры и добавим в анимацию атаки персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieDie" + i)));
        dieAnimation = new Animation(0.12f, frames);
        frames.clear();


        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieHart" + i)));
        hartAnimation = new Animation(0.05f, frames);
        frames.clear();

//
        //  получим кадры и добавим в анимацию стоянки персонажа
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++)
                frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieStay" + i)));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieStay3")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieStay2")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieStay1")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieStay0")));
        }
        stayAnimation = new Animation(0.25f, frames);
        frames.clear();
    }

    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public void setHealth(float health) {
        this.health -= health;
        isDamaged = true;
        bloodSpray.start();
        if (health > 0)
            currentState = State.HART;
        else currentState = State.DIE;
        stateTime = 0;
    }

    @Override
    public void attack() {
//        if (currentState != State.ATTACK) {        // проверяем, в состоянии ли "атаки" юнит
//            currentState = State.ATTACK;
//            stateTime = 0;
//            isAttack = true;
//        }

        if (!isAttack) {        // проверяем, в состоянии ли "атаки" юнит
            currentState = State.STAY;
            stateTime = 0;
            isAttack = true;
        }
    }

    private void stay() {
        body.setLinearVelocity(0, 0);
    }

    public void setTargetPlayer(PlayerUnit targetPlayer) {
        this.targetPlayer = targetPlayer;
    }
}

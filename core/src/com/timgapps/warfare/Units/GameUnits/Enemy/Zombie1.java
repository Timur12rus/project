package com.timgapps.warfare.Units.GameUnits.Enemy;

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
import com.timgapps.warfare.Units.GameUnits.GameUnit;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Stone;
import com.timgapps.warfare.Units.GameUnits.Player.PlayerUnit;
import com.timgapps.warfare.Warfare;

import java.util.Random;

public class Zombie1 extends EnemyUnit {

    public enum State {WALKING, ATTACK, STAY, DIE, RUN, HART}

    private final float VELOCITY = -0.25f;
    public Zombie.State currentState = Zombie.State.ATTACK;
    private float stateTime;

    private Animation walkAnimation;            // анимация для ходьбы
    private Animation attackAnimation;          // анимация для атаки
    private Animation dieAnimation;             // анимация для уничтожения
    private Animation stayAnimation;            // анимация для стоит
    private Animation runAnimation;            // анимация для бежит
    private Animation hartAnimation;            // анимация для получает урон

    private World world;

    private float x, y;
    private PlayerUnit targetPlayer;

    private boolean isAttack = false;

    private boolean continueWalk = false;
    private boolean isDie = false;
    private boolean isDamaged = false;


    private ParticleEffect bloodSpray;

    public Zombie1(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        this.level = level;
        this.world = level.getWorld();
        bloodSpray = new ParticleEffect();
        bloodSpray.load(Gdx.files.internal("effects/bloodSpray.paty"), Gdx.files.internal("effects/")); //file);     //Air2.paty
        createAnimations();     // создадим анимации для различных состояний персонажа
        level.addChild(this, x, y);

//        body = createBody(x, y);
        stateTime = 0;
        currentState = Zombie.State.WALKING;
        level.arrayActors.add(this);

    }


//    @Override
//    public Body createBody(float x, float y) {
//
//        BodyDef def = new BodyDef();
//        def.type = BodyDef.BodyType.DynamicBody;
//         body = world.createBody(def);
//
//        FixtureDef fDef = new FixtureDef();
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(12 / Level.WORLD_SCALE, 12 / Level.WORLD_SCALE);
//        fDef.filter.categoryBits = GameUnit.ENEMY_BIT;
//        fDef.filter.maskBits = GameUnit.PLAYER_BIT | GameUnit.BULLET_BIT;
//
//        fDef.shape = shape;
//        body.createFixture(fDef).setUserData(this);
//        shape.dispose();
//
//        body.setTransform((x) / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);
//    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        if (level.getState() == Level.PLAY) {
        stateTime += Gdx.graphics.getDeltaTime();
//        }
//        batch.setColor(1, 1, 1, 1);

        if (isDraw()) {
            if (currentState == Zombie.State.WALKING) {
                batch.draw((TextureRegion) walkAnimation.getKeyFrame(stateTime, true), getX() - 82, getY() - 6);
            }

            if (currentState == Zombie.State.ATTACK) {
                batch.draw((TextureRegion) attackAnimation.getKeyFrame(stateTime, false), getX() - 82, getY() - 6);
            }

            if (currentState == Zombie.State.STAY) {
                batch.draw((TextureRegion) stayAnimation.getKeyFrame(stateTime, true), getX() - 82, getY() - 6);
            }

            if (currentState == Zombie.State.RUN) {
                batch.draw((TextureRegion) runAnimation.getKeyFrame(stateTime, true), getX() - 82, getY() - 6);
            }

            if (currentState == Zombie.State.HART) {
                batch.draw((TextureRegion) hartAnimation.getKeyFrame(stateTime, false), getX() - 82, getY() - 6);
            }

            if (currentState == Zombie.State.DIE) {
                batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, false), getX() - 82, getY() - 14);
            }
        } else setDraw(true);
        if (isDamaged)
            bloodSpray.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!body.isActive()) {
            world.destroyBody(body);
            this.remove();
            level.removeEnemyUnitFromArray(this);
        }


        if (isDamaged) {
            bloodSpray.setPosition(getX() + 30, getY() + 60);
            bloodSpray.update(delta);
        }
        if (isDamaged && bloodSpray.isComplete())
            isDamaged = false;

        if (currentState == Zombie.State.ATTACK && targetPlayer != null && targetPlayer.getHealth() <= 0 && attackAnimation.isAnimationFinished(stateTime)) {
            isAttack = false;
            currentState = Zombie.State.WALKING;
            stateTime = 0;

            targetPlayer = null;

        }

        if (targetPlayer != null && targetPlayer.getHealth() <= 0) {
            isAttack = false;
        }

        if (currentState == Zombie.State.HART && hartAnimation.isAnimationFinished(stateTime)) {
            if (isAttack) {
                currentState = Zombie.State.ATTACK;
            } else {
                currentState = Zombie.State.STAY;
            }
            stateTime = 0;
        }

        if (currentState == Zombie.State.STAY && stayAnimation.isAnimationFinished(stateTime)) {
            stateTime = 0;
            if (isAttack) {
                currentState = Zombie.State.ATTACK;
            } else
                currentState = Zombie.State.WALKING;
        }

        if (currentState == Zombie.State.WALKING && walkAnimation.isAnimationFinished(stateTime)) {
            Random random = new Random();
            continueWalk = random.nextBoolean();
            if (continueWalk) {
                stateTime = 0;
                currentState = Zombie.State.WALKING;

            } else {
                stateTime = 0;
                currentState = Zombie.State.STAY;
//                stay();
            }
        }


        /** Здесь проверяем, атакует ли юнит врага
         * @isAttackStone - флаг, не должен быть true
         **/
        if (currentState == Zombie.State.ATTACK && !isAttackStone) {
            stay();
            if (attackAnimation.isAnimationFinished(stateTime)) {
                stateTime = 0;
                inflictDamage(targetPlayer, damage);
                currentState = Zombie.State.STAY;
            }
        }


        /** Здесь проверяем, атакует ли юнит камень в данный момент
         * @isAttackStone - флаг, true - аттакует, false - нет
         * **/
        if (isAttackStone && currentState != Zombie.State.ATTACK) {
            stateTime = 0;
            currentState = Zombie.State.ATTACK;
        }

        if (currentState == Zombie.State.ATTACK && isAttackStone) {
            stay();
            if (attackAnimation.isAnimationFinished(stateTime)) {
                stone.setHealth(damage);
//                isAttackStone = false;
                if (stone.getHealth() <= 0) {
                    isAttackStone = false;
                }
                stateTime = 0;
                currentState = Zombie.State.STAY;
            }
        }


        if (currentState == Zombie.State.DIE && dieAnimation.isAnimationFinished(stateTime)) {
            destroy();
//            setToDestroyBody = true;
        }
//
//        if (setToDestroyBody) {
//            body.setActive(false);
//        }

        if (health <= 0 && body.isActive()) {
//            isDie = true;

            stateTime = 0;
            currentState = Zombie.State.DIE;
            body.setActive(false);
        }

        if (currentState == Zombie.State.WALKING)
            if (body.getPosition().x * Level.WORLD_SCALE > 500)
                moveLeft(body);
            else {
                stateTime = 0;
                currentState = Zombie.State.STAY;
            }

        if (currentState == Zombie.State.STAY) {
            stay();
        }
    }

//    public void setAttackStone(Stone stone) {
//        System.out.println("stoneAttack");
//        isAttackStone = super.isAttackStone;
//        System.out.println("isAttackStone = " + isAttackStone);
////        super.setAttackStone(stone);
//
////        stone.setHealth(damage);
//    }

    private void destroy() {
        if (!body.isActive()) {
//            world.destroyBody(body);
            this.remove();
            bloodSpray.dispose();
            level.removeEnemyUnitFromArray(this);
            System.out.println("Destroy()");
        }
    }

    public void moveLeft(Body body) {
        Vector2 vel = body.getLinearVelocity();
        vel.x = VELOCITY;
        body.setLinearVelocity(vel);
    }

    private void createAnimations() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Walk" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Walk1")));

        walkAnimation = new Animation(0.12f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию атаки персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Attack" + i)));
        attackAnimation = new Animation(0.12f, frames);
        frames.clear();


        //  получим кадры и добавим в анимацию атаки персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Die" + i)));
        dieAnimation = new Animation(0.12f, frames);
        frames.clear();


        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Hart" + i)));
        hartAnimation = new Animation(0.05f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию стоянки персонажа
        for (int j = 0; j < 2; j++) {
            for (int i = 4; i > 0; i--)
                frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Stay" + i)));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Stay1")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Stay2")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Stay3")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombie1Stay4")));
        }
        stayAnimation = new Animation(0.35f, frames);
        frames.clear();
    }

    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }

    @Override
    public void setHealth(float health) {
        this.health -= health;
        isDamaged = true;
        bloodSpray.start();
        if (health > 0)
            currentState = Zombie.State.HART;
        else currentState = Zombie.State.DIE;
        stateTime = 0;
    }

    @Override
    public void attack() {

        if (!isAttack) {        // проверяем, в состоянии ли "атаки" юнит
            currentState = Zombie.State.STAY;
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

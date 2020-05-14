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
import com.timgapps.warfare.Units.GameUnits.Player.PlayerUnit;
import com.timgapps.warfare.Warfare;

import java.util.Random;

public class Zombie extends EnemyUnit {

//    public enum State {WALKING, ATTACK, STAY, DIE, RUN, HART}

    protected final float VELOCITY = -0.35f;
    //    public State currentState = State.ATTACK;
//    protected float stateTime;

    protected World world;
    protected float x, y;
//    protected PlayerUnit targetPlayer;
    protected boolean isAttack = false;
    protected boolean continueWalk = false;
    protected boolean isDie = false;
    protected boolean isDamaged = false;
    private ParticleEffect bloodSpray;


    protected int stayCount;
    private final int STAY_COUNT = 2;

    public Zombie(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        this.level = level;
        this.world = level.getWorld();
        xPosDamageLabel = 16;

        this.setWidth(Warfare.atlas.findRegion("zombieWalk0").getRegionWidth());
        this.setHeight(Warfare.atlas.findRegion("zombieWalk0").getRegionHeight());

        bloodSpray = new ParticleEffect();
        bloodSpray.load(Gdx.files.internal("effects/bloodSpray.paty"), Gdx.files.internal("effects/")); //file);     //Air2.paty
        createAnimations();     // создадим анимации для различных состояний персонажа
        level.addChild(this, x, y);
        stateTime = 0;
        currentState = State.WALKING;
        level.arrayActors.add(this);

        stayCount = STAY_COUNT;
        deltaX = 72;        // смещение рисоания анимации относительно позиции актёра по оси Х
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        /** если юниту нанесен урон: isDamaged = true, обновляем анимацию брызг крови **/
        if (isDamaged) {
            bloodSpray.setPosition(getX() + 30, getY() + 60);
            bloodSpray.update(delta);
        }

        /** завершим анимацию брызг крови **/
        if (isDamaged && bloodSpray.isComplete())
            isDamaged = false;

        if (currentState == State.STAY || currentState == State.ATTACK) {
            stay();
        }

        /** проверим юнита в текущем состоянии = State.ATTACK **/
        if (currentState == State.ATTACK) {
            /** если анимация завершилась **/
            if (attackAnimation.isAnimationFinished(stateTime)) {
                if (isAttackStone) {                                // если юнит атакует камень
                    stone.setHealth(damage);
                    /** проверим уровень здоровья у камня **/
                    if (stone.getHealth() <= 0) {
                        isAttackStone = false;
                        stateTime = 0;
                        currentState = State.WALKING;
                    } else {
                        stateTime = 0;
                        currentState = State.STAY;
                    }
                } else if (isAttackTower) {              // если юнит атакует ОСАДНУЮ БАШНЮ
                    if (level.getSiegeTower() != null) {
                        System.out.println("Attack tower!");
                        level.getSiegeTower().setHealth(damage);
                        stateTime = 0;
                        currentState = State.STAY;
                        if (level.getSiegeTower().getHealth() <= 0) {
//                            level.getSiegeTower().setToDestroy();
                            isAttackTower = false;
                        }
                    }

                } else {
                    // нанесем урон целевому юниту
                    inflictDamage(targetPlayer, damage);
                    stateTime = 0;
                    currentState = State.STAY;
                    if (targetPlayer.getHealth() <= 0) {
                        resetTarget();
                    }
                }

                if (targetPlayer != null && targetPlayer.getHealth() <= 0) {
                    resetTarget();  // сбросим ЦЕЛЬ
                    stateTime = 0;
                    currentState = State.WALKING;
                }
            }
        }

        /** проверяем юнита в состоянии = State.STAY **/
        if (currentState == State.STAY) {
//            System.out.println("STAY");
            if (stayAnimation.isAnimationFinished(stateTime)) {
//                System.out.println("StayAnimFinishED!");
//                if (isAttack) {
//                    System.out.println("isAttack = TRUE");
                if (isAttackStone || isAttack || isAttackTower) {
                    stateTime = 0;
                    currentState = State.ATTACK;
                } else {
                    stateTime = 0;
                    currentState = State.WALKING;
                }
            }
        }

        /** если текущее состояние = State.WALKING, и анимация завершена,
         * установим следующее состояние или STAY или WALKING
         */
        if (currentState == State.WALKING && walkAnimation.isAnimationFinished(stateTime)) {
//
            stayCount--;
//            Random random = new Random();
            continueWalk = level.getRandom().nextBoolean();

//            if (continueWalk) {
//                stateTime = 0;
//                currentState = State.WALKING;
//            } else {
//                stateTime = 0;
//                currentState = State.STAY;
//            }

            if (stayCount == 0) {
                if (level.getRandom().nextBoolean()) {
                    stateTime = 0;
                    currentState = State.STAY;
                } else {
                    stateTime = 0;
                    currentState = State.WALKING;
                }
                stayCount = STAY_COUNT;
            }
        }

        /** если состояние = State.DIE и анимация завершена, то уничтожаем юнита **/
        if (currentState == State.DIE && dieAnimation.isAnimationFinished(stateTime)) {

            /** МОЖЕТ ПРИГОДИТЬСЯ 17.02.2020
             //            destroy();
             **/
            setToDestroy();
        }

        if (health <= 0 && body.isActive()) {
            currentState = State.DIE;
            stateTime = 0;
            body.setActive(false);
        }

        if (currentState == State.WALKING)
            moveLeft(body);
        /** ограничиваем движение юнита ВЛЕВО, чтобы не ушел за границу экрана **/
//            if (body.getPosition().x * Level.WORLD_SCALE > 100)
//                moveLeft(body);
//            else {
//                stateTime = 0;
//                currentState = State.STAY;
//            }

    }

    @Override
    public boolean remove() {
        bloodSpray.dispose();
//        System.out.println("dispose REMOVE");
        level.removeEnemyUnitFromArray(this);
        return super.remove();
    }

    /**
     * РАБОЧИЙ КОДА 17.02.2020
     * <p>
     * protected void destroy() {
     * if (!body.isActive()) {
     * //            world.destroyBody(body);
     * bloodSpray.dispose();
     * level.removeEnemyUnitFromArray(this);
     * this.remove();
     * <p>
     * }
     * }
     **/


    public void moveLeft(Body body) {
        Vector2 vel = body.getLinearVelocity();
        vel.x = VELOCITY;
        body.setLinearVelocity(vel);
    }

    protected void createAnimations() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // получим кадры и добавим в анимацию ходьбы персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieWalk" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieWalk0")));

        walkAnimation = new Animation(0.15f, frames);
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

        //  получим кадры и добавим в анимацию стоянки персонажа
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++)
                frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieStay" + i)));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieStay3")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieStay2")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieStay1")));
            frames.add(new TextureRegion(Warfare.atlas.findRegion("zombieStay0")));
        }
        stayAnimation = new Animation(0.2f, frames);
        frames.clear();
    }

    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }

    @Override
    public void setHealth(float damage) {
        super.setHealth(damage);
        isDamaged = true;
        bloodSpray.start();
        if (health <= 0) {
            stateTime = 0;
            currentState = State.DIE;
        }
    }

    @Override
    public void attack() {
//        if (currentState != State.ATTACK) {        // проверяем, в состоянии ли "атаки" юнит
        isAttack = true;
        stateTime = 0;
        currentState = State.ATTACK;
//    }

//        if (!isAttack) {        // проверяем, в состоянии ли "атаки" юнит
//            currentState = State.STAY;
//            stateTime = 0;
//            isAttack = true;
//        }
    }

    protected void stay() {
        body.setLinearVelocity(0, 0);
    }


    /**
     * метод для назначения цели - "игрового-юнита цели"
     */
//    public void setTargetPlayer(PlayerUnit targetPlayer) {
//        this.targetPlayer = targetPlayer;
//    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        /** здесь deltaX - расстояние в пикселях, на сколько сдвигаем изображение относительно позиции актера **/
        if (isDraw()) {
            if (currentState == State.WALKING) {
                batch.draw((TextureRegion) walkAnimation.getKeyFrame(stateTime, true), getX() - deltaX, getY());
            }

            if (currentState == State.ATTACK) {
                batch.draw((TextureRegion) attackAnimation.getKeyFrame(stateTime, false), getX() - deltaX, getY());
            }

            if (currentState == State.STAY) {
                batch.draw((TextureRegion) stayAnimation.getKeyFrame(stateTime, true), getX() - deltaX, getY());
            }

            if (currentState == State.RUN) {
                batch.draw((TextureRegion) runAnimation.getKeyFrame(stateTime, true), getX() - deltaX, getY());
            }

            if (currentState == State.HART) {
                batch.draw((TextureRegion) hartAnimation.getKeyFrame(stateTime, false), getX() - deltaX, getY());
            }

            if (currentState == State.DIE) {
                batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, false), getX() - deltaX, getY());
            }

            if (isDrawHealthBar)
                drawHealthBar(batch, 4, getHeight() + 6);
        } else {
            setDraw(true);
        }
        if (isDamaged)
            bloodSpray.draw(batch);
    }

    /**
     * метод сбрасывает "ЦЕЛЬ-ИГРОКА" для текущего юнита
     **/
    private void resetTarget() {
        isAttack = false;
        targetPlayer = null;
        isHaveTargetPlayer = false;
    }
}

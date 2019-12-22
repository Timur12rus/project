package com.timgapps.warfare.Units.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnit;
import com.timgapps.warfare.Units.Player.Bullets.Arrow;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class Archer1 extends PlayerUnit {

    private float stateTime;
    private boolean isAttack = false;   // флаг, указывет на то, в состоянии ли атаки находится юнит

    private World world;
    private Body body;

    private float x, y;
    private boolean isHaveTarget = false;
    private GameUnit targetEnemy;

    private boolean isHaveVerticalDirection = false;

    private Direction verticalDirectionMovement = Direction.NONE;

    private final float ATTACK_DISTANCE = 300;
    private final float VELOCITY = 0.6f;
    private static float APPEARANCE_TIME = 25;

    public Archer1(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        this.level = level;
        this.world = level.getWorld();

        createBody(x, y);
        createAnimations();     // создадим анимации для различных состояний персонажа
        currentState = State.WALKING;
        level.addChild(this, x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        /** если не имеем врага-цель, то найдём его **/
        if (!isHaveTarget) {
            findTarget();
        }

        /** проверим вертикальное перемещение, если достигнем нужной координаты по горизонтали, изменим направление
         * вертикального перемещения
         */
        if (isHaveTarget) {
            checkVerticalMovement();
            /** обновим позицию текущего игрового объекта **/


            /** проверим, может ли игровой юнит атаковать врага **/
            checkAttack((EnemyUnit) targetEnemy);
        }
        setPosition(body.getPosition().x * Level.WORLD_SCALE - 18, body.getPosition().y * Level.WORLD_SCALE);
    }


    /**
     * Метод для поиска цели-врага
     **/
    private void findTarget() {
        System.out.println("findTarget");
        ArrayList<EnemyUnit> enemies = level.getArrayEnemies();
        Vector2 playerPosition = body.getPosition();
        Vector2 enemyPosition;
//        targetEnemy = enemies.get(0);
        float minDistance = 0;
        for (int i = 0; i < enemies.size(); i++) {
            enemyPosition = enemies.get(i).getBodyPosition();
            if ((enemyPosition.x - playerPosition.x) * Level.WORLD_SCALE > 30) {
                if (minDistance == 0) {
                    minDistance = (enemyPosition.x - playerPosition.x) * Level.WORLD_SCALE;
                    targetEnemy = enemies.get(i);
                    System.out.println("minDistance = " + minDistance);
                }
                if ((enemyPosition.x - playerPosition.x) * Level.WORLD_SCALE < minDistance) {
                    minDistance = (enemyPosition.x - playerPosition.x) * Level.WORLD_SCALE;
                    targetEnemy = enemies.get(i);
                }
            }
        }

        if (targetEnemy != null) {
            isHaveTarget = true;
            calculateVerticalDirection((EnemyUnit) targetEnemy);
        } else {
            if (currentState == State.WALKING && !walkAnimation.isAnimationFinished(stateTime)) {
                body.setLinearVelocity(VELOCITY, 0);
            }
        }
    }


    /**
     * метод для проверки, может ли игровой юнит атаковать врага
     **/
    private void checkAttack(EnemyUnit targetEnemy) {
        float distance = (targetEnemy.getBodyPosition().x - body.getPosition().x) * Level.WORLD_SCALE;

        if (currentState == State.ATTACK && attackAnimation.isAnimationFinished(stateTime)) {
            attack();
            currentState = State.STAY;
            stay();
            stateTime = 0;
        }

        if ((verticalDirectionMovement == Direction.NONE) && (distance <= ATTACK_DISTANCE)) {
            if ((currentState == State.WALKING) && (walkAnimation.isAnimationFinished(stateTime))) {
                currentState = State.ATTACK;
                stay();
                stateTime = 0;
            } else if ((currentState == State.STAY) && (stayAnimation.isAnimationFinished(stateTime))) {
                if (targetEnemy.getHealth() <= 0) {
                    resetTarget();
                    currentState = State.WALKING;
                } else {
                    currentState = State.ATTACK;
                }
                stateTime = 0;
            }
        } else if ((verticalDirectionMovement == Direction.NONE) && (distance > ATTACK_DISTANCE)) {
            if ((currentState == State.STAY) && (stayAnimation.isAnimationFinished(stateTime))) {
                currentState = State.WALKING;
            }
            moveRight();
        }
    }

    public void resetTarget() {
        targetEnemy = null;
        isHaveTarget = false;
//        isAttack = false;

    }

    @Override
    public void attack() {
        super.attack();
        new Arrow(level, body.getPosition().x * Level.WORLD_SCALE, body.getPosition().y * Level.WORLD_SCALE, damage);
    }

    private void moveRight() {
        body.setLinearVelocity(VELOCITY, 0);
    }

    /**
     * Метод для вычисления направления вертикального перемещения
     **/
    private void calculateVerticalDirection(EnemyUnit targetEnemy) {
        Vector2 playerPosition = body.getPosition();
        Vector2 enemyPosition = targetEnemy.getBodyPosition();
        if (enemyPosition.y > playerPosition.y) {
            verticalDirectionMovement = Direction.UP;
            isHaveVerticalDirection = true;
            moveUp();
        } else if (enemyPosition.y < playerPosition.y) {
            verticalDirectionMovement = Direction.DOWN;
            isHaveVerticalDirection = true;
            moveDown();
        } else {
            verticalDirectionMovement = Direction.NONE;
            isHaveVerticalDirection = false;
            stay();
        }
    }

    /**
     * Метод для преверки вертикального перемещения, нужно ли еще перемещаться вверх или вниз
     **/
    private void checkVerticalMovement() {
        Vector2 playerPosition = body.getPosition();
        Vector2 enemyPosition = targetEnemy.getBodyPosition();
        if (isHaveVerticalDirection) {
            if ((verticalDirectionMovement == Direction.UP) && (playerPosition.y > enemyPosition.y)) {
                verticalDirectionMovement = Direction.NONE;
                stay();
            } else if ((verticalDirectionMovement == Direction.DOWN) && (playerPosition.y < enemyPosition.y)) {
                verticalDirectionMovement = Direction.NONE;
                stay();
            }
//            else if (verticalDirectionMovement == Direction.NONE)

        }
    }

    private void moveUp() {
        body.setLinearVelocity(0, VELOCITY);
    }

    private void moveDown() {
        body.setLinearVelocity(0, -VELOCITY);
    }

    private void stay() {
        body.setLinearVelocity(0, 0);
    }


    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        if (level.getState() == Level.PLAY) {
        stateTime += Gdx.graphics.getDeltaTime();
//        }
//        batch.setColor(1, 1, 1, 1);

//        if (isDraw) {
        if (currentState == State.WALKING) {
            batch.draw((TextureRegion) walkAnimation.getKeyFrame(stateTime, true), getX() - 212, getY() - 26);
        }

        if (currentState == State.ATTACK) {
            batch.draw((TextureRegion) attackAnimation.getKeyFrame(stateTime, true), getX() - 212, getY() - 26);
        }

        if (currentState == State.STAY) {
            batch.draw((TextureRegion) stayAnimation.getKeyFrame(stateTime, true), getX() - 212, getY() - 26);
        }

        if (currentState == State.RUN) {
            batch.draw((TextureRegion) runAnimation.getKeyFrame(stateTime, true), getX() - 212, getY() - 26);
        }

        if (currentState == State.DIE) {
            batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, true), getX() - 212, getY() - 26);
        }

        if (currentState == State.HART) {
            batch.draw((TextureRegion) hartAnimation.getKeyFrame(stateTime, true), getX() - 212, getY() - 26);
        }
    }

    private void createAnimations() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // получим кадры и добавим в анимацию ходьбы персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("archer1Walk" + i)));
        for (int i = 3; i < 0; i--)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("archer1Walk" + i)));
        walkAnimation = new Animation(0.1f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию атаки персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("archer1Attack" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion("archer1Attack0")));
        attackAnimation = new Animation(0.12f, frames);
//        attackAnimation = new Animation(0.12f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию стоянки персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("archer1Stay" + i)));
        for (int i = 3; i < 1; i--)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("archer1Stay" + i)));
        stayAnimation = new Animation(0.25f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию уничтожения персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("archer1Die" + i)));
        dieAnimation = new Animation(0.1f, frames);
        frames.clear();
        stateTime = 0;

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("archer1Hart" + i)));
        hartAnimation = new Animation(0.05f, frames);
        frames.clear();

    }

    @Override
    public void createBody(float x, float y) {

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(12 / Level.WORLD_SCALE, 12 / Level.WORLD_SCALE);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.filter.categoryBits = GameUnit.PLAYER_BIT;
        fDef.filter.maskBits = GameUnit.ENEMY_BIT;

        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        body.setTransform((x) / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);
    }

    public static float getAppearanceTime() {
        return APPEARANCE_TIME;
    }
}

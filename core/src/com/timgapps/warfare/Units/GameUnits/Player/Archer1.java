package com.timgapps.warfare.Units.GameUnits.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnits.GameUnit;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Arrow;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class Archer1 extends PlayerUnit {

    //    private float stateTime;
    private boolean isAttack = false;   // флаг, указывет на то, в состоянии ли атаки находится юнит
    private boolean isHaveTarget = false;
    private GameUnit targetEnemy;

    private boolean isHaveVerticalDirection = false;

    private Direction verticalDirectionMovement = Direction.NONE;

    private final float ATTACK_DISTANCE = 300;
    //    private final float VELOCITY = 0.6f;
    private static float APPEARANCE_TIME = 1;
    //    private static float APPEARANCE_TIME = 30;
    protected static int ENERGY_PRICE = 1;
//    protected static int ENERGY_PRICE = 20;


    private boolean isFired = false;

    public Archer1(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        velocity = 0.4f;
        energyPrice = 20;
        this.level = level;
        this.world = level.getWorld();

        xPosDamageLabel = -45;

        this.setWidth(Warfare.atlas.findRegion("archer1Walk0").getRegionWidth());
        this.setHeight(Warfare.atlas.findRegion("archer1Walk0").getRegionHeight());
        this.debug();

//        body = createBody(x, y);
        createAnimations();     // создадим анимации для различных состояний персонажа
        currentState = State.WALKING;
        level.addChild(this, x, y);
        level.arrayActors.add(this);
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

        if (currentState == State.DIE || currentState == State.STAY || currentState == State.ATTACK) {
            stay();
        }
//        setPosition(body.getPosition().x * Level.WORLD_SCALE - 18, body.getPosition().y * Level.WORLD_SCALE - bodyHeight / 2);
    }


    /**
     * Метод для поиска цели-врага
     **/
    private void findTarget() {
//        System.out.println("findTarget");
        ArrayList<EnemyUnit> enemies = level.getArrayEnemies();
        Vector2 playerPosition = body.getPosition();
        Vector2 enemyPosition;
//        targetEnemy = enemies.get(0);


        /** найдем ближайшего к юниту ВРАГА **/
        float minDistance = 0;
        for (int i = 0; i < enemies.size(); i++) {
            enemyPosition = enemies.get(i).getBodyPosition();
            /** если разница позиций по оси Х > 30, такой ВРАГ подойдет **/
            if ((enemyPosition.x - playerPosition.x) * Level.WORLD_SCALE > 30) {
                if (minDistance == 0) {
                    minDistance = (enemyPosition.x - playerPosition.x) * Level.WORLD_SCALE;
                    targetEnemy = enemies.get(i);
//                    System.out.println("minDistance = " + minDistance);
                }
                if ((enemyPosition.x - playerPosition.x) * Level.WORLD_SCALE < minDistance) {
                    minDistance = (enemyPosition.x - playerPosition.x) * Level.WORLD_SCALE;
                    targetEnemy = enemies.get(i);
                }
            }
        }

        /** если имеем цель-врага, то вычислим направление вертикального перемещения
         * если не имеем цель-врага, то движемся вправо
         * **/
        if (targetEnemy != null) {
            isHaveTarget = true;
            calculateVerticalDirection((EnemyUnit) targetEnemy);
        } else {
            if (currentState == State.WALKING) {
//            if (currentState == State.WALKING && !walkAnimation.isAnimationFinished(stateTime)) {
                moveRight();
            }
        }
    }

//    @Override
//    public void attack() {
//        super.attack();
//        stay();
//    }

    /**
     * метод для проверки, может ли игровой юнит атаковать врага
     **/
    private void checkAttack(EnemyUnit targetEnemy) {
        float distance = (targetEnemy.getBodyPosition().x - body.getPosition().x) * Level.WORLD_SCALE;


        /** юнит выпустит стрелу на третьем кадре анимации
         * @param isFired - флаг, если выстрелил, то isFired = true, не будет стрелять, пока isFired снова не станет - false
         */
        if (currentState == State.ATTACK && (attackAnimation.getKeyFrameIndex(stateTime) == 3)) {
            if (!isFired) {
                isFired = true;
                fire();
            }
        }


        /** если юнит завершил атаку, то текущее состояние устанавливаем = State.STAY **/
        if (currentState == State.ATTACK && attackAnimation.isAnimationFinished(stateTime)) {
            stateTime = 0;
            currentState = State.STAY;

            isFired = false;

//            /** сбросим флаг, что юнит (стрелок) атакует */
//            resetIsFired();

        }

        /** если текущее состояние = State.STAY и анмация завершена **/
        if ((currentState == State.STAY) && (stayAnimation.isAnimationFinished(stateTime))) {

//            if (isFired) {
//                isFired = false;
//            }
            /** проеверим унчитожен ли ВРАГ-ЦЕЛЬ?
             * если уничтожен, то устанавливаем текущее состояние = State.WALKING
             * если не уничтожен, то устанавливаем текщее состояние = State.ATTACK **/
            if (targetEnemy.getHealth() <= 0) {
                resetTarget();
                currentState = State.WALKING;
            } else {
                currentState = State.ATTACK;
            }
            stateTime = 0;
        }

        /** если вертикальное перемещение = Direction.NONE и расстояние до врага <= ATTACK_DISTANCE
         * если текущее состояние = State.WALKING и анимация завершена, установим тек. состояние = State.ATTACK
         * **/
        if ((verticalDirectionMovement == Direction.NONE) && (distance <= ATTACK_DISTANCE)) {
            if ((currentState == State.WALKING) && (walkAnimation.isAnimationFinished(stateTime))) {
                stateTime = 0;
                currentState = State.ATTACK;
                stay();
            }
        }

        /** если вертикальное перемещение = Direction.NONE и и расстояние до врага <= ATTACK_DISTANCE
         * если текущее состояние = State.STAY и анимация завершена, установим тек. состояние = State.WALKING и двигаем юнита вправо
         * **/
        if ((verticalDirectionMovement == Direction.NONE) && (distance > ATTACK_DISTANCE)) {
            if ((currentState == State.STAY) && (stayAnimation.isAnimationFinished(stateTime))) {
                moveRight();
                stateTime = 0;
                currentState = State.WALKING;
            }

            if (currentState == State.WALKING && walkAnimation.isAnimationFinished(stateTime)) {
                stateTime = 0;
                moveRight();
            }
//            System.out.println("moveRight");
//            moveRight();
        }
    }

    public void resetTarget() {
        targetEnemy = null;
        isHaveTarget = false;
//        isAttack = false;

    }

    public void fire() {
        new Arrow(level, this, body.getPosition().x * Level.WORLD_SCALE, body.getPosition().y * Level.WORLD_SCALE, damage);
    }

    private void moveRight() {

        // TODO: 20.02.2020  Нужно будети справить, чтобы юнит мог отбиваться в этот момент от врагов, если они подойдут близко !!!!!
//        System.out.println("barricadeX = " + barricade.getX());
//        System.out.println("archerX = " + getX());
        if (barricade.getX() - getX() > 100) {
            body.setLinearVelocity(velocity, 0);
        } else {
            if (currentState == State.WALKING && walkAnimation.isAnimationFinished(stateTime)) {
                stay();
                stateTime = 0;
                currentState = State.STAY;
            }
        }
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
        body.setLinearVelocity(0, velocity);
    }

    private void moveDown() {
        body.setLinearVelocity(0, -velocity);
    }

    private void stay() {
        body.setLinearVelocity(0, 0);
    }


    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        if (level.getState() == Level.PLAY) {
//        stateTime += Gdx.graphics.getDeltaTime();
//        }
//        batch.setColor(0.1f, 0.1f, 0.1f, 1);

//        if (isDraw) {
        if (currentState == State.WALKING) {
            batch.draw((TextureRegion) walkAnimation.getKeyFrame(stateTime, true), getX() - 174, getY());
        }

        if (currentState == State.ATTACK) {
            batch.draw((TextureRegion) attackAnimation.getKeyFrame(stateTime, true), getX() - 174, getY());
//            batch.draw((TextureRegion) attackAnimation.getKeyFrame(stateTime, true), getX() - 212, getY() - 26);
        }

        if (currentState == State.STAY) {
            batch.draw((TextureRegion) stayAnimation.getKeyFrame(stateTime, true), getX() - 174, getY());
        }

        if (currentState == State.RUN) {
            batch.draw((TextureRegion) runAnimation.getKeyFrame(stateTime, true), getX() - 174, getY());
        }

        if (currentState == State.DIE) {
            batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, false), getX() - 174, getY());
        }

        if (currentState == State.HART) {
            batch.draw((TextureRegion) hartAnimation.getKeyFrame(stateTime, true), getX() - 174, getY());
        }

        if (isDrawHealthBar)
            drawHealthBar(batch, -64, getHeight() + 8);
//        batch.setColor(1, 1, 1, 1);
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

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("archer1Hart" + i)));
        hartAnimation = new Animation(0.05f, frames);
        frames.clear();

    }

    public static int getEnergyPrice() {
        return ENERGY_PRICE;
    }

    public static float getAppearanceTime() {
        return APPEARANCE_TIME;
    }

    public void resetIsFired() {
        isFired = false;
    }
}

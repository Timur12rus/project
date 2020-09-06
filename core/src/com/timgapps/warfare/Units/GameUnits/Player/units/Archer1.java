package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnits.GameUnit;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Arrow;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class Archer1 extends PlayerUnit {
    private boolean isAttack = false;   // флаг, указывет на то, в состоянии ли атаки находится юнит
    private boolean isHaveTarget = false;
    private GameUnit targetEnemy;
    private boolean isHaveVerticalDirection = false;
    private Direction verticalDirectionMovement = Direction.NONE;
    private final float ATTACK_DISTANCE = 300;
    private final float MIN_ATTACK_DISTANCE = 24;
    private final float VELOCITY = 0.5f;
    private static float APPEARANCE_TIME = 1;
    //    private static float APPEARANCE_TIME = 30;
    protected static int ENERGY_PRICE = 1;
    //    protected static int ENERGY_PRICE = 20;
    private float minDistance = 0; // расстояние до ближайшего вражеского юнита
    private boolean isReachedEnemyYPos;
    private boolean isFired = false;
    private Vector2 velocity;

    public Archer1(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
//        velocity = 0.4f;
        velocity = new Vector2();
        energyPrice = 20;
        this.level = level;
        this.world = level.getWorld();
        xPosDamageLabel = -45;

        this.setWidth(Warfare.atlas.findRegion("archer1Walk0").getRegionWidth());
        this.setHeight(Warfare.atlas.findRegion("archer1Walk0").getRegionHeight());
        this.debug();
        createAnimations();     // создадим анимации для различных состояний персонажа
        currentState = State.WALKING;
        level.addChild(this, x, y);
        level.arrayActors.add(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (health <= 0 && body.isActive()) {
            currentState = State.DIE;
            stateTime = 0;
            stay();
            body.setActive(false);
        }

        if (body.isActive()) {
            if (currentState == State.WALKING) {
                if (isHaveTarget) {  // если определен "враг-цель", то
                    moveToTarget();     //движемся к цели
                } else {                  // в противном случае, если "враг-цель" не определен, то двигаемся прямо вправо
                    if (checkCanMoveRight()) {   //если можем двигаться вправо
                        moveRight();             // движемся вправо
                    }
                }
                if (verticalDirectionMovement == Direction.NONE && !checkCanMoveRight()) {
                    stateTime = 0;
                    currentState = State.STAY;
                    velocity.set(0, 0);
                }
                body.setLinearVelocity(velocity);
            }
            // проверим, может ли игровой юнит атаковать врага **/
            if (isReachedEnemyYPos && targetEnemy != null) { // если юнит достиг вертикальной координаты вражеского "юнита-цели"
                checkAttack((EnemyUnit) targetEnemy);
            }
            // TODO если юнит достиг верт-ой координаты и уперся в баррикаду, что делать тогда, нужно стоять и ждать, пока враг не подойдет
            System.out.println("CurrentState = " + currentState);
            System.out.println("targetEnemy = " + targetEnemy);
            if (currentState == State.STAY && stayAnimation.isAnimationFinished(stateTime) && barricade.isBarricadeDestroyed()) {
                stateTime = 0;
                currentState = State.WALKING;
            }
        }

        /** проверим вертикальное перемещение, если достигнем нужной координаты по горизонтали, изменим направление
         * вертикального перемещения
         */
        if (currentState == State.WALKING && !isAttack || currentState == State.STAY && !isAttack) {
            findTarget();
        }
        // если да, то движемся вправо, если нет, продолжаем
        if (!isReachedEnemyYPos && targetEnemy != null)   // если игрвой юнит не достиг вертикальной координаты Ypos у вражеского юнита
            checkVerticalMovement();
        if (currentState == State.DIE || currentState == State.STAY || currentState == State.ATTACK) {
            stay();
        }
    }

    /**
     * Метод для поиска цели-врага
     **/
    private void findTarget() {
        /** массив вражеских юнитов **/
        ArrayList<EnemyUnit> enemies = level.getArrayEnemies();
        /** массив вражеских юнитов - "потенциальных целей" **/
        ArrayList<EnemyUnit> targetEnemies = new ArrayList<EnemyUnit>();

        /** выполним поиск ВРАЖЕСКОГО ЮНИТА-ЦЕЛЬ **/
        try {
            /** выполним поиск потенциальных вражеских "юнитов-целей" */
            for (int i = 0; i < enemies.size(); i++) {
                /** проверим расстояяние до вражеского юнита, можем ли мы двигаться к нему (успеем ли..)
                 * если да, то добавим его в массив вражеских юнитов, которых видит ИГРОВОЙ ЮНИТ
                 * **/
                if (checkDistanceToEnemy(enemies.get(i))) {
//                    System.out.println("checkDistance to enemy = TRUE");
                    targetEnemies.add(enemies.get(i));  // добавим вражеский юнита в массив потенциальных "целевых юнитов"
                } else {
                    System.out.println("checkDistance to enemy = FALSE");
                }
            }
            /** здесь определим самого ближнего ВРАЖЕСКОГО ЮНИТА к ИГРОВОМУ
             * т.е. найдем по расстоянию между ними, т.е. самое маленькое расстояние
             * **/
            minDistance = (targetEnemies.get(0).getBodyPosition().sub(getBodyPosition())).len();
            GameUnit newTargetEnemy = targetEnemies.get(0);
            for (int i = 1; i < targetEnemies.size(); i++) {
                float distanceToEnemy = (targetEnemies.get(i).getBodyPosition().sub(getBodyPosition())).len();
                if (distanceToEnemy < minDistance) {
                    minDistance = distanceToEnemy;
                    newTargetEnemy = targetEnemies.get(i);
                }
            }
            minDistance = 0;
            if (targetEnemy != null) {
                // если текущий "враг-цель" находится дальше чем новый найденный, то новый "враг-цель"  - это новый найденный
                if (!targetEnemy.equals(newTargetEnemy)) {
                    isHaveTarget = true;        // изменим флаг на true, т.е. есть "враг-цель"
                    calculateVerticalDirection();       // вычислим направление вертикального перемещения
                    currentState = State.WALKING;
                }
//                isHaveTarget = true;        // изменим флаг на true, т.е. есть "враг-цель"
                // TODO возможно здесь нужно будет изменить и связать с расстоянием до баррикады

//                calculateVerticalDirection();       // вычислим направление вертикального перемещения
//                currentState = State.WALKING;
            } else {
                targetEnemy = newTargetEnemy;
                isHaveTarget = true;
                calculateVerticalDirection();       // вычислим направление вертикального перемещения
                currentState = State.WALKING;
            }
        } catch (Exception e) {
//            currentState = State.WALKING;
            verticalDirectionMovement = Direction.NONE;
        }
    }

    /**
     * метод проверяет расстояние до врага
     **/
    private boolean checkDistanceToEnemy(EnemyUnit enemyUnit) {
        /** расстояние от врага до текущего юнита (путь который должен пройти игровой юнита до врага **/
        Vector2 bodyPosition = new Vector2();       // текущая позиция юнита
        bodyPosition.set(getBodyPosition().x + 24 / Level.WORLD_SCALE, getBodyPosition().y);      // позиция игрового юнита
        Vector2 enemyPos = enemyUnit.getBodyPosition();   // позиция вражеского юнита
        enemyPos.x -= 24 / Level.WORLD_SCALE;
        Vector2 distanceToEnemy = (enemyPos.sub(bodyPosition));  // расстояние между юнитом и вражеский юнитом

        /** время необходимое для движения до вражеского юнита tф = S / V **/
        float time = distanceToEnemy.len() / VELOCITY;

        /** рассчитанное время для движения до вражеского юнита tp = Sy / Vy**/
        float y1 = enemyUnit.getBodyPosition().y;
        float y2 = bodyPosition.y;

        float timeCalculated = (float) Math.abs((y1 - y2) / (VELOCITY * Math.sin(MathUtils.degreesToRadians * distanceToEnemy.angle())));
        if (timeCalculated <= time)
            return true;        // игровой юнит успевает достигнуть варжеского = true
        else
            return false;       // игровой юнит не успевает достигнуть вражеского = false
    }

    /**
     * метод для движения игрового юнита к "целевому" вражескому юниту
     **/
    private void moveToTarget() {
        float posY = body.getPosition().y;
        float posYTarget = targetEnemy.getBodyPosition().y;
        Vector2 enemyPos = targetEnemy.getBodyPosition();
        enemyPos.x -= (36 / Level.WORLD_SCALE);
        Vector2 velocityDirection = targetEnemy.getBodyPosition().sub(body.getPosition());      // вектор направления движения игрового юнита
//        Vector2 velocityDirection = targetEnemy.getBodyPosition().sub(body.getPosition());      // вектор направления движения игрового юнита

        // опрледелим направление вертикального перемещения
        if (verticalDirectionMovement == Direction.DOWN) {
            System.out.println("verticalDirection = " + verticalDirectionMovement);
            /** если направление вертикального перемещения DOWN, то проверим условие: **/
            if (posY > posYTarget) {
                if (velocityDirection.x > ATTACK_DISTANCE / Level.WORLD_SCALE)  // если расстояние по оси х до вражеского юнита больше ATACK_DISTANCE
                    velocityDirection.x -= ATTACK_DISTANCE / Level.WORLD_SCALE;
                else
                    velocityDirection.x = 0;
//                body.setLinearVelocity(velocityDirection.nor().scl(VELOCITY));
                if (checkCanMoveRight()) {
                    velocity = velocityDirection.nor().scl(VELOCITY);
                } else {
                    velocity.set(0, -VELOCITY);
                }
//                body.setLinearVelocity(velocityDirection.nor().scl(VELOCITY));
                System.out.println("velocity.y = " + body.getLinearVelocity().y);
//                }
            } else {
                isReachedEnemyYPos = true;
                verticalDirectionMovement = Direction.NONE;
            }

            /** если направления вертикального перемещения DOWN, то проверим условие: **/
        } else if (verticalDirectionMovement == Direction.UP) {
            System.out.println("verticalDirection = " + verticalDirectionMovement);
            if (posY < posYTarget) {
                if (velocityDirection.x > ATTACK_DISTANCE / Level.WORLD_SCALE)  // если расстояние по оси х до вражеского юнита больше ATACK_DISTANCE
                    velocityDirection.x -= ATTACK_DISTANCE / Level.WORLD_SCALE;
                else
                    velocityDirection.x = 0;
//                velocity = velocityDirection.nor().scl(VELOCITY);
                if (checkCanMoveRight()) {
                    velocity = velocityDirection.nor().scl(VELOCITY);
                } else {
                    velocity.set(0, VELOCITY);
                }
//                body.setLinearVelocity(velocityDirection.nor().scl(velocity));
                System.out.println("velocity.y = " + body.getLinearVelocity().y);
            } else {
                isReachedEnemyYPos = true;
                verticalDirectionMovement = Direction.NONE;
            }
        }
        if (verticalDirectionMovement == Direction.NONE) {
            System.out.println("verticalDirection = " + verticalDirectionMovement);
//        else if (verticalDirectionMovement == Direction.NONE) {
            if (checkCanMoveRight()) {
                velocity.set(VELOCITY, 0);
//                body.setLinearVelocity(velocity, 0);
            } else {
                if (currentState != State.STAY) {
                    stateTime = 0;
                    currentState = State.STAY;
                }
            }
        }
    }

    /**
     * метод определяет достиг ли юнит баррикады, и может ли двигаться вперед
     **/
    public boolean checkCanMoveRight() {
        boolean canMoveRight = false;
        if (barricade.isBarricadeDestroyed()) {
            canMoveRight = true;
        } else {
            if ((barricade.getX() - getX() > 200)) {      // если расстояние м/у игровым юнитом и баррикадой меншье или равно 200
                canMoveRight = true;
            }
        }
        return canMoveRight;
    }

    /**
     * Метод для вычисления направления вертикального перемещения
     **/
    private Direction calculateVerticalDirection() {
        float posY = body.getPosition().y;
        float posYTarget = targetEnemy.getBodyPosition().y;
        if (posY < posYTarget) verticalDirectionMovement = Direction.UP;
        if (posY > posYTarget) verticalDirectionMovement = Direction.DOWN;
        if (posY == posYTarget) verticalDirectionMovement = Direction.NONE;

        isHaveVerticalDirection = true;
        return verticalDirectionMovement;
    }

    /**
     * метод для проверки, может ли игровой юнит атаковать врага
     **/
    private void checkAttack(EnemyUnit targetEnemy) {
        float distance = (targetEnemy.getBodyPosition().x - body.getPosition().x) * Level.WORLD_SCALE;
        if (distance <= ATTACK_DISTANCE) {
            System.out.println("Distance = " + distance);
            isAttack = true;
        } else moveRight();

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
            isFired = false;
            stateTime = 0;
            currentState = State.STAY;
        }

        /** если текущее состояние = State.STAY и анмация завершена **/
        if ((currentState == State.STAY) && (stayAnimation.isAnimationFinished(stateTime))) {
            /** проеверим унчитожен ли ВРАГ-ЦЕЛЬ?
             * если уничтожен, то устанавливаем текущее состояние = State.WALKING
             * если не уничтожен, то устанавливаем текщее состояние = State.ATTACK **/
            if (targetEnemy.getHealth() <= 0) {
                isAttack = false;
                resetTarget();
                currentState = State.WALKING;
            } else {
                if (distance <= ATTACK_DISTANCE && isAttack) {
                    currentState = State.ATTACK;
                } else {
                    currentState = State.STAY;
                }
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

        if ((verticalDirectionMovement == Direction.NONE) && (distance <= ATTACK_DISTANCE)) {
            if ((currentState == State.STAY) && (stayAnimation.isAnimationFinished(stateTime))) {
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
        }
    }

    public void resetTarget() {
        // TODO нужно сделать так, чтобы метод вызывался когда вражеский-юнит "цель" уничтожен
        targetEnemy = null;
        isHaveTarget = false;
        isReachedEnemyYPos = false;
    }

    public void fire() {
        new Arrow(level, this, body.getPosition().x * Level.WORLD_SCALE, body.getPosition().y * Level.WORLD_SCALE, damage);
    }

    private void moveRight() {
        velocity.set(VELOCITY, 0);
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
                isHaveVerticalDirection = false;
                stay();
            } else if ((verticalDirectionMovement == Direction.DOWN) && (playerPosition.y < enemyPosition.y)) {
                verticalDirectionMovement = Direction.NONE;
                isHaveVerticalDirection = false;
                stay();
            }
        }
    }

//    private void moveUp() {
//        body.setLinearVelocity(0, velocity);
//    }
//
//    private void moveDown() {
//        body.setLinearVelocity(0, -velocity);
//    }

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

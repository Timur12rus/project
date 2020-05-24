package com.timgapps.warfare.Units.GameUnits.Player;

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
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnits.GameUnit;
import com.timgapps.warfare.Warfare;

import java.awt.Label;
import java.util.ArrayList;

import static com.badlogic.gdx.math.Vector2.len;

public class Gnome extends PlayerUnit {
    private float x, y;
    private float minDistance = 0; // расстояние до ближайшего вражеского юнита
    private float distanceToVerticalMovement = 300;     // минимальное расстояние до врага, чтобы изменить направление движения игрока по вертикали

    private boolean isHaveVerticalDirection = false;

    private Direction verticalDirectionMovement = Direction.NONE;
//    private static float APPEARANCE_TIME = 10;
//    protected static int ENERGY_PRICE = 15;

    private static float APPEARANCE_TIME = 1;
    protected static int ENERGY_PRICE = 1;


    public Gnome(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        this.level = level;

        velocity = 0.6f;
        xPosDamageLabel = -50;

        this.setWidth(Warfare.atlas.findRegion("gnomeWalk0").getRegionWidth());
        this.setHeight(Warfare.atlas.findRegion("gnomeWalk0").getRegionHeight());
        this.debug();
        createAnimations();                  // создадим анимации для различных состояний персонажа
        currentState = State.RUN;            // установим текущее состояние юнита = State.RUN
        level.addChild(this, x, y);
        level.arrayActors.add(this);        // добавим юнита к массиву игровых актеров
    }

    @Override
    public void act(float delta) {
        super.act(delta);


        System.out.println("Gnome isHaveTarget = " + isHaveTarget + " /Target = " + targetEnemy);
        System.out.println("isAttack = " + isAttack + " /isAttackBarricade = " + isAttackBarricade);

        if (currentState == State.RUN && !isAttack) {
            findTarget();
//            if (!isHaveTarget)                            // если юнит не имеет цель-врага, то найдем цель-врага
//                findTarget();
        }

        if (health <= 0 && body.isActive()) {
            currentState = State.DIE;
            stateTime = 0;
            stay();
            body.setActive(false);
        }

        if (body.isActive()) {
            /** проверим, атакует ли юнита баррикаду **/
            if (!isAttackBarricade) {
                if (currentState == State.RUN) {
                    if (isHaveTarget) {  // если определен "враг-цель", то
                        moveToTarget();     //движемся к цели
                    } else {                  // в противном случае, если "враг-цель" не определен, то двигаемся прямо вправо
                        moveRight(body);    // движемся вправо
                    }
                }
            }

//            if (targetEnemy != null) {
            if (currentState == State.ATTACK) {     // если текущее состояние ATTACK
                stay();                             // установим скорость тела (0;0)
                if (attackAnimation.isAnimationFinished(stateTime)) {
                    stateTime = 0;
                    currentState = State.STAY;                  // установим состояние STAY
                    if (isAttack) {                             // если юнит атакует врага
                        if (targetEnemy != null) {
                            if (targetEnemy.getHealth() > 0)
                                inflictDamage(targetEnemy, damage);     // наносим урон вражескому юниту
                            if (targetEnemy.getHealth() <= 0) {
                                resetTarget();
                                currentState = State.RUN;
                            }
                        }
                    } else if (isAttackBarricade) {              // если юнит атакует баррикаду
                        if (barricade != null) {
                            barricade.setHealth(damage);
                            stateTime = 0;
                            currentState = State.STAY;
                            if (barricade.getHealth() <= 0) {
                                isAttackBarricade = false;
                                currentState = State.RUN;
                            }
                        }
                    }
                }
            }

            if (currentState == State.STAY && stayAnimation.isAnimationFinished(stateTime)) {
                if (isAttack || isAttackBarricade)
                    currentState = State.ATTACK;
                else
                    currentState = State.RUN;
                stateTime = 0;
            }

            if (currentState == State.STAY) {
                stay();
            }
        }

        if (currentState == State.DIE && dieAnimation.isAnimationFinished(stateTime)) {

            /** МОЖЕТ ПРИГОДИТЬСЯ 17.02.2020
             //            destroy();
             **/
            setToDestroy();
        }
    }

    private void stay() {
        body.setLinearVelocity(0, 0);
    }

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
     * метод для поиска "ПРОИТВНИКА-ЦЕЛИ"
     **/
    private void findTarget() {

        /** массив вражеских юнитов **/
        ArrayList<EnemyUnit> enemies = level.getArrayEnemies();

        /** массив вражеских юнитов - "потенциальных целей" **/
        ArrayList<EnemyUnit> targetEnemies = new ArrayList<EnemyUnit>();

        /** выполним поиск ВРАЖЕСКОГО ЮНИТА-ЦЕЛЬ **/
        try {
            for (int i = 0; i < enemies.size(); i++) {

                /** проверим расстояяние до вражеского юнита, можем ли мы двигаться к нему (успеем ли..)
                 * если да, то добавим его в массив вражеских юнитов, которых видит ИГРОВОЙ ЮНИТ
                 * **/

                if (checkDistanceToEnemy(enemies.get(i))) {
                    System.out.println("checkDistance to enemy = TRUE");
                    targetEnemies.add(enemies.get(i));
                } else {
                    System.out.println("checkDistance to enemy = FALSE");
                }
            }

            /** здесь определим самого ближнего ВРАЖЕСКОГО ЮНИТА к ИГРОВОМУ
             * т.е. найдем по расстоянию между ними, т.е. самое маленькое расстояние
             * **/
            minDistance = (targetEnemies.get(0).getBodyPosition().sub(getBodyPosition())).len();
            targetEnemy = targetEnemies.get(0);
            for (int i = 1; i < targetEnemies.size(); i++) {
                float distanceToEnemy = (targetEnemies.get(i).getBodyPosition().sub(getBodyPosition())).len();
                if (distanceToEnemy < minDistance) {
                    minDistance = distanceToEnemy;
                    targetEnemy = targetEnemies.get(i);
                }
            }

            minDistance = 0;

            if (targetEnemy != null) {
                isHaveTarget = true;        // изменим флаг на true, т.е. есть "враг-цель"
                calculateVerticalDirection();       // вычислим направление вертикального перемещения
                currentState = State.RUN;
            }

        } catch (Exception e) {
            System.out.println("ERROR FIND TARGET!!!!!!!!!!");
            currentState = State.RUN;
            verticalDirectionMovement = Direction.NONE;
        }
    }

    private boolean checkDistanceToEnemy(EnemyUnit enemyUnit) {

        /** расстояние от врага до текущего юнита (путь который должен пройти игровой юнита до врага **/
//        - 24 / Level.WORLD_SCALE;
        Vector2 bodyPosition = new Vector2();
        bodyPosition.set(getBodyPosition().x - 24 / Level.WORLD_SCALE, getBodyPosition().y);

        Vector2 distanceToEnemy = (enemyUnit.getBodyPosition().sub(bodyPosition));
//        Vector2 distanceToEnemy = (enemyUnit.getBodyPosition().sub(getBodyPosition()));

        /** время необходимое для движения до вражеского юнита t = S / V **/
        float time = distanceToEnemy.len() / velocity;

        /** рассчитанное время для движения до вражеского юнита tp = Sy / Vy**/
        float timeCalculated = (Math.abs((float) (distanceToEnemy.len() * Math.sin(distanceToEnemy.angle()))));


//        System.out.println("time = " + time);
//        System.out.println("timeCalculated = " + timeCalculated);

        if (enemyUnit.getBodyPosition().x + 12 / Level.WORLD_SCALE > bodyPosition.x) {
//        if (enemyUnit.getBodyPosition().x > getBodyPosition().x) {
            if (timeCalculated < time) return true;
            else
                return false;
        } else
            return false;
    }


    private void moveToTarget() {
        float posY = body.getPosition().y;
        float posYTarget = targetEnemy.getBodyPosition().y;

        Vector2 velocityDirection = targetEnemy.getBodyPosition().sub(getBodyPosition());

        // опрледелим направление вертикального перемещения
        if (verticalDirectionMovement == Direction.DOWN) {
            /** если направления вертикального перемещения DOWN, то проверим условие: **/
            if (posY > posYTarget) {
                body.setLinearVelocity(velocityDirection.nor().scl(velocity));
            } else {
                verticalDirectionMovement = Direction.NONE;
            }
            /** если направления вертикального перемещения DOWN, то проверим условие: **/
        } else if (verticalDirectionMovement == Direction.UP) {
            if (posY < posYTarget) {
                body.setLinearVelocity(velocityDirection.nor().scl(velocity));
            } else {
                verticalDirectionMovement = Direction.NONE;
            }
        } else if (verticalDirectionMovement == Direction.NONE) {
            body.setLinearVelocity(velocity, 0);
        }
    }

    /**
     * метод для движения юнита вправо
     **/
    public void moveRight(Body body) {
        Vector2 vel = body.getLinearVelocity();
        vel.x = velocity;
        vel.y = 0;
        body.setLinearVelocity(vel);
    }


    /**
     * метод для создания анимации юнита
     **/
    protected void createAnimations() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // получим кадры и добавим в анимацию ходьбы персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeWalk" + i)));
        for (int i = 4; i < 0; i--)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeWalk" + i)));
        walkAnimation = new Animation(0.1f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию атаки персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeAttack" + i)));
        attackAnimation = new Animation(0.1f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию стоянки персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeStay" + i)));
        for (int i = 4; i < 1; i--)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeStay" + i)));
        stayAnimation = new Animation(0.2f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию бега персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeRun" + i)));
        runAnimation = new Animation(0.15f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию уничтожения персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeDie" + i)));
        dieAnimation = new Animation(0.1f, frames);
        frames.clear();
//        stateTime = 0;
    }


    /**
     * метод сбрасывает текущую цель
     **/
    @Override
    public void resetTarget() {
        super.resetTarget();
//        targetEnemy = null;
//        isHaveTarget = false;
//        isAttack = false;
        isHaveVerticalDirection = false;
        verticalDirectionMovement = Direction.NONE;
    }

    /**
     * метод устанавливает ВРАГА_ЦЕЛЬ (используется при определении столкновения с вражеским юнитом
     **/


    public static int getEnergyPrice() {
        return ENERGY_PRICE;
    }

    public static float getAppearanceTime() {
        return APPEARANCE_TIME;
    }

    /**
     * метод для получения позиции тела
     **/
    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        if (level.getState() == Level.PLAY) {
//        stateTime += Gdx.graphics.getDeltaTime();
//        }
//        batch.setColor(1, 1, 1, 1);

//        if (isDraw) {
        if (currentState == State.WALKING) {
            batch.draw((TextureRegion) walkAnimation.getKeyFrame(stateTime, true), getX() - 124, getY());
        }

        if (currentState == State.ATTACK) {
            batch.draw((TextureRegion) attackAnimation.getKeyFrame(stateTime, false), getX() - 124, getY());
        }

        if (currentState == State.STAY) {
            batch.draw((TextureRegion) stayAnimation.getKeyFrame(stateTime, false), getX() - 124, getY());
        }

        if (currentState == State.RUN) {
            batch.draw((TextureRegion) runAnimation.getKeyFrame(stateTime, true), getX() - 124, getY());
        }

        if (currentState == State.DIE) {
            batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, false), getX() - 124, getY());
        }

        if (isDrawHealthBar)
            drawHealthBar(batch, -84, getHeight());
    }


    @Override
    public void setHealth(float value) {
        super.setHealth(value);
//        if (health <= 0) {
//            stateTime = 0;
//            currentState = State.DIE;
//        }
    }

    @Override
    public void attack() {
        if (currentState != State.ATTACK) {        // проверяем, в состоянии ли "атаки" юнит
            currentState = State.ATTACK;
            stateTime = 0;
            isAttack = true;
        }
    }

    @Override
    public State getCurrentState() {
        return currentState;
    }


}

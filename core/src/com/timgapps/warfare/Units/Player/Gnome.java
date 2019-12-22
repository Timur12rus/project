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
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

import static com.badlogic.gdx.math.Vector2.len;

public class Gnome extends PlayerUnit {

    private boolean isAttack = false;   // флаг, указывет на то, в состоянии ли атаки находится юнит

    private final float VELOCITY = 0.8f;
    //    public State currentState = State.RUN;
    private float stateTime;

    private World world;
    private Body body;
    private float x, y;
    private boolean isHaveTarget = false;
    private GameUnit targetEnemy;
    private float minDistance = 0; // расстояние до ближайшего вражеского юнита
    private float distanceToVerticalMovement = 300;     // минимальное расстояние до врага, чтобы изменить направление движения игрока по вертикали

    private boolean isHaveVerticalDirection = false;

    private Direction verticalDirectionMovement = Direction.NONE;
    private static float APPEARANCE_TIME = 15;

    public Gnome(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        this.level = level;
        this.world = level.getWorld();
        createAnimations();     // создадим анимации для различных состояний персонажа
        createBody(x, y);
        currentState = State.RUN;
        level.addChild(this, x, y);

    }

    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }

    @Override
    public void setHealth(float health) {
        this.health -= health;
    }

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


    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        if (level.getState() == Level.PLAY) {
        stateTime += Gdx.graphics.getDeltaTime();
//        }
//        batch.setColor(1, 1, 1, 1);

//        if (isDraw) {
        if (currentState == State.WALKING) {
            batch.draw((TextureRegion) walkAnimation.getKeyFrame(stateTime, true), getX() - 58, getY() - 26);
        }

        if (currentState == State.ATTACK) {
            batch.draw((TextureRegion) attackAnimation.getKeyFrame(stateTime, false), getX() - 104, getY() - 26);
        }

        if (currentState == State.STAY) {
            batch.draw((TextureRegion) stayAnimation.getKeyFrame(stateTime, false), getX() - 104, getY() - 26);
        }

        if (currentState == State.RUN) {
            batch.draw((TextureRegion) runAnimation.getKeyFrame(stateTime, true), getX() - 104, getY() - 26);
        }

        if (currentState == State.DIE) {
            batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, false), getX() - 104, getY() - 26);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!body.isActive()) {
            world.destroyBody(body);
            this.remove();
//            level.removeEnemyUnitFromArray(this);
        }

        if (!isHaveTarget) {    //если нет "врага-цели", то
            findTarget();       //найдем "врага-цель"
////            verticalDirectionMovement = calculateVerticalDirection();
//            currentState = State.RUN;
        }

        if (isHaveTarget) {
            Vector2 line = new Vector2();
            line = targetEnemy.getBodyPosition().sub(body.getPosition());
            if (!isHaveVerticalDirection) {
                if (len(line.x, line.y) * Level.WORLD_SCALE < distanceToVerticalMovement) {
                    verticalDirectionMovement = calculateVerticalDirection();
                }
            }
        }

        if (targetEnemy != null) {
            if (targetEnemy.getHealth() <= 0 || targetEnemy == null) {
//            currentState = State.RUN;
                resetTarget();
//            findTarget();
//            level.getArrayEnemies().remove()
            }
        }

        if (currentState == State.RUN) {
//        if (currentState != State.ATTACK) {
            if (isHaveTarget) {  // если определен "враг-цель", то
                moveToTarget();     //движемся к цели
            } else {                  // в противном случае, если "враг-цель" не определен, то двигаемся прямо вправо
                moveRight(body);    // движемся вправо
            }
        }

        if (currentState == State.ATTACK) {
            stay();
            if (attackAnimation.isAnimationFinished(stateTime)) {
                stateTime = 0;
//                System.out.println("attackAnimationFinished!");
                inflictDamage(targetEnemy, damage);
                currentState = State.STAY;

            }
//            System.out.println("currentState = " + currentState);
        }

        if (currentState == State.STAY && stayAnimation.isAnimationFinished(stateTime)) {
            if (isAttack)
                currentState = State.ATTACK;
            else
                currentState = State.RUN;
            stateTime = 0;
        }

        if (currentState == State.STAY) {
            stay();
        }


        if (setToDestroyBody) {
            body.setActive(false);
        }

        if (health <= 0) {
            setToDestroyBody = true;
        }

        /** обновим позицию текущего игрового объекта **/
        setPosition(body.getPosition().x * Level.WORLD_SCALE - 18, body.getPosition().y * Level.WORLD_SCALE);
    }

    private void stay() {
        body.setLinearVelocity(0, 0);
    }

    private Direction calculateVerticalDirection() {
        float posY = body.getPosition().y;
        float posYTarget = targetEnemy.getBodyPosition().y;


//        System.out.println("calculate Vertical Direction");
        if (posY < posYTarget) verticalDirectionMovement = Direction.UP;
        if (posY > posYTarget) verticalDirectionMovement = Direction.DOWN;
        if (posY == posYTarget) verticalDirectionMovement = Direction.NONE;

        isHaveVerticalDirection = true;

        return verticalDirectionMovement;
    }

    private void findTarget() {
//        System.out.println("find TARGET!");
        ArrayList<EnemyUnit> enemies = level.getArrayEnemies();
        ArrayList<EnemyUnit> targetEnemies = new ArrayList<EnemyUnit>();

        // найдем "врага-цель"

        try {
            for (int i = 0; i < enemies.size(); i++) {
                // расстояние от врага до текущего юнита
                float distanceToEnemy = (enemies.get(i).getBodyPosition().x - getBodyPosition().x) * Level.WORLD_SCALE;

                // расстояние по прямой от вражеского юнита до текущего юнита
                Vector2 line = new Vector2(body.getPosition().sub(enemies.get(i).getBodyPosition()));

                // сделаем проверку условия
                if (((Math.abs(line.x)) * Level.WORLD_SCALE - 30) * VELOCITY > (Math.abs(line.y)) * Level.WORLD_SCALE * VELOCITY && (distanceToEnemy > 10)) {
                    targetEnemies.add(enemies.get(i));
                }
            }


            minDistance = (targetEnemies.get(0).getBodyPosition().x - getBodyPosition().x) * Level.WORLD_SCALE;
            targetEnemy = targetEnemies.get(0);

//        minDistance = (targetEnemies.get(0).getBodyPosition().x - getBodyPosition().x) * Level.WORLD_SCALE;
            for (int i = 1; i < targetEnemies.size(); i++) {
                float distanceToEnemy = (targetEnemies.get(i).getBodyPosition().x - getBodyPosition().x) * Level.WORLD_SCALE;
                if (distanceToEnemy < minDistance) {
                    minDistance = distanceToEnemy;
                    targetEnemy = targetEnemies.get(i);
                    System.out.println("targetEnemy = " + targetEnemy.toString());
                }
            }

            if (targetEnemy != null) {
                isHaveTarget = true;        // изменим флаг на true, т.е. есть "враг-цель"
//                verticalDirectionMovement = calculateVerticalDirection();
                currentState = State.RUN;
            }
            minDistance = 0;
        } catch (Exception e) {
            currentState = State.RUN;
            verticalDirectionMovement = Direction.NONE;
        }
    }


    private void moveToTarget() {
//
        float posY = body.getPosition().y;
        float posYTarget = targetEnemy.getBodyPosition().y;

        // опрледелим направление вертикального перемещения
        if (verticalDirectionMovement == Direction.DOWN) {
            if (posY > posYTarget) {
                body.setLinearVelocity(VELOCITY, -VELOCITY);
            } else {
                verticalDirectionMovement = Direction.NONE;
            }
        } else if (verticalDirectionMovement == Direction.UP) {
            if (posY < posYTarget) {
                body.setLinearVelocity(VELOCITY, VELOCITY);
            } else {
                verticalDirectionMovement = Direction.NONE;
            }
        } else if (verticalDirectionMovement == Direction.NONE) {
            body.setLinearVelocity(VELOCITY, 0);
        }
    }

    public void moveRight(Body body) {
        Vector2 vel = body.getLinearVelocity();
        vel.x = VELOCITY;
        vel.y = 0;
        body.setLinearVelocity(vel);
    }

    private void createAnimations() {
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
//        attackAnimation = new Animation(0.12f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию стоянки персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeStay" + i)));
        for (int i = 4; i < 1; i--)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeStay" + i)));
        stayAnimation = new Animation(0.25f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию бега персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeRun" + i)));
        runAnimation = new Animation(0.18f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию уничтожения персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeDie" + i)));
        dieAnimation = new Animation(0.1f, frames);
        frames.clear();
        stateTime = 0;
    }

    public void resetTarget() {
        targetEnemy = null;
        isHaveTarget = false;
        isAttack = false;
        isHaveVerticalDirection = false;
    }

    public static float getAppearanceTime() {
        return APPEARANCE_TIME;
    }
}

package com.timgapps.warfare.Units.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnit;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class Gnome extends GameUnit {

    public enum State {WALKING, ATTACK, STAY, DIE, RUN}

    private final float VELOCITY = 1f;
    public State currentState = State.RUN;
    private float stateTime;

    private Animation walkAnimation;            // анимация для ходьбы
    private Animation attackAnimation;          // анимация для атаки
    private Animation dieAnimation;             // анимация для уничтожения
    private Animation stayAnimation;            // анимация для стоит
    private Animation runAnimation;            // анимация для бежит

    private World world;
    private Body body;
    private float x, y;
    private boolean isHaveTarget = false;
    private GameUnit targetEnemy;
    private float minDistance = 0; // расстояние до ближайшего вражеского юнита

    public Gnome(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        this.level = level;
        this.world = level.getWorld();
        createAnimations();     // создадим анимации для различных состояний персонажа
        level.addChild(this, x, y);
        createBody(x, y);
        body.setLinearVelocity(1, 0);
    }


    @Override
    public Vector2 getBodyPosition() {
        return body.getPosition();
    }


    @Override
    public void createBody(float x, float y) {

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(38 / Level.WORLD_SCALE);

        fDef.shape = shape;
        fDef.density = 1;
        fDef.restitution = 0.1f;
        body.createFixture(fDef).setUserData(this);
        shape.dispose();

        body.setTransform((x + 16) / Level.WORLD_SCALE, y / Level.WORLD_SCALE, 0);
    }


    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        if (level.getState() == Level.PLAY) {
        stateTime += Gdx.graphics.getDeltaTime();
//        }
//        batch.setColor(1, 1, 1, 1);

//        if (isDraw) {
        if (currentState == State.WALKING) {
            batch.draw((TextureRegion) walkAnimation.getKeyFrame(stateTime, true), getX() - 48, getY() - 46);
        }

        if (currentState == State.ATTACK) {
            batch.draw((TextureRegion) attackAnimation.getKeyFrame(stateTime, true), getX() - 48, getY() - 46);
        }

        if (currentState == State.STAY) {
            batch.draw((TextureRegion) stayAnimation.getKeyFrame(stateTime, true), getX() - 48, getY() - 46);
        }

        if (currentState == State.RUN) {
            batch.draw((TextureRegion) runAnimation.getKeyFrame(stateTime, true), getX() - 48, getY() - 46);
        }

        if (currentState == State.DIE) {
            batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, false), getX() - 48, getY() - 46);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!isHaveTarget) {
            ArrayList<GameUnit> enemies = level.getArrayEnemies();
            minDistance = enemies.get(0).getBodyPosition().x * Level.WORLD_SCALE;
            targetEnemy = enemies.get(0);
            // найдем "врага-цель"
            for (int i = 1; i < enemies.size(); i++) {
                if ((enemies.get(i).getBodyPosition().x - getBodyPosition().x) * Level.WORLD_SCALE < minDistance) {
                    minDistance = (enemies.get(i).getBodyPosition().x - getBodyPosition().x) * Level.WORLD_SCALE;
                    targetEnemy = enemies.get(i);   // определили "врага-цель"
                }
            }
            if (targetEnemy != null)
                isHaveTarget = true;        // изменим флаг на true, т.е. есть "враг-цель"
        }

        // если определен "враг-цель", то
        if (isHaveTarget) {
            Vector2 pos = new Vector2(body.getPosition());
            Vector2 enemyPos = targetEnemy.getBodyPosition();
            Vector2 vel = enemyPos.sub(pos);
            body.setLinearVelocity(vel.x - 0.5f, vel.y);
        }
        else {                  // в противном случае, если "враг-цель" не определен, то двигаемся прямо вправо
            moveRight(body);
        }
        setPosition(body.getPosition().x * Level.WORLD_SCALE - 18, body.getPosition().y * Level.WORLD_SCALE);
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
        attackAnimation = new Animation(0.12f, frames);
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
        runAnimation = new Animation(0.18f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию уничтожения персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("gnomeDie" + i)));
        dieAnimation = new Animation(0.1f, frames);
        frames.clear();
    }


}

package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Effects.Explosion;
import com.timgapps.warfare.Units.GameUnits.Player.PlayerUnit;
import com.timgapps.warfare.Warfare;

public class Goblin1 extends EnemyUnit {
    private ParticleEffect bloodSpray;
    protected boolean isAttack = false;         // флаг - атакует ли в данный момент юнит, в состоянии ли атаки
    protected boolean isDamaged = false;        // флаг - нанесен ли урон юниту
    protected PlayerUnit targetPlayer;          // юнит игрока - "ЦЕЛЕВОЙ ЮНИТ" или "ЮНИТ-ЦЕЛЬ"
    protected final float VELOCITY = -0.6f;
//    private Explosion explosion;
    private boolean isStartExplosion;

    public Goblin1(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        createAnimations();                   // создадим анимацию для различных состояний юнита

        xPosDamageLabel = 16;       // позиция надписи "цифры" получаемого урона
        level.addChild(this, x, y);

//        explosion = new Explosion();
//        level.addChild(explosion);

        // зададим размер для актера
        this.setSize(Warfare.atlas.findRegion("skeleton1Stay0").getRegionWidth(),
                Warfare.atlas.findRegion("skeleton1Stay0").getRegionHeight());

        // создадим партикл-эффект для брызг крови
        bloodSpray = new ParticleEffect();
        bloodSpray.load(Gdx.files.internal("effects/bloodSpray.paty"), Gdx.files.internal("effects/")); //file);

        stateTime = 0;
        currentState = State.RUN;
        level.arrayActors.add(this);        // добавляем в массив актеров текущего актера
        deltaX = 52;                        // смещение изображения относительно тела по оси Х
    }

//    private void startExplosion() {
//        explosion.setPosition(getX(), getY());
//        explosion.start();
//    }


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


        if (health <= 0 && body.isActive()) {
            currentState = State.DIE;
            stateTime = 0;
            body.setActive(false);
        }

        if (currentState == State.RUN)
            moveLeft(body);

        /** если юнит имеет цель игрока и взрыв не запущен и в состоянии атаки (т.е. произошла коллизия), то запускаем взрыв**/
        if (isAttack && targetPlayer != null && !isStartExplosion) {
            isStartExplosion = true;
//            startExplosion();
            setHealth(200);
            inflictDamage(targetPlayer, 50);
        }

        /** если состояние = State.DIE и анимация завершена, то уничтожаем юнита **/
//        if (currentState == State.DIE && dieAnimation.isAnimationFinished(stateTime) && !isStartExplosion) {
//            setToDestroy();
//        }

        if (currentState == State.DIE && dieAnimation.isAnimationFinished(stateTime)) {
            setToDestroy();
        }


//        if (currentState == State.DIE && dieAnimation.isAnimationFinished(stateTime) && explosion.isEnd()) {
////            explosion.remove();
//            setToDestroy();
//        }
    }

    protected void createAnimations() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // получим кадры и добавим в анимацию бега персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("goblin1Run" + i)));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion("skeleton1Walk0")));

        runAnimation = new Animation(0.15f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию гибели персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("goblin1Die" + i)));
        dieAnimation = new Animation(0.12f, frames);
        frames.clear();
    }


    @Override
    public boolean remove() {
        bloodSpray.dispose();
        level.removeEnemyUnitFromArray(this);
        return super.remove();
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        /** здесь deltaX - расстояние в пикселях, на сколько сдвигаем изображение относительно позиции актера **/
        if (isDraw()) {
            if (currentState == State.RUN) {
                batch.draw((TextureRegion) runAnimation.getKeyFrame(stateTime, true), getX() - deltaX, getY());
            }

            if (currentState == State.DIE) {
                batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, false), getX() - deltaX / 2, getY());
//                batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, false), getX() - deltaX, getY());
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
     * метод для атаки башни
     **/
    public void attackTower() {
        if (currentState == State.RUN) {
            if (!isAttackTower) {
                isAttackTower = true;
                // запускаем анимацию взрыва
//                startExplosion();

                // наносим урон текущему юниту
                setHealth(100);
                // наносим урон башне
                level.getSiegeTower().setHealth(10);
            }
        }
    }


    // метод для атаки
    @Override
    public void attack() {
        if (!isAttack) {
            isAttack = true;
            stateTime = 0;
            currentState = State.DIE;
        }
    }


    public void moveLeft(Body body) {
        Vector2 vel = body.getLinearVelocity();
        vel.x = VELOCITY;
        body.setLinearVelocity(vel);
    }

    /**
     * метод для назначения цели - "игрового-юнита цели"
     */
    @Override
    public void setTargetPlayer(PlayerUnit targetPlayer) {
        System.out.println("setTargetPlayer = " + targetPlayer.toString());
        this.targetPlayer = targetPlayer;
    }


    @Override
    public String toString() {
        return "Goblin1";
    }
}

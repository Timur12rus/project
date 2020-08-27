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
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Stone;
import com.timgapps.warfare.Warfare;

public class Goblin1 extends EnemyUnit {
    private ParticleEffect bloodSpray;
    protected boolean isAttack = false;         // флаг - атакует ли в данный момент юнит, в состоянии ли атаки
    protected boolean isDamaged = false;        // флаг - нанесен ли урон юниту
    //    protected PlayerUnit targetPlayer;          // юнит игрока - "ЦЕЛЕВОЙ ЮНИТ" или "ЮНИТ-ЦЕЛЬ"
    protected final float VELOCITY = -0.6f;
    private Explosion explosion;
    private boolean isStartExplosion = false;
//    private boolean isHaveTargetPlayer;

    public Goblin1(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        // создадим партикл-эффект для брызг крови
        bloodSpray = new ParticleEffect();
        bloodSpray.load(Gdx.files.internal("effects/bloodSpray.paty"), Gdx.files.internal("effects/")); //file);

        createAnimations();                   // создадим анимацию для различных состояний юнита

        xPosDamageLabel = 16;       // позиция надписи "цифры" получаемого урона
        level.addChild(this, x, y);

        explosion = new Explosion();
        level.addChild(explosion);

        // зададим размер для актера
        this.setSize(Warfare.atlas.findRegion("goblin1Run0").getRegionWidth(),
                Warfare.atlas.findRegion("goblin1Run0").getRegionHeight());


        stateTime = 0;
        currentState = State.RUN;
        level.arrayActors.add(this);        // добавляем в массив актеров текущего актера
        deltaX = 52;                        // смещение изображения относительно тела по оси Х
    }

//    @Override
//    public void setHealth(float value) {
//        if (!isDrawHealthBar) {
//            if (health > 0) isDrawHealthBar = true;
//        }
//        health -= value;
//        if (health <= 0) {
//            health = 0;
//            isDrawHealthBar = false;
//            if (body.isActive()) {
//                stateTime = 0;
//                currentState = State.DIE;
//                body.setActive(false);
//            }
//        }
//    }


    private void startExplosion() {
        explosion.setPosition(getX() - 72, getY() + 16);
        isStartExplosion = true;
        explosion.start();
        isAttack = false;
        isAttackTower = false;

    }


    @Override
    public void act(float delta) {
        super.act(delta);

        if (!isStartExplosion && isAttack) {
            startExplosion();
            if (targetPlayer != null) {
                inflictDamage(targetPlayer, 100);
                setHealth(200);
            }
        }

        if (!isStartExplosion && isAttackTower) {
            startExplosion();
            if (level.getSiegeTower() != null) {
                level.getSiegeTower().setHealth(100);
                setHealth(200);
            }
        }

        if (!isStartExplosion && isAttackStone) {
            startExplosion();
            System.out.println("Stone = " + stone);
            if (stone != null) {
                stone.setHealth(100);
                setHealth(200);
            }
        }

        if (isAttack || isAttackTower) {
            stay();
        }

//        if (targetPlayer != null && isHaveTargetPlayer == true && !isStartExplosion) {
//            startExplosion();
//
//            targetPlayer.setHealth(200);
//            inflictDamage(targetPlayer, 200);
//            setHealth(200);
//        }

//        if (currentState == State.DIE && !isStartExplosion) {
//            startExplosion();
//            stateTime = 0;
////            setHealth(200);
////            inflictDamage(targetPlayer, 100);
//        }

        if (currentState == State.RUN)
            moveLeft(body);

//        if (health <= 0 && body.isActive()) {
//            body.setActive(false);
//            stateTime = 0;
//            currentState = State.DIE;
//
//            if (!isStartExplosion) {
//                startExplosion();
//            }
//        }

        /** если юниту нанесен урон: isDamaged = true, обновляем анимацию брызг крови **/
        if (isDamaged) {
            bloodSpray.setPosition(getX() + 30, getY() + 60);
            bloodSpray.update(delta);
        }
        /** завершим анимацию брызг крови **/
        if (isDamaged && bloodSpray.isComplete())
            isDamaged = false;

        /** если юнит имеет цель игрока и взрыв не запущен и в состоянии атаки (т.е. произошла коллизия), то запускаем взрыв**/
//        if (isAttack && targetPlayer != null && !isStartExplosion) {
//            setHealth(200);
//            inflictDamage(targetPlayer, 50);
//            isAttack = false;
//        }

        if (!isAttack && health <= 0 && !isStartExplosion) {
            startExplosion();
        } else {
            if (!isAttackTower && health <= 0 && !isStartExplosion)
                startExplosion();
        }


        /** если состояние = State.DIE и анимация завершена, то уничтожаем юнита **/
        if (currentState == State.DIE && dieAnimation.isAnimationFinished(stateTime) && !isStartExplosion) {
            setToDestroy();
        }

//        if (currentState == State.DIE && dieAnimation.isAnimationFinished(stateTime)) {
//            setToDestroy();
//        }

        if (currentState == State.DIE && dieAnimation.isAnimationFinished(stateTime) && explosion.isEnd()) {
            explosion.remove();
            setToDestroy();
        }
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
        return super.remove();
    }

    protected void stay() {
        body.setLinearVelocity(0, 0);
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
            }

            if (isDrawHealthBar)
                drawHealthBar(batch, 4, getHeight() + 6);
        } else {
            setDraw(true);
        }
        if (isDamaged)
            bloodSpray.draw(batch);
    }

    @Override
    public void setAttackStone(Stone stone) {
//        super.setAttackStone(stone);

        if (!isAttackStone) {
            this.stone = stone;
            isAttackStone = true;
        }
    }

    /**
     * метод для атаки башни
     **/
    public void attackTower() {
        if (!isAttackTower) {
            isAttackTower = true;
        }
//            if (!isStartExplosion) {
//                startExplosion();
//                level.getSiegeTower().setHealth(10);
//                stateTime = 0;
//                currentState = State.DIE;
//            }
    }

//    @Override
//    public void setHealth(float value) {
//        super.setHealth(value);
////        if (health <= 0) {
////            if (!isStartExplosion) {
////                startExplosion();
////            }
////        }
//    }

    // метод для атаки
    @Override
    public void attack() {

        if (!isAttack) {
            isAttack = true;
        }

//        if (targetPlayer != null && isHaveTargetPlayer == true && !isStartExplosion) {
//            if (!isAttack) {
//                isAttack = true;
//                inflictDamage(targetPlayer, 200);
//                setHealth(200);
//            }
//        if (!isStartExplosion) {
//            startExplosion();
////            inflictDamage(targetPlayer, 200);
//        }
//            targetPlayer.setHealth(200);
//            inflictDamage(targetPlayer, 200);
//            setHealth(200);
//        }

//        if (!isAttack) {
//            isAttack = true;
//            inflictDamage(targetPlayer, 200);
//            setHealth(200);
//        }
    }


    public void moveLeft(Body body) {
        Vector2 vel = body.getLinearVelocity();
        vel.x = VELOCITY;
        body.setLinearVelocity(vel);
    }

    /**
     * метод для назначения цели - "игрового-юнита цели"
     */
//    @Override
//    public void setTargetPlayer(PlayerUnit targetPlayer) {
//            super.setTargetPlayer(targetPlayer);
//            if (currentState != State.DIE) {
//                stateTime = 0;
//                currentState = State.DIE;
//                stay();
//            }
//        }
    @Override
    public String toString() {
        return "Goblin1";
    }
}

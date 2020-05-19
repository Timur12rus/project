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
import com.timgapps.warfare.Units.GameUnits.Player.PlayerUnit;
import com.timgapps.warfare.Warfare;

public class Skeleton3 extends EnemyUnit {
    private ParticleEffect bloodSpray;
    //    protected boolean isAttack = false;         // флаг - атакует ли в данный момент юнит, в состоянии ли атаки
    protected boolean isDamaged = false;        // флаг - нанесен ли урон юниту
    //    protected PlayerUnit targetPlayer;          // юнит игрока - "ЦЕЛЕВОЙ ЮНИТ" или "ЮНИТ-ЦЕЛЬ"
//    protected final float VELOCITY = -0.0f;
//    protected final float RUN_VELOCITY = -0.0f;

    protected final float VELOCITY = -0.3f;
    protected final float RUN_VELOCITY = -0.6f;

    public Skeleton3(Level level, float x, float y, float health, float damage) {
        super(level, x, y, health, damage);
        xPosDamageLabel = 16;       // позиция надписи "цифры" получаемого урона

        createAnimations();                   // создадим анимацию для различных состояний юнита
        level.addChild(this, x, y);

        // зададим размер для актера
        this.setSize(Warfare.atlas.findRegion("skeleton3Stay0").getRegionWidth(),
                Warfare.atlas.findRegion("skeleton3Stay0").getRegionHeight());

        // создадим партикл-эффект для брызг крови
        bloodSpray = new ParticleEffect();
        bloodSpray.load(Gdx.files.internal("effects/bloodSpray.paty"), Gdx.files.internal("effects/")); //file);

        stateTime = 0;
//        currentState = State.WALKING;
        currentState = State.RUN;
        level.arrayActors.add(this);        // добавляем в массив актеров текущего актера
        deltaX = 112;                        // смещение изображения относительно тела по оси Х
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

        String name;
        if (targetPlayer == null) {
            name = "NULL";
        } else
            name = targetPlayer.toString();

//        System.out.println("CurrentState = " + currentState + "/ isAttack = " + isAttack + "/ isAttackTower = "
//                + isAttackTower + "/ isAttackStone = " + isAttackStone + "targetPlayer = " + name);

        /** если текущее состояние = RUN, юнит бежит влево **/
        if (currentState == State.RUN && !isAttack) {
            runLeft(body);
        }

        /** проверим юнита в текущем состоянии = State.ATTACK **/
        if (currentState == State.ATTACK) {
            /** если анимация завершилась **/
            if (attackAnimation.isAnimationFinished(stateTime)) {

                // проверяем, если юнит не атакует игрока, то проверяем
                if (!isAttack) {
                    // проверяем, если юнит атакует камень:
                    if (isAttackStone) {
                        stone.setHealth(damage);
                        /** проверим уровень здоровья у камня **/
                        if (stone.getHealth() <= 0) {
                            isAttackStone = false;
                            stateTime = 0;
                            currentState = State.STAY;
                        } else {
                            // если камень не уничтожен устанавливаем состояние "STAY"
                            stateTime = 0;
                            currentState = State.ATTACK;
                        }
                    } else
                        // если юнит атакует ОСАДНУЮ БАШНЮ
                        if (isAttackTower) {
                            System.out.println("ATTACK TOWER!!!");
                            if (level.getSiegeTower() != null) {
                                System.out.println("Attack tower!");
                                level.getSiegeTower().setHealth(damage);
                                stateTime = 0;
                                currentState = State.STAY;
                                if (level.getSiegeTower().getHealth() <= 0) {
                                    isAttackTower = false;
                                }
                            }
                        }
                }
                // если атакует игрока, то наносим урон
                if (isAttack) {
                    inflictDamage(targetPlayer, damage);        // метод для нанесения урона ВРАГУ
                    stateTime = 0;
                    currentState = State.STAY;
                    isAttackTower = false;
                    isAttackStone = false;
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
            if (stayAnimation.isAnimationFinished(stateTime)) {
                if (isAttackStone || isAttack || isAttackTower) {
                    stateTime = 0;
                    currentState = State.ATTACK;
                } else {
                    stateTime = 0;
                    currentState = State.WALKING;
                }
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

        /** проверим, если юнит в состоянии STAY или ATTACK, то установим для него скорость = (0;0) **/
        if (currentState == State.STAY || currentState == State.ATTACK) {
            stay();
        }

        if (targetPlayer != null && targetPlayer.getHealth() <= 0) {
            resetTarget();
        }
    }

    // метод для атаки
    @Override
    public void attack() {
        if (isAttackTower) {
            isAttackTower = false;
        }
        if (!isAttack) {
            isAttack = true;
            stateTime = 0;
            currentState = State.ATTACK;
        }
    }

    /**
     * метод сбрасывает "ЦЕЛЬ-ИГРОКА" для текущего юнита
     */
    private void resetTarget() {
        isAttack = false;
        targetPlayer = null;
    }

    protected void createAnimations() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // получим кадры и добавим в анимацию ходьбы персонажа
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("skeleton3Walk" + i)));
//            frames.add(new TextureRegion(Warfare.atlas.findRegion("skeleton3Walk" + i)));

        walkAnimation = new Animation(0.16f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("skeleton3Run" + i)));
        runAnimation = new Animation(0.14f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию атаки персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("skeleton3Attack" + i)));
        attackAnimation = new Animation(0.12f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию гибели персонажа
        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion("skeleton3Die" + i)));
        dieAnimation = new Animation(0.12f, frames);
        frames.clear();

        //  получим кадры и добавим в анимацию стоянки персонажа
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++)
                frames.add(new TextureRegion(Warfare.atlas.findRegion("skeleton3Stay" + i)));
        }
        stayAnimation = new Animation(0.1f, frames);
        frames.clear();
    }

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
     * метод останавливает "ТЕЛО" юнита, устанавливает ему скорость (0;0)
     */
    protected void stay() {
        body.setLinearVelocity(0, 0);
    }

    public void moveLeft(Body body) {
        Vector2 vel = body.getLinearVelocity();
        vel.x = VELOCITY;
        body.setLinearVelocity(vel);
    }

    public void runLeft(Body body) {
        Vector2 vel = body.getLinearVelocity();
        vel.x = RUN_VELOCITY;
        body.setLinearVelocity(vel);
    }

    @Override
    public void setHealth(float damage) {
        super.setHealth(damage);
        isDamaged = true;
        bloodSpray.start();
        if (health <= 0 && body.isActive()) {
            body.setActive(false);
            level.removeEnemyUnitFromArray(this);
            stateTime = 0;
            currentState = State.DIE;
        }
    }

    @Override
    public boolean remove() {
        bloodSpray.dispose();
        System.out.println("Remove");
        return super.remove();
    }
}

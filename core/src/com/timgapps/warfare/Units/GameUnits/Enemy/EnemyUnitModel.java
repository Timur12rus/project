package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;

public class EnemyUnitModel extends GameUnitModel {
    private float damage;
    private EnemyUnitData enemyUnitData;
    private String name;
    //    private boolean isDamaged;
    private boolean isRemovedFromEnemiesArray;
    private ParticleEffect bloodSpray;
    private boolean isMove;
    private boolean isTouchedPlayer;
    private boolean isTouchedTower;
    private boolean isAttackTower;

    public EnemyUnitModel(Level level, Vector2 position, EnemyUnitData enemyUnitData) {
        super(level, position);
        this.enemyUnitData = enemyUnitData;
        damage = enemyUnitData.getDamage();
        health = enemyUnitData.getHealth();
        speed = enemyUnitData.getSpeed();
        name = enemyUnitData.getName();
        xPosDamageLabel = 8;        // смещение надписи получаемого урона по оси х
        bodyWidth = 48;
        bodyHeight = 24;
        body = createBody();
        bloodSpray = new ParticleEffect();
        bloodSpray.load(Gdx.files.internal("effects/bloodSpray.paty"), Gdx.files.internal("effects/")); //file);
        isMove = true;
        isTouchedPlayer = false;
        isTouchedTower = false;
        isAttackTower = false;
        unitBit = ENEMY_BIT;
    }

    @Override
    protected Rectangle createBody() {
        Rectangle body = new Rectangle(position.x, position.y, bodyWidth, bodyHeight);
        return body;
    }

    public float getHealth() {
        return health;
    }

    public Vector2 getBodySize() {
        return new Vector2(bodyWidth, bodyHeight);
    }

    @Override
    public void subHealth(float damage) {
        super.subHealth(damage);
        isDamaged = true;
        bloodSpray.start();
        if (health <= 0 && !isRemovedFromEnemiesArray) {   // если здоровье меньше или равно 0, то удаляем из массива вражеских юнитов
            isRemovedFromEnemiesArray = true;
            level.removeEnemyUnitFromArray(this);                      // текущий юнит
        }
    }

    public String getName() {
        return name;
    }

    public EnemyUnitData getUnitData() {
        return enemyUnitData;
    }

    public float getDamage() {
        return damage;
    }

    public void setIsDamaged(boolean isDamaged) {
        this.isDamaged = isDamaged;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public ParticleEffect getBloodSpray() {
        return bloodSpray;
    }

    public boolean isTouchedPlayer() {
        return isTouchedPlayer;
    }

    public void setIsTouchedPlayer(boolean isTouchedPlayer) {
        this.isTouchedPlayer = isTouchedPlayer;
    }

    public void setIsTouchedTower(boolean isTouchedTower) {
        this.isTouchedTower = isTouchedTower;
    }

    public boolean isTouchedTower() {
        return isTouchedTower;
    }

    public void setIsAttackTower(boolean iaAttackTower) {
        this.isAttackTower = isTouchedTower;
    }

    public boolean isAttackTower() {
        return isAttackTower;
    }

    public void disposeBloodSpray() {
        bloodSpray.dispose();
    }
}
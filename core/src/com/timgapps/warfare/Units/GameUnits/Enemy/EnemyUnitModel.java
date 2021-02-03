package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Units.GameUnits.Enemy.bonus.Bonus;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;

public class EnemyUnitModel extends GameUnitModel {
    private float damage;
    private EnemyUnitData enemyUnitData;
    private String name;
    private boolean isRemovedFromEnemiesArray;
    private ParticleEffect bloodSpray;
    private boolean isMove;
    private boolean isTouchedPlayer;
    private boolean isTouchedTower;
    private boolean isAttackTower;
    private boolean isTouchedStone;
    private boolean isAttackStone;
    private boolean isShoot;        //  готов ли юнит стрелять
    private boolean isShooted;      //  выстрелил ли юнит
    private boolean isAttacked;     // атаковал ли юнит врага
    private boolean isHaveTargetPlayer;  // имеет ли юнит игрока-цель
    private boolean isMoveToTarget;
    private EnemyUnitModel targetPlayer;
    private boolean isHaveBonus;

    public EnemyUnitModel(LevelScreen levelScreen, Vector2 position, EnemyUnitData enemyUnitData, boolean isHaveBonus) {
        super(levelScreen, position);
        this.enemyUnitData = enemyUnitData;
        this.isHaveBonus = isHaveBonus;
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

    // метод задает юнита "игрока-цель"
    public void setTargetPlayer(EnemyUnitModel targetEnemy) {
        if (this.targetPlayer != null) {
            if (!this.targetPlayer.equals(targetEnemy))
                this.targetPlayer = targetEnemy;
        } else {
            this.targetPlayer = targetEnemy;
        }
    }

    public void setIsMoveToTarget(boolean isMoveToTarget) {
        this.isMoveToTarget = isMoveToTarget;
    }

    public boolean isMoveToTarget() {
        return isMoveToTarget;
    }

    public void setIsHaveTargetPlayer(boolean isHaveTargetPlayer) {
        this.isHaveTargetPlayer = isHaveTargetPlayer;
    }

    public boolean isHaveTargetPlayer() {
        return isHaveTargetPlayer;
    }

    // проверяет коснулся ли юнит врага
    public boolean isTouchedPlayer() {
        return isTouchedPlayer;
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

    // метод для получения урона от игрока
    @Override
    public void subHealth(float damage) {
        super.subHealth(damage);
        isDamaged = true;
        bloodSpray.start();
        if (health <= 0 && !isRemovedFromEnemiesArray) {   // если здоровье меньше или равно 0, то удаляем из массива вражеских юнитов
            isRemovedFromEnemiesArray = true;
            setBodyIsActive(false);                         // тело не активно
            setIsDestroyed(true);
            levelScreen.removeEnemyUnitFromArray(this);                      // текущий юнит

            if (isHaveBonus) {
                // добавляем бонус на уровень
                new Bonus(levelScreen, position);
            }
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

    public void setIsTouchedPlayer(boolean isTouchedPlayer) {
        this.isTouchedPlayer = isTouchedPlayer;
    }

    public void setIsTouchedTower(boolean isTouchedTower) {
        if (isTouchedTower && levelScreen.getSiegeTower().isMove()) {
            subHealth(100);
        }
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

    public void setIsTouchedStone(boolean isTouchedStone) {
        this.isTouchedStone = isTouchedStone;
    }

    public boolean isTouchedStone() {
        return isTouchedStone;
    }

    public void setIsAttackStone(boolean isAttackStone) {
        this.isAttackStone = isAttackStone;
    }

    public boolean isAttackStone() {
        return isAttackStone;
    }


    public void disposeBloodSpray() {
        bloodSpray.dispose();
    }

    // проверяет готов ли стрелять
    public boolean isShoot() {
        return isShoot;
    }

    // проверяет выстрелил ли юнит
    public boolean isShooted() {
        return isShooted;
    }

    // устанавливает готов ли стрелять
    public void setIsShoot(boolean isShoot) {
        this.isShoot = isShoot;
    }

    // устанавливает выстрелил ли юнит
    public void setIsShooted(boolean isShooted) {
        this.isShooted = isShooted;
    }

    // утсанавливает атаковал ли юнит врага
    public void setIsAttacked(boolean isAttacked) {
        this.isAttacked = isAttacked;
    }

    // возвращает атаковал ли юнит врага
    public boolean isAttacked() {
        return isAttacked;
    }

}

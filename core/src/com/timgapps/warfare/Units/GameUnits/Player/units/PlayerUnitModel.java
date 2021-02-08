package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;

public class PlayerUnitModel extends GameUnitModel {
    private String name;
    private float damage;
    private PlayerUnitData playerUnitData;      // данные юнита (тип, урон, здоровье
    private int energyPrice;
    private boolean isTouchedBarricade;
    private boolean isAttackBarricade;
    private boolean isTouchedEnemy;
    private boolean isHaveTargetEnemy;
    private boolean isHaveVerticalDirection;        // имеет ли вертикальное перемещение
    private boolean isShoot;         // в состоянии стрельбы
    private boolean isShooted;      // выстрелил
    private boolean isHited;      // уже атаковал врага
    private boolean isMoveToTarget;
    private boolean barricadeIsDetected;
    private EnemyUnitModel targetEnemy;
    private boolean isRemovedFromPlayersArray;

    public PlayerUnitModel(LevelScreen levelScreen, Vector2 position, PlayerUnitData playerUnitData) {
        super(levelScreen, position);
        this.playerUnitData = playerUnitData;
        System.out.println("PlayerUnitModel = " + playerUnitData.getUnitId());
        speed = playerUnitData.getSpeed();
        name = playerUnitData.getName();
        damage = playerUnitData.getDamage();
        health = playerUnitData.getHealth();
        energyPrice = playerUnitData.getEnergyPrice();
        xPosDamageLabel = playerUnitData.getDeltaX();
        bodyWidth = 48;
        bodyHeight = 24;
        body = createBody();
        unitBit = PLAYER_BIT;
    }

    // устанавливает атаковал ли уже врага
    public void setIsHited(boolean isHited) {
        this.isHited = isHited;
    }

    // возвращает атаковал ли уже врага
    public boolean isHited() {
        return isHited;
    }


    // устанавливает имеет ли юнит вертикальное перемещение
    public void setIsHaveVerticalDirection(boolean isHaveVerticalDirection) {
        this.isHaveVerticalDirection = isHaveVerticalDirection;
    }

    // возвращает имеет ли юнит вертикальное перемещение
    public boolean isHaveVerticalDirection() {
        return isHaveVerticalDirection;
    }

    public void setIsMoveToTarget(boolean isMoveToTarget) {
        this.isMoveToTarget = isMoveToTarget;
    }

    public boolean isMoveToTarget() {
        return isMoveToTarget;
    }

    public PlayerUnitData getPlayerUnitData() {
        return playerUnitData;
    }

    // возвращает значение урона, который наносит юнит
    public float getDamage() {
        return damage;
    }

    public void setIsHaveTargetEnemy(boolean isHaveTargetEnemy) {
        this.isHaveTargetEnemy = isHaveTargetEnemy;
    }

    public void setIsAttackBarricade(boolean isAttackBarricade) {
        this.isAttackBarricade = isAttackBarricade;
    }

    public boolean isAttackBarricade() {
        return isAttackBarricade;
    }

    public boolean isHaveTargetEnemy() {
        return isHaveTargetEnemy;
    }

    // метод задает юнита врага-цель
    public void setTargetEnemy(EnemyUnitModel targetEnemy) {
        if (this.targetEnemy != null) {
            if (!this.targetEnemy.equals(targetEnemy))
                this.targetEnemy = targetEnemy;
        } else {
            this.targetEnemy = targetEnemy;
        }
    }

    @Override
    public void subHealth(float damage) {
        super.subHealth(damage);
        if (health <= 0 && !isRemovedFromPlayersArray) {
            isRemovedFromPlayersArray = true;
            levelScreen.removePlayerUnitFromArray(this);
        }
    }

    // проверяет коснулся ли юнит врага
    public boolean isTouchedEnemy() {
        return isTouchedEnemy;
    }

    // проверяет готов ли стрелять
    public boolean isShoot() {
        return isShoot;
    }

    // проверяет выстрелил ли юнит
    public boolean isShooted() {
        return isShooted;
    }

    // устанавливает выстрелил ли юнит
    public void setIsShooted(boolean isShooted) {
        this.isShooted = isShooted;
    }

    // устанавливает готов ли стрелять
    public void setIsShoot(boolean isShoot) {
        this.isShoot = isShoot;
    }

    // устанавливает коснулся ли юнит врага
    public void setIsTouchedEnemy(boolean isTouchedEnemy) {
//        System.out.println("isTouchedEnemy = " + isTouchedEnemy);
        this.isTouchedEnemy = isTouchedEnemy;
    }

    public void setIsTouchedBarricade(boolean isTouchedBarricade) {
        this.isTouchedBarricade = isTouchedBarricade;
    }

    public void setBarricadeIsDetected(boolean barricadeIsDetected) {
        this.barricadeIsDetected = barricadeIsDetected;
    }

    public boolean isBarricadeDetected() {
        return barricadeIsDetected;
    }

    public boolean isTouchedBarricade() {
        return isTouchedBarricade;
    }

    public Vector2 getBodySize() {
        return new Vector2(bodyWidth, bodyHeight);
    }

    @Override
    protected Rectangle createBody() {
        Rectangle body = new Rectangle(position.x, position.y, bodyWidth, bodyHeight);
        return body;
    }

    public PlayerUnitData getUnitData() {
        return playerUnitData;
    }

    public String getName() {
        return name;
    }
}

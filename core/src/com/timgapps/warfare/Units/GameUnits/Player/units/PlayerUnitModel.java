package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.GUI.Screens.PlayerUnitData;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;

public class PlayerUnitModel extends GameUnitModel {
    //    private float speed;
    private String name;
    private float damage;
    private PlayerUnitData playerUnitData;      // данные юнита (тип, урон, здоровье
    private int energyPrice;
    private boolean isTouchedBarricade;
    private boolean isAttackBarricade;
    private boolean isTouchedEnemy;
    private boolean isHaveTargetEnemy;
    private boolean isMoveToTarget;
    private EnemyUnitModel targetEnemy;
    private boolean isRemovedFromPlayersArray;

    public PlayerUnitModel(Level level, Vector2 position, PlayerUnitData playerUnitData) {
        super(level, position);
        this.playerUnitData = playerUnitData;
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
            level.removePlayerUnitFromArray(this);
        }
    }

    // проверяет коснулся ли юнит врага
    public boolean isTouchedEnemy() {
        return isTouchedEnemy;
    }

    // устанавливает коснулся ли юнит врага
    public void setIsTouchedEnemy(boolean isTouchedEnemy) {
//        System.out.println("isTouchedEnemy = " + isTouchedEnemy);
        this.isTouchedEnemy = isTouchedEnemy;
    }

    public void setIsTouchedBarricade(boolean isTouchedBarricade) {
        this.isTouchedBarricade = isTouchedBarricade;
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

}

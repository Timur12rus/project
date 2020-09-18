package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.timgapps.warfare.Level.GUI.Screens.PlayerUnitData;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;
import com.timgapps.warfare.Units.GameUnits.UnitData;

public class PlayerUnitModel extends GameUnitModel {
    //    private float speed;
    private String name;
    private float damage;
    private PlayerUnitData playerUnitData;      // данные юнита (тип, урон, здоровье
    private int energyPrice;
    private boolean isTouchedBarricade;
    private boolean isAttackEnemy;
    private boolean isAttackBarricade;
    private boolean isTouchedEnemy;
    private boolean isHaveTargetEnemy;
    private EnemyUnitModel targetEnemy;

    public PlayerUnitModel(World world, Vector2 position, PlayerUnitData playerUnitData) {
        super(world, position);
        this.playerUnitData = playerUnitData;
        speed = playerUnitData.getSpeed();
        name = playerUnitData.getName();
        damage = playerUnitData.getDamage();
        health = playerUnitData.getHealth();
        energyPrice = playerUnitData.getEnergyPrice();
        bodyWidth = 48;
        bodyHeight = 24;
        body = createBody();
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

    // проверяет коснулся ли юнит врага
    public boolean isTouchedEnemy() {
        return isTouchedEnemy;
    }

    // устанавливает коснулся ли юнит врага
    public void setIsTouchedEnemy(boolean isTouchedEnemy) {
        System.out.println("isTouchedEnemy = " + isTouchedEnemy);
        this.isTouchedEnemy = isTouchedEnemy;
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

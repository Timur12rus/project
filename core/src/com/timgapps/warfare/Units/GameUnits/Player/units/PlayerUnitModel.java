package com.timgapps.warfare.Units.GameUnits.Player.units;

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
    protected float bodyWidth = 48;
    protected float bodyHeight = 24;
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
        body = createBody(world);
    }

    public PlayerUnitData getPlayerUnitData() {
        return playerUnitData;
    }

    // возвращает значение урона, который наносит юнит
    public float getDamage() {
        return damage;
    }

    @Override
    protected Body createBody(World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bodyWidth / 2) / Level.WORLD_SCALE, (bodyHeight / 2) / Level.WORLD_SCALE);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.filter.categoryBits = PLAYER_BIT;
        fDef.filter.maskBits = ENEMY_BIT | BARRICADE_BIT;
        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        body.setTransform(position.x / Level.WORLD_SCALE, position.y / Level.WORLD_SCALE, 0);
        return body;
    }

    public void  setIsHaveTargetEnemy(boolean isHaveTargetEnemy) {
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

    public UnitData getUnitData() {
        return playerUnitData;
    }
}

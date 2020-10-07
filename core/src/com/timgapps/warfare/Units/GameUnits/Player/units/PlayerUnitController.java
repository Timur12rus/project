package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Barricade;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitController;

public class PlayerUnitController extends GameUnitController {
    protected PlayerUnitModel model;
    protected EnemyUnitModel targetEnemy;
    protected Vector2 velocity;
    protected boolean isHaveTargetEnemy;
    protected Barricade barricade;

    public PlayerUnitController(Level level, PlayerUnitModel model) {
        super(level, model);
        this.model = model;
        this.level = level;
        barricade = level.getBarricade();
        body = model.getBody();
        velocity = new Vector2();
    }

    // для проверки столкновения
    public boolean checkCollision(Rectangle bodyA, Rectangle bodyB) {
        if (Intersector.overlaps(bodyA, bodyB)) {
            return true;
        } else {
            return false;
        }
    }

    // наносим урон вражескому юниту
    public void hit() {
        if (targetEnemy != null) {
            targetEnemy.subHealth(model.getDamage());
            System.out.println("TargetEnemy health = " + targetEnemy.getHealth());
        }
    }

    public void hitBarricade() {
        if (barricade.getHealth() > 0) {
            barricade.setHealth(model.getDamage());
        }
    }

    public void throwBullet() {
    }
}

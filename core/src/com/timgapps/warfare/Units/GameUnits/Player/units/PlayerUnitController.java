package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Barricade;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitController;

public class PlayerUnitController extends GameUnitController {
    protected PlayerUnitModel model;
    protected EnemyUnitModel targetEnemy;
    protected Vector2 velocity;
    protected boolean isHaveTargetEnemy;
    protected Barricade barricade;
    protected int num = 1;

    public PlayerUnitController(LevelScreen levelScreen, PlayerUnitModel model) {
        super(levelScreen, model);
        this.model = model;
        this.levelScreen = levelScreen;
        barricade = levelScreen.getBarricade();
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
//            int num = (int) (Math.random() * 1);
            // если врагу последний удар остался, то звук
            if (targetEnemy.getHealth() <= model.getUnitData().getDamage()) {
                Warfare.media.playSound("hit3.ogg");
                num = 1;
            } else {
                Warfare.media.playSound("hit" + num + ".ogg");
                num++;
                if (num == 4) {
                    num = 1;
                }
            }

//            Warfare.media.playSound("hitToZombie.ogg");
        }
    }

    public void hitBarricade() {
        if (barricade.getHealth() > 0) {
            barricade.setHealth(model.getDamage());
            Warfare.media.playSound("hitStone.ogg");
        }
    }

    public void throwBullet() {
    }

    public EnemyUnitModel getTargetEnemy() {
        return targetEnemy;
    }
}

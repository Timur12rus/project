package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnitController;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;

public class EnemyUnitController extends GameUnitController {
    protected EnemyUnitModel model;
    protected PlayerUnitModel targetPlayer;
    protected Vector2 velocity;


    public EnemyUnitController(Level level, EnemyUnitModel model) {
        super(level, model);
        this.model = model;
        body = model.getBody();
        velocity = new Vector2();
    }

    public void hit() {
        if (targetPlayer != null) {
            targetPlayer.subHealth(model.getDamage());
            if (targetPlayer.getHealth() <= 0) {
                targetPlayer = null;
            }
        }
    }

    public void hitTower() {
        if (level.getSiegeTower().getHealth() > 0) {
            level.getSiegeTower().setHealth(model.getDamage());
        }
    }

    public void checkCollisions() {
        if (!model.isTouchedPlayer()) {               // проверяем коллизию
            for (PlayerUnitModel playerUnit : level.getArrayPlayers()) {
                if (playerUnit.isBodyActive()) {     // если тело активно
                    model.setIsTouchedPlayer(checkCollision(body, playerUnit.getBody()));
                    if (model.isTouchedPlayer()) {
                        targetPlayer = playerUnit;
                        break;
                    } else {
                        model.setIsTouchedPlayer(false);
                    }
                }
            }
        }
    }

    public boolean checkCollision(Rectangle bodyA, Rectangle bodyB) {
        if (Intersector.overlaps(bodyA, bodyB)) {
            return true;
        } else {
            return false;
        }
    }
}

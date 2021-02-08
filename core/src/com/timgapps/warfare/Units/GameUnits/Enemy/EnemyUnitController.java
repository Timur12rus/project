package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.GameUnitController;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;

public class EnemyUnitController extends GameUnitController {
    protected EnemyUnitModel model;
    protected PlayerUnitModel targetPlayer;
    protected Vector2 velocity;

    public EnemyUnitController(LevelScreen levelScreen, EnemyUnitModel model) {
        super(levelScreen, model);
        this.model = model;
        body = model.getBody();
        velocity = new Vector2();
    }

    public void hit() {
        if (targetPlayer != null) {
            targetPlayer.subHealth(model.getDamage());
        }
    }

    public void hitTower() {
        if (levelScreen.getSiegeTower().getHealth() > 0) {
            levelScreen.getSiegeTower().subHealth(model.getDamage());
        }
    }


    public void checkCollisions() {
        if (!model.isTouchedPlayer()) {               // проверяем коллизию
            for (PlayerUnitModel playerUnit : levelScreen.getArrayPlayers()) {
                if (playerUnit.isBodyActive()) {     // если тело активно
                    model.setIsTouchedPlayer(checkCollision(body, playerUnit.getBody()));
                    if (model.isTouchedPlayer()) {
                        targetPlayer = playerUnit;
                        break;
                    } else {
                        model.setIsTouchedPlayer(false);
                        model.setIsAttack(false);   // 08.02.2021
                    }
                } else {
                    if (targetPlayer.equals(playerUnit)) {
                        targetPlayer = null;
                        model.setIsTouchedPlayer(false);
                        model.setIsAttack(false);   // 08.02.2021
                    }
                }
            }
        }   /** 08.02.2021 добавил **/
        else {
            if (targetPlayer == null || !targetPlayer.isBodyActive()) {
                model.setIsTouchedPlayer(false);
                model.setIsAttack(false);   // 08.02.2021

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

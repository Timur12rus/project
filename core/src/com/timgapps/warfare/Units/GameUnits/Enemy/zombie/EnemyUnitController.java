package com.timgapps.warfare.Units.GameUnits.Enemy.zombie;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnitController;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;

public class EnemyUnitController extends GameUnitController {
    protected PlayerUnitModel targetPlayer;
    protected EnemyUnitModel model;

    public EnemyUnitController(Level level, EnemyUnitModel model) {
        super(level, model);
        this.model = model;
        body = model.getBody();
    }

    public void checkCollisions() {
//        model.setIsTouchedPlayer(checkCollision(body, ((PlayerUnitModel) gameUnitModel).getBody()));
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

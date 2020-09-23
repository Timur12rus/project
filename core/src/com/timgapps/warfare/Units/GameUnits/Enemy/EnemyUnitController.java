package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnitController;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;

public class EnemyUnitController extends GameUnitController {
    //    private ParticleEffect bloodSpray;      // эффект брызги
    private EnemyUnitModel model;
    private PlayerUnitModel targetPlayer;

    public EnemyUnitController(Level level, EnemyUnitModel model) {
        super(level, model);
        this.model = model;
        this.level = level;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (model.getHealth() <= 0) {
            if (!model.isDestroyed()) {
                model.setIsDestroyed(true);
                model.setBodyIsActive(false);
                System.out.println("DESTROY!");
            }
        } else {
            // проверяем коллизию
            for (GameUnitModel gameUnitModel : level.getArrayModels()) {
                if (gameUnitModel.getHealth() > 0) {
                    if (gameUnitModel.getUnitBit() == GameUnitModel.PLAYER_BIT) {
                        if (checkCollision(body, gameUnitModel.getBody())) {
                            model.setIsTouchedPlayer(true);
                            if (targetPlayer == null) {
                                targetPlayer = (PlayerUnitModel) gameUnitModel;
                            }
                        } else {
                            model.setIsTouchedPlayer(false);
                            if (targetPlayer != null) {
                                targetPlayer = null;
                            }
                        }
                    }
                }
            }

            if (model.isTouchedPlayer()) {
                attackPlayer();
            } else move();
        }

        if (model.isDamaged()) {
            model.getBloodSpray().setPosition(model.getX(), model.getY());
            model.getBloodSpray().update(delta);
        }
        if (model.isDamaged() && model.getBloodSpray().isComplete()) {
            model.setIsDamaged(false);
        }
        if (model.getHealth() <= 0) {
        }
    }

    // для проверки столкновения
    public boolean checkCollision(Rectangle bodyA, Rectangle bodyB) {
        if (Intersector.overlaps(bodyA, bodyB)) {
            return true;
        } else {
            return false;
        }
    }


    public void attackPlayer() {
        if (model.isAttack()) {
            Vector2 velocity = new Vector2(0, 0);
            model.setVelocity(velocity);
        } else {
            System.out.println("attackPlayer");
            model.setIsAttack(true);
            model.setIsMove(false);
        }
    }

    public void move() {
        System.out.println("Move");
        model.setIsMove(true);
        model.getPosition().add(model.getSpeed(), 0);
    }

    @Override
    public void hit() {
        super.hit();
        System.out.println("Hit");
    }
}

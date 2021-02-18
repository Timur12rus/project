package com.timgapps.warfare.Units.GameUnits.Player.units.shooter;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Arrow;
import com.timgapps.warfare.Units.GameUnits.Player.interfacesAi.PlayerShooterAi;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitController;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.units.archer.ArcherController;

import java.util.ArrayList;

public class ShooterController extends ArcherController {
    private boolean isHaveTarget;
    private boolean isHaveVerticalDirection;
    private ArcherController.Direction verticalDirectionMovement = ArcherController.Direction.NONE;
    private final float ATTACK_DISTANCE = 300;
    private final float DISTANCE_TO_BARRICADE = 300;
    private boolean isReachedEnemyYPos;

    public ShooterController(LevelScreen levelScreen, PlayerUnitModel model) {
        super(levelScreen, model);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
}
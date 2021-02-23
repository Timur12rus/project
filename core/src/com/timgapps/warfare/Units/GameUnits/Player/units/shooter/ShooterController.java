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
    public ShooterController(LevelScreen levelScreen, PlayerUnitModel model) {
        super(levelScreen, model);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void throwBullet() {
        new Arrow(levelScreen, new Vector2(model.getX() + 64, model.getY() + 16), 2, new Vector2(10, 0));
    }
}
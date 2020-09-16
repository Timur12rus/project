package com.timgapps.warfare.Units.GameUnits.Enemy;

import com.timgapps.warfare.Units.GameUnits.GameUnitController;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;

public class EnemyUnitController extends GameUnitController {
    private EnemyUnitModel model;

    public EnemyUnitController(EnemyUnitModel model) {
        super(model);
        body.getFixtureList().get(0).setUserData(this);
    }
}

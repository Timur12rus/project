package com.timgapps.warfare.Units.GameUnits.Enemy.Zombie1Runner;

import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitView;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie2.Zombie2Controller;
import com.timgapps.warfare.Units.GameUnits.GameUnitController;
import com.timgapps.warfare.screens.level.LevelScreen;


public class Zombie1RunnerUnitView extends EnemyUnitView {
    protected Zombie1RunnerController controller;
    public Zombie1RunnerUnitView(LevelScreen levelScreen, EnemyUnitModel model, Zombie1RunnerController controller) {
        super(levelScreen, model, controller);
        this.controller = controller;
    }
}

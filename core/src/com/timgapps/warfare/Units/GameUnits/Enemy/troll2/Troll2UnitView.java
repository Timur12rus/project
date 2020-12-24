package com.timgapps.warfare.Units.GameUnits.Enemy.troll2;

import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.ork1.Ork1Controller;
import com.timgapps.warfare.Units.GameUnits.Enemy.troll1.Troll1UnitView;
import com.timgapps.warfare.screens.level.LevelScreen;

public class Troll2UnitView extends Troll1UnitView {
    public Troll2UnitView(LevelScreen levelScreen, EnemyUnitModel model, Ork1Controller controller) {
        super(levelScreen, model, controller);
        WAIT_COUNT = 2;                              // счетчик ожидания, когда юнит атаковал -> стоит и ждет
        createAnimations();
    }
}

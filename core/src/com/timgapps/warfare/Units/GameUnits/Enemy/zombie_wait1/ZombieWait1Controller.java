package com.timgapps.warfare.Units.GameUnits.Enemy.zombie_wait1;

import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie1.Zombie1Controller;
import com.timgapps.warfare.screens.level.LevelScreen;

public class ZombieWait1Controller extends Zombie1Controller {
    //    private boolean isWait = true;
    private float waitCounter = 0;
    private final float WAIT_TIME = 900;

    public ZombieWait1Controller(LevelScreen levelScreen, EnemyUnitModel model) {
        super(levelScreen, model);
        model.setIsWait(true);
    }

    @Override
    public void update(float delta) {
        if (model.isWait()) {
            if (levelScreen.getState() != LevelScreen.PAUSED) {
                waitCounter++;
            }
            if (waitCounter > WAIT_TIME || levelScreen.getArrayPlayers().size() > 0) {
//                isWait = false;
                model.setIsWait(false);
            }
        } else {
            super.update(delta);
        }
    }
}

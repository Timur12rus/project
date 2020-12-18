package com.timgapps.warfare.screens.level.timer;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Units.GameUnits.unitTypes.EnemyUnits;
import com.timgapps.warfare.screens.level.LevelScreen;

public class MonsterWave {
    private LevelScreen levelScreen;

    public MonsterWave(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
    }

    public void start() {
        levelScreen.createEnemyUnit(EnemyUnits.Zombie3, new Vector2(1150, 210));
        levelScreen.createEnemyUnit(EnemyUnits.Zombie3, new Vector2(1250, 240));
        levelScreen.createEnemyUnit(EnemyUnits.Skeleton1, new Vector2(1380, 190));
        levelScreen.createEnemyUnit(EnemyUnits.Zombie3, new Vector2(1450, 240));
        levelScreen.createEnemyUnit(EnemyUnits.Skeleton1, new Vector2(1500, 210));
        levelScreen.createEnemyUnit(EnemyUnits.Zombie3, new Vector2(1600, 230));
        levelScreen.createEnemyUnit(EnemyUnits.Goblin, new Vector2(1700, 205));
        levelScreen.createEnemyUnit(EnemyUnits.Zombie3, new Vector2(1800, 240));
        levelScreen.createEnemyUnit(EnemyUnits.Skeleton2, new Vector2(1900, 220));
        levelScreen.createEnemyUnit(EnemyUnits.Ent1, new Vector2(2000, 210));
        levelScreen.createEnemyUnit(EnemyUnits.Goblin, new Vector2(2100, 230));
        levelScreen.createEnemyUnit(EnemyUnits.Zombie3, new Vector2(2000, 210));
    }
}

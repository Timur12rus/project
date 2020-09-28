package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.skeleton.SkeletonWarriorController;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitData;
import com.timgapps.warfare.Units.GameUnits.Enemy.skeleton.SkeletonView;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitController;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Units.GameUnits.unitTypes.EnemyUnits;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;

// класс для создания игровых юнитов (юнита игрока или вражеского юнита)
public class UnitCreator {
    protected GameUnitModel model;
    //    private EnemyController controller;
    private GameUnitView view;
    private Vector2 position;
    private Level level;
    public final int PLAYER_UNIT = 0;
    public final int ENEMEY_UNIT = 1;
    public final int NONE_UNIT = 2;
    private int typeOfUnit;     // тип юнита (вражеский или юнит игрока)

    public UnitCreator(Level level) {
        this.level = level;
    }

    public void createUnit(String unitName, Vector2 position) {
        this.position = position;
        // определим вражеский юнит или юнит игрока
        for (PlayerUnits playerUnit : PlayerUnits.values()) {
            if (unitName.equals(playerUnit.name())) {
                typeOfUnit = PLAYER_UNIT;
                break;
            }
        }
        for (EnemyUnits enemyUnit : EnemyUnits.values()) {
            if (unitName.equals(enemyUnit.name())) {
                typeOfUnit = ENEMEY_UNIT;
                break;
            }
        }
        switch (typeOfUnit) {
            case PLAYER_UNIT:
                PlayerUnitModel playerUnitModel = new PlayerUnitModel(level, position, level.getGameManager().getUnitData(unitName));
                PlayerUnitController controller = new PlayerUnitController(level, playerUnitModel);
                PlayerUnitView view = new PlayerUnitView(level, playerUnitModel, controller);
                createPlayerUnit(playerUnitModel, view);
                level.addPlayerUnitToPlayerArray(playerUnitModel);
                level.addUnitModel(playerUnitModel);
                break;
            case ENEMEY_UNIT:
                // TODO нужно сделать для вражеских юнитов
                EnemyUnits unitId = EnemyUnits.None;
                for (EnemyUnits enemyUnit : EnemyUnits.values()) {
                    if (unitName.equals(enemyUnit.name())) {
                        System.out.println("unitName = " + unitName + " unitId = " + enemyUnit.name());
                        unitId = enemyUnit;
                    }
                }
                EnemyUnitModel enemyUnitModel = new EnemyUnitModel(level, position, new EnemyUnitData(unitId));
                SkeletonWarriorController skeletonWarriorController = new SkeletonWarriorController(level, enemyUnitModel);
//                EnemyUnitController enemyUnitController = new EnemyUnitController(level, enemyUnitModel);
                SkeletonView skeletonView = new SkeletonView(level, enemyUnitModel, skeletonWarriorController);
//                EnemyUnitView enemyUnitView = new EnemyUnitView(level, enemyUnitModel, (SkeletWarriorController) enemyUnitController);
                createEnemyUnit(enemyUnitModel, skeletonView);
                level.addEnemyUnitToEnemyArray(enemyUnitModel);
                level.addUnitModel(enemyUnitModel);
//                level.addEnemyUnitToEnemyArray(enemyUnitView);
                break;
        }
    }

    public void createEnemyUnit(GameUnitModel model, GameUnitView view) {
//    public void create(GameUnitModel model, GameUnitView view, GameUnitController controller) {
        this.model = model;
//        this.controller = controller;
        this.view = view;
        level.addChild(view, position.x, position.y);
    }

    //    public void create(GameUnitModel model, GameUnitView view, UnitController controller) {
    public void createPlayerUnit(GameUnitModel model, GameUnitView view) {
        this.model = model;
//        this.controller = controller;
        this.view = view;
        level.addChild(view, position.x, position.y);
    }
}

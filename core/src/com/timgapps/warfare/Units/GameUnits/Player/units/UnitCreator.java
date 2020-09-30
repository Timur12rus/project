package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitData;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie1.Zombie1Controller;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie1.Zombie1View;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie2.Zombie2Controller;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie2.Zombie2View;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie3.Zombie3Controller;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie3.Zombie3View;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Units.GameUnits.Player.units.thor.ThorController;
import com.timgapps.warfare.Units.GameUnits.Player.units.thor.ThorView;
import com.timgapps.warfare.Units.GameUnits.unitTypes.EnemyUnits;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;

// класс для создания игровых юнитов (юнита игрока или вражеского юнита)
public class UnitCreator {
    protected GameUnitModel model;
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
                ThorController thorController = new ThorController(level, playerUnitModel);
                ThorView thorView = new ThorView(level, playerUnitModel, thorController);
                createPlayerUnit(playerUnitModel, thorView);
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
                switch (unitId) {
                    case Zombie1:
                        Zombie1Controller zombie1Controller = new Zombie1Controller(level, enemyUnitModel);
                        Zombie1View zombie1View = new Zombie1View(level, enemyUnitModel, zombie1Controller);
                        createEnemyUnit(enemyUnitModel, zombie1View);
                        break;
                    case Zombie2:
                        Zombie2Controller zombie2Controller = new Zombie2Controller(level, enemyUnitModel);
                        Zombie2View zombie2View = new Zombie2View(level, enemyUnitModel, zombie2Controller);
                        createEnemyUnit(enemyUnitModel, zombie2View);
                        break;
                    case Zombie3:
                        Zombie3Controller zombie3Controller = new Zombie3Controller(level, enemyUnitModel);
                        Zombie3View zombie3View = new Zombie3View(level, enemyUnitModel, zombie3Controller);
                        createEnemyUnit(enemyUnitModel, zombie3View);
                        break;
                }
//                Zombie2Controller zombie2Controller = new Zombie2Controller(level, enemyUnitModel);
//                Zombie2View zombie2View = new Zombie2View(level, enemyUnitModel, zombie2Controller);
//                createEnemyUnit(enemyUnitModel, zombie2View);
                level.addEnemyUnitToEnemyArray(enemyUnitModel);
                level.addUnitModel(enemyUnitModel);
                break;
        }
    }

    public void createEnemyUnit(GameUnitModel model, GameUnitView view) {
        this.model = model;
        this.view = view;
        level.addChild(view, position.x, position.y);
    }

    public void createPlayerUnit(GameUnitModel model, GameUnitView view) {
        this.model = model;
        this.view = view;
        level.addChild(view, position.x, position.y);
    }
}

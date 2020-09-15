package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.GameUnitController;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Units.GameUnits.unitTypes.EnemyUnits;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;


// класс для создания игровых юнитов (юнита игрока или вражеского юнита)
public class UnitCreator {
    private GameUnitModel model;
    private GameUnitController controller;
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

    public void createUnit(String unitId, Vector2 position) {
        this.position = position;
        // определим вражеский юнит или юнит игрока
        for (PlayerUnits playerUnit : PlayerUnits.values()) {
            if (unitId.equals(playerUnit.name())) {
                typeOfUnit = PLAYER_UNIT;
                break;
            }
        }
        for (EnemyUnits enemyUnit : EnemyUnits.values()) {
            if (unitId.equals(enemyUnit.name())) {
                typeOfUnit = ENEMEY_UNIT;
                break;
            }
        }
        switch (typeOfUnit) {
            case PLAYER_UNIT:
                PlayerUnitModel model = new PlayerUnitModel(level.getWorld(), position, level.getGameManager().getUnitData(unitId));
                PlayerUnitController controller = new PlayerUnitController(model);
                PlayerUnitView view = new PlayerUnitView(level, model, controller);
                create(model, view, controller);
                break;
            case ENEMEY_UNIT:
                // TODO нужно сделать для вражеских юнитов
//                UnitModel model = new UnitModel(level.getWorld(), position, level.getGameManager().getUnitData(unitId));
//                UnitController controller = new UnitController(model);
//                UnitView view = new UnitView(level, model, controller);
//                create(model, view, controller);
                break;
        }
    }

    public void create(GameUnitModel model, GameUnitView view, GameUnitController controller) {
        this.model = model;
        this.controller = controller;
        this.view = view;
        level.addChild(view, position.x, position.y);
    }
}

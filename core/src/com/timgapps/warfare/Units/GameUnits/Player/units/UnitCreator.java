package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitData;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.ent_1.Ent1Controller;
import com.timgapps.warfare.Units.GameUnits.Enemy.ent_1.Ent1View;
import com.timgapps.warfare.Units.GameUnits.Enemy.skeleton1.Skeleton1Controller;
import com.timgapps.warfare.Units.GameUnits.Enemy.skeleton1.Skeleton1UnitView;
import com.timgapps.warfare.Units.GameUnits.Enemy.skeleton2.Skeleton2Controller;
import com.timgapps.warfare.Units.GameUnits.Enemy.skeleton2.Skeleton2UnitView;
import com.timgapps.warfare.Units.GameUnits.Enemy.wizard.WizardController;
import com.timgapps.warfare.Units.GameUnits.Enemy.wizard.WizardUnitView;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie1.Zombie1Controller;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie1.Zombie1UnitView;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie2.Zombie2Controller;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie2.Zombie2UnitView;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie3.Zombie3Controller;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie3.Zombie3UnitView;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Units.GameUnits.Player.units.archer.ArcherController;
import com.timgapps.warfare.Units.GameUnits.Player.units.archer.ArcherView;
import com.timgapps.warfare.Units.GameUnits.Player.units.barbarian.BarbarianController;
import com.timgapps.warfare.Units.GameUnits.Player.units.barbarian.BarbarianView;
import com.timgapps.warfare.Units.GameUnits.Player.units.gnome.GnomeController;
import com.timgapps.warfare.Units.GameUnits.Player.units.gnome.GnomeView;
import com.timgapps.warfare.Units.GameUnits.Player.units.knight.KnightController;
import com.timgapps.warfare.Units.GameUnits.Player.units.knight.KnightView;
import com.timgapps.warfare.Units.GameUnits.Player.units.shooter.ShooterController;
import com.timgapps.warfare.Units.GameUnits.Player.units.shooter.ShooterView;
import com.timgapps.warfare.Units.GameUnits.Player.units.thor.ThorController;
import com.timgapps.warfare.Units.GameUnits.Player.units.thor.ThorView;
import com.timgapps.warfare.Units.GameUnits.Player.units.viking.VikingController;
import com.timgapps.warfare.Units.GameUnits.Player.units.viking.VikingView;
import com.timgapps.warfare.Units.GameUnits.unitTypes.EnemyUnits;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;

// класс для создания игровых юнитов (юнита игрока или вражеского юнита)
public class UnitCreator {
    protected GameUnitModel model;
    private GameUnitView view;
    private Vector2 position;
    private LevelScreen levelScreen;
    public final int PLAYER_UNIT = 0;
    public final int ENEMEY_UNIT = 1;
    public final int NONE_UNIT = 2;
    private int typeOfUnit;     // тип юнита (вражеский или юнит игрока)

    public UnitCreator(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
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
                PlayerUnits playerUnitId = PlayerUnits.None;
                System.out.println("PLAYER UNIT NAME = " + unitName);
                for (PlayerUnits playerUnit : PlayerUnits.values()) {
                    System.out.println("PlayerUnit Value.name = " + playerUnit.name());
                    if (unitName.equals(playerUnit.name())) {
                        playerUnitId = playerUnit;
                    }
                }
                System.out.println("PLAYER UNIT ID = " + playerUnitId);
//                System.out.println("getUnitData = " +  level.getGameManager().getUnitData(playerUnitId.toString()).toString());
                PlayerUnitModel playerUnitModel = new PlayerUnitModel(levelScreen, position, levelScreen.getGameManager().getUnitData(playerUnitId));
                switch (playerUnitId) {
                    case Thor:
                        ThorController thorController = new ThorController(levelScreen, playerUnitModel);
                        ThorView thorView = new ThorView(levelScreen, playerUnitModel, thorController);
                        createPlayerUnit(playerUnitModel, thorView);
                        break;
                    case Gnome:
                        GnomeController gnomeController = new GnomeController(levelScreen, playerUnitModel);
                        GnomeView gnomeView = new GnomeView(levelScreen, playerUnitModel, gnomeController);
                        createPlayerUnit(playerUnitModel, gnomeView);
                        break;
                    case Knight:
                        KnightController knightController = new KnightController(levelScreen, playerUnitModel);
                        KnightView knightView = new KnightView(levelScreen, playerUnitModel, knightController);
                        createPlayerUnit(playerUnitModel, knightView);
                        break;
                    case Archer:
                        ArcherController archerController = new ArcherController(levelScreen, playerUnitModel);
                        ArcherView archerView = new ArcherView(levelScreen, playerUnitModel, archerController);
                        createPlayerUnit(playerUnitModel, archerView);
                        break;
                    case Shooter:
                        ShooterController shooterController = new ShooterController(levelScreen, playerUnitModel);
                        ShooterView shooterView = new ShooterView(levelScreen, playerUnitModel, shooterController);
                        createPlayerUnit(playerUnitModel, shooterView);
                        break;
                    case Barbarian:
                        BarbarianController barbarianController = new BarbarianController(levelScreen, playerUnitModel);
                        BarbarianView barbarianView = new BarbarianView(levelScreen, playerUnitModel, barbarianController);
                        createPlayerUnit(playerUnitModel, barbarianView);
                        break;
                    case Viking:
                        VikingController vikingController = new VikingController(levelScreen, playerUnitModel);
                        VikingView vikingView = new VikingView(levelScreen, playerUnitModel, vikingController);
                        createPlayerUnit(playerUnitModel, vikingView);
                        break;
                }

                levelScreen.addPlayerUnitToPlayerArray(playerUnitModel);
                levelScreen.addUnitModel(playerUnitModel);
                break;
            case ENEMEY_UNIT:
                // TODO нужно сделать для вражеских юнитов
                EnemyUnits enemyUnitId = EnemyUnits.None;
                for (EnemyUnits enemyUnit : EnemyUnits.values()) {
                    if (unitName.equals(enemyUnit.name())) {
                        System.out.println("unitName = " + unitName + " unitId = " + enemyUnit.name());
                        enemyUnitId = enemyUnit;
                    }
                }
                EnemyUnitModel enemyUnitModel = new EnemyUnitModel(levelScreen, position, new EnemyUnitData(enemyUnitId));
                switch (enemyUnitId) {
                    case Zombie1:
                        Zombie1Controller zombie1Controller = new Zombie1Controller(levelScreen, enemyUnitModel);
                        Zombie1UnitView zombie1View = new Zombie1UnitView(levelScreen, enemyUnitModel, zombie1Controller);
                        createEnemyUnit(enemyUnitModel, zombie1View);
                        break;
                    case Zombie2:
                        Zombie2Controller zombie2Controller = new Zombie2Controller(levelScreen, enemyUnitModel);
                        Zombie2UnitView zombie2View = new Zombie2UnitView(levelScreen, enemyUnitModel, zombie2Controller);
                        createEnemyUnit(enemyUnitModel, zombie2View);
                        break;
                    case Zombie3:
                        Zombie3Controller zombie3Controller = new Zombie3Controller(levelScreen, enemyUnitModel);
                        Zombie3UnitView zombie3View = new Zombie3UnitView(levelScreen, enemyUnitModel, zombie3Controller);
                        createEnemyUnit(enemyUnitModel, zombie3View);
                        break;
                    case Skeleton1:
                        Skeleton1Controller skeleton1Controller = new Skeleton1Controller(levelScreen, enemyUnitModel);
                        Skeleton1UnitView skeleton1UnitView = new Skeleton1UnitView(levelScreen, enemyUnitModel, skeleton1Controller);
                        createEnemyUnit(enemyUnitModel, skeleton1UnitView);
                        break;
                    case Skeleton2:
                        Skeleton2Controller skeleton2Controller = new Skeleton2Controller(levelScreen, enemyUnitModel);
                        Skeleton2UnitView skeleton2UnitView = new Skeleton2UnitView(levelScreen, enemyUnitModel, skeleton2Controller);
                        createEnemyUnit(enemyUnitModel, skeleton2UnitView);
                        break;
                    case Ent1:
                        Ent1Controller ent1Controller = new Ent1Controller(levelScreen, enemyUnitModel);
                        Ent1View ent1View = new Ent1View(levelScreen, enemyUnitModel, ent1Controller);
                        createEnemyUnit(enemyUnitModel, ent1View);
                        break;
                    case Wizard:
                        WizardController wizardController = new WizardController(levelScreen, enemyUnitModel);
                        WizardUnitView wizardUnitView = new WizardUnitView(levelScreen, enemyUnitModel, wizardController);
                        createEnemyUnit(enemyUnitModel, wizardUnitView);
                        break;
                }
//                Zombie2Controller zombie2Controller = new Zombie2Controller(level, enemyUnitModel);
//                Zombie2View zombie2View = new Zombie2View(level, enemyUnitModel, zombie2Controller);
//                createEnemyUnit(enemyUnitModel, zombie2View);
                levelScreen.addEnemyUnitToEnemyArray(enemyUnitModel);
                levelScreen.addUnitModel(enemyUnitModel);
                break;
        }
    }

    public void createEnemyUnit(GameUnitModel model, GameUnitView view) {
        this.model = model;
        this.view = view;
        levelScreen.addChild(view, position.x, position.y);
    }

    public void createPlayerUnit(GameUnitModel model, GameUnitView view) {
        this.model = model;
        this.view = view;
        levelScreen.addChild(view, position.x, position.y);
    }
}

package com.timgapps.warfare.Level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.boontaran.MessageListener;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Level.GUI.HUD;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;
import com.timgapps.warfare.Level.GUI.StoneButton;
import com.timgapps.warfare.Level.GUI.UnitButton;
import com.timgapps.warfare.Level.LevelScreens.DarkLayer;
import com.timgapps.warfare.Level.LevelScreens.LevelCompletedScreen;
import com.timgapps.warfare.Tools.WorldContactListener;
import com.timgapps.warfare.Units.GameUnits.Barricade;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnits.Enemy.Zombie;
import com.timgapps.warfare.Units.GameUnits.Enemy.Zombie1;
import com.timgapps.warfare.Units.GameUnits.Player.Archer1;
import com.timgapps.warfare.Units.GameUnits.Player.Gnome;
import com.timgapps.warfare.Units.GameUnits.Player.SiegeTower;
import com.timgapps.warfare.Units.GameUnits.Player.Thor;
import com.timgapps.warfare.Utils.Setting;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;
import java.util.Random;

public class Level extends StageGame {

    public static final int ON_COMPLETED = 3;

    public static final float WORLD_SCALE = 100; // коэффициент масштабирования
    private Box2DDebugRenderer debugRender;

    private World world;
    private float accumulator;
    public static final float STEP = 1 / 55f;
    private ArrayList<EnemyUnit> arrayEnemies;
    public ArrayList<Actor> arrayActors;

    private float timeCount = 0;
    private HUD hud;

    private float energyCount = 0;
    private float coinCount;
    private int levelNumber;
    private GameManager gameManager;
    Random random;
    private ArrayList<TeamEntity> team;
    private Barricade barricade;
    private Image rockBig, rockMiddle, rockSmall;
    private SiegeTower siegeTower;
    private LevelCompletedScreen levelCompletedScreen;

    private boolean isActiveScreen = true;

    private DarkLayer darkLayer;
    private Table tableUnitButtons;


    public Level(int levelNumber, GameManager gameManager) {

        this.levelNumber = levelNumber;
        this.gameManager = gameManager;

//        System.out.println("Level Number " + levelNumber);
        setBackGround("level_bg");
        arrayEnemies = new ArrayList<EnemyUnit>();
        arrayActors = new ArrayList<Actor>();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener()); // присваиваем слушатель ContactListener, который регистрирует событие столкновения в игровом мире
        debugRender = new Box2DDebugRenderer(); // объект debugRendered будем использовать для отладки игрового мира, он позволяет выделить границы полигона


        /** создадим баррикаду **/
        barricade = new Barricade(this, Barricade.ROCKS);
//        addChild(barricade);

        siegeTower = new SiegeTower(this, 8, 260, gameManager.getTowerHealth(), 2);


        /** Добавим вражеских юнитов **/
        random = new Random();

        Zombie1 zombie = new Zombie1(this, 350, 250, 20, 5);
//        Zombie1 zombie = new Zombie1(this, 400, 250, 20, 5);
//        Zombie1 zombie = new Zombie1(this, 400, 250, 100, 5);
        zombie.debug();
        Zombie1 zombie1 = new Zombie1(this, 1500, 230, 20, 3);
        Zombie1 zombie2 = new Zombie1(this, 1200, 180, 20, 3);
        Zombie1 zombie3 = new Zombie1(this, 900, 210, 20, 3);
//        Zombie zombie4 = new Zombie(this, 1200, 200, 20, 5);

//        Zombie1 zombie3 = new Zombie1(this, 900, 120 + (random.nextFloat() * 150) + 30, 20, 10);
//        Zombie1 zombie4 = new Zombie1(this, 870, 120 + (random.nextFloat() * 150), 50, 10);
//        Zombie zombie5 = new Zombie(this, 930, 150 + (random.nextFloat() * 150) + 20, 50, 10);
        accumulator = 0;

        arrayEnemies.add(zombie);
        arrayEnemies.add(zombie1);
        arrayEnemies.add(zombie2);
        arrayEnemies.add(zombie3);
//        arrayEnemies.add(zombie4);


//        arrayEnemies.add(zombie1);
//        arrayEnemies.add(zombie2);
//        arrayEnemies.add(zombie3);
//        arrayEnemies.add(zombie4);
//        arrayEnemies.add(zombie5);

//        addChild(new Archer1(this, 200, 200, 30, 20));


        darkLayer = new DarkLayer(0, 0, getWidth(), getHeight(), new Color(0, 0, 0, 0.7f));
        darkLayer.setVisible(false);
        addOverlayChild(darkLayer);

        coinCount = gameManager.getCoinsCount();
        hud = new HUD(this);
        hud.setPosition(32, getHeight() - hud.getHeight());
        addOverlayChild(hud);
        addUnitButtons();

//        siegeTower.setHealth(30);

        levelCompletedScreen = new LevelCompletedScreen(this, gameManager.getCoinsRewardForLevel(), gameManager.getScoreRewardForLevel());

        levelCompletedScreen.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == LevelCompletedScreen.ON_OK) {   // у нас только одна кнопка,
//                    savePlayerData();
                    call(ON_COMPLETED);                       // при получении сообщений от которой мы передаем сообщение ON_COMPLETED
                }
            }
        });
    }


    public void getParricadeX() {
        barricade.getX();
    }


    public ArrayList<EnemyUnit> getArrayEnemies() {
        return arrayEnemies;
    }

    public void removeEnemyUnitFromArray(EnemyUnit unit) {
        arrayEnemies.remove(unit);
    }

    private void setBackGround(String region) {
        clearBackground();
        Image bg = new Image(Warfare.atlas.findRegion(region));
        addBackground(bg, true, false);
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (Setting.DEBUG_WORLD) {
            debugRender.render(world, camera.combined.cpy().scl(WORLD_SCALE));
        }
    }

    @Override
    protected void update(float delta) {
        super.update(delta);

//        timeCount += delta;
        energyCount += delta;

        /** Timur **/
        accumulator += delta;
        while (accumulator >= STEP) {
            world.step(STEP, 8, 6);
            accumulator -= STEP;
        }

        compareActorsYPos();
    }

    public void addGnome() {
        new Gnome(this, 160, 210, 50, 2);
    }

    public void addArcher1() {
        new Archer1(this, 100, 160, 20, 2);
    }

    public void addThor() {
        new Thor(this, 100, 160, 20, 2);
    }

    public void compareActorsYPos() {


        /** Полностью рабочий код, но с ошиблкой Zindex cannot be < 0 **/
//        if (arrayActors.size() > 1) {
//            ArrayList<Actor> gameActors = arrayActors;
////            ArrayList<Actor> gameActors = arrayActors;
//            boolean sorted = false;
//            int tempZIndex;
//            Actor tempActor;
//            while (!sorted) {
//                sorted = true;
//                for (int i = 0; i < gameActors.size() - 1; i++) {
//                    if (gameActors.get(i).getZIndex() < gameActors.get(i + 1).getZIndex()) {
//                        if (gameActors.get(i).getY() < gameActors.get(i + 1).getY()) {
//                            tempZIndex = gameActors.get(i).getZIndex();
//                            gameActors.get(i).setZIndex(gameActors.get(i + 1).getZIndex());
//                            gameActors.get(i + 1).setZIndex(tempZIndex);
//
//                            tempActor = gameActors.get(i);
//
//                            gameActors.set(i, gameActors.get(i + 1));
//                            gameActors.set(i + 1, tempActor);
//                            sorted = false;
//                        }
//                    }
//                }
//            }
//        }

        if (arrayActors.size() > 1) {
            Array<Actor> gameActors = stage.getActors();
//            ArrayList<Actor> gameActors = arrayActors;
            boolean sorted = false;
            int tempZIndex;
            Actor tempActor;
            while (!sorted) {
                sorted = true;
                for (int i = 0; i < gameActors.size - 1; i++) {
                    if (gameActors.get(i).getZIndex() < gameActors.get(i + 1).getZIndex()) {
                        if (gameActors.get(i).getY() < gameActors.get(i + 1).getY()) {
                            tempZIndex = gameActors.get(i).getZIndex();
                            gameActors.get(i).setZIndex(gameActors.get(i + 1).getZIndex());
                            gameActors.get(i + 1).setZIndex(tempZIndex);

                            tempActor = gameActors.get(i);

                            gameActors.set(i, gameActors.get(i + 1));
                            gameActors.set(i + 1, tempActor);
                            sorted = false;
                        }
                    }
                }
            }
        }
    }

    public Random getRandom() {
        return random;
    }

    public int getEnergyCount() {
        return (int) energyCount;
    }

    public int getCoinCount() {
        return (int) coinCount;
    }

    public void setEnergyCount(float priceEnergy) {
        this.energyCount -= priceEnergy;
    }

    @Override
    public void dispose() {
        super.dispose();
//        hud.dispose();
    }


    /**
     * метод для получения БАРРИКАДЫ
     **/
    public Barricade getBarricade() {
        return barricade;
    }

    /**
     * метод для получения ОСАДНОЙ БАШНИ
     **/
    public SiegeTower getSiegeTower() {
        return siegeTower;
    }

    /**
     * метод добавляет таблицу с кнопками юнитов (для их появления)
     **/
    public void addUnitButtons() {
        team = gameManager.getTeam();
        tableUnitButtons = new Table().debug();
        float unitButtonWidth = team.get(0).getWidth();
        float unitButtonHeight = team.get(0).getHeight();
        StoneButton stoneButton = new StoneButton(this, new Image(Warfare.atlas.findRegion("stoneButtonActive")),
                new Image(Warfare.atlas.findRegion("stoneButtonInactive")), UnitButton.TypeOfUnit.STONE);


        for (int i = 0; i < team.size(); i++) {
//            tableUnitButtons.add(team.get(i).getUnitButton()).padLeft(12).padRight(12);
            switch (team.get(i).getUnitType()) {
                case TeamEntity.GNOME:
                    tableUnitButtons.add(new UnitButton(this, new Image(Warfare.atlas.findRegion("gnomeActive")),
                            new Image(Warfare.atlas.findRegion("gnomeInactive")), UnitButton.TypeOfUnit.GNOME))
                            .width(unitButtonWidth).height(unitButtonHeight).padLeft(12).padRight(12);
                    break;
                case TeamEntity.ARCHER:
                    tableUnitButtons.add(new UnitButton(this, new Image(Warfare.atlas.findRegion("archer1Active")),
                            new Image(Warfare.atlas.findRegion("archer1Inactive")), UnitButton.TypeOfUnit.ARCHER1))
                            .width(unitButtonWidth).height(unitButtonHeight).padLeft(12).padRight(12);
                    break;
                case TeamEntity.THOR:
//                     tableUnitButtons.add(new UnitButton(this, new Image(Warfare.atlas.findRegion("thorActive")),
//                             new Image(Warfare.atlas.findRegion("thorInactive")),UnitButton.TypeOfUnit.ARCHER1)).padLeft(12).padRight(12);
//                     break;
                    tableUnitButtons.add(new UnitButton(this, new Image(Warfare.atlas.findRegion("thorActive")),
                            new Image(Warfare.atlas.findRegion("thorInactive")), UnitButton.TypeOfUnit.THOR))
                            .width(unitButtonWidth).height(unitButtonHeight).padLeft(12).padRight(12);
                    break;

                case TeamEntity.STONE:
                    tableUnitButtons.add(stoneButton)
                            .width(unitButtonWidth).height(unitButtonHeight).padLeft(12).padRight(12);
                    break;
            }
        }

        tableUnitButtons.setWidth(team.size() * unitButtonWidth + 24);
        tableUnitButtons.setHeight(team.get(0).getHeight());

        tableUnitButtons.setPosition((getWidth() - tableUnitButtons.getWidth()) / 2, 24);

        stoneButton.setUnitButtonTablePosX(tableUnitButtons.getX());
        addOverlayChild(tableUnitButtons);
    }

    public void levelCompleted() {
        levelCompletedScreen.setPosition((getWidth() - levelCompletedScreen.getWidth()) / 2, getHeight() * 2 / 3);
        addOverlayChild(levelCompletedScreen);
        isActiveScreen = false;
        darkLayer.setVisible(true);     // затемняем задний план
        tableUnitButtons.setVisible(false);      // кнопки юитов делаем невидимыми
//        tableUnitButtons.remove();      // кнопки юитов делаем невидимыми
        hud.hideEnergyPanel();
        levelCompletedScreen.start();   // запускаем экран завершения уровня
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}

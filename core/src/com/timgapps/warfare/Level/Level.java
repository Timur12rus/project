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
import com.timgapps.warfare.Level.LevelScreens.GameOverScreen;
import com.timgapps.warfare.Level.LevelScreens.LevelCompletedScreen;
import com.timgapps.warfare.Tools.WorldContactListener;
import com.timgapps.warfare.Units.GameUnits.Barricade;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnits.Enemy.Goblin1;
import com.timgapps.warfare.Units.GameUnits.Enemy.Skeleton;
import com.timgapps.warfare.Units.GameUnits.Enemy.Skeleton3;
import com.timgapps.warfare.Units.GameUnits.Enemy.Zombie;
import com.timgapps.warfare.Units.GameUnits.Enemy.Zombie1;
import com.timgapps.warfare.Units.GameUnits.Enemy.Zombie3;
import com.timgapps.warfare.Units.GameUnits.Player.Archer1;
import com.timgapps.warfare.Units.GameUnits.Player.Gnome;
import com.timgapps.warfare.Units.GameUnits.Player.SiegeTower;
import com.timgapps.warfare.Units.GameUnits.Player.Thor;
import com.timgapps.warfare.Utils.Setting;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;
import java.util.Random;

public class Level extends StageGame {

    public static final int ON_COMPLETED = 1;
    public static final int ON_FAILED = 2;
    public static final int ON_RETRY = 3;

    public static final float WORLD_SCALE = 100; // коэффициент масштабирования
    private Box2DDebugRenderer debugRender;

    private World world;
    private float accumulator;
    public static final float STEP = 1 / 60f;
    private ArrayList<EnemyUnit> arrayEnemies;
    public ArrayList<Actor> arrayActors;

    private float timeCount = 0;
    private HUD hud;

    private float energyCount = 0;
    private int coinsCount;         // кол-во монет у игрока
    private int levelNumber;
    private GameManager gameManager;
    Random random;
    private ArrayList<TeamEntity> team;
    private Barricade barricade;
    private Image rockBig, rockMiddle, rockSmall;
    private SiegeTower siegeTower;
    private LevelCompletedScreen levelCompletedScreen;
    private GameOverScreen gameOverScreen;

    private boolean isActiveScreen = true;

    private DarkLayer darkLayer;
    private Table tableUnitButtons;
    private int coinsReward;            // награда - кол-во монет за уровень
    private int scoreReward;            // награда - кол-во очков за уровень


    public Level(int levelNumber, final GameManager gameManager) {

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

//        Zombie1 zombie = new Zombie1(this, 700, 250, 400, 5);
//        zombie.debug();

//        Zombie zombie1 = new Zombie(this, 600, 230, 400, 10);

//        Zombie zombie2 = new Zombie(this, 600, 180, 400, 10);

//
        Zombie zombie3 = new Zombie(this, 900, 210, 100, 3);
        Zombie zombie4 = new Zombie(this, 1000, 190, 100, 3);
        Zombie3 zombie5 = new Zombie3(this, 1100, 220, 100, 3);
        Skeleton skeleton = new Skeleton(this, 800, 180, 400, 3);
        Skeleton skeleton1 = new Skeleton(this, 1260, 220, 100, 3);



        Zombie zombie6 = new Zombie(this, 1300, 200, 100, 3);
        Zombie zombie7 = new Zombie(this, 1400, 220, 100, 3);
        Zombie3 zombie8 = new Zombie3(this, 1600, 180, 100, 3);
        Skeleton skeleton2= new Skeleton(this, 1500, 190, 400, 3);
        Skeleton skeleton3 = new Skeleton(this, 1700, 210, 100, 3);




//        Skeleton skeleton2 = new Skeleton(this, 1100, 220, 100, 3);


//        Skeleton skeleton1 = new Skeleton(this, 1200, 220, 100, 3);
//        Skeleton3 skeleton3 = new Skeleton3(this, 900, 180, 100, 10);
//        Skeleton skeleton1 = new Skeleton(this, 700, 200, 50, 3);


        Goblin1 goblin1 = new Goblin1(this, 1600, 220, 100, 3);
        Goblin1 goblin2 = new Goblin1(this, 1400, 230, 100, 3);

//        zombie4.debug();

        accumulator = 0;

////        arrayEnemies.add(zombie);
//        arrayEnemies.add(zombie1);
//        arrayEnemies.add(zombie2);

        arrayEnemies.add(zombie3);
        arrayEnemies.add(zombie4);
        arrayEnemies.add(zombie5);
        arrayEnemies.add(skeleton);
        arrayEnemies.add(skeleton1);

        arrayEnemies.add(zombie6);
        arrayEnemies.add(zombie7);
        arrayEnemies.add(zombie8);
        arrayEnemies.add(skeleton2);
        arrayEnemies.add(skeleton3);

        arrayEnemies.add(goblin1);
        arrayEnemies.add(goblin2);

//        arrayEnemies.add(goblin3);


//        arrayEnemies.add(zombie4);


//        arrayEnemies.add(zombie1);
//        arrayEnemies.add(zombie2);
//        arrayEnemies.add(zombie3);
//        arrayEnemies.add(zombie4);
//        arrayEnemies.add(zombie5);
//        arrayEnemies.add(skeleton3);
//        arrayEnemies.add(skeleton);
//        arrayEnemies.add(skeleton1);

//        addChild(new Archer1(this, 200, 200, 30, 20));


        darkLayer = new DarkLayer(0, 0, getWidth(), getHeight(), new Color(0, 0, 0, 0.7f));
        darkLayer.setVisible(false);
        addOverlayChild(darkLayer);

        coinsCount = gameManager.getCoinsCount();
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

        gameOverScreen = new GameOverScreen(this);
        gameOverScreen.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == gameOverScreen.ON_MAP) {
//                    savePlayerData();
                    call(ON_FAILED);                       // при получении сообщений от которой мы передаем сообщение ON_FAILED
                }

                if (message == GameOverScreen.ON_RETRY) {
                    call(ON_RETRY);
                }
            }
        });

    }

    /**
     * метод получает кол-во монет в качестве награды
     **/
    public int getRewardCoinsCount() {
        coinsReward = gameManager.getLevelIcons().get(levelNumber - 1).getData().getCoinsCount();
        return coinsReward;
    }

    /**
     * метод получает кол-во очков в качестве награды
     **/
    public int getRewardScoreCount() {
        scoreReward = gameManager.getLevelIcons().get(levelNumber - 1).getData().getScoreCount();
        return scoreReward;
    }

    /**
     * метод устанавливает количество звезд после прохождения уровня
     **/
    private void setStarsCountToLevelIcon() {
        // установим количество звезд за уровень для текущего уровня, установив кол-во звезд в объекте данных - Data
        gameManager.getLevelIcons().get(levelNumber - 1).getData().setStarsCount(calculateStarsCount());

        // обновим кол-во звезд за уровень для текущего уровня
        gameManager.getLevelIcons().get(levelNumber - 1).updateStarsCount();

//        gameManager.getLevelIcons().get(levelNumber - 1).updateStarsCount(calculateStarsCount());

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
        addBackground(bg, true, true);
//        addBackground(bg, false, false);
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

    public void addGnome(int health, int damage) {
        new Gnome(this, 160, 210, health, damage);
    }

    public void addArcher1(int health, int damage) {
        new Archer1(this, 140, 210, health, 50);
//        new Archer1(this, 140, 210, health, damage);
//        new Archer1(this, 600, 210, health, damage);

    }

    public void addThor(int health, int damage) {
        new Thor(this, 160, 210, health, damage);
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

    public int getCoinsCount() {
        return coinsCount;
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
     * метод разблокирует следующие три уровня
     **/
    private void unlockNextLevels() {
        for (int i = levelNumber - 1; i < levelNumber + 3; i++) {
            // делаем levelIcon активным
            gameManager.getLevelIcons().get(i).getData().setActive();

            // обновляем визуальное представление levelIcon
            gameManager.getLevelIcons().get(i).checkIsActive();
        }
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
        StoneButton stoneButton = null;
//        StoneButton stoneButton = new StoneButton(this, new Image(Warfare.atlas.findRegion("stoneButtonActive")),
//                new Image(Warfare.atlas.findRegion("stoneButtonInactive")), );


        for (int i = 0; i < team.size(); i++) {
//            tableUnitButtons.add(team.get(i).getUnitButton()).padLeft(12).padRight(12);
            switch (team.get(i).getUnitType()) {
                case TeamEntity.GNOME:
//                    tableUnitButtons.add(new UnitButton(this, new Image(Warfare.atlas.findRegion("gnomeActive")),
//                            new Image(Warfare.atlas.findRegion("gnomeInactive")), TeamEntity.GNOME))
//                            .width(unitButtonWidth).height(unitButtonHeight).padLeft(12).padRight(12);

                    tableUnitButtons.add(new UnitButton(this, new Image(Warfare.atlas.findRegion("gnomeActive")),
                            new Image(Warfare.atlas.findRegion("gnomeInactive")), team.get(i).getEntityData()))
                            .width(unitButtonWidth).height(unitButtonHeight).padLeft(12).padRight(12);
                    break;
                case TeamEntity.ARCHER:
                    tableUnitButtons.add(new UnitButton(this, new Image(Warfare.atlas.findRegion("archer1Active")),
                            new Image(Warfare.atlas.findRegion("archer1Inactive")), team.get(i).getEntityData()))
                            .width(unitButtonWidth).height(unitButtonHeight).padLeft(12).padRight(12);
                    break;
                case TeamEntity.THOR:
//                     tableUnitButtons.add(new UnitButton(this, new Image(Warfare.atlas.findRegion("thorActive")),
//                             new Image(Warfare.atlas.findRegion("thorInactive")),UnitButton.TypeOfUnit.ARCHER1)).padLeft(12).padRight(12);
//                     break;
                    tableUnitButtons.add(new UnitButton(this, new Image(Warfare.atlas.findRegion("thorActive")),
                            new Image(Warfare.atlas.findRegion("thorInactive")), team.get(i).getEntityData()))
                            .width(unitButtonWidth).height(unitButtonHeight).padLeft(12).padRight(12);
                    break;

                case TeamEntity.STONE:
                    stoneButton = new StoneButton(this, new Image(Warfare.atlas.findRegion("stoneButtonActive")),
                            new Image(Warfare.atlas.findRegion("stoneButtonInactive")), team.get(i).getEntityData());
                    tableUnitButtons.add(stoneButton)
                            .width(unitButtonWidth).height(unitButtonHeight).padLeft(12).padRight(12);
                    break;
            }
        }

        tableUnitButtons.setWidth(team.size() * unitButtonWidth + 24);
        tableUnitButtons.setHeight(team.get(0).getHeight());

        tableUnitButtons.setPosition((getWidth() - tableUnitButtons.getWidth()) / 2, 24);

        if (stoneButton != null) stoneButton.setUnitButtonTablePosX(tableUnitButtons.getX());
        addOverlayChild(tableUnitButtons);
    }

    public void gameOver() {
        gameOverScreen.setPosition((getWidth() - gameOverScreen.getWidth()) / 2, getHeight() * 2 / 3);
        addOverlayChild(gameOverScreen);
        darkLayer.setVisible(true);         // затемняем задний план
        tableUnitButtons.setVisible(false); // кнопки юитов делаем невидимыми
        hud.hideEnergyPanel();
    }

    public void levelCompleted() {
        levelCompletedScreen.setPosition((getWidth() - levelCompletedScreen.getWidth()) / 2, getHeight() * 2 / 3);
        addOverlayChild(levelCompletedScreen);

        isActiveScreen = false;
        darkLayer.setVisible(true);     // затемняем задний план
        tableUnitButtons.setVisible(false);      // кнопки юитов делаем невидимыми
//        tableUnitButtons.remove();      // кнопки юитов делаем невидимыми
        hud.hideEnergyPanel();

        int starsCount = calculateStarsCount();

        /** установим кол-во монет в менеджере и сохраняем игру
         * позже просто выведем анимацию добавления монет, очков и звезд полученных за уровень
         */
        gameManager.setCoinsCount(coinsCount + getRewardCoinsCount());
        gameManager.addScoreCount(getRewardScoreCount());

        /** добавим к панели звёзд полученное кол-во звёзд */
        gameManager.getStarsPanel().addStarsCount(starsCount);


        gameManager.getSavedGame().addStarsCount(starsCount);
        gameManager.getSavedGame().setIndexRewardStars(gameManager.getStarsPanel().getIndexOfRewardStars());

        gameManager.getStarsPanel().updateCountReward();


        setStarsCountToLevelIcon();

        unlockNextLevels();

        gameManager.saveGame();


        levelCompletedScreen.start(starsCount);   // запускаем экран завершения уровня


    }

    /**
     * метод для вычисления кол-ва звезд получаемых за уровень
     **/
    private int calculateStarsCount() {
        int starsCount = 1;

        float towerHealth = siegeTower.getHealth();
        float fullTowerHealth = siegeTower.getFullHealth();

        if (((towerHealth / fullTowerHealth) >= 1.0 / 3.0) && (towerHealth / fullTowerHealth) <= 2.0 / 3.0) {
            starsCount = 2; // starCount = 2;
        }

        if ((towerHealth / fullTowerHealth) == 1) {
            starsCount = 3;
        }
//        System.out.println("starsCount = " + starsCount);
        return starsCount;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}

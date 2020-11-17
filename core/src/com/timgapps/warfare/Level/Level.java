package com.timgapps.warfare.Level;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.boontaran.MessageListener;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Level.GUI.Finger;
import com.timgapps.warfare.Level.GUI.HUD;
import com.timgapps.warfare.Level.GUI.team_unit.CreateUnitButton;
import com.timgapps.warfare.Level.GUI.team_unit.TeamUnit;
import com.timgapps.warfare.Level.GUI.StoneButton;
import com.timgapps.warfare.Level.GUI.team_unit.UnitImageButton;
import com.timgapps.warfare.Level.LevelScreens.ColorRectangle;
import com.timgapps.warfare.Level.LevelScreens.GameOverScreen;
import com.timgapps.warfare.Level.LevelScreens.LevelCompletedScreen;
import com.timgapps.warfare.Level.LevelScreens.PauseScreen;
import com.timgapps.warfare.Units.GameUnits.Barricade;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock.FireRockShoot;
import com.timgapps.warfare.Units.GameUnits.Player.SiegeTower;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.units.UnitCreator;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;
import java.util.Random;

public class Level extends StageGame {
    // добавим несколько констант для хранения состояния игры
    public static final int PLAY = 1;
    public static final int LEVEL_FAILED = 2;
    public static final int LEVEL_COMPLETED = 3;
    public static final int PAUSED = 4;
    public static final int ON_COMPLETED = 1;
    public static final int ON_FAILED = 2;
    public static final int ON_RETRY = 3;
    public static final int ON_EXIT = 4;
    public static final int ON_PAUSED = 5;
    private Box2DDebugRenderer debugRender;
    private ShapeRenderer shapeRenderer;
    //    private ShapeRenderer shapeRenderer;
    private float accumulator;
    public static final float STEP = 1 / 60f;
    private ArrayList<EnemyUnitModel> arrayEnemies;
    private ArrayList<PlayerUnitModel> arrayPlayers;
    public ArrayList<Actor> arrayActors;
    public ArrayList<GameUnitModel> arrayModels;
    private float timeCount = 0;
    private HUD hud;
    private float energyCount = 0;
    private int coinsCount;         // кол-во монет у игрока
    private int levelNumber;
    private GameManager gameManager;
    private Random random;
    private ArrayList<TeamUnit> team;
    private Barricade barricade;
    private Image rockBig, rockMiddle, rockSmall;
    private SiegeTower siegeTower;
    private LevelCompletedScreen levelCompletedScreen;
    private GameOverScreen gameOverScreen;
    private PauseScreen pausedScreen;
    private boolean isActiveScreen = true;
    private ColorRectangle colorRectangle;
    private TableUnitButton tableUnitButtons;
    private int coinsReward;            // награда - кол-во монет за уровень
    private int scoreReward;            // награда - кол-во очков за уровень
    private int state = 1;
    private boolean isPausedScreenStart = false;
    private boolean isPausedScreenHide = true;
    private boolean isPausedScreenAdded = false;
    private LevelCreator levelCreator;
    private float unitButtonWidth;
    private float unitButtonHeight;
    private Finger finger;
    private UnitCreator unitCreator;
    private float waitTime = 300;

    public Level(int levelNumber, final GameManager gameManager) {
        this.levelNumber = levelNumber;
        this.gameManager = gameManager;

        shapeRenderer = new ShapeRenderer();

//        System.out.println("Level Number " + levelNumber);
        setBackGround("level_bg");
        arrayEnemies = new ArrayList<EnemyUnitModel>();
        arrayPlayers = new ArrayList<PlayerUnitModel>();
        arrayModels = new ArrayList<GameUnitModel>();
        arrayActors = new ArrayList<Actor>();
        debugRender = new Box2DDebugRenderer(); // объект debugRendered будем использовать для отладки игрового мира, он позволяет выделить границы полигона
        /** создадим баррикаду **/
        barricade = new Barricade(this, Barricade.ROCKS);
        siegeTower = new SiegeTower(this, -48, 270, gameManager.getTowerHealth(), 2);
        /** Добавим вражеских юнитов **/
        random = new Random();
        levelCreator = new LevelCreator(this, levelNumber);
        accumulator = 0;
        colorRectangle = new ColorRectangle(0, 0, getWidth(), getHeight(), new Color(0, 0, 0, 0.7f));
        colorRectangle.setVisible(false);
        addOverlayChild(colorRectangle);


        unitCreator = new UnitCreator(this);

//        unitCreator.createUnit("Zombie1", new Vector2(1200, 270));
//        unitCreator.createUnit("Zombie1", new Vector2(750, 250));

//        unitCreator.createUnit("Zombie3", new Vector2(2500, 280));
//        unitCreator.createUnit("Zombie3", new Vector2(5000, 300));
//        unitCreator.createUnit("Zombie3", new Vector2(3000, 220));


        // TODO TEST 08.11.2020
//        unitCreator.createUnit("Zombie1", new Vector2(370, 270));
//        unitCreator.createUnit("Zombie3", new Vector2(570, 270));
//        unitCreator.createUnit("Zombie1", new Vector2(700, 250));
//        unitCreator.createUnit("Zombie1", new Vector2(300, 240));
//        unitCreator.createUnit("Zombie2", new Vector2(600, 200));
//        unitCreator.createUnit("Wizard", new Vector2(1100, 250));
//        unitCreator.createUnit("Skeleton2", new Vector2(900, 270));
//        unitCreator.createUnit("Ent1", new Vector2(1000, 270));

//        unitCreator.createUnit("Zombie2", new Vector2(600, 230));

////

//        unitCreator.createUnit("Skeleton1", new Vector2(1200, 230));

//

//        unitCreator.createUnit("Barbarian", new Vector2(200, 240));
//        unitCreator.createUnit("Viking", new Vector2(200, 240));

//        unitCreator.createUnit("Zombie2", new Vector2(720, 270));
//        unitCreator.createUnit("Skeleton1", new Vector2(640, 240));
//        unitCreator.createUnit("Skeleton2", new Vector2(820, 230));

//        unitCreator.createUnit("Thor", new Vector2(100, 200));
//        unitCreator.createUnit("Thor", new Vector2(200, 220));
//        unitCreator.createUnit("Gnome", new Vector2(400, 200));
//        unitCreator.createUnit("Knight", new Vector2(400, 200));
//        unitCreator.createUnit("Archer", new Vector2(400, 200));

        // молния для теста
//        Lightning lightning = new Lightning(this, new Vector2(500, 240), -100);

//        new FireRockShoot(this);

        coinsCount = gameManager.getCoinsCount();
        hud = new HUD(this);
        hud.setPosition(32, getHeight() - hud.getHeight());
        addOverlayChild(hud);
        // создадим таблицу с юнитами
        team = gameManager.getTeam();
        // создадим таблицу с кнопками юнитов
        tableUnitButtons = new TableUnitButton(this, team);
        tableUnitButtons.debug();
        tableUnitButtons.setWidth(team.size() * unitButtonWidth + 24);
        tableUnitButtons.setHeight(unitButtonHeight);
        tableUnitButtons.setPosition((getWidth() - tableUnitButtons.getWidth()) / 2, 24);
        // TODO fix X coordiante
        System.out.println("tableUnitButton X = " + tableUnitButtons.getX());
        System.out.println("unitButtonDeltaX = " + (team.get(0).getUnitImageButton().getWidth() + 24) * 2);
        tableUnitButtons.setStoneButtonPosX(tableUnitButtons.getX());
//        tableUnitButtons.setStoneButtonPosX(tableUnitButtons.getX() + (team.get(2).getUnitImageButton().getWidth() + 24) * 2);
        addOverlayChild(tableUnitButtons);
        // добавим указатель "ПАЛЕЦ"
        if (levelNumber == 1) {
            /** если статус обучалки "как создать юнит", то создадим указатель **/
            if (gameManager.getHelpStatus() == gameManager.HELP_UNIT_CREATE) {
                finger = new Finger(tableUnitButtons.getX() + (unitButtonWidth / 2 - Finger.WIDTH / 2) + 48 + 36,
                        tableUnitButtons.getY() + unitButtonHeight + 16 + Finger.HEIGHT,
                        Finger.DOWN, new TextureRegion(Warfare.atlas.findRegion("fingerUpRight")));
                finger.debug();
                float x = tableUnitButtons.getX() + (unitButtonWidth - Finger.WIDTH) / 2 + 48 + 36;
                float y = tableUnitButtons.getY() + unitButtonHeight + 16 + Finger.HEIGHT;
                finger.setPosition(x, y);
                addChild(finger);
                finger.setVisible(false);
            }
        } else {
            finger = null;
        }
//        siegeTower.setHealth(30);
        pausedScreen = new PauseScreen(this);
        pausedScreen.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == pausedScreen.ON_MAP) {   // у нас только одна кнопка,
//                    savePlayerData();
                    call(ON_FAILED);                       // при получении сообщений от которой мы передаем сообщение ON_EXIT
                } else if (message == pausedScreen.ON_CONTINUE) {
                    resumeLevel();      // возвращаемся к игре, если получено сообщение ON_CONTINUE
                }
            }
        });

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

    public void createPlayerUnit(PlayerUnits unitId) {
//        unitCreator.createUnit(unitId.name(), new Vector2(900 + random.nextFloat() * 16 - 8, 220));           // это для теста
        unitCreator.createUnit(unitId.name(), new Vector2(200 + random.nextFloat() * 16 - 8, 220));
//        unitCreator.createUnit("Wizard", new Vector2(1200, 250));
    }

    public void addEnemyUnitToEnemyArray(EnemyUnitModel enemyUnit) {
        arrayEnemies.add(enemyUnit);
    }

    public void addPlayerUnitToPlayerArray(PlayerUnitModel playerUnitModel) {
        arrayPlayers.add(playerUnitModel);
    }

    private void resumeLevel() {
        if (!isPausedScreenHide && isPausedScreenStart) {
            isPausedScreenHide = true;
            isPausedScreenStart = false;
            hidePauseScreen();
            state = PLAY;
        }
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

    public ArrayList<EnemyUnitModel> getArrayEnemies() {
        return arrayEnemies;
    }

    public ArrayList<PlayerUnitModel> getArrayPlayers() {
        return arrayPlayers;
    }

//    public void removeEnemyUnitFromArray(EnemyUnit unit) {
//        arrayEnemies.remove(unit);
//    }

    public void removeEnemyUnitFromArray(EnemyUnitModel unit) {
        arrayEnemies.remove(unit);
    }

    public void removePlayerUnitFromArray(PlayerUnitModel unit) {
        arrayPlayers.remove(unit);
    }

    private void setBackGround(String region) {
        clearBackground();
        Image bg = new Image(Warfare.atlas.findRegion(region));
        addBackground(bg, true, true);
//        addBackground(bg, false, false);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
//        if (Setting.DEBUG_WORLD) {
//            debugRender.render(world, camera.combined.cpy().scl(WORLD_SCALE));
//            stage.getBatch().end();
//            shapeRenderer.begin();
//            shapeRenderer.setColor(Color.RED);
//            for (GameUnitModel unitModel : arrayModels) {
//                shapeRenderer.rect(unitModel.getBody().getX(), unitModel.getBody().getY(), unitModel.getBody().getWidth(), unitModel.getBody().getHeight());
//            }
//            shapeRenderer.end();
//            stage.getBatch().begin();
//        }
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        if (state != PAUSED) {

            // для теста (пуск огненных камней)
//            waitTime--;
//            if (waitTime < 0) {
//                new FireRockShoot(this);
//                waitTime = 200;
//            }

//        if (state == PLAY) {
//        timeCount += delta;
            energyCount += delta;
            /** Timur **/
//            accumulator += delta;
//            while (accumulator >= STEP) {
//                world.step(STEP, 8, 6);
//                accumulator -= STEP;
//            }
            compareActorsYPos();
        }
        // finger
//        if (finger != null) {
//            if (tableUnitButtons.getUnitButton(0).getIsUnitButtonReady() && state == PLAY) {
//                finger.show();
//            } else {
//                finger.hide();
//            }
//            if (state != PLAY) {
//                finger.setVisible(false);
//            }
//        }
        // если баррикада разрушена и текущее состояние не "пауза" и экран завершения уровня не запущен
        if (barricade.isBarricadeDestroyed() && state != PAUSED && !levelCompletedScreen.isStarted()) {
            levelCompleted();   // запускаем метод завершения уровня
        }


//        if (state != PLAY) {
//            finger.stopPlayAction(true);
//        } else {
//            finger.stopPlayAction(false);
//        }
    }

    public void shakeCamera() {
        float x = camera.position.x;
        float y = camera.position.y;
        camera.position.x = (float) (x + Math.random() * 16 + 4);
        camera.position.y = (float) (y + Math.random() * 16 + 4);
    }

    public void compareActorsYPos() {
        /** Полностью рабочий код, но с ошиблкой Zindex cannot be < 0 **/
        if (arrayActors.size() > 1) {                           // если актеров на сцене > 1
            Array<Actor> gameActors = stage.getActors();        // получим массив всех актеров на сцене
            boolean sorted = false;         // флаг обозначает отсортирован ли
            int tempZIndex;                 // переменная "буфер", в ней сохраним ZIndex актера, у которого будем менять ZIndex
            Actor tempActor;                // актер "буфер", в него сохраянем актера при смене ZIndex
            while (!sorted) {               // пока не будеет отсортирован массив актеров
                sorted = true;
                for (int i = 0; i < gameActors.size - 1; i++) {
                    if (gameActors.get(i).getZIndex() < gameActors.get(i + 1).getZIndex()) {
                        /** меняем местами ZIndex актеров, если их коориднаты не соответствуют ZIndex'ам **/
                        if (gameActors.get(i).getY() < gameActors.get(i + 1).getY()) {
//                        if (gameActors.get(i).getY() < gameActors.get(i + 1).getY()) {
                            if ((gameActors.get(i + 1).getY() - gameActors.get(i).getY()) >= 2) {
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

    // вычитает кол-во энергии из общего кол-ва энергии
    public void subEnergyCount(float priceEnergy) {
        this.energyCount -= priceEnergy;
    }

    @Override
    public void dispose() {
        super.dispose();
//        hud.dispose();
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {

            if (pausedScreen != null) {
                if (state == PLAY) {
                    pauseLevel(true);
                } else {
                    pauseLevel(false);
                }
            }
        }
        return super.keyUp(keycode);
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

    class TableUnitButton extends Table {
        ArrayList<UnitImageButton> unitButtonArrayList;
        ArrayList<TeamUnit> team;
        //        float unitButtonWidth;
//        float unitButtonHeight;
        StoneButton stoneButton;
        Level level;
        float stoneButtonXpos;

        public TableUnitButton(Level level, ArrayList<TeamUnit> team) {
            super();
            this.team = team;
            this.level = level;
            unitButtonArrayList = new ArrayList<UnitImageButton>();
            unitButtonWidth = team.get(0).getUnitImageButton().getWidth();
            unitButtonHeight = team.get(0).getUnitImageButton().getHeight();
            stoneButton = null;
            // добавим кнопки с юнитами в соответствии с имеющимися юнитами в команде
            addUnitButtons();

            for (int i = 0; i < unitButtonArrayList.size(); i++) {
                add(unitButtonArrayList.get(i)).width(unitButtonWidth).height(unitButtonHeight).padLeft(12).padRight(12);
            }
            setWidth((unitButtonWidth + 24) * unitButtonArrayList.size());
            setHeight(unitButtonHeight);
        }

        void setStoneButtonPosX(float posX) {
            if (stoneButton != null)
                stoneButton.setPosX(posX);
        }

        UnitImageButton getUnitButton(int i) {
            return unitButtonArrayList.get(i);
        }

        // метод добавляет кнопки юнитов в соответствии с командой
        void addUnitButtons() {
            for (TeamUnit teamUnit : team) {
//            for (int i = 0; i < team.size(); i++) {
                if (teamUnit.getUnitData().getUnitId() != PlayerUnits.Rock) {
                    unitButtonArrayList.add(new CreateUnitButton(level, teamUnit.getUnitData()));
                } else {
                    stoneButton = new StoneButton(level, teamUnit.getUnitData());
                    unitButtonArrayList.add(stoneButton);
                }
//                unitButtonArrayList.add(team.get(i).getUnitImageButton());
                if (teamUnit.getUnitId() != PlayerUnits.Rock) {
                    this.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            super.clicked(event, x, y);
//                            System.out.println("Add Unit " + team.get(i).getName() + " on Scene!");
//                            if ((isReadyUnitButton) && (checkEnergyCount(energyPrice))) {
//                                isReadyUnitButton = false;
//                                setInActive();
//                                addPlayerUnit(unitType);
//                            }
                        }
                    });
                }


//                unitButtonArrayList.get(i).addListener(new ClickListener())
//                switch (team.get(i).getUnitId()) {
//                    case Gnome:
//                        unitButtonArrayList.add(new UnitButton(level, new Image(Warfare.atlas.findRegion("gnomeActive")),
//                                new Image(Warfare.atlas.findRegion("gnomeInactive")), team.get(i).getUnitData()));
//                        break;
//                    case Archer:
//                        unitButtonArrayList.add(new UnitButton(level, new Image(Warfare.atlas.findRegion("archer1Active")),
//                                new Image(Warfare.atlas.findRegion("archer1Inactive")), team.get(i).getUnitData()));
//                        break;
//                    case Thor:
//                        unitButtonArrayList.add(new UnitButton(level, new Image(Warfare.atlas.findRegion("thorActive")),
//                                new Image(Warfare.atlas.findRegion("thorInactive")), team.get(i).getUnitData()));
//                        break;
//
//                    case Knight:
//                        unitButtonArrayList.add(new UnitButton(level, new Image(Warfare.atlas.findRegion("knightActive")),
//                                new Image(Warfare.atlas.findRegion("knightInactive")), team.get(i).getUnitData()));
//                        break;
//
//                    case Stone:
//                        stoneButton = new StoneButton(level, new Image(Warfare.atlas.findRegion("stoneButtonActive")),
//                                new Image(Warfare.atlas.findRegion("stoneButtonInactive")), team.get(i).getUnitData());
//                        unitButtonArrayList.add(stoneButton);
//                        break;
//                }
            }
        }
    }

    // метод для показа экрана паузы
    public void showPausedScreen() {
        pausedScreen.setPosition(getWidth() / 2 - pausedScreen.getWidth() / 2, getHeight() / 2 - pausedScreen.getHeight() / 2);
        if (!isPausedScreenAdded) {
            addOverlayChild(pausedScreen);  // если экран паузы не добавлен
            isPausedScreenAdded = true;     // то добавляем,и меняем флаг на true
        } else {
            pausedScreen.setVisible(true);
        }
        colorRectangle.setVisible(true);
        tableUnitButtons.setVisible(false); // кнопки юитов делаем невидимыми
    }

    // метод для удаления указателя "палец"
    public void removeFinger() {
        if (finger != null)
            finger.remove();
    }

    // скрывает экран паузы
    public void hidePauseScreen() {
        pausedScreen.setVisible(false);
        colorRectangle.setVisible(false);
        tableUnitButtons.setVisible(true); // кнопки юитов делаем видимыми
    }

    private void pauseLevel() {     // будет вызываться с передачей true
        pauseLevel(true);
    }

    private void pauseLevel(boolean withDialog) {
//        if (state == PAUSED ) return;
        if (state != PLAY) return;
        state = PAUSED;

        if (!isPausedScreenStart && isPausedScreenHide) {
            isPausedScreenStart = true;
            if (withDialog) {
                addOverlayChild(pausedScreen);
                showPausedScreen();
            }
            isPausedScreenHide = false;
            call(ON_PAUSED);
        } else return;
    }

    @Override
    public void pause() {
        super.pause();
        if (state == PLAY) {   // проверяем игровое состояние и вызываем метод pauseLevel(), после чего вызываем метод суперкласса
            pauseLevel(true);
        } else {
            pauseLevel(false);
        }
    }

    public void gameOver() {
        state = LEVEL_FAILED;
        gameOverScreen.setPosition((getWidth() - gameOverScreen.getWidth()) / 2, getHeight() * 2 / 3);
        addOverlayChild(gameOverScreen);
        colorRectangle.setVisible(true);         // затемняем задний план
        tableUnitButtons.setVisible(false); // кнопки юитов делаем невидимыми
        hud.hideEnergyPanel();
    }

    /**
     * метод завершения уровня, вызывается после того, как разрушилась баррикада
     **/
    public void levelCompleted() {
        levelCompletedScreen.setPosition((getWidth() - levelCompletedScreen.getWidth()) / 2, getHeight() * 2 / 3);
        addOverlayChild(levelCompletedScreen);

        isActiveScreen = false;
        colorRectangle.setVisible(true);     // затемняем задний план
        tableUnitButtons.setVisible(false);      // кнопки юитов делаем невидимыми
//        tableUnitButtons.remove();      // кнопки юитов делаем невидимыми
        hud.hideEnergyPanel();

        int starsCount = calculateStarsCount();

        /** установим кол-во монет в менеджере и сохраняем игру
         * позже просто выведем анимацию добавления монет, очков и звезд полученных за уровень
         */
        gameManager.setCoinsCount(coinsCount + getRewardCoinsCount());
        gameManager.addScoreCount(getRewardScoreCount());
//        gameManager.addStarsCount(starsCount);

        /** добавим к панели звёзд полученное кол-во звёзд */
        gameManager.getStarsPanel().addStarsCount(starsCount);


        gameManager.getSavedGame().addStarsCount(starsCount);
        gameManager.getSavedGame().setIndexRewardStars(gameManager.getStarsPanel().getIndexOfRewardStars());

        gameManager.getStarsPanel().updateCountReward();
        setStarsCountToLevelIcon();
        gameManager.getLevelIcons().get(levelNumber - 1).setFinished();
//        gameManager.getLevelIcons().get(levelNumber).setActive();
//        unlockNextLevels();

        gameManager.saveGame();


        if (levelNumber == 1) {
            gameManager.setHelpStatus(GameManager.HELP_STARS_PANEL);
        }

        // после того как разрушилась баррикада, вызываем метод запуска экрана завершения уровня
        levelCompletedScreen.start(starsCount);   // запускаем экран завершения уровня, запускаем звезды


    }

    public void setState(int state) {
        this.state = state;
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

    public void addUnitModel(GameUnitModel model) {
        arrayModels.add(model);
    }

    public void removeUnitModelFromArray(GameUnitModel unitModel) {
        for (GameUnitModel model : arrayModels) {
            if (model.equals(unitModel)) {
                arrayModels.remove(model);
            }
        }
    }

    public ArrayList<GameUnitModel> getArrayModels() {
        return arrayModels;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public int getState() {
        return state;
    }
}

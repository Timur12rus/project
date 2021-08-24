package com.timgapps.warfare.screens.level;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.boontaran.MessageListener;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Units.GameUnits.unitTypes.EnemyUnits;
import com.timgapps.warfare.Utils.Helper.Finger;
import com.timgapps.warfare.Utils.Helper.GameHelper;
import com.timgapps.warfare.Utils.Setting;
import com.timgapps.warfare.screens.level.background.BackgroundBuilder;
import com.timgapps.warfare.screens.level.gui_elements.UnitButtons;
import com.timgapps.warfare.screens.level.timer.MonsterTimer;
import com.timgapps.warfare.screens.map.interfaces.RewardVideoButtonController;
import com.timgapps.warfare.screens.map.windows.team_window.team_unit.TeamUnit;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.screens.level.level_windows.ColorRectangle;
import com.timgapps.warfare.screens.level.level_windows.GameOverScreen;
import com.timgapps.warfare.screens.level.level_windows.LevelCompletedScreen;
import com.timgapps.warfare.screens.level.level_windows.PauseScreen;
import com.timgapps.warfare.Units.GameUnits.Barricade;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.SiegeTower;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;
import com.timgapps.warfare.Units.GameUnits.Player.units.UnitCreator;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;
import java.util.Random;

public class LevelScreen extends StageGame {
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
    private float energyCount = 10;
    private int coinsCount;         // кол-во монет у игрока
    private int levelNumber;
    private GameManager gameManager;
    private Random random;
    private ArrayList<TeamUnit> team;
    private Barricade barricade;
    private SiegeTower siegeTower;
    private PauseScreen pausedScreen;
    private ColorRectangle fade;
    private int coinsReward;            // награда - кол-во монет за уровень
    private int scoreReward;            // награда - кол-во очков за уровень
    private int state = 1;
    private boolean isPausedScreenStart = false;
    private boolean isPausedScreenHide = true;
    private boolean isPausedScreenAdded = false;
    private LevelCreator levelCreator;
    private UnitCreator unitCreator;
    private float waitTime = 300;
    private boolean isShowLevelCompletedScreen;
    private UnitButtons unitButtons;
    private TiledMap levelMap;
    private MonsterTimer monsterTimer;
    private boolean isCompleted;
    public static float screenScale;
    private BackgroundBuilder backgroundBuilder;
    private RewardVideoButtonController rewardVideoButtonController;
    private GameHelper gameHelper;

    // метод строит уровень
    public void build(int levelNumber) {

        System.out.println("GetWidth = " + getWidth());
        System.out.println("GetHeight = " + getHeight());
        System.out.println("ViewPortWidth = " + stage.getViewport().getScreenWidth());
        System.out.println("ViewPortHeight = " + stage.getViewport().getScreenHeight());
        System.out.println("V_WIDTH = " + Warfare.V_WIDTH);
        System.out.println("V_HEIGHT = " + Warfare.V_HEIGHT);
        setBackGround("level_bg");

        isCompleted = false;
        if (arrayModels != null) {
            arrayModels.clear();
        } else {
            arrayModels = new ArrayList<GameUnitModel>();
        }
        if (arrayEnemies != null) {
            arrayEnemies.clear();
        } else {
            arrayEnemies = new ArrayList<EnemyUnitModel>();
        }
        if (arrayPlayers != null) {
            arrayPlayers.clear();
        } else {
            arrayPlayers = new ArrayList<PlayerUnitModel>();
        }
        stage.getActors().clear();      // очищаем набор всех актеров на сцене

        levelCreator.loadLevel(levelNumber);
        /** создадим баррикаду **/
        if (barricade != null) {
            removeChild(barricade);
        }
        /** создадим башню **/
        if (siegeTower != null) {
//            siegeTower.remove();
            removeChild(siegeTower);
        }

        screenScale = getScreenScale();

        barricade = new Barricade(this, Barricade.ROCKS);
        siegeTower = new SiegeTower(this, -48, 270, gameManager.getTowerHealth(), 2);
        state = PLAY;
        hideFade();
        unitButtons.redraw();      // кнопки юитов делаем видимыми
//        tableUnitButtons.setVisible(true);      // кнопки юитов делаем невидимыми
        hud.redraw();

        isShowLevelCompletedScreen = false;
        levelCreator.createScreens();
        pausedScreen.redraw();
        energyCount = 10;
        coinsCount = gameManager.getCoinsCount();

        // создаем вражеских юнитов
        levelMap = new TiledMap();
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters(); // здесь мы прописываем параметры обработки tmx-карты уровня
        params.generateMipMaps = true;
        params.textureMinFilter = Texture.TextureFilter.MipMapLinearNearest;
        params.textureMagFilter = Texture.TextureFilter.Linear;
        // загружаем карту
        levelMap = new TmxMapLoader().load("levels/level" + levelNumber + ".tmx", params);
        String layerName;
        for (MapLayer layer : levelMap.getLayers()) {
            layerName = layer.getName();
            System.out.println("Layer NAME = " + layerName);
            for (EnemyUnits enemyUnit : EnemyUnits.values()) {
                if (layerName.contains(enemyUnit.name())) {
                    createEnemyUnit(layer.getObjects(), layerName);
                }
            }
        }
        monsterTimer = new MonsterTimer(this);
//        new Bat(this, new Vector2(500, 240));
//        monsterTimer.reset();

//        if (Setting.DEBUG_GAME) {
//            gameHelper.showCreateUnit();
//        }
    }

    public void hideFade() {
        fade.setVisible(false);
        unitButtons.show();
    }

    public void showFade() {
        fade.setVisible(true);
        unitButtons.hide();
    }

    private float getScreenScale() {
        if (getWidth() / getHeight() > (float) appWidth / (float) appHeight) {
            return getWidth() / appWidth;
        } else {
            return getHeight() / appHeight;
        }
    }

    public void createEnemyUnit(MapObjects objects, String layerName) {
        System.out.println("layerName = " + layerName);
        for (MapObject object : objects) {
            float x = object.getProperties().get("x", Float.class);
            float y = object.getProperties().get("y", Float.class);
            unitCreator.createUnit(layerName, new Vector2(x, 140 + y));
        }
    }

    public void createEnemyUnit(EnemyUnits unitId, Vector2 position) {
        unitCreator.createUnit(unitId.name(), position);
    }

    public LevelScreen(final GameManager gameManager, RewardVideoButtonController rewardVideoButtonController) {
//        this.levelNumber = levelNumber;
        this.gameManager = gameManager;
        this.rewardVideoButtonController = rewardVideoButtonController;
        backgroundBuilder = new BackgroundBuilder();

        // класс для создания подсказок в игре
        gameHelper = new GameHelper(this, gameManager);

        shapeRenderer = new ShapeRenderer();

        /** 13.02.2021 закоментировал **/
//        setBackGround("level_bg");

        arrayEnemies = new ArrayList<EnemyUnitModel>();
        arrayPlayers = new ArrayList<PlayerUnitModel>();
        arrayModels = new ArrayList<GameUnitModel>();
        arrayActors = new ArrayList<Actor>();
        debugRender = new Box2DDebugRenderer(); // объект debugRendered будем использовать для отладки игрового мира, он позволяет выделить границы полигона

        /** Добавим вражеских юнитов **/
        random = new Random();
        levelCreator = new LevelCreator(this, gameManager);
//        levelCreator = new LevelCreator(this, levelNumber);
        accumulator = 0;


        fade = new ColorRectangle(0, 0, getWidth(), getHeight(), new Color(0, 0, 0, 0.7f));
        addOverlayChild(fade);
        unitCreator = new UnitCreator(this);

        hud = new HUD(this);
        addOverlayChild(hud);

        // создадим таблицу с юнитами
        team = gameManager.getTeam();
        // создадим таблицу с кнопками юнитов
        unitButtons = new UnitButtons(this, team);

        addOverlayChild(unitButtons);
        hideFade();
        pausedScreen = new PauseScreen(this);
        pausedScreen.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == pausedScreen.ON_MAP) {   // у нас только одна кнопка,
//                    savePlayerData();
                    Warfare.media.stopMusic("battleMusic1.ogg");
                    call(ON_FAILED);                       // при получении  сообщений от которой мы передаем сообщение ON_EXIT
                } else if (message == pausedScreen.ON_CONTINUE) {
                    resumeLevel();      // возвращаемся к игре, если получено сообщение ON_CONTINUE
                }
            }
        });

//        // создаем счетчик начала "волны врагов"
//        countDownTimer = new CountDownTimer(this);
//        monsterTimer = new MonsterTimer(this);
//        monsterTimer = new MonsterTimer(this);
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    // метод вызывается после нажатия кнопки "ОК" в окне завершения уровня
    public void onCompleted() {
        //TODO возможно нужно будет переделать, чтобы монеты добавлялись позже, при выходе из экрана уровня на карте, пока сделаю
        // чтобы просто менялось значение монет в панели монет
//        gameManager.getCoinsPanel().setCoinsCount(coinsCount + getRewardCoinsCount());
        call(ON_COMPLETED);                       // при получении сообщений от которой мы передаем сообщение ON_COMPLETED
    }

    public void onFailed() {
        call(ON_FAILED);
    }

    public void onRetry() {
        call(ON_RETRY);
    }

    @Override
    public void show() {
        super.show();
        build(levelNumber);
//        Warfare.media.playMusic("battleMusic1.ogg", false);
    }

    // показывает сообщение с подсказкой о "храбрости"
    public void showBraveryMessage() {
        if (gameManager.getHelpStatus() == GameHelper.BRAVERY) {
            gameHelper.showBravery();
        }
    }

    public void createPlayerUnit(PlayerUnits unitId) {
//        unitCreator.createUnit(unitId.name(), new Vector2(900 + random.nextFloat() * 16 - 8, 220));           // это для теста
//        unitCreator.createUnit(unitId.name(), new Vector2(900 + random.nextFloat() * 16 - 8, 220));
        unitCreator.createUnit(unitId.name(), new Vector2(200 + random.nextFloat() * 16 - 8, 220));
//        unitCreator.createUnit("Wizard", new Vector2(1200, 250));
    }

    public void addEnemyUnitToEnemyArray(EnemyUnitModel enemyUnit) {
        arrayEnemies.add(enemyUnit);
    }

    public void addPlayerUnitToPlayerArray(PlayerUnitModel playerUnitModel) {
        arrayPlayers.add(playerUnitModel);
    }

    public void resumeLevel() {
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
//        Image bg = new Image(Warfare.atlas.findRegion(region));
        Group backGround = backgroundBuilder.build(levelNumber);
        float scaleX = getScale(getWidth(), Warfare.V_WIDTH);
        float scaleY = getScale(getHeight(), Warfare.V_HEIGHT);

        System.out.println("BackGround Width = " + backGround.getWidth());
        System.out.println("BackGround Height = " + backGround.getHeight());

        System.out.println("ScaleX = " + scaleX);
        System.out.println("ScaleY = " + scaleY);
//        bg.setWidth(bg.getWidth() * scaleX);
//        bg.setHeight(bg.getHeight() * scaleY);

//        backGround.setWidth(backGround.getWidth() * scaleX);
//        backGround.setHeight(backGround.getHeight() * scaleY);

        backGround.setScale(scaleX, scaleY);

//        backGround.setWidth(bg.getWidth() * scaleX);
//        backGround.setHeight(bg.getHeight() * scaleY);

        addBackground(backGround, false, false);
    }

    private float getScale(float screenSize, float gameSize) {
        if (screenSize > gameSize) {
            return screenSize / gameSize;
        } else {
            return gameSize / gameSize;
        }
    }

    @Override
    protected void addBackground(Actor actor, boolean centerX, boolean centerY) {
//        super.addBackground(actor, centerX, centerY);
        background.addActor(actor);
        fillScreen(actor, centerX, centerY);
//        fitScreen(actor, centerX, centerY);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
//        if (!isCreateUnitHelp) {
//            gameHelper.showCreateUnit();
//            isCreateUnitHelp = true;
//        }
        if (state != PAUSED) {
//            countDownTimer.update(delta);
//            System.out.println("camera Pos y = " + camera.position);

            // Трясем камеру
//            if (Rumble.getRumbleTimeLeft() > 0) {
//                Rumble.tick(delta);
//                background.getActors().get(0).setY(-5);
//                camera.position.y = Rumble.getPos().y;
//            } else {
//                background.getActors().get(0).setY(0);
//                camera.position.y = 360;
//            }

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

        if (state == PLAY) {
            monsterTimer.update(delta);
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
//        if (barricade.isBarricadeDestroyed() && state != PAUSED && !levelCompletedScreen.isStarted()) {
//            levelCompleted();   // запускаем метод завершения уровня
//        }

        if (barricade.isBarricadeDestroyed() && state != PAUSED && !isShowLevelCompletedScreen) {
            isShowLevelCompletedScreen = true;
//            barricade.remove();
            levelCompleted();   // запускаем метод завершения уровня
            Warfare.media.stopMusic("battleMusic.ogg");
        }

//        if (state != PLAY) {
//            finger.stopPlayAction(true);
//        } else {
//            finger.stopPlayAction(false);
//        }
    }

    public void shakeCamera() {
//        float x = camera.position.x;
//        float y = camera.position.y;
//        System.out.println("Camera position = " + y);
//        camera.position.y = y - 6;
//
        Rumble.rumble(0.5f, 0.05f);
//        System.out.println("backgroundPos = " + background.getCamera().position.y);

//        camera.position.x = (float) (x + Math.random() * 16 + 4);
//        camera.position.y = (float) (y + Math.random() * 16 + 4);
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

    public void addEnergyCount(float energy) {
        energyCount += energy;
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

    // метод для показа экрана паузы
    public void showPausedScreen() {
        pausedScreen.setPosition(getWidth() / 2 - pausedScreen.getWidth() / 2, getHeight() / 2 - pausedScreen.getHeight() / 2);
        if (!isPausedScreenAdded) {
            addOverlayChild(pausedScreen);  // если экран паузы не добавлен
            isPausedScreenAdded = true;     // то добавляем,и меняем флаг на true
        } else {
            pausedScreen.setVisible(true);
        }
//        if (!pausedScreen.isVisible()) {
//            pausedScreen.setVisible(true);
//        }
        showFade();
//        tableUnitButtons.setVisible(false); // кнопки юитов делаем невидимыми
//        unitButtons.hide();
    }

    // скрывает экран паузы
    public void hidePauseScreen() {
        pausedScreen.setVisible(false);
        hideFade();
//        unitButtons.setVisible(true); // кнопки юитов делаем видимыми
    }

    // скрывает экран завершения уровня
    public void hideCompletedScreen() {

    }

    private void pauseLevel(boolean withDialog) {
//        if (state != PLAY) return;
        setState(PAUSED);
        // если сообщение с подсказкой не показано на экране, выводим экран паузы
        if (!gameHelper.isShowMessage()) {
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

    public void levelFailed() {
        Warfare.media.stopMusic("battleMusic1.ogg");
        Warfare.media.playMusic("failedMusic.ogg", false);

        // скрываем значок таймера волны монстров, если он на экране
        hideMonsterTimer();
        state = LEVEL_FAILED;
        showFade();        // затемняем задний план
//        unitButtons.hide();
//        tableUnitButtons.setVisible(false); // кнопки юитов делаем невидимыми
        hud.hideEnergyPanel();
        monsterTimer.hide();
        levelCreator.showGameOverScreen();

        rewardVideoButtonController.showRewardVideoButton();  // покажем кнопку для показа видеорекламы
    }

    /**
     * метод завершения уровня, вызывается после того, как разрушилась баррикада
     **/
    public void levelCompleted() {
        Warfare.media.stopMusic("battleMusic1.ogg");
//        Warfare.media.playMusic("victory.ogg", false);
        isCompleted = true;
        showFade();    // затемняем задний план

        // если подсказка о создании юнита (подсказка с "пальцем"), удаляем "палец"
        if (gameManager.getHelpStatus() == GameManager.HELP_UNIT_CREATE && levelNumber == 1) {
            gameManager.setHelpStatus(GameHelper.HELP_STARS_PANEL);
            gameHelper.clearCreateUnit();
        }
//        unitButtons.hide();
        hud.hideEnergyPanel();
        rewardVideoButtonController.showRewardVideoButton();  // покажем кнопку для показа видеорекламы

        int starsCount = calculateStarsCount();         // вычисляем кол-во звезд полученных за уровень
        int starsOfLevel = gameManager.getLevelIcons().get(levelNumber - 1).getData().getStarsCount();  // кол-во звезд у уровня
        /** установим кол-во монет в менеджере и сохраняем игру
         * позже просто выведем анимацию добавления монет, очков и звезд полученных за уровень
         */
        gameManager.addCoinsCount(gameManager.getLevelIcons().get(levelNumber - 1).getData().getCoinsCount());
//        gameManager.setCoinsCount(coinsCount + getRewardCoinsCount());
        gameManager.addScoreCount(getRewardScoreCount());

        // скрываем значок таймера волны монстров, если он на экране
        hideMonsterTimer();

        /** добавим к панели звёзд полученное кол-во звёзд */
//        если получили звезд за уровень больше чем было, то прибавим это кол-во к общему кол-ву звезд у игрока
        if (starsCount > starsOfLevel) {
            int addStarsCount = starsCount - starsOfLevel;      // кол-во звезд, которое добавим к общему кол-ву
            gameManager.addStarsCount(addStarsCount);           // добавим кол-во звезд к общему кол-ву звезд у игрока
            // установим кол-во полученных звезд текущему значку уровня (уровню)
            gameManager.getLevelIcons().get(levelNumber - 1).setFinished();
            gameManager.getLevelIcons().get(levelNumber - 1).getData().setStarsCount(starsCount);
            // обновим кол-во звезд за уровень для текущего уровня
            gameManager.getLevelIcons().get(levelNumber - 1).updateStarsCount();
        }

        // сохраним номер текущего завершенного уровня (это будет пока последний завершенный уровень)
        gameManager.setLastCompletedLevelNum(levelNumber);
        gameManager.saveGame();
//        if (levelNumber == 1) {
//            gameManager.setHelpStatus(GameManager.HELP_STARS_PANEL);
//        }
        float towerHealth = siegeTower.getHealth();         // кол-во здоровья у башни после окончания уровня
        float fullTowerHealth = siegeTower.getFullHealth(); // кол-во полного здоровья у башни
        levelCreator.showLevelCompletedScreen(starsCount, towerHealth / fullTowerHealth * 100);
    }

    private void hideMonsterTimer() {
        if (monsterTimer.isTimerIconStarted()) {
            monsterTimer.hide();
        }
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    // добавляет экран(группу) на сцену
    public void addScreen(Group screen) {
        addOverlayChild(screen);
    }

    // удаляет экран(группу) на сцену
    public void removeScreen(Group screen) {
        screen.clearChildren();
//        screen.clear();
        removeOverlayChild(screen);
    }

    // устанавливает текущий статус игры (пауза или игра и т.д.)
    public void setState(int state) {
        this.state = state;
    }

    /**
     * метод для вычисления кол-ва звезд получаемых за уровень
     **/
    private int calculateStarsCount() {
        int starsCount = 0;

        float towerHealth = siegeTower.getHealth();         // кол-во здоровья у башни после окончания уровня
        float fullTowerHealth = siegeTower.getFullHealth(); // кол-во полного здоровья у башни
        float healthTowerPercent = (towerHealth / fullTowerHealth) * 100;
        if (healthTowerPercent > 0 && healthTowerPercent < 33) {
            starsCount = 1;
        }
        if (healthTowerPercent >= 33 && healthTowerPercent < 100) {
            starsCount = 2;
        }
//        if (healthTowerPercent >= 33 && healthTowerPercent < 66) {
//            starsCount = 2;
//        }
        if (healthTowerPercent == 100) {
            starsCount = 3;
        }
//        System.out.println("starsCount = " + starsCount);
        return starsCount;
    }

    public void addUnitModel(GameUnitModel model) {
        arrayModels.add(model);
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

    @Override
    public void hide() {
        super.hide();
        hud.clear();
        levelCreator.clear();
        background.clear();
        dispose();
//        System.out.println("background = " + background.toString());
//        System.out.println("Hide!!!");
        stage.clear();
//        for (Actor actor : stage.clear();)
    }

    @Override
    public void addOverlayChild(Actor actor) {
        super.addOverlayChild(actor);
    }

    @Override
    public void removeOverlayChild(Actor actor) {
        super.removeOverlayChild(actor);
    }

    public GameHelper getGameHelper() {
        return gameHelper;
    }

    public UnitButtons getUnitButtons() {
        return unitButtons;
    }
}

package com.timgapps.warfare.screens.level;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.boontaran.MessageListener;
import com.boontaran.games.StageGame;
import com.boontaran.games.tiled.TileLayer;
import com.timgapps.warfare.Units.GameUnits.unitTypes.EnemyUnits;
import com.timgapps.warfare.screens.level.gui_elements.UnitButtons;
import com.timgapps.warfare.screens.level.timer.CountDownTimer;
import com.timgapps.warfare.screens.level.timer.MonsterWave;
import com.timgapps.warfare.screens.map.windows.team_upgrade_window.team_unit.TeamUnit;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.screens.level.LevelWindows.ColorRectangle;
import com.timgapps.warfare.screens.level.LevelWindows.GameOverScreen;
import com.timgapps.warfare.screens.level.LevelWindows.LevelCompletedScreen;
import com.timgapps.warfare.screens.level.LevelWindows.PauseScreen;
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
    //    private boolean isActiveScreen = true;      // активный экран или нет
    private ColorRectangle fade;
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
    private boolean isShowLevelCompletedScreen;
    private UnitButtons unitButtons;
    private TiledMap levelMap;
    private CountDownTimer countDownTimer;
    private boolean isCompleted;
    public static float screenScale;

    // метод строит уровень
    public void build(int levelNumber) {
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
        fade.setVisible(false);
        unitButtons.show();      // кнопки юитов делаем видимыми
//        tableUnitButtons.setVisible(true);      // кнопки юитов делаем невидимыми
        hud.redraw();

        isShowLevelCompletedScreen = false;
        levelCreator.createScreens();
        pausedScreen.redraw();
        energyCount = 0;

        // создаем вражеских юнитов
        levelMap = new TiledMap();
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters(); // здесь мы прописываем параметры обработки tmx-карты уровня
        params.generateMipMaps = true;
        params.textureMinFilter = Texture.TextureFilter.MipMapLinearNearest;
        params.textureMagFilter = Texture.TextureFilter.Linear;
        // загружаем карту
        levelMap = new TmxMapLoader().load("levels/level" + levelNumber + ".tmx", params);

//        unitCreator.createUnit("Goblin", new Vector2(570, 270));
        unitCreator.createUnit("Ork1", new Vector2(700, 220));
        unitCreator.createUnit("Troll1", new Vector2(1300, 230));
        unitCreator.createUnit("Ork1", new Vector2(1800, 250));
        unitCreator.createUnit("Troll2", new Vector2(900, 260));


        String layerName;
        for (MapLayer layer : levelMap.getLayers()) {
            layerName = layer.getName();
            System.out.println("Layer NAME = " + layerName);
            for (EnemyUnits enemyUnit : EnemyUnits.values()) {
                if (enemyUnit.name().equals(layerName)) {
                    createEnemyUnit(layer.getObjects(), layerName);
                }
            }
//            if (EnemyUnits.values().toString().equals(layerName)) {
//                createEnemyUnit(layer.getObjects(), layerName);
//            }
        }
//        unitCreator.createUnit("Zombie3", new Vector2(570, 270));
//        unitCreator.createUnit("Zombie1", new Vector2(700, 250));
//        unitCreator.createUnit("Zombie1", new Vector2(640, 240));
//        unitCreator.createUnit("Zombie1", new Vector2(300, 240));

        // создание волны с монстрами
//        MonsterWave monsterWave = new MonsterWave(this);
//        monsterWave.start();

        // создаем счетчик начала "волны врагов"
        countDownTimer = new CountDownTimer(this);
        countDownTimer.reset();
        System.out.println("GET WIDTH = " + getWidth());
        System.out.println("GET HEIGHT = " + getHeight());
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

    public LevelScreen(final GameManager gameManager) {
//        this.levelNumber = levelNumber;
        this.gameManager = gameManager;

        shapeRenderer = new ShapeRenderer();

//        System.out.println("Level Number " + levelNumber);
        setBackGround("level_bg");
        arrayEnemies = new ArrayList<EnemyUnitModel>();
        arrayPlayers = new ArrayList<PlayerUnitModel>();
        arrayModels = new ArrayList<GameUnitModel>();
        arrayActors = new ArrayList<Actor>();
        debugRender = new Box2DDebugRenderer(); // объект debugRendered будем использовать для отладки игрового мира, он позволяет выделить границы полигона
//        /** создадим баррикаду **/
//        barricade = new Barricade(this, Barricade.ROCKS);
//        siegeTower = new SiegeTower(this, -48, 270, gameManager.getTowerHealth(), 2);
        /** Добавим вражеских юнитов **/
        random = new Random();
        levelCreator = new LevelCreator(this, gameManager);
//        levelCreator = new LevelCreator(this, levelNumber);
        accumulator = 0;


        fade = new ColorRectangle(0, 0, getWidth(), getHeight(), new Color(0, 0, 0, 0.7f));
        fade.setVisible(false);
        addOverlayChild(fade);
        unitCreator = new UnitCreator(this);

//        unitCreator.createUnit("Zombie1", new Vector2(750, 250));

//        unitCreator.createUnit("Zombie1", new Vector2(1200, 270));
//        unitCreator.createUnit("Zombie1", new Vector2(750, 250));

//        unitCreator.createUnit("Zombie3", new Vector2(2500, 280));
//        unitCreator.createUnit("Zombie3", new Vector2(5000, 300));
//        unitCreator.createUnit("Zombie3", new Vector2(3000, 220));


        // TODO TEST 08.11.2020
//        unitCreator.createUnit("Zombie1", new Vector2(370, 270));
//        unitCreator.createUnit("Zombie1", new Vector2(470, 270));
//        unitCreator.createUnit("Zombie3", new Vector2(570, 270));
//        unitCreator.createUnit("Zombie1", new Vector2(700, 250));
//        unitCreator.createUnit("Zombie1", new Vector2(640, 240));
//        unitCreator.createUnit("Zombie1", new Vector2(300, 240));
//        unitCreator.createUnit("Zombie2", new Vector2(600, 200));
//        unitCreator.createUnit("Wizard", new Vector2(1100, 250));
//        unitCreator.createUnit("Skeleton2", new Vector2(900, 270));
//        unitCreator.createUnit("Ent1", new Vector2(1000, 270));

//        unitCreator.createUnit("Zombie2", new Vector2(600, 230));

////

//        unitCreator.createUnit("Skeleton1", new Vector2(1200, 230));

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
//        hud.setPosition(32, getHeight() - hud.getHeight());
        addOverlayChild(hud);
        // создадим таблицу с юнитами
        team = gameManager.getTeam();
        // создадим таблицу с кнопками юнитов
        unitButtons = new UnitButtons(this, team);
//        tableUnitButtons = new TableUnitButton(this, team);
//        unitButtons.debug();
//        unitButtons.setWidth(team.size() * unitButtonWidth + 24);
//        unitButtons.setHeight(unitButtonHeight);

//        unitButtons.setPosition((getWidth() - unitButtons.getWidth()) / 2, 24);
        // TODO fix X coordiante
//        System.out.println("tableUnitButton X = " + unitButtons.getX());
//        System.out.println("unitButtonDeltaX = " + (team.get(0).getUnitImageButton().getWidth() + 24) * 2);
//        unitButtons.setStoneButtonPosX(unitButtons.getX());
//        tableUnitButtons.setStoneButtonPosX(tableUnitButtons.getX() + (team.get(2).getUnitImageButton().getWidth() + 24) * 2);

        unitButtons.show();
        addOverlayChild(unitButtons);
        // добавим указатель "ПАЛЕЦ"
        if (levelNumber == 1) {
            /** если статус обучалки "как создать юнит", то создадим указатель **/
            if (gameManager.getHelpStatus() == gameManager.HELP_UNIT_CREATE) {
                finger = new Finger(unitButtons.getX() + (unitButtonWidth / 2 - Finger.WIDTH / 2) + 48 + 36,
                        unitButtons.getY() + unitButtonHeight + 16 + Finger.HEIGHT,
                        Finger.DOWN, new TextureRegion(Warfare.atlas.findRegion("fingerUpRight")));
                finger.debug();
                float x = unitButtons.getX() + (unitButtonWidth - Finger.WIDTH) / 2 + 48 + 36;
                float y = unitButtons.getY() + unitButtonHeight + 16 + Finger.HEIGHT;
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

//        // создаем счетчик начала "волны врагов"
//        countDownTimer = new CountDownTimer(this);

    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public void onCompleted() {
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
        addBackground(bg, false, false);
//        addBackground(bg, false, false);
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
        if (state != PAUSED) {
            countDownTimer.update(delta);
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
//        if (barricade.isBarricadeDestroyed() && state != PAUSED && !levelCompletedScreen.isStarted()) {
//            levelCompleted();   // запускаем метод завершения уровня
//        }

        if (barricade.isBarricadeDestroyed() && state != PAUSED && !isShowLevelCompletedScreen) {
            isShowLevelCompletedScreen = true;
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
        fade.setVisible(true);
//        tableUnitButtons.setVisible(false); // кнопки юитов делаем невидимыми
        unitButtons.hide();
    }

    // метод для удаления указателя "палец"
    public void removeFinger() {
        if (finger != null)
            finger.remove();
    }

    // скрывает экран паузы
    public void hidePauseScreen() {
        pausedScreen.setVisible(false);
        fade.setVisible(false);
        unitButtons.setVisible(true); // кнопки юитов делаем видимыми
    }

    // скрывает экран завершения уровня
    public void hideCompletedScreen() {

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

    public void levelFailed() {
        state = LEVEL_FAILED;
        fade.setVisible(true);         // затемняем задний план
        unitButtons.hide();
//        tableUnitButtons.setVisible(false); // кнопки юитов делаем невидимыми
        hud.hideEnergyPanel();
        levelCreator.showGameOverScreen();
    }

    /**
     * метод завершения уровня, вызывается после того, как разрушилась баррикада
     **/
    public void levelCompleted() {
//        isActiveScreen = false;
        isCompleted = true;
        fade.setVisible(true);     // затемняем задний план
        unitButtons.hide();
        countDownTimer.stop();
//        tableUnitButtons.setVisible(false);      // кнопки юитов делаем невидимыми
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
        gameManager.addStarsCount(starsCount);
//        gameManager.getStarsPanel().addStarsCount(starsCount);
//        gameManager.getSavedGame().addStarsCount(starsCount);
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
        levelCreator.showLevelCompletedScreen(starsCount);
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
        if (((towerHealth / fullTowerHealth) >= (1.0 / 3.0)) && (towerHealth / fullTowerHealth) <= (2.0 / 3.0)) {
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

    @Override
    public void hide() {
        super.hide();
        hud.clear();
        levelCreator.clear();
        dispose();
    }
}

package com.timgapps.warfare.screens.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boontaran.MessageListener;
import com.boontaran.games.StageGame;
import com.boontaran.games.tiled.TileLayer;
import com.timgapps.warfare.screens.map.actions.AddOverlayActionHelper;
import com.timgapps.warfare.screens.map.actions.MyCoinsAction;
import com.timgapps.warfare.screens.map.gui_elements.CoinsPanel;
import com.timgapps.warfare.screens.map.gui_elements.VideoRewardButton;
import com.timgapps.warfare.screens.map.interfaces.RewardVideoButtonController;
import com.timgapps.warfare.screens.map.interfaces.RewardedVideoAdListener;
import com.timgapps.warfare.screens.map.interfaces.RoundCircleController;
import com.timgapps.warfare.screens.map.windows.MissionInfoWindow;
import com.timgapps.warfare.screens.map.windows.gifts_window.GiftScreen;
import com.timgapps.warfare.screens.map.windows.reward_video_window.RewardVideoWindow;
import com.timgapps.warfare.screens.map.windows.team_upgrade_window.TeamUpgradeScreen;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.screens.level.level_windows.ColorRectangle;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.map.gui_elements.GiftIcon;
import com.timgapps.warfare.screens.map.gui_elements.LevelIcon;
import com.timgapps.warfare.screens.map.gui_elements.ScorePanel;
import com.timgapps.warfare.screens.map.gui_elements.StarsPanel;
import com.timgapps.warfare.screens.map.gui_elements.TeamUpgradeIcon;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class MapScreen extends StageGame implements AddOverlayActionHelper, RoundCircleController, RewardVideoButtonController {
    // создаем несколько констант для создания callBack сообщений, которые будут передаваться в зависимости от нажатия кнопок
    public static final int ON_BACK = 1;
    public static final int ON_LEVEL_SELECTED = 2;
    public static final int ON_SHOW_GET_REWARD = 3;
    public static final int ON_SHOW_REWARD_FOR_STARS_SCREEN = 4;
    public static final int ON_SHOW_REWARDED_VIDEO = 5;
    public static final int ON_LOAD_REWARD_VIDEO = 6;
    public static final int ON_SHARE = 4;
    private int selectedLevelId = 1;
    private ArrayList<LevelIcon> levelIcons;
    private MissionInfoWindow missionInfoWindow;
    private TeamUpgradeScreen teamUpgradeScreen;
    private RewardVideoWindow rewardVideoWindow;
    private GiftScreen giftScreen;
    private TeamUpgradeIcon teamUpgradeIcon;      // кнопка для вызова окна апгрейда юнитов
    private GiftIcon giftIcon;
    private GameManager gameManager;
    private CoinsPanel coinsPanel;              // панель с монетами
    private ScorePanel scorePanel;              // панель с игровыми очками
    private StarsPanel starsPanel;              // панель с наградой за звезды
    private boolean isEndCoinsAction = false;
    private int coinsReward;
    private int scoreReward;
    private TiledMap map;
    private int mapWidth, mapHeight, tilePixelWidth, tilePixelHeight, levelWidth, levelHeight;
    private boolean isFocused = true;
    private Label teamLabel;
    private ColorRectangle fade;
    private float cameraXpos;
    private float cameraYpos;
    private ColorRectangle greenRectangle;
    private final int W_RECT = 10;
    private final int H_RECT = 8;
    private boolean isScreenShown;
    private boolean isFirstStart = true;
    private boolean isStartCameraZoom = false;
    private float cameraZoomValue = 0.5f;
    private float timeToCameraZoomTarget, cameraZoomTarget, cameraZoomOrigin, cameraZoomDuration;
    private final float LEVEL_WIDTH = 1792; // (56 x 32)
    private final float LEVEL_HEIGHT = 1024; // (32 x 32)
    private VideoRewardButton rewardVideoButton;
    private RewardedVideoAdListener rewardedVideoAdListener;
    private boolean onShowRewardVideo;
    private boolean onLoadRewardVideo;
    private MyCoinsAction rewardCoinsAction;
    private boolean isShowRewardVideoButton = true;

    @Override
    protected void update(float delta) {
        super.update(delta);
        if (onShowRewardVideo) {
            if (rewardedVideoAdListener.isErnedReward()) {
                rewardedVideoAdListener.resetIsErnedReward();
                onShowRewardVideo = false;
                rewardPlayer();
                hideRewardVideoButton();
            }
        }
        if (onLoadRewardVideo) {
            if (rewardedVideoAdListener.isLoaded()) {
                showRewardVideo();
            }
        }
        if (isStartCameraZoom) {
            if (cameraZoomValue < 1) {
                if (cameraZoomValue + 0.05f > 1) {
                    cameraZoomValue = 1;
                    isStartCameraZoom = false;
                } else {
                    cameraZoomValue += 0.05f;
                }
            } else {
                cameraZoomValue = 1;
                isStartCameraZoom = false;
            }
        }
        camera.zoom = cameraZoomValue;
    }

    private void rewardPlayer() {
//        showAddCoinsAnimation();
        gameManager.addCoinsCount(50);
        startCoinsAnimation();
    }

    private void startCoinsAnimation() {
        System.out.println("Start Animation");
        if (rewardCoinsAction != null) {
            rewardCoinsAction.clear();
        }
//        gameManager.addCoinsCount(50);
        rewardCoinsAction = new MyCoinsAction(this, new Vector2(getWidth() / 2, getHeight() / 2), gameManager, 50);
//        Vector2 coinsStartPosition = new Vector2(cameraXpos, cameraYpos - 38);
//        final CoinsAction coinsAction = new CoinsAction(this, new Vector2(coinsStartPosition));
//        Vector2 coinsEndPosition = new Vector2(coinsStartPosition.x + gameManager.getCoinsPanel().getPos().x / 2,
//                coinsStartPosition.y + gameManager.getCoinsPanel().getPos().y / 2);
//
//        coinsAction.setEndPosition(coinsEndPosition);
//        coinsAction.startAnimation();
    }

    public MapScreen(GameManager gameManager, int coinsReward, int scoreReward, final RewardedVideoAdListener rewardedVideoAdListener) {
        this.coinsReward = coinsReward;
        this.scoreReward = scoreReward;
        this.rewardedVideoAdListener = rewardedVideoAdListener;
        this.gameManager = gameManager;
        levelIcons = gameManager.getLevelIcons();
        cameraZoomTarget = 1;
        cameraZoomOrigin = 0.5f;
        timeToCameraZoomTarget = 2;

//        new Color(0x35a1afff);
        greenRectangle = new ColorRectangle(0, 0, LEVEL_WIDTH, LEVEL_HEIGHT, new Color(0x84a327ff));
//        greenRectangle = new ColorRectangle(0, 0, 1536, 1024, new Color(0x8eb353ff));
        addChild(greenRectangle);

        String directory = "location1";
        loadMap("tiled/" + directory + "/map.tmx");   // метод загружает карту и создает объекты

        fade = new ColorRectangle(0, 0, getWidth(), getHeight(), new Color(0, 0, 0, 0.7f));
        fade.setVisible(false);

        // если нажимаем на fade, скрываем окно, которое сейчас открыто, и возвращаемся на карту
        fade.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resumeLevelMap();
            }
        });
        addChild(fade);

        /** создадим окно с описанием уровня **/
        missionInfoWindow = new MissionInfoWindow(this);
        missionInfoWindow.hide();
        addChildOnOverlay(missionInfoWindow);
        missionInfoWindow.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == missionInfoWindow.ON_RESUME) {
//                    Warfare.media.playSound("click.ogg");
//                    showButtons();
                    resumeLevelMap();
                } else if (message == missionInfoWindow.ON_START) { //
                    selectLevel();
//                    call(ON_LEVEL_SELECTED);
                }
            }
        });

        // создадим окно с предложением посмотреть рекламу
        rewardVideoWindow = new RewardVideoWindow(this, rewardedVideoAdListener);
//        rewardVideoWindow.setVisible(false);
        rewardVideoWindow.setPosition((getWidth() - rewardVideoWindow.getWidth()) / 2,
                (getHeight() - rewardVideoWindow.getHeight()) / 2);
        addOverlayChild(rewardVideoWindow);
        rewardVideoWindow.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == rewardVideoWindow.ON_RESUME) {
                    resumeLevelMap();
                } else if (message == rewardVideoWindow.ON_REWARD) { //
                    showRewardVideo();
                }
            }
        });

        giftIcon = new GiftIcon(gameManager);
        giftIcon.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isFocused = false;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                isFocused = true;
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
//                isFocused = false;
                showGiftScreen();
            }
        });

//        addOverlayChild(giftIcon);
        /** создадим окно с вознаграждениями **/
        giftScreen = new GiftScreen(this, gameManager);
        giftScreen.setVisible(false);
        giftScreen.setPosition((getWidth() - giftScreen.getWidth()) / 2,
                (getHeight() - giftScreen.getHeight()) / 2);
        addOverlayChild(giftScreen);

        giftScreen.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == giftScreen.ON_RESUME) {
//                    Warfare.media.playSound("click.ogg");
                    resumeLevelMap();
                } else if (message == giftScreen.ON_SHOW_ANIMATIONS) {
                    showAddCoinsAnimation();
                }
            }
        });

        teamUpgradeIcon = new TeamUpgradeIcon();
        giftIcon.setPosition(32, 96);
        teamUpgradeIcon.setPosition(32, giftIcon.getY() + giftIcon.getHeight() + 64);
        addChildOnOverlay(teamUpgradeIcon);
        addChildOnOverlay(giftIcon);
        gameManager.addGeom(teamUpgradeIcon, "teamUpgradeIcon");

        Label.LabelStyle teamLabelStyle = new Label.LabelStyle();
        teamLabelStyle.fontColor = Color.WHITE;
        teamLabelStyle.font = Warfare.font18;
        teamUpgradeIcon.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isFocused = false;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                isFocused = true;
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                showTeamUpgradeScreen();
            }
        });

        // добавим панель с монетами на экран
        coinsPanel = gameManager.getCoinsPanel();
        /** !!!!!!!!!!!!!!!!!!!!! 10.03.2021
         // coinsPanel.setCoinsCount(gameManager.getCoinsCount());
         **/
        coinsPanel.setPosition(getWidth() - coinsPanel.getWidth() - 32, getHeight() - coinsPanel.getHeight() - 32);
        coinsPanel.setPos(new Vector2(coinsPanel.getX(), coinsPanel.getY()));

        // Добавим пенель с очками
        scorePanel = gameManager.getScorePanel();
        scorePanel.setPosition(32, getHeight() - scorePanel.getHeight() - 32);
        addChildOnOverlay(scorePanel);

        // Добавим панель с наградой за зведзды
        starsPanel = gameManager.getStarsPanel();
        starsPanel.setVisible(true);
        starsPanel.setPosition(32, scorePanel.getY() - starsPanel.getHeight());
        addChildOnOverlay(starsPanel);
        starsPanel.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                starsPanel.onDown();
                isFocused = false;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                starsPanel.onUp();
                isFocused = true;
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
//                coinsPanel.remove();
//                call(ON_SHOW_REWARD_FOR_STARS_SCREEN);
//                isFocused = false;
                showRewardForStarsScreen();
            }
        });

//        gameManager.setHelpStatus(GameManager.HELP_TEAM_UPGRADE);

        int helpStatus = gameManager.getHelpStatus();     // получим статус обучалки
        checkHelpStatus(helpStatus);

        /** создадим окно апргейда команды и передаём информацию о составе команды(manager)**/
        teamUpgradeScreen = new TeamUpgradeScreen(gameManager);
        teamUpgradeScreen.setVisible(false);
        addChildOnOverlay(teamUpgradeScreen);

        teamUpgradeScreen.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == teamUpgradeScreen.ON_RESUME) {
//                    Warfare.media.playSound("click.ogg");
                    resumeLevelMap();
                }
            }
        });

        // кнопка для простмотра видеорекламы
        rewardVideoButton = new VideoRewardButton();
        rewardVideoButton.setPosition(getWidth() / 2 - rewardVideoButton.getWidth() / 2, 96);
        addOverlayChild(rewardVideoButton);
        rewardVideoButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isFocused = false;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                isFocused = true;
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (rewardVideoButton.isEndAction()) {
                    showRewardVideoWindow();
                }
            }
        });

        // запустим анимацию  получения монет к общему кол-ву монет
        if (coinsReward > 0) {
//            showAddCoinsAnimation();
//            showAddScoreAnimation();

            //TODO нужно сделать сохранение в SavedGame() кол-во монет и очков
//            gameManager.setCoinsCount(coinsPanel.getCoinsCount());
//            gameManager.setScoreCount(scorePanel.getScoreCount());
//            gameManager.saveGame();
        }

        cameraXpos = camera.position.x;
        cameraYpos = camera.position.y;
        selectedLevelId = gameManager.getLastCompletedNum();
        zoomActionCamera();
    }

    // метод для просмотра видеорекламы
    private void showRewardVideo() {
        if (rewardedVideoAdListener.isLoaded()) {
            onShowRewardVideo = true;
            onLoadRewardVideo = false;
            call(ON_SHOW_REWARDED_VIDEO);
        } else {
            if (!onLoadRewardVideo) {
                // показываем в окне индикатор загузки рекламы (...)
                rewardVideoWindow.loadRewardVideo();
                onLoadRewardVideo = true;
                onShowRewardVideo = false;
                call(ON_LOAD_REWARD_VIDEO);
            }
        }
    }

    // метод выбора уровня
    public void selectLevel() {
        gameManager.setCameraPosition(new Vector2(cameraXpos, cameraYpos));
        call(ON_LEVEL_SELECTED);
    }

    public void setIsTouchedIcon(boolean isTouchedIcon) {
        this.isScreenShown = isTouchedIcon;
    }

    public boolean isScreenShown() {
        return isScreenShown;
    }

    private void clearOverlayActions() {
        for (Actor actor : overlay.getActors()) {
            if (actor instanceof Image) {
//                actor.clearActions();
                actor.remove();
//                removeOverlayChild(actor);
            }
        }
    }

    // метод для показа экрана наград за звезды
    private void showRewardForStarsScreen() {
//        hide();
//        rewardVideoButton.clearAction();
        isScreenShown = true;
        call(ON_SHOW_REWARD_FOR_STARS_SCREEN);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void hide() {
        super.hide();
        giftScreen.removeActions();
        teamUpgradeIcon.clearActions();
        removeOverlayChild(coinsPanel);
    }

    @Override
    public void show() {
        super.show();
        isScreenShown = false;
        redrawLevelIcons();
        resumeLevelMap();
        updateCameraPosition();
        coinsPanel.redraw();
        showCoinsPanel();

        // проверим, вернулись ли мы на карту после выхода из уровня
        if (isShowRewardVideoButton && !rewardVideoButton.isShown()) {
            rewardVideoButton.show();
        }
    }

    // метод запускает увеличение камеры на карте при запуске игры
    private void zoomActionCamera() {
        isStartCameraZoom = true;
    }

    // метод обновляет позицию камеры
    public void updateCameraPosition() {
        float levelIconPosX;
        float levelIconPosY;
        try {
            levelIconPosX = levelIcons.get(selectedLevelId).getX();
            levelIconPosY = levelIcons.get(selectedLevelId).getY();
        } catch (Exception e) {
            levelIconPosX = levelIcons.get(selectedLevelId - 1).getX();
            levelIconPosY = levelIcons.get(selectedLevelId - 1).getY();
        }
        if (levelIconPosX <= camera.viewportWidth / 2) {
            camera.position.x = camera.viewportWidth / 2;
        } else {
            camera.position.x = levelIconPosX;
        }
        if (levelIconPosX > levelWidth - camera.viewportWidth / 2) {
            camera.position.x = (levelWidth - camera.viewportWidth / 2);
        }

        if (levelIconPosY <= camera.viewportHeight / 2) {
            camera.position.y = camera.viewportHeight / 2;
        } else {
            camera.position.y = levelIconPosY;
        }
        if (levelIconPosY > levelHeight - camera.viewportHeight / 2) {
            camera.position.y = (levelHeight - camera.viewportHeight / 2);
        }
        cameraXpos = camera.position.x;
        cameraYpos = camera.position.y;
    }

    private void showCoinsPanel() {
        addChildOnOverlay(coinsPanel);
    }

    /**
     * метод для скрытия кнопок на экране
     **/
    private void hideButtons() {
//        teamLabel.setVisible(false);
        starsPanel.setVisible(false);
        fade.toFront();
        fade.setPosition(cameraXpos - camera.viewportWidth / 2, cameraYpos - camera.viewportHeight / 2);
        giftIcon.setVisible(false);
        teamUpgradeIcon.addAction(Actions.fadeOut(0));
        teamUpgradeIcon.setTouchable(Touchable.disabled);
        if (isShowRewardVideoButton) {
            rewardVideoButton.setVisible(false);
        }
//        teamUpgradeIcon.setVisible(false);
        fade.setVisible(true);
    }

    private void showButtons() {
//        teamLabel.setVisible(true);
        starsPanel.setVisible(true);
        fade.setVisible(false);
        giftIcon.setVisible(true);
//        if (finger != null && gameManager.getHelpStatus() != GameManager.HELP_GET_GIFT) {
//            finger.setVisible(true);
//        }
        gameManager.checkCanUpgrade();
        System.out.println("IsCan Upgrade = " + gameManager.isCanUpgrade());
        if (gameManager.isCanUpgrade()) {
            teamUpgradeIcon.showRoundCircle();
        } else {
            teamUpgradeIcon.hideRoundCircle();
        }
        teamUpgradeIcon.addAction(Actions.fadeIn(0));
        teamUpgradeIcon.setTouchable(Touchable.enabled);
        if (isShowRewardVideoButton) {
            rewardVideoButton.setVisible(true);
        }
//        teamUpgradeIcon.setVisible(true);
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isFocused && !isScreenShown) {
            float x = Gdx.input.getDeltaX();
            float y = Gdx.input.getDeltaY();
            cameraXpos = camera.position.x;
            cameraYpos = camera.position.y;
            if (cameraXpos - x < camera.viewportWidth / 2) {
                x = 0;
            }
            if (cameraXpos - x > (levelWidth - camera.viewportWidth / 2)) {
//            if (cameraXpos - x > (camera.viewportWidth / 2 + 256)) {
//            if (cameraXpos - x > camera.viewportWidth + 320) {
                x = 0;
            }

            if (cameraYpos + y < camera.viewportHeight / 2) {
                y = 0;
            }
            if (cameraYpos + y > camera.viewportHeight / 2 + 300) {
                y = 0;
            }
//            System.out.println("Camera position = " + new Vector2(camera.position.x, camera.position.y));
            camera.translate(-x, y);
//            System.out.println("Camera position After translate = " + new Vector2(camera.position.x, camera.position.y));
            if (fade != null) {
                fade.setPosition(camera.position.x - camera.viewportWidth / 2,
                        camera.position.y - camera.viewportHeight / 2);
            }
        }
        return true;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    private void loadMap(String tmxFile) {
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters(); // здесь мы прописываем параметры обработки tmx-карты уровня
        params.generateMipMaps = true;
        params.textureMinFilter = Texture.TextureFilter.MipMapLinearNearest;
        params.textureMagFilter = Texture.TextureFilter.Linear;
        // загружаем карту
        map = new TmxMapLoader().load(tmxFile, params);
        MapProperties prop = map.getProperties();
        mapWidth = prop.get("width", Integer.class);  // получаем и рассчитываем размеры объектов
        mapHeight = prop.get("height", Integer.class);
        tilePixelWidth = prop.get("tilewidth", Integer.class);
        tilePixelHeight = prop.get("tileheight", Integer.class);
        levelWidth = mapWidth * tilePixelWidth;
        levelHeight = mapHeight * tilePixelHeight;

        String layerName = new String();
        MapLayer mapLayer = new MapLayer();
        for (MapLayer layer : map.getLayers()) {
            layerName = layer.getName();
            if (layerName.equals("locations")) {
                mapLayer = layer;
                createLevelIcons(layer.getObjects(), layerName);
                clearFog();
                showLevelIcons(mapLayer.getObjects());
            } else {
                TileLayer tLayer = new TileLayer(camera, map, layerName, stage.getBatch());
                addChild(tLayer);
            }
        }

        levelIcons.get(0).setVisible(true);
    }

    private void redrawLevelIcons() {
        String layerName;
        MapLayer mapLayer;
        for (MapLayer layer : map.getLayers()) {
            layerName = layer.getName();
            if (layerName.equals("locations")) {
                mapLayer = layer;
                createLevelIcons(layer.getObjects(), layerName);
                clearFog();
                showLevelIcons(mapLayer.getObjects());
            }
        }
    }

    // метод создает значки с уровней
    private void createLevelIcons(MapObjects objects, String LayerName) {
        for (MapObject object : objects) {
            Rectangle rectangle;
            float x = object.getProperties().get("x", Float.class);
            float y = object.getProperties().get("y", Float.class);
            rectangle = new Rectangle(x, y, 32, 32);
            // добавим значки на карту
            if (LayerName.equals("locations")) {
                int i = parseInt(object.getName()) - 1;
                if (i < levelIcons.size()) {
                    levelIcons.get(i).addListener(iconListener);
                    levelIcons.get(i).setRectX((int) x / 32);
                    levelIcons.get(i).setRectY((int) y / 32);
                    levelIcons.get(i).toFront();
                    addChild(levelIcons.get(i), rectangle.x - 16, rectangle.y - 16);
                    System.out.println("LevelIcon[" + i + "] = (" + levelIcons.get(i).getX() + ", " + levelIcons.get(i).getY());
                }
            }
        }
    }


    // метод создает значки уровней
    private void showLevelIcons(MapObjects objects) {
        for (MapObject object : objects) {
            float x = object.getProperties().get("x", Float.class);
            float y = object.getProperties().get("y", Float.class);

            TiledMapTileLayer fogTileLayer = (TiledMapTileLayer) map.getLayers().get("fog");
            for (com.timgapps.warfare.screens.map.gui_elements.LevelIcon levelIcon : levelIcons) {
                levelIcon.toFront();
                TiledMapTileLayer.Cell fogCell = fogTileLayer.getCell(levelIcon.getRectX(), levelIcon.getRectY());
                TiledMapTileLayer.Cell fogUnderCell = fogTileLayer.getCell(levelIcon.getRectX(), levelIcon.getRectY() - 2);
                TiledMapTileLayer.Cell fogAboveCell = fogTileLayer.getCell(levelIcon.getRectX(), levelIcon.getRectY() + 1);
                levelIcon.setVisible(true);
                if (fogCell.getTile() != null) {
                    levelIcon.setVisible(false);
                }
                if (fogUnderCell.getTile() != null) {
                    levelIcon.setVisible(false);
                }
            }
        }
        levelIcons.get(0).setVisible(true);
    }

    // метод очищает область вокруг значка уровня на карте от "тумана войны"
    private void clearFog() {
        for (LevelIcon levelIcon : levelIcons) {
            if (levelIcon.equals(levelIcons.get(0))) {
                int xLevelIcon = (int) levelIcon.getX() / 32;
                int yLevelIcon = (int) levelIcon.getY() / 32;
                xLevelIcon = xLevelIcon - W_RECT / 2 + 1;
                yLevelIcon = yLevelIcon - H_RECT / 2 + 1;
                for (int deltaY = 0; deltaY < H_RECT; deltaY++) {
                    for (int deltaX = 0; deltaX < W_RECT; deltaX++) {
                        if (((deltaY == 0 || (deltaY == (H_RECT - 1))) && (deltaX == 0 || (deltaX == (W_RECT - 1))))) {
                            continue;
                        }
                        clearFogTile(xLevelIcon + deltaX, yLevelIcon + deltaY);
                    }
                }
            }

            if (levelIcon.isFinished()) {
                int xLevelIcon = (int) levelIcon.getX() / 32;
                int yLevelIcon = (int) levelIcon.getY() / 32;
                int width = W_RECT + 8;
                int height = H_RECT + 5;
                xLevelIcon = xLevelIcon - width / 2 + 2;
                yLevelIcon = yLevelIcon - height / 2 + 1;
                for (int deltaY = 0; deltaY < height; deltaY++) {
                    for (int deltaX = 0; deltaX < width; deltaX++) {
                        int y = yLevelIcon + deltaY;
                        // не удаляем углы (чтобы была форма не прямоугольника)
                        if (((deltaY == 0 || (deltaY == (height - 1))) && (deltaX == 0 || (deltaX == (width - 1))))) {
                            continue;
                        }
                        clearFogTile(xLevelIcon + deltaX, yLevelIcon + deltaY);
                    }
                }
            }
        }
        // светлая рамка вокруг открытых областей карты
        setLightMapTiles();
    }

    private void setLightMapTiles() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("fog");
        int layerWidth = layer.getWidth();
        int layerHeight = layer.getHeight();

        for (int x = 0; x < layerWidth; x++) {
            for (int y = 0; y < layerHeight; y++) {
                try {
                    if ((layer.getCell(x, y).getTile() != null) && (layer.getCell(x + 1, y).getTile() == null)) {
                        layer.getCell(x, y).setTile(map.getTileSets().getTileSet(1).getTile(109));
                    }
                } catch (Exception e) {
                }
                try {
                    if ((layer.getCell(x, y).getTile() != null) && (layer.getCell(x - 1, y).getTile() == null)) {
                        layer.getCell(x, y).setTile(map.getTileSets().getTileSet(1).getTile(109));
                    }
                } catch (Exception e) {
                }
                try {
                    if ((layer.getCell(x, y).getTile() != null) && (layer.getCell(x, y + 1).getTile() == null)) {
                        layer.getCell(x, y).setTile(map.getTileSets().getTileSet(1).getTile(109));
                    }
                } catch (Exception e) {
                }
                try {
                    if ((layer.getCell(x, y).getTile() != null) && (layer.getCell(x, y - 1).getTile() == null)) {
                        layer.getCell(x, y).setTile(map.getTileSets().getTileSet(1).getTile(109));
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    private void clearFogTile(int x, int y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("fog");
//        layer.setOpacity(0.6f);     // добавим прозрачность слою
        if (layer.getCell(x, y) != null) {
            layer.getCell(x, y).getTile();
            layer.getCell(x, y).setTile(null);
        }
    }

    public TiledMapTileLayer.Cell getCell(int x, int y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("roads");
        return layer.getCell(x, y);
    }

    private void showTeamUpgradeScreen() {
        isScreenShown = true;
        isFocused = false;
        hideButtons();
        teamUpgradeScreen.redrawTeamTable();
        teamUpgradeScreen.redrawCollectionTable();
        teamUpgradeScreen.setVisible(true);

        // TODO нужно исправить!!!!!!!!
        if (gameManager.getHelpStatus() == GameManager.HELP_TEAM_UPGRADE) {
            gameManager.setHelpStatus(GameManager.HELP_GET_GIFT);
        }
    }

    private void showGiftScreen() {
        isScreenShown = true;
        hideButtons();
        isFocused = false;
        giftScreen.redraw();
        giftScreen.setVisible(true);
    }

    /**
     * метод для возврата к карте уровней для выбора уровня
     **/
    public void resumeLevelMap() {
        if (teamUpgradeScreen.isUpgradeScreenVisible()) {
            teamUpgradeScreen.hide();
        }
        isScreenShown = false;
        /** скрываем окно с описанием уровня **/
        missionInfoWindow.hide();
        teamUpgradeScreen.setVisible(false);
        giftScreen.setVisible(false);
        rewardVideoWindow.hide();
        isFocused = true;
        showButtons();
        checkHelpStatus(gameManager.getHelpStatus());
    }

// TODO showAddCoinAnimation() !~!!!!!!!!!!!!!!

    private void checkHelpStatus(int helpStatus) {
        if (helpStatus == GameManager.HELP_STARS_PANEL) {
            starsPanel.showFinger();
            gameManager.setHelpStatus(GameManager.HELP_TEAM_UPGRADE);
            starsPanel.hideFinger();
        }
    }

    private void showAddCoinsAnimation() {
    }

    /**
     * слушатель, назначаем его значкам уровней на нашей карте уровней
     **/
    private ClickListener iconListener = new ClickListener() {
// при клике мы будем сохранять в переменную полученный id нажатого уровня

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            isFocused = false;
            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            super.touchUp(event, x, y, pointer, button);
            isFocused = true;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
//            isFocused = false;
            //будем воспроизводить звук щелчка и передавать в callBack сообщенме onLevelSelected
            selectedLevelId = ((LevelIcon) event.getTarget()).getId();

            // отобразим окно с информацией о выбранном уровне
            if (levelIcons.get(selectedLevelId - 1).getData().isActiveIcon())
                showMissionInfo(selectedLevelId);

//            /** установим в GameManager значение наград (монеты и очки за уровень) **/
            gameManager.setCoinsRewardforLevel(missionInfoWindow.getRewardCoinsForLevel());
            gameManager.setScoreRewardforLevel(missionInfoWindow.getRewardScoreForLevel());
        }
    };

    public GiftIcon getGiftIcon() {
        return giftIcon;
    }

    private void showMissionInfo(int selectedLevelId) {
        isFocused = false;
        isScreenShown = true;
        hideButtons();
        if (levelIcons.get(selectedLevelId - 1).getData().isActiveIcon()) {     // проверяем, активен ли уровень,
            if (!missionInfoWindow.isVisible()) {                               // если да, показываем окно с информацией об уровне
                /** передадим данные об уровне в объект экрана информации об уровне
                 * здесь "selectedLevelId - 1", потому что levelIcons.get(..) возвращает элемент списка с индексом на 1 меньше,
                 * т.к. нумеруется с "0"
                 * **/

                // TODO возможно можно убрать пару строк снизу
//                /** установим в GameManager значение наград (монеты и очки за уровень) **/
//                gameManager.setCoinsRewardforLevel(missionInfoScreen.getRewardCoinsForLevel());
//                gameManager.setScoreRewardforLevel(missionInfoScreen.getRewardScoreForLevel());

                missionInfoWindow.setData(levelIcons.get(selectedLevelId - 1).getData());
                missionInfoWindow.setVisible(true);
            }
        }
    }

    private void showRewardVideoWindow() {
        isFocused = false;
        isScreenShown = true;
        hideButtons();
        rewardVideoWindow.show();
    }

    /**
     * метод возвращает Id выбранного уровня
     **/
    public int getSelectedLevelId() {
        return selectedLevelId;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    @Override
    public void addChildOnOverlay(Actor actor) {
        addOverlayChild(actor);
    }

    // TODO исправить что то не так со значком апгрейда юнитов
    @Override
    public void startIconAction() {
        teamUpgradeIcon.addAction(Actions.fadeIn(0));
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                if (!isScreenShown) {
                    teamUpgradeIcon.addAction(Actions.fadeIn(0));
                } else {
                    teamUpgradeIcon.addAction(Actions.fadeOut(0));
                }
                return true;
            }
        };
        SequenceAction moveAction = new SequenceAction(Actions.fadeIn(0),
                Actions.fadeOut(0.8f),
                checkEndOfAction
        );

        SequenceAction sizeAction = new SequenceAction(
//                Actions.sizeTo(64, 64, 1f),
//                Actions.sizeTo(32, 32, 1f)

//                Actions.sizeBy(-8, -8, 0.2f)
                Actions.sizeBy(8, 8, 0.2f),
                Actions.sizeBy(-8, -8, 0.2f),
                Actions.fadeOut(0.6f)
        );
        teamUpgradeIcon.addAction(moveAction);
    }

    @Override
    public void showRoundCircle() {
        giftIcon.showRoundCircle();
    }

    @Override
    public void hideRoundCircle() {
        giftIcon.hideRoundCircle();
    }

    @Override
    public boolean isRoundCircleVisible() {
        return giftIcon.roundCircleIsVisible();
    }


    @Override
    public void showRewardVideoButton() {
        isShowRewardVideoButton = true;
        rewardVideoButton.setVisible(true);
        rewardVideoButton.show();
    }

    @Override
    public void hideRewardVideoButton() {
        isShowRewardVideoButton = false;
        rewardVideoButton.setVisible(false);
        rewardVideoButton.resetAction();
    }
}



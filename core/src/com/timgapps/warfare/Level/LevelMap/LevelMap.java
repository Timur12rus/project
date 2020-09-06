package com.timgapps.warfare.Level.LevelMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boontaran.MessageListener;
import com.boontaran.games.StageGame;
import com.boontaran.games.tiled.TileLayer;
import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
import com.timgapps.warfare.Level.GUI.Screens.MissionInfoScreen;
import com.timgapps.warfare.Level.GUI.Screens.gifts_window.GiftScreen;
import com.timgapps.warfare.Level.GUI.Screens.TeamUpgradeScreen;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Level.LevelMap.actions.CoinsAction;
import com.timgapps.warfare.Level.LevelMap.actions.ResourcesAction;
import com.timgapps.warfare.Level.LevelMap.actions.StartCoinsAction;
import com.timgapps.warfare.Level.LevelMap.actions.StartResourcesAction;
import com.timgapps.warfare.Level.LevelScreens.ColorRectangle;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class LevelMap extends StageGame implements StartCoinsAction, StartResourcesAction {
    // создаем несколько констант для создания callBack сообщений, которые будут передаваться в зависимости от нажатия кнопок
    public static final int ON_BACK = 1;
    public static final int ON_LEVEL_SELECTED = 2;
    public static final int ON_SHOW_ANIMATION = 3;
    public static final int ON_SHOW_REWARD_FOR_STARS_SCREEN = 4;
    public static final int ON_SHARE = 4;
    private int selectedLevelId = 1;
    private ArrayList<LevelIcon> levelIcons;
    private MissionInfoScreen missionInfoScreen;
    private TeamUpgradeScreen teamUpgradeScreen;
    private GiftScreen giftScreen;
    public static BitmapFont font40;
    private TeamUpgradeIcon teamUpgradeIcon;      // кнопка для вызова окна апгрейда юнитов
    //    private ImageButton upgradeTeamButton;      // кнопка для вызова окна апгрейда юнитов
    private GiftIcon giftIcon;
    private GameManager gameManager;
    private CoinsPanel coinsPanel;              // панель с монетами
    private ScorePanel scorePanel;              // панель с игровыми очками
    private StarsPanel starsPanel;              // панель с наградой за звезды
    private boolean isEndCoinsAction = false;
    private int coinsReward = 0;
    private int scoreReward = 0;

    private TiledMap map;
    private int mapWidth, mapHeight, tilePixelWidth, tilePixelHeight, levelWidth, levelHeight;
    private boolean isFocused = true;
    private Label teamLabel;
    private ColorRectangle fade;
    private float cameraXpos;
    private float cameraYpos;
    private ColorRectangle greenRectangle;
    private CoinsAction coinsAction;
    private ResourcesAction resourcesAction;

    public LevelMap(GameManager gameManager, int coinsReward, int scoreReward) {
        this.coinsReward = coinsReward;
        this.scoreReward = scoreReward;
//        coinsAction.setCoinsPosition(getWidth() / 2, getHeight() / 2);

//        setBackGround("map");
        this.gameManager = gameManager;
        levelIcons = gameManager.getLevelIcons();

//        new Color(0x35a1afff);
        greenRectangle = new ColorRectangle(0, 0, 1536, 1024, new Color(0x8eb353ff));
        addChild(greenRectangle);

        String directory = "location1";
        loadMap("tiled/" + directory + "/map.tmx");   // метод загружает карту и создает объекты

        fade = new ColorRectangle(0, 0, getWidth(), getHeight(), new Color(0, 0, 0, 0.7f));
        fade.setVisible(false);
        fade.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (teamUpgradeScreen.isUpgradeScreenVisible()) {
                    teamUpgradeScreen.setUpgradeScreenVisible(false);
                }
                resumeLevelMap();
            }
        });
        addChild(fade);

        /** создадим окно с описанием уровня **/
        missionInfoScreen = new MissionInfoScreen();
        missionInfoScreen.setVisible(false);
        addOverlayChild(missionInfoScreen);
//        addChild(missionInfoScreen);

        missionInfoScreen.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == missionInfoScreen.ON_RESUME) {
//                    Warfare.media.playSound("click.ogg");
//                    showButtons();
                    resumeLevelMap();
                } else if (message == missionInfoScreen.ON_START) { //
                    call(ON_LEVEL_SELECTED);
                }
            }
        });

        giftIcon = new GiftIcon(gameManager);
        giftIcon.setPosition(getWidth() - giftIcon.getWidth() - 32, getHeight() / 3);
//        giftIcon.setPosition(getWidth() - giftIcon.getWidth() - 32, getHeight() / 2);

        giftIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showGiftScreen();
            }
        });

        addOverlayChild(giftIcon);

        /** создадим окно с вознаграждениями **/
        giftScreen = new GiftScreen(this, gameManager);
        giftScreen.setVisible(false);
        addOverlayChild(giftScreen);
//        addChild(giftScreen);

        giftScreen.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == giftScreen.ON_RESUME) {
//                    Warfare.media.playSound("click.ogg");
                    resumeLevelMap();
                } else if (message == giftScreen.ON_SHOW_ANIMATIONS) {
                    showAddCoinsAnimation();
                }
//
            }
        });


//        upgradeTeamButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("teamButton")),
//                new TextureRegionDrawable(Warfare.atlas.findRegion("teamButtonDwn")));

//        addChild(upgradeTeamButton, 32, 340);
        teamUpgradeIcon = new TeamUpgradeIcon();
        teamUpgradeIcon.setPosition(32, getHeight() / 3);
//        teamUpgradeIcon.setPosition(32, getHeight() / 2);
        addOverlayChild(teamUpgradeIcon);

        Label.LabelStyle teamLabelStyle = new Label.LabelStyle();
        teamLabelStyle.fontColor = Color.WHITE;
        teamLabelStyle.font = Warfare.font10;
        teamLabel = new Label("Team", teamLabelStyle);
        teamLabel.setPosition(teamUpgradeIcon.getX() + (teamUpgradeIcon.getWidth() - teamLabel.getWidth()) / 2,
                teamUpgradeIcon.getY() - teamLabel.getHeight());
        addOverlayChild(teamLabel);

//        gameManager.setHelpStatus(gameManager.HELP_TEAM_UPGRADE);

        teamUpgradeIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showTeamUpgradeScreen();
            }
        });

        // добавим панель с монетами на экран
        coinsPanel = gameManager.getCoinsPanel();
        coinsPanel.setCoinsCount(gameManager.getCoinsCount());
        coinsPanel.addAction(Actions.fadeIn(0.1f));
//        if (coinsPanel.isVisible()) {
//            coinsPanel.setVisible(true);
//        }

        coinsPanel.setVisible(true);
        coinsPanel.setPosition(getWidth() - coinsPanel.getWidth() - 32, getHeight() - coinsPanel.getHeight() - 32);
        addOverlayChild(coinsPanel);

        // Добавим пенель с очками
        scorePanel = gameManager.getScorePanel();
        scorePanel.setPosition(32, getHeight() - scorePanel.getHeight() - 32);
        addOverlayChild(scorePanel);

        // Добавим панель с наградой за зведзды
        starsPanel = gameManager.getStarsPanel();
        starsPanel.setVisible(true);
        starsPanel.setPosition(32, scorePanel.getY() - starsPanel.getHeight());
        addOverlayChild(starsPanel);

        starsPanel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                coinsPanel.remove();
                call(ON_SHOW_REWARD_FOR_STARS_SCREEN);
            }
        });


//        gameManager.setHelpStatus(GameManager.HELP_TEAM_UPGRADE);

        int helpStatus = gameManager.getHelpStatus();     // получим статус обучалки
        checkHelpStatus(helpStatus);

//        if (helpStatus == GameManager.HELP_STARS_PANEL) {
//            starsPanel.showFinger();
//            gameManager.setHelpStatus(GameManager.HELP_TEAM_UPGRADE);
//        }
//
//        if (helpStatus == GameManager.HELP_TEAM_UPGRADE) {
//            teamUpgradeIcon.showFinger();
//            gameManager.setHelpStatus(GameManager.HELP_GET_GIFT);
//        }
//
//        if (helpStatus == GameManager.HELP_GET_GIFT) {
//            giftIcon.showFinger();
//        }

        /** создадим окно апргейда команды и передаём информацию о составе команды(manager)**/
        teamUpgradeScreen = new TeamUpgradeScreen(gameManager);
        teamUpgradeScreen.setVisible(false);
        addOverlayChild(teamUpgradeScreen);

        teamUpgradeScreen.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == teamUpgradeScreen.ON_RESUME) {
//                    Warfare.media.playSound("click.ogg");
                    resumeLevelMap();
                }
            }
        });


        // запустим анимацию  получения монет к общему кол-ву монет
        if (coinsReward > 0) {
            showAddCoinsAnimation();
            showAddScoreAnimation();

            //TODO нужно сделать сохранение в SavedGame() кол-во монет и очков
//            gameManager.setCoinsCount(coinsPanel.getCoinsCount());
//            gameManager.setScoreCount(scorePanel.getScoreCount());
//            gameManager.saveGame();
        }

        cameraXpos = camera.position.x;
        cameraYpos = camera.position.y;

//        addChild(giftIcon);
    }

    /**
     * метод для скрытия кнопок на экране
     **/
    private void hideButtons() {
        teamLabel.setVisible(false);
        starsPanel.setVisible(false);
        fade.setPosition(cameraXpos - camera.viewportWidth / 2, cameraYpos - camera.viewportHeight / 2);
        giftIcon.setVisible(false);
        teamUpgradeIcon.setVisible(false);
//        if (finger != null) {
//            finger.setVisible(false);
//        }
        fade.setVisible(true);
    }

    private void showButtons() {
        teamLabel.setVisible(true);
        starsPanel.setVisible(true);
        fade.setVisible(false);
        giftIcon.setVisible(true);
//        if (finger != null && gameManager.getHelpStatus() != GameManager.HELP_GET_GIFT) {
//            finger.setVisible(true);
//        }
        teamUpgradeIcon.setVisible(true);
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isFocused) {
            float x = Gdx.input.getDeltaX();
            float y = Gdx.input.getDeltaY();
            cameraXpos = camera.position.x;
            cameraYpos = camera.position.y;
//        System.out.println("camera.ViewportWidth = " + camera.viewportWidth);
//        System.out.println("xPos = " + xPos);
//        if (x - (camera.viewportWidth / 2) > 0) {
//            camera.translate(-x, y);
//        }

            if (cameraXpos - x < camera.viewportWidth / 2) {
                x = 0;
            }
            if (cameraXpos - x > (1536 - camera.viewportWidth / 2)) {
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

//            System.out.println("cameraPosition.y = " + camera.position.y);
//            System.out.println("cameraPosition.x = " + camera.position.x);
//        if (cameraXpos + x )
            camera.translate(-x, y);
            if (fade != null) {
                fade.setPosition(camera.position.x - camera.viewportWidth / 2,
                        camera.position.y - camera.viewportHeight / 2);
            }
//            System.out.println("cameraPosition.y = " + camera.position.y);
//            System.out.println("cameraPosition.x = " + camera.position.x);
        }
        return true;
    }

    private void createLevelIcons(MapObjects objects, String LayerName) {
        for (MapObject object : objects) {
            Rectangle rectangle;
            float x = object.getProperties().get("x", Float.class);
            float y = object.getProperties().get("y", Float.class);
            rectangle = new Rectangle(x, y, 32, 32);
            if (LayerName.equals("locations")) {
                int i = parseInt(object.getName()) - 1;
                if (i < levelIcons.size() - 1) {
                    levelIcons.get(i).addListener(iconListener);
                    addChild(levelIcons.get(i), rectangle.x - 16, rectangle.y - 32);
                }

//                    for (int i = 0; i < levelIcons.size(); i++) {
////            levelIcons.add(new LevelIcon(i + 1, true));
//                        levelIcons.get(i).addListener(iconListener);
////                new Troll(this, rectangle.x, rectangle.y);
////                new LevelIcon(parseInt(object.getName()), 25, 75, LevelIcon.EASY, true);
//                        addChild(new LevelIcon(parseInt(object.getName()),
//                                25, 75, LevelIcon.EASY, true), rectangle.x - 32, rectangle.y);
//
//                    }
            }
        }
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

        // сканируем все слои и получаем объекты
        for (MapLayer layer : map.getLayers()) {
            String name = layer.getName();

            if (name.equals("locations")) {
                createLevelIcons(layer.getObjects(), name);
            } else {
                TileLayer tLayer = new TileLayer(camera, map, name, stage.getBatch());
                addChild(tLayer);
            }
        }
    }

    public void setCoinsReward(int coinsCount) {
        coinsReward = coinsCount;
    }

    private void showTeamUpgradeScreen() {
        isFocused = false;
        hideButtons();

        teamUpgradeScreen.updateTeam();
        teamUpgradeScreen.updateCollection();
        teamUpgradeScreen.setVisible(true);

        teamUpgradeIcon.hideFinger();

        // TODO нужно исправить!!!!!!!!
        if (gameManager.getHelpStatus() == GameManager.HELP_TEAM_UPGRADE) {
            System.out.println("GIIIIIIIIIIIIIDE!!!!!!!!!!!");
            gameManager.setHelpStatus(GameManager.HELP_GET_GIFT);
            teamUpgradeIcon.hideFinger();
        }
    }

    private void showGiftScreen() {
        hideButtons();
        isFocused = false;
        giftScreen.setVisible(true);
    }

    /**
     * метод устанавливает фоновое изображение
     **/
    private void setBackGround(String region) {
        clearBackground();
        Image bg = new Image(Warfare.atlas.findRegion(region));
        addBackground(bg, false, false);
    }

    /**
     * метод для возврата к карте уровней для выбора уровня
     **/
    private void resumeLevelMap() {
        /** скрываем окно с описанием уровня **/
        missionInfoScreen.hide();
        teamUpgradeScreen.setVisible(false);
        giftScreen.setVisible(false);
        isFocused = true;
        showButtons();
        checkHelpStatus(gameManager.getHelpStatus());
//        teamUpgradeScreen.hide();
    }

    // TODO showAddCoinAnimation() !~!!!!!!!!!!!!!!

    private void checkHelpStatus(int helpStatus) {
        if (helpStatus == GameManager.HELP_STARS_PANEL) {
            starsPanel.showFinger();
            gameManager.setHelpStatus(GameManager.HELP_TEAM_UPGRADE);
            starsPanel.hideFinger();
        }

        if (helpStatus == GameManager.HELP_TEAM_UPGRADE) {
            teamUpgradeIcon.showFinger();
            gameManager.setHelpStatus(GameManager.HELP_GET_GIFT);
        }

        if (helpStatus == GameManager.HELP_GET_GIFT) {
            giftIcon.showFinger();
        }
    }


    private void showAddCoinsAnimation() {
        Image coinOne = new Image(Warfare.atlas.findRegion("coin_icon"));
        Image coinTwo = new Image(Warfare.atlas.findRegion("coin_icon"));
        Image coinThree = new Image(Warfare.atlas.findRegion("coin_icon"));

        // установим позицию для добавляемых монет, к которым будут применены action'ы
        coinOne.setPosition(getWidth() / 2, getHeight() / 2);
        coinTwo.setPosition(getWidth() / 2, getHeight() / 2);
        coinThree.setPosition(getWidth() / 2, getHeight() / 2);

        addOverlayChild(coinOne);
        addOverlayChild(coinTwo);
        addOverlayChild(coinThree);

        float endXPos = coinsPanel.getX() + coinsPanel.getWidth() / 2;
        float endYPos = coinsPanel.getY();

        // action проверки завершения действия
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
//                isEndCoinsAction = true;
                // добавим к общему кол-ву монет монеты (награду)
                coinsPanel.addCoins(coinsReward);
                return true;
            }
        };

        // action для первой монеты
        SequenceAction moveActionCoinOne = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(getWidth() / 2 - 32, getHeight() / 2 - 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0)
        );

        // action для второй монеты
        SequenceAction moveActionCoinTwo = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(getWidth() / 2 + 32, getHeight() / 2 - 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0)
        );

        // action для третьей монеты
        SequenceAction moveActionCoinThree = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(getWidth() / 2 + 32, getHeight() / 2 + 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0),
                checkEndOfAction
        );

        coinOne.addAction(moveActionCoinOne);
        coinTwo.addAction(moveActionCoinTwo);
        coinThree.addAction(moveActionCoinThree);
    }

    /**
     * метод для запуска анимации добавления очков
     **/
    private void showAddScoreAnimation() {
        Image scoreOne = new Image(Warfare.atlas.findRegion("score_icon"));
        Image scoreTwo = new Image(Warfare.atlas.findRegion("score_icon"));
        Image scoreThree = new Image(Warfare.atlas.findRegion("score_icon"));

        // установим позицию для добавляемых монет, к которым будут применены action'ы
        scoreOne.setPosition(getWidth() / 2, getHeight() / 2);
        scoreTwo.setPosition(getWidth() / 2, getHeight() / 2);
        scoreThree.setPosition(getWidth() / 2, getHeight() / 2);

        addOverlayChild(scoreOne);
        addOverlayChild(scoreTwo);
        addOverlayChild(scoreThree);

        float endXPos = scorePanel.getX() + scorePanel.getWidth() / 3;
        float endYPos = scorePanel.getY();

        // action проверки завершения действия
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
//                isEndCoinsAction = true;
                // добавим к общему кол-ву монет монеты (награду)
                scorePanel.addScore(scoreReward);
                return true;
            }
        };

        // action для первого значка
        SequenceAction moveActionCoinOne = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(getWidth() / 2 - 64, getHeight() / 2 - 64, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0)
        );

        // action для второго значка
        SequenceAction moveActionCoinTwo = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(getWidth() / 2 - 16, getHeight() / 2 + 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0)
        );

        // action для третьего значка
        SequenceAction moveActionCoinThree = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(getWidth() / 2 + 32, getHeight() / 2 + 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0),
                checkEndOfAction
        );

        scoreOne.addAction(moveActionCoinOne);
        scoreTwo.addAction(moveActionCoinTwo);
        scoreThree.addAction(moveActionCoinThree);
    }


    /**
     * слушатель, назначаем его значкам уровней на нашей карте уровней
     **/
    private ClickListener iconListener = new ClickListener() {
        // при клике мы будем сохранять в переменную полученный id нажатого уровня

        @Override
        public void clicked(InputEvent event, float x, float y) {
            //будем воспроизводить звук щелчка и передавать в callBack сообщенме onLevelSelected
            selectedLevelId = ((LevelIcon) event.getTarget()).getId();

            // отобразим окно с информацией о выбранном уровне
            if (levelIcons.get(selectedLevelId - 1).getData().isActiveIcon())
                showMissionInfo(selectedLevelId);

//            /** установим в GameManager значение наград (монеты и очки за уровень) **/
            gameManager.setCoinsRewardforLevel(missionInfoScreen.getRewardCoinsForLevel());
            gameManager.setScoreRewardforLevel(missionInfoScreen.getRewardScoreForLevel());
        }
    };

    private void setGiftPrepared(boolean flag) {
        giftIcon.setVisibleRoundCirlce(flag);
    }

    public GiftIcon getGiftIcon() {
        return giftIcon;
    }


    private void showMissionInfo(int selectedLevelId) {
        isFocused = false;
        hideButtons();
        if (levelIcons.get(selectedLevelId - 1).getData().isActiveIcon()) {     // проверяем, активен ли уровень,
            if (!missionInfoScreen.isVisible()) {                               // если да, показываем окно с информацией об уровне
                /** передадим данные об уровне в объект экрана информации об уровне
                 * здесь "selectedLevelId - 1", потому что levelIcons.get(..) возвращает элемент списка с индексом на 1 меньше,
                 * т.к. нумеруется с "0"
                 * **/

                // TODO возможно можно убрать пару строк снизу
//                /** установим в GameManager значение наград (монеты и очки за уровень) **/
//                gameManager.setCoinsRewardforLevel(missionInfoScreen.getRewardCoinsForLevel());
//                gameManager.setScoreRewardforLevel(missionInfoScreen.getRewardScoreForLevel());

                missionInfoScreen.setData(levelIcons.get(selectedLevelId - 1).getData());
                missionInfoScreen.setVisible(true);
            }
        }
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

    public TeamUpgradeIcon getTeamUpgradeIcon() {
        return teamUpgradeIcon;
    }

    @Override
    public void startCoinsAction() {
        coinsAction = new CoinsAction();
        coinsAction.setStartPosition(Warfare.V_WIDTH / 2 - 160, Warfare.V_HEIGHT / 2);
        coinsAction.setEndPosition(coinsPanel.getX(), coinsPanel.getY());
        coinsAction.start();
        addOverlayChild(coinsAction);
    }

    @Override
    public void startResourcesAction(int resourcesCount) {
        resourcesAction = new ResourcesAction(gameManager, resourcesCount);
        if (resourcesCount == 1) {
            resourcesAction.setStartPosition(Warfare.V_WIDTH / 2 - 160, Warfare.V_HEIGHT / 2);
        } else if (resourcesCount == 2) {
            resourcesAction.setStartPosition(Warfare.V_WIDTH / 2 + 80, Warfare.V_HEIGHT / 2);
        }
        resourcesAction.setEndPosition(teamUpgradeIcon.getX(), teamUpgradeIcon.getY());
        resourcesAction.start();
        addOverlayChild(resourcesAction);
    }

    @Override
    public boolean isEndResourcesAction() {
        if (resourcesAction != null) {
            return resourcesAction.isEndResourcesAction();
        }
        return false;
    }

    @Override
    public void setEndResourcesAction() {
        resourcesAction.setEndResourcesAction();
    }

    @Override
    public boolean isEndCoinsAction() {
        if (coinsAction != null) {
            return coinsAction.isEndCoinsAction();
        }
        return false;
    }

    @Override
    public void setEndCoinsAction() {
        coinsAction.setEndCoinsAction();
    }
}



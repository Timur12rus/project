package com.timgapps.warfare.Level.LevelMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boontaran.MessageListener;
import com.boontaran.games.StageGame;
import com.boontaran.games.tiled.TileLayer;
import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
import com.timgapps.warfare.Level.GUI.Screens.MissionInfoScreen;
import com.timgapps.warfare.Level.GUI.Screens.GiftsWindow.GiftScreen;
import com.timgapps.warfare.Level.GUI.Screens.TeamUpgradeScreen;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class LevelMap extends StageGame {
    // создаем несколько констант для создания callBack сообщений, которые будут передаваться в зависимости от нажатия кнопок
    public static final int ON_BACK = 1;
    public static final int ON_LEVEL_SELECTED = 2;
    public static final int ON_SHOW_ANIMATION = 3;

    public static final int ON_SHOW_TEAM_UPGRADE = 3;
    public static final int ON_SHARE = 4;

    private Group container;
    private int selectedLevelId = 1;
    private ArrayList<LevelIcon> levelIcons;

    private Viewport mViewport;
    private OrthographicCamera mOrthographicCamera;

    private MissionInfoScreen missionInfoScreen;
    private TeamUpgradeScreen teamUpgradeScreen;
    private GiftScreen giftScreen;
    private Group parent;
    public static BitmapFont font40;
    private ImageButton upgradeTeamButton;      // кнопка для вызова окна апгрейда юнитов
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

    public LevelMap(GameManager gameManager, int coinsReward, int scoreReward) {
        this.coinsReward = coinsReward;
        this.scoreReward = scoreReward;

//        setBackGround("map");
        this.gameManager = gameManager;
        levelIcons = gameManager.getLevelIcons();

        String directory = "location1";
        loadMap("tiled/" + directory + "/map.tmx");   // метод загружает карту и создает объекты

//        for (int i = 0; i < levelIcons.size(); i++) {
////            levelIcons.add(new LevelIcon(i + 1, true));
//            levelIcons.get(i).addListener(iconListener);
//        }


//        addChild(levelIcons.get(0), 244, 56);
//        addChild(levelIcons.get(1), 320, 210);
//        addChild(levelIcons.get(2), 484, 280);
//        addChild(levelIcons.get(3), 640, 210);
//        addChild(levelIcons.get(4), 800, 130);

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
                    resumeLevelMap();
                } else if (message == missionInfoScreen.ON_START) { //
                    call(ON_LEVEL_SELECTED);
                }
            }
        });

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

        /** создадим окно апргейда команды и передаём информацию о составе команды(manager)**/
        teamUpgradeScreen = new TeamUpgradeScreen(gameManager);
        teamUpgradeScreen.setVisible(false);
        addOverlayChild(teamUpgradeScreen);
//        addChild(teamUpgradeScreen);

        upgradeTeamButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("upgradeTeamBtn")),
                new TextureRegionDrawable(Warfare.atlas.findRegion("upgradeTeamBtn")));
//        addChild(upgradeTeamButton, 32, 340);
        upgradeTeamButton.setPosition(32, 340);
        addOverlayChild(upgradeTeamButton);
//        addChild(upgradeTeamButton, 32, 340);

        upgradeTeamButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showTeamUpgradeScreen();
            }
        });

        teamUpgradeScreen.addListener(new MessageListener() {
            @Override
            protected void receivedMessage(int message, Actor actor) {
                if (message == teamUpgradeScreen.ON_RESUME) {
//                    Warfare.media.playSound("click.ogg");
                    resumeLevelMap();
                }
            }
        });

        // добавим панель с монетами на экран
        coinsPanel = gameManager.getCoinsPanel();
        coinsPanel.setPosition(getWidth() - coinsPanel.getWidth() - 32, getHeight() - coinsPanel.getHeight() - 32);
        addOverlayChild(coinsPanel);

        // Добавим пенель с очками
        scorePanel = gameManager.getScorePanel();
        scorePanel.setPosition(32, getHeight() - scorePanel.getHeight() - 32);
        addOverlayChild(scorePanel);

        // Добавим панель с наградой за зведзды
        starsPanel = gameManager.getStarsPanel();
        starsPanel.setPosition(32, scorePanel.getY() - 4 - starsPanel.getHeight());
        addOverlayChild(starsPanel);


//        addChild(coinsPanel, getWidth() - coinsPanel.getWidth() - 32, getHeight() - coinsPanel.getHeight() - 32);

        // запустим анимацию  получения монет к общему кол-ву монет
        if (coinsReward > 0) {
            showAddCoinsAnimation();
            showAddScoreAnimation();

            //TODO нужно сделать сохранение в SavedGame() кол-во монет и очков
//            gameManager.setCoinsCount(coinsPanel.getCoinsCount());
//            gameManager.setScoreCount(scorePanel.getScoreCount());
//            gameManager.saveGame();
        }

        giftIcon = new GiftIcon();
        giftIcon.setPosition(getWidth() - giftIcon.getWidth() - 32, getHeight() / 2);

        giftIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showGiftScreen();
            }
        });

        addOverlayChild(giftIcon);
//        addChild(giftIcon);
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isFocused) {
            float x = Gdx.input.getDeltaX();
            float y = Gdx.input.getDeltaY();
            float cameraXpos = camera.position.x;
            float cameraYpos = camera.position.y;
//        System.out.println("camera.ViewportWidth = " + camera.viewportWidth);
//        System.out.println("xPos = " + xPos);
//        if (x - (camera.viewportWidth / 2) > 0) {
//            camera.translate(-x, y);
//        }

            if (cameraXpos - x < camera.viewportWidth / 2) {
                x = 0;
            }
            if (cameraXpos - x > camera.viewportWidth + 320) {
                x = 0;
            }

            if (cameraYpos + y < camera.viewportHeight / 2) {
                y = 0;
            }
            if (cameraYpos + y > camera.viewportHeight / 2 + 300) {
                y = 0;
            }

            System.out.println("cameraPosition.y + y = " + camera.position.y + y);
//        System.out.println("cameraPosition.x - x = " + cameraXpos - x);
//        if (cameraXpos + x )
            camera.translate(-x, y);
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
        teamUpgradeScreen.setVisible(true);
    }

    private void showGiftScreen() {
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
//        teamUpgradeScreen.hide();
    }

    // TODO showAddCoinAnimation() !~!!!!!!!!!!!!!!

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
            showMissionInfo(selectedLevelId);

//            /** установим в GameManager значение наград (монеты и очки за уровень) **/
            gameManager.setCoinsRewardforLevel(missionInfoScreen.getRewardCoinsForLevel());
            gameManager.setScoreRewardforLevel(missionInfoScreen.getRewardScoreForLevel());
        }
    };


    private void showMissionInfo(int selectedLevelId) {
        isFocused = false;
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

}

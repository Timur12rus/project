package com.timgapps.warfare.Level.LevelMap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
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
import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
import com.timgapps.warfare.Level.GUI.Screens.MissionInfoScreen;
import com.timgapps.warfare.Level.GUI.Screens.TeamUpgradeScreen;
import com.timgapps.warfare.Level.GUI.TextManager;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class LevelMap extends StageGame {
    // создаем несколько констант для создания callBack сообщений, которые будут передаваться в зависимости от нажатия кнопок
    public static final int ON_BACK = 1;
    public static final int ON_LEVEL_SELECTED = 2;
    public static final int ON_SHOW_TEAM_UPGRADE = 3;
    public static final int ON_SHARE = 4;

    private Group container;
    private int selectedLevelId = 1;
    private ArrayList<LevelIcon> levelIcons;

    private Viewport mViewport;
    private OrthographicCamera mOrthographicCamera;

    private MissionInfoScreen missionInfoScreen;
    private TeamUpgradeScreen teamUpgradeScreen;
//    private TeamUpgradeScreen teamUpgradeScreen;

    private Group parent;

    private TextManager textManager;

    public static BitmapFont font40;

    private ImageButton upgradeTeamButton;      // кнопка для вызова окна апгрейда юнитов

    private GameManager gameManager;

    private CoinsPanel coinsPanel;              // панель с монетами

    private boolean isEndCoinsAction = false;

    public LevelMap(final GameManager gameManager) {
        setBackGround("map");
        this.gameManager = gameManager;
        levelIcons = new ArrayList<LevelIcon>();

        for (int i = 0; i < 10; i++) {
            levelIcons.add(new LevelIcon(i + 1, true));
            levelIcons.get(i).addListener(iconListener);
//            System.out.println("i = " + i + " id = " + levelIcons.get(i).getId());
        }


        addChild(levelIcons.get(0), 244, 56);
        addChild(levelIcons.get(1), 320, 210);
        addChild(levelIcons.get(2), 484, 280);
        addChild(levelIcons.get(3), 640, 210);
        addChild(levelIcons.get(4), 800, 130);

        /** создадим окно с описанием уровня **/

        missionInfoScreen = new MissionInfoScreen();
        missionInfoScreen.setVisible(false);
        addChild(missionInfoScreen);

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

        /** создадим окно апргейда команды и передаём информацию о составе команды**/
        teamUpgradeScreen = new TeamUpgradeScreen(gameManager);
//        teamUpgradeScreen = new TeamUpgradeScreen(gameManager.getTeam());
//        teamUpgradeScreen = new TeamUpgradeScreen();
        teamUpgradeScreen.setVisible(false);
        addChild(teamUpgradeScreen);

        upgradeTeamButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("upgradeTeamBtn")),
                new TextureRegionDrawable(Warfare.atlas.findRegion("upgradeTeamBtn")));
        addChild(upgradeTeamButton, 32, 340);

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
//                else if (message == missionInfoScreen.ON_START) { //
//                    call(ON_LEVEL_SELECTED);
//                }
            }
        });

        coinsPanel = gameManager.getCoinsPanel();
//        coinsPanel = new CoinsPanel(gameManager.getCoinsCount());
        addChild(coinsPanel, getWidth() - coinsPanel.getWidth() - 32, getHeight() - coinsPanel.getHeight() - 32);

        showAddCoinsAnimation();
    }

    private void showTeamUpgradeScreen() {
        teamUpgradeScreen.setVisible(true);
//        System.out.println("SHOW!");
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
        teamUpgradeScreen.hide();
    }


    // TODO showAddCoinAnimation() !~!!!!!!!!!!!!!!

    private void showAddCoinsAnimation() {
        Image coinOne = new Image(Warfare.atlas.findRegion("coin_icon"));
        Image coinTwo = new Image(Warfare.atlas.findRegion("coin_icon"));
        Image coinThree = new Image(Warfare.atlas.findRegion("coin_icon"));


        coinOne.setPosition(getWidth() / 2, getHeight() / 2);
        coinTwo.setPosition(getWidth() / 2, getHeight() / 2);
        coinThree.setPosition(getWidth() / 2, getHeight() / 2);

        addChild(coinOne);
        addChild(coinTwo);
        addChild(coinThree);

        float endXPos = coinsPanel.getX() + coinsPanel.getWidth() / 2;
        float endYPos = coinsPanel.getY();

        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                isEndCoinsAction = true;
                coinsPanel.addCoins(50);
                return true;
            }
        };


        SequenceAction moveActionCoinOne = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(getWidth() / 2 - 32, getHeight() / 2 - 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 1.5f),
                Actions.fadeOut(0)
                );

        SequenceAction moveActionCoinTwo = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(getWidth() / 2 + 32, getHeight() / 2 - 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 1.5f),
                Actions.fadeOut(0)
        );

        SequenceAction moveActionCoinThree = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(getWidth() / 2 + 32, getHeight() / 2 + 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 1.5f),
                Actions.fadeOut(0), checkEndOfAction
        );

        coinOne.addAction(moveActionCoinOne);
        coinTwo.addAction(moveActionCoinTwo);
        coinThree.addAction(moveActionCoinThree);
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
//            System.out.println("selectedLevelId = " + selectedLevelId);
//            call(ON_LEVEL_SELECTED);
            showMissionInfo(selectedLevelId);

            /** установим в GameManager значение наград (монеты и очки за уровень) **/
            gameManager.setCoinsRewardforLevel(missionInfoScreen.getRewardCoinsForLevel());
            gameManager.setScoreRewardforLevel(missionInfoScreen.getRewardScoreForLevel());
        }
    };


    private void showMissionInfo(int selectedLevelId) {
//        System.out.println("showMission Info");
        if (!missionInfoScreen.isVisible()) {
            /** передадим данные об уровне в объект экрана информации об уровне
             * здесь "selectedLevelId - 1", потому что levelIcons.get(..) возвращает элемент списка с индексом на 1 меньше,
             * т.к. нумеруется с "0"
             * **/
            missionInfoScreen.setData(levelIcons.get(selectedLevelId - 1).getData());
            missionInfoScreen.setVisible(true);
        }
    }

    /**
     * метод возвращает Id выбранного уровня
     **/
    public int getSelectedLevelId() {
        return selectedLevelId;
    }

}

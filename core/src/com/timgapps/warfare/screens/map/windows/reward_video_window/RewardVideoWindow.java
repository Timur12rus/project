package com.timgapps.warfare.screens.map.windows.reward_video_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.Utils.StringHolder;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.map.MapScreen;
import com.timgapps.warfare.screens.map.interfaces.RewardedVideoAdListener;
import com.timgapps.warfare.screens.map.win_creator.ConstructedWindow;
import com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements.ColorButton;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.hide;

public class RewardVideoWindow extends Group {
    private ConstructedWindow constructedWindow;
    private ImageButton closeButton;
    private ColorButton watchButton;
    private Image speachBalloon;
    private Label speachLabel, rewardLabel, coinsCountLabel, loadLabel, internetConnectionErrorLabel;
    private int coinsCount = 50;
    public static final int ON_REWARD = 1;
    public static final int ON_RESUME = 2;
    private final int LOADING = 1;
    private final int LOADED = 2;
    private final int SHOWN = 3;
    private final int ERROR = 4;
    private int videoStatus;
    private RewardedVideoAdListener rewardedVideoAdListener;
    private boolean isShown;
    private Table rewardTable;
    private MapScreen mapScreen;
    private boolean adMobInitializing;      // происходит ли инициализация adMob

    public RewardVideoWindow(MapScreen mapScreen, RewardedVideoAdListener rewardedVideoAdListener) {
        this.mapScreen = mapScreen;
        createWindow();
        this.rewardedVideoAdListener = rewardedVideoAdListener;
    }

    public void showRewardVideo() {
        watchButton.setVisible(false);
        videoStatus = 0;
        if (isOnline()) {
            if (rewardedVideoAdListener.isLoaded()) {
                hide();
                mapScreen.resumeLevelMap();
                fire(new MessageEvent(ON_REWARD));
                rewardedVideoAdListener.resetIsLoaded();
                videoStatus = SHOWN;
            } else {
                if (videoStatus != LOADING) {
                    videoStatus = LOADING;
                    showLoading();
                    loadRewardVideo();
                    rewardedVideoAdListener.loadRewardedVideoAd();
                }
//                if (videoStatus != LOADING) {
//                    if (!rewardedVideoAdListener.isInitializationComplete()) {
//                        rewardedVideoAdListener.initializeAdmob();
//                    }
//                    showLoading();
//                    videoStatus = LOADING;
//                    loadRewardVideo();
//                } else {
//                    // если при загрузке получена ошибка, выводим ошибку
//                    if (rewardedVideoAdListener.isError()) {
//                        showErrorConnection();
//                        videoStatus = ERROR;
//                    } else {
//                        showLoading();
//                        if (!rewardedVideoAdListener.isInitializationComplete()) {
//                            rewardedVideoAdListener.initializeAdmob();
//                        }
//                    }
//                }
            }
        } else {
            showErrorConnection();
            internetConnectionErrorLabel.setVisible(true);
        }
        // проверяем соединение с интренетом
        // если интернет подключен
        // сделана ли инициализайия Admob
        // делаем инициализацию AdMob
        // если реклама загружена
        // показваем рекламу
        // else загружаем рекламу (videoStatus = LOADING)
        // else выводим сообщение "интернет не подключен!" (videoStatus = ERROR)

//        fire(new MessageEvent(ON_REWARD));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (videoStatus == LOADING) {
            if (rewardedVideoAdListener.isInitializationComplete()) {   // если admob инициализирован
                adMobInitializing = false;
                if (rewardedVideoAdListener.isLoaded()) {               // если реклама загружена
                    if (videoStatus != LOADED) {
                        videoStatus = LOADED;
                        showRewardVideo();
                    }
                } else {
                    if (rewardedVideoAdListener.isError()) {
                        if (videoStatus != ERROR) {
                            videoStatus = ERROR;
                            showErrorConnection();
                        }
                    }
                }
            } else {
                if (!adMobInitializing) {
                    rewardedVideoAdListener.initializeAdmob();
                    adMobInitializing = true;
                }
            }
//            if (rewardedVideoAdListener.isLoaded()) {
//                videoStatus = LOADED;
//                loadLabel.setVisible(false);
//                if (isShown) {
//                    showRewardVideo();
//                }
//            } else {
//                if (rewardedVideoAdListener.isError()) {
//                    videoStatus = ERROR;
//                    showErrorConnection();
//                }
//            }
        }
    }

    public void showLoading() {
        internetConnectionErrorLabel.setVisible(false);
        speachLabel.setVisible(false);
        rewardTable.setVisible(false);
        watchButton.setVisible(false);
        loadLabel.setVisible(true);
    }

    public void showErrorConnection() {
        internetConnectionErrorLabel.setVisible(true);
        speachLabel.setVisible(false);
        rewardTable.setVisible(false);
        watchButton.setVisible(false);
        loadLabel.setVisible(false);
    }

    public void loadRewardVideo() {
        loadLabel.setVisible(true);
        speachLabel.setVisible(false);
        rewardTable.setVisible(false);
        watchButton.setVisible(false);
        internetConnectionErrorLabel.setVisible(false);
    }

    public void show() {
        loadLabel.setVisible(false);
        internetConnectionErrorLabel.setVisible(false);
        speachLabel.setVisible(true);
        rewardTable.setVisible(true);
        watchButton.setVisible(true);
        setVisible(true);
        isShown = true;
    }

    public void hide() {
//        mapScreen.resumeLevelMap();
        setVisible(false);
        loadLabel.setVisible(false);
        internetConnectionErrorLabel.setVisible(false);
        speachLabel.setVisible(true);
        rewardTable.setVisible(true);
        watchButton.setVisible(true);
        isShown = false;
    }

    private void createWindow() {
        constructedWindow = new ConstructedWindow(610, 350, "");
        setSize(constructedWindow.getWidth(), constructedWindow.getHeight());

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.GRAY;
        labelStyle.font = Warfare.font30;

        speachLabel = new Label("", labelStyle);
        speachLabel.setText(Warfare.stringHolder.getString(StringHolder.SPEACH_VIDEO_REWARD) + ":");

        rewardLabel = new Label("", labelStyle);
        rewardLabel.setText(Warfare.stringHolder.getString(StringHolder.REWARD) + ":");
        rewardLabel.setPosition(getWidth() / 2 - rewardLabel.getWidth() / 2, getHeight() / 2);

        loadLabel = new Label(Warfare.stringHolder.getString(StringHolder.LOADING), labelStyle);

        internetConnectionErrorLabel = new Label(Warfare.stringHolder.getString(StringHolder.CONNECTION_ERROR), labelStyle);


        Label.LabelStyle coinsCountLabelStyle = new Label.LabelStyle();
        coinsCountLabelStyle.fontColor = Color.ORANGE;
        coinsCountLabelStyle.font = Warfare.font30;
        coinsCountLabel = new Label("50", coinsCountLabelStyle);
        coinsCountLabel.setText("" + coinsCount);

        rewardTable = new Table();
        Image coinImage = new Image(Warfare.atlas.findRegion("coin_icon"));
        rewardTable.add(coinsCountLabel);
        rewardTable.add(coinImage).width(48).height(48).padLeft(2);

        speachLabel = new Label("" + Warfare.stringHolder.getString(StringHolder.SPEACH_VIDEO_REWARD), labelStyle);
        watchButton = new ColorButton(Warfare.stringHolder.getString(StringHolder.WATCH), ColorButton.GREEN_BUTTON);
        speachLabel.setPosition((getWidth() - speachLabel.getWidth()) / 2, (getHeight() - speachLabel.getHeight()) / 2 + 48);
        rewardTable.setPosition((getWidth() - rewardTable.getWidth()) / 2, speachLabel.getY() - 32);
        watchButton.setPosition((getWidth() - watchButton.getWidth()) / 2, rewardTable.getY() - rewardTable.getHeight() - 48 - watchButton.getHeight());
        loadLabel.setPosition((getWidth() - loadLabel.getWidth()) / 2, speachLabel.getY() - 32);
        internetConnectionErrorLabel.setPosition((getWidth() - internetConnectionErrorLabel.getWidth()) / 2,
                speachLabel.getY() - 32);
        hide();

//        addActor(speachBalloon);
        addActor(constructedWindow);
        addActor(speachLabel);
        addActor(rewardTable);
        addActor(loadLabel);
        addActor(internetConnectionErrorLabel);
        addActor(watchButton);

        // кнопка "ЗАКРЫТЬ" для закрытия окна с информацией о миссии
        closeButton = constructedWindow.getCloseButton();
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                fire(new MessageEvent(ON_RESUME));
            }
        });

        watchButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                hide();
                showRewardVideo();
            }
        });
    }

    // метод проевреят соедниенение с интренетом
    private boolean isOnline() {
        return rewardedVideoAdListener.isOnline();
    }
}

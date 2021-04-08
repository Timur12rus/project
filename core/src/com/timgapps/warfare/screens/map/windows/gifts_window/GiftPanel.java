package com.timgapps.warfare.screens.map.windows.gifts_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.screens.map.gifts_panel.gui_elements.BoxImage;
import com.timgapps.warfare.screens.map.gui_elements.CoinsPanel;
import com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements.ColorButton;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.screens.map.gui_elements.GiftIcon;
import com.timgapps.warfare.screens.map.MapScreen;
import com.timgapps.warfare.Warfare;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
//
//class GiftPanel extends Group {
//    private Image background;
//    private Label timeLabel;
//    private GiftRewardTable rewardTable;
//    private ColorButton claimButton;
//    private BoxImage boxImage;
//    private SimpleDateFormat formatForDate;
//    private Date date;
//    public static final int RESOURCES_GIFT = 1;
//    public static final int RESOURCE_AND_COINS_GIFT = 2;
//    public static final int BUFFS_GIFT = 3;
//    //    private final long DELTA_TIME_FIRST = 300000L;
////    private final long DELTA_TIME_SECOND = 600000L;
//    private final long DELTA_TIME_FIRST = 5000L;
//    private final long DELTA_TIME_SECOND = 3000L;
//    private final int COINS_COUNT_REWARD = 55;
//    private long deltaTime;
//    private long giftTime;          // время в мсек до получения подарка
//    private int giftsType;
//    private boolean isEndCoinsAction = false;
//    private CoinsPanel coinsPanel;
//    private GameManager gameManager;
//    private GiftIcon giftIcon;
//
//    // панель с горизонтальной таблицей GiftRewardTable и кнопкой "ПОЛУЧИТЬ"
//    public GiftPanel(MapScreen mapScreen, float x, float y, GameManager gameManager, int giftsType) {
//        this.giftIcon = mapScreen.getGiftIcon();
//        this.giftsType = giftsType;
//        this.gameManager = gameManager;
//        date = new Date();      // получим текущее время
//        coinsPanel = gameManager.getCoinsPanel();
//        formatForDate = new SimpleDateFormat("HH:mm:ss");
//        formatForDate.setTimeZone(TimeZone.getTimeZone("GMT"));
//        background = new Image(Warfare.atlas.findRegion("gifts_bg"));
//        boxImage = new BoxImage();
//        claimButton = new ColorButton("Claim", ColorButton.YELLOW_BUTTON);
//
//        // кнопка "ПОЛУЧИТЬ"
//        claimButton.setVisible(false);
//        Label.LabelStyle timeLabelStyle = new Label.LabelStyle();
//        timeLabelStyle.fontColor = Color.LIGHT_GRAY;
//        timeLabelStyle.font = Warfare.font20;
//
//        // задаём кол-во времени, которое нужно для ожидания подарка ресурсов
//        if (giftsType == RESOURCE_AND_COINS_GIFT) {
//            giftTime = gameManager.getGiftTimeFirst();
//            deltaTime = DELTA_TIME_FIRST;
////            giftTime = date.getTime() + DELTA_TIME;
//        }
//
//        // задаём кол-во времени, которое нужно для ожидания подарка баффов
//        if (giftsType == RESOURCES_GIFT) {
//            giftTime = gameManager.getGiftTimeSecond();
//            deltaTime = DELTA_TIME_SECOND;
////            giftTime = date.getTime() + DELTA_TIME;
//        }
//        // надпись кол-ва оставшегося времени для подарка
//        timeLabel = new Label("" + formatForDate.format(giftTime - date.getTime()), timeLabelStyle);              // текст оставшееся время в формате времени
//        if (giftTime - date.getTime() < 0) {
//            timeLabel.setVisible(false);
//        } else {
//            timeLabel.setVisible(true);
//        }
//        /** таблица с вознаграждениями (две панели) **/
//        if (giftsType == RESOURCES_GIFT) {
//            rewardTable = new GiftRewardTable(0, 2);
//        }
//        if (giftsType == RESOURCE_AND_COINS_GIFT) {
//            rewardTable = new GiftRewardTable(55, 1);
//        }
//        addActor(claimButton);
//        background.setPosition(claimButton.getX(), claimButton.getY() + 16 + claimButton.getHeight());
//        addActor(background);
//        timeLabel.setPosition((background.getWidth() - timeLabel.getWidth()) / 2,
//                background.getY() + background.getHeight() - 8 - timeLabel.getHeight());
//        addActor(timeLabel);
//        rewardTable.setPosition((background.getWidth() - rewardTable.getWidth()) / 2,
//                timeLabel.getY() - 32);
//        addActor(rewardTable);
//        boxImage.setPosition((background.getWidth() - boxImage.getWidth()) / 2,
//                background.getY() + 16);
//        addActor(boxImage);
//        claimButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                boxImage.startAnimation();
//                showAddGiftsAnimation();
//            }
//        });
//    }
//
//    private void showAddGiftsAnimation() {
//        if (gameManager.getHelpStatus() == GameManager.HELP_GET_GIFT) {
//            gameManager.setHelpStatus(GameManager.NONE);
//            giftIcon.hideFinger();
//        }
//        giftTime = new Date().getTime() + deltaTime;       // вычислим время необходимое для получения подарка, текущ время + DELTA_TIME
//        if (giftsType == RESOURCE_AND_COINS_GIFT) {
//            gameManager.addCoinsCount(COINS_COUNT_REWARD);
//
//            gameManager.setGiftTimeFirst(giftTime);      // сохраним значение текщего времени для получения подарка
//            giftIcon.setGiftTimeFirst(giftTime);
//        }
//        if (giftsType == RESOURCES_GIFT) {
//            gameManager.setGiftTimeSecond(giftTime);      // сохраним значение текщего времени для получения подарка
//            giftIcon.setGiftTimeSecond(giftTime);
//        }
//        showAddCoinsAnimation();
//        hideClaimButton();
//    }
//
//    private void hideClaimButton() {
//        claimButton.setVisible(false);
//        rewardTable.setVisible(false);
//        timeLabel.setVisible(false);
//    }
//
//    private void showAddCoinsAnimation() {
//        if (giftsType == RESOURCE_AND_COINS_GIFT) {
//            startCoinsAction.startCoinsAction();
//            startResourcesAction.startResourcesAction(1);
//        }
//        if (giftsType == RESOURCES_GIFT) {
//            startResourcesAction.startResourcesAction(2);
//        }
//    }
//
//    @Override
//    public float getWidth() {
//        return background.getWidth();
//    }
//
//    @Override
//    public float getHeight() {
//        return background.getHeight() + 16 + claimButton.getHeight();
//    }
//
//    @Override
//    public void act(float delta) {
//        if (startCoinsAction != null && startCoinsAction.isEndCoinsAction() && giftsType == RESOURCE_AND_COINS_GIFT) {
//            rewardTable.setVisible(true);
////          giftTime = new Date().getTime() + 10000;
//            timeLabel.setVisible(true);
//            boxImage.close();
//            // добавим к общему кол-ву монет монеты (награду)
//            coinsPanel.redraw();
////            coinsPanel.addCoins(COINS_COUNT_REWARD);
////            coinsPanel.startAddCoinsAction();
//            startCoinsAction.setEndCoinsAction();
//        }
//        if (startResourcesAction != null && startResourcesAction.isEndResourcesAction() && giftsType == RESOURCES_GIFT) {
//            rewardTable.setVisible(true);
//            timeLabel.setVisible(true);
//            boxImage.close();
//            startResourcesAction.setEndResourcesAction();
////            }
//        }
//        if (!claimButton.isVisible()) {
//            // вычислим разницу во времени между текущим временем и временем, необходимым для получения подарка
//            long deltaTime = giftTime - System.currentTimeMillis();
//            // если разница < 0 , т.е. если времени прошло больше, чем нужно для получения подарка, тогда делаем кнопку "ПОЛУЧИТЬ" видимой
//            if (deltaTime < 0 && !claimButton.isVisible()) {
//                claimButton.setVisible(true);
//                timeLabel.setVisible(false);
//            }
//            // если кнопка "ПОЛУЧИТЬ" не видима, то установим значение времени, чтобы обновить время в надписи кол-ва времени (сколько осталось до получения подарка)
//            if (!claimButton.isVisible()) {
//                date.setTime(deltaTime);                        // установим значение для даты
//                timeLabel.setText("" + formatForDate.format(deltaTime));
//            }
//            super.act(delta);
//        }
//    }
//
//    public void clearAction() {
//
//    }
//}
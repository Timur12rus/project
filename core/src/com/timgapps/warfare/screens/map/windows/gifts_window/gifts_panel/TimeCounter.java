package com.timgapps.warfare.screens.map.windows.gifts_window.gifts_panel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Warfare;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeCounter {
    private float counter;
    private Date date;
    private SimpleDateFormat formatForDate;
    private Label timeLabel;
    //    private final long DELTA_TIME_FIRST = 300000L;
//    private final long DELTA_TIME_SECOND = 600000L;
    private final long DELTA_TIME_FIRST = 10000L;
    private final long DELTA_TIME_SECOND = 20000L;
    private long giftTime;          // время в мсек до получения подарка
    public static final int RESOURCES_GIFT = 1;
    public static final int RESOURCE_AND_COINS_GIFT = 2;
    private long timeToGift;        // время в мил.сек оставшееся до получения подарка
    private long deltaTimeToGift;        // время в мил.сек оставшееся до получения подарка
    private int giftsType;
    private GameManager gameManager;
    private boolean isStop;  // флаг, значит счетчик остановлен и время вышло
    private GiftsPanelGuiController giftsPanelGuiController;

    public TimeCounter(GiftsPanelGuiController giftsPanelGuiController, GameManager gameManager, int giftsType) {
        this.gameManager = gameManager;
        this.giftsType = giftsType;
        this.giftsPanelGuiController = giftsPanelGuiController;
        date = new Date();      // получим текущее время
        formatForDate = new SimpleDateFormat("HH:mm:ss");
        formatForDate.setTimeZone(TimeZone.getTimeZone("GMT"));

        Label.LabelStyle timeLabelStyle = new Label.LabelStyle();
        timeLabelStyle.fontColor = Color.LIGHT_GRAY;
        timeLabelStyle.font = Warfare.font20;

        // задаём кол-во времени, которое нужно для ожидания подарка ресурсов
        if (giftsType == RESOURCE_AND_COINS_GIFT) {
            timeToGift = gameManager.getGiftTimeFirst();      // время в мсек до получения подарка
            deltaTimeToGift = DELTA_TIME_FIRST;
        }

        // задаём кол-во времени, которое нужно для ожидания подарка баффов
        if (giftsType == RESOURCES_GIFT) {
            timeToGift = gameManager.getGiftTimeSecond();
            deltaTimeToGift = DELTA_TIME_SECOND;
        }

        // надпись кол-ва оставшегося времени для подарка
        // текст оставшееся время в формате времени
        timeLabel = new Label("" + formatForDate.format(timeToGift - date.getTime()), timeLabelStyle);
        giftsPanelGuiController.addTimeLabel(timeLabel);
        redraw();
    }

    /**
     * сброс счетчика
     **/
    public void reset() {
        timeToGift = new Date().getTime() + deltaTimeToGift;  // время в мил. сек. до получения подарка, считая от текущего момента
        if (giftsType == RESOURCE_AND_COINS_GIFT) {
            gameManager.setGiftTimeFirst(timeToGift);      // сохраним значение текщего времени для получения подарка
        }
        if (giftsType == RESOURCES_GIFT) {
            gameManager.setGiftTimeSecond(timeToGift);      // сохраним значение текщего времени для получения подарка
        }
        isStop = false;
        giftsPanelGuiController.hideClaimButton();
    }

    public void update() {
        // разница во времени между временем оставшимся до получения подарка и текущим временем
        long deltaTime = timeToGift - System.currentTimeMillis();
        // если разница < 0 , т.е. если времени прошло больше, чем нужно для получения подарка, тогда делаем кнопку "ПОЛУЧИТЬ" видимой
        if (deltaTime < 0 && !isStop) {
            isStop = true;
            giftsPanelGuiController.showClaimButton();
            timeLabel.setVisible(false);
        }
        if (!isStop) {
            timeLabel.setText("" + formatForDate.format(deltaTime));
        }
    }

    // перерисовывает надпись с оставшимся временем, если оно вышло делает надпись не видимой
    public void redraw() {
        if (timeToGift - date.getTime() < 0) {
            timeLabel.setVisible(false);
            isStop = true;
            giftsPanelGuiController.showClaimButton();
        } else {
            timeLabel.setVisible(true);
            isStop = false;
            giftsPanelGuiController.hideClaimButton();
        }
//        long deltaTime = timeToGift - System.currentTimeMillis();
//        // если разница < 0 , т.е. если времени прошло больше, чем нужно для получения подарка, тогда делаем кнопку "ПОЛУЧИТЬ" видимой
//        if (deltaTime < 0 && !isStop) {
//            isStop = true;
//            giftsPanelGuiController.showClaimButton();
//            timeLabel.setVisible(false);
//        } else {
//            timeLabel.setVisible(true);
//            isStop = false;
//            giftsPanelGuiController.hideClaimButton();
//        }
    }

    public boolean isStop() {
        return isStop;
    }
}

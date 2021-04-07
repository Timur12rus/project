package com.timgapps.warfare.screens.map.windows.gifts_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.screens.map.gifts_panel.MyGiftsPanel;
import com.timgapps.warfare.screens.map.win_creator.ConstructedWindow;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.screens.map.MapScreen;
import com.timgapps.warfare.Warfare;

public class GiftScreen extends Group {
    public static final int ON_SHOW_ANIMATIONS = 1;
    public static final int ON_RESUME = 2;
    private ConstructedWindow constructedWindow;
    private ImageButton closeButton;
    private Label rewardTitle; // отображаем текст заголовка
    private GameManager gameManager;
    private GiftPanel giftPanelLeft, giftPanelRight;
    private MapScreen mapScreen;
    private MyGiftsPanel leftGiftPanel, rightGiftPanel;

    // экран - окно, с двумя панелями подарков, кнопкой закрыть
    public GiftScreen(MapScreen mapScreen, GameManager gameManager) {
        this.mapScreen = mapScreen;
        this.gameManager = gameManager;
        constructedWindow = new ConstructedWindow(700, 550, "Gifts");
        addActor(constructedWindow);
        setSize(constructedWindow.getWidth(), constructedWindow.getHeight());
        initializeLabels();

        leftGiftPanel = new MyGiftsPanel(mapScreen, gameManager, MyGiftsPanel.RESOURCES_GIFT);
        leftGiftPanel.debug();
        rightGiftPanel = new MyGiftsPanel(mapScreen, gameManager, MyGiftsPanel.RESOURCE_AND_COINS_GIFT);
        leftGiftPanel.setPosition(constructedWindow.getWidth() / 2 - leftGiftPanel.getWidth() - 32, constructedWindow.getY() + 64);
        rightGiftPanel.setPosition(constructedWindow.getWidth() / 2 + 32, constructedWindow.getY() + 64);
        addActor(leftGiftPanel);
        addActor(rightGiftPanel);

        closeButton = constructedWindow.getCloseButton();
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new MessageEvent(ON_RESUME));
            }
        });
    }

    // метод создает надписи заголовка окна
    private void initializeLabels() {
        Label.LabelStyle rewardTitleLabelStyle = new Label.LabelStyle();
        rewardTitleLabelStyle.fontColor = Color.DARK_GRAY;
        rewardTitleLabelStyle.font = Warfare.font40;
        rewardTitle = new Label("Daily gifts", rewardTitleLabelStyle);
        rewardTitle.setAlignment(Align.center);
        rewardTitle.setPosition(constructedWindow.getX() + constructedWindow.getWidth() / 2 - rewardTitle.getWidth() / 2,
                constructedWindow.getY() + constructedWindow.getHeight() - rewardTitle.getHeight() - 8);
        addActor(rewardTitle);
    }
}




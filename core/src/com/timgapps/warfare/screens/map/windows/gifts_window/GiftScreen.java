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
    private com.timgapps.warfare.screens.map.windows.gifts_window.GiftPanel giftPanel, buffPanel;
    private GiftsTable giftsTable;
    private MapScreen mapScreen;

    // экран - окно, с двумя панелями подарков, кнопкой закрыть
    public GiftScreen(MapScreen mapScreen, GameManager gameManager) {
        this.mapScreen = mapScreen;
        this.gameManager = gameManager;
        constructedWindow = new ConstructedWindow(700, 550, "Gifts");
        constructedWindow.setX((Warfare.V_WIDTH - constructedWindow.getWidth()) / 2);       // устанавливаем позицию заголовка
        constructedWindow.setY(Warfare.V_HEIGHT / 2 - constructedWindow.getHeight() / 2);
        addActor(constructedWindow);
        initializeLabels();

        // таблица с панелями подарков (GiftPanel's)
        giftsTable = new GiftsTable();
        giftsTable.debug();
        giftsTable.setPosition(constructedWindow.getX() + (constructedWindow.getWidth() - giftsTable.getWidth()) / 2,
                constructedWindow.getY() + 64);
        addActor(giftsTable);
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

    /**
     * таблица с панелями подарков (GiftPanel's)
     **/
    class GiftsTable extends Table {
        public GiftsTable() {
            float giftPanelX = constructedWindow.getX() + (constructedWindow.getWidth() - 190 * 2 + 64) / 2;
            float giftPanelY = constructedWindow.getY() + 64;
            // первая панель подарков (левая)
            giftPanel = new com.timgapps.warfare.screens.map.windows.gifts_window.GiftPanel(mapScreen, giftPanelX, giftPanelY, gameManager, com.timgapps.warfare.screens.map.windows.gifts_window.GiftPanel.RESOURCE_AND_COINS_GIFT);
            // вторая панель подарков (правая)
            buffPanel = new com.timgapps.warfare.screens.map.windows.gifts_window.GiftPanel(mapScreen, giftPanelX + 190 + 64, giftPanelY, gameManager, com.timgapps.warfare.screens.map.windows.gifts_window.GiftPanel.RESOURCES_GIFT);
            add(giftPanel).padLeft(32).padRight(32);
            add(buffPanel).padLeft(32).padRight(32);
            setWidth(giftPanel.getWidth() * 2 + 64 * 2);
            setHeight(giftPanel.getHeight());
        }
    }
}




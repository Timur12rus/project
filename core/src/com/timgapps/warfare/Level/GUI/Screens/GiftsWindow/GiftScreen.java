package com.timgapps.warfare.Level.GUI.Screens.GiftsWindow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Level.LevelMap.LevelMap;
import com.timgapps.warfare.Warfare;

public class GiftScreen extends Group {
    public static final int ON_SHOW_ANIMATIONS = 1;
    public static final int ON_RESUME = 2;

    private Image background;
    private ImageButton closeButton;
    private Label rewardTitle; // отображаем текст заголовка
    private GameManager gameManager;
    private GiftPanel giftPanel, buffPanel;

    private GiftsTable giftsTable;
    private LevelMap levelMap;

    // экран - окно, с двумя панелями подарков, кнопкой закрыть
    public GiftScreen(LevelMap levelMap, GameManager gameManager) {
        this.levelMap = levelMap;
        this.gameManager = gameManager;
        background = new Image(Warfare.atlas.findRegion("teamScreen"));
        background.setX((Warfare.V_WIDTH - background.getWidth()) / 2); // устанавливаем позицию заголовка
        background.setY(((Warfare.V_HEIGHT / 2 - background.getHeight() / 2)));

        addActor(background);
        closeButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("button_close")),
                new TextureRegionDrawable(Warfare.atlas.findRegion("button_close_dwn")));
        closeButton.setX(background.getX() + background.getWidth() - closeButton.getWidth() - 28);
        closeButton.setY(background.getY() + background.getHeight() - closeButton.getHeight() - 12);
        addActor(closeButton);

        initializeLabels();

        // таблица с панелями подарков (GiftPanel's)
        giftsTable = new GiftsTable();
        giftsTable.debug();
        giftsTable.setPosition(background.getX() + (background.getWidth() - giftsTable.getWidth()) / 2,
                background.getY() + 64);
        addActor(giftsTable);

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
        rewardTitle.setPosition(background.getX() + background.getWidth() / 2 - rewardTitle.getWidth() / 2,
                background.getY() + background.getHeight() - rewardTitle.getHeight() - 8);
        addActor(rewardTitle);
    }

    /**
     * таблица с панелями подарков (GiftPanel's)
     **/
    class GiftsTable extends Table {
        public GiftsTable() {

            float giftPanelX = background.getX() + (background.getWidth() - 190 * 2 + 64) / 2;
            float giftPanelY = background.getY() + 64;

            giftPanel = new GiftPanel(giftPanelX, giftPanelY, gameManager, GiftPanel.RESOURCES_GIFT);
            buffPanel = new GiftPanel(giftPanelX + 190 + 64, giftPanelY, gameManager, GiftPanel.BUFFS_GIFT);
            add(giftPanel).padLeft(32).padRight(32);
            add(buffPanel).padLeft(32).padRight(32);
            setWidth(giftPanel.getWidth() * 2 + 64 * 2);
            setHeight(giftPanel.getHeight());
        }
    }
}




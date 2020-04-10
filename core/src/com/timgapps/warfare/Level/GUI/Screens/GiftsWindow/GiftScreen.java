package com.timgapps.warfare.Level.GUI.Screens.GiftsWindow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow.ColorButton;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Level.LevelScreens.RewardTable;
import com.timgapps.warfare.Warfare;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GiftScreen extends Group {
    public static final int ON_START = 1;
    public static final int ON_RESUME = 2;

    private Image background;
    private ImageButton closeButton;
    private Label rewardTitle; // отображаем текст заголовка
    private GameManager gameManager;
    private GiftPanel giftPanel;

    long giftTime;  // времея в которое буедт доступен подарок
    Date currentDate;   // текущее время
    Date date;

    public GiftScreen(GameManager gameManager) {
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

        date = new Date();                      // поулим текущее время
        giftTime = date.getTime() + 30000;   // время в миллисекундах до получения вознаграждения (2ч.)
//        giftTime = date.getTime() + 7200000L;   // время в миллисекундах до получения вознаграждения (2ч.)

        giftPanel = new GiftPanel();
        giftPanel.setPosition((background.getWidth() - giftPanel.getWidth()) / 2,
                background.getY() + 152);
        addActor(giftPanel);

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

    class GiftPanel extends Group {
        Image background;
        Label timeLabel;
        Label doneLabel;
        RewardTable rewardTable;
        ColorButton claimButton;
        Image boxImage;
        SimpleDateFormat formatForDate;

        public GiftPanel() {

            formatForDate = new SimpleDateFormat("HH:mm:ss");
            formatForDate.setTimeZone(TimeZone.getTimeZone("GMT"));
//            formatForDate = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
//            System.out.println(formatForDate.getTimeZone().toString());
//            formatForDate.setTimeZone();
            background = new Image(Warfare.atlas.findRegion("gifts_bg"));
            boxImage = new Image(Warfare.atlas.findRegion("boxImage"));

            Label.LabelStyle timeLabelStyle = new Label.LabelStyle();
            timeLabelStyle.fontColor = Color.LIGHT_GRAY;
            timeLabelStyle.font = Warfare.font20;

            timeLabel = new Label("" + formatForDate.format(giftTime - date.getTime()), timeLabelStyle);              // текст оставшееся время в формате времени
            rewardTable = new RewardTable(100, 35);
            claimButton = new ColorButton("Claim", ColorButton.YELLOW_BUTTON);
            claimButton.setVisible(false);

            addActor(background);

            timeLabel.setPosition((background.getWidth() - timeLabel.getWidth()) / 2,
                    background.getHeight() - 8 - timeLabel.getHeight());
            addActor(timeLabel);

//            rewardTable.setPosition((background.getWidth() - rewardTable.getWidth()) / 2,
//                    timeLabel.getY() - rewardTable.getHeight());

            rewardTable.setPosition((background.getWidth() - rewardTable.getWidth()) / 2,
                    timeLabel.getY() - 32);
            addActor(rewardTable);

            boxImage.setPosition((background.getWidth() - boxImage.getWidth()) / 2,
                    background.getY());
            addActor(boxImage);

            claimButton.setPosition((background.getWidth() - claimButton.getWidth()) / 2,
                    background.getY() - 16 - claimButton.getHeight());
            addActor(claimButton);


        }

        @Override
        public void act(float delta) {

            if (!claimButton.isVisible()) {
                long deltaTime = giftTime - new Date().getTime();
                if (deltaTime < 0)
                    claimButton.setVisible(true);
                if (!claimButton.isVisible()) {
                    date.setTime(deltaTime);                        // установим значение для даты
                    timeLabel.setText("" + formatForDate.format(deltaTime));
                }
                super.act(delta);
            }
        }
    }
}


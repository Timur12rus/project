package com.timgapps.warfare.Level.GUI.Screens.GiftsWindow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
import com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow.ColorButton;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Level.LevelMap.LevelMap;
import com.timgapps.warfare.Level.LevelScreens.RewardTable;
import com.timgapps.warfare.Warfare;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

class GiftPanel extends Group {
    private Image background;
    private Label timeLabel;
    private Label doneLabel;
    private GiftRewardTable rewardTable;
    private ColorButton claimButton;
    private Image boxImage;
    private SimpleDateFormat formatForDate;
    private Date date;

    public static final int RESOURCES_GIFT = 1;
    public static final int BUFFS_GIFT = 2;
    private long giftTime;

    private boolean isEndCoinsAction = false;
    private CoinsPanel coinsPanel;
    private float xPos, yPos;

    private static final int FOOD = 1;
    private static final int IRON = 2;
    private static final int WOOD = 3;

    private GameManager gameManager;


    // панель с горизонтальной таблицей GiftRewardTable и кнопкой "ПОЛУЧИТЬ"
    public GiftPanel(float x, float y, GameManager gameManager, int giftsType) {
        xPos = x;
        yPos = y;
        this.gameManager = gameManager;
        date = new Date();
        coinsPanel = gameManager.getCoinsPanel();
        formatForDate = new SimpleDateFormat("HH:mm:ss");
        formatForDate.setTimeZone(TimeZone.getTimeZone("GMT"));
        background = new Image(Warfare.atlas.findRegion("gifts_bg"));
        boxImage = new Image(Warfare.atlas.findRegion("boxImage"));
        claimButton = new ColorButton("Claim", ColorButton.YELLOW_BUTTON);

        // кнопка "ПОЛУЧИТЬ"
        claimButton.setVisible(false);

        Label.LabelStyle timeLabelStyle = new Label.LabelStyle();
        timeLabelStyle.fontColor = Color.LIGHT_GRAY;
        timeLabelStyle.font = Warfare.font20;

        // задаём кол-во времени, которое нужно для ожидания подарка ресурсов
        if (giftsType == RESOURCES_GIFT) {
            giftTime = date.getTime() + 2000;
        }

        // задаём кол-во времени, которое нужно для ожидания подарка баффов
        if (giftsType == BUFFS_GIFT) {
            giftTime = date.getTime() + 2000;
        }

        // надпись кол-ва оставшегося времени для подарка
        timeLabel = new Label("" + formatForDate.format(giftTime - date.getTime()), timeLabelStyle);              // текст оставшееся время в формате времени

        /** таблица с вознаграждениями (две панели) **/
        rewardTable = new GiftRewardTable(120, 2);
        addActor(claimButton);
        background.setPosition(claimButton.getX(), claimButton.getY() + 16 + claimButton.getHeight());
        addActor(background);

        timeLabel.setPosition((background.getWidth() - timeLabel.getWidth()) / 2,
                background.getY() + background.getHeight() - 8 - timeLabel.getHeight());
        addActor(timeLabel);

        rewardTable.setPosition((background.getWidth() - rewardTable.getWidth()) / 2,
                timeLabel.getY() - 32);
        addActor(rewardTable);

        boxImage.setPosition((background.getWidth() - boxImage.getWidth()) / 2,
                background.getY());
        addActor(boxImage);

        claimButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showAddCoinsAnimation();
                showAddResoursesAnimtion();
            }
        });
    }

    private Image getImageResourse(int resourceType) {
        Image resourceImage;
        switch (resourceType) {
            case FOOD:
                resourceImage = new Image(Warfare.atlas.findRegion("food_icon"));
                gameManager.addFoodCount(1);
                break;
            case IRON:
                resourceImage = new Image(Warfare.atlas.findRegion("iron_icon"));
                gameManager.addIronCount(1);
                break;
            case WOOD:
                resourceImage = new Image(Warfare.atlas.findRegion("wood_icon"));
                gameManager.addWoodCount(1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + resourceType);
        }
        return resourceImage;
    }


    private void showAddResoursesAnimtion() {
        final Random random = new Random();

        // получим тип ресурса первого и второго
        int firstResourse = random.nextInt(3) + 1;
        int secondResource = random.nextInt(3) + 1;

        Image resOne = getImageResourse(firstResourse);
        Image resTwo = getImageResourse(secondResource);

        // установим позицию для добавляемых монет, к которым будут применены action'ы
        float x = rewardTable.getX() + rewardTable.getWidth() - 96;
        float y = getY() + getHeight() / 2;
        resOne.setPosition(x, y);
        resTwo.setPosition(x, y);

        addActor(resOne);
        addActor(resTwo);

        float endXPos = -300;
        float endYPos = y + 16;


        // action проверки завершения действия
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
//                isEndCoinsAction = true;
//                rewardTable.setVisible(true);
////                giftTime = new Date().getTime() + 10000;
//                timeLabel.setVisible(true);

                // добавим к общему кол-ву монет монеты (награду)
//                coinsPanel.addCoins(120);
                return true;
            }
        };

        // action для первого ресурса
        SequenceAction moveActionResOne = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(getWidth() / 2 + 32, getHeight() / 2 + 88, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0)
        );

        // action для вторго ресурса
        SequenceAction moveActionResTwo = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(getWidth() / 2 - 64, getHeight() / 2 + 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0),
                checkEndOfAction
        );

        resOne.addAction(moveActionResOne);
        resTwo.addAction(moveActionResTwo);
    }

    private void showAddCoinsAnimation() {

        claimButton.setVisible(false);
        rewardTable.setVisible(false);
        timeLabel.setVisible(false);
        giftTime = new Date().getTime() + 2000;

        Image coinOne = new Image(Warfare.atlas.findRegion("coin_icon"));
        Image coinTwo = new Image(Warfare.atlas.findRegion("coin_icon"));
        Image coinThree = new Image(Warfare.atlas.findRegion("coin_icon"));

        // установим позицию для добавляемых монет, к которым будут применены action'ы
        float x = rewardTable.getX() + rewardTable.getWidth();
        float y = getY() + getHeight() / 2 + 48;
        coinOne.setPosition(x, y);
        coinTwo.setPosition(x, y);
        coinThree.setPosition(x, y);

        this.getParent().addActor(coinOne);
        this.getParent().addActor(coinTwo);
        this.getParent().addActor(coinThree);

        float endXPos = coinsPanel.getX() + coinsPanel.getWidth() / 2 - xPos + 48;
        float endYPos = coinsPanel.getY() - yPos;

        // action проверки завершения действия
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                isEndCoinsAction = true;
                rewardTable.setVisible(true);
//                giftTime = new Date().getTime() + 10000;
                timeLabel.setVisible(true);

                // добавим к общему кол-ву монет монеты (награду)
                coinsPanel.addCoins(120);
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
                Actions.fadeOut(0), checkEndOfAction
        );

        coinOne.addAction(moveActionCoinOne);
        coinTwo.addAction(moveActionCoinTwo);
        coinThree.addAction(moveActionCoinThree);
    }

    @Override
    public float getWidth() {
        return background.getWidth();
    }

    @Override
    public float getHeight() {
        return background.getHeight() + 16 + claimButton.getHeight();
    }

    @Override
    public void act(float delta) {

        if (!claimButton.isVisible()) {
            long deltaTime = giftTime - System.currentTimeMillis();
//            long deltaTime = giftTime - new Date().getTime();
            if (deltaTime < 0) {
                claimButton.setVisible(true);
            }
            if (!claimButton.isVisible()) {
                date.setTime(deltaTime);                        // установим значение для даты
                timeLabel.setText("" + formatForDate.format(deltaTime));
            }
            super.act(delta);
        }
    }
}
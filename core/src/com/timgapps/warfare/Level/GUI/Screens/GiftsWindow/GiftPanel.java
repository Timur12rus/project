package com.timgapps.warfare.Level.GUI.Screens.GiftsWindow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
import com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow.ColorButton;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Level.LevelMap.GiftIcon;
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
    private BoxImage boxImage;
    //    private Image boxImage;
    private SimpleDateFormat formatForDate;
    private Date date;

    public static final int RESOURCES_GIFT = 1;
    public static final int RESOURCE_AND_COINS_GIFT = 2;
    public static final int BUFFS_GIFT = 3;
//    private final long DELTA_TIME_FIRST = 300000L;
//    private final long DELTA_TIME_SECOND = 600000L;

    private final long DELTA_TIME_FIRST = 5000L;
    private final long DELTA_TIME_SECOND = 3000L;
    private final int COINS_COUNT_REWARD = 55;


    private long deltaTime;
    private long giftTime;          // время в мсек до получения подарка
    private int giftsType;

    private boolean isEndCoinsAction = false;
    private CoinsPanel coinsPanel;
    private float xPos, yPos;

    private static final int FOOD = 1;
    private static final int IRON = 2;
    private static final int WOOD = 3;

    private GameManager gameManager;
    private GiftIcon giftIcon;


    // панель с горизонтальной таблицей GiftRewardTable и кнопкой "ПОЛУЧИТЬ"
    public GiftPanel(GiftIcon giftIcon, float x, float y, GameManager gameManager, int giftsType) {
        this.giftIcon = giftIcon;
//        System.out.println("GIFT ICON = " + giftIcon);
        xPos = x;
        yPos = y;
        this.giftsType = giftsType;
        this.gameManager = gameManager;
        date = new Date();      // получим текущее время
        coinsPanel = gameManager.getCoinsPanel();
        formatForDate = new SimpleDateFormat("HH:mm:ss");
        formatForDate.setTimeZone(TimeZone.getTimeZone("GMT"));
        background = new Image(Warfare.atlas.findRegion("gifts_bg"));

//        boxImage = new Image(Warfare.atlas.findRegion("boxImage"));
        boxImage = new BoxImage();

        claimButton = new ColorButton("Claim", ColorButton.YELLOW_BUTTON);

        // кнопка "ПОЛУЧИТЬ"
        claimButton.setVisible(false);

        Label.LabelStyle timeLabelStyle = new Label.LabelStyle();
        timeLabelStyle.fontColor = Color.LIGHT_GRAY;
        timeLabelStyle.font = Warfare.font20;

        // задаём кол-во времени, которое нужно для ожидания подарка ресурсов
        if (giftsType == RESOURCE_AND_COINS_GIFT) {
            giftTime = gameManager.getGiftTimeFirst();
            deltaTime = DELTA_TIME_FIRST;
//            giftTime = date.getTime() + DELTA_TIME;
        }

        // задаём кол-во времени, которое нужно для ожидания подарка баффов
        if (giftsType == RESOURCES_GIFT) {
            giftTime = gameManager.getGiftTimeSecond();
            deltaTime = DELTA_TIME_SECOND;

//            giftTime = date.getTime() + DELTA_TIME;
        }

        // надпись кол-ва оставшегося времени для подарка
        timeLabel = new Label("" + formatForDate.format(giftTime - date.getTime()), timeLabelStyle);              // текст оставшееся время в формате времени

        if (giftTime - date.getTime() < 0) {
            timeLabel.setVisible(false);
        } else {
            timeLabel.setVisible(true);
        }

        /** таблица с вознаграждениями (две панели) **/
        if (giftsType == RESOURCES_GIFT) {
            rewardTable = new GiftRewardTable(0, 2);
        }
        if (giftsType == RESOURCE_AND_COINS_GIFT) {
            rewardTable = new GiftRewardTable(55, 1);
        }

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
                background.getY() + 16);
        addActor(boxImage);

        claimButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boxImage.startAnimation();
                showAddGiftsAnimation();
//                showAddCoinsAnimation();
//                showAddResoursesAnimation();
            }
        });
    }

    class BoxImage extends Actor {
        Animation openBoxAnimation;     // анимация отркырия ящика
        private boolean isStarted;
        private float stateTime = 0;
        private TextureRegion textureBox;

        public BoxImage() {
            textureBox = new TextureRegion(Warfare.atlas.findRegion("boxImage0"));
            setSize(textureBox.getRegionWidth(), textureBox.getRegionHeight());
            Array<TextureRegion> frames = new Array<TextureRegion>();
            for (int i = 0; i < 6; i++)
                frames.add(new TextureRegion(Warfare.atlas.findRegion("boxImage" + i)));
            openBoxAnimation = new Animation(0.2f, frames);
            frames.clear();
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            stateTime += Gdx.graphics.getDeltaTime();
            if (isStarted) {
                batch.draw((TextureRegion) openBoxAnimation.getKeyFrame(stateTime, false), getX(), getY());
            } else {
                batch.draw(textureBox, getX(), getY());
            }
        }

        void startAnimation() {
            isStarted = true;
        }

        void closeBox() {
            isStarted = false;
        }
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


    private void showAddResoursesAnimation() {
        final Random random = new Random();

        // получим тип ресурса первого и второго
        int firstResourse = random.nextInt(3) + 1;
        int secondResource = random.nextInt(3) + 1;

        Image resOne = getImageResourse(firstResourse);

        Image resTwo = null;
        if (giftsType == RESOURCES_GIFT) {
            resTwo = getImageResourse(secondResource);

        }

        // сохраним соятояние игры с новыми значениями ресурсов
        gameManager.saveGame();

        // установим позицию для добавляемых монет, к которым будут применены action'ы
        float x = rewardTable.getX() + rewardTable.getWidth();
//        float x = rewardTable.getX() + rewardTable.getWidth() - 96;
        float y = getY() + getHeight() / 2;
        resOne.setPosition(x, y);

        this.getParent().addActor(resOne);
//        addActor(resOne);
//        addActor(resTwo);
        if (giftsType == RESOURCES_GIFT) {
            resOne.setPosition(x + 160, y);
            resTwo.setPosition(x + 160, y);
//            addActor(resTwo);
            this.getParent().addActor(resTwo);
        }

        float endXPos = -300;
        float endYPos = y + 16;

        if (giftsType == RESOURCES_GIFT) {
            endXPos = -600;
        }

        // action проверки завершения действия
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
//                isEndCoinsAction = true;
                rewardTable.setVisible(true);
//                giftTime = new Date().getTime() + 10000;
                timeLabel.setVisible(true);
                boxImage.closeBox();
//                isEndCoinsAction = true;
//                rewardTable.setVisible(true);
////                giftTime = new Date().getTime() + 10000;
//                timeLabel.setVisible(true);

                // добавим к общему кол-ву монет монеты (награду)
//                coinsPanel.addCoins(120);
                return true;
            }
        };

        float deltaXOne = 0;
        float deltaXTwo = 0;
        if (giftsType == RESOURCES_GIFT) {
            deltaXOne = 210;
            deltaXTwo = 48;
        }
        // action для первого ресурса
        SequenceAction moveActionResOne = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(getWidth() / 2 + 120 + deltaXOne, getHeight() / 2 + 88, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos + deltaXOne + deltaXTwo, endYPos, 0.8f),
                Actions.fadeOut(0),
                checkEndOfAction
        );


        // action для вторго ресурса
        SequenceAction moveActionResTwo = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(getWidth() / 2 + 48 + deltaXOne, getHeight() / 2 + 32, 0.8f, new Interpolation.SwingOut(1)),
//                Actions.moveTo(getWidth() / 2 - 64, getHeight() / 2 + 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos + deltaXOne + deltaXTwo, endYPos, 0.8f),
                Actions.fadeOut(0)
//                ,checkEndOfAction
        );

        resOne.addAction(moveActionResOne);
//        resTwo.addAction(moveActionResTwo);
        if (giftsType == RESOURCES_GIFT) {
            resTwo.addAction(moveActionResTwo);
        }

    }

    private void showAddGiftsAnimation() {
        if (gameManager.getHelpStatus() == GameManager.HELP_GET_GIFT) {
            gameManager.setHelpStatus(GameManager.NONE);
            giftIcon.hideFinger();
        }
        claimButton.setVisible(false);
        rewardTable.setVisible(false);
        timeLabel.setVisible(false);
        giftTime = new Date().getTime() + deltaTime;       // вычислим время необходимое для получения подарка, текущ время + DELTA_TIME
        if (giftsType == RESOURCE_AND_COINS_GIFT) {
            gameManager.setGiftTimeFirst(giftTime);      // сохраним значение текщего времени для получения подарка
            giftIcon.setGiftTimeFirst(giftTime);
        }
        if (giftsType == RESOURCES_GIFT) {
            gameManager.setGiftTimeSecond(giftTime);      // сохраним значение текщего времени для получения подарка
            giftIcon.setGiftTimeSecond(giftTime);
        }

        showAddCoinsAnimation();
        showAddResoursesAnimation();
    }

    private void showAddCoinsAnimation() {
        gameManager.addCoinsCount(COINS_COUNT_REWARD);

        if (giftsType == RESOURCE_AND_COINS_GIFT) {

            claimButton.setVisible(false);
            rewardTable.setVisible(false);
            timeLabel.setVisible(false);
            giftTime = new Date().getTime() + deltaTime;       // вычислим время необходимое для получения подарка, текущ время + DELTA_TIME
//            if (giftsType == RESOURCE_AND_COINS_GIFT) {
            gameManager.setGiftTimeFirst(giftTime);      // сохраним значение текщего времени для получения подарка
//            }
//            if (giftsType == RESOURCES_GIFT) {
//                gameManager.setGiftTimeSecond(giftTime);      // сохраним значение текщего времени для получения подарка
//            }

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
                    boxImage.closeBox();

                    // добавим к общему кол-ву монет монеты (награду)
                    coinsPanel.addCoins(COINS_COUNT_REWARD);
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

            // вычислим разницу во времени между текущим временем и временем, необходимым для получения подарка
            long deltaTime = giftTime - System.currentTimeMillis();

            // если разница < 0 , т.е. если времени прошло больше, чем нужно для получения подарка, тогда делаем кнопку "ПОЛУЧИТЬ" видимой
            if (deltaTime < 0 && !claimButton.isVisible()) {
                claimButton.setVisible(true);
            }

            // если кнопка "ПОЛУЧИТЬ" не видима, то установим значение времени, чтобы обновить время в надписи кол-ва времени (сколько осталось до получения подарка)
            if (!claimButton.isVisible()) {
                date.setTime(deltaTime);                        // установим значение для даты
                timeLabel.setText("" + formatForDate.format(deltaTime));
            }
            super.act(delta);
        }
    }
}
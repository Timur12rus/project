package com.timgapps.warfare.Level.GUI.Screens.RewardForStars;

import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.games.StageGame;
import com.sun.org.apache.regexp.internal.RE;
import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Warfare;

import java.util.Random;

public class GiftAnimation {
    private float x, y;
    private RewardForStarsScreen rewardForStarsScreen;
    private float endXPos;
    private float endYPos;
    private Image coinOne, coinTwo, coinThree;
    private int typeOfAnimation;
    public static final int COIN_GIFT = 1;
    public static final int RESOURSES_GIFT = 2;
    private final int COINS_COUNT_REWARD = 100;
    private static final int FOOD = 1;
    private static final int IRON = 2;
    private static final int WOOD = 3;
    private GameManager gameManager;

    public GiftAnimation(RewardForStarsScreen rewardForStarsScreen, float x, float y, int typeOfAnimation) {
        this.typeOfAnimation = typeOfAnimation;
        this.rewardForStarsScreen = rewardForStarsScreen;
        gameManager = rewardForStarsScreen.getGameManager();
        this.x = x;
        this.y = y;
        if (typeOfAnimation == COIN_GIFT) {
            coinOne = new Image(Warfare.atlas.findRegion("coin_icon"));
            coinTwo = new Image(Warfare.atlas.findRegion("coin_icon"));
            coinThree = new Image(Warfare.atlas.findRegion("coin_icon"));
            coinOne.setPosition(x, y);
            coinTwo.setPosition(x, y);
            coinThree.setPosition(x, y);
            rewardForStarsScreen.addChild(coinOne);
            rewardForStarsScreen.addChild(coinTwo);
            rewardForStarsScreen.addChild(coinThree);
        }
        if (typeOfAnimation == COIN_GIFT) {
            endXPos = rewardForStarsScreen.getWidth() - 100;
            endYPos = rewardForStarsScreen.getHeight() - 64;
        }
        if (typeOfAnimation == RESOURSES_GIFT) {
            endXPos = 32;
            endYPos = rewardForStarsScreen.getHeight() / 3;
        }
    }

    private void showResoursesAnimation() {
        final Random random = new Random();
        final IconAnimation iconAnimation = new IconAnimation(rewardForStarsScreen,
                "teamButton", 32, rewardForStarsScreen.getHeight() / 3);
        // получим тип ресурса первого и второго
        int firstResourse = random.nextInt(3) + 1;
        int secondResourse = random.nextInt(3) + 1;
        int thirdResourse = random.nextInt(3) + 1;
        Image resOne = getImageResourse(firstResourse);
        Image resTwo = getImageResourse(secondResourse);
        Image resThree = getImageResourse(thirdResourse);
        rewardForStarsScreen.addChild(resOne);
        rewardForStarsScreen.addChild(resTwo);
        rewardForStarsScreen.addChild(resThree);
        resOne.setPosition(x, y);
        resTwo.setPosition(x, y);
        resThree.setPosition(x, y);
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                iconAnimation.start();
                return true;
            }
        };

        SequenceAction moveActionResOne = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(x - 32, y + 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.6f),
                Actions.fadeOut(0),
                checkEndOfAction
        );

        SequenceAction moveActionResTwo = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(x + 32, y + 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.6f),
                Actions.fadeOut(0)
        );

        SequenceAction moveActionResThree = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(x + 32, y - 64, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.6f),
                Actions.fadeOut(0)
        );
        resOne.addAction(moveActionResOne);
        resTwo.addAction(moveActionResTwo);
        resThree.addAction(moveActionResThree);
    }

    private void showCoinsAnimation() {
        final CoinsPanel coinsPanel = gameManager.getCoinsPanel();
        float coinsPanelX = rewardForStarsScreen.getWidth() - coinsPanel.getWidth() - 32;
        float coinsPanelY = rewardForStarsScreen.getHeight() - coinsPanel.getHeight() - 32;
        coinsPanel.setPosition(coinsPanelX, coinsPanelY);
        coinsPanel.setVisible(false);
        rewardForStarsScreen.addChild(coinsPanel);
        coinsPanel.addAction(Actions.fadeOut(0));

        // действие для панели монет
        SequenceAction moveAction = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(coinsPanelX - 4, coinsPanelY - 4, 0.2f),
                Actions.moveTo(coinsPanelX, coinsPanelY, 0.2f)
        );

        // действие для панели монет
        final SequenceAction sizeAction = new SequenceAction(Actions.fadeIn(0.1f),
                Actions.sizeBy(8, 8, 0.2f),
                Actions.sizeBy(-8, -8, 0.2f),
                Actions.fadeOut(0)
        );


        // action проверки завершения действия
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                // добавим к общему кол-ву монет монеты (награду)
                coinsPanel.setVisible(true);
                coinsPanel.addCoins(COINS_COUNT_REWARD);
                coinsPanel.addAction(sizeAction);
                return true;
            }
        };
        // action для первой монеты
        SequenceAction moveActionCoinOne = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(x - 32, y - 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0)
        );

        // action для второй монеты
        SequenceAction moveActionCoinTwo = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(x + 32, y - 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0)
        );

        // action для третьей монеты
        SequenceAction moveActionCoinThree = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(x + 32, y + 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0), checkEndOfAction
        );
        coinOne.addAction(moveActionCoinOne);
        coinTwo.addAction(moveActionCoinTwo);
        coinThree.addAction(moveActionCoinThree);
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

    public void start() {
        if (typeOfAnimation == COIN_GIFT) {
            showCoinsAnimation();
        }
        if (typeOfAnimation == RESOURSES_GIFT) {
            showResoursesAnimation();
        }
    }
}

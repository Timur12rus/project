package com.timgapps.warfare.screens.map.windows.gifts_window.gifts_panel;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.timer.TimerIcon;
import com.timgapps.warfare.screens.map.actions.AddOverlayActionHelper;
import com.timgapps.warfare.screens.map.actions.MyCoinsAction;
import com.timgapps.warfare.screens.map.actions.MyResourcesAction;
import com.timgapps.warfare.screens.map.interfaces.RoundCircleController;
import com.timgapps.warfare.screens.map.windows.gifts_window.GiftRewardTable;
import com.timgapps.warfare.screens.map.windows.gifts_window.gifts_panel.gui_elements.BoxImage;
import com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements.ColorButton;

public class MyGiftsPanel extends Group implements GiftsPanelGuiController {
    public static final int RESOURCES_GIFT = 1;             // тип панели с подарками (русурсы)
    public static final int RESOURCE_AND_COINS_GIFT = 2;    // тип панели с подарками (ресурс и монеты)
    private int numOfResources;                             // кол-во ресурсов
    private int numOfCoins;                                 // кол-во монет
    private BoxImage boxImage;
    private ColorButton claimButton;
    private TimeCounter timeCounter;
    private int panelType;                                  // тип панели (ресурсы или ресурс с монетами)
    private Image background;
    private GiftRewardTable rewardTable;
    private GameManager gameManager;
    private MyCoinsAction myCoinsAction;
    private MyResourcesAction myResourcesAction;
    private RoundCircleController roundCircleController;

    public MyGiftsPanel(final AddOverlayActionHelper addOverlayActionHelper, GameManager gameManager, int panelType) {
        this.panelType = panelType;
        this.gameManager = gameManager;
        roundCircleController = (RoundCircleController) addOverlayActionHelper;
        this.debug();
        claimButton = new ColorButton("Claim", ColorButton.YELLOW_BUTTON);
        background = new Image(Warfare.atlas.findRegion("gifts_bg"));
        boxImage = new BoxImage();
        background.setPosition(0, claimButton.getHeight() + 16);
        setSize(background.getWidth(), claimButton.getHeight() + 16 + background.getHeight());
        /** таблица с вознаграждениями **/
        if (panelType == RESOURCES_GIFT) {
            numOfResources = 2;
            numOfCoins = 0;
        }
        if (panelType == RESOURCE_AND_COINS_GIFT) {
            numOfResources = 1;
            numOfCoins = 55;
        }
        rewardTable = new GiftRewardTable(numOfCoins, numOfResources);
        boxImage.debug();
        boxImage.setPosition((48), claimButton.getHeight() + 16 + 16);
        rewardTable.setPosition((getWidth() - rewardTable.getWidth()) / 2,
                getHeight() - rewardTable.getHeight());
        claimButton.setPosition((getWidth() - claimButton.getWidth()) / 2, 0);
        addActor(background);
        addActor(rewardTable);
        addActor(boxImage);
        addActor(claimButton);
        claimButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boxImage.startAnimation();
                showAddGiftsAnimation(addOverlayActionHelper);
                timeCounter.reset();           // сбросим счетчик
                rewardTable.setVisible(false); //делаем невидимым таблицу в с данными о вознаграждении
                showRewardTable();
            }
        });
        timeCounter = new TimeCounter(this, gameManager, panelType);
    }

    // метод делает видимым таблицу в с данными о вознаграждении
    private void showRewardTable() {
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                redraw();
                return true;
            }
        };
        SequenceAction sa = new SequenceAction(Actions.delay(2), checkEndOfAction);
        addAction(sa);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        timeCounter.update();
        if (timeCounter.isStop() && !roundCircleController.isRoundCircleVisible()) {
            roundCircleController.showRoundCircle();
        }
    }

    private void showAddGiftsAnimation(AddOverlayActionHelper addOverlayActionHelper) {
        float startXPos = getX() + getParent().getX() + boxImage.getX();
        float startYPos = getY() + boxImage.getY() + 128 + getParent().getY();
        if (panelType == RESOURCE_AND_COINS_GIFT) {
            gameManager.addCoinsCount(numOfCoins);
            myCoinsAction = new MyCoinsAction(addOverlayActionHelper, new Vector2(startXPos, startYPos), gameManager, 55);
            myResourcesAction = new MyResourcesAction(addOverlayActionHelper, new Vector2(startXPos, startYPos), gameManager, 1);
        }
        if (panelType == RESOURCES_GIFT) {
            myResourcesAction = new MyResourcesAction(addOverlayActionHelper, new Vector2(startXPos, startYPos), gameManager, 2);
        }
    }

    public void removeActions() {
        if (myCoinsAction != null) {
            myCoinsAction.clear();
        }

        if (myResourcesAction != null) {
            myResourcesAction.clear();
        }
    }

    // метод перерисовывает панель с подарками (после окончания действия добавления монет или (ресурсов)
    public void redraw() {
        boxImage.close();
        timeCounter.redraw();
        rewardTable.setVisible(true);
    }


    @Override
    public void addTimeLabel(Actor actor) {
        actor.setPosition((background.getWidth() - actor.getWidth()) / 2,
                background.getY() + background.getHeight() - 8 - actor.getHeight());
        addActor(actor);
    }

    @Override
    public void showClaimButton() {
        claimButton.setVisible(true);
//        roundCircleController.showRoundCircle();
    }

    @Override
    public void hideClaimButton() {
        claimButton.setVisible(false);
        roundCircleController.hideRoundCircle();
    }
}

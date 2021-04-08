package com.timgapps.warfare.screens.map.gifts_panel;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.map.actions.AddOverlayActionHelper;
import com.timgapps.warfare.screens.map.actions.MyCoinsAction;
import com.timgapps.warfare.screens.map.actions.MyResourcesAction;
import com.timgapps.warfare.screens.map.gifts_panel.gui_elements.BoxImage;
import com.timgapps.warfare.screens.map.windows.gifts_window.GiftRewardTable;
import com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements.ColorButton;

import java.util.Date;

public class MyGiftsPanel extends Group {
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
    private AddOverlayActionHelper addOverlayActionHelper;
    private MyCoinsAction myCoinsAction;
    private MyResourcesAction myResourcesAction;

    public MyGiftsPanel(final AddOverlayActionHelper addOverlayActionHelper, GameManager gameManager, int panelType) {
        this.panelType = panelType;
        this.gameManager = gameManager;
        this.addOverlayActionHelper = addOverlayActionHelper;
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
//                addGiftsToManager();
                showAddGiftsAnimation(addOverlayActionHelper);
            }
        });
    }

    private void showAddGiftsAnimation(AddOverlayActionHelper addOverlayActionHelper) {
        float startXPos = getX() + getParent().getX() + boxImage.getX();
        float startYPos = getY() + boxImage.getY() + 128 + getParent().getY();
        if (panelType == RESOURCE_AND_COINS_GIFT) {
            gameManager.addCoinsCount(numOfCoins);
            myCoinsAction = new MyCoinsAction(addOverlayActionHelper, new Vector2(startXPos, startYPos), gameManager, 55);
            myResourcesAction = new MyResourcesAction(addOverlayActionHelper, new Vector2(startXPos, startYPos), gameManager, 1);

//            gameManager.setGiftTimeFirst(giftTime);      // сохраним значение текщего времени для получения подарка
//            giftIcon.setGiftTimeFirst(giftTime);
        }
        if (panelType == RESOURCES_GIFT) {
//            gameManager.setGiftTimeSecond(giftTime);      // сохраним значение текщего времени для получения подарка
//            giftIcon.setGiftTimeSecond(giftTime);
            myResourcesAction = new MyResourcesAction(addOverlayActionHelper, new Vector2(startXPos, startYPos), gameManager, 2);
//            new MyCoinsAction(addOverlayActionHelper, new Vector2(startXPos, startYPos), gameManager, 55);
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

    public void redraw() {
        boxImage.close();
//        timerCount.redraw();
    }
}

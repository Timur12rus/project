package com.timgapps.warfare.screens.map.gui_elements;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Utils.StringHolder;
import com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class StarsPanel extends Group {
    private Label starsCountLabel;
    private Label textLabel;                         // текст "НАГРАДА ЗА ЗВЕЗДЫ"
    private int rewardStarsCount;       // кол-во звезд необходимых для получения награды
    private int starsCount;            // кол-во звезд у игрока текущее
    private Image starIcon;
    private Image background;
    private Image rewardIcon;       // изобрежение награды в панели со звездами
    private StarsBar starsBar;
    private ArrayList<RewardForStarsData> rewardForStarsDataList;
    private int indexOfNextRewardStars;     // индекс следующей награды за звезды (начинаем отсчет с "0")
    private GameManager gameManager;

    /**
     * панель с кол-вом звезд (Например: 4/11)
     **/
    public StarsPanel(GameManager gameManager) {
        this.gameManager = gameManager;
        indexOfNextRewardStars = 0;
        rewardForStarsDataList = gameManager.getRewardForStarsDataList();
        rewardStarsCount = gameManager.getRewardForStarsDataList().get(0).getStarsCount();

        rewardIcon = new Image(Warfare.atlas.findRegion("rockUnitImage"));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.WHITE;
        labelStyle.font = Warfare.font30;
        starsCountLabel = new Label("" + starsCount + "/" + rewardStarsCount, labelStyle);

        Label.LabelStyle textLabelStyle = new Label.LabelStyle();
        textLabelStyle.fontColor = Color.ORANGE;
        textLabelStyle.font = Warfare.font18;
        textLabel = new Label(Warfare.stringHolder.getString(StringHolder.REWARD_FOR_STARS), textLabelStyle);

        /** Изображение ЗНАЧОК ЗВЕЗДА **/
        starIcon = new Image(Warfare.atlas.findRegion("star_icon"));
        background = new Image(Warfare.atlas.findRegion("starsPanel"));
        starsBar = new StarsBar(gameManager);
        starsBar.setPosition(4, background.getHeight() - 36);
        redraw();
        starsCountLabel.setPosition((16 + background.getWidth() - starsCountLabel.getWidth()) / 2, starsBar.getY() - 2);
        starIcon.setPosition(8, starsBar.getY() - (starIcon.getHeight() - starsBar.getHeight()) / 2);
        rewardIcon.setPosition(starsBar.getX() + starsBar.getWidth() + 8, -12);
        textLabel.setPosition(4, starIcon.getHeight() - starIcon.getHeight() - 4);

        addActor(background);
        addActor(starsBar);
        addActor(starIcon);
        addActor(rewardIcon);
        addActor(starsCountLabel);
        addActor(textLabel);
        setSize(background.getWidth() + rewardIcon.getWidth(), background.getHeight() + 16);
    }

    // метод обновляет данные в пенели звезд (кол-во и следующую награду)
    // TODO улучшить код!
    public void redraw() {
        starsCount = gameManager.getStarsCount();                               // кол-во звезд у игрока
//        System.out.println("starsCount = " + starsCount);
        gameManager.updateIndexOfNextRewardStars();
        indexOfNextRewardStars = gameManager.getIndexOfNextRewardStars();       // индекс следующей награды
//        System.out.println("Index of reward stars in StarsPanel = " + indexOfNextRewardStars);

        /** если индекс следующей награды меньше индекса последней награды **/
//        if (indexOfNextRewardStars < rewardForStarsDataList.size() - 1) {

        redrawRewardImage(rewardForStarsDataList.get(indexOfNextRewardStars).getTypeOfReward());
        rewardStarsCount = rewardForStarsDataList.get(indexOfNextRewardStars).getStarsCount(); // необходимое кол-во звезд для плучения следующей награды
        starsCountLabel.setText("" + starsCount + "/" + rewardStarsCount);

//        } else {
//            rewardIcon.setVisible(false);
//            rewardStarsCount = rewardForStarsDataList.get(rewardForStarsDataList.size() - 1).getStarsCount();
//            starsCountLabel.setText("" + starsCount + "/" + rewardStarsCount);
//            indexOfNextRewardStars = rewardForStarsDataList.size() - 1;
//        }
        starsBar.redraw();
//        System.out.println("StarsCount = " + starsCount);
//        System.out.println("RewardStarsCount = " + rewardStarsCount);
        // если все награды собраны, делаем панель со звездами невидидмыми
        if (gameManager.isHaveFullRewardsForStars()) {
            setVisible(false);
            setTouchable(Touchable.disabled);
            starsCountLabel.setVisible(false);
            rewardIcon.setVisible(false);
            starsBar.setVisible(false);
            starIcon.setVisible(false);
            background.setVisible(false);
            textLabel.setVisible(false);
            System.out.println("SetVisible = " + isVisible());
        }
    }

    private void redrawRewardImage(int typeOfRewardForStars) {
        Image rewardSmallIcon;
        switch (typeOfRewardForStars) {
            case RewardForStarsData.REWARD_STONE:
                rewardSmallIcon = new Image(Warfare.atlas.findRegion("rockUnitImage"));
                break;
            case RewardForStarsData.REWARD_ARCHER:
                rewardSmallIcon = new Image(Warfare.atlas.findRegion("archerUnitImage"));
                break;
            case RewardForStarsData.REWARD_GNOME:
                rewardSmallIcon = new Image(Warfare.atlas.findRegion("gnomeUnitImage"));
                break;
            case RewardForStarsData.REWARD_BOX:
                rewardSmallIcon = new Image(Warfare.atlas.findRegion("boxImage0"));
                break;
            case RewardForStarsData.REWARD_KNIGHT:
                rewardSmallIcon = new Image(Warfare.atlas.findRegion("knightUnitImage"));
                break;
            case RewardForStarsData.REWARD_VIKING:
                rewardSmallIcon = new Image(Warfare.atlas.findRegion("vikingUnitImage"));
                break;
            default:
                rewardSmallIcon = new Image(Warfare.atlas.findRegion("rockUnitImage"));
        }
        rewardIcon.setDrawable(rewardSmallIcon.getDrawable());
    }

    public void onDown() {
        rewardIcon.setPosition(rewardIcon.getX(), rewardIcon.getY() - 4);
    }

    public void onUp() {
        rewardIcon.setPosition(rewardIcon.getX(), rewardIcon.getY() + 4);
    }

    public void showFinger() {
//        addActor(finger);
//        finger.show();
    }

    public void hideFinger() {
//        finger.hide();
    }
}

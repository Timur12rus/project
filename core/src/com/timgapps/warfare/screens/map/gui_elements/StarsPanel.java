package com.timgapps.warfare.screens.map.gui_elements;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
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
    public void redraw() {
        starsCount = gameManager.getStarsCount();                               // кол-во звезд у игрока
        indexOfNextRewardStars = gameManager.getIndexOfNextRewardStars();       // индекс следующей награды
        // TODO сделать ограничение индекса, т.е. если индекс > последнего индекса в массиве наград, то объявить что наград больше нет
        if (indexOfNextRewardStars < rewardForStarsDataList.size() - 1) {
            redrawRewardImage(rewardForStarsDataList.get(indexOfNextRewardStars).getTypeOfReward());
            rewardStarsCount = rewardForStarsDataList.get(indexOfNextRewardStars).getStarsCount(); // необходимое кол-во звезд для плучения следующей награды
            starsCountLabel.setText("" + starsCount + "/" + rewardStarsCount);
        } else {
            rewardIcon.setVisible(false);
            rewardStarsCount = rewardForStarsDataList.get(rewardForStarsDataList.size() - 1).getStarsCount();
            starsCountLabel.setText("" + starsCount + "/" + rewardStarsCount);
        }
        starsBar.redraw();
        System.out.println("StarsCount = " + starsCount);
        System.out.println("RewardStarsCount = " + rewardStarsCount);
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

//    /**
//     * класс индикатор количество звезд до награды, полоса
//     **/
//    class StarsBar extends Actor {
//        int barWidth, barHeight;
//        Texture cursorTexture;
//        float deltaX = 0;
//
//        public StarsBar() {
//            barWidth = 152;
//            barHeight = 32;
//            createBar(barWidth, barHeight);
//            setSize(barWidth, barHeight);
//        }
//
//        private void createBar(int width, int barHeight) {
//            // тёмный бар
//            Pixmap barPixmap = createProceduralPixmap(width - 2, barHeight - 2, 1, 191, 137, 0);
//
//            // фон бара
//            Pixmap backPixmap = createProceduralPixmap(width, barHeight, 0.4f, 0, 0, 0);
//
//            Pixmap cursorPixmap = createProceduralPixmap(12, barHeight - 2, 0.5f, 1, 1, 0);
//
//            barTexture = new Texture(barPixmap);
//            backTexture = new Texture(backPixmap);
//            cursorTexture = new Texture(cursorPixmap);
//        }
//
//        private Pixmap createProceduralPixmap(int width, int height, float a, int r, int g, int b) {
//            Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
//            pixmap.setColor(r, g, b, a);
//            pixmap.fill();
//            return pixmap;
//        }
//
//        @Override
//        public void draw(Batch batch, float parentAlpha) {
//            super.draw(batch, parentAlpha);
//            deltaX += 0.6f;
//            if (deltaX >= (starsCount * (barWidth - 2) / rewardStarsCount) - 8) {
//                deltaX = 0;
//            }
//            batch.draw(backTexture, getX(), getY());
//            batch.draw(barTexture, getX() + 1, getY() + 1,
//                    starsCount * (barWidth - 2) / rewardStarsCount, barHeight - 2);
//            batch.draw(cursorTexture, getX() + deltaX, getY() + 1);
//        }
//    }

    public void showFinger() {
//        addActor(finger);
//        finger.show();
    }

    public void hideFinger() {
//        finger.hide();
    }
}

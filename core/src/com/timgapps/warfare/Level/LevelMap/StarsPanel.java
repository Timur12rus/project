package com.timgapps.warfare.Level.LevelMap;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Level.GUI.Finger;
import com.timgapps.warfare.Level.GUI.Screens.RewardForStars.RewardForStarsData;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class StarsPanel extends Group {
    private Table table;
    private Label starsCountLabel;
    private Label textLabel;                         // текст "НАГРАДА ЗА ЗВЕЗДЫ"
    private int rewardStarsCount;       // кол-во звезд необходимых для получения награды
    private int starsCount;            // кол-во звезд у игрока текущее
    private Image starIcon;
    private Image background;
    private Image rewardIcon;
    private StarsBar starsBar;

    private Texture barTexture, backTexture;
    private ArrayList<RewardForStarsData> rewardForStarsDataList;
    private int indexOfRewardStars;
    private int countRewardIsChecked;       // кол-во доступных наград
    private RoundCircle roundCircle;
    private Finger finger;

    /**
     * панель с кол-вом звезд (Например: 4/11)
     **/
    public StarsPanel(int starsCount, int rewardStarsCount, ArrayList<RewardForStarsData> rewardForStarsDataList, int indexOfRewardStars) {
        this.starsCount = starsCount;                           // кол-во звезд у игрока
        this.rewardStarsCount = rewardStarsCount;               // кол-во звезд для следующей награды
        this.rewardForStarsDataList = rewardForStarsDataList;   // массив "ДАННЫХ" наград
        this.indexOfRewardStars = indexOfRewardStars;                // индекс следующей награды за звёзды

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.WHITE;
        labelStyle.font = Warfare.font20;
        starsCountLabel = new Label("" + starsCount + "/" + rewardStarsCount, labelStyle);

        Label.LabelStyle textLabelStyle = new Label.LabelStyle();
        textLabelStyle.fontColor = Color.ORANGE;
        textLabelStyle.font = Warfare.font10;
        textLabel = new Label("Reward for stars", textLabelStyle);

        /** Изображение ЗНАЧОК ЗВЕЗДА **/
        starIcon = new Image(Warfare.atlas.findRegion("star_icon"));

        /** зададим изображение следующей награды **/
        rewardIcon = new Image(Warfare.atlas.findRegion("rockUnitImage"));

        setRewardImage(rewardForStarsDataList.get(indexOfRewardStars).getTypeOfReward());
//        rewardIcon = rewardForStarsDataList.get(indexOfRewardStars).getRewardSmallIcon();

        background = new Image(Warfare.atlas.findRegion("coinsPanel"));
        starsBar = new StarsBar();

        starsBar.setPosition(4, background.getHeight() - 36);
        starsCountLabel.setPosition((16 + background.getWidth() - starsCountLabel.getWidth()) / 2, starsBar.getY() - 2);

        starIcon.setPosition(8, starsBar.getY() - (starIcon.getHeight() - starsBar.getHeight()) / 2);
        rewardIcon.setPosition(starsBar.getX() + starsBar.getWidth() - 4, -12);
        textLabel.setPosition(4, starIcon.getHeight() - starIcon.getHeight() - 4);

        addActor(background);
        addActor(starsBar);
        addActor(starIcon);
        addActor(rewardIcon);
        addActor(starsCountLabel);
        addActor(textLabel);

        finger = new Finger(rewardIcon.getX() + rewardIcon.getWidth() + 64,
                rewardIcon.getY() + (rewardIcon.getHeight() - 44) / 2 - 24,
                Finger.LEFT, Warfare.atlas.findRegion("fingerUpLeft"));

        finger.setPosition(rewardIcon.getX() + rewardIcon.getWidth() + 64,
                rewardIcon.getY() + (rewardIcon.getHeight() - 44) / 2 - 24);


        /** определим кол-во доступных наград **/
        for (int i = 0; i < rewardForStarsDataList.size(); i++) {
            if (rewardForStarsDataList.get(i).getIsChecked() && !rewardForStarsDataList.get(i).getIsReceived()) {
                countRewardIsChecked++;
            }
        }

        setSize(background.getWidth() + rewardIcon.getWidth(), background.getHeight() + 16);

        roundCircle = new RoundCircle(countRewardIsChecked);
        roundCircle.setPosition(getWidth() - roundCircle.getWidth(), getHeight() - roundCircle.getHeight() / 2);
        addActor(roundCircle);

        if (countRewardIsChecked > 0) {
            roundCircle.setVisible(true);
        } else {
            roundCircle.setVisible(false);
        }
    }

    public void showFinger() {
        addActor(finger);
        finger.show();
    }

    public void hideFinger() {
        finger.hide();
    }

    private void setRewardImage(int typeOfRewardForStars) {
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

    /**
     * метод получает индекс следующей награды за звезды
     */
    public int getIndexOfRewardStars() {
        return indexOfRewardStars;
    }


    /**
     * метод получает кол-во звезд у игрока
     **/
    public int getStarsCount() {
        return starsCount;
    }

    /**
     * метод добавляет количество звезд к общему кол-ву звезд
     */
    public void addStarsCount(int count) {
        this.starsCount += count;
        if (starsCount >= rewardForStarsDataList.get(indexOfRewardStars).getStarsCount()) {
            indexOfRewardStars++;
            if (indexOfRewardStars <= rewardForStarsDataList.size() - 1) {
                rewardStarsCount = rewardForStarsDataList.get(indexOfRewardStars).getStarsCount();
                int typeOfRewardForStars = rewardForStarsDataList.get(indexOfRewardStars).getTypeOfReward();
                setRewardImage(typeOfRewardForStars);
            }
        }
        starsCountLabel.setText("" + starsCount + "/" + rewardStarsCount);
    }


    // метод для обновления кол-ва наград за звёзды
    public void updateCountReward() {
        for (int i = 0; i < rewardForStarsDataList.size(); i++) {
            if (starsCount >= rewardForStarsDataList.get(i).getStarsCount()) {
                rewardForStarsDataList.get(i).setChecked();     // установим флаг - "НАГРАДА ДОСТУПНА" для получения
            }
        }
        /** определим кол-во доступных наград **/
        countRewardIsChecked = 0;

        for (int i = 0; i < rewardForStarsDataList.size(); i++) {
            System.out.println("getIsChecked = " + rewardForStarsDataList.get(i).getIsChecked());
            System.out.println("getIsReceived = " + rewardForStarsDataList.get(i).getIsReceived());
            if ((rewardForStarsDataList.get(i).getIsChecked() == true) && (rewardForStarsDataList.get(i).getIsReceived() == false)) {
                countRewardIsChecked++;
            }
            System.out.println("CountRewardIsChecked = " + countRewardIsChecked);
            roundCircle.setCountRewardStars(countRewardIsChecked);
            if (countRewardIsChecked <= 0) {
                roundCircle.setVisible(false);
            } else {
                roundCircle.setVisible(true);
            }
        }
    }

    // изображение "красный кружок"
    class RoundCircle extends Group {
        Label label;    // надпись (кол-во доступных наград)
        Image bg;
        int countRewards;

        public RoundCircle(int countRewards) {
            this.countRewards = countRewards;
            Label.LabelStyle countLabelStyle = new Label.LabelStyle();
            countLabelStyle.fontColor = Color.WHITE;
            countLabelStyle.font = Warfare.font10;
            label = new Label("" + countRewards, countLabelStyle);
            bg = new Image(Warfare.atlas.findRegion("redCircle"));
            setSize(bg.getWidth(), bg.getHeight());
            label.setPosition((getWidth() - label.getWidth()) / 2, (getHeight() - label.getHeight()) / 2);
            addActor(bg);
            addActor(label);
        }

        // метод устанавливает надпись кол-во наград за звёзды доступных в данный момент (на фоне красного куржка)
        public void setCountRewardStars(int count) {
            countRewards = count;
            label.setText("" + countRewards);
        }
    }

    /**
     * класс индикатор количество звезд до награды, полоса
     **/
    class StarsBar extends Actor {
        int barWidth, barHeight;
        int cursorWidth, cursorHeight;
        Texture cursorTexture;
        float deltaX = 0;

        public StarsBar() {
            barWidth = 152;
            barHeight = 32;
            createBar(barWidth, barHeight);
            setSize(barWidth, barHeight);
        }

        private void createBar(int width, int barHeight) {
            // тёмный бар
            Pixmap barPixmap = createProceduralPixmap(width - 2, barHeight - 2, 1, 191, 137, 0);

            // фон бара
            Pixmap backPixmap = createProceduralPixmap(width, barHeight, 0.4f, 0, 0, 0);

            Pixmap cursorPixmap = createProceduralPixmap(12, barHeight - 2, 0.5f, 1, 1, 0);

            barTexture = new Texture(barPixmap);
            backTexture = new Texture(backPixmap);
            cursorTexture = new Texture(cursorPixmap);
        }

        private Pixmap createProceduralPixmap(int width, int height, float a, int r, int g, int b) {
            Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
            pixmap.setColor(r, g, b, a);
            pixmap.fill();
            return pixmap;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            deltaX += 0.6f;
            if (deltaX >= (starsCount * (barWidth - 2) / rewardStarsCount) - 8) {
                deltaX = 0;
            }
            batch.draw(backTexture, getX(), getY());
            batch.draw(barTexture, getX() + 1, getY() + 1,
                    starsCount * (barWidth - 2) / rewardStarsCount, barHeight - 2);
            batch.draw(cursorTexture, getX() + deltaX, getY() + 1);
        }
    }
}

package com.timgapps.warfare.Level.GUI.Screens.GiftsWindow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Warfare;

public class GiftRewardTable extends Group {
    private Label rewardLabel;
    private int coinsCount, resourcesCount;
    Label coinsCountLabel, resourcesCountLabel;
    private float height;

    // горизонтальная таблица "REWARD:"  и значки ресурсы и монеты и их количество
    public GiftRewardTable(int coinsCount, int resourcesCount) {
        this.coinsCount = coinsCount;
        this.resourcesCount = resourcesCount;

        Label.LabelStyle rewardsLabelStyle = new Label.LabelStyle();
        rewardsLabelStyle.fontColor = Color.GRAY;
        rewardsLabelStyle.font = Warfare.font20;

        rewardLabel = new Label("", rewardsLabelStyle);
        rewardLabel.setText("Reward:");
        Table rewardTable = new Table();
//        Table rewardTable = new Table().debug();
        Image coinImage = new Image(Warfare.atlas.findRegion("coin_icon"));

        /** Label для надписи КОЛИЧЕСТВА МОНЕТ **/
        Label.LabelStyle coinsCountLabelStyle = new Label.LabelStyle();
        coinsCountLabelStyle.fontColor = Color.ORANGE;
        coinsCountLabelStyle.font = Warfare.font20;
        coinsCountLabel = new Label("", coinsCountLabelStyle);
        coinsCountLabel.setText("" + coinsCount);
        Label.LabelStyle resoursesCountLabelStyle = new Label.LabelStyle();
        resoursesCountLabelStyle.fontColor = new Color(0xa06846ff);
        resoursesCountLabelStyle.font = Warfare.font20;
        resourcesCountLabel = new Label("", resoursesCountLabelStyle);
        resourcesCountLabel.setText("" + resourcesCount);
        Image resourseImage = new Image(Warfare.atlas.findRegion("boxIcon"));
        rewardTable.add(resourcesCountLabel);
        rewardTable.add(resourseImage).width(48).height(48).padRight(24).padLeft(4);
        if (coinsCount > 0) {
            rewardTable.add(coinsCountLabel);
            rewardTable.add(coinImage).width(48).height(48).padLeft(2);
        }
        rewardTable.setHeight(coinImage.getHeight());
        rewardTable.setWidth(coinImage.getWidth() * 2 + 24 + coinsCountLabel.getWidth() + resourcesCountLabel.getWidth());
        setHeight(coinImage.getHeight());
        setWidth(coinImage.getWidth() * 2 + 24 + coinsCountLabel.getWidth() + resourcesCountLabel.getWidth());
        rewardLabel.setPosition(0, 0);
        rewardTable.setPosition(0, -rewardTable.getHeight() - 24);

        height = rewardLabel.getHeight() + rewardTable.getHeight() + 24;

        addActor(rewardLabel);
        addActor(rewardTable);

    }

    public void setCoinsCount(int coins) {
        coinsCount = coins;
        coinsCountLabel.setText("" + coinsCount);
    }

    @Override
    public float getHeight() {
        return height;
    }
}
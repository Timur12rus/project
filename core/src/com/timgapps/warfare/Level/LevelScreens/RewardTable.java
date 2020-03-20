package com.timgapps.warfare.Level.LevelScreens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Warfare;

public class RewardTable extends Group {
    private Label rewardLabel;
    private int coinsCount, scoreCount;

    public RewardTable(int coinsCount, int scoreCount) {
        this.coinsCount = coinsCount;
        this.scoreCount = scoreCount;

        Label.LabelStyle rewardsLabelStyle = new Label.LabelStyle();
        rewardsLabelStyle.fontColor = Color.GRAY;
        rewardsLabelStyle.font = Warfare.font20;

        rewardLabel = new Label("", rewardsLabelStyle);
        rewardLabel.setText("Reward:");
        Table rewardTable = new Table();
//        Table rewardTable = new Table().debug();


        /** Label для надписи КОЛИЧЕСТВА МОНЕТ **/
        Label.LabelStyle coinsCountLabelStyle = new Label.LabelStyle();
        coinsCountLabelStyle.fontColor = Color.ORANGE;
        coinsCountLabelStyle.font = Warfare.font20;

        Label.LabelStyle scoreCountLabelStyle = new Label.LabelStyle();
//        scoreCountLabelStyle.fontColor = Color.CYAN;
        scoreCountLabelStyle.fontColor = new Color(0x35a1afff);
        scoreCountLabelStyle.font = Warfare.font20;

        Label coinsCountLabel = new Label("", coinsCountLabelStyle);
        coinsCountLabel.setText("" + coinsCount);

        Label scoreCountLabel = new Label("", scoreCountLabelStyle);
        scoreCountLabel.setText("" + scoreCount);

        Image coinImage = new Image(Warfare.atlas.findRegion("coin_icon"));
        Image scoreImage = new Image(Warfare.atlas.findRegion("coin_icon"));
        rewardTable.add(coinsCountLabel);
        rewardTable.add(coinImage).width(48).height(48);

        rewardTable.add(scoreCountLabel).padLeft(48);
        rewardTable.add(scoreImage).width(48).height(48);

        rewardTable.setHeight(coinImage.getHeight());
        rewardTable.setWidth(coinImage.getWidth() * 2 + 48 + coinsCountLabel.getWidth() + scoreCountLabel.getWidth());

        setHeight(coinImage.getHeight());
        setWidth(coinImage.getWidth() * 2 + 48 + coinsCountLabel.getWidth() + scoreCountLabel.getWidth());

        rewardLabel.setPosition(0, 0);
        rewardTable.setPosition( 0, - rewardTable.getHeight() - 24);

        addActor(rewardLabel);
        addActor(rewardTable);

    }


}

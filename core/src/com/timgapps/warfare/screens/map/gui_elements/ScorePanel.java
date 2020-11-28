package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Warfare;

public class ScorePanel extends Group {
    private Table table;
    private int scoreCount;
    private Label scoreValueLabel;
    private ScoreIcon scoreIcon;
    private TextureRegionDrawable background;
    private Label playerLevelLabel;
    private int playerLevel = 1;

    public ScorePanel(int scoreCount) {
        this.scoreCount = scoreCount;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = new Color(0x35a1afff);
        labelStyle.font = Warfare.font20;
        scoreValueLabel = new Label("" + scoreCount, labelStyle);

        Label.LabelStyle playerLabelStyle = new Label.LabelStyle();
        playerLabelStyle.fontColor = Color.WHITE;
        playerLabelStyle.font = Warfare.font20;
        playerLevelLabel = new Label("" + playerLevel, playerLabelStyle);

        /** Изображение ЗНАЧОК ОЧКИ **/
        scoreIcon = new ScoreIcon();
//        scoreIcon = new Image(Warfare.atlas.findRegion("score_icon"));
        background = new TextureRegionDrawable(Warfare.atlas.findRegion("coinsPanel"));

//        table = new Table();
        table = new Table().debug();
        table.align(Align.left);
        table.setWidth(background.getRegion().getRegionWidth());
        table.setHeight(46);
        table.setBackground(background);
        table.add(scoreIcon).width(scoreIcon.getWidth());
        table.add(scoreValueLabel).expand().left();


        setWidth(table.getWidth());
        setHeight(table.getHeight());
        addActor(table);
    }



    public void addScore(int score) {
        scoreCount += score;
        scoreValueLabel.setText("" + scoreCount);
    }

    public int getScoreCount() {
        return scoreCount;
    }

    class ScoreIcon extends Group {
        Image bg;

        public ScoreIcon() {
//            Label.LabelStyle playerLabelStyle = new Label.LabelStyle();
//            playerLabelStyle.fontColor = Color.WHITE;
//            playerLabelStyle.font = Warfare.font20;
//            playerLevelLabel = new Label("" + playerLevel, playerLabelStyle);

            bg = new Image(Warfare.atlas.findRegion("score_icon"));

            playerLevelLabel.setPosition((bg.getWidth() - playerLevelLabel.getWidth()) / 2,
                    (bg.getHeight() - playerLevelLabel.getHeight()) / 2);

            setSize(bg.getWidth(), bg.getHeight());
            addActor(bg);
            addActor(playerLevelLabel);
        }
    }
}



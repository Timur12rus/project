package com.timgapps.warfare.Level.GUI.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Warfare;

public class CoinsPanel extends Group {
    private Table table;
    private int coinsCount;
    private Label coinsValueLabel;

    public CoinsPanel(int coinsCount) {
        this.coinsCount = coinsCount;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.YELLOW;
        labelStyle.font = Warfare.font20;
        coinsValueLabel = new Label("" + coinsCount, labelStyle);

        table = new Table();
//        table = new Table().debug();
        table.align(Align.right);
        table.setWidth(160);
        table.setHeight(46);
        table.setBackground(new TextureRegionDrawable(Warfare.atlas.findRegion("coinsPanel")));
        table.add(coinsValueLabel).expand().right();
        table.add(new Image(Warfare.atlas.findRegion("coin_icon"))).width(48);

        setWidth(table.getWidth());
        setHeight(table.getHeight());

        addActor(table);

    }

    public void setCoinsCount(int coins) {
        coinsCount = coins;
        coinsValueLabel.setText("" + coinsCount);
    }

}

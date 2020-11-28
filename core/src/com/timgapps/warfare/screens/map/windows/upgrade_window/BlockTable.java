package com.timgapps.warfare.screens.map.windows.upgrade_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Warfare;

/**
 * таблица : собрите n-ое кол-во звезд для разблокировки
 **/
public class BlockTable extends Table {
    private Image star;
    private Image blockIcon;
    private Label label;
    private int starsCount;

    public BlockTable() {
        Label label1;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;
        label = new Label("" + "collect " + starsCount, labelStyle);
        label1 = new Label(" for unlock unit", labelStyle);
        star = new Image(Warfare.atlas.findRegion("star_active"));
        blockIcon = new Image(Warfare.atlas.findRegion("lockIcon"));
        add(blockIcon).width(blockIcon.getWidth()).padRight(12);
        add(label);
        add(star).width(star.getWidth()).padLeft(4).padRight(4);
        add(label1);
        setSize(200, 64);
    }

    void setLabelStarsCount(int count) {
        starsCount = count;
        label.setText("" + "collect " + starsCount);
    }
}
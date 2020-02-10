package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class HUD extends Group {
    private Level level;
    private Label energyCountLabel;
    private Label coinCountLabel;

    private Image energyIcon;
    private Image coinIcon;

    private Table energyTable;
    private int coinsCount;

    public HUD(Level level) {
        this.level = level;
        coinsCount = level.getCoinCount();

        /** Таблица для отображения количества энергии и монет **/
        energyTable = new Table().debug();

        energyTable.setWidth(level.getWidth() - 64);

        /** Изображение ЗНАЧОК ЭНЕРГИИ **/
        energyIcon = new Image(Warfare.atlas.findRegion("energyIcon"));
        coinIcon = new Image(Warfare.atlas.findRegion("coin_icon"));

        setHeight(coinIcon.getHeight());
//        setHeight(coinIcon.getHeight());

        /** Надпись о КОЛИЧЕСТВЕ ЭНЕРГИИ **/
        Label.LabelStyle energyLabelStyle = new Label.LabelStyle();
        energyLabelStyle.fontColor = new Color(0x35a1afff);
        energyLabelStyle.font = Warfare.font20;
        energyCountLabel = new Label("" + level.getEnergyCount(), energyLabelStyle);        // надпись "КОЛИЧЕСТВО ЭНЕРГИИ"


        /** Изображение ЗНАЧОК МОНЕТА **/
        energyIcon = new Image(Warfare.atlas.findRegion("energyIcon"));

        /** Надпись о КОЛИЧЕСТВЕ МОНЕТ **/
        Label.LabelStyle coinLabelStyle = new Label.LabelStyle();
        coinLabelStyle.fontColor = Color.YELLOW;
        coinLabelStyle.font = Warfare.font20;
        coinCountLabel = new Label("" + coinsCount, coinLabelStyle);        // надпись "КОЛИЧЕСТВО ЭНЕРГИИ"
        coinCountLabel.setAlignment(Align.right);

        /** Добавляем элементы в таблицу **/
        energyTable.add(energyIcon).width(energyIcon.getWidth());
        energyTable.add(energyCountLabel).padLeft(8).width(64);
        energyTable.add().expandX();
        energyTable.add(coinCountLabel).expandX().width(96).right().padRight(8);
        energyTable.add(coinIcon).width(coinIcon.getWidth());

        addActor(energyTable);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        energyCountLabel.setText("" + level.getEnergyCount());
    }

    public void updateCoinsCount(int count) {
        coinsCount = count;
    }
}

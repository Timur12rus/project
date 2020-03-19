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
import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
import com.timgapps.warfare.Level.GUI.Screens.EnergyPanel;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class HUD extends Group {
    private Level level;
    private Image coinIcon;

    private Table energyTable;
    private int coinsCount;

    private EnergyPanel energyPanel;
    private CoinsPanel coinsPanel;

    public HUD(Level level) {
        this.level = level;
        coinsCount = level.getCoinCount();

        coinsPanel = new CoinsPanel(coinsCount);
        energyPanel = new EnergyPanel(level);

        /** Таблица для отображения количества энергии и монет **/
        energyTable = new Table();
//        energyTable = new Table().debug();

        energyTable.setWidth(level.getWidth() - 64);
        coinIcon = new Image(Warfare.atlas.findRegion("coin_icon"));

        setHeight(coinIcon.getHeight());
        energyTable.add(energyPanel);
        energyTable.add().expandX();
        energyTable.add(coinsPanel);

        addActor(energyTable);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void updateCoinsCount(int count) {
        coinsCount = count;
    }
}

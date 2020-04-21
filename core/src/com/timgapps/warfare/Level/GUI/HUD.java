package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
import com.timgapps.warfare.Level.GUI.Screens.EnergyPanel;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class HUD extends Group {
    private Level level;
    private Image coinIcon;

    private Table hudTable;
    private int coinsCount;

    private EnergyPanel energyPanel;
    private CoinsPanel coinsPanel;

    public HUD(Level level) {
        this.level = level;
        coinsCount = level.getCoinsCount();

        coinsPanel = level.getGameManager().getCoinsPanel();
//        coinsPanel = new CoinsPanel(coinsCount);
        energyPanel = new EnergyPanel(level);

        /** Таблица для отображения количества энергии и монет **/
        hudTable = new Table();
//        energyTable = new Table().debug();

        hudTable.setWidth(level.getWidth() - 64);
        coinIcon = new Image(Warfare.atlas.findRegion("coin_icon"));

        setHeight(coinIcon.getHeight());
        hudTable.add(energyPanel);
        hudTable.add().expandX();
        hudTable.add(coinsPanel);

        addActor(hudTable);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void updateCoinsCount(int count) {
        coinsCount = count;
    }

    public void hideEnergyPanel() {
        energyPanel.setVisible(false);
    }
}

package com.timgapps.warfare.screens.level;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.screens.map.gui_elements.CoinsPanel;
import com.timgapps.warfare.screens.map.gui_elements.EnergyPanel;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Warfare;

public class HUD extends Group {
    private LevelScreen levelScreen;
    private Image coinIcon;

    private Table hudTable;
    private int coinsCount;

    private EnergyPanel energyPanel;
    private CoinsPanel coinsPanel;

    public HUD(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
        coinsCount = levelScreen.getCoinsCount();

        coinsPanel = levelScreen.getGameManager().getCoinsPanel();
//        coinsPanel = new CoinsPanel(coinsCount);
        energyPanel = new EnergyPanel(levelScreen);

        /** Таблица для отображения количества энергии и монет **/
        hudTable = new Table();
//        energyTable = new Table().debug();

        hudTable.setWidth(levelScreen.getWidth() - 64);
        coinIcon = new Image(Warfare.atlas.findRegion("coin_icon"));

        setHeight(coinIcon.getHeight());
        hudTable.add(energyPanel);
        hudTable.add().expandX();
        hudTable.add(coinsPanel);

        addActor(hudTable);
    }

    public void redraw() {
        hudTable.clearChildren();
        hudTable.add(energyPanel);
        hudTable.add().expandX();
        hudTable.add(coinsPanel);
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

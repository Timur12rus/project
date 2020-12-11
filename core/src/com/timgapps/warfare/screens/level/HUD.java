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
    private EnergyPanel energyPanel;
    private CoinsPanel coinsPanel;

    public HUD(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
        coinsPanel = levelScreen.getGameManager().getCoinsPanel();
        energyPanel = new EnergyPanel(levelScreen);
        coinsPanel.setPosition(coinsPanel.getPos().x, coinsPanel.getPos().y);
        energyPanel.setPosition(32, levelScreen.getHeight() - 32 - energyPanel.getHeight());
        addActor(energyPanel);
    }

    public void redraw() {
        addActor(coinsPanel);
        showEnergyPanel();
    }

    public void clear() {
        removeActor(coinsPanel);
        hideEnergyPanel();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void hideEnergyPanel() {
        energyPanel.setVisible(false);
    }

    public void showEnergyPanel() {
        energyPanel.setVisible(true);
    }
}

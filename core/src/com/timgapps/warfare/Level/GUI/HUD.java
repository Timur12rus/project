package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class HUD extends Group {
    private TextureRegion energyPanel;
    private TextureRegion coinPanel;
    private TextManager textManager;
    private Level level;

    public HUD(Level level) {
        this.level = level;
        this.energyPanel = Warfare.atlas.findRegion("energy_icon");
        this.coinPanel = Warfare.atlas.findRegion("coin_icon");
        textManager = new TextManager(level);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        level.getEnergyCount();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(energyPanel, 32, Warfare.V_HEIGHT - 64);
        batch.draw(coinPanel, Warfare.V_WIDTH - 64 - coinPanel.getRegionWidth(), Warfare.V_HEIGHT - 64);
        textManager.displayMessage(batch, level.getEnergyCount(), level.getCoinCount());
    }

    public void dispose() {
        textManager.dispose();
    }
}

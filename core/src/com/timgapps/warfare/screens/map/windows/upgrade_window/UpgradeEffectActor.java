package com.timgapps.warfare.screens.map.windows.upgrade_window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class UpgradeEffectActor extends Actor {
    private ParticleEffect upgradeEffect;
    private UpgradeWindow upgradeWindow;

    public UpgradeEffectActor(UpgradeWindow upgradeWindow) {
        upgradeEffect = new ParticleEffect();
        upgradeEffect.load(Gdx.files.internal("effects/upgradeEffect3.paty"), Gdx.files.internal("effects/")); //file);
    }

    public void start() {
        upgradeEffect.start();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        upgradeEffect.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        upgradeEffect.update(delta);
    }

    public void dispose() {
        upgradeEffect.dispose();
    }
}

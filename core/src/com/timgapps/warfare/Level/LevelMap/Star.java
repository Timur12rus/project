package com.timgapps.warfare.Level.LevelMap;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.timgapps.warfare.Warfare;

public class Star extends Actor {
    private TextureRegion starActive, starInactive;
    private boolean isActive = false;

    public Star(Group parent) {
        starActive = new TextureRegion(Warfare.atlas.findRegion("star_active"));
        starInactive = new TextureRegion(Warfare.atlas.findRegion("star_inactive"));
        parent.addActor(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isActive)
            batch.draw(starActive, getX(), getY());
        else
            batch.draw(starInactive, getX(), getY());
    }

    public void setActive() {
        isActive = true;
    }

    public void setInactive() {
        isActive = false;
    }

    public boolean isActive() {
        return true;
    }

    @Override
    public float getWidth() {
//        return super.getWidth();
        return starActive.getRegionWidth();
    }
}

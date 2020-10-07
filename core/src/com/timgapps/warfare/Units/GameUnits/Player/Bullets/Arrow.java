package com.timgapps.warfare.Units.GameUnits.Player.Bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class Arrow extends Bullet {
    public Arrow(Level level, Vector2 position) {
        super(level, position);
        image = new TextureRegion(Warfare.atlas.findRegion("arrow"));
        velocity.set(5f, 0);
        level.addChild(this, position.x, position.y);
        isDebug = true;
        deltaX = 12;
        deltaY = 42;
    }

    @Override
    Rectangle createBody() {
        Rectangle body = new Rectangle();
        body.set(position.x, position.y, 16, 24);
        return body;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}

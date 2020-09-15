package com.timgapps.warfare.Units.GameUnits.Player;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BarricadeHealthBar extends Actor {

    private Texture healthTexture;
    private Texture backTexture;

    private float health;
    private float fullHealth;

    private int healthBarWidth, healthBarHeight; // ширина и высота HealthBar
    private float x, y;                         // координаты х, у

    private boolean isDrawHealthBar = false;

    public BarricadeHealthBar(int healthBarWidth, int healthBarHeight, float health) {
        this.health = health;
        fullHealth = health;
        createHealthBar(healthBarWidth, healthBarHeight);

    }

    public void setHealth(float health) {
        this.health = health;
    }

    private void createHealthBar(int healthBarWidth, int healthBarHeight) {
        this.healthBarWidth = healthBarWidth;
        this.healthBarHeight = healthBarHeight;
        Pixmap healthPixmap = createProceduralPixmap(healthBarWidth - 2, healthBarHeight - 2, 1, 0, 0);
        Pixmap backPixmap = createProceduralPixmap(healthBarWidth, healthBarHeight, 0, 0, 0);
        healthTexture = new Texture(healthPixmap);
        backTexture = new Texture(backPixmap);
    }

    private Pixmap createProceduralPixmap(int width, int height, int r, int g, int b) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, 1);
        pixmap.fill();
        return pixmap;
    }

    public void setIsDrawHealthBar(boolean isDraw) {
        isDrawHealthBar = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isDrawHealthBar) {
            batch.draw(backTexture, getX(), getY());
            batch.draw(healthTexture, getX() + x + 1, getY() + y + 1,
                    health * (healthBarWidth - 2) / fullHealth, healthBarHeight - 2);
        }
    }
}


package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class HealthBar {
    private Texture healthTexture;
    private Texture backTexture;
    protected int healthBarWidth;
    protected int healthBarHeight;
    private float fullHealth;

    public HealthBar(int healthBarWidth, int healthBarHeight, float fullHealth) {
        this.healthBarWidth = healthBarWidth;
        this.healthBarHeight = healthBarHeight;
        this.fullHealth = fullHealth;
        createHealthBar(healthBarWidth, healthBarHeight);
    }

    /**
     * метод для создания Pixmap для текстур healthBar'a
     *
     * @param width  - ширина Pixmap
     * @param height - высота Pixmap
     **/
    private Pixmap createProceduralPixmap(int width, int height, int r, int g, int b) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, 1);
        pixmap.fill();
        return pixmap;
    }

    private void createHealthBar(int healthBarWidth, int healthBarHeight) {
        Pixmap healthPixmap = createProceduralPixmap(healthBarWidth - 2, healthBarHeight - 2, 1, 0, 0);
        Pixmap backPixmap = createProceduralPixmap(healthBarWidth, healthBarHeight, 0, 0, 0);
        healthTexture = new Texture(healthPixmap);
        backTexture = new Texture(backPixmap);
    }

    public void drawHealthBar(Batch batch, float x, float y, float health) {
        batch.draw(backTexture, x, y);
        batch.draw(healthTexture, x + 1, y + 1, health * (healthBarWidth - 2) / fullHealth, healthBarHeight - 2);
    }
}

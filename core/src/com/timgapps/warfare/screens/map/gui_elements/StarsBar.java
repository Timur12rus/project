package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.timgapps.warfare.GameManager;

/**
 * класс индикатор количество звезд до награды, полоса
 **/
public class StarsBar extends Actor {
    private Texture cursorTexture;
    private float deltaX = 0;
    private Texture barTexture, backTexture;
    private int starsCount;                     // кол-во звезд у игрока
    private int rewardStarsCount;               // кол-во звезд необходимое для следующей награды
    private GameManager gameManager;
    private final int BAR_WIDTH = 152;
    private final int BAR_HEIGHT = 32;

    public StarsBar(GameManager gameManager) {
        this.gameManager = gameManager;
        redraw();
        createBar(BAR_WIDTH, BAR_HEIGHT);
        setSize(BAR_WIDTH, BAR_HEIGHT);
    }

    public void redraw() {
        starsCount = gameManager.getStarsCount();
        rewardStarsCount = gameManager.getStarsCountForReward();
    }

    private void createBar(int width, int barHeight) {
        // тёмный бар
        Pixmap barPixmap = createProceduralPixmap(width - 2, barHeight - 2, 1, 191, 137, 0);

        // фон бара
        Pixmap backPixmap = createProceduralPixmap(width, barHeight, 0.4f, 0, 0, 0);

        Pixmap cursorPixmap = createProceduralPixmap(12, barHeight - 2, 0.5f, 1, 1, 0);

        barTexture = new Texture(barPixmap);
        backTexture = new Texture(backPixmap);
        cursorTexture = new Texture(cursorPixmap);
    }

    private Pixmap createProceduralPixmap(int width, int height, float a, int r, int g, int b) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, a);
        pixmap.fill();
        return pixmap;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        deltaX += 0.6f;
        if (deltaX >= (starsCount * (BAR_WIDTH - 2) / rewardStarsCount) - 8) {
            deltaX = 0;
        }
        batch.draw(backTexture, getX(), getY());
        batch.draw(barTexture, getX() + 1, getY() + 1,
                starsCount * (BAR_WIDTH - 2) / rewardStarsCount, BAR_HEIGHT - 2);
        batch.draw(cursorTexture, getX() + deltaX, getY() + 1);
    }
}
package com.timgapps.warfare.screens.reward_for_stars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.GameManager;

class StarsBar extends Actor {
    Texture bgBarTexture, barTexture;
    Texture bgTexture;
    float x, y;
    boolean isReceived;
    Pixmap progressPixmap;
    protected final int barWidth = 184;
    protected final int barHeight = 32;
    private RewardForStarsData data;
    private int starsCount;                 // текущее кол-во звезд
    private int lastRewardCountStars;       // кол-во звезд за предыдущую награду
    private int rewardStarsCount;           // кол-во звезд за награду
    private int deltaCountStars;            // разница между текущим кол-вом и кол-вом за последнюю награду

    StarsBar(float x, float y, RewardForStarsData data, int lastRewardCountStars) {
//    StarsBar(float x, float y, boolean isReceived, int deltaCountStars, int lastRewardCountStars, int rewardStarsCount) {
        setSize(bgBarTexture.getWidth(), bgBarTexture.getHeight());
        this.data = data;
        this.x = x;
        this.y = y;
        this.lastRewardCountStars = lastRewardCountStars;
        this.rewardStarsCount = data.getStarsCount(); // кол-во звезд, необходимое для награды
        createStarsBar(x);
    }

    // метод перерисовывает элемент
    public void redraw(int starsCount) {
        this.isReceived = data.getIsReceived(); // получена ли награда
        this.starsCount = starsCount;
        deltaCountStars = starsCount - lastRewardCountStars;
    }

    // метод окрашивает бар в темный цвет, что означает что награда получена
    void setIsReceived(boolean flag) {
        isReceived = flag;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(bgBarTexture, x, y);
        if (isReceived) {
            batch.setColor(1, 0.5f, 0, 0.7f);
        }
        batch.draw(barTexture, x + 1, y + 1);
        batch.setColor(1, 1, 1, 1);
    }

    private void createStarsBar(float x) {
        /** проеверим, если награда получена, то окрасим темно-оранжевым цветом Bar*/
        if (!isReceived) {  // не получена, bar - желтый
            int calculatedWidth;
            // rewardStarsCount - кол-во здёзд необходимое для получения награды
            // deltaCountStars      - кол-во звезд между текущим кол-вом и кол-вом за последнюю награду
            if (starsCount < rewardStarsCount) {
//                health * (healthBarWidth - 2) / fullHealth
                if (deltaCountStars >= 0) {
                    calculatedWidth = deltaCountStars * (barWidth - 2) / (rewardStarsCount - lastRewardCountStars);
                    if (calculatedWidth <= 0) calculatedWidth = 2;
                } else {
                    calculatedWidth = 2;
                }
            } else {
                calculatedWidth = barWidth;
            }

//            if ((starsCount >= lastRewardCountStars) && (starsCount <= rewardStarsCount)) {
//                xPos = calculatedWidth + x;
//                rewardForStarsScreen.updateXposSmallStarsPanel(xPos);
//            }
            progressPixmap = createProceduralPixmap(calculatedWidth - 2, barHeight - 2, new Color(0xf2d900ff));
        } else {    // получена - темно-оранжевый цвет
            progressPixmap = createProceduralPixmap(barWidth - 2, barHeight - 2, new Color(0xf2d900ff));
//                progressPixmap = createProceduralPixmap(barWidth - 2, barHeight - 2, new Color(0xa29100ff));
        }
        Pixmap backPixmap = createProceduralPixmap(barWidth, barHeight, new Color(0x464642));
        barTexture = new Texture(progressPixmap);
        bgBarTexture = new Texture(backPixmap);
    }


    private Pixmap createProceduralPixmap(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }
}

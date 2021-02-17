package com.timgapps.warfare.screens.reward_for_stars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

// класс StarsBar - полоса, индикатор кол-во звдезд за награду
class StarsBar extends Actor {
    private Texture bgBarTexture, barTexture;
    float x, y;
    boolean isReceived;
//    private Pixmap progressPixmap;
    protected final int BAR_WIDTH = 224;
//    private final int BAR_WIDTH = 224;
    protected final int barHeight = 32;
    private RewardForStarsData data;
    private int starsCount;                 // текущее кол-во звезд
    private int lastRewardCountStars;       // кол-во звезд за предыдущую награду
    private int rewardStarsCount;           // кол-во звезд за награду
    private int deltaCountStars;            // разница между текущим кол-вом и кол-вом за последнюю награду

    StarsBar(float x, float y, RewardForStarsData data, int lastRewardCountStars) {
        this.data = data;
        this.x = x;
        this.y = y;
        createStarsBar();
        setSize(bgBarTexture.getWidth(), bgBarTexture.getHeight());
        this.lastRewardCountStars = lastRewardCountStars;
        this.rewardStarsCount = data.getStarsCount(); // кол-во звезд, необходимое для награды
    }

    // метод перерисовывает элемент
    public void redraw(int starsCount) {
        this.isReceived = data.getIsReceived(); // получена ли награда
        this.starsCount = starsCount;
        this.rewardStarsCount = data.getStarsCount();
        deltaCountStars = starsCount - lastRewardCountStars;
        bgBarTexture.dispose();
        barTexture.dispose();
//        progressPixmap.dispose();

//        System.out.println("progressPixmap = " + progressPixmap.toString());
//        System.out.println("barTexture = " + barTexture.toString());
        createStarsBar();
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

    private void createStarsBar() {
        Pixmap progressPixmap;
        /** проеверим, если награда получена, то окрасим темно-оранжевым цветом Bar*/
        if (!isReceived) {  // не получена, bar - желтый
            int calculatedWidth;
            // rewardStarsCount - кол-во здёзд необходимое для получения награды
            // deltaCountStars      - кол-во звезд между текущим кол-вом и кол-вом за последнюю награду
            if (starsCount < rewardStarsCount) {
                if (deltaCountStars >= 0) {
                    calculatedWidth = deltaCountStars * (BAR_WIDTH - 2) / (rewardStarsCount - lastRewardCountStars);
                    if (calculatedWidth <= 0) calculatedWidth = 2;
                } else {
                    calculatedWidth = 2;
                }
            } else {
                calculatedWidth = BAR_WIDTH;
            }

//            if ((starsCount >= lastRewardCountStars) && (starsCount <= rewardStarsCount)) {
//                xPos = calculatedWidth + x;
//                rewardForStarsScreen.updateXposSmallStarsPanel(xPos);
//            }
            progressPixmap = createProceduralPixmap(calculatedWidth - 2, barHeight - 2, new Color(0xf2d900ff));
        } else {    // получена - темно-оранжевый цвет
            progressPixmap = createProceduralPixmap(BAR_WIDTH - 2, barHeight - 2, new Color(0xf2d900ff));
        }
        Pixmap backPixmap = createProceduralPixmap(BAR_WIDTH, barHeight, new Color(0x464642));
        barTexture = new Texture(progressPixmap);
        bgBarTexture = new Texture(backPixmap);
        progressPixmap.dispose();
        backPixmap.dispose();
    }


    private Pixmap createProceduralPixmap(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }
}

package com.timgapps.warfare.Level.LevelMap;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Warfare;

public class StarsPanel extends Group {
    private Table table;
    private Label starsCountLabel;
    private Label textLabel;                         // текст "НАГРАДА ЗА ЗВЕЗДЫ"
    private int starsRewardCount = 10;       // кол-во звезд необходимых для получения награды
    private int starsCount;            // кол-во звезд у игрока текущее
    private Image starIcon;
    private Image background;
    private Image rewardIcon;
    private StarsBar starsBar;

    private Texture barTexture, backTexture;


    public StarsPanel(int starsCount) {
        this.starsCount = starsCount;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.WHITE;
        labelStyle.font = Warfare.font20;
        starsCountLabel = new Label("" + starsCount + "/" + starsRewardCount, labelStyle);

        Label.LabelStyle textLabelStyle = new Label.LabelStyle();
        textLabelStyle.fontColor = Color.ORANGE;
        textLabelStyle.font = Warfare.font10;
        textLabel = new Label("Reward for stars", textLabelStyle);


        /** Изображение ЗНАЧОК ЗВЕЗДА **/
        starIcon = new Image(Warfare.atlas.findRegion("star_icon"));
        rewardIcon = new Image(Warfare.atlas.findRegion("score_icon"));
        background = new Image(Warfare.atlas.findRegion("coinsPanel"));
        starsBar = new StarsBar();

        starsBar.setPosition(4, background.getHeight() - 36);
        starsCountLabel.setPosition((16 + background.getWidth() - starsCountLabel.getWidth()) / 2, starsBar.getY() - 2);

        starIcon.setPosition(8, starsBar.getY() - (starIcon.getHeight() - starsBar.getHeight()) / 2);
        rewardIcon.setPosition(starsBar.getX() + starsBar.getWidth() - 4, 0);
        textLabel.setPosition(4, starIcon.getHeight() - starIcon.getHeight() - 4);

        addActor(background);
        addActor(starsBar);
        addActor(starIcon);
        addActor(rewardIcon);
        addActor(starsCountLabel);
        addActor(textLabel);

        setSize(background.getWidth() + rewardIcon.getWidth(), background.getHeight() + 16);
    }

    /**  метод добавляет количество звезд к общему кол-ву звезд */
    public void addStarsCount(int count) {
        this.starsCount += count;
    }

    /**
     * класс индикатор количество звезд до награды, полоса
     **/
    class StarsBar extends Actor {
        int barWidth, barHeight;

        public StarsBar() {
            barWidth = 152;
            barHeight = 32;
            createBar(barWidth, barHeight);
            setSize(barWidth, barHeight);
        }

        private void createBar(int width, int barHeight) {
            Pixmap barPixmap = createProceduralPixmap(width - 2, barHeight - 2, 1, 191, 137, 0);
//            Pixmap barPixmap = createProceduralPixmap(width - 2, barHeight - 2, 1, 0, 0);
            Pixmap backPixmap = createProceduralPixmap(width, barHeight, 0.4f, 0, 0, 0);

            barTexture = new Texture(barPixmap);
            backTexture = new Texture(backPixmap);
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
            batch.draw(backTexture, getX(), getY());
            batch.draw(barTexture, getX() + 1, getY() + 1,
                    starsCount * (barWidth - 2) / starsRewardCount, barHeight - 2);
        }
    }
}

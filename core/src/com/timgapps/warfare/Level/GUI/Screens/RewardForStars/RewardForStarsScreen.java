package com.timgapps.warfare.Level.GUI.Screens.RewardForStars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class RewardForStarsScreen extends StageGame {
    public static final int ON_BACK = 1;
    private ArrayList<RewardForStarsData> rewardForStarsDataList;
    private ArrayList<RewardForStars> rewardForStarsList;
    private BackButton backButton;
    private Texture bgTexture;
    protected final int barWidth = 180;
    protected final int barHeight = 32;

    public RewardForStarsScreen(GameManager gameManager) {
        createBackground();

        backButton = new BackButton();
        backButton.setPosition(64, 64);
        addOverlayChild(backButton);

        rewardForStarsDataList = gameManager.getRewardForStarsDataList();
        rewardForStarsList = new ArrayList<RewardForStars>();

        for (int i = 0; i < rewardForStarsDataList.size(); i++) {
            rewardForStarsList.add(new RewardForStars(rewardForStarsDataList.get(i)));
            rewardForStarsList.get(i).setPosition(100 + 200 * i + rewardForStarsList.get(i).getWidth(), 300);
            addChild(rewardForStarsList.get(i));

            StarsBar bar = new StarsBar(rewardForStarsList.get(i).getX() - (barWidth - rewardForStarsList.get(i).getWidth()) / 2,
                    rewardForStarsList.get(i).getY() - barHeight - 16);
            addChild(bar);
        }
    }

    class StarsBar extends Actor {
        Texture bgBarTexture, barTexture;
        float x, y;

        public StarsBar(float x, float y) {
            createStarsBar(barWidth, barHeight);
            setSize(bgBarTexture.getWidth(), bgBarTexture.getHeight());
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            batch.draw(bgBarTexture, x, y);
            batch.draw(barTexture, x + 1, y + 1);
        }

        private void createStarsBar(int barWidth, int barHeight) {
            int calculatedWidth = 30;
            Pixmap progressPixmap = createProceduralPixmap(calculatedWidth - 2, barHeight - 2, new Color(0xa29100ff));
            Pixmap backPixmap = createProceduralPixmap(barWidth, barHeight, new Color(0x464642));
            barTexture = new Texture(progressPixmap);
            bgBarTexture = new Texture(backPixmap);
        }
    }


    private void createBackground() {
        Pixmap bgPixmap = createProceduralPixmap((int) getWidth(), (int) getHeight(), new Color(0x6da86bff));
        bgTexture = new Texture(bgPixmap);
        addBackground(new Image(bgTexture), false, false);
    }

    private Pixmap createProceduralPixmap(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }

    class BackButton extends Group {
        Image bg;
        Image back;
        Image backDown;
        Label backLabel;

        public BackButton() {
            bg = new Image(Warfare.atlas.findRegion("coinsPanel"));
            back = new Image(Warfare.atlas.findRegion("backImage"));
            backDown = new Image(Warfare.atlas.findRegion("backImage_dwn"));
            backDown.setVisible(false);
//            setSize(backDown);

            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.fontColor = Color.WHITE;
            labelStyle.font = Warfare.font20;
            backLabel = new Label("Back", labelStyle);

            back.setPosition((bg.getWidth() - back.getWidth()) / 2 - 4, bg.getHeight() / 2);
            backDown.setPosition(back.getX() - (backDown.getWidth() - back.getWidth()) / 2,
                    back.getY() - (backDown.getHeight() - back.getHeight()) / 2);
            backLabel.setPosition((bg.getWidth() - backLabel.getWidth()) / 2, 0);

            addActor(bg);
            addActor(back);
            addActor(backDown);
            addActor(backLabel);

            addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
                @Override
                public boolean handle(Event event) {
                    event.setTarget(BackButton.this);
                    return true;
                }
            });

            addListener(new ClickListener() { // создаем слушателя события нажатия кнопки
                // переопределяем метод TouchDown(), который называется прикасание
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    backDown.setVisible(true); // устанавливаем видимость для фона нажатой кнопки, а также оставим вызов метода суперкласса
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    call(ON_BACK);
                    super.clicked(event, x, y);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    backDown.setVisible(false);
                    super.touchUp(event, x, y, pointer, button);
                }
            });
        }
    }
}

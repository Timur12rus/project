package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.screens.level.Finger;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Warfare;

public class GiftIcon extends Group {
    private Image rewardIcon, rewardIconDown;
    private RoundCircle roundCircle;
    private GameManager gameManager;
    private Long firstGiftTime;
    private Long secondGiftTime;
    private Finger finger;
    private Label giftsLabel;

    public GiftIcon(GameManager gameManager) {
        this.gameManager = gameManager;
        firstGiftTime = gameManager.getGiftTimeFirst();
        secondGiftTime = gameManager.getGiftTimeSecond();

        rewardIcon = new Image(Warfare.atlas.findRegion("rewardButton"));
        rewardIconDown = new Image(Warfare.atlas.findRegion("rewardButton_dwn"));
        rewardIconDown.setVisible(false);

        roundCircle = new RoundCircle();

        setWidth(rewardIcon.getWidth());
        setHeight(rewardIcon.getHeight());

        roundCircle = new RoundCircle();
        roundCircle.setPosition(getWidth() - roundCircle.getWidth(), getHeight() - roundCircle.getHeight());

        Label.LabelStyle teamLabelStyle = new Label.LabelStyle();
        teamLabelStyle.fontColor = Color.WHITE;
        teamLabelStyle.font = Warfare.font20;
        giftsLabel = new Label("Podarki", teamLabelStyle);
        giftsLabel.setPosition((getWidth() - giftsLabel.getWidth()) / 2,
                14);

        /** добавим неактивный значок и активный **/
        addActor(rewardIcon);
        addActor(rewardIconDown);
        addActor(giftsLabel);
        addActor(roundCircle);


        finger = new Finger(rewardIcon.getX() - 120 + 16,
                rewardIcon.getY() + 80,
                Finger.RIGHT, new TextureRegion(Warfare.atlas.findRegion("fingerUpRight")));

        finger.setPosition(rewardIcon.getX() - 120 + 16,
                rewardIcon.getY() + 80);

        addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
            @Override
            public boolean handle(Event event) {
                event.setTarget(GiftIcon.this);
                return true;
            }
        });

        addListener(new ClickListener() { // создаем слушателя события нажатия кнопки
            // переопределяем метод TouchDown(), который называется прикасание
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rewardIconDown.setVisible(true); // устанавливаем видимость для фона нажатой кнопки, а также оставим вызов метода суперкласса
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rewardIconDown.setVisible(false);
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    public void showFinger() {
        addActor(finger);
        finger.show();
    }

    public void hideFinger() {
        finger.hide();
    }

    public void setGiftTimeFirst(long giftTimeFirst) {
        this.firstGiftTime = giftTimeFirst;
    }

    public void setGiftTimeSecond(long giftTimeSecond) {
        this.secondGiftTime = giftTimeSecond;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        long deltaTimeFirst = firstGiftTime - System.currentTimeMillis();
        long deltaTimeSecond = secondGiftTime - System.currentTimeMillis();
        if (deltaTimeFirst <= 0 || deltaTimeSecond <= 0) {
            if (!roundCircle.isVisible())
                roundCircle.setVisible(true);
        } else {
            if (roundCircle.isVisible())
                roundCircle.setVisible(false);
        }
    }

    public void setVisibleRoundCirlce(boolean flag) {
        roundCircle.setVisible(flag);
    }

    class RoundCircle extends Group {
        Label label;    // надпись (кол-во доступных наград)
        Image bg;
        int countRewards;

        public RoundCircle() {
            Label.LabelStyle countLabelStyle = new Label.LabelStyle();
            countLabelStyle.fontColor = Color.WHITE;
            countLabelStyle.font = Warfare.font10;
            label = new Label("!", countLabelStyle);
            bg = new Image(Warfare.atlas.findRegion("redCircle"));
            setSize(bg.getWidth(), bg.getHeight());
            label.setPosition((getWidth() - label.getWidth()) / 2, (getHeight() - label.getHeight()) / 2);
            addActor(bg);
            addActor(label);
        }

        public void setCountRewardStars(int count) {
            countRewards = count;
            label.setText("" + countRewards);
        }
    }
}

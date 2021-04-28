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
import com.timgapps.warfare.Utils.StringHolder;
import com.timgapps.warfare.screens.level.Finger;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Warfare;

public class GiftIcon extends IconOnMap {
    private Image rewardIcon, rewardIconDown;
    private GameManager gameManager;
    private Label giftsLabel;

    public GiftIcon(GameManager gameManager) {
        this.gameManager = gameManager;
        rewardIcon = new Image(Warfare.atlas.findRegion("rewardButton"));
        rewardIconDown = new Image(Warfare.atlas.findRegion("rewardButton_dwn"));
        rewardIconDown.setVisible(false);
        setSize(rewardIcon.getWidth(), rewardIcon.getHeight());
        roundCircle.setPosition(getWidth() - roundCircle.getWidth(), getHeight() - roundCircle.getHeight());

        Label.LabelStyle teamLabelStyle = new Label.LabelStyle();
        teamLabelStyle.fontColor = Color.WHITE;
        teamLabelStyle.font = Warfare.font20;
        giftsLabel = new Label(Warfare.stringHolder.getString(StringHolder.GIFTS), teamLabelStyle);
        giftsLabel.setPosition((getWidth() - giftsLabel.getWidth()) / 2,
                14);

        /** добавим неактивный значок и активный **/
        addActor(rewardIcon);
        addActor(rewardIconDown);
        addActor(giftsLabel);

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

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}

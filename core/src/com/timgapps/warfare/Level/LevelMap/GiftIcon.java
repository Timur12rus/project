package com.timgapps.warfare.Level.LevelMap;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Warfare;

public class GiftIcon extends Group {
    private Image rewardIcon, rewardIconDown;

    public GiftIcon() {
        rewardIcon = new Image(Warfare.atlas.findRegion("rewardButton"));
        rewardIconDown = new Image(Warfare.atlas.findRegion("rewardButton_dwn"));

        setWidth(rewardIcon.getWidth());
        setHeight(rewardIcon.getHeight());

        /** добавим неактивный значок и активный **/
        addActor(rewardIcon);
        addActor(rewardIconDown);

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
}

package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class IconOnMap extends Group {
    protected RoundCircle roundCircle;
    protected Image buttonIcon, buttonIconDown;

    public IconOnMap() {
        roundCircle = new RoundCircle();
        roundCircle.setVisible(false);
        addActor(roundCircle);
    }

    public void addClickListener() {
        addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
            @Override
            public boolean handle(Event event) {
                event.setTarget(IconOnMap.this);
                return true;
            }
        });

        addListener(new ClickListener() { // создаем слушателя события нажатия кнопки
            // переопределяем метод TouchDown(), который называется прикасание
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchedDown();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                touchedUp();
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    protected void touchedDown() {
        buttonIconDown.setVisible(true); // устанавливаем видимость для фона нажатой кнопки, а также оставим вызов метода суперкласса
    }

    protected void touchedUp() {
        buttonIconDown.setVisible(false);
    }

    public void setSize() {
        setSize(buttonIconDown.getWidth(), buttonIconDown.getHeight());
    }

    public void showRoundCircle() {
        roundCircle.toFront();
        roundCircle.setVisible(true);
    }

    public Image getButtonIcon() {
        return buttonIcon;
    }

    public void hideRoundCircle() {
        roundCircle.setVisible(false);
    }

    public boolean roundCircleIsVisible() {
        return roundCircle.isVisible();
    }
}

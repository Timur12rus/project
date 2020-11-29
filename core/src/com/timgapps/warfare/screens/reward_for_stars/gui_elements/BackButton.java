package com.timgapps.warfare.screens.reward_for_stars.gui_elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.reward_for_stars.interfaces.ScreenCloser;

// кнопка "назад"
public class BackButton extends Group {
    private Image bg;
    private Image back;
    private Image backDown;
    private Label backLabel;
    private ScreenCloser screenCloser;
    public BackButton(ScreenCloser screenCloser) {
        this.screenCloser = screenCloser;
        bg = new Image(Warfare.atlas.findRegion("coinsPanel"));
        back = new Image(Warfare.atlas.findRegion("backImage"));
        backDown = new Image(Warfare.atlas.findRegion("backImage_dwn"));
        backDown.setVisible(false);
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
                super.clicked(event, x, y);
                closeScreen();
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                backDown.setVisible(false);
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    private void closeScreen() {
        screenCloser.closeScreen();
    }
}

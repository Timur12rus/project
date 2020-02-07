package com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Warfare;

public class YellowButton extends Group {
    private Label textLabel;
    private Image bg, bgDown;
    private Label.LabelStyle labelStyle;

    public YellowButton(String text) {
        bg = new Image(Warfare.atlas.findRegion("yellowButton"));
        bgDown = new Image(Warfare.atlas.findRegion("yellowButton_dwn"));

        addActor(bg);
        addActor(bgDown);
        bgDown.setVisible(false);
        setSize(bg.getWidth(), bg.getHeight());  // устанавливаем её размер по размеру текстуры


        labelStyle = new Label.LabelStyle();
//        style.fontColor = new Color(0x000000ff);
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font40;

        textLabel = new Label("" + text, labelStyle);
        textLabel.setPosition(getWidth() / 2 - textLabel.getWidth() / 2, getHeight() / 2 - textLabel.getHeight() / 2);
        addActor(textLabel);

        /** добавляет слушателя события корневому элементу, отключая его для дочерних элементов **/
        addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
            @Override
            public boolean handle(Event event) {
                event.setTarget(YellowButton.this);
                return true;
            }
        });

        addListener(new ClickListener() { // создаем слушателя события нажатия кнопки
            // переопределяем метод TouchDown(), который называется прикасание

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bgDown.setVisible(true); // устанавливаем видимость для фона нажатой кнопки, а также оставим вызов метода суперкласса
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bgDown.setVisible(false);
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }
}

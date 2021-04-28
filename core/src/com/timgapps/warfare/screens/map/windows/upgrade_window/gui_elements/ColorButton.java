package com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Warfare;

public class ColorButton extends Group {
    private Label textLabel;
    protected Image bg, bgDown;
    private Label.LabelStyle labelStyle;
    public static final int GREEN_BUTTON = 1;
    public static final int YELLOW_BUTTON = 2;
    public static final int RED_BUTTON = 3;
    protected int buttonColor;

    public ColorButton(String text, int buttonColor) {
        this.buttonColor = buttonColor;
        createButtonImage();
        addActor(bg);
        addActor(bgDown);
        bgDown.setVisible(false);
        setSize(bg.getWidth(), bg.getHeight());  // устанавливаем её размер по размеру текстуры

        labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font40;

        textLabel = new Label("" + text, labelStyle);
        textLabel.setPosition(getWidth() / 2 - textLabel.getWidth() / 2, getHeight() / 2 - textLabel.getHeight() / 2);
        addActor(textLabel);

//        if (textLabel.getWidth() > bg.getWidth()) {
//            setButtonWidth((textLabel.getWidth() + 24) / bg.getWidth());
//        }

        /** добавляет слушателя события корневому элементу, отключая его для дочерних элементов **/
        addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
            @Override
            public boolean handle(Event event) {
                event.setTarget(ColorButton.this);
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

    protected void createButtonImage() {
        switch (buttonColor) {
            case GREEN_BUTTON:
                bg = new Image(Warfare.atlas.findRegion("button_ok"));
                bgDown = new Image(Warfare.atlas.findRegion("button_ok_dwn"));
                break;
            case YELLOW_BUTTON:
                bg = new Image(Warfare.atlas.findRegion("yellowButton"));
                bgDown = new Image(Warfare.atlas.findRegion("yellowButton_dwn"));
                break;
            case RED_BUTTON:

                break;
        }
    }

    public void setButtonWidth(float scale) {
        bg.setScaleX(scale);
        bgDown.setScaleX(scale);
        setSize(bg.getWidth() * scale, bg.getHeight());
        textLabel.setPosition(getWidth() / 2 - textLabel.getWidth() / 2, getHeight() / 2 - textLabel.getHeight() / 2);
    }
}

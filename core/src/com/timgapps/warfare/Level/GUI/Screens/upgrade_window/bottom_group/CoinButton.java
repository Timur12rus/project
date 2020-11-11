package com.timgapps.warfare.Level.GUI.Screens.upgrade_window.bottom_group;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Warfare;

// кнопка с надписью и монетой, используется для апгрейда и покупки юнита
public class CoinButton extends Group {
    private ImageButton button;
    private Label label;
    private Image bg, bgDown, coin;
    private float width, height;
    private int upgradeCost;
    Label.LabelStyle labelStyle;

    public CoinButton() {
        bg = new Image(Warfare.atlas.findRegion("button_ok"));
        bgDown = new Image(Warfare.atlas.findRegion("button_ok_dwn"));
        coin = new Image(Warfare.atlas.findRegion("coin_icon"));
        addActor(bg);
        addActor(bgDown);
        bgDown.setVisible(false);
        setSize(bg.getWidth(), bg.getHeight());  // устанавливаем её размер по размеру текстуры
        labelStyle = new Label.LabelStyle();
//        style.fontColor = new Color(0x000000ff);
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font40;
        label = new Label("" + upgradeCost, labelStyle);
        Table table = new Table();
//        table.debug();
        table.setWidth(bg.getWidth());
        table.setHeight(bg.getHeight());
        table.add(label);
        table.add(coin).padBottom(6);
        table.setY(2);
        addActor(table);

        /** добавляет слушателя события корневому элементу, отключая его для дочерних элементов **/
        addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
            @Override
            public boolean handle(Event event) {
                event.setTarget(CoinButton.this);
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

    /**
     * метод для уставки цвета ТЕКСТА КНОПКИ
     *
     * @param upgradeCost - количество монет, стоимость действия
     * @param canBeMake   - флаг, может ли быть совершено действие (покупа или улучшение), если true - может, если false - не может
     **/
    public void setCost(int upgradeCost, boolean canBeMake) {
        this.upgradeCost = upgradeCost;
        if (canBeMake) {
            labelStyle.fontColor = Color.DARK_GRAY;
        } else {
            labelStyle.fontColor = Color.RED;
//            label.setStyle();
        }
        label.setText("" + upgradeCost);
    }

}
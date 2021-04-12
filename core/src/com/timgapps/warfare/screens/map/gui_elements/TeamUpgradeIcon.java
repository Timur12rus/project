package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Warfare;


public class TeamUpgradeIcon extends IconOnMap {
    private Image upgradeIcon, upgradeIconDown;
    private Label teamLabel;
    private ImageButton imageButton;

    public TeamUpgradeIcon() {
        upgradeIcon = new Image(Warfare.atlas.findRegion("teamButton"));
        upgradeIconDown = new Image(Warfare.atlas.findRegion("teamButtonDwn"));
        upgradeIconDown.setVisible(false);
        setSize(upgradeIconDown.getWidth(), upgradeIconDown.getHeight());

        Label.LabelStyle teamLabelStyle = new Label.LabelStyle();
        teamLabelStyle.fontColor = Color.WHITE;
        teamLabelStyle.font = Warfare.font20;
        teamLabel = new Label("KOMAHDA", teamLabelStyle);
        teamLabel.setPosition((getWidth() - teamLabel.getWidth()) / 2,
                14);
        addActor(upgradeIcon);
        addActor(upgradeIconDown);
        addActor(teamLabel);

        addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
            @Override
            public boolean handle(Event event) {
                event.setTarget(TeamUpgradeIcon.this);
                return true;
            }
        });

        addListener(new ClickListener() { // создаем слушателя события нажатия кнопки
            // переопределяем метод TouchDown(), который называется прикасание
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upgradeIconDown.setVisible(true); // устанавливаем видимость для фона нажатой кнопки, а также оставим вызов метода суперкласса
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upgradeIconDown.setVisible(false);
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }
}

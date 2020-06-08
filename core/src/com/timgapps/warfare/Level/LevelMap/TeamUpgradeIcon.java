package com.timgapps.warfare.Level.LevelMap;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.GUI.Finger;
import com.timgapps.warfare.Warfare;


public class TeamUpgradeIcon extends Group {
    private Image upgradeIcon, UpgradeIconDown;
    private Image greenArrow;
    private Finger finger;

    public TeamUpgradeIcon() {
        upgradeIcon = new Image(Warfare.atlas.findRegion("teamButton"));
        UpgradeIconDown = new Image(Warfare.atlas.findRegion("teamButtonDwn"));
        UpgradeIconDown.setVisible(false);
        setSize(UpgradeIconDown.getWidth(), UpgradeIconDown.getHeight());

        addActor(upgradeIcon);
        addActor(UpgradeIconDown);


        finger = new Finger(upgradeIcon.getX() + upgradeIcon.getWidth() + 96,
                upgradeIcon.getY() + (upgradeIcon.getHeight() - 44) / 2 - 24,
                Finger.LEFT, new TextureRegion(Warfare.atlas.findRegion("fingerUpLeft")));

        finger.setPosition(upgradeIcon.getX() + upgradeIcon.getWidth() + 96,
                upgradeIcon.getY() + (upgradeIcon.getHeight() - 44) / 2 - 24);

//        greenArrow = new Image(Warfare.atlas.findRegion("greenArrow"));

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
                UpgradeIconDown.setVisible(true); // устанавливаем видимость для фона нажатой кнопки, а также оставим вызов метода суперкласса
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                UpgradeIconDown.setVisible(false);
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
}

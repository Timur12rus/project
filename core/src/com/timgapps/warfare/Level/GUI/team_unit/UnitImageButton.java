package com.timgapps.warfare.Level.GUI.team_unit;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;
import com.timgapps.warfare.Warfare;

// кнопка юнита (в магазине на сцене для вызова юнита)
public class UnitImageButton extends Group {
    private Image bgImage, dwnImage, lockImage;
    private boolean isUnlock;
    private PlayerUnits unitId;
    protected boolean isReadyUnitButton;

    public UnitImageButton(PlayerUnits unitId, final boolean isUnlock) {
        this.unitId = unitId;
        this.isUnlock = isUnlock;
        System.out.println("unitId = " + unitId);
        bgImage = new Image(Warfare.atlas.findRegion(unitId.name().toLowerCase() + "Active"));
        dwnImage = new Image(Warfare.atlas.findRegion(unitId.name().toLowerCase() + "Inactive"));
        lockImage = new Image(Warfare.atlas.findRegion(unitId.name().toLowerCase() + "Lock"));

        setSize(bgImage.getWidth(), bgImage.getHeight());
        dwnImage.setVisible(false);
        bgImage.setVisible(false);
        lockImage.setVisible(false);

        if (isUnlock) {                     // если разблокирован
            bgImage.setVisible(true);
        } else {                            // в противном случае, заблокирован
            lockImage.setVisible(true);
        }
        addActor(bgImage);
        addActor(dwnImage);
        dwnImage.setPosition(0, -10);
        addActor(lockImage);

        addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
            @Override
            public boolean handle(Event event) {
                event.setTarget(UnitImageButton.this);
                return true;
            }
        });
        addListener(new ClickListener() { // создаем слушателя события нажатия кнопки
            // переопределяем метод TouchDown(), который называется прикасание

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!isUnlock) {
                    lockImage.setY(lockImage.getY() - 10);
                } else {
                    dwnImage.setVisible(true); // устанавливаем видимость для фона нажатой кнопки, а также оставим вызов метода суперкласса
                    bgImage.setVisible(false);
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!isUnlock) {
                    lockImage.setY(lockImage.getY() + 10);
                } else {
                    dwnImage.setVisible(false);
                    bgImage.setVisible(true);
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    public void unlock() {
        isUnlock = true;
        lockImage.setVisible(false);
        bgImage.setVisible(true);
    }

    public void setInActive() {
        bgImage.setVisible(false);
        dwnImage.setVisible(true);
//        isReadyUnitButton = false;
    }
}

package com.timgapps.warfare.Level.GUI.team_unit;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;
import com.timgapps.warfare.Warfare;

// кнопка юнита (в магазине на сцене для вызова юнита)
public class UnitImageButton extends Group {
    protected Image activeImage, inactiveImage, lockImage;
    private boolean isUnlock;
    private PlayerUnits unitId;
    protected boolean isReadyUnitButton;
    protected PlayerUnitData playerUnitData;
    protected float height;

    public UnitImageButton(PlayerUnitData playerUnitData) {
//    public UnitImageButton(PlayerUnits unitId, final boolean isUnlock) {
        this.playerUnitData = playerUnitData;
        this.unitId = playerUnitData.getUnitId();
        this.isUnlock = playerUnitData.isUnlock();
        System.out.println("unitId = " + unitId);
        activeImage = new Image(Warfare.atlas.findRegion(unitId.name().toLowerCase() + "Active"));
        inactiveImage = new Image(Warfare.atlas.findRegion(unitId.name().toLowerCase() + "Inactive"));
        lockImage = new Image(Warfare.atlas.findRegion(unitId.name().toLowerCase() + "Lock"));
        height = activeImage.getHeight();

        setSize(activeImage.getWidth(), activeImage.getHeight());
        activeImage.setVisible(true);
        inactiveImage.setVisible(false);
        lockImage.setVisible(false);

        if (isUnlock) {                     // если разблокирован
            activeImage.setVisible(true);
            lockImage.setVisible(false);
        } else {                            // в противном случае, заблокирован
            lockImage.setVisible(true);
            activeImage.setVisible(false);
            inactiveImage.setVisible(false);
        }
        addActor(activeImage);
        addActor(inactiveImage);
        inactiveImage.setPosition(0, -10);
        addActor(lockImage);
//        setInActive();

        addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
            @Override
            public boolean handle(Event event) {
                event.setTarget(UnitImageButton.this);
                return true;
            }
        });
        addClickListener();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    private void addClickListener() {
        addListener(new ClickListener() { // создаем слушателя события нажатия кнопки
            // переопределяем метод TouchDown(), который называется прикасание

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!isUnlock) {        // если заблокирован юнит
                    lockImage.setY(lockImage.getY() - 10);      // изображение с блокировкой
                    activeImage.setY(activeImage.getY() - 10);
                } else {
//                    dwnImage.setVisible(true); // устанавливаем видимость для фона нажатой кнопки, а также оставим вызов метода суперкласса
//                    bgImage.setVisible(false);
                    activeImage.setY(activeImage.getY() - 10);
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!isUnlock) {
                    lockImage.setY(lockImage.getY() + 10);
                    activeImage.setY(activeImage.getY() + 10);
                } else {
                    activeImage.setY(activeImage.getY() + 10);
//                    activeImage.setY(activeImage.getY() + 10);
                }
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClicked();
                super.clicked(event, x, y);
            }
        });
    }

    public void buttonClicked() {

    }

    public void unlock() {
        isUnlock = true;
        lockImage.setVisible(false);
        activeImage.setVisible(true);
    }

    public void setInActive() {
        activeImage.setVisible(false);
        inactiveImage.setVisible(true);
//        isReadyUnitButton = false;
    }
}

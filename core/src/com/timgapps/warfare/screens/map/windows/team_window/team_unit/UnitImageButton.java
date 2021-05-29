package com.timgapps.warfare.screens.map.windows.team_window.team_unit;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;
import com.timgapps.warfare.Warfare;

// кнопка юнита (в магазине и на сцене для вызова юнита)
public class UnitImageButton extends Group {
    protected Image activeImage, inactiveImage, lockImage;
    protected boolean isUnlock;
    protected PlayerUnits unitId;
    protected boolean isReadyUnitButton;
    protected PlayerUnitData playerUnitData;
    protected float height;
    protected com.timgapps.warfare.screens.map.windows.team_window.team_unit.UnitLevelIcon unitLevelIcon;
    protected Image lockIcon;
    protected boolean isCalled;     // юнит призван (куплен)
    protected boolean isTouchedDown;

    public UnitImageButton(PlayerUnitData playerUnitData) {
//    public UnitImageButton(PlayerUnits unitId, final boolean isUnlock) {
        this.playerUnitData = playerUnitData;
        this.unitId = playerUnitData.getUnitId();
        this.isUnlock = playerUnitData.isUnlock();
        this.isCalled = playerUnitData.isHired();
        System.out.println("unitId = " + unitId);
        activeImage = new Image(Warfare.atlas.findRegion(unitId.name().toLowerCase() + "Active"));
        inactiveImage = new Image(Warfare.atlas.findRegion(unitId.name().toLowerCase() + "Inactive"));
        lockImage = new Image(Warfare.atlas.findRegion(unitId.name().toLowerCase() + "Lock"));
        lockIcon = new Image(Warfare.atlas.findRegion("lockIcon"));         // значок "замок" заблокирован юнит
        unitLevelIcon = new UnitLevelIcon(playerUnitData.getUnitLevel());           // значок "уровень юнита"
        height = activeImage.getHeight();

        setSize(activeImage.getWidth(), activeImage.getHeight());
        activeImage.setVisible(true);
        inactiveImage.setVisible(false);
        lockImage.setVisible(false);

//        unitLevelIcon.setPosition(getWidth() - unitLevelIcon.getWidth(), getHeight() - unitLevelIcon.getHeight() + 10);
        lockIcon.setPosition(getWidth() - lockIcon.getWidth(), getHeight() - lockIcon.getHeight() + 10);

        redraw();
        addActor(activeImage);
        addActor(inactiveImage);
        inactiveImage.setPosition(0, -10);
        addActor(lockImage);
        addActor(lockIcon);
        addActor(unitLevelIcon);
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

    // метод запускает действие движения значка уровня юнита, если юнит может быть улучшен
    public void startLevelIconAction() {
//        MoveToAction actionOne = new MoveToAction();
//        actionOne.setDuration(0.5f);
//        actionOne.setInterpolation(Interpolation.smooth);
//
//        MoveToAction actionTwo = new MoveToAction();
//        actionTwo.setDuration(0.5f);
//        actionTwo.setInterpolation(Interpolation.smooth);


        SequenceAction sequenceAction = new SequenceAction(
                Actions.moveBy(0, 8, 0.4f, Interpolation.smooth),
                Actions.moveBy(0, -8, 0.4f, Interpolation.smooth)
        );
        RepeatAction repeatAction = new RepeatAction();

        repeatAction.setCount(RepeatAction.FOREVER);
        repeatAction.setAction(sequenceAction);
        unitLevelIcon.addAction(repeatAction);
    }

    public void clearLevelIconAction() {
        unitLevelIcon.clearActions();
        unitLevelIcon.setPosition(getWidth() - unitLevelIcon.getWidth(), getHeight() - unitLevelIcon.getHeight() + 10);
//        unitLevelIcon.
    }

    // перерисовывает кнопку-изображение (значок) юнита в зависимости от доступности юнита
    public void redraw() {
        unitLevelIcon.setPosition(getWidth() - unitLevelIcon.getWidth(), getHeight() - unitLevelIcon.getHeight() + 10);
        this.isUnlock = playerUnitData.isUnlock();
        this.isCalled = playerUnitData.isHired();
        if (isUnlock) {                         // если разблокирован
            lockIcon.setVisible(false);
            if (isCalled) {                     // если призван
                activeImage.setVisible(true);
                unitLevelIcon.setVisible(true);
                lockImage.setVisible(false);
            } else {
                activeImage.setVisible(false);
                unitLevelIcon.setVisible(false);
                lockImage.setVisible(true);
            }
        } else {                            // в противном случае, заблокирован
            lockImage.setVisible(true);
            activeImage.setVisible(false);
            inactiveImage.setVisible(false);
            lockIcon.setVisible(true);
            unitLevelIcon.setVisible(false);
        }
    }

    public void setLevelValue(int levelValue) {
        unitLevelIcon.setLevelValue(levelValue);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    private void addClickListener() {
        addListener(new ClickListener() { // создаем слушателя события нажатия кнопки
            // переопределяем метод TouchDown(), который называется прикасание


            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                touchedDragged(x, y);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchedDown();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                touchedUp(x, y);
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClicked(x, y);
                super.clicked(event, x, y);
            }
        });
    }

    public void setInActive() {
        activeImage.setVisible(false);
        inactiveImage.setVisible(true);
    }

    public void touchedDragged(float x, float y) {
    }

    public void touchedDown() {
        if (!isUnlock) {                 // если не разблокирован юнит
            lockImage.setY(lockImage.getY() - 10);      // изображение с блокировкой
            activeImage.setY(activeImage.getY() - 10);
            lockIcon.setY(lockIcon.getY() - 10);
        } else {
            lockImage.setY(lockImage.getY() - 10);
            activeImage.setY(activeImage.getY() - 10);
            unitLevelIcon.setY(unitLevelIcon.getY() - 10);
        }
    }

    public void touchedUp(float x, float y) {
        if (!isUnlock) {            // если не разблокирован юнит
            lockImage.setY(lockImage.getY() + 10);
            activeImage.setY(activeImage.getY() + 10);
            lockIcon.setY(lockIcon.getY() + 10);
        } else {
            lockImage.setY(lockImage.getY() + 10);
            activeImage.setY(activeImage.getY() + 10);
            unitLevelIcon.setIsActiveIcon(true);
//            unitLevelIcon.setY(unitLevelIcon.getY() + 10);
        }
    }

    public void buttonClicked(float x, float y) {
    }

    public void unlock() {
        isUnlock = true;
        lockImage.setVisible(false);
        activeImage.setVisible(true);
    }

    public PlayerUnits getUnitId() {
        return unitId;
    }
}

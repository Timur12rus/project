package com.timgapps.warfare.Level.GUI.Screens;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow.UnitImage;
import com.timgapps.warfare.Warfare;


public class TeamEntity extends Group {

    public static final int NONE = 0;
    public static final int GNOME = 1;
    public static final int ARCHER = 2;
    public static final int THOR = 3;
    public static final int STONE = 4;
    public static final int KNIGHT = 5;

    private UnitImageButton unitButton;
    //    private ImageButton unitButton;
    private int unitType;
    private int DAMAGE;
    private int HEALTH;
    private int SPEED;
    private String NAME;
    private int unitLevel;
    private int addHealthValue;
    private int addDamageValue;
    private int timePrepare;
    private int energyCost;

    public float width, height;
    private UnitImage unitImage;
    private int unitIndex;          // индекс юнита
    private TeamEntityData data;
    private final int MAX_UNIT_LEVEL = 3;

    @Override
    public String toString() {
        return NAME;
    }

    /**
     * Объект СУЩНОСТЬ КОМАНДЫ (ЮНИТ) в массиве команды или коллекции
     *
     * @param data - data, данные: параметры юнита
     **/
    public TeamEntity(TeamEntityData data) {
        this.unitType = data.getUnitType();
        this.data = data;
        switch (unitType) {
            case GNOME:
//                unitButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("gnomeActive")),
//                        new TextureRegionDrawable(Warfare.atlas.findRegion("gnomeInactive")));

                unitButton = new UnitImageButton(new Image(Warfare.atlas.findRegion("gnomeActive")),
                        new Image(Warfare.atlas.findRegion("gnomeInactive")),
                        new Image(Warfare.atlas.findRegion("gnomeLock")),
                        data.isUnlock());

                NAME = "Gnome";
                SPEED = 6;
                addHealthValue = 2;
                addDamageValue = 2;
                timePrepare = 10;
                energyCost = 15;
                break;
            case ARCHER:
//                unitButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("archer1Active")),
//                        new TextureRegionDrawable(Warfare.atlas.findRegion("archer1Inactive")));

                unitButton = new UnitImageButton(new Image(Warfare.atlas.findRegion("archer1Active")),
                        new Image(Warfare.atlas.findRegion("archer1Inactive")),
                        new Image(Warfare.atlas.findRegion("archer1Lock")),
                        data.isUnlock());

                NAME = "Archer";
                SPEED = 4;
                addHealthValue = 2;
                addDamageValue = 2;
                timePrepare = 25;
                energyCost = 20;
                break;

            case THOR:
//                unitButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("thorActive")),
//                        new TextureRegionDrawable(Warfare.atlas.findRegion("thorInactive")));
                unitButton = new UnitImageButton(new Image(Warfare.atlas.findRegion("thorActive")),
                        new Image(Warfare.atlas.findRegion("thorInactive")),
                        new Image(Warfare.atlas.findRegion("thorLock")),
                        data.isUnlock());

                NAME = "Thor";
                SPEED = 8;
                addHealthValue = 2;
                addDamageValue = 2;
                timePrepare = 18;
                energyCost = 25;
                break;

            case KNIGHT:
                unitButton = new UnitImageButton(new Image(Warfare.atlas.findRegion("knightActive")),
                        new Image(Warfare.atlas.findRegion("knightInactive")),
                        new Image(Warfare.atlas.findRegion("knightLock")),
                        data.isUnlock());           // заблокирован ли юнит
                NAME = "Knight";
                SPEED = 8;
                addHealthValue = 2;
                addDamageValue = 2;
                timePrepare = 18;
                energyCost = 25;
                break;

            case STONE:
//                unitButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("stoneButtonActive")),
//                        new TextureRegionDrawable(Warfare.atlas.findRegion("stoneButtonInactive")));
                unitButton = new UnitImageButton(new Image(Warfare.atlas.findRegion("stoneButtonActive")),
                        new Image(Warfare.atlas.findRegion("stoneButtonInactive")),
                        new Image(Warfare.atlas.findRegion("stoneButtonLock")),
                        data.isUnlock());


                NAME = "Rock";
                SPEED = 0;
                addHealthValue = 2;
                addDamageValue = 2;
                timePrepare = 10;
                energyCost = 6;
                break;

            case NONE:
//                unitButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("emptyButtonActive")),
//                        new TextureRegionDrawable(Warfare.atlas.findRegion("emptyButtonInactive")));
                SPEED = 12;
                break;
        }

        DAMAGE = data.getDAMAGE();
        HEALTH = data.getHEALTH();
        unitLevel = data.getUnitLevel();

        unitImage = new UnitImage(unitType, unitLevel, energyCost);

        width = unitButton.getWidth();
        height = unitButton.getHeight();
        addActor(unitButton);

//        unitLevelIcon = new UnitLevelIcon(unitLevel);
//        unitLevelIcon.setPosition(unitImage.getWidth(), unitImage.getHeight() - 20);
//        addActor(unitLevelIcon);
    }

    public int getMaxUnitLevel() {
        return MAX_UNIT_LEVEL;
    }

    public class UnitImageButton extends Group {
        Image bgImage, dwnImage, lockImage;
        boolean isUnlock;

        public UnitImageButton(final Image bgImage, final Image dwnImage, final Image lockImage, final boolean isUnlock) {
            this.bgImage = bgImage;
            this.dwnImage = dwnImage;
            this.lockImage = lockImage;
            this.isUnlock = isUnlock;       // isLock - значит заблокирован

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
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    super.clicked(event, x, y);
//                }
            });
        }

        public void unlock() {
            isUnlock = true;
            lockImage.setVisible(false);
            bgImage.setVisible(true);
        }
    }

    public void unlock() {
        unitButton.unlock();
    }

    /**
     * метод для добавления количества здоровья
     **/
    public void addHEALTH(int health) {
        HEALTH += health;
    }

    /**
     * метод для добавления количества урона
     **/
    public void addDAMAGE(int damage) {
        DAMAGE += damage;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public UnitImage getUnitImage() {
        return unitImage;
    }

    public int getHEALTH() {
        return HEALTH;
    }

    public int getDAMAGE() {
        return DAMAGE;
    }

    public int getSPEED() {
        return SPEED;
    }

    public int getAddHealthValue() {
        return addHealthValue;
    }

    public int getAddDamageValue() {
        return addDamageValue;
    }

    public int getTimePrepare() {
        return timePrepare;
    }

    public int getUnitLevel() {
        return unitLevel;
    }

    public void setUnitLevel(int unitLevel) {
        this.unitLevel = unitLevel;
    }

    public UnitImageButton getUnitImageButton() {
        return unitButton;
    }

    public String getName() {
        return NAME;
    }

    public int getUnitType() {
        return unitType;
    }

    public TeamEntityData getEntityData() {
        return data;
    }

    public void updateTeamEntityData() {
        data.setUnitLevel(unitLevel);
        data.setHEALTH(HEALTH);
        data.setDAMAGE(DAMAGE);
    }
}


package com.timgapps.warfare.Level.GUI.Screens;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow.UnitImage;
import com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow.UnitLevelIcon;
import com.timgapps.warfare.Level.GUI.UnitButton;
import com.timgapps.warfare.Warfare;


public class TeamEntity extends Group {

    public static final int NONE = 0;
    public static final int GNOME = 1;
    public static final int ARCHER = 2;
    public static final int THOR = 3;
    public static final int STONE = 4;

    private int unitType;

    private ImageButton unitButton;
    //    private Image unitImage;
    private int id;                  // номер в КОМАНДЕ
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

    private UnitLevelIcon unitLevelIcon;
    private UnitImage unitImage;
    private int unitIndex;          // индекс юнита


    @Override
    public String toString() {
        return NAME;
//        return super.toString();

    }

    /**
     * Объект СУЩНОСТЬ КОМАНДЫ (ЮНИТ) в массиве команды или коллекции
     *
     * @param unitType - тип юнита
     **/
    public TeamEntity(int unitType) {
//    public TeamEntity(int unitType, int unitIndex) {
        this.unitType = unitType;
//        this.unitIndex = unitIndex;

        switch (unitType) {
            case GNOME:
                unitButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("gnomeActive")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("gnomeInactive")));
//                unitImage = new Image(Warfare.atlas.findRegion("gnomeStay0"));

                NAME = "Gnome";
                DAMAGE = 14;
                HEALTH = 30;
                SPEED = 6;
                addHealthValue = 2;
                addDamageValue = 2;
                timePrepare = 10;
                energyCost = 15;
                unitType = TeamEntity.GNOME;

                // TODO: 23.01.2020 Исправить ПОЛУЧИТЬ ЗНАЧЕНИЕ УРОВНЯ ЮНИТА
                unitLevel = 1;
                break;
            case ARCHER:
                unitButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("archer1Active")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("archer1Inactive")));
//                unitImage = new Image(Warfare.atlas.findRegion("archer1Stay0"));
                NAME = "Archer";
                DAMAGE = 10;
                HEALTH = 30;
                SPEED = 4;

                addHealthValue = 2;
                addDamageValue = 2;
                timePrepare = 25;
                energyCost = 20;
                unitType = TeamEntity.ARCHER;


                unitLevel = 1;
                break;

            case THOR:
                unitButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("thorActive")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("thorInactive")));
                NAME = "Thor";
                DAMAGE = 12;
                HEALTH = 20;
                SPEED = 8;

                addHealthValue = 2;
                addDamageValue = 2;
                timePrepare = 18;
                energyCost = 25;

                unitLevel = 1;
                unitType = TeamEntity.THOR;

                break;

            case STONE:
                unitButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("stoneButtonActive")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("stoneButtonInactive")));
//                unitImage = new Image(Warfare.atlas.findRegion("stoneButtonActive"));
                NAME = "Rock";
                DAMAGE = 10;
                HEALTH = 50;
                SPEED = 0;

                addHealthValue = 2;
                addDamageValue = 2;
                timePrepare = 10;
                energyCost = 6;
                unitLevel = 1;

                unitType = TeamEntity.STONE;

                break;

            case NONE:
                unitButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("emptyButtonActive")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("emptyButtonInactive")));
//                unitImage = new Image(Warfare.atlas.findRegion("stoneButtonActive"));
                DAMAGE = 10;
                HEALTH = 50;
                SPEED = 12;
                break;
        }

        unitImage = new UnitImage(unitType, unitLevel, energyCost);

        width = unitButton.getWidth();
        height = unitButton.getHeight();
        addActor(unitButton);

//        unitLevelIcon = new UnitLevelIcon(unitLevel);
//        unitLevelIcon.setPosition(unitImage.getWidth(), unitImage.getHeight() - 20);
//        addActor(unitLevelIcon);
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

    public ImageButton getUnitButton() {
        return unitButton;
    }

    public String getName() {
        return NAME;
    }

    public int getUnitType() {
        return unitType;
    }
}


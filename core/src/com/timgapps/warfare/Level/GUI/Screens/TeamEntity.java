package com.timgapps.warfare.Level.GUI.Screens;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.timgapps.warfare.Warfare;


public class TeamEntity extends Group {

    public static final int NONE = 0;
    public static final int GNOME = 1;
    public static final int ARCHER = 2;
    public static final int THOR = 3;
    public static final int STONE = 4;

    private int unitType;

    private ImageButton unitImage;
    private Image image;
    private int id;                  // номер в КОМАНДЕ
    private int DAMAGE;
    private int HEALTH;
    private int SPEED;
    private String NAME;
    private int unitLevel;
    private int addHealthValue;
    private int addDamageValue;
    private int timePrepare;

    public float width, height;


    /**
     * Объект СУЩНОСТЬ КОМАНДЫ (ЮНИТ) в массиве команды или коллекции
     *
     * @param unitType - тип юнита
     **/
    public TeamEntity(int unitType) {
        this.unitType = unitType;
        switch (unitType) {
            case GNOME:
                unitImage = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("gnomeActive")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("gnomeInactive")));
                image = new Image(Warfare.atlas.findRegion("gnomeStay0"));
                NAME = "Gnome";
                DAMAGE = 10;
                HEALTH = 50;
                SPEED = 15;
                addHealthValue = 2;
                addDamageValue = 2;
                timePrepare = 10;

                // TODO: 23.01.2020 Исправить ПОЛУЧИТЬ ЗНАЧЕНИЕ УРОВНЯ ЮНИТА
                unitLevel = 1;
                break;
            case ARCHER:
                unitImage = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("archer1Active")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("archer1Inactive")));
                image = new Image(Warfare.atlas.findRegion("archer1Stay0"));
                NAME = "Archer";
                DAMAGE = 10;
                HEALTH = 30;
                SPEED = 8;

                addHealthValue = 2;
                addDamageValue = 2;
                timePrepare = 25;

                unitLevel = 1;
                break;

            case THOR:
                unitImage = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("thorActive")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("thorInactive")));
                NAME = "Thor";
                DAMAGE = 15;
                HEALTH = 30;
                SPEED = 10;

                addHealthValue = 2;
                addDamageValue = 2;
                timePrepare = 18;

                unitLevel = 1;
                break;

            case STONE:
                unitImage = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("stoneButtonActive")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("stoneButtonInactive")));
                image = new Image(Warfare.atlas.findRegion("stoneButtonActive"));
                NAME = "Stone";
                DAMAGE = 10;
                HEALTH = 50;
                SPEED = 0;

                addHealthValue = 2;
                addDamageValue = 2;
                timePrepare = 10;

                break;

            case NONE:
                unitImage = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("emptyButtonActive")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("emptyButtonInactive")));
                image = new Image(Warfare.atlas.findRegion("stoneButtonActive"));
                DAMAGE = 10;
                HEALTH = 50;
                SPEED = 12;
                break;
        }

        width = unitImage.getWidth();
        height = unitImage.getHeight();
        addActor(unitImage);
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

    public Image getImage() {
        return image;
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
}
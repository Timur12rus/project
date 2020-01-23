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
    private float BORN_TIME;
    private int UNIT_LEVEL;

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
                image = new Image(Warfare.atlas.findRegion("gnomeActive"));
                NAME = "Gnome";
                DAMAGE = 10;
                HEALTH = 50;
                SPEED = 12;
                BORN_TIME = 35;

                // TODO: 23.01.2020 Исправить ПОЛУЧИТЬ ЗНАЧЕНИЕ УРОВНЯ ЮНИТА
                UNIT_LEVEL = 1;
                break;
            case ARCHER:
                unitImage = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("archer1Active")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("archer1Inactive")));
                NAME = "Archer";
                DAMAGE = 10;
                HEALTH = 50;
                SPEED = 12;
                BORN_TIME = 35;
                break;

            case THOR:
                unitImage = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("thorActive")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("thorInactive")));
                NAME = "Thor";
                DAMAGE = 10;
                HEALTH = 50;
                SPEED = 12;
                BORN_TIME = 35;
                break;

            case STONE:
                unitImage = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("stoneButtonActive")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("stoneButtonInactive")));
                NAME = "Stone";
                DAMAGE = 10;
                HEALTH = 50;
                SPEED = 12;
                BORN_TIME = 35;

                break;

            case NONE:
                unitImage = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("emptyButtonActive")),
                        new TextureRegionDrawable(Warfare.atlas.findRegion("emptyButtonInactive")));
                DAMAGE = 10;
                HEALTH = 50;
                SPEED = 12;
                BORN_TIME = 35;
                break;
        }

        width = unitImage.getWidth();
        height = unitImage.getHeight();
        addActor(unitImage);
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
}
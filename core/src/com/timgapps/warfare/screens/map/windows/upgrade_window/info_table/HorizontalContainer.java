package com.timgapps.warfare.screens.map.windows.upgrade_window.info_table;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.Warfare;

public class HorizontalContainer extends HorizontalGroup {
    public static final int HEALTH = 1;
    public static final int DAMAGE = 2;
    public static final int SPEED = 3;
    public static final int TIME_PREPEAR = 4;
    private int typeOfString;           // тип строки ("Здоровье", "Урон" и т.д.)
    private Image icon;
    private Label nameLabel;
    private Label valueLabel;       // текст строки ("Здоровье", "Урон" и т.д.)
    private Label addValueLabel;        // текст "+ значение добавления" ("+2")

    public HorizontalContainer(InfoTabe1 infoTabel1, int typeOfString) {
        this.typeOfString = typeOfString;

        Label.LabelStyle labelStyle = infoTabel1.getLabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;

        Label.LabelStyle greenLabelStyle = new Label.LabelStyle();
        greenLabelStyle.fontColor = Color.FOREST;
        greenLabelStyle.font = Warfare.font20;

        nameLabel = new Label("", labelStyle);
        valueLabel = new Label("", labelStyle);
        addValueLabel = new Label("", greenLabelStyle);
        switch (typeOfString) {
            case HEALTH:
                icon = new Image(Warfare.atlas.findRegion("heart_icon"));
                nameLabel.setText("Health");
                break;
            case DAMAGE:
                icon = new Image(Warfare.atlas.findRegion("damage_icon"));
                nameLabel.setText("Damage");
                addValueLabel.setText("2");
                break;
            case SPEED:
                icon = new Image(Warfare.atlas.findRegion("speed_icon"));
                nameLabel.setText("Speed");
                break;
            case TIME_PREPEAR:
                icon = new Image(Warfare.atlas.findRegion("clock_icon"));
                nameLabel.setText("Time prepear");
                break;
        }
    }

    // метод для перерисовки данных о характеристиках юнита
    public void redraw(PlayerUnitData data) {
        switch (typeOfString) {
            case HEALTH:
                valueLabel.setText("" + data.getHealth());
                if (data.getUnitLevel() < 10) {
                    addValueLabel.setText("+ 2");
                } else {
                    addValueLabel.setText("");
                }
                break;
            case DAMAGE:
                valueLabel.setText("" + data.getDamage());
                if (data.getUnitLevel() < 10) {
                    addValueLabel.setText("+ 2");
                } else {
                    addValueLabel.setText("");
                }
                break;
            case SPEED:
                valueLabel.setText("" + data.getSpeed() * 10);
                break;
            case TIME_PREPEAR:
                valueLabel.setText("" + data.getPrepareTime());
                break;
        }
    }
}

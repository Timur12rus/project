package com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;


/**
 * Таблица с "ЮНИТАМИ В КОМАНДЕ", используется в классе TeamUpgradeScreen
 */
public class TeamTable extends Table {
    private ArrayList<TeamEntity> unitTeam; // массив юнитов "КОМАНДА"
    private float width, height;
    private int numOfUnits = 5;

    private Label collectionLabel;
    private String collectionText;

    public TeamTable(ArrayList<TeamEntity> unitTeam) {
        this.unitTeam = unitTeam;

        this.left().top();

        collectionText = "Collection";

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font40;

        collectionLabel = new Label(collectionText, labelStyle);


        for (int i = 0; i < unitTeam.size(); i++) {
            add(unitTeam.get(i)).width(unitTeam.get(i).getWidth()).height(unitTeam.get(i).getHeight()).padLeft(12).padRight(12).left();
        }

        if (unitTeam.size() < numOfUnits) {
            for (int i = 0; i < numOfUnits - unitTeam.size(); i++) {
                add().width(unitTeam.get(0).getWidth()).height(unitTeam.get(0).getHeight()).padLeft(12).padRight(12).left();
            }
        }

        width = (unitTeam.get(0).getWidth() + 24) * numOfUnits;
//        width = (unitTeam.get(0).getWidth() + 24) * numOfUnits;

        /** добавим горизонтальную серую черту-разделитель **/

        row();
        float lineHeight = 0;

//        for (int i = 1; i < 6; i++) {
//            Image line = new Image(Warfare.atlas.findRegion("line"));
//            lineHeight = line.getHeight();
//            add(line).width(line.getWidth()).height(line.getHeight()).padTop(16);
//        }

        add(collectionLabel).colspan(5).center().expandX().padTop(8);

        height = unitTeam.get(0).getHeight() + lineHeight + 16;         //  высота таблицы в px

        setWidth(width);
        setHeight(height);
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
}
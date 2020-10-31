package com.timgapps.warfare.Level.GUI.Screens.team_upgrade_screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Level.GUI.team_unit.TeamUnit;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;


/**
 * Таблица с "ЮНИТАМИ В КОМАНДЕ", используется в классе TeamUpgradeScreen
 */
public class TeamTable extends Table {
    private ArrayList<TeamUnit> unitTeam; // массив юнитов "КОМАНДА"
    private float width, height;
    private int numOfUnits = 5;
    private Label collectionLabel;
    private String collectionText;

    /**
     * таблица с командой юнитов
     **/
    public TeamTable(ArrayList<TeamUnit> unitTeam) {
        this.unitTeam = unitTeam;

        this.left().top();

        // надпись "КОЛЛЕКЦИЯ"
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font40;
        collectionText = "Collection";
        collectionLabel = new Label(collectionText, labelStyle);

        redraw(unitTeam);

//        for (int i = 0; i < unitTeam.size(); i++) {
//            add(unitTeam.get(i).getUnitImageButton()).width(unitTeam.get(i).getImageButtonWidth()).height(unitTeam.get(i).getImageButtonHeight()).padLeft(12).padRight(12).left();
//        }
//
//        /** проверим, если количество юнитов в команде, меньше ячеек,
//         * то делаем оставшиеся ячейки пустыми
//         */
//        if (unitTeam.size() < numOfUnits) {
//            for (int i = 0; i < numOfUnits - unitTeam.size(); i++) {
//                add().width(unitTeam.get(0).getImageButtonWidth()).height(unitTeam.get(0).getImageButtonHeight()).padLeft(12).padRight(12).left();
//            }
//        }
//
//        width = (unitTeam.get(0).getImageButtonWidth() + 24) * numOfUnits;
//
//        /** добавим горизонтальную серую черту-разделитель **/
//        row();
//        float lineHeight = 0;
//        add(collectionLabel).colspan(5).center().expandX().padTop(8);
//        height = unitTeam.get(0).getImageButtonHeight() + lineHeight + 16;         //  высота таблицы в px
//
//        // установим ширину и высоту таблицы
//        setWidth(width);
//        setHeight(height);
    }

    public void redraw(ArrayList<TeamUnit> team) {
        unitTeam = team;
        clear();
        for (int i = 0; i < unitTeam.size(); i++) {
            add(unitTeam.get(i).getUnitImageButton()).width(unitTeam.get(i).getImageButtonWidth()).height(unitTeam.get(i).getImageButtonHeight()).padLeft(12).padRight(12).left();
        }
        /** проверим, если количество юнитов в команде, меньше ячеек,
         * то делаем оставшиеся ячейки пустыми
         */
        if (unitTeam.size() < numOfUnits) {
            for (int i = 0; i < numOfUnits - unitTeam.size(); i++) {
                add().width(unitTeam.get(0).getImageButtonWidth()).height(unitTeam.get(0).getImageButtonHeight()).padLeft(12).padRight(12).left();
            }
        }
        width = (unitTeam.get(0).getImageButtonWidth() + 24) * numOfUnits;
        /** добавим горизонтальную серую черту-разделитель **/
        row();
        float lineHeight = 0;
        add(collectionLabel).colspan(5).center().expandX().padTop(8);
        height = unitTeam.get(0).getImageButtonHeight() + lineHeight + 16;         //  высота таблицы в px
        // установим ширину и высоту таблицы
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

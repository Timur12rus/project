package com.timgapps.warfare.Level.GUI.Screens.team_upgrade_screen;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Level.GUI.team_unit.TeamUnit;

import java.util.ArrayList;

/**
 * Создадим таблицу КОЛЛЕКЦИИ ЮНИТОВ
 * unitCollection - массив юнитов в коллекции
 **/
public class CollectionTable extends Table {
    private ArrayList<TeamUnit> unitCollectiion;
    private float width, height;

    public CollectionTable(ArrayList<TeamUnit> unitCollection) {
        this.unitCollectiion = unitCollection;
//        for (int j = 0; j < unitCollection.size(); j++) {

        this.left().top();

        height = unitCollection.get(0).getImageButtonHeight();
        width = (unitCollection.get(0).getImageButtonWidth() + 24) * 5;
        for (int i = 0; i < unitCollection.size(); i++) {
//            unitCollection.get(i);
            if (i % 5 == 0) {
//                row().padBottom(24);
                row().padTop(24);
                height += unitCollection.get(0).getImageButtonHeight();
            }
            add(unitCollection.get(i).getUnitImageButton()).width(unitCollection.get(i).getImageButtonWidth()).height(unitCollection.get(i).getImageButtonHeight()).left().padLeft(12).padRight(12);
        }
    }

    public void clear() {
        this.clearChildren();
    }

    public void updateCellTable(ArrayList<TeamUnit> unitCollection) {
        for (int i = 0; i < unitCollection.size(); i++) {
            unitCollection.get(i);
            add(unitCollection.get(i).getUnitImageButton()).width(unitCollection.get(i).getImageButtonWidth()).height(unitCollection.get(i).getImageButtonHeight()).left().padLeft(12).padRight(12);
        }
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

package com.timgapps.warfare.screens.map.windows.team_upgrade_window;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.screens.map.windows.team_upgrade_window.team_unit.TeamUnit;

import java.util.ArrayList;

/**
 * Создадим таблицу КОЛЛЕКЦИИ ЮНИТОВ
 * unitCollection - массив юнитов в коллекции
 **/
public class CollectionTable extends Table {
    private ArrayList<TeamUnit> unitCollection;
    private float width, height;

    public CollectionTable(ArrayList<TeamUnit> unitCollection) {
        this.unitCollection = unitCollection;
//        for (int j = 0; j < unitCollection.size(); j++) {

        this.left().top();

        height = unitCollection.get(0).getImageButtonHeight();
        width = (unitCollection.get(0).getImageButtonWidth() + 24) * 5;
        for (int i = 0; i < unitCollection.size(); i++) {
//            unitCollection.get(i);
            if (i % 5 == 0) {       //  переводим на следующую строку таблицы (если юнитов в строке больше 5)
//                row().padBottom(24);
                row().padTop(24);
                height += unitCollection.get(0).getImageButtonHeight();
            }
            add(unitCollection.get(i).getUnitImageButton()).width(unitCollection.get(i).getImageButtonWidth()).height(unitCollection.get(i).getImageButtonHeight()).left().padLeft(12).padRight(12);
        }
    }

    // метод перерисовывает значки в коллекции юнитов
    public void redraw(ArrayList<TeamUnit> unitCollection) {
        this.unitCollection = unitCollection;
        clear();
        this.left().top();

        height = unitCollection.get(0).getImageButtonHeight();
        width = (unitCollection.get(0).getImageButtonWidth() + 24) * 5;
        for (int i = 0; i < unitCollection.size(); i++) {
//            unitCollection.get(i);
            if (i % 5 == 0) {       //  переводим на следующую строку таблицы (если юнитов в строке больше 5)
//                row().padBottom(24);
                row().padTop(24);
                height += unitCollection.get(0).getImageButtonHeight();
            }
            add(unitCollection.get(i).getUnitImageButton()).width(unitCollection.get(i).getImageButtonWidth()).height(unitCollection.get(i).getImageButtonHeight()).left().padLeft(12).padRight(12);
        }

//        // установим ширину и высоту таблицы
        setWidth(width);
        setHeight(height);
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

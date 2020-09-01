package com.timgapps.warfare.Level.GUI.Screens.upgrade_window;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;

import java.util.ArrayList;

/** Создадим таблицу КОЛЛЕКЦИИ ЮНИТОВ
 * unitCollection - массив юнитов в коллекции
 * **/
public class CollectionTable extends Table {
    private Image unitImage;
    private ArrayList<TeamEntity> unitCollectiion;

    private float width, height;

    public CollectionTable(ArrayList<TeamEntity> unitCollection) {
        this.unitCollectiion = unitCollection;
//        for (int j = 0; j < unitCollection.size(); j++) {

        this.left().top();

        height = unitCollection.get(0).getHeight();
        width = (unitCollection.get(0).getWidth() + 24) * 5;
        for (int i = 0; i < unitCollection.size(); i++) {
//            unitCollection.get(i);
            if (i % 5 == 0) {
//                row().padBottom(24);
                row().padTop(24);
                height +=  unitCollection.get(0).getHeight();
            }
            add(unitCollection.get(i)).width(unitCollection.get(i).getWidth()).height(unitCollection.get(i).getHeight()).left().padLeft(12).padRight(12);
        }
    }

    public void clear() {
        this.clearChildren();
    }

    public void updateCellTable(ArrayList<TeamEntity> unitCollection) {
        for (int i = 0; i < unitCollection.size(); i++) {
            unitCollection.get(i);
            add(unitCollection.get(i)).width(unitCollection.get(i).getWidth()).height(unitCollection.get(i).getHeight()).left().padLeft(12).padRight(12);
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

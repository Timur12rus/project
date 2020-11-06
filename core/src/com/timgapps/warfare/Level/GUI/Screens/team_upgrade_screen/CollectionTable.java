package com.timgapps.warfare.Level.GUI.Screens.team_upgrade_screen;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Level.GUI.team_unit.TeamUnit;

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

//        for (int i = 0; i < this.unitCollection.size(); i++) {
//            add(this.unitCollection.get(i).getUnitImageButton()).width(this.unitCollection.get(i).getImageButtonWidth()).height(this.unitCollection.get(i).getImageButtonHeight()).padLeft(12).padRight(12).left();
//        }
//        /** проверим, если количество юнитов в команде, меньше ячеек,
//         * то делаем оставшиеся ячейки пустыми
//         */
//        if (this.unitCollection.size() < numOfUnits) {
//            for (int i = 0; i < numOfUnits - this.team.size(); i++) {
//                add().width(this.team.get(0).getImageButtonWidth()).height(this.team.get(0).getImageButtonHeight()).padLeft(12).padRight(12).left();
//            }
//        }
//        width = (this.unitCollection.get(0).getImageButtonWidth() + 24) * numOfUnits;
//        /** добавим горизонтальную серую черту-разделитель **/
//        row();
//        float lineHeight = 0;
//        add(collectionLabel).colspan(5).center().expandX().padTop(8);
//        height = this.team.get(0).getImageButtonHeight() + lineHeight + 16;         //  высота таблицы в px
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

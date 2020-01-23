package com.timgapps.warfare.Level.GUI.Screens.ResourcesView;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;

import java.util.ArrayList;

public class CollectionTable extends Table {
    private Image unitImage;
    private ArrayList<TeamEntity> unitCollectiion;

    public CollectionTable(ArrayList<TeamEntity> unitCollection) {
        this.unitCollectiion = unitCollection;


//        for (int j = 0; j < unitCollection.size(); j++) {
        for (int i = 0; i < unitCollection.size(); i++) {
            unitCollection.get(i);
            add(unitCollection.get(i)).width(unitCollection.get(i).getWidth()).height(unitCollection.get(i).getHeight()).left().padLeft(12).padRight(12);
        }
    }
}

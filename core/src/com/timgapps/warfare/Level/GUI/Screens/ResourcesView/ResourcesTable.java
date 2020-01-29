package com.timgapps.warfare.Level.GUI.Screens.ResourcesView;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Warfare;

public class ResourcesTable extends Table {
    private float height, width;
    private float posX, posY;

    //    private Label foodText, ironText, woodText;
    private Label foodCountLabel, ironCountLabel, woodCountLabel;


    public ResourcesTable(int foodCount, int ironCount, int woodCount) {
        /** Таблица ресурсов **/

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;

//        foodText = new Label("Food", labelStyle);
        foodCountLabel = new Label("" + foodCount, labelStyle);

//        ironText = new Label("Iron", labelStyle);
        ironCountLabel = new Label("" + ironCount, labelStyle);


//        woodText = new Label("Wood", labelStyle);
        woodCountLabel = new Label("" + woodCount, labelStyle);


        setWidth(180);
        height = 224;
        add(new Image(Warfare.atlas.findRegion("food_icon"))).width(64).height(64);
        add(foodCountLabel).expand().left().padLeft(16);

        row();

        add(new Image(Warfare.atlas.findRegion("iron_icon"))).width(64).height(64).padTop(16);
        add(ironCountLabel).expand().left().padLeft(16);
        row();
        add(new Image(Warfare.atlas.findRegion("wood_icon"))).width(64).height(64).padTop(16);
        add(woodCountLabel).expand().left().padLeft(16);
    }

    /**
     * метод для установки текста, отображающего количество ресурсов в таблице
     **/
    public void setResourcesText(int foodCount, int ironCount, int woodCount) {
        foodCountLabel.setText("x " + foodCount);
        ironCountLabel.setText("x " + ironCount);
        woodCountLabel.setText("x " + woodCount);
    }

    public void updateResources(int foodCount, int ironCount, int woodCount) {
        foodCountLabel.setText("" + foodCount);
        ironCountLabel.setText("" + ironCount);
        woodCountLabel.setText("" + woodCount);
    }

    @Override
    public float getHeight() {
        return height;
    }
}

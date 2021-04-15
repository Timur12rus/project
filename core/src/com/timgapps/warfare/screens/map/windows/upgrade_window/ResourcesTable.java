package com.timgapps.warfare.screens.map.windows.upgrade_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Warfare;

public class ResourcesTable extends Group {
    //public class ResourcesTable extends Table {
    private Table table;
    private float height, width;
    private float posX, posY;

    //    private Label foodText, ironText, woodText;
    private Label foodCountLabel, ironCountLabel, woodCountLabel;
    private Image foodImage, ironImage, woodImage;

    private boolean isEndAction = false;


    public ResourcesTable(int foodCount, int ironCount, int woodCount) {

        /** создадим значки для анимации апгрейда **/
        foodImage = new Image(Warfare.atlas.findRegion("food_icon"));
        ironImage = new Image(Warfare.atlas.findRegion("iron_icon"));
        woodImage = new Image(Warfare.atlas.findRegion("wood_icon"));


        /** Таблица ресурсов **/

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;
        foodCountLabel = new Label("" + foodCount, labelStyle);
        ironCountLabel = new Label("" + ironCount, labelStyle);
        woodCountLabel = new Label("" + woodCount, labelStyle);
        table = new Table().debug();
        table.setHeight(224);
        setWidth(180);
        height = 224;
        table.add(new Image(Warfare.atlas.findRegion("food_icon"))).width(64).height(64);
        table.add(foodCountLabel).expand().left().padLeft(16);
        table.row();
        table.add(new Image(Warfare.atlas.findRegion("iron_icon"))).width(64).height(64).padTop(16);
        table.add(ironCountLabel).expand().left().padLeft(16);
        table.row();
        table.add(new Image(Warfare.atlas.findRegion("wood_icon"))).width(64).height(64).padTop(16);
        table.add(woodCountLabel).expand().left().padLeft(16);
        addActor(table);
        foodImage.setPosition(table.getX(), foodImage.getHeight() * 2 + 32);
        ironImage.setPosition(table.getX(), ironImage.getHeight() * 1 + 16);
        woodImage.setPosition(table.getX(), table.getX());
        foodImage.setVisible(false);
        ironImage.setVisible(false);
        woodImage.setVisible(false);
        addActor(foodImage);
        addActor(ironImage);
        addActor(woodImage);
    }

    public void startActions(boolean isShowResoursesImage) {
        foodImage.clearActions();
        ironImage.clearActions();
        woodImage.clearActions();
//        if (isShowResoursesImage) {
            foodImage.setVisible(isShowResoursesImage);
            ironImage.setVisible(isShowResoursesImage);
            woodImage.setVisible(isShowResoursesImage);
//        } else {
//            foodImage.setVisible(false);
//            ironImage.setVisible(false);
//            woodImage.setVisible(false);
//        }
        /**
         SequenceAction sa = new SequenceAction(mtaUp, mtaDown);
         levelIcon.addAction(sa);
         SequenceAction flicker = new SequenceAction(Actions.fadeOut(0.25f), Actions.fadeIn(0.25f));
         image.addAction(flicker);
         **/

        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                isEndAction = true;
                return true;
            }
        };
        SequenceAction maFood = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(-170, 110, 0.7f, new Interpolation.PowIn(3)),
//                Actions.moveTo(-170, 110, 1, new Interpolation.SwingOut(1)),
                Actions.fadeOut(0),
                Actions.moveTo(table.getX(), foodImage.getHeight() * 2 + 32));

        SequenceAction maIron = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(-170, 110, 0.9f, new Interpolation.PowIn(3)),
//                Actions.moveTo(-170, 110, 1.1f, new Interpolation.SwingOut(1)),
                Actions.fadeOut(0),
                Actions.moveTo(table.getX(), ironImage.getHeight() * 1 + 16));

        SequenceAction maWood = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(-170, 110, 1.1f, new Interpolation.PowIn(3)),
//        SequenceAction maWood = new SequenceAction(Actions.moveTo(-170, 110, 1.2f, new Interpolation.SwingOut(1)),
                Actions.fadeOut(0),
                Actions.moveTo(table.getX(), table.getY()),
                checkEndOfAction);
        foodImage.addAction(maFood);
        ironImage.addAction(maIron);
        woodImage.addAction(maWood);
    }

    public boolean getIsEndAction() {
        return isEndAction;
    }

    public void setIsEndAction(boolean flag) {
        isEndAction = flag;
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

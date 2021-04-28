package com.timgapps.warfare.screens.map.windows.team_upgrade_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Utils.StringHolder;
import com.timgapps.warfare.screens.map.windows.team_upgrade_window.team_unit.TeamUnit;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;


/**
 * Таблица с "ЮНИТАМИ В КОМАНДЕ", используется в классе TeamUpgradeScreen
 */
public class TeamTable extends Table {
    private ArrayList<TeamUnit> team; // массив юнитов "КОМАНДА"
    private float width, height;
    private int numOfUnits = 5;
    private Label collectionLabel;
    private String collectionText;

    /**
     * таблица с командой юнитов
     **/
    public TeamTable(ArrayList<TeamUnit> team) {
        this.team = team;
        this.left().top();
        // надпись "КОЛЛЕКЦИЯ"
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font40;
        collectionText = Warfare.stringHolder.getString(StringHolder.COLLECTION);
        collectionLabel = new Label(collectionText, labelStyle);
        redraw(team);
    }

    // метод перерисовывает таблицу с командой юнитов
    public void redraw(ArrayList<TeamUnit> team) {
        this.team = team;
        clear();

        for (int i = 0; i < this.team.size(); i++) {
            if (team.get(i).isCanUpgrade()) {
                team.get(i).startCanUpgradeAction();
            }
            add(this.team.get(i).getUnitImageButton()).width(this.team.get(i).getImageButtonWidth()).height(this.team.get(i).getImageButtonHeight()).padLeft(12).padRight(12).left();
        }
        /** проверим, если количество юнитов в команде, меньше ячеек,
         * то делаем оставшиеся ячейки пустыми
         */
        if (this.team.size() < numOfUnits) {
            for (int i = 0; i < numOfUnits - this.team.size(); i++) {
                add().width(this.team.get(0).getImageButtonWidth()).height(this.team.get(0).getImageButtonHeight()).padLeft(12).padRight(12).left();
            }
        }
        width = (this.team.get(0).getImageButtonWidth() + 24) * numOfUnits;
        /** добавим горизонтальную серую черту-разделитель **/
        row();
        float lineHeight = 0;
        add(collectionLabel).colspan(5).center().expandX().padTop(8);
        height = this.team.get(0).getImageButtonHeight() + lineHeight + 16;         //  высота таблицы в px
        // установим ширину и высоту таблицы
        setWidth(width);
        setHeight(height);
    }

    public int getMaxNumOfUnits() {
        return numOfUnits;
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

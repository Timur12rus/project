package com.timgapps.warfare.screens.map.windows.upgrade_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;
import com.timgapps.warfare.Utils.StringHolder;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.map.windows.team_window.team_unit.TeamUnit;

// горизонтальная таблица с кол-вом ресурсов для апгрейда
public class UpgradeCostTable extends Table {
    private Label upgradeCostLabel;     // надпись "стоимость апгрейда"
    private String upgradeCostText = Warfare.stringHolder.getString(StringHolder.UPGRADE_COST);
    private Label foodCostLabel, ironCostLabel, woodCostLabel, upgradeToLevelLabel;
    private int foodCostValue, ironCostValue, woodCostValue;        // значение количества ресурсов, необходимое для апргрейда
    private Image foodIcon, ironIcon, woodIcon;
    //    private String upgradeToLevelText = "Upgrade to Level ";
    private int nextUnitLevel;          // номер следующего уровня юнита

    public UpgradeCostTable() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font30;
        upgradeCostLabel = new Label(upgradeCostText, labelStyle);          // текст "СТОИМОСТЬ УЛУЧШЕНИЯ"
//        upgradeToLevelLabel = new Label(upgradeToLevelText + nextUnitLevel, labelStyle);
        upgradeCostLabel.setAlignment(Align.center);
        Table line = new Table();
        line.setBackground(new TextureRegionDrawable(Warfare.atlas.findRegion("div_line")));
        line.add(upgradeCostLabel).width(500);

        /** значки для ресурсов**/
        foodIcon = new Image(Warfare.atlas.findRegion("food_icon"));
        ironIcon = new Image(Warfare.atlas.findRegion("iron_icon"));
        woodIcon = new Image(Warfare.atlas.findRegion("wood_icon"));

        foodCostLabel = new Label("" + foodCostValue, labelStyle);
        ironCostLabel = new Label("" + ironCostValue, labelStyle);
        woodCostLabel = new Label("" + woodCostValue, labelStyle);

        add(line).colspan(6).padTop(24).padBottom(16);
        row();
        add(foodIcon).width(64).left().padLeft(96);
        add(foodCostLabel).expandX().left().padLeft(16);
        add(ironIcon).width(64).left();
        add(ironCostLabel).expandX().left().padLeft(16);
        add(woodIcon).width(64).left();
        add(woodCostLabel).padLeft(16).padRight(96);
        row();
//        add(upgradeToLevelLabel).colspan(6).padTop(32);
    }


    public void redraw(TeamUnit teamUnit) {
        foodCostValue = teamUnit.getUnitData().getFoodValueForUpgrade();
        ironCostValue = teamUnit.getUnitData().getIronValueForUpgrade();
        woodCostValue = teamUnit.getUnitData().getWoodValueForUpgrade();
        foodCostLabel.setText("" + foodCostValue);
        ironCostLabel.setText("" + ironCostValue);
        woodCostLabel.setText("" + woodCostValue);
        if (teamUnit.getUnitData().getUnitId().equals(PlayerUnits.Rock)) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }

    public int getFoodCostValue() {
        return foodCostValue;
    }

    public int getIronCostValue() {
        return ironCostValue;
    }

    public int getWoodCostValue() {
        return woodCostValue;
    }
}

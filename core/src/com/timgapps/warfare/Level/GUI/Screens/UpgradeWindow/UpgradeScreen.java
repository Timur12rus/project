package com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;
import com.timgapps.warfare.Warfare;

public class UpgradeScreen extends Group {
    private Image background;
    private Image unitImage;
    private ImageButton closeButton;
    private Table infoTable;        // таблица содержит данные о характеристиках юнита

    private Table container;

    private Label healthLabel;      // текст "ЗДОРОВЬЕ"
    private Label damageLabel;      // текст "УРОН"
    private Label speedLabel;       // текст "СКОРОСТЬ"
    private Label upgradeCostLabel;       // текст "СТОИМОСТЬ АПРГРЕЙДА"

    private Label healthValueLabel;      // текст количество здоровья
    private Label damageValueLabel;      // текст количество урона
    private Label speedValueLabel;       // текст количество скорость

    private Label foodCostLabel, ironCostLabel, woodCostLabel, upgradeToLevelLabel;

    private int foodCostValue, ironCostValue, woodCostValue;

    private int healthValue;
    private int damageValue;
    private int speedValue;

    private String healthText = "Health";
    private String damageText = "Damage";
    private String speedText = "Speed";
    private String upgradeCostText = "Upgrade cost";
    private String upgradeToLevelText = "Upgrade to Level ";

    private float containerX, containerY;
    private float paddingLeft = 48;

    private Image foodIcon, ironIcon, woodIcon;

    private UpgradeButton upgradeButton;


    public UpgradeScreen() {

        /** зададим значения характеристик юнита**/
//        containerX = x;
//        containerY = y;

        foodIcon = new Image(Warfare.atlas.findRegion("food_icon"));
        ironIcon = new Image(Warfare.atlas.findRegion("iron_icon"));
        woodIcon = new Image(Warfare.atlas.findRegion("wood_icon"));

        healthValue = 100;
        damageValue = 12;
        speedValue = 10;

        foodCostValue = 2;
        ironCostValue = 2;
        woodCostValue = 1;

        background = new Image(Warfare.atlas.findRegion("upgradeScreen"));
        background.setX((Warfare.V_WIDTH - background.getWidth()) / 2); // устанавливаем позицию заголовка
        background.setY(Warfare.V_HEIGHT / 2 - background.getHeight() / 2);

//        background.setX(x); // устанавливаем позицию заголовка
//        background.setY(y);

        addActor(background);

        unitImage = new Image(Warfare.atlas.findRegion("thorActive"));      // изображение юнита

        container = new Table().debug();        // таблица "КОНТЕЙНЕР"
        infoTable = new Table().debug();       // таблица с характеристиками юнита

        upgradeButton = new UpgradeButton();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;

//        Label.LabelStyle upgradeLabelStyle = new Label.LabelStyle();
//        labelStyle.fontColor = Color.DARK_GRAY;
//        labelStyle.font = Warfare.font20;


        healthLabel = new Label(healthText, labelStyle);        // текст "ЗДОРОВЬЕ"
        damageLabel = new Label(damageText, labelStyle);        // текст "УРОН"
        speedLabel = new Label(speedText, labelStyle);          // текст "СКОРОСТЬ"

        healthValueLabel = new Label("" + healthValue, labelStyle);        // текст значения здоровья
        damageValueLabel = new Label("" + damageValue, labelStyle);        // текст значения урон
        speedValueLabel = new Label("" + speedValue, labelStyle);          // текст значения скорость
        upgradeCostLabel = new Label(upgradeCostText, labelStyle);          // текст значения скорость

        healthLabel.setAlignment(Align.right);
        damageLabel.setAlignment(Align.right);
        speedLabel.setAlignment(Align.right);

        healthValueLabel.setAlignment(Align.left);
        damageValueLabel.setAlignment(Align.left);
        speedValueLabel.setAlignment(Align.left);

        upgradeCostLabel.setAlignment(Align.center);


        foodCostLabel = new Label("" + foodCostValue, labelStyle);
        ironCostLabel = new Label("" + ironCostValue, labelStyle);
        woodCostLabel = new Label("" + woodCostValue, labelStyle);
        upgradeToLevelLabel = new Label(upgradeToLevelText + 1, labelStyle);


//        container.setWidth(background.getWidth());
        container.setHeight(600);
//        infoTable.setWidth(background.getWidth() / 2);
//        container.add(infoTable).left().expandX();
//        container.add(infoTable).left().fillX();

//        infoTable.left().top();


        container.left().top();
        container.add(infoTable).left();
//        container.add(infoTable).width(background.getWidth() / 2).left();

        infoTable.add(healthLabel).width(200).padRight(8);
        infoTable.add(new Image(Warfare.atlas.findRegion("heart_icon"))).width(56).height(56);
        infoTable.add(healthValueLabel).fillX().padLeft(8);

        infoTable.row();
        infoTable.add(damageLabel).width(200).padRight(8);
        infoTable.add(new Image(Warfare.atlas.findRegion("damage_icon"))).width(56).height(56);
        infoTable.add(damageValueLabel).fillX().padLeft(8);

        infoTable.row();

        infoTable.add(speedLabel).width(200).padRight(8);
        infoTable.add(new Image(Warfare.atlas.findRegion("speed_icon"))).width(56).height(56);
        infoTable.add(speedValueLabel).fillX().padLeft(8);

//        container.add(unitImage).width(200).padLeft(32).left();
//        infoTable.add(unitImage).width(unitImage.getWidth()).padLeft(32).left();
        container.add(unitImage).width(unitImage.getWidth()).padLeft(32).left();

//        container.add(new Image(Warfare.atlas.findRegion("div_line"))).width(500).colspan(2).left().padLeft(64).padTop(24);


        Table costUpgradeTable = new Table().debug();

        Table line = new Table();
        line.setBackground(new TextureRegionDrawable(Warfare.atlas.findRegion("div_line")));
        line.add(upgradeCostLabel).width(500);

        costUpgradeTable.add(line).colspan(6).padTop(24).padBottom(16);
        costUpgradeTable.row();
        costUpgradeTable.add(foodIcon).width(64).left();
        costUpgradeTable.add(foodCostLabel).expandX().left().padLeft(16);
//        costUpgradeTable.add(foodCostLabel).expandX().left();

        costUpgradeTable.add(ironIcon).width(64).left();
        costUpgradeTable.add(ironCostLabel).expandX().left().padLeft(16);
//        costUpgradeTable.add(ironCostLabel).expandX().left();

        costUpgradeTable.add(woodIcon).width(64).left();
        costUpgradeTable.add(woodCostLabel).padLeft(16);
        costUpgradeTable.row();
        costUpgradeTable.add(upgradeToLevelLabel).colspan(6).padTop(24);

        container.row();
        container.add(costUpgradeTable).colspan(3);
//        container.add(costUpgradeTable).colspan(3);
//        container.add(line).colspan(3).padTop(24);

        container.row();

        container.add(upgradeButton).height(upgradeButton.getHeight()).width(upgradeButton.getWidth()).colspan(3).padTop(8);

//        container.add(foodIcon).width(64);
//        container.add(ironIcon).width(64);
//        container.add(woodIcon).width(64);


        container.setPosition(background.getX() + paddingLeft, background.getY());
        addActor(container);
        setVisible(false);
    }

    public void showUpgradeScreen(TeamEntity teamEntity) {
        unitImage = teamEntity.getImage();
        setVisible(true);
    }

}

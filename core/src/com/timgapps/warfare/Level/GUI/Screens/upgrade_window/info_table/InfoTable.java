package com.timgapps.warfare.Level.GUI.Screens.upgrade_window.info_table;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.Warfare;

// таблица содержит данные о характеристиках юнита
public class InfoTable extends Table {
    private int healthValue;        // значение здоровья юнита
    private int damageValue;        // значениеурона юнита
    private int speedValue;         // значение скорость юнита
    private int timePrepearValue;     // значние время появления юнита
    private Table container;        // контейнер для изображения юнита и его значка со значением уровня
    private Label healthLabel;      // текст "ЗДОРОВЬЕ"
    private Label damageLabel;      // текст "УРОН"
    private Label speedLabel;       // текст "СКОРОСТЬ"
    private Label timePrepearLabel;       // текст "ВРЕМЯ ПОЯВЛЕНИЯ"
    private Label upgradeCostLabel;       // текст "СТОИМОСТЬ АПРГРЕЙДА"
    private Label healthValueLabel;      // текст количество "здоровья"
    private Label damageValueLabel;      // текст количество "урона"
    private Label speedValueLabel;       // текст количество "скорость"
    private Label timePrepearValueLabel;       // текст значение "время появления"
    private Label healthAddValueLabel;      // текст на сколько прибавится "здоровье"
    private Label damageAddValueLabel;      // текст на сколько прибавится "здоровье"

    private String healthText = "Health";
    private String damageText = "Damage";
    private String speedText = "Speed";
    private String timePrepearText = "Time prepear";
    private float paddingLeft = 48;
    private PlayerUnitData data;
    private int addHealthValue = 2;
    private int addDamageValue = 2;
    private Label.LabelStyle labelStyle;
    private Label.LabelStyle greenLabelStyle;

    public InfoTable() {
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(new Color(0xfde9c0ff));
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        setBackground(textureRegionDrawableBg);     // установим цветной фон

        labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;

        greenLabelStyle = new Label.LabelStyle();
        greenLabelStyle.fontColor = Color.FOREST;
        greenLabelStyle.font = Warfare.font20;

        healthLabel = new Label(healthText, labelStyle);                // текст "ЗДОРОВЬЕ"
        damageLabel = new Label(damageText, labelStyle);                // текст "УРОН"
        speedLabel = new Label(speedText, labelStyle);                  // текст "СКОРОСТЬ"
        timePrepearLabel = new Label(timePrepearText, labelStyle);      // текст "ВРЕМЯ ПОЯВЛЕНИЯ"

        healthValueLabel = new Label("", labelStyle);        // текст значения здоровья
        damageValueLabel = new Label("", labelStyle);        // текст значения урон
        speedValueLabel = new Label("", labelStyle);          // текст значения скорость
        timePrepearValueLabel = new Label("", labelStyle);          // текст значения скорость
        healthAddValueLabel = new Label(" + " + addHealthValue, greenLabelStyle);  // текст на сколько прибавится здоровья
        damageAddValueLabel = new Label(" + " + addDamageValue, greenLabelStyle);  // текст на сколько прибавится урон

        healthLabel.setAlignment(Align.right);
        damageLabel.setAlignment(Align.right);
        speedLabel.setAlignment(Align.right);
        timePrepearLabel.setAlignment(Align.right);
        healthValueLabel.setAlignment(Align.left);
        damageValueLabel.setAlignment(Align.left);
        speedValueLabel.setAlignment(Align.left);
        timePrepearValueLabel.setAlignment(Align.left);

        // добавим все надписи в таблицу
        add(healthLabel).width(200).padRight(8);
        add(new Image(Warfare.atlas.findRegion("heart_icon"))).width(56).height(56);
        add(healthValueLabel).width(48).padLeft(8);
        add(healthAddValueLabel);
        row();

        add(damageLabel).width(200).padRight(8);
        add(new Image(Warfare.atlas.findRegion("damage_icon"))).width(56).height(56);
        add(damageValueLabel).fillX().padLeft(8);
        add(damageAddValueLabel);
        row();

        add(speedLabel).width(200).padRight(8);
        add(new Image(Warfare.atlas.findRegion("speed_icon"))).width(56).height(56);
        add(speedValueLabel).fillX().padLeft(8);
        row();

        add(timePrepearLabel).width(200).padRight(8);
        add(new Image(Warfare.atlas.findRegion("clock_icon"))).width(56).height(56);
        add(timePrepearValueLabel).fillX().padLeft(8);
    }

    // метод для перерисовки данных о характеристиках юнита
    public void  redraw(PlayerUnitData data) {
        this.data = data;
        healthValueLabel.setText("" + data.getHealth());        // текст значения здоровья
        damageValueLabel.setText("" + data.getDamage());        // текст значения урон
        speedValueLabel.setText("" + (int) data.getSpeed() * 10 * 2);       // текст значения скорость
        timePrepearValue = data.getPrepareTime();           // время приготовления
        timePrepearValueLabel.setText("" + timePrepearValue);
    }

    public int getAddDamageValue() {
        return addDamageValue;
    }

    public int getAddHealthValue() {
        return addHealthValue;
    }

    public void showUpgradeLabels() {
        healthAddValueLabel.setVisible(true);
        damageAddValueLabel.setVisible(true);
    }

    public void hideUpgradeLabels() {
        healthAddValueLabel.setVisible(false);
        damageAddValueLabel.setVisible(false);
    }

}

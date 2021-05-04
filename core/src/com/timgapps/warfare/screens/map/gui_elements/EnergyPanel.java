package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Warfare;

public class EnergyPanel extends Group {
    private Table table;
    private int coinsCount;
    private Label energyCountLabel;
    private Image energyIcon;
    private LevelScreen levelScreen;
    private TextureRegionDrawable background;

    public EnergyPanel(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;

        /** Изображение ЗНАЧОК ЭНЕРГИИ **/
        energyIcon = new Image(Warfare.atlas.findRegion("energyIcon"));
        background = new TextureRegionDrawable(Warfare.atlas.findRegion("coinsPanel"));

        /** Надпись о КОЛИЧЕСТВЕ ЭНЕРГИИ **/
        Label.LabelStyle energyLabelStyle = new Label.LabelStyle();
        energyLabelStyle.fontColor = Color.CYAN;
//        energyLabelStyle.fontColor = new Color(0x35a1afff);
        energyLabelStyle.font = Warfare.font30;
        energyCountLabel = new Label("" + levelScreen.getEnergyCount(), energyLabelStyle);        // надпись "КОЛИЧЕСТВО ЭНЕРГИИ"

        table = new Table();
//        table = new Table().debug();
        table.align(Align.left);
        table.add(energyIcon).width(energyIcon.getWidth());
        table.add(energyCountLabel).padLeft(8).width(64);
        table.setHeight(46);
        table.setWidth(background.getRegion().getRegionWidth());
        table.setBackground(background);
        setWidth(table.getWidth());
        setHeight(table.getHeight());

        addActor(table);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        energyCountLabel.setText("" + levelScreen.getEnergyCount());
    }
}

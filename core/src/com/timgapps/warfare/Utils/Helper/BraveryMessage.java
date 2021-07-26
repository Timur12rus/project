package com.timgapps.warfare.Utils.Helper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.screens.level.level_windows.ColorRectangle;
import com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements.ColorButton;


public class BraveryMessage extends Group {
    private ColorRectangle messageBg;
    private Label bravelLabel;
    private Label infoLabel;
    private Image bravelImage;
    private ColorButton okButton;

    public BraveryMessage(LevelScreen levelScreen, final GameHelper gameHelper) {
        // TODO поменять цвает фона
        float messageBgWidth = 400;
        float messageBgHeight = 300;
        messageBg = new ColorRectangle(0, 0, 520, 400, new Color(Color.LIGHT_GRAY));
//        messageBg.setPosition((levelScreen.getWidth() - messageBgWidth) / 2,
//                (levelScreen.getHeight() - messageBgHeight) / 2);
        setSize(messageBg.getWidth(), messageBg.getHeight());

        Label.LabelStyle labelStyle = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font30;
        bravelLabel = new Label("Bravel", labelStyle);
        infoLabel = new Label("Bravel nakaplivaets avtomaticheski \n Ispolzuite dlia naima unitov", labelStyle);
        bravelImage = new Image(Warfare.atlas.findRegion("bravelImage"));

        bravelLabel.setPosition((getWidth() - bravelLabel.getWidth()) / 2,
                getHeight() - 64);
        bravelImage.setPosition((getWidth() - bravelImage.getWidth()) / 2,
                getHeight() - 124);
        Table infoTable = new Table().debug();
        infoLabel.setAlignment(Align.center);
        infoLabel.setWrap(true);
        infoTable.add(infoLabel).left().width(getWidth() - 64).height(200).expand();
        infoTable.setPosition(32, getHeight() / 2 - 16);
        okButton = new ColorButton("OK", ColorButton.GREEN_BUTTON) {
            @Override
            public void click() {
                gameHelper.hideBravery(getParent());
                super.click();
            }
        };
        okButton.setPosition((getWidth() - okButton.getWidth()) / 2, getY() - 96);
        addActor(messageBg);
        addActor(bravelLabel);
        addActor(bravelImage);
        addActor(infoTable);
        addActor(okButton);
    }
}

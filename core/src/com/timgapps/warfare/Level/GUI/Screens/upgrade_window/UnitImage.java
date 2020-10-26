package com.timgapps.warfare.Level.GUI.Screens.upgrade_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.Level.GUI.team_unit.UnitLevelIcon;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;
import com.timgapps.warfare.Warfare;


/**
 * Класс - ИЗОБРАЖЕНИЕ ЮНИТА в ОКНЕ АПГРЕЙДА  и его значков: уровня и стоимости энергии для появления
 **/
public class UnitImage extends Group {
    private Label levelLabel;       // надпись, текущий уровень юнита
    private Label energyLabel;       // надпись, стоимость энергии
    private com.timgapps.warfare.Level.GUI.team_unit.UnitLevelIcon levelIcon;       // значок для отображения текущего уровня
    //    private Image levelIcon;       // значок для отображения текущего уровня
    private Image energyIcon;
    private Image image;       // изображение юнита
    private int unitLevel;      // значение текущего уровня юнита
    private String textureRegionName = "gnomeUnitImage";
    private Table table;
    private ColorButton selectButton;

    /**
     * Конструктор
     *
     * @param unitId     - тип юнита
     * @param unitLevel  - текущий уровень юнита
     * @param energyCost - кол-во энергии, необходимое для появления юнита
     */
    public UnitImage(PlayerUnits unitId, int unitLevel, int energyCost) {
        this.unitLevel = unitLevel;
        switch (unitId) {
            case Gnome:
                textureRegionName = "gnomeUnitImage";
                break;
            case Archer:
                textureRegionName = "archerUnitImage";
                break;
            case Thor:
                textureRegionName = "thorUnitImage";
                break;
            case Knight:
                textureRegionName = "knightUnitImage";
                break;
            case Stone:
                textureRegionName = "rockUnitImage";
                break;
        }
        image = new Image(Warfare.atlas.findRegion(textureRegionName));         // зададим изображение юнита
        energyIcon = new Image(Warfare.atlas.findRegion("energyIcon"));    // зададим изображение юнита
        selectButton = new ColorButton("Select", ColorButton.YELLOW_BUTTON);
        levelIcon = new com.timgapps.warfare.Level.GUI.team_unit.UnitLevelIcon(unitLevel);
        levelIcon.setPosition(image.getWidth(), image.getHeight() - levelIcon.getHeight());
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;
        Label.LabelStyle energyLabelStyle = new Label.LabelStyle();
        energyLabelStyle.fontColor = new Color(0x35a1afff);
        energyLabelStyle.font = Warfare.font20;
        /** текст, отображает текущий уровень юнита**/
        energyLabel = new Label("" + energyCost, energyLabelStyle);

        /** таблица, которая содержит значок энергии и надпись - т.е. в общем значок сколько нужно энергии **/
        table = new Table();
//        table = new Table().debug();
        table.setHeight(energyIcon.getHeight());

        table.add(energyLabel).padTop(16);
        table.add(energyIcon).width(energyIcon.getWidth()).height(energyIcon.getHeight());
        table.setPosition(image.getWidth(), image.getY() - energyIcon.getHeight() / 2);
        selectButton.setPosition(16 + (image.getWidth() - selectButton.getWidth()) / 2,
                (-selectButton.getHeight()) - 32);
        addActor(image);
        addActor(levelIcon);
        addActor(table);
        addActor(selectButton);
    }

    public UnitLevelIcon getUnitLevelIcon() {
        return levelIcon;
    }


    /**
     * метод применяет действие к значку УРОВЕНЬ ИГРОКА
     **/
    public void startAction() {
        MoveToAction mtaUp = new MoveToAction();
        MoveToAction mtaDown = new MoveToAction();
        float startPosY = levelIcon.getY();
        mtaUp.setPosition(levelIcon.getX(), startPosY + 16);
        mtaUp.setDuration(0.3f);
        mtaDown.setPosition(levelIcon.getX(), startPosY);
        mtaDown.setDuration(0.3f);
        SequenceAction sa = new SequenceAction(mtaUp, mtaDown);
        levelIcon.addAction(sa);
        SequenceAction flicker = new SequenceAction(Actions.fadeOut(0.25f), Actions.fadeIn(0.25f));
        image.addAction(flicker);
    }

    public void clearActions() {
        levelIcon.clearActions();
        image.clearActions();
    }

    public void setLevelValue(int levelValue) {
        levelIcon.setLevelValue(levelValue);
    }

    public ColorButton getSelectButton() {
        return selectButton;
    }

    public Image getImage() {
        return image;
    }

}

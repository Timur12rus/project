package com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timgapps.warfare.screens.map.windows.team_upgrade_window.team_unit.UnitLevelIcon;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.map.windows.upgrade_window.UpgradeEffectStarter;


/**
 * Класс - ИЗОБРАЖЕНИЕ ЮНИТА в ОКНЕ АПГРЕЙДА  и его значков: уровня и стоимости энергии для появления
 **/
public class UnitImage extends Group {
    private Label levelLabel;       // надпись, текущий уровень юнита
    private Label energyLabel;       // надпись, стоимость энергии
    private UnitLevelIcon levelIcon;       // значок для отображения текущего уровня
    private Image energyIcon;
    private Image image;       // изображение юнита
    private int unitLevel;      // значение текущего уровня юнита
    private String textureRegionName = "gnomeUnitImage";
    private Table table;
    //    private ColorButton selectButton;
    private boolean isEndAction;        // завершено действие
    private UpgradeEffectStarter upgradeEffectStarter;

    /**
     * Конструктор
     *
     * @param unitId     - тип юнита
     * @param unitLevel  - текущий уровень юнита
     * @param energyCost - кол-во энергии, необходимое для появления юнита
     */
    public UnitImage(PlayerUnits unitId, int unitLevel, int energyCost) {
        this.upgradeEffectStarter = upgradeEffectStarter;
        this.unitLevel = unitLevel;
        textureRegionName = unitId.name().toLowerCase() + "UnitImage";
        image = new Image(Warfare.atlas.findRegion(textureRegionName));         // зададим изображение юнита
        energyIcon = new Image(Warfare.atlas.findRegion("energyIcon"));    // зададим изображение юнита
//        selectButton = new ColorButton("Select", ColorButton.YELLOW_BUTTON);
        levelIcon = new UnitLevelIcon(unitLevel);
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
        addActor(image);
        addActor(levelIcon);
        addActor(table);
//        addActor(selectButton);
    }

    public void setUpgradeEffectStarter(UpgradeEffectStarter upgradeEffectStarter) {
        this.upgradeEffectStarter = upgradeEffectStarter;
    }

    public UnitLevelIcon getUnitLevelIcon() {
        return levelIcon;
    }

    public void hideLevelIcon() {
        levelIcon.setVisible(false);
    }

    public void showLevelIcon() {
        levelIcon.setVisible(true);
    }


    /**
     * метод применяет действие к значку УРОВЕНЬ ИГРОКА
     **/
    public void startAction() {

        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                isEndAction = true;
                return true;
            }
        };

        Action startParticleEffect = new Action() {
            @Override
            public boolean act(float delta) {
                startUpgradeEffect();
                return true;
            }
        };

        MoveToAction mtaUp = new MoveToAction();
        MoveToAction mtaDown = new MoveToAction();
        float startPosY = levelIcon.getY();
        mtaUp.setPosition(levelIcon.getX(), startPosY + 16);
        mtaUp.setDuration(0.3f);
        mtaDown.setPosition(levelIcon.getX(), startPosY);
        mtaDown.setDuration(0.3f);
        SequenceAction sa = new SequenceAction(mtaUp, mtaDown, checkEndOfAction);
        levelIcon.addAction(sa);
        SequenceAction flicker = new SequenceAction(Actions.fadeOut(0.25f),
                startParticleEffect,
                Actions.fadeIn(0.25f));
        image.addAction(flicker);
    }

    public void startUpgradeEffect() {
        System.out.println("StartParticleEffect()");
        upgradeEffectStarter.start();   // запускаем партикл эффект (сияние и звездочки)
    }

    public void setIsEndAction(boolean isEndAction) {
        this.isEndAction = isEndAction;
    }

    private boolean isEndAction() {
        return isEndAction;
    }

    public void clearAction() {
        image.setColor(1, 1, 1, 1);
        levelIcon.clearActions();
        levelIcon.setPosition(image.getWidth(), image.getHeight() - levelIcon.getHeight());
        image.clearActions();
    }

    public void setLevelValue(int levelValue) {
        levelIcon.setLevelValue(levelValue);
    }

//    public ColorButton getSelectButton() {
//        return selectButton;
//    }

    public Image getImage() {
        return image;
    }

}

package com.timgapps.warfare.Level.GUI.Screens.upgrade_window.bottom_group;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.GUI.Screens.upgrade_window.UpgradeWindow;
import com.timgapps.warfare.Warfare;

// группа из кнопки апгрейда, кнопки нанять, надписи "Улучишть до уровня", "Нанять",
public class BottomGroup extends Group {
    private Label upgradeLabel;
    private Label hireLabel;
    private String upgradeToLevelText = "Upgrade to Level ";
    private String hireLabelText = "Hire this unit: ";
    private CoinButton upgradeButton;              // кнопка для апгрейда юнита
    private CoinButton hireButton;                // кнопка для найма(покупки) юнита
    private float WIDTH = 550;
    private float HEIGHT = 160;
    private int nextLevel;      // номер слудющего уровня
    private boolean canBeUpgrade;

    public BottomGroup() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;
        Label.LabelStyle greenLabelStyle = new Label.LabelStyle();
        greenLabelStyle.fontColor = Color.FOREST;
        greenLabelStyle.font = Warfare.font20;
        Label.LabelStyle redLabelStyle = new Label.LabelStyle();
        redLabelStyle.fontColor = Color.RED;
        redLabelStyle.font = Warfare.font20;
        setSize(WIDTH, HEIGHT);

        upgradeButton = new CoinButton();        // зеленая кнопка для апгрейда
        hireButton = new CoinButton();        // зеленая кнопка для апгрейда

        // добавим в группу кнопку апгрейда и надпись "улучшить до следующего уровня"
        upgradeLabel = new Label(upgradeToLevelText, labelStyle);
        addActor(upgradeLabel);
        addActor(upgradeButton);

        // добавим в группу кнопку "нанять" и надпись "нанять этого юнита"
        hireLabel = new Label(hireLabelText, labelStyle);
        hireLabel.setPosition((WIDTH - hireLabel.getWidth()) / 2, 0);
        addActor(hireLabel);
        hireButton.setPosition((WIDTH - hireButton.getWidth()) / 2, -hireButton.getHeight() - 16);
        addActor(hireButton);
    }

    public void setUpgradeCost(int upgradeCost, boolean canUpgrade) {
        this.canBeUpgrade = canUpgrade;
        upgradeButton.setCost(upgradeCost, canUpgrade);
    }

    public void setHireCost(int hireCost, boolean canHire) {
        hireButton.setCost(hireCost, canHire);
    }

    // устанавливает значение следующего уровня апгрейда
    public void setNextLevel(int nextLevel) {
        this.nextLevel = nextLevel;
        upgradeLabel.setText(upgradeToLevelText + nextLevel);
    }

    public void showUpgradeButton() {
        upgradeLabel.setPosition((WIDTH - upgradeLabel.getWidth()) / 2, 0);
        upgradeLabel.setVisible(true);
        upgradeButton.setPosition((WIDTH - upgradeButton.getWidth()) / 2, -upgradeButton.getHeight() - 16);
        upgradeButton.setVisible(true);

    }

    public void hideUpgradeButton() {
        upgradeButton.setVisible(false);
        upgradeLabel.setVisible(false);
    }

    public void showHireButton() {
        hireLabel.setVisible(true);
        hireButton.setVisible(true);
    }

    public void hideHireButton() {
        hireButton.setVisible(false);
        hireLabel.setVisible(false);
    }

    // возвращает upgradeButton
    public CoinButton getUpgradeButton() {
        return upgradeButton;
    }

    // возвращает hareButton
    public CoinButton getHireButton() {
        return hireButton;
    }
}

package com.timgapps.warfare.Level.GUI.Screens.upgrade_window.bottom_group;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Warfare;

// группа из кнопки апгрейда, кнопки нанять, надписи "Улучишть до уровня", "Нанять",
public class BottomGroup extends Group {
    private Label textLabel;
    private Label hareLabel;
    private String upgradeToLevelText = "Upgrade to Level ";
    private String hireLabelText = "Hire this unit: ";
    private CoinButton upgradeButton;              // кнопка для апгрейда юнита
    private CoinButton hireButton;                // кнопка для найма(покупки) юнита
    private float WIDTH = 550;
    private float HEIGHT = 160;
    private int nextLevel;      // номер слудющего уровня

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
        textLabel = new Label(upgradeToLevelText, labelStyle);
        addActor(textLabel);
        addActor(upgradeButton);

        // добавим в группу кнопку "нанять" и надпись "нанять этого юнита"
        hareLabel = new Label(hireLabelText, labelStyle);
        hareLabel.setPosition((WIDTH - hareLabel.getWidth()) / 2, 0);
        addActor(hareLabel);
        hireButton.setPosition((WIDTH - hireButton.getWidth()) / 2, -hireButton.getHeight() - 16);
        addActor(hireButton);

        upgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                /** проверяем, может ли быть сделан апгрейд, если да - делаем апгрейд, если нет проверяем чего не хватает и выводим сообщение **/
//                if (upgradeCostTable.isVisible()) {
//                    if (canBeUpgrade) {
//                        upgradeTeamEntity(teamUnit);
//                    } else {
//                        applyActionsToToast();
//                    }
//                    checkRecourcesAndCoinsCount();
//                } else {        // если покупаем ("призываем" юнита)//
//                    // если места не хватает, то оставляем его в коллекции
//                    hireUnit();         // нанимаем юнита
//                }
            }
        });
    }

    // устанавливает значение следующего уровня апгрейда
    public void setNextLevel(int nextLevel) {
        this.nextLevel = nextLevel;
    }

    public void redraw() {
        textLabel.setText(upgradeToLevelText + nextLevel);
        textLabel.setPosition((WIDTH - textLabel.getWidth()) / 2, 0);
        upgradeButton.setPosition((WIDTH - upgradeButton.getWidth()) / 2, -upgradeButton.getHeight() - 16);
    }

    public void showUpgradeButton() {
        textLabel.setText(upgradeToLevelText + nextLevel);
        textLabel.setPosition((WIDTH - textLabel.getWidth()) / 2, 0);
        textLabel.setVisible(true);
        upgradeButton.setPosition((WIDTH - upgradeButton.getWidth()) / 2, -upgradeButton.getHeight() - 16);
        upgradeButton.setVisible(true);

    }

    public void hideUpgradeButton() {
        upgradeButton.setVisible(false);
        textLabel.setVisible(false);
    }

    public void showHireButton() {
        hireButton.setVisible(true);
        textLabel.setText(hireLabelText);
        textLabel.setVisible(true);
    }

    public void hideHireButton() {
        hireButton.setVisible(false);
        textLabel.setVisible(false);
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

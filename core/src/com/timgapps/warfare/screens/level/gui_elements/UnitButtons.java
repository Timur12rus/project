package com.timgapps.warfare.screens.level.gui_elements;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.screens.level.StoneButton;
import com.timgapps.warfare.screens.map.windows.team_upgrade_window.team_unit.CreateUnitButton;
import com.timgapps.warfare.screens.map.windows.team_upgrade_window.team_unit.TeamUnit;
import com.timgapps.warfare.screens.map.windows.team_upgrade_window.team_unit.UnitImageButton;

import java.util.ArrayList;

public class UnitButtons extends Group {
    private ArrayList<UnitImageButton> unitButtonArrayList;
    private ArrayList<TeamUnit> team;
    private float unitButtonWidth;
    private float unitButtonHeight;
    private StoneButton stoneButton;
    private FireBoosterButton fireBoosterButton;
    protected LevelScreen levelScreen;
    private float stoneButtonXpos;
    private float width, height;

    public UnitButtons(LevelScreen levelScreen, ArrayList<TeamUnit> team) {
//        super();
        this.team = team;
        this.levelScreen = levelScreen;
        unitButtonArrayList = new ArrayList<UnitImageButton>();
        unitButtonWidth = team.get(0).getUnitImageButton().getWidth();
        unitButtonHeight = team.get(0).getUnitImageButton().getHeight();
        stoneButton = null;
        fireBoosterButton = null;
    }

    public void redraw() {
        // добавим кнопки с юнитами в соответствии с имеющимися юнитами в команде
        for (UnitImageButton unitImageButton : unitButtonArrayList) {
            removeActor(unitImageButton);
        }

        width = 0;
        height = 0;
        unitButtonHeight = 0;
        unitButtonArrayList.clear();
        addUnitButtons();

        float stoneButtonPosX = 0;
        float fireBoosterPosX = 0;
        for (int i = 0; i < unitButtonArrayList.size(); i++) {
            unitButtonArrayList.get(i).setPosition((unitButtonWidth + 24) * i, 0);
            width += unitButtonWidth + 24;
            addActor(unitButtonArrayList.get(i));
            if (unitButtonArrayList.get(i).getUnitId() == PlayerUnits.Rock) {
                stoneButtonPosX = i * (unitButtonWidth + 24);
                System.out.println("stoneButton Pos X = " + stoneButtonPosX);
            }
            if (unitButtonArrayList.get(i).getUnitId() == PlayerUnits.Firebooster) {
                fireBoosterPosX = i * (unitButtonWidth + 24);
                System.out.println("FireBoosterButton Pos X = " + fireBoosterPosX);
            }
//            add(unitButtonArrayList.get(i)).width(unitButtonWidth).height(unitButtonHeight).padLeft(12).padRight(12);
        }
        setSize(width, unitButtonHeight);
        this.setPosition((levelScreen.getWidth() - this.getWidth()) / 2, 24);
        setStoneButtonPosX(this.getX() + stoneButtonPosX);
        setFireBoosterButtonPosX(this.getX() + fireBoosterPosX);
        System.out.println("UnitButtons getX() = " + this.getX());
    }

    public void show() {
        redraw();       // обновляем кнопки вызова юнитов
        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }

    public void setStoneButtonPosX(float posX) {
        if (stoneButton != null)
            stoneButton.setPosX(posX);
    }

    public void setFireBoosterButtonPosX(float posX) {
        if (fireBoosterButton != null)
            fireBoosterButton.setPosX(posX);
    }

    public UnitImageButton getUnitButton(int i) {
        return unitButtonArrayList.get(i);
    }

    // метод добавляет кнопки юнитов в соответствии с командой
    void addUnitButtons() {
        for (TeamUnit teamUnit : team) {
            if (teamUnit.getUnitData().getUnitId() != PlayerUnits.Rock) {
                unitButtonArrayList.add(new CreateUnitButton(levelScreen, teamUnit.getUnitData()));
            } else {
                if (teamUnit.getUnitData().getUnitId() == PlayerUnits.Rock) {
                    stoneButton = new StoneButton(levelScreen, teamUnit.getUnitData());
                    unitButtonArrayList.add(stoneButton);
                }
//                if (teamUnit.getUnitData().getUnitId() == PlayerUnits.Firebooster) {
//                    fireBoosterButton = new FireBoosterButton(levelScreen, teamUnit.getUnitData());
//                    unitButtonArrayList.add(fireBoosterButton);
//                }
            }
//            if (teamUnit.getUnitId() != PlayerUnits.Rock) {
//                this.addListener(new ClickListener() {
//                    @Override
//                    public void clicked(InputEvent event, float x, float y) {
//                        super.clicked(event, x, y);
//                    }
//                });
//            }
        }
        if (levelScreen.getGameManager().isHaveFireBooster()) {
            if (fireBoosterButton == null) {
                fireBoosterButton = new FireBoosterButton(levelScreen, new PlayerUnitData(PlayerUnits.Firebooster));
            } else {
                fireBoosterButton.redraw();
            }
            unitButtonArrayList.add(fireBoosterButton);
        }
    }
}
package com.timgapps.warfare.Units.GameUnits;

public class GameUnitController {
    protected GameUnitModel model;

    public GameUnitController(GameUnitModel model) {
        this.model = model;
    }

    public void update() {
    }

    private GameUnitView view;
}

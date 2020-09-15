package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.timgapps.warfare.Units.GameUnits.GameUnitController;

public class PlayerUnitController extends GameUnitController {
    private PlayerUnitModel model;
    private PlayerUnitView view;

    public PlayerUnitController(PlayerUnitModel model) {
        super(model);
        this.model = model;
        move();
    }

    public void move() {
        model.getBody().setLinearVelocity(1, 0);
    }
}

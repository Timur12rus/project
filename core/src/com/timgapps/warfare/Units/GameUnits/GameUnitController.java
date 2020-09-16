package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.physics.box2d.Body;
import com.timgapps.warfare.Level.Level;

public class GameUnitController {
    protected Body body;
    protected GameUnitModel model;
    protected Level level;

    public GameUnitController(GameUnitModel model) {
        this.model = model;
        body = model.getBody();
    }

    public void update() {
    }

    private GameUnitView view;
}

package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;

public class GameUnitController {
    protected Rectangle body;
    protected GameUnitModel model;
    protected Level level;

    public GameUnitController(GameUnitModel model) {
        this.model = model;
        body = model.getBody();
    }

    public void update() {
        model.updateBodyPosition();     // обновляем позицию тела по координатам модели
    }

    public void setVelocity(Vector2 velocity) {
        model.setVelocity(velocity);
    }
}

package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;

public class GameUnitController {
    protected Rectangle body;
    protected GameUnitModel model;
    protected Level level;

    public GameUnitController(Level level, GameUnitModel model) {
        this.level = level;
        this.model = model;
        body = model.getBody();
    }

    public void update(float delta) {
        model.updateBodyPosition();     // обновляем позицию тела по координатам модели
    }

    public void setVelocity(Vector2 velocity) {
        model.setVelocity(velocity);
    }

//    // метод для нанесения урона вражескому юниту
//    public void hit() {
//    }

    public void removeUnitFromLevel(GameUnitModel model) {
        level.getArrayModels().remove(model);
    }

}

package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;

public class GameUnitController {
    protected Rectangle body;
    protected GameUnitModel model;
    protected LevelScreen levelScreen;

    public GameUnitController(LevelScreen levelScreen, GameUnitModel model) {
        this.levelScreen = levelScreen;
        this.model = model;
    }

    public void update(float delta) {
        model.updateBodyPosition();     // обновляем позицию тела по координатам модели
    }

    public void setVelocity(Vector2 velocity) {
        model.setVelocity(velocity);
    }

    public void removeUnitFromLevel(GameUnitModel model) {
        levelScreen.getArrayModels().remove(model);
    }

}

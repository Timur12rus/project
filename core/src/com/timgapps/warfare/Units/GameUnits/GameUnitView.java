package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Player.units.HealthBar;
import com.timgapps.warfare.Utils.Setting;

public class GameUnitView extends Actor {
    protected Animation walkAnimation;            // анимация для ходьбы
    protected Animation attackAnimation;          // анимация для атаки
    protected Animation dieAnimation;             // анимация для уничтожения
    protected Animation stayAnimation;            // анимация для стоит
    protected Animation runAnimation;            // анимация для бежит
    protected ShapeRenderer shapeRenderer;
    protected boolean isDebug = true;
    protected LevelScreen levelScreen;
    protected GameUnitModel model;
    protected GameUnitController controller;
    //    private EnemyController controller;
    protected boolean isDrawHealthBar;
    protected HealthBar healthBar;
    protected float stateTime;
    protected State currentState;

    public enum State {
        WALKING, ATTACK, STAY, DIE, RUN, HART
    }

    public GameUnitView(LevelScreen levelScreen, GameUnitModel model, GameUnitController controller) {
        this.levelScreen = levelScreen;
        this.model = model;
        this.controller = controller;
        currentState = model.getCurrentState();

        /** создадим HealthBar **/
        healthBar = new HealthBar(54, 10, model.getHealth());

        // для отладки
        shapeRenderer = new ShapeRenderer();
        setDebug(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (levelScreen.getState() != LevelScreen.PAUSED) {
            controller.update(delta);
        }
        setPosition(model.getPosition().x, model.getPosition().y);      // обновляем позицию view по координатам позиции модели
        isDrawHealthBar = model.isDrawHealthBar();
    }

    public float getHealth() {
        return model.getHealth();
    }

    public GameUnitModel getModel() {
        return model;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (levelScreen.getState() != LevelScreen.PAUSED) {
            stateTime += Gdx.graphics.getDeltaTime();
        }
        batch.end();
        if (Setting.DEBUG_GAME) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(getX(), getY(), model.getBodyWidth(), model.getBodyHeight());
            shapeRenderer.end();
        }
        batch.begin();
    }

    public void resetStateTime() {
        stateTime = 0;
    }
}

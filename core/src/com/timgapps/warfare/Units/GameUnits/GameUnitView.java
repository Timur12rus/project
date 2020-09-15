package com.timgapps.warfare.Units.GameUnits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Player.units.HealthBar;

public class GameUnitView extends Actor {
    protected Animation walkAnimation;            // анимация для ходьбы
    protected Animation attackAnimation;          // анимация для атаки
    protected Animation dieAnimation;             // анимация для уничтожения
    protected Animation stayAnimation;            // анимация для стоит
    protected Animation runAnimation;            // анимация для бежит
    protected ShapeRenderer shapeRenderer;
    protected boolean isDebug = true;
    private Level level;
    private GameUnitModel model;
    private GameUnitController controller;
    protected boolean isDrawHealthBar;
    protected HealthBar healthBar;
    protected float stateTime;

    @Override
    public void act(float delta) {
        super.act(delta);
        controller.update();
        if (level.getState() != Level.PAUSED) {
            stateTime += Gdx.graphics.getDeltaTime();
        }
        setPosition(model.getBody().getPosition().x * Level.WORLD_SCALE, model.getBody().getPosition().y * Level.WORLD_SCALE);
//        setPosition(model.getPosition().x, model.getPosition().y);
        System.out.println("bodyX = " + model.getPosition());
    }

    public GameUnitView(Level level, GameUnitModel model, GameUnitController controller) {
        this.level = level;
        this.model = model;
        this.controller = controller;

        /** создадим HealthBar **/
        healthBar = new HealthBar(54, 10, model.getHealth());
    }

    public enum State {
        WALKING, ATTACK, STAY, DIE, RUN, HART
    }


}

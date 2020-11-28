package com.timgapps.warfare.Units.GameUnits.Player.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.GameUnitController;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Warfare;

public class PlayerUnitView extends GameUnitView {
    protected PlayerUnitModel model;
    protected float deltaX, deltaY;       // значение в px на сколько нужно сдвигать изобажение юнита относительно его тела, при отрисовке
    protected float healthBarDeltaX;
    protected float healthBarDeltaY;
    protected State currentState;
    protected SequenceAction fadeOutAction;
    protected boolean isAddAction;

    public PlayerUnitView(LevelScreen levelScreen, PlayerUnitModel model, GameUnitController controller) {
        super(levelScreen, model, controller);
        this.model = model;
        deltaX = model.getUnitData().getDeltaX();
        deltaY = model.getUnitData().getDeltaY();
        healthBarDeltaX = model.getUnitData().getBarDeltaX();
        healthBarDeltaY = model.getUnitData().getBarDeltaY();
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                remove();
                return true;
            }
        };
        fadeOutAction = new SequenceAction(Actions.delay(1.5f), Actions.fadeOut(1f),
                checkEndOfAction
        );

        System.out.println("UnitName = " + model.getPlayerUnitData().getName());
        setSize(Warfare.atlas.findRegion(model.getPlayerUnitData().getName().toLowerCase() + "Stay1").getRegionWidth(),
                Warfare.atlas.findRegion(model.getPlayerUnitData().getName().toLowerCase() + "Stay1").getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        if (currentState == State.WALKING) {
            batch.draw((TextureRegion) walkAnimation.getKeyFrame(stateTime, true), getX() + deltaX, getY() + deltaY);
        }
        if (currentState == State.ATTACK) {
            batch.draw((TextureRegion) attackAnimation.getKeyFrame(stateTime, false), getX() + deltaX, getY() + deltaY);
        }
        if (currentState == State.STAY) {
            batch.draw((TextureRegion) stayAnimation.getKeyFrame(stateTime, false), getX() + deltaX, getY() + deltaY);
        }
        if (currentState == State.RUN) {
            batch.draw((TextureRegion) runAnimation.getKeyFrame(stateTime, true), getX() + deltaX, getY() + deltaY);
        }
        if (currentState == State.DIE) {
            batch.draw((TextureRegion) dieAnimation.getKeyFrame(stateTime, false), getX() + deltaX, getY() + deltaY);
        }
        if (isDrawHealthBar) {
            healthBar.drawHealthBar(batch, getX() + healthBarDeltaX, getY() + getHeight() + healthBarDeltaY, model.getHealth());
        }
    }
}

package com.timgapps.warfare.Units.GameUnits.Enemy.zombie_wait1;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.zombie1.Zombie1UnitView;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;

// зомби1, который ждет пока не появится вражеский юнит
public class ZombieWait1View extends Zombie1UnitView {
    private float stayCounter = 120;

    public ZombieWait1View(LevelScreen levelScreen, EnemyUnitModel model, ZombieWait1Controller controller) {
        super(levelScreen, model, controller);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (model.isWait()) {
            if (stayAnimation.isAnimationFinished(stateTime) && stayCounter < 0) {
                stayCounter = 120;
                resetStateTime();
            }
            if (levelScreen.getState() != LevelScreen.PAUSED) {
                stayCounter--;
            }
        }
    }

    @Override
    protected void createAnimations() {
//        String name = model.getUnitData().getUnitId().toString().toLowerCase();
        String name = "zombie1";
        System.out.println("Name = " + name);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk0")));
        walkAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack" + i)));
        attackAnimation = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay2")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay0")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay2")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay2")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay0")));
        stayAnimation = new Animation(0.12f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Die" + i)));
        dieAnimation = new Animation(0.12f, frames);
        frames.clear();
    }
}

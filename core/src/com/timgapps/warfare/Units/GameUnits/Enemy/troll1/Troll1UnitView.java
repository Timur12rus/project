package com.timgapps.warfare.Units.GameUnits.Enemy.troll1;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.Enemy.ork1.Ork1Controller;
import com.timgapps.warfare.Units.GameUnits.Enemy.ork1.Ork1UnitView;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;

public class Troll1UnitView extends Ork1UnitView {
    public Troll1UnitView(LevelScreen levelScreen, EnemyUnitModel model, Ork1Controller controller) {
        super(levelScreen, model, controller);
        WAIT_COUNT = 1;                              // счетчик ожидания, когда юнит атаковал -> стоит и ждет
        createAnimations();
    }

    protected void createAnimations() {
        String name = model.getUnitData().getUnitId().toString().toLowerCase();
        System.out.println("Name = " + name);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk" + i)));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk2")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Walk0")));
        walkAnimation = new Animation(0.09f, frames);
        frames.clear();

        for (int i = 0; i < 7; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Attack" + i)));
        attackAnimation = new Animation(0.06f, frames);
        frames.clear();

//        for (int i = 0; i < 4; i++)
//            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay" + i)));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay2")));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay0")));

        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay0")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay2")));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay0")));

//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay1")));
//        frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Stay0")));
        stayAnimation = new Animation(0.12f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(Warfare.atlas.findRegion(name + "Die" + i)));
        dieAnimation = new Animation(0.1f, frames);
        frames.clear();
    }
}

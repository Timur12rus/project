package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class UnitContainer extends Group {

    private UnitButton unitButton;
    private TextureRegion up;
    private float count = 0;
    private float percentage = 0;
    private float height;
    private float interpolation;
    private float time = 15;


    public UnitContainer(UnitButton unitButton) {
        this.unitButton = unitButton;
        up = new TextureRegion(Warfare.atlas.findRegion("unitButtonDark"));
        height = up.getRegionHeight();

        addActor(unitButton);
        interpolation = (height / time) / 60;
        System.out.println("interpolation = " + interpolation);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!unitButton.getUnitButtonStatus()) {
            batch.setColor(Color.BLACK);
            batch.draw(up, getX(), getY(), up.getRegionWidth(), height - percentage);
            batch.setColor(Color.WHITE);
        }
//        System.out.println("count = " + count / 30);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        count ++;
        if (!unitButton.getUnitButtonStatus()) {
            if (percentage < height) {
                percentage += interpolation;
            } else {
                unitButton.setActive();
            }
        }

//        System.out.println("percentage = " + percentage);


//        System.out.println("count = " + (int) count / 60);

    }
}

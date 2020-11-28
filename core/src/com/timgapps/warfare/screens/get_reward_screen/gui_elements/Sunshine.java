package com.timgapps.warfare.screens.get_reward_screen.gui_elements;


import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class Sunshine extends Group {
    private ArrayList<Image> rays;
    private int raysCount = 10;
    private float radius = 16;

    public Sunshine() {
        rays = new ArrayList<Image>();
        rays.add(new Image(Warfare.atlas.findRegion("sun rays2")));
        rays.add(new Image(Warfare.atlas.findRegion("sun rays2")));
        rays.add(new Image(Warfare.atlas.findRegion("sun rays2")));
        rays.add(new Image(Warfare.atlas.findRegion("sun rays2")));
        rays.add(new Image(Warfare.atlas.findRegion("sun rays2")));
        rays.add(new Image(Warfare.atlas.findRegion("sun rays2")));
        rays.add(new Image(Warfare.atlas.findRegion("sun rays2")));
        rays.add(new Image(Warfare.atlas.findRegion("sun rays2")));
        rays.add(new Image(Warfare.atlas.findRegion("sun rays2")));
        rays.add(new Image(Warfare.atlas.findRegion("sun rays2")));

        setSize((rays.get(0).getHeight() + radius) * 2, (rays.get(0).getHeight() + radius) * 2);
        System.out.println("width = " + getWidth() + ", height = " + getHeight());
        setOrigin(rays.get(0).getWidth() / 2, -radius);
        int i = 1;
        for (Image ray : rays) {
            ray.setColor(1, 1, 1, 0.7f);
            ray.setOrigin(ray.getWidth() / 2, -radius);
//            ray.setRotation(45);
            ray.setRotation((360 / raysCount) * i);
            i++;
            addActor(ray);
        }

        Action rotateAction = Actions.forever((Actions.rotateBy(90, 10)));
        this.addAction(rotateAction);
    }

    public float getRadius() {
        return radius;
    }
    public float getRayWidth() {
        return rays.get(0).getWidth();
    }
}

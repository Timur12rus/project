package com.timgapps.warfare.screens.reward_for_stars.gui_elements;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Warfare;

public  // подсветка для значка награды
class Hilite extends Actor {
    Image image;
    boolean isHilited = false;
    boolean alphaUp = false;

    public Hilite(Group group) {
        image = new Image(Warfare.atlas.findRegion("hiliteImage"));
        image.setVisible(false);
        setSize(image.getWidth(), image.getHeight());
        group.addActor(image);
    }

    public void setHilite() {
        image.setVisible(true);
        isHilited = true;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isHilited) {
            float alpha = image.getColor().a;
            if (alphaUp) {
                alpha += delta;
                if (alpha >= 1) {
                    alpha = 1;
                    alphaUp = false;
                }
            } else {
                alpha -= delta;
                if (alpha < 0) {
                    alpha = 0;
                    alphaUp = true;
                }
            }
            image.setColor(1, 1, 1, alpha);
        }
    }
}
package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Warfare;

public class Finger extends Actor {
    private float x, y;
    private Image fingerImage;

    public Finger(StageGame stageGame) {
        fingerImage = new Image(Warfare.atlas.findRegion("finger"));
        setSize(fingerImage.getWidth(), fingerImage.getHeight());
    }
}

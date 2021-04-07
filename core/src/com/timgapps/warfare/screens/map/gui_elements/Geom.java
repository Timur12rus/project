package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Geom {
    private String name;
    private Vector2 size;
    private Vector2 position;

    public Geom(Actor actor, String name, GeomHolder geomHolder) {
        this.name = name;
        create(actor);
        geomHolder.addGeom(this);
    }

    public void create(Actor actor) {
        this.size = new Vector2(actor.getWidth(), actor.getHeight());
        this.position = new Vector2(actor.getX(), actor.getY());
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}

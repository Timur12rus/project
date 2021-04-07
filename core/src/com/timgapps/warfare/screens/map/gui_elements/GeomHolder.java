package com.timgapps.warfare.screens.map.gui_elements;

import java.util.ArrayList;

public class GeomHolder {
    private ArrayList<Geom> geoms;

    public GeomHolder() {
        geoms = new ArrayList<Geom>();
    }

    public void addGeom(Geom geom) {
        geoms.add(geom);
    }

    public Geom getGeom(String name) {
        for (Geom geom : geoms) {
            if (geom.getName().equals(name)) {
                return geom;
            }
        }
        return null;
    }
}

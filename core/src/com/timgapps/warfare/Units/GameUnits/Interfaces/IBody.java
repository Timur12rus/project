package com.timgapps.warfare.Units.GameUnits.Interfaces;

import com.badlogic.gdx.physics.box2d.Body;
import com.timgapps.warfare.Units.GameUnits.GameUnit;

public interface IBody {
    public Body createBody(float x, float y);

}
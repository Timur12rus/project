package com.timgapps.warfare.Units.GameUnits;

import java.io.Serializable;

public class UnitData implements Serializable {
    protected float deltaX, deltaY;           // смещение изображения юнита относительно тела по оси х и у
    protected float barDeltaX, barDeltaY;    // смещение healthBar по оси х

    public UnitData() {
    }

    public float getDeltaX() {
        return deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }

    public float getBarDeltaX() {
        return barDeltaX;
    }

    public float getBarDeltaY() {
        return barDeltaY;
    }
}

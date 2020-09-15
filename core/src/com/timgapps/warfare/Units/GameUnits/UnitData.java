package com.timgapps.warfare.Units.GameUnits;

public class UnitData {
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

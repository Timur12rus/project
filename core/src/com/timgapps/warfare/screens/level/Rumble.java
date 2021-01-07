package com.timgapps.warfare.screens.level;

import com.badlogic.gdx.math.Vector2;

public class Rumble {
    private static float time = 0;
    private static float currentTime = 0;
    private static float power = 0;
    private static float currentPower = 0;
    private static Vector2 pos = new Vector2();

    public static void rumble(float rumblePower, float rumbleLength) {
        power = rumblePower;
        time = rumbleLength;
        currentTime = 0;
        pos.y = 365;

    }

    public static void tick(float delta) {
        if (currentTime <= time) {
            currentTime += delta;
            System.out.println("Current Time = " + currentTime);
        } else {
            time = 0;
            pos.y = 360;
        }
    }

    public static float getRumbleTimeLeft() {
        return time;
    }

    public static Vector2 getPos() {
        return pos;
    }
}

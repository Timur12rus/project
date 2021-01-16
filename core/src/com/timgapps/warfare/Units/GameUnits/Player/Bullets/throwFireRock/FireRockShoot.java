package com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;

import java.util.ArrayList;

public class FireRockShoot {
    private ArrayList<FireRock> fireRocks;
    private LevelScreen levelScreen;
    private float firstPoint, secondPoint, thirdPoint, fourthPoint;
    private final float DELTA_X = 32;

    public FireRockShoot(LevelScreen levelScreen, float x, float y) {
        this.levelScreen = levelScreen;
        Vector2 position = new Vector2(x + DELTA_X, y);
        Vector2 firstPosition = new Vector2(x - DELTA_X, y);
        Vector2 secondPosition = new Vector2(x + 2 * DELTA_X, y - 16);
        Vector2 thirdPosition = new Vector2(x - 2 * DELTA_X, y - 16);
        Vector2 fourthPosition = new Vector2(x + 2 * DELTA_X, y);
        Vector2 fifthPosition = new Vector2(x - 2 * DELTA_X, y);

        new FireRock(levelScreen, new Vector2(-64, 500), firstPosition, 10, 0f);
        new FireRock(levelScreen, new Vector2(-64, 500), secondPosition, 10, 0.3f);
        new FireRock(levelScreen, new Vector2(-64, 500), thirdPosition, 10, 0.6f);
        new FireRock(levelScreen, new Vector2(-64, 500), fourthPosition, 10, 0.9f);
        new FireRock(levelScreen, new Vector2(-64, 500), fifthPosition, 10, 1.2f);

//        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), firstPosition, 10, 0f));
//        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), secondPosition, 10, 0.3f));
//        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), thirdPosition, 10, 0.6f));
//        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), fourthPosition, 10, 0.9f));
//        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), fifthPosition, 10, 1.2f));
    }
}

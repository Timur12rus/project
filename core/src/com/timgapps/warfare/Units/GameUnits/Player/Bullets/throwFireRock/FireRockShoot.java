package com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;

import java.util.ArrayList;

public class FireRockShoot {
    private ArrayList<FireRock> fireRocks;
    private LevelScreen levelScreen;
    private float firstPoint, secondPoint, thirdPoint, fourthPoint;
    private final float DELTA_X = 32;
//    private final float DELTA_X = 48;


    public FireRockShoot(LevelScreen levelScreen, float x, float y) {
        this.levelScreen = levelScreen;
//        Vector2 firstPosition = new Vector2(x, y);
//        Vector2 secondPosition = new Vector2(x, y);
//        Vector2 thirdPosition = new Vector2(x, y);
//        Vector2 fourthPosition = new Vector2(x, y);
//        Vector2 fifthPosition = new Vector2(x, y);

            Vector2 firstPosition = new Vector2(x, y);
            Vector2 secondPosition = new Vector2(x - DELTA_X, y - 16);
            Vector2 thirdPosition = new Vector2(x + DELTA_X, y + 8);
            Vector2 fourthPosition = new Vector2(x - 2 * DELTA_X, y + 8);
            Vector2 fifthPosition = new Vector2(x + 1.5f * DELTA_X, y);


            System.out.println("First Position = " + firstPosition);
            new FireRock(levelScreen, firstPosition, 10, 0f);
            new FireRock(levelScreen, secondPosition, 10, 0.5f);
            new FireRock(levelScreen, thirdPosition, 10, 1f);
            new FireRock(levelScreen, fourthPosition, 10, 1.5f);
            new FireRock(levelScreen, fifthPosition, 10, 2f);
            new FireRock(levelScreen, fourthPosition, 10, 2.5f);

//        new FireRock(levelScreen, firstPosition, 10, 0f);
//        new FireRock(levelScreen, secondPosition, 10, 0.6f);
//        new FireRock(levelScreen, thirdPosition, 10, 1.2f);
//        new FireRock(levelScreen, fourthPosition, 10, 1.8f);
//        new FireRock(levelScreen, fifthPosition, 10, 2.4f);
//        new FireRock(levelScreen, fourthPosition, 10, 3);


//        new FireRock(levelScreen, secondPosition, 10, 3.6f);
//        new FireRock(levelScreen, firstPosition, 10, 4.2f);
//        new FireRock(levelScreen, fourthPosition, 10, 4.2f);
    }
}

package com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;

import java.util.ArrayList;

public class FireRockShoot {
    private ArrayList<FireRock> fireRocks;
    private LevelScreen levelScreen;
    private float firstPoint, secondPoint, thirdPoint, fourthPoint;

    public FireRockShoot(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
        float deltaX;
        firstPoint = levelScreen.getSiegeTower().getX() + levelScreen.getSiegeTower().getWidth() - 64;
        fourthPoint = levelScreen.getBarricade().getX();
        deltaX = (fourthPoint - firstPoint) / 4;          // расстояние между точками, куда нацелен снаряд
        secondPoint = firstPoint + deltaX;
        thirdPoint = secondPoint + deltaX;
        fourthPoint += 128;
        System.out.println("firstPoint = " + firstPoint);
        System.out.println("secondPoint = " + secondPoint);
        System.out.println("thirdPoint = " + thirdPoint);
        System.out.println("fourthPoint = " + fourthPoint);
        boolean isHaveFirstTarget = false;
        boolean isHaveSecondTarget = false;
        boolean isHaveThirdTarget = false;
        boolean isHaveFourthTarget = false;
        fireRocks = new ArrayList<FireRock>();
        Vector2 firstPosition = new Vector2();
        Vector2 secondPosition = new Vector2();
        Vector2 thirdPosition = new Vector2();
        Vector2 fourthPosition = new Vector2();
        // найдем вражеских юнитов по которым будем стрелять
        for (EnemyUnitModel enemy : levelScreen.getArrayEnemies()) {
            if (enemy.getHealth() > 0) {
                // если враг находится на первом промежутке, то запоминаем его позицию
                if ((enemy.getX() < secondPoint && enemy.getX() > firstPoint) && (!isHaveFirstTarget)) {
                    System.out.println("Enemy First POSITION = " + enemy.getX() + ", " + enemy.getY());
                    firstPosition.set(enemy.getX() - 64, enemy.getY() - 60);
                    isHaveFirstTarget = true;
                } else if ((enemy.getX() < thirdPoint && enemy.getX() >= secondPoint) && (!isHaveSecondTarget)) {
                    System.out.println("Enemy Second POSITION = " + enemy.getX() + ", " + enemy.getY());
                    secondPosition.set(enemy.getX() - 64, enemy.getY() - 60);       // позиция второй цели
                    isHaveSecondTarget = true;
                } else if ((enemy.getX() < fourthPoint && enemy.getX() >= thirdPoint) && (!isHaveThirdTarget)) {
                    System.out.println("Enemy Third POSITION = " + enemy.getX() + ", " + enemy.getY());
                    thirdPosition.set(enemy.getX() - 64, enemy.getY() - 60);
                    isHaveThirdTarget = true;
                }
            }
        }
        if (isHaveFirstTarget && !isHaveSecondTarget && !isHaveThirdTarget) {
            secondPosition.set(firstPosition.x + 64, firstPosition.y - 32);
            thirdPosition.set(secondPosition.x + 64, secondPosition.y - 32);
        } else if (!isHaveFirstTarget && isHaveSecondTarget && !isHaveThirdTarget) {
            firstPosition.set(secondPosition.x - 64, secondPosition.y + 32);
            thirdPosition.set(secondPosition.x + 64, secondPosition.y - 32);
        } else if (!isHaveFirstTarget && !isHaveSecondTarget && isHaveThirdTarget) {
            secondPosition.set(thirdPosition.x - 64, thirdPosition.y + 32);
            firstPosition.set(secondPosition.x - 64, secondPosition.y + 32);
        } else if (isHaveFirstTarget && !isHaveSecondTarget && isHaveThirdTarget) {
            secondPosition.set(firstPosition.x + 64, firstPosition.y - 32);
        } else if (isHaveFirstTarget && isHaveSecondTarget && !isHaveThirdTarget) {
            thirdPosition.set(firstPosition.x + 64, firstPosition.y - 32);
        } else if (!isHaveFirstTarget && isHaveSecondTarget && isHaveThirdTarget) {
            firstPosition.set(secondPosition.x - 64, secondPosition.y);
        } else if (!isHaveFirstTarget && !isHaveSecondTarget && !isHaveThirdTarget) {
            firstPosition.set(fourthPoint - 256, 210);
            secondPosition.set(firstPosition.x + 16, 190);
            thirdPosition.set(secondPosition.x + 16, 170);
        }

        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), firstPosition, 10, 0f));
        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), secondPosition, 10, 0.3f));
        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), thirdPosition, 10, 0.6f));
    }
}


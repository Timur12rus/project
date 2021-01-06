package com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.screens.level.LevelScreen;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;

import java.util.ArrayList;

public class FireRockShoot {
    private ArrayList<FireRock> fireRocks;
    private LevelScreen levelScreen;

    public FireRockShoot(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
        boolean isHaveFirstTarget = false;
        boolean isHaveSecondTarget = false;
        boolean isHaveThirdTarget = false;
        fireRocks = new ArrayList<FireRock>();
        Vector2 firstPosition = new Vector2(0, 0);
        Vector2 secondPosition = new Vector2(0, 0);
        Vector2 thirdPosition = new Vector2(0, 0);
        // найдем вражеских юнитов по которым будем стрелять
        for (EnemyUnitModel enemy : levelScreen.getArrayEnemies()) {
            if (enemy.getHealth() > 0) {
//            if (enemy.isBodyActive()) {
                if ((enemy.getX() < 500 && enemy.getX() > 300) && (firstPosition != null)) {
                    System.out.println("Enemy POSITION = " + enemy.getX() + ", " + enemy.getY());
                    if (!isHaveFirstTarget) {
                        firstPosition.set(enemy.getX() - 64, enemy.getY() - 60);
                        isHaveFirstTarget = true;
                    }
                }
                if ((enemy.getX() < 960 && enemy.getX() >= 500) && (secondPosition != null)) {
                    System.out.println("Enemy POSITION = " + enemy.getX() + ", " + enemy.getY());
                    if (!isHaveSecondTarget) {
                        secondPosition.set(enemy.getX() - 64, enemy.getY() - 60);
                        isHaveSecondTarget = true;
                    }
                }
                if ((enemy.getX() < 1300 && enemy.getX() >= 960) && (thirdPosition != null)) {
                    System.out.println("Enemy POSITION = " + enemy.getX() + ", " + enemy.getY());
                    if (!isHaveThirdTarget) {
                        thirdPosition.set(enemy.getX() - 196, enemy.getY() - 60);
                        isHaveThirdTarget = true;
                    }
                }
            }
        }

        if (!isHaveFirstTarget) {        // если первый камень не имеет цель
            if (isHaveSecondTarget) {       // если второй камень имеет цель
                firstPosition.set(secondPosition.x - 128, secondPosition.y + 24);
                if (!isHaveThirdTarget) {
                    thirdPosition.set(secondPosition.x + 128, secondPosition.y - 24);
                }
            } else {                        // если второй камень не имеет цель
                if (isHaveThirdTarget) {    // если третий камень имеет цель
                    firstPosition.set(thirdPosition.x - 128, thirdPosition.y + 24);
                    secondPosition.set(thirdPosition.x + 128, thirdPosition.y - 24);
                } else {                // если третий камень не имеет цель
//                    thirdPosition.set(levelScreen.getBarricade().getX() + 270, 210);
                    thirdPosition.set(960, 180);
//                    thirdPosition.set(1100, 210);
                    firstPosition.set(thirdPosition.x + 64, thirdPosition.y - 48);
                    secondPosition.set(thirdPosition.x + 64, thirdPosition.y - 96);
                }
            }
        } else {            // если первый камень имеет цель
            if (!isHaveSecondTarget) {          // если второй камень не имеет цель
                secondPosition.set(firstPosition.x + 128, secondPosition.y + 24);
            }
            if (!isHaveThirdTarget) {
                thirdPosition.set(secondPosition.x + 128, thirdPosition.y - 24);
            }
        }


//        if (firstPosition.x == 0 && firstPosition.y == 0) {
//            firstPosition.set(500, 130);
//        }
//
//        if (secondPosition.x == 0 && secondPosition.y == 0) {
//            secondPosition.set(1100, 180);
//        }
//
//        if (thirdPosition.x == 0 && thirdPosition.y == 0) {
//            thirdPosition.set(1100, 210);
//        }

//        System.out.println("FIRST POSITION = " + firstPosition);
//        System.out.println("SECOND POSITION = " + secondPosition);
//        System.out.println("THIRD POSITION = " + thirdPosition);

        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), firstPosition, 10));
        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), secondPosition, 10));
        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), thirdPosition, 10));

        System.out.println("FirstEndPosition = " + firstPosition);
//        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), new Vector2(640, 160 ), 10));
//        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), new Vector2(320, 140 ), 10));
//        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), new Vector2(960, 180 ), 10));
//        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), secondPosition, 10));
//        fireRocks.add(new FireRock(levelScreen, new Vector2(-64, 500), thirdPosition, 10));

//        fireRocks.add(new
//
//                FireRock(levelScreen, new Vector2(100, 1000), firstPosition, 10));
//        fireRocks.add(new
//
//                FireRock(levelScreen, new Vector2(-160, 1600), secondPosition, 10));
//        fireRocks.add(new
//
//                FireRock(levelScreen, new Vector2(240, 1100), thirdPosition, 10));
    }




    // метод для запуска огненног камня, обращается к огненным камням из массива
    public void throwFireRock(int i) {
        if (fireRocks != null && fireRocks.size() > 0)
            fireRocks.get(i).start();
    }
}

package com.timgapps.warfare.Units.GameUnits.Player.Bullets.throwFireRock;

import com.badlogic.gdx.math.Vector2;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;

import java.util.ArrayList;

public class FireRockShoot {
    private ArrayList<FireRock> fireRocks;
    private Level level;

    public FireRockShoot(Level level) {
        this.level = level;
        fireRocks = new ArrayList<FireRock>();
        Vector2 firstPosition = new Vector2();
        Vector2 secondPosition = new Vector2();
        Vector2 thirdPosition = new Vector2();
        // найдем вражеских юнитов по которым будем стрелять
        for (EnemyUnitModel enemy : level.getArrayEnemies()) {
            if (enemy.getHealth() > 0) {
//            if (enemy.isBodyActive()) {
                if ((enemy.getX() < 500 && enemy.getX() > 300) && (firstPosition != null)) {
                    System.out.println("Enemy POSITION = " + enemy.getX() + ", " + enemy.getY());
                    firstPosition.set(enemy.getX(), enemy.getY() - 60);
                }
                if ((enemy.getX() < 800 && enemy.getX() >= 500) && (secondPosition != null)) {
                    System.out.println("Enemy POSITION = " + enemy.getX() + ", " + enemy.getY());
                    secondPosition.set(enemy.getX(), enemy.getY() - 60);
                }
                if ((enemy.getX() < 1300 && enemy.getX() >= 800) && (thirdPosition != null)) {
                    System.out.println("Enemy POSITION = " + enemy.getX() + ", " + enemy.getY());
                    thirdPosition.set(enemy.getX(), enemy.getY() - 60);
                }
            }
        }
        if (firstPosition.x == 0 && firstPosition.y == 0) {
            firstPosition.set(500, 130);
//            firstPosition.set(500, 220);
        }
        if (secondPosition.x == 0 && secondPosition.y == 0) {
            secondPosition.set(800, 180);
//            secondPosition.set(800, 230);
        }
        if (thirdPosition.x == 0 && thirdPosition.y == 0) {
            thirdPosition.set(1100, 210);
//            thirdPosition.set(1100, 270);
        }
        System.out.println("FIRST POSITION = " + firstPosition);
        System.out.println("SECOND POSITION = " + secondPosition);
        System.out.println("THIRD POSITION = " + thirdPosition);
        fireRocks.add(new FireRock(level, new Vector2(100, 1000), firstPosition, 10));
        fireRocks.add(new FireRock(level, new Vector2(-160, 1600), secondPosition, 10));
        fireRocks.add(new FireRock(level, new Vector2(240, 1100), thirdPosition, 10));
    }

    // метод для запуска огненног камня, обращается к огненным камням из массива
    public void throwFireRock(int i) {
        if (fireRocks != null && fireRocks.size() > 0)
            fireRocks.get(i).start();
    }
}

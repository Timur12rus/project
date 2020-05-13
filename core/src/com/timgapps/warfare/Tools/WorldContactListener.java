package com.timgapps.warfare.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.timgapps.warfare.Units.GameUnits.Barricade;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnits.GameUnit;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Bullet;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Stone;
import com.timgapps.warfare.Units.GameUnits.Player.PlayerUnit;
import com.timgapps.warfare.Units.GameUnits.Player.SiegeTower;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case GameUnit.PLAYER_BIT | GameUnit.ENEMY_BIT:
                checkPlayerToEnemyCollision(fixA, fixB);
                break;
            case GameUnit.BULLET_BIT | GameUnit.ENEMY_BIT:
                checkPlayerBulletToEnemy(fixA, fixB);
                break;
            case GameUnit.STONE_BIT | GameUnit.ENEMY_BIT:
                checkCollisionStoneToEnemy(fixA, fixB);
                break;
            case GameUnit.PLAYER_BIT | GameUnit.BARRICADE_BIT:
                checkCollisionPlayerToBarricade(fixA, fixB);
                break;
            case GameUnit.TOWER_BIT | GameUnit.ENEMY_BIT:
                checkCollisionTowerToEnemy(fixA, fixB);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    /**
     * Метод для проверки столкновения БАШНИ С ВРАЖЕСКИМ ЮНИТОМ
     **/
    private void checkCollisionTowerToEnemy(Fixture fixA, Fixture fixB) {
        if (fixA.getFilterData().categoryBits == GameUnit.TOWER_BIT) {

//            Object towerData = fixA.getUserData();
            Object enemyUserData = fixB.getUserData();

            ((EnemyUnit) enemyUserData).attackTower();

        } else {
//            Object towerData = fixB.getUserData();
            Object enemyUserData = fixA.getUserData();

            ((EnemyUnit) enemyUserData).attackTower();
        }
    }


    /**
     * Метод для проверки столкновения ИГРОВОГО ЮНИТА С ВРАЖЕСКИМ ЮНИТОМ
     **/
    private void checkPlayerToEnemyCollision(Fixture fixA, Fixture fixB) {
        if (fixA.getFilterData().categoryBits == GameUnit.PLAYER_BIT) {

            Object userData = fixA.getUserData();
            Object enemyUserData = fixB.getUserData();
            if (userData instanceof PlayerUnit) {
                if (((PlayerUnit) userData).getCurrentState() != GameUnit.State.ATTACK) {
                    ((PlayerUnit) userData).setTargetEnemy((EnemyUnit) enemyUserData);
                    ((PlayerUnit) userData).attack();

                } else return;
            }
            if (enemyUserData instanceof EnemyUnit) {
//                System.out.println("AAAAAAAAAAAAAAAAAAAAA");
//                System.out.println("GETTARGETPLAYER = " + ((EnemyUnit) enemyUserData).getTargetPlayer());
                if (((EnemyUnit) enemyUserData).getCurrentState() != GameUnit.State.ATTACK) {

                    if (((EnemyUnit) enemyUserData).getTargetPlayer() == null) {
                        // назначим вражескому юниту цель-игрока, которого он коснулся
                        ((EnemyUnit) enemyUserData).setTargetPlayer((PlayerUnit) userData);
//                        System.out.println("GETTARGETPLAYER = " + ((EnemyUnit) enemyUserData).getTargetPlayer());

                        // установим для вражеского юнита состояние "атака"
                        ((EnemyUnit) enemyUserData).attack();
                    }

//                    ((EnemyUnit) enemyUserData).setTargetPlayer((PlayerUnit) userData);
//                    ((EnemyUnit) enemyUserData).attack();
                } else return;
            }
        } else {
            Object userData = fixB.getUserData();
            Object enemyUserData = fixA.getUserData();
            if (userData instanceof PlayerUnit) {
                if (((PlayerUnit) userData).getCurrentState() != GameUnit.State.ATTACK) {
                    ((PlayerUnit) userData).setTargetEnemy((EnemyUnit) enemyUserData);
                    ((PlayerUnit) userData).attack();

                } else return;
            }
//                    Object enemyUserData = fixA.getUserData();
            if (enemyUserData instanceof EnemyUnit) {
                System.out.println("BBBBBBB");
                if (((EnemyUnit) enemyUserData).getCurrentState() != GameUnit.State.ATTACK) {
                    System.out.println("GETTARGETPLAYER = " + ((EnemyUnit) enemyUserData).getTargetPlayer());
                    if (((EnemyUnit) enemyUserData).getTargetPlayer() == null) {
                        ((EnemyUnit) enemyUserData).setTargetPlayer((PlayerUnit) userData);
                        System.out.println("GETTARGETPLAYER = " + ((EnemyUnit) enemyUserData).getTargetPlayer());
                        ((EnemyUnit) enemyUserData).attack();
                    }
//                    ((EnemyUnit) enemyUserData).attack();
                } else return;
            }
        }
    }

    /**
     * Метод для проверки столкновения ИГРОВОГО ЮНИТА С БАРРИКАДОЙ
     **/
    private void checkCollisionPlayerToBarricade(Fixture fixA, Fixture fixB) {
        if (fixA.getFilterData().categoryBits == GameUnit.BARRICADE_BIT) {

//            Object barricadeData = fixA.getUserData();
            Object playerData = fixB.getUserData();

//            if (((PlayerUnit) playerData).getCurrentState() != GameUnit.State.ATTACK) {
            ((PlayerUnit) playerData).attackBarricade();
//                ((PlayerUnit) playerData).attackBarricade((Barricade) barricadeData);
//            }
        } else {
            Object playerData = fixA.getUserData();
//            Object barricadeData = fixB.getUserData();

//            if (((PlayerUnit) playerData).getCurrentState() != GameUnit.State.ATTACK) {
            ((PlayerUnit) playerData).attackBarricade();
//                ((PlayerUnit) playerData).attackBarricade((Barricade) barricadeData);
//            }
        }
    }

    /**
     * Метод для проверки столкновения СНАРЯДА ИГРОВОГО ЮНИТА С ВРАЖЕСКИМ ЮНИТОМ
     **/
    private void checkPlayerBulletToEnemy(Fixture fixA, Fixture fixB) {
        if (fixA.getFilterData().categoryBits == GameUnit.BULLET_BIT) {
            Object bulletData = fixA.getUserData();
            Object enemyUserData = fixB.getUserData();
            if (bulletData instanceof Bullet) {
                ((Bullet) bulletData).inflictDamage((EnemyUnit) enemyUserData);

            }
        } else {
            Object bulletData = fixB.getUserData();
            Object enemyUserData = fixA.getUserData();
            if (bulletData instanceof Bullet) {
                ((Bullet) bulletData).inflictDamage((EnemyUnit) enemyUserData);
            }
        }
    }

    /**
     * Метод для проверки столкновения КАМНЯ ИГРОВОГО ЮНИТА С ВРАЖЕСКИМ ЮНИТОМ
     **/
    private void checkCollisionStoneToEnemy(Fixture fixA, Fixture fixB) {
        if (fixA.getFilterData().categoryBits == GameUnit.STONE_BIT) {

            Object stoneData = fixA.getUserData();
            Object enemyUserData = fixB.getUserData();

            if (((EnemyUnit) enemyUserData).getCurrentState() != GameUnit.State.ATTACK) {
                ((EnemyUnit) enemyUserData).setAttackStone((Stone) stoneData);
            }
        } else {
            Object stoneData = fixB.getUserData();
            Object enemyUserData = fixA.getUserData();

            if (((EnemyUnit) enemyUserData).getCurrentState() != GameUnit.State.ATTACK) {
                ((EnemyUnit) enemyUserData).setAttackStone((Stone) stoneData);
            }
        }
    }


}
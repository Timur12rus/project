package com.timgapps.warfare.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnits.GameUnit;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Bullet;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Stone;
import com.timgapps.warfare.Units.GameUnits.Player.PlayerUnit;

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
     * Метод для проверки столкновения ИГРОВОГО ЮНИТА С ВРАЖЕСКИМ ЮНИТОМ
     **/
    private void checkPlayerToEnemyCollision(Fixture fixA, Fixture fixB) {
        if (fixA.getFilterData().categoryBits == GameUnit.PLAYER_BIT) {

            Object userData = fixA.getUserData();
            Object enemyUserData = fixB.getUserData();
            if (userData instanceof PlayerUnit) {
                if (((PlayerUnit) userData).getCurrentState() != GameUnit.State.ATTACK) {
                    ((PlayerUnit) userData).attack();
                    ((PlayerUnit) userData).setTargetEnemy((EnemyUnit) enemyUserData);
                } else return;
            }
            if (enemyUserData instanceof EnemyUnit) {
                if (((EnemyUnit) enemyUserData).getCurrentState() != GameUnit.State.ATTACK) {
                    ((EnemyUnit) enemyUserData).attack();
                    ((EnemyUnit) enemyUserData).setTargetPlayer((PlayerUnit) userData);
                } else return;
            }
        } else {
            Object userData = fixB.getUserData();
            Object enemyUserData = fixA.getUserData();
            if (userData instanceof PlayerUnit) {
                if (((PlayerUnit) userData).getCurrentState() != GameUnit.State.ATTACK) {
                    ((PlayerUnit) userData).attack();
                    ((PlayerUnit) userData).setTargetEnemy((EnemyUnit) enemyUserData);
                } else return;
            }
//                    Object enemyUserData = fixA.getUserData();
            if (enemyUserData instanceof EnemyUnit) {
                if (((EnemyUnit) enemyUserData).getCurrentState() != GameUnit.State.ATTACK) {
                    ((EnemyUnit) enemyUserData).attack();
                    ((EnemyUnit) enemyUserData).setTargetPlayer((PlayerUnit) userData);
                } else return;
            }
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
            Object enemyUserData = fixA.getUserData();
            Object bulletData = fixB.getUserData();
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
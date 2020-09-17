package com.timgapps.warfare.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnit;
import com.timgapps.warfare.Units.GameUnits.Enemy.EnemyUnitModel;
import com.timgapps.warfare.Units.GameUnits.GameUnitView;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Bullet;
import com.timgapps.warfare.Units.GameUnits.Player.Bullets.Stone;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnit;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitController;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitModel;

import static com.timgapps.warfare.Units.GameUnits.GameUnitModel.BARRICADE_BIT;
import static com.timgapps.warfare.Units.GameUnits.GameUnitModel.BULLET_BIT;
import static com.timgapps.warfare.Units.GameUnits.GameUnitModel.ENEMY_BIT;
import static com.timgapps.warfare.Units.GameUnits.GameUnitModel.PLAYER_BIT;
import static com.timgapps.warfare.Units.GameUnits.GameUnitModel.STONE_BIT;
import static com.timgapps.warfare.Units.GameUnits.GameUnitModel.TOWER_BIT;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case BULLET_BIT | ENEMY_BIT:
                checkPlayerBulletToEnemy(fixA, fixB);
                break;
            case STONE_BIT | ENEMY_BIT:
                checkCollisionStoneToEnemy(fixA, fixB);
                break;
            case PLAYER_BIT | BARRICADE_BIT:
                checkCollisionPlayerToBarricade(fixA, fixB);
                break;
            case TOWER_BIT | ENEMY_BIT:
                checkCollisionTowerToEnemy(fixA, fixB);
                break;
            case PLAYER_BIT | ENEMY_BIT:
                checkPlayerToEnemyCollision(fixA, fixB);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case PLAYER_BIT | ENEMY_BIT:
//                endCollisionPlayerToEnemy(fixA, fixB);
//                checkPlayerToEnemyCollision(fixA, fixB);
//                checkPlayerToEnemyCollision(fixA, fixB);
                break;
        }
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
        if (fixA.getFilterData().categoryBits == TOWER_BIT) {
            Object enemyUserData = fixB.getUserData();
            ((EnemyUnit) enemyUserData).attackTower();
        } else {
            Object enemyUserData = fixA.getUserData();
            ((EnemyUnit) enemyUserData).attackTower();
        }
    }


    /**
     * Метод для проверки столкновения ИГРОВОГО ЮНИТА С ВРАЖЕСКИМ ЮНИТОМ
     **/
    private void checkPlayerToEnemyCollision(Fixture fixA, Fixture fixB) {
        if (fixA.getFilterData().categoryBits == PLAYER_BIT) {
            Object userData = fixA.getUserData();
            Object enemyUserData = fixB.getUserData();

            System.out.println("Enemy To Player collision!");

            // если игрок не имеет "врвга-цель", то зададим для него "врага-цель" и установим флаг "атакует" (isAttack = true)

            ((PlayerUnitModel) userData).setIsTouchedEnemy(true);
            ((PlayerUnitModel) userData).setTargetEnemy((EnemyUnitModel) enemyUserData);

//            ((PlayerUnit) userData).setTargetEnemy((EnemyUnit) enemyUserData);
//            ((PlayerUnit) userData).attack();

            // если вражеский юнит не имеет "игрока-цель", то зададим для него "игрока-цель" и установим флаг "атакует" (isAttack = true)
//            ((EnemyUnit) enemyUserData).setTargetPlayer((PlayerUnit) userData);
//            ((EnemyUnit) enemyUserData).attack();
        } else {
            Object userData = fixB.getUserData();
            Object enemyUserData = fixA.getUserData();

            System.out.println("Enemy To Player collision!");

            // если игрок не имеет "врвга-цель", то зададим для него "врага-цель" и установим флаг "атакует" (isAttack = true)

            ((PlayerUnitModel) userData).setIsTouchedEnemy(true);
            ((PlayerUnitModel) userData).setTargetEnemy((EnemyUnitModel) enemyUserData);

//            ((PlayerUnit) userData).setTargetEnemy((EnemyUnit) enemyUserData);
//            ((PlayerUnit) userData).attack();

            // если вражеский юнит не имеет "игрока-цель", то зададим для него "игрока-цель" и установим флаг "атакует" (isAttack = true)
//            ((EnemyUnit) enemyUserData).setTargetPlayer((PlayerUnit) userData);
//            ((EnemyUnit) enemyUserData).attack();
        }
    }

    /**
     * Метод для проверки столкновения ИГРОВОГО ЮНИТА С БАРРИКАДОЙ
     **/
    private void checkCollisionPlayerToBarricade(Fixture fixA, Fixture fixB) {
        if (fixA.getFilterData().categoryBits == BARRICADE_BIT) {
            Object playerData = fixB.getUserData();
            ((PlayerUnit) playerData).attackBarricade();
        } else {
            Object playerData = fixA.getUserData();
            ((PlayerUnit) playerData).attackBarricade();
        }
    }

    /**
     * Метод для проверки столкновения СНАРЯДА ИГРОВОГО ЮНИТА С ВРАЖЕСКИМ ЮНИТОМ
     **/
    private void checkPlayerBulletToEnemy(Fixture fixA, Fixture fixB) {
        if (fixA.getFilterData().categoryBits == BULLET_BIT) {
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
     * Метод для проверки столкновения КАМНЯ С ВРАЖЕСКИМ ЮНИТОМ
     **/
    private void checkCollisionStoneToEnemy(Fixture fixA, Fixture fixB) {
        if (fixA.getFilterData().categoryBits == STONE_BIT) {

            Object stoneData = fixA.getUserData();
            Object enemyUserData = fixB.getUserData();

            if (((EnemyUnit) enemyUserData).getCurrentState() != GameUnitView.State.ATTACK) {
                ((EnemyUnit) enemyUserData).setAttackStone((Stone) stoneData);
            }
        } else {
            Object stoneData = fixB.getUserData();
            Object enemyUserData = fixA.getUserData();

            if (((EnemyUnit) enemyUserData).getCurrentState() != GameUnitView.State.ATTACK) {
                ((EnemyUnit) enemyUserData).setAttackStone((Stone) stoneData);
            }
        }
    }

    private void endCollisionPlayerToEnemy(Fixture fixA, Fixture fixB) {
        if (fixA.getFilterData().categoryBits == PLAYER_BIT) {

            Object userData = fixA.getUserData();
            Object enemyUserData = fixB.getUserData();

            System.out.println("Enemy To Player endCollision!");

//            ((PlayerUnit) userData).setTargetEnemy((EnemyUnit) enemyUserData);
            ((PlayerUnit) userData).resetTarget();

        } else {
            Object userData = fixB.getUserData();
            Object enemyUserData = fixA.getUserData();

            System.out.println("Enemy To Player endCollision!");
//
//            // если игрок не имеет "врвга-цель", то зададим для него "врага-цель" и установим флаг "атакует" (isAttack = true)
//            ((PlayerUnit) userData).setTargetEnemy((EnemyUnit) enemyUserData);
//            ((PlayerUnit) userData).attack();

            ((PlayerUnit) userData).resetTarget();
        }
    }
}
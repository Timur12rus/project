package com.timgapps.warfare.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.timgapps.warfare.Units.Enemy.Zombie;
import com.timgapps.warfare.Units.GameUnit;
import com.timgapps.warfare.Units.Player.Gnome;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case GameUnit.PLAYER_BIT | GameUnit.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == GameUnit.PLAYER_BIT) {

                    Object userData = fixA.getUserData();
                    Object enemyUserData = fixB.getUserData();
                    if (userData instanceof Gnome) {
                        if (((Gnome) userData).getCurrentState() != GameUnit.State.ATTACK) {
                            ((Gnome) userData).attack();
                            System.out.println("Touched");
                            System.out.println(userData.toString());
                        } else return;
                    }
                } else {
                    Object userData = fixB.getUserData();
                    System.out.println(userData.toString());
                    if (userData instanceof Gnome) {
                        if (((Gnome) userData).getCurrentState() != GameUnit.State.ATTACK) {
                            ((Gnome) userData).attack();
                            System.out.println("Touched");
                            System.out.println(userData.toString());
                        } else return;
                    }
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch (cDef) {
            case GameUnit.PLAYER_BIT | GameUnit.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == GameUnit.PLAYER_BIT) {

                    Object userData = fixA.getUserData();
                    if (userData instanceof Gnome) {
                        if (((Gnome) userData).getCurrentState() != GameUnit.State.RUN) {
                            ((Gnome) userData).setCurrentState(GameUnit.State.RUN);
                            ((Gnome) userData).resetTarget();
                        } else return;
                    }
                } else {
                    Object userData = fixB.getUserData();
                    System.out.println(userData.toString());
                    if (userData instanceof Gnome) {
                        if (((Gnome) userData).getCurrentState() != GameUnit.State.RUN) {
                            ((Gnome) userData).setCurrentState(GameUnit.State.RUN);
                            ((Gnome) userData).resetTarget();

                        } else return;
                    }
                }
                break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
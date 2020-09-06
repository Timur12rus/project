package com.timgapps.warfare.Level.LevelMap.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class IconAction {
    private Actor actor;
    private SequenceAction sizeAction;
    private boolean isEndAction;

    public IconAction(Actor actor, Group group) {
        this.actor = actor;
        group.addActor(actor);
//        Action checkEndOfAction = new Action() {
//            @Override
//            public boolean act(float delta) {
//                isEndAction = true;
//                return true;
//            }
//        };
        sizeAction = new SequenceAction(
                Actions.fadeIn(0),
                Actions.sizeBy(8, 8, 0.2f),
                Actions.sizeBy(-8, -8, 0.2f),
                Actions.fadeOut(0.5f)
//                ,
//                checkEndOfAction
        );
    }

    public void setActorPosition(float x, float y) {
        actor.setX(x);
        actor.setY(y);
    }

    public void start() {
        actor.addAction(sizeAction);
    }
}

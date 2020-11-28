package com.timgapps.warfare.screens.reward_for_stars;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Warfare;

public class IconAnimation {
    private Image image;
    private String regionName;
    private SequenceAction sizeAction, moveAction;
    private ParallelAction action;

    public IconAnimation(StageGame stageGame, String regionName, float x, float y) {
        image = new Image(Warfare.atlas.findRegion(regionName));
        image.setPosition(x, y);
        stageGame.addChild(image);
        image.setVisible(false);

        MoveToAction ma = new MoveToAction();
        ma.setX(x - 4);
        ma.setY(y - 4);
        ma.setDuration(0.1f);

        moveAction = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(x - 4, y - 4, 0.2f),
                Actions.moveTo(x, y, 0.2f)
        );

        sizeAction = new SequenceAction(Actions.sizeBy(8, 8, 0.2f),
                Actions.sizeBy(-8, -8, 0.2f),
                Actions.fadeOut(0.5f)
        );

        action = new ParallelAction(moveAction, sizeAction);
    }

    public void start() {
        image.setVisible(true);
        image.addAction(action);
//        image.addAction(sizeAction);
    }
}

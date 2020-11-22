package com.timgapps.warfare.Level.GUI.Screens.reward_for_stars.gui_elements;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Warfare;

public class StarsAction {
    private Image starOne;
    private Image starTwo;
    private Image starThree;
    private Image starFour;
    private Image starFive;
    private StageGame stageGame;
    private Vector2 position;

    public StarsAction(StageGame stageGame, Vector2 position) {
        this.stageGame = stageGame;
        this.position = position;
        starOne = new Image(Warfare.atlas.findRegion("star"));
        starTwo = new Image(Warfare.atlas.findRegion("star"));
        starThree = new Image(Warfare.atlas.findRegion("star"));
        starFour = new Image(Warfare.atlas.findRegion("star"));
        starFive = new Image(Warfare.atlas.findRegion("star"));

        starOne.setPosition(position.x - 32, position.y);
        starTwo.setPosition(position.x + 32, position.y);
        starThree.setPosition(position.x, position.y - 64);
        starFour.setPosition(position.x - 32, position.y);
        starFive.setPosition(position.x + 32, position.y);


        stageGame.addChild(starOne);
        stageGame.addChild(starTwo);
        stageGame.addChild(starThree);
        stageGame.addChild(starFour);
        stageGame.addChild(starFive);

//        Action rotateAction  = Actions.forever((Actions.rotateBy(90, 3)));

        ParallelAction MoveActionStarOne = new ParallelAction(
                Actions.rotateTo(180, 1.4f),
                Actions.fadeIn(1.4f),
                Actions.sizeTo(128, 128, 2),
                Actions.moveTo(-130, 480, 1.4f, Interpolation.pow3In)
        );

        ParallelAction MoveActionStarTwo = new ParallelAction(
                Actions.rotateTo(180, 1.4f),
                Actions.fadeIn(1.4f),
                Actions.sizeTo(128, 128, 2),
                Actions.moveTo(1300, 900, 1.4f, Interpolation.pow3In)
        );

        ParallelAction MoveActionStarThree = new ParallelAction(
                Actions.rotateTo(180, 1.4f),
                Actions.fadeIn(1.4f),
                Actions.sizeTo(128, 128, 2),
                Actions.moveTo(1000, -100, 1.4f, Interpolation.pow3In)
        );

        ParallelAction MoveActionStarFour = new ParallelAction(
                Actions.rotateTo(180, 1.4f),
                Actions.fadeIn(1.4f),
                Actions.sizeTo(128, 128, 2),
                Actions.moveTo(200, 1100, 1.4f, Interpolation.pow3In)
        );

        ParallelAction MoveActionStarFive = new ParallelAction(
                Actions.rotateTo(180, 1.4f),
                Actions.fadeIn(1.4f),
                Actions.sizeTo(128, 128, 2),
                Actions.moveTo(1200, 1200, 1.4f, Interpolation.pow3In)
        );


        // Действия для звезд
        SequenceAction starOneAction = new SequenceAction(
                Actions.fadeOut(0),
                Actions.sizeTo(4, 4),
                Actions.delay(2f),
                MoveActionStarOne
        );

        SequenceAction starTwoAction = new SequenceAction(
                Actions.fadeOut(0),
                Actions.sizeTo(4, 4),
                Actions.delay(2.5f),
//                Actions.delay(3f),
                MoveActionStarTwo
        );

        SequenceAction starThreeAction = new SequenceAction(
                Actions.fadeOut(0),
                Actions.sizeTo(4, 4),
                Actions.delay(3f),
                MoveActionStarThree
        );

        SequenceAction starFourAction = new SequenceAction(
                Actions.fadeOut(0),
                Actions.sizeTo(4, 4),
                Actions.delay(3.5f),
                MoveActionStarFour
        );
        SequenceAction starFiveAction = new SequenceAction(
                Actions.fadeOut(0),
                Actions.sizeTo(4, 4),
                Actions.delay(4f),
                MoveActionStarFive
        );
        starOne.addAction(starOneAction);
        starTwo.addAction(starTwoAction);
        starThree.addAction(starThreeAction);
        starFour.addAction(starFourAction);
        starFive.addAction(starFiveAction);
    }

    public void clear() {
        starOne.clearActions();
        starTwo.clearActions();
        starThree.clearActions();
        starFour.clearActions();
        starFive.clearActions();

        starOne.remove();
        starTwo.remove();
        starThree.remove();
        starFour.remove();
        starFive.remove();
    }
}

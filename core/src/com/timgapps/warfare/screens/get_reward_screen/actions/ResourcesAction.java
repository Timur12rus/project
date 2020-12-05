package com.timgapps.warfare.screens.get_reward_screen.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.reward_for_stars.GiftAnimation;
import com.timgapps.warfare.screens.reward_for_stars.IconAnimation;

public class ResourcesAction extends Group {
    private StageGame stageGame;
    private Image resourceOne, resourceTwo, resourceThree, resourceFour, resourceFive;
    private Vector2 startPosition;
    private Vector2 endPosition;
    private boolean isEnd;

    public ResourcesAction(StageGame stageGame, Vector2 startPosition) {
        this.stageGame = stageGame;
        this.startPosition = new Vector2(startPosition.x - 64, startPosition.y + 64);
        this.endPosition = new Vector2(32, stageGame.getHeight() / 3);      // позиция icon
        resourceOne = new Image(Warfare.atlas.findRegion("food_icon"));
        resourceTwo = new Image(Warfare.atlas.findRegion("food_icon"));
        resourceThree = new Image(Warfare.atlas.findRegion("food_icon"));
        resourceFour = new Image(Warfare.atlas.findRegion("food_icon"));
        resourceFive = new Image(Warfare.atlas.findRegion("food_icon"));
        resourceOne.setPosition(this.startPosition.x, this.startPosition.y);
        resourceTwo.setPosition(this.startPosition.x, this.startPosition.y);
        resourceThree.setPosition(this.startPosition.x, this.startPosition.y);
        resourceFour.setPosition(this.startPosition.x, this.startPosition.y);
        resourceFive.setPosition(this.startPosition.x, this.startPosition.y);
        addActor(resourceOne);
        addActor(resourceTwo);
        addActor(resourceThree);
        addActor(resourceFour);
        addActor(resourceFive);

        stageGame.addChild(this);
    }

    public void startAnimation() {
//        new MoveResourcesAction(resourceOne, -32, 104);
//        new MoveResourcesAction(resourceTwo, 0, 84);
//        new MoveResourcesAction(resourceThree, 24, 104);
//        new MoveResourcesAction(resourceFour, -8, 114);
//        new MoveResourcesAction(resourceFive, 8, 148);

        new MoveResourcesAction(resourceOne, -96, 104);
        new MoveResourcesAction(resourceTwo, -64, 84);
        new MoveResourcesAction(resourceThree, -40, 104);
        new MoveResourcesAction(resourceFour, -74, 114);
        new MoveResourcesAction(resourceFive, -40, 148);
    }

    class MoveResourcesAction {
        public MoveResourcesAction(Image image, float deltaX, float deltaY) {
            Action isEndAnimation = new Action() {
                @Override
                public boolean act(float delta) {
                    isEnd = true;
                    startIconAnimation();
                    removeResources();
                    return true;
                }
            };
            SequenceAction moveResourcesAction = new SequenceAction(
                    Actions.fadeOut(0),
                    Actions.delay(3.2f),
                    Actions.fadeIn(0),
                    Actions.moveTo(startPosition.x + deltaX, startPosition.y + deltaY, 0.7f, Interpolation.pow3Out),
                    Actions.moveTo(endPosition.x, endPosition.y, 0.8f, Interpolation.pow3In)
            );
            moveResourcesAction.addAction(isEndAnimation);
            image.addAction(moveResourcesAction);
        }
    }

    private void startIconAnimation() {
        final IconAnimation iconAnimation = new IconAnimation(stageGame,
                "teamButton", 32, stageGame.getHeight() / 3);
        iconAnimation.start();
    }

    private void removeResources() {
        resourceOne.clearActions();
        resourceTwo.clearActions();
        resourceThree.clearActions();
        resourceFour.clearActions();
        resourceFive.clearActions();
        removeActor(resourceOne);
        removeActor(resourceTwo);
        removeActor(resourceThree);
        removeActor(resourceFour);
        removeActor(resourceFive);
        clearActions();
    }
}

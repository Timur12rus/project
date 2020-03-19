package com.timgapps.warfare.Level.LevelScreens;


import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Warfare;

public class Stars extends Group {
    private Image starOne;
    private Image starTwo;
    private Image starThree;

    private Image starOneInactive;
    private Image starTwoInactive;
    private Image starThreeInactive;

    private boolean firstStarActionIsEnd = false;
    private boolean secondStarActionIsEnd = false;
    private boolean thirdStarActionIsEnd = false;


    public Stars() {
        starOne = new Image(Warfare.atlas.findRegion("star"));
        starTwo = new Image(Warfare.atlas.findRegion("star"));
        starThree = new Image(Warfare.atlas.findRegion("star"));

        starOneInactive = new Image(Warfare.atlas.findRegion("starInactive"));
        starTwoInactive = new Image(Warfare.atlas.findRegion("starInactive"));
        starThreeInactive = new Image(Warfare.atlas.findRegion("starInactive"));

        starOne.setPosition(-500, 300);
        starTwo.setPosition(0, 500);
        starThree.setPosition(500, 300);

        starOneInactive.setPosition(-2, 2);
        starTwoInactive.setPosition(starOneInactive.getWidth() + 8 - 2, 18);
        starThreeInactive.setPosition(starOneInactive.getWidth() + 8 - 2 + starTwoInactive.getWidth() + 8 - 2, 2);

        starOne.setVisible(false);
        starTwo.setVisible(false);
        starThree.setVisible(false);

        starOneInactive.setVisible(false);
        starTwoInactive.setVisible(false);
        starThreeInactive.setVisible(false);

        addActor(starOneInactive);
        addActor(starTwoInactive);
        addActor(starThreeInactive);

        addActor(starOne);
        addActor(starTwo);
        addActor(starThree);

        startStarsActions();

        setWidth(starOne.getWidth() * 3 + 12);
        setHeight(starOne.getHeight() + 16);
    }

    public void startStarsActions() {

        starOneInactive.setVisible(true);
        starTwoInactive.setVisible(true);
        starThreeInactive.setVisible(true);

        starOne.setVisible(true);
//        starTwo.setVisible(true);
//        starThree.setVisible(true);

        Action checkEndOfActionFirstStar = new Action() {
            @Override
            public boolean act(float delta) {
                firstStarActionIsEnd = true;
                return true;
            }
        };

        SequenceAction maStarFirst = new SequenceAction(
                Actions.moveTo(0, 0, 0.5f), Actions.moveTo(-2, 2, 0.1f), checkEndOfActionFirstStar);
        starOne.addAction(maStarFirst);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (firstStarActionIsEnd) {
            Action checkEndOfActionSecondStar = new Action() {
                @Override
                public boolean act(float delta) {
                    secondStarActionIsEnd = true;
                    return true;
                }
            };
            firstStarActionIsEnd = false;
            starTwo.setVisible(true);
            SequenceAction maStarSecond = new SequenceAction(
                    Actions.moveTo(0 + starOne.getWidth() + 8, 16, 0.5f),
                    Actions.moveTo(starOne.getWidth() + 8 - 2, 18, 0.1f), checkEndOfActionSecondStar);
            starTwo.addAction(maStarSecond);
        }

        if (secondStarActionIsEnd) {
            Action checkEndOfActionThirdStar = new Action() {
                @Override
                public boolean act(float delta) {
                    thirdStarActionIsEnd = true;
                    return true;
                }
            };

            secondStarActionIsEnd = false;
            starThree.setVisible(true);
            SequenceAction maStarThird = new SequenceAction(
                    Actions.moveTo(starTwo.getX() + starTwo.getWidth() + 8, 0, 0.5f),
                    Actions.moveTo(starTwo.getX() + starTwo.getWidth() + 8 - 2, 2, 0.1f), checkEndOfActionThirdStar);
            starThree.addAction(maStarThird);

        }
    }
}

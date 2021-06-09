package com.timgapps.warfare.screens.level.level_windows;


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
    private boolean isEndActions = false;           // указывает, что actions звезд завершены

    private float towerHealth;
    private float fullTowerHealth;
    private int starsCount = 0;
    private int count = 0;

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

        setWidth(starOne.getWidth() * 3 + 12);
        setHeight(starOne.getHeight() + 16);
        startStarsActions(starsCount);
    }

    public void startStarsActions(int starsCount) {
        this.starsCount = starsCount;

        starOneInactive.setVisible(true);
        starTwoInactive.setVisible(true);
        starThreeInactive.setVisible(true);

        starOne.setVisible(true);
        starTwo.setVisible(true);
        starThree.setVisible(true);

        Action checkEndOfActionFirstStar = new Action() {
            @Override
            public boolean act(float delta) {
                firstStarActionIsEnd = true;
                return true;
            }
        };

        SequenceAction maStarFirst = new SequenceAction(
                Actions.moveTo(0, 0, 0.5f),
                Actions.moveTo(-2, 2, 0.1f),
                checkEndOfActionFirstStar);
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
                    Warfare.media.playSound("star.ogg");
                    return true;
                }
            };
            count++;
            firstStarActionIsEnd = false;

            /** проеверим, если здоровье башни больше 33 % , то запускаем action для второй звезды, если нет, isEndActions = true;*/
            if (count < starsCount) {       // count = 1
//                System.out.println("count = " + count + "\n starTwo.Start");
                starTwo.setVisible(true);
                SequenceAction maStarSecond = new SequenceAction(
                        Actions.moveTo(0 + starOne.getWidth() + 8, 16, 0.5f),
                        Actions.moveTo(starOne.getWidth() + 8 - 2, 18, 0.1f), checkEndOfActionSecondStar);
                starTwo.addAction(maStarSecond);
            } else isEndActions = true;
        }


        if (secondStarActionIsEnd) {
            Action checkEndOfActionThirdStar = new Action() {
                @Override
                public boolean act(float delta) {
                    thirdStarActionIsEnd = true;
                    Warfare.media.playSound("star.ogg");
                    return true;
                }
            };
            count++;        // count = 2;
            secondStarActionIsEnd = false;
            if (count < starsCount) {                   // count = 2
//                System.out.println("count = " + count + "\n starThree.Start");
//                count++; // count = 3
                starThree.setVisible(true);
                SequenceAction maStarThird = new SequenceAction(
                        Actions.moveTo(starTwo.getX() + starTwo.getWidth() + 8, 0, 0.5f),
                        Actions.moveTo(starTwo.getX() + starTwo.getWidth() + 8 - 2, 2, 0.1f), checkEndOfActionThirdStar);
//                System.out.println("addAction(maStarThird");
                starThree.addAction(maStarThird);
            } else isEndActions = true;
        }

        if (thirdStarActionIsEnd) {
//            thirdStarActionIsEnd = false;
            isEndActions = true;
            Warfare.media.playSound("star.ogg");
            thirdStarActionIsEnd = false;
        }

    }

    public boolean getIsEndActions() {
        return isEndActions;
    }
}

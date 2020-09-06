package com.timgapps.warfare.Level.GUI.Screens.gifts_window;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Level.LevelMap.LevelMap;
import com.timgapps.warfare.Warfare;

import java.util.Random;

public class ResoursesGiftAnimation {
    private float x, y;                 // начальные позиции картинок ресурсов
    private float endXPos, endYPos;     // конечные позиции картинок ресурсов
    private int resoursesCount;
    private LevelMap levelMap;
    private boolean isEndAnimation;
    private static final int FOOD = 1;
    private static final int IRON = 2;
    private static final int WOOD = 3;
    private GameManager gameManager;
    private Group group;

    public ResoursesGiftAnimation(LevelMap levelMap, Group group, float x, float y, int resoursesCount) {
        this.levelMap = levelMap;
        this.x = x;
        this.y = y;
        this.resoursesCount = resoursesCount;
        this.group = group;
        gameManager = levelMap.getGameManager();
        endXPos = -386;
//        endXPos = -320;
        if (resoursesCount == 2)
            endXPos = -640;
        endYPos = levelMap.getHeight() / 3 - 128;
    }

    public void start() {
        final Random random = new Random();
//        final IconAnimation iconAnimation = new IconAnimation(levelMap,
//                "teamButton", 32, levelMap.getHeight() / 3);

        final AnimationIcon animationIcon = new AnimationIcon(group, endXPos, endYPos, "teamButton");

        // получим тип ресурса первого и второго
        int firstResourse = random.nextInt(3) + 1;
        int secondResourse = random.nextInt(3) + 1;


        Image resOne = getImageResourse(firstResourse);
        Image resTwo = null;
        if (resoursesCount == 2)
            resTwo = getImageResourse(secondResourse);


        group.addActor(resOne);
//        levelMap.addChild(resOne);
        resOne.toFront();

        if (resTwo != null) {
//            levelMap.addChild(resTwo);
            group.addActor(resTwo);
            resTwo.setPosition(x, y);
        }

        resOne.setPosition(x, y);


        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
//                iconAnimation.start();
                animationIcon.start();
                isEndAnimation = true;
//                isEndCoinsAction = true;
//                rewardTable.setVisible(true);
//                timeLabel.setVisible(true);
//                boxImage.closeBox();
                return true;
            }
        };

        SequenceAction moveActionResOne = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(x - 32, y + 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.6f),
                Actions.fadeOut(0),
                checkEndOfAction
        );

        resOne.addAction(moveActionResOne);


        if (resTwo != null) {
            SequenceAction moveActionResTwo = new SequenceAction(Actions.fadeIn(0),
                    Actions.moveTo(x + 56, y + 64, 0.8f, new Interpolation.SwingOut(1)),
                    Actions.moveTo(endXPos, endYPos, 0.6f),
                    Actions.fadeOut(0)
            );

            resTwo.addAction(moveActionResTwo);
        }
    }

    class AnimationIcon {
        float x, y;
        Image image;
        Group group;
        SequenceAction sizeAction, moveAction;
        ParallelAction action;

        public AnimationIcon(Group group, float x, float y, String regionName) {
            image = new Image(Warfare.atlas.findRegion(regionName));
            image.setPosition(x, y);
            image.setVisible(false);
            group.addActor(image);
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

        void start() {
            image.setVisible(true);
            image.addAction(action);
        }
    }

    private Image getImageResourse(int resourceType) {
        Image resourceImage;
        switch (resourceType) {
            case FOOD:
                resourceImage = new Image(Warfare.atlas.findRegion("food_icon"));
                gameManager.addFoodCount(1);
                break;
            case IRON:
                resourceImage = new Image(Warfare.atlas.findRegion("iron_icon"));
                gameManager.addIronCount(1);
                break;
            case WOOD:
                resourceImage = new Image(Warfare.atlas.findRegion("wood_icon"));
                gameManager.addWoodCount(1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + resourceType);
        }
        return resourceImage;
    }

    public boolean isEndAnimation() {
        return isEndAnimation;
    }

    public void setIsEndAnimation(boolean flag) {
        isEndAnimation = flag;
    }
}

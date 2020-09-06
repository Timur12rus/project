package com.timgapps.warfare.Level.LevelMap.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Warfare;

import java.util.Random;

public class ResourcesAction extends Group {
    private int resourcesCount;
    private boolean isEndResourcesAction = false;
    private float startXPos, startYPos, endXPos, endYPos;
    private Image resourceOne, resourceTwo;
    private static final int FOOD = 1;
    private static final int IRON = 2;
    private static final int WOOD = 3;
    private GameManager gameManager;
    private Image iconImage;

    public ResourcesAction(GameManager gameManager, int resourcesCount) {
        this.gameManager = gameManager;
        this.resourcesCount = resourcesCount;
        Random random = new Random();
        resourceOne = getImageResourse(random.nextInt(3) + 1);
        if (resourcesCount == 2) {
            resourceTwo = getImageResourse(random.nextInt(3) + 1);
            addActor(resourceTwo);
            resourceTwo.toFront();
        }
        addActor(resourceOne);
        resourceOne.toFront();
        iconImage = new Image(Warfare.atlas.findRegion("teamButton"));
        iconImage.addAction(Actions.fadeOut(0));
        addActor(iconImage);
    }

    public void start() {
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                startIconImageAction();
//                isEndResourcesAction = true;
                return true;
            }
        };

        // action для первого изображения ресурса
        SequenceAction moveActionResOne = new SequenceAction(
                Actions.moveTo(startXPos - 32, startYPos + 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.6f),
                Actions.fadeOut(0),
                checkEndOfAction
        );

        resourceOne.addAction(moveActionResOne);
        // action для второго изображения ресурса
        if (resourceTwo != null) {
            SequenceAction moveActionResTwo = new SequenceAction(Actions.fadeIn(0),
                    Actions.moveTo(startXPos + 56, startYPos + 64, 0.8f, new Interpolation.SwingOut(1)),
                    Actions.moveTo(endXPos, endYPos, 0.6f),
                    Actions.fadeOut(0)
            );
            resourceTwo.addAction(moveActionResTwo);
        }
    }

    private void startIconImageAction() {
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
//                startIconImageAction();
                isEndResourcesAction = true;
                return true;
            }
        };
        float x = iconImage.getX();
        float y = iconImage.getY();
        SequenceAction iconSizeAction = new SequenceAction(
                Actions.fadeIn(0),
                Actions.sizeBy(8, 8, 0.2f),
                Actions.sizeBy(-8, -8, 0.2f),
                Actions.fadeOut(0.5f),
                checkEndOfAction
        );
        SequenceAction moveAction = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(x - 4, y - 4, 0.2f),
                Actions.moveTo(x, y, 0.2f)
        );
        ParallelAction parallelAction = new ParallelAction(moveAction, iconSizeAction);
        iconImage.addAction(parallelAction);
//        iconImage.addAction(iconSizeAction);
    }

    public void setStartPosition(float x, float y) {
        resourceOne.setPosition(x, y);
        if (resourceTwo != null) {
            resourceTwo.setPosition(x, y);
        }
        startXPos = x;
        startYPos = y;
    }

    public void setEndPosition(float x, float y) {
        endXPos = x;
        endYPos = y;
        iconImage.setPosition(x, y);
    }

    public boolean isEndResourcesAction() {
        return isEndResourcesAction;
    }

    // метод устанавливает, что действие завершилось
    public void setEndResourcesAction() {
        isEndResourcesAction = false;
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

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isEndResourcesAction) {
            this.remove();
            System.out.println("REMOVE RESOURCESACTION");
        }
    }
}

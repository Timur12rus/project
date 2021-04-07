package com.timgapps.warfare.screens.map.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.map.gifts_panel.gui_elements.GiftImageIcon;

import java.util.Random;

public class MyResourcesAction {
    private GameManager gameManager;
    private int resourcesCount;
    private Image resourceOne, resourceTwo;
    //    private GiftImageIcon resourceOne, resourceTwo;
    private static final int FOOD = 1;
    private static final int IRON = 2;
    private static final int WOOD = 3;
    private Vector2 startPosition, endPosition;
    private AddOverlayActionHelper addOverlayActionHelper;

    public MyResourcesAction(AddOverlayActionHelper addOverlayActionHelper, Vector2 startPosition, GameManager gameManager, int resourcesCount) {
        this.gameManager = gameManager;
        this.resourcesCount = resourcesCount;
        this.addOverlayActionHelper = addOverlayActionHelper;
        this.startPosition = startPosition;
        Random random = new Random();
        resourceOne = getImageResourse(random.nextInt(3) + 1);
        resourceOne.setPosition(startPosition.x, startPosition.y);
        if (resourcesCount == 2) {
            resourceTwo = getImageResourse(random.nextInt(3) + 1);
            resourceTwo.setPosition(startPosition.x, startPosition.y);
            resourceTwo.addAction(Actions.fadeOut(0));
            addOverlayActionHelper.addChildOnOverlay(resourceTwo);
            resourceTwo.toFront();
        }
        resourceOne.addAction(Actions.fadeOut(0));
        addOverlayActionHelper.addChildOnOverlay(resourceOne);
        endPosition = new Vector2();
        float deltaX = gameManager.getGeom("teamUpgradeIcon").getSize().x / 2 - resourceOne.getWidth() / 2;
        float deltaY = gameManager.getGeom("teamUpgradeIcon").getSize().y / 2 - resourceOne.getHeight() / 2;
        endPosition.set(gameManager.getGeom("teamUpgradeIcon").getPosition());
        endPosition.add(deltaX, deltaY);
        start();
    }

    public void start() {
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                addOverlayActionHelper.startIconAction();
//                addOverlayActionHelper.removeChildFromOverlay(resourceOne);
//                resourceOne.addAction(Actions.fadeOut(0));
                resourceOne.setVisible(false);
                addOverlayActionHelper.removeChildFromOverlay(resourceOne);
                if (resourceTwo != null) {
//                    resourceTwo.addAction(Actions.fadeOut(0));
                    resourceTwo.setVisible(false);
                    addOverlayActionHelper.removeChildFromOverlay(resourceTwo);
                }
                return true;
            }
        };

        // action для первого изображения ресурса
        SequenceAction moveActionResOne = new SequenceAction(
                Actions.delay(0.28f),
                Actions.fadeIn(0),
                Actions.moveTo(startPosition.x - 32, startPosition.y + 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endPosition.x, endPosition.y, 0.6f),
//                Actions.fadeOut(0),
                checkEndOfAction
        );

        resourceOne.addAction(moveActionResOne);
        // action для второго изображения ресурса
        if (resourceTwo != null) {
            SequenceAction moveActionResTwo = new SequenceAction(
//                    Actions.fadeIn(0),
                    Actions.delay(0.28f),
                    Actions.fadeIn(0),
                    Actions.moveTo(startPosition.x + 56, startPosition.y + 64, 0.8f, new Interpolation.SwingOut(1)),
                    Actions.moveTo(endPosition.x, endPosition.y, 0.6f)
//                    Actions.fadeOut(0)
            );
            resourceTwo.addAction(moveActionResTwo);
        }
    }

    // метод получает изображение знача ресурса
    private Image getImageResourse(int resourceType) {
//    private GiftImageIcon getImageResourse(int resourceType) {
        Image resourceImage;
//        GiftImageIcon resourceImage;
        switch (resourceType) {
            case FOOD:
                resourceImage = new Image(Warfare.atlas.findRegion("food_icon"));
//                resourceImage = new GiftImageIcon(Warfare.atlas.findRegion("food_icon"));
                gameManager.addFoodCount(1);                // добавляем в менеджер ресурс (мы его получилил)
                break;
            case IRON:
                resourceImage = new GiftImageIcon(Warfare.atlas.findRegion("iron_icon"));
//                resourceImage = new GiftImageIcon(Warfare.atlas.findRegion("iron_icon"));
                gameManager.addIronCount(1);                 // добавляем в менеджер ресурс (мы его получилил)
                break;
            case WOOD:
                resourceImage = new GiftImageIcon(Warfare.atlas.findRegion("wood_icon"));
//                resourceImage = new GiftImageIcon(Warfare.atlas.findRegion("wood_icon"));
                gameManager.addWoodCount(1);                  // добавляем в менеджер ресурс (мы его получилил)
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + resourceType);
        }
        return resourceImage;
    }
}

package com.timgapps.warfare.screens.map.actions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class IconAction {
    private Image iconImage;
    private Vector2 iconPosition;
    private AddOverlayActionHelper addOverlayActionHelper;

    public IconAction(AddOverlayActionHelper addOverlayActionHelper, Image image, Vector2 iconPosition) {
        iconImage = image;
        this.iconPosition = iconPosition;
        this.addOverlayActionHelper = addOverlayActionHelper;
        iconImage.setPosition(iconPosition.x, iconPosition.y);
        addOverlayActionHelper.addChildOnOverlay(iconImage);
        start();
    }

    // метод запускает действие
    public void start() {

        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
//                addOverlayActionHelper.removeChildOnOverlay(iconImage);
                iconImage.remove();
                return true;
            }
        };

        ParallelAction parallelActionOne = new ParallelAction(
                Actions.sizeBy(8, 8, 0.4f),
                Actions.moveBy(-4, -4, 0.4f)
        );
        ParallelAction parallelActionTwo = new ParallelAction(
                Actions.sizeBy(-8, -8, 0.4f),
                Actions.moveBy(4, 4, 0.4f)
        );
        SequenceAction sequenceAction = new SequenceAction(
                parallelActionOne,
                parallelActionTwo,
                Actions.fadeOut(0.5f),
                checkEndOfAction
        );
        iconImage.addAction(sequenceAction);
    }
}

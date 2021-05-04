package com.timgapps.warfare.Units.GameUnits.Enemy.bonus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.level.LevelScreen;

public class Bonus extends Group {
    protected Image image;
    protected Label label;
    private LevelScreen levelScreen;
    private boolean isClicked;
    private float imageWidth, imageHeight;

    public Bonus(LevelScreen levelScreen, Vector2 position) {
        debug();
        this.levelScreen = levelScreen;
        image = new Image(Warfare.atlas.findRegion("energyIcon_box"));
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.CYAN;
        labelStyle.font = Warfare.font30;
        label = new Label(" + 15", labelStyle);
        addActor(image);
        addActor(label);
        setSize(image.getWidth() * 1.5f, image.getHeight() * 1.5f);
        this.levelScreen.addChild(this, position.x - 16, position.y);
        float labelX = imageWidth;
        float labelY = imageHeight / 2 - 16;
        label.setVisible(false);
        label.setPosition(getParent().getX() + labelX, getParent().getY() + labelY);
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                click();
            }
        });
        float deltaMoveX = (imageWidth - imageWidth * 1.2f) / 2;
        ParallelAction moveSizeActionOne = new ParallelAction(
                Actions.sizeTo(imageWidth * 1.2f, imageHeight, 0.12f, Interpolation.pow5In),
                Actions.moveBy(deltaMoveX, 0, 0.12f, Interpolation.pow5In)
        );

        ParallelAction moveSizeActionTwo = new ParallelAction(
                Actions.sizeTo(imageWidth, imageHeight, 0.08f, Interpolation.pow5In),
                Actions.moveBy(-deltaMoveX, 0, 0.08f, Interpolation.pow5In)
        );

        ParallelAction moveSizeActionThree = new ParallelAction(
                Actions.sizeTo(imageWidth, imageHeight * 1.2f, 0.2f, Interpolation.swingIn),
                Actions.moveBy(0, 12, 0.3f, Interpolation.swingIn)
        );

        ParallelAction moveSizeActionFour = new ParallelAction(
                Actions.sizeTo(imageWidth, imageHeight, 0.3f, Interpolation.swingIn),
                Actions.moveBy(0, -12, 0.3f, Interpolation.swingIn)
        );

        SequenceAction sizeAction = new SequenceAction(
                moveSizeActionOne,
                moveSizeActionTwo,
                moveSizeActionThree,
                moveSizeActionFour
        );
        image.addAction(sizeAction);
    }

    @Override
    public void act(float delta) {
        if (levelScreen.getState() != LevelScreen.PAUSED) {
            super.act(delta);
        }
    }

    private void click() {
        if (!isClicked) {
            isClicked = true;
//            image.remove();
            label.setVisible(true);
            levelScreen.addEnergyCount(15);

            Action checkEndOfAction = new Action() {
                @Override
                public boolean act(float delta) {
                    clearActions();
                    remove();
                    return true;
                }
            };

            SequenceAction moveAction = new SequenceAction(
                    Actions.moveBy(0, 108, 1f, Interpolation.pow3Out),
                    Actions.fadeOut(0.6f, Interpolation.pow3Out),
                    checkEndOfAction
            );
            addAction(moveAction);
        }
    }
}

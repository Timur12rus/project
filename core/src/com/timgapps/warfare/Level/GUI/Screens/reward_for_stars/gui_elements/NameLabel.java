package com.timgapps.warfare.Level.GUI.Screens.reward_for_stars.gui_elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Warfare;


public class NameLabel {
    private Label label;
    private Texture texture;
    private Image rectangleImage;

    public NameLabel(StageGame stageGame, Vector2 position) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.WHITE;
        labelStyle.font = Warfare.font40;
        label = new Label("Gnome", labelStyle);

        rectangleImage = new Image(createBackgroundTexture());
        Vector2 rectangleImagePosition = new Vector2(position.x - rectangleImage.getWidth() / 2, position.y);
        rectangleImage.setPosition(rectangleImagePosition.x, rectangleImagePosition.y);

        label.setPosition(rectangleImage.getX() + (rectangleImage.getWidth() - label.getWidth()) / 2,
                position.y + (rectangleImage.getHeight() - label.getHeight()) / 2);
        rectangleImage.setColor(1, 1, 1, 0.5f);

        stageGame.addChild(rectangleImage);
        stageGame.addChild(label);

        ParallelAction sizeAction = new ParallelAction(
                Actions.moveTo(rectangleImagePosition.x, rectangleImagePosition.y, 1, Interpolation.swingOut),
                Actions.sizeTo(rectangleImage.getWidth(), rectangleImage.getHeight(), 1, Interpolation.swingOut)
        );

        SequenceAction action = new SequenceAction(
                Actions.sizeTo(0, 0, 0),
                Actions.moveTo(rectangleImagePosition.x + rectangleImage.getWidth() / 2, rectangleImagePosition.y + rectangleImage.getHeight() / 2, 0),
                Actions.delay(1.7f),
                sizeAction
        );
        rectangleImage.addAction(action);

        SequenceAction labelAction = new SequenceAction(
                Actions.fadeOut(0),
                Actions.delay(2.4f),
                Actions.fadeIn(1));
        label.addAction(labelAction);
    }

    private Texture createBackgroundTexture() {
        Pixmap bgPixmap = createProceduralPixmap(280, 64, new Color(0x1d511bff));
        return new Texture(bgPixmap);
    }

    private Pixmap createProceduralPixmap(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }
}

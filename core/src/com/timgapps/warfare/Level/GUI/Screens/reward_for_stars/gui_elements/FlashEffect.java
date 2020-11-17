package com.timgapps.warfare.Level.GUI.Screens.reward_for_stars.gui_elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Warfare;

public class FlashEffect {
    private Vector2 position;
    private boolean isStarted, isEnd;
    private StageGame stageGame;
    private SequenceAction sa;
    private Image flashImage, circleLightImage, bgImage, bgColor;
    private Texture bgTexture;

    public FlashEffect(StageGame stageGame, Vector2 position) {
        this.stageGame = stageGame;
        createBackgroundTexture();
//        bgImage.setColor(0,1,0,1);

        flashImage = new Image(Warfare.atlas.findRegion("flash4"));
        circleLightImage = new Image(Warfare.atlas.findRegion("flash1"));
        this.position = position;
//        this.position.x -= flashImage.getWidth() / 2;
//        this.position.y -= flashImage.getHeight() / 2;
        stageGame.addChild(flashImage, position.x - flashImage.getWidth() / 2, position.y - flashImage.getHeight() / 2);
        stageGame.addChild(circleLightImage, position.x - circleLightImage.getWidth() / 2, position.y - circleLightImage.getHeight() / 2);
//        System.out.println("Size = " + flashImage.getWidth() + ", " + flashImage.getHeight());
    }

    private void createBackgroundTexture() {
        Pixmap bgPixmap = createProceduralPixmap((int) stageGame.getWidth(), (int) stageGame.getHeight(), new Color(0x6da86bff));
        bgTexture = new Texture(bgPixmap);
        bgImage = new Image(bgTexture);
//        bgImage = new Image(bgTexture);
//        bgImage.addAction(Actions.fadeOut(0));
        stageGame.addChild(bgImage);
    }

    private Pixmap createProceduralPixmap(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }



    public void start() {
//        if (!isStarted) {
        isStarted = true;

//        SequenceAction sa = new SequenceAction();
////        sa.addAction(Actions.delay(0.1f));
//        sa.addAction(Actions.fadeIn(1, new Interpolation.PowIn(3)));
//        bgImage.addAction(sa);

        SequenceAction sequenceAction1 = new SequenceAction(Actions.fadeOut(0), Actions.fadeIn(1.3f, Interpolation.pow3In),
                Actions.fadeOut(1.4f));
        circleLightImage.addAction(sequenceAction1);

        Action changeBgColor  = new Action() {
            @Override
            public boolean act(float delta) {
                bgImage.setColor(0,1,0,1);
                return false;
            }
        };

        SequenceAction changeColor = new SequenceAction();
        changeColor.addAction(Actions.delay(2.2f));
        changeColor.addAction(changeBgColor);
//        bgImage.addAction(changeColor);

                ParallelAction flashAction = new ParallelAction();
//        flashAction.addAction(Actions.moveTo(position.x - 600, position.y - 600, 1.4f, Interpolation.pow4In));
        flashAction.addAction(Actions.moveTo(position.x - 700, position.y - 700, 1.4f, Interpolation.swingOut));
        flashAction.addAction(Actions.sizeTo(1400, 1400, 1.4f, Interpolation.swingOut));
//        flashAction.addAction(Actions.sizeTo(1200, 1200, 1.4f, Interpolation.swingIn));
//        flashAction.addAction(Actions.sizeTo(1200, 1200, 1.4f, Interpolation.pow4In));
        flashAction.addAction(Actions.fadeOut(2.4f));
//        flashImage.addAction(flashAction);

        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(0));
        sequenceAction.addAction(Actions.delay(0.8f));
        sequenceAction.addAction(Actions.fadeIn(0.7f));
        sequenceAction.addAction(flashAction);

        flashImage.addAction(sequenceAction);

//        ParallelAction flashAction = new ParallelAction();
////        flashAction.addAction(Actions.moveTo(position.x - 600, position.y - 600, 2, new Interpolation.SwingOut(1)));
////        flashAction.addAction(Actions.sizeTo(1200, 1200, 2, new Interpolation.SwingOut(1)));
//        flashAction.addAction(Actions.moveTo(position.x - 600, position.y - 600, 1.4f, Interpolation.pow3In));
//        flashAction.addAction(Actions.sizeTo(1200, 1200, 1.4f, Interpolation.pow3In));
//        flashAction.addAction(Actions.fadeOut(2.4f));
//        flashImage.addAction(flashAction);


        bgImage.addAction(Actions.fadeIn(0.8f, Interpolation.pow3In));
//        bgImage.addAction(Actions.fadeIn(0.8f, new Interpolation.PowIn(3)));

    }
}

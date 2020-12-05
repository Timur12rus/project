package com.timgapps.warfare.screens.get_reward_screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.get_reward_screen.gui_elements.NameLabel;
import com.timgapps.warfare.screens.get_reward_screen.actions.StarsAction;
import com.timgapps.warfare.screens.get_reward_screen.gui_elements.Sunshine;

public class FlashEffect {
    protected Vector2 position;
    protected boolean isStarted, isEnd;
    private StageGame stageGame;
    private SequenceAction sa;
    protected Image flashImage, circleLightImage, bgImage, bgColor;
    private Texture bgTexture;
    protected Sunshine sunshine;
    protected float imageWidth, imageHeight;
    protected Image rewardImage;
    private StarsAction starsAction;
    private String imageString;
    protected NameLabel nameLabel;

    public FlashEffect(StageGame stageGame, RewardForStarsData rewardForStarsData, Vector2 position) {
        this.stageGame = stageGame;
        createBackgroundTexture();
        imageString = rewardForStarsData.getImageString();

        rewardImage = new Image(Warfare.atlas.findRegion(imageString));
//        rewardImage = new Image(Warfare.atlas.findRegion("gnomeUnitImage"));
        imageWidth = rewardImage.getWidth();
        imageHeight = rewardImage.getHeight();

        flashImage = new Image(Warfare.atlas.findRegion("flash4"));
        circleLightImage = new Image(Warfare.atlas.findRegion("flash1"));
        sunshine = new Sunshine();      // лучи света

        sunshine.addAction(Actions.fadeOut(0));
        this.position = position;
        stageGame.addChild(flashImage, position.x - flashImage.getWidth() / 2, position.y - flashImage.getHeight() / 2);
        stageGame.addChild(circleLightImage, position.x - circleLightImage.getWidth() / 2, position.y - circleLightImage.getHeight() / 2);
        sunshine.setPosition(position.x - sunshine.getRayWidth() / 2 - 48, position.y + sunshine.getRadius() + 48);
        stageGame.addChild(sunshine);

        starsAction = new StarsAction(stageGame, position);

        rewardImage.setPosition(position.x - rewardImage.getWidth() / 2, position.y - rewardImage.getHeight() / 2);
        stageGame.addChild(rewardImage);

        nameLabel = new NameLabel(stageGame, new Vector2(position.x, position.y - 148), rewardForStarsData.getName());

    }

    public void setEndCoinsPosition(Vector2 position) {

    }

    private void createBackgroundTexture() {
        Pixmap bgPixmap = createProceduralPixmap((int) stageGame.getWidth(), (int) stageGame.getHeight(), new Color(0x6da86bff));
        bgTexture = new Texture(bgPixmap);
        bgImage = new Image(bgTexture);
        stageGame.addChild(bgImage);
    }

    private Pixmap createProceduralPixmap(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }


    public void start() {
        isStarted = true;
        SequenceAction sequenceAction1 = new SequenceAction(Actions.fadeOut(0),
                Actions.fadeIn(1.3f,
                        Interpolation.pow3In),
                Actions.fadeOut(1.4f));

        circleLightImage.addAction(sequenceAction1);

        SequenceAction sunshineActions = new SequenceAction(
//                Actions.fadeOut(0),
                Actions.delay(2.5f),
                Actions.fadeIn(3));
        sunshine.addAction(sunshineActions);

        rewardImage.setOrigin(imageWidth / 2, imageHeight / 2);

        ParallelAction imageSizeAction = new ParallelAction(
                Actions.moveTo(position.x - rewardImage.getWidth(), position.y - rewardImage.getHeight() / 2, 1.3f, Interpolation.swingOut),
//                Actions.moveTo(position.x - rewardImage.getWidth() / 2 - rewardImage.getWidth() * 0.5f , position.y - rewardImage.getHeight() / 2, 1.3f, Interpolation.swingOut),
                Actions.sizeTo(imageWidth * 2, imageHeight * 2, 1.3f, Interpolation.swingOut),
                Actions.fadeIn(2));


        SequenceAction imageAction1 = new SequenceAction(
                Actions.fadeOut(0),
                Actions.sizeTo(imageWidth * 0.5f, imageHeight * 0.5f, 0),
                Actions.moveTo(position.x - rewardImage.getWidth() / 4, position.y, 0),
                Actions.delay(1.4f),
                imageSizeAction);

        rewardImage.addAction(imageAction1);

        ParallelAction flashAction = new ParallelAction();
        flashAction.addAction(Actions.moveTo(position.x - 700, position.y - 700, 1.4f, Interpolation.swingOut));
        flashAction.addAction(Actions.sizeTo(1400, 1400, 1.4f, Interpolation.swingOut));
        flashAction.addAction(Actions.fadeOut(2.4f));

        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(0));
        sequenceAction.addAction(Actions.delay(0.8f));
        sequenceAction.addAction(Actions.fadeIn(0.7f));
        sequenceAction.addAction(flashAction);

        flashImage.addAction(sequenceAction);
        bgImage.addAction(Actions.fadeIn(0.8f, Interpolation.pow3In));
    }

    public void clear() {
        rewardImage.clearActions();
        flashImage.clearActions();
        bgImage.clearActions();
        sunshine.clearActions();
        sunshine.clear();
        bgTexture.dispose();

        rewardImage.remove();
        flashImage.remove();
        bgImage.remove();
        sunshine.remove();
        sunshine.remove();

        starsAction.clear();
    }
}

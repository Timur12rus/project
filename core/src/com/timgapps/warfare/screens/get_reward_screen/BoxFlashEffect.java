package com.timgapps.warfare.screens.get_reward_screen;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData;

public class BoxFlashEffect extends FlashEffect {
    public BoxFlashEffect(StageGame stageGame, RewardForStarsData rewardForStarsData, Vector2 position) {
        super(stageGame, rewardForStarsData, position);
    }

    @Override
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
}

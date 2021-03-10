package com.timgapps.warfare.screens.get_reward_screen;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.screens.get_reward_screen.actions.CoinsAction;
import com.timgapps.warfare.screens.get_reward_screen.actions.ResourcesAction;

public class BoxFlashEffect extends FlashEffect {
    private BoxActor boxActor;
    private StageGame stageGame;

    public BoxFlashEffect(StageGame stageGame, GameManager gameManager, int indexOfReward, Vector2 position) {
        super(stageGame, gameManager, indexOfReward, position);
        nameLabel.remove();
        this.stageGame = stageGame;
        boxActor = new BoxActor();
        boxActor.setPosition(position.x - rewardImage.getWidth() / 2, position.y - rewardImage.getHeight() / 2);
        stageGame.addChild(boxActor);
//        gameManager.addCoinsCount(100);
//        gameManager.addFoodCount(2);
//        gameManager.addIronCount(2);
//        gameManager.addWoodCount(2);
//        gameManager.saveGame();
    }

    @Override
    public void start() {
        rewardImage.remove();
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

        boxActor.setOrigin(boxActor.getWidth() / 2, boxActor.getHeight() / 2);

        ParallelAction imageSizeAction = new ParallelAction(
                Actions.moveTo(position.x - boxActor.getWidth(), position.y - boxActor.getHeight() / 2, 1.3f, Interpolation.swingOut),
//                Actions.moveTo(position.x - rewardImage.getWidth() / 2 - rewardImage.getWidth() * 0.5f , position.y - rewardImage.getHeight() / 2, 1.3f, Interpolation.swingOut),
                Actions.sizeTo(boxActor.getWidth() * 2, boxActor.getHeight() * 2, 1.3f, Interpolation.swingOut),
                Actions.fadeIn(0.8f));
//                Actions.fadeIn(2));

        // начало действия анимации открытия ящика
        Action startBoxAction = new Action() {
            @Override
            public boolean act(float delta) {
                boxActor.startAnimation();
                return true;
            }
        };

        SequenceAction imageAction1 = new SequenceAction(
                Actions.fadeOut(0),
                Actions.sizeTo(boxActor.getWidth() * 0.5f, boxActor.getHeight() * 0.5f, 0),
                Actions.moveTo(position.x - boxActor.getWidth() / 4, position.y, 0),
                Actions.delay(1.4f),
                imageSizeAction);


        imageAction1.addAction(startBoxAction);
        boxActor.addAction(imageAction1);           // анмация появления сундука

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

        // анимация появления монет и их движения к панели монет

        // начало действия анимации открытия ящика
        final CoinsAction coinsAction = new CoinsAction(stageGame, position);
        coinsAction.setEndPosition(gameManager.getCoinsPanel().getPos());
        final ResourcesAction resourcesAction = new ResourcesAction(stageGame, position);
        Action startCoinsAction = new Action() {
            @Override
            public boolean act(float delta) {
                coinsAction.startAnimation();
                resourcesAction.startAnimation();
                return true;
            }
        };

        Action isEndCoinsAnimation = new Action() {
            @Override
            public boolean act(float delta) {
                coinsAction.isEndAnimation();
                gameManager.getCoinsPanel().redraw();
                isEnd = true;
                return true;
            }
        };

        SequenceAction coinsSequenceAction = new SequenceAction(
                startCoinsAction,
                Actions.delay(4.8f),           // время, после, которого уничтожатся монетки
                isEndCoinsAnimation
        );

        boxActor.addAction(coinsSequenceAction);

        // TODO сделать анимацию движения монет и ресурсов
    }
}

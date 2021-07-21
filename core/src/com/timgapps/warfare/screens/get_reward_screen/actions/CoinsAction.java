package com.timgapps.warfare.screens.get_reward_screen.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Warfare;

public class CoinsAction {
    private Image coinOne, coinTwo, coinThree, coinFour, coinFive;
    private Vector2 startPosition;
    private Vector2 endPosition;
    private boolean isEnd;

    public CoinsAction(StageGame stageGame, Vector2 startPosition, Vector2 endPosition) {
        coinOne = new Image(Warfare.atlas.findRegion("coin_icon"));
        coinTwo = new Image(Warfare.atlas.findRegion("coin_icon"));
        coinThree = new Image(Warfare.atlas.findRegion("coin_icon"));
        coinFour = new Image(Warfare.atlas.findRegion("coin_icon"));
        coinFive = new Image(Warfare.atlas.findRegion("coin_icon"));
        this.startPosition = new Vector2(startPosition.x - 32, startPosition.y + 64);  // позиция coinsPanel
        this.endPosition = endPosition;

        coinOne.setPosition(this.startPosition.x, this.startPosition.y);
        coinTwo.setPosition(this.startPosition.x, this.startPosition.y);
        coinThree.setPosition(this.startPosition.x, this.startPosition.y);
        coinFour.setPosition(this.startPosition.x, this.startPosition.y);
        coinFive.setPosition(this.startPosition.x, this.startPosition.y);

        stageGame.addChild(coinOne);
        stageGame.addChild(coinTwo);
        stageGame.addChild(coinThree);
        stageGame.addChild(coinFour);
        stageGame.addChild(coinFive);
    }

//    public void setEndPosition(Vector2 endPosition) {
//        this.endPosition = endPosition;
//    }

    // класс-действие для движения монет
    class MoveCoinAction {
        public MoveCoinAction(Image image, float deltaX, float deltaY) {
            Action isEndAnimation = new Action() {
                @Override
                public boolean act(float delta) {
                    isEnd = true;
                    return true;
                }
            };
            SequenceAction moveCoinAction = new SequenceAction(
                    Actions.fadeOut(0),
                    Actions.delay(3.2f),
                    Actions.fadeIn(0),
                    Actions.moveTo(startPosition.x + deltaX, startPosition.y + deltaY, 0.7f, Interpolation.pow3Out),
                    Actions.moveTo(endPosition.x, endPosition.y, 0.8f, Interpolation.pow3In)        // TODO испрвить здесь какой-то баг при запуске бывает
            );
            moveCoinAction.addAction(isEndAnimation);
            image.addAction(moveCoinAction);
        }
    }

    public void endAnimation(GameManager gameManager) {
        gameManager.getCoinsPanel().redraw();
        coinOne.clearActions();
        coinTwo.clearActions();
        coinThree.clearActions();
        coinFour.clearActions();
        coinFive.clearActions();
        coinOne.remove();
        coinTwo.remove();
        coinThree.remove();
        coinFour.remove();
        coinFive.remove();
    }

    public boolean isEndAnimation() {
        System.out.println("THE END");
        coinOne.clearActions();
        coinTwo.clearActions();
        coinThree.clearActions();
        coinFour.clearActions();
        coinFive.clearActions();
        coinOne.remove();
        coinTwo.remove();
        coinThree.remove();
        coinFour.remove();
        coinFive.remove();
        return isEnd;
    }

    public void startAnimation() {
        new MoveCoinAction(coinOne, 0, 104);
        new MoveCoinAction(coinTwo, 32, 84);
        new MoveCoinAction(coinThree, 56, 104);
        new MoveCoinAction(coinFour, 24, 114);
        new MoveCoinAction(coinFive, 42, 148);
    }
}

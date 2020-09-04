package com.timgapps.warfare.Level.LevelMap.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Warfare;

public class CoinsAction extends Group {
    private Image coinOne, coinTwo, coinThree;
    private boolean isEndCoinsAction = false;
    private float startXPos, startYPos, endXPos, endYPos;

    public CoinsAction() {
        coinOne = new Image(Warfare.atlas.findRegion("coin_icon"));
        coinTwo = new Image(Warfare.atlas.findRegion("coin_icon"));
        coinThree = new Image(Warfare.atlas.findRegion("coin_icon"));
        addActor(coinOne);
        addActor(coinTwo);
        addActor(coinThree);
        coinOne.toFront();
        coinTwo.toFront();
        coinThree.toFront();
    }

    public void setStartPosition(float x, float y) {
        coinOne.setPosition(x, y);
        coinTwo.setPosition(x, y);
        coinThree.setPosition(x, y);
        startXPos = x;
        startYPos = y;
    }

    public void setEndPosition(float x, float y) {
        endXPos = x;
        endYPos = y;
    }

    public void start() {
        // action проверки завершения действия
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                isEndCoinsAction = true;
                return true;
            }
        };
        // action для первой монеты
        SequenceAction moveActionCoinOne = new SequenceAction(
                Actions.moveTo(startXPos - 32, startYPos - 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0)
        );

        // action для второй монеты
        SequenceAction moveActionCoinTwo = new SequenceAction(
                Actions.moveTo(startXPos + 32, startYPos - 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0)
        );

        // action для третьей монеты
        SequenceAction moveActionCoinThree = new SequenceAction(
                Actions.moveTo(startXPos + 32, startYPos + 32, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endXPos, endYPos, 0.8f),
                Actions.fadeOut(0), checkEndOfAction
        );
        coinOne.addAction(moveActionCoinOne);
        coinTwo.addAction(moveActionCoinTwo);
        coinThree.addAction(moveActionCoinThree);
    }

    // метод возвращает закончилось ли действие добавления монет
    public boolean isEndCoinsAction() {
        return isEndCoinsAction;
    }

    // метод устанавливает, что действие завершилось
    public void setEndCoinsAction() {
        isEndCoinsAction = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isEndCoinsAction) {
            this.remove();
            System.out.println("REMOVE");
        }
    }
}

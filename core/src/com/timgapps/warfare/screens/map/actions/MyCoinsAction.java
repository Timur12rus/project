package com.timgapps.warfare.screens.map.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class MyCoinsAction {
    private ArrayList<Image> coins;
    private Vector2 startPosition, endPosition;
    private int coinsNum;
    private AddOverlayActionHelper addOverlayActionHelper;
    private int coinsCount;
    private GameManager gameManager;

    public MyCoinsAction(AddOverlayActionHelper addOverlayActionHelper, Vector2 startPosition, GameManager gameManager, int coinsNum) {
        this.coinsNum = coinsNum;
        this.addOverlayActionHelper = addOverlayActionHelper;
        this.startPosition = startPosition;
        this.gameManager = gameManager;
        coins = new ArrayList<Image>();
        for (int i = 0; i < 6; i++) {
            coins.add(new Image(Warfare.atlas.findRegion("coin_icon")));
        }
        endPosition = new Vector2(gameManager.getCoinsPanel().getX(), gameManager.getCoinsPanel().getY());
        int index = 0;
        for (Image coin : coins) {
            coin.setPosition(startPosition.x, startPosition.y);
            addOverlayActionHelper.addChildOnOverlay(coin);
            coin.toFront();
            coin.addAction(Actions.fadeOut(0));
            startCoinAction(coin, index);
            index++;
        }
    }

    // метод для старта action
    private void startCoinAction(Actor actor, int index) {
        // action для монеты
        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                actor.setVisible(false);
                coinsCount++;
//                addOverlayActionHelper.removeChildFromOverlay(actor);
                if (coinsCount > 4) {
                    gameManager.getCoinsPanel().redraw();
                }
                return true;
            }
        };

        SequenceAction moveActionCoin = new SequenceAction(
                Actions.delay(0.35f),
                Actions.fadeIn(0),
                Actions.moveBy(generateAmount(index).x, generateAmount(index).y, 0.8f, new Interpolation.SwingOut(1)),
                Actions.moveTo(endPosition.x, endPosition.y, 0.6f),
                checkEndOfAction
        );
        actor.addAction(moveActionCoin);
    }

    public void clear() {
        for (Image coin : coins) {
            coin.remove();
        }
    }

    private Vector2 generateAmount(int index) {
        switch (index) {
            case 0:
                return new Vector2(0, -32);
            case 1:
                return new Vector2(16, 16);
            case 2:
                return new Vector2(0, 64);
            case 3:
                return new Vector2(48, 32);
            case 4:
                return new Vector2(64, 0);
            case 5:
                return new Vector2(32, -24);
//            default:
//                return new Vector2(0, 0);
        }
        return new Vector2(0, 0);
    }
}

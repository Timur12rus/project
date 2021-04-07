package com.timgapps.warfare.screens.map.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Warfare;

public class AddScoreAction {
//    public AddScoreAction() {
//        Image scoreOne = new Image(Warfare.atlas.findRegion("score_icon"));
//        Image scoreTwo = new Image(Warfare.atlas.findRegion("score_icon"));
//        Image scoreThree = new Image(Warfare.atlas.findRegion("score_icon"));
//
//        // установим позицию для добавляемых монет, к которым будут применены action'ы
//        scoreOne.setPosition(getWidth() / 2, getHeight() / 2);
//        scoreTwo.setPosition(getWidth() / 2, getHeight() / 2);
//        scoreThree.setPosition(getWidth() / 2, getHeight() / 2);
//
//        addOverlayChild(scoreOne);
//        addOverlayChild(scoreTwo);
//        addOverlayChild(scoreThree);
//
//        float endXPos = scorePanel.getX() + scorePanel.getWidth() / 3;
//        float endYPos = scorePanel.getY();
//
//        // action проверки завершения действия
//        Action checkEndOfAction = new Action() {
//            @Override
//            public boolean act(float delta) {
////                isEndCoinsAction = true;
//                // добавим к общему кол-ву монет монеты (награду)
//                scorePanel.addScore(scoreReward);
//                return true;
//            }
//        };
//
//        // action для первого значка
//        SequenceAction moveActionCoinOne = new SequenceAction(Actions.fadeIn(0),
//                Actions.moveTo(getWidth() / 2 - 64, getHeight() / 2 - 64, 0.8f, new Interpolation.SwingOut(1)),
//                Actions.moveTo(endXPos, endYPos, 0.8f),
//                Actions.fadeOut(0)
//        );
//
//        // action для второго значка
//        SequenceAction moveActionCoinTwo = new SequenceAction(Actions.fadeIn(0),
//                Actions.moveTo(getWidth() / 2 - 16, getHeight() / 2 + 32, 0.8f, new Interpolation.SwingOut(1)),
//                Actions.moveTo(endXPos, endYPos, 0.8f),
//                Actions.fadeOut(0)
//        );
//
//        // action для третьего значка
//        SequenceAction moveActionCoinThree = new SequenceAction(Actions.fadeIn(0),
//                Actions.moveTo(getWidth() / 2 + 32, getHeight() / 2 + 32, 0.8f, new Interpolation.SwingOut(1)),
//                Actions.moveTo(endXPos, endYPos, 0.8f),
//                Actions.fadeOut(0),
//                checkEndOfAction
//        );
//
//        scoreOne.addAction(moveActionCoinOne);
//        scoreTwo.addAction(moveActionCoinTwo);
//        scoreThree.addAction(moveActionCoinThree);
//    }
}

package com.timgapps.warfare.Level.LevelScreens;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.Level.GUI.Screens.upgrade_window.ColorButton;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class GameOverScreen extends Group {
    public static final int ON_MAP = 1;
    public static final int ON_RETRY = 2;

    private int levelNumber;

    private Label defeatLabel;
    private Label missionLabel;
    private Label retryLabel;
    private Table buttonsTable;
    private boolean isEndAction;

    private ColorButton mapButton, retryButton;

    public GameOverScreen(Level level) {
        levelNumber = level.getLevelNumber();

        Label.LabelStyle defeatLabelStyle = new Label.LabelStyle();
        defeatLabelStyle.fontColor = Color.RED;
        defeatLabelStyle.font = Warfare.font40;

        Label.LabelStyle missionLabelStyle = new Label.LabelStyle();
        missionLabelStyle.fontColor = Color.GOLD;
        missionLabelStyle.font = Warfare.font40;

        Label.LabelStyle retryLabelStyle = new Label.LabelStyle();
        retryLabelStyle.fontColor = Color.FOREST;
//        retryLabelStyle.fontColor = Color.CHARTREUSE;
//        retryLabelStyle.fontColor = Color.DARK_GRAY;
        retryLabelStyle.font = Warfare.font20;

        defeatLabel = new Label("Defeat!", defeatLabelStyle);
        missionLabel = new Label("Mission " + levelNumber, missionLabelStyle);
        retryLabel = new Label("Retry:", retryLabelStyle);

        mapButton = new ColorButton("Map", ColorButton.GREEN_BUTTON);
        retryButton = new ColorButton("2 Steps", ColorButton.GREEN_BUTTON);

        mapButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new MessageEvent(ON_MAP));
//                if (CrazyCatapult.soundEnabled)
//                    CrazyCatapult.media.playSound("click.ogg");
            }
        });

        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new MessageEvent(ON_RETRY));
//                if (CrazyCatapult.soundEnabled)
//                    CrazyCatapult.media.playSound("click.ogg");
            }
        });

        buttonsTable = new Table().debug();

        buttonsTable.add(retryLabel).colspan(2).align(Align.right).padRight((mapButton.getWidth() - retryLabel.getWidth()) / 2);
        buttonsTable.row();
        buttonsTable.add(mapButton);
        buttonsTable.add(retryButton).padLeft(32);
        buttonsTable.setWidth(mapButton.getWidth() + retryButton.getWidth() + 32);

        buttonsTable.setVisible(false);

        defeatLabel.setPosition(-defeatLabel.getWidth() / 2, Warfare.V_HEIGHT / 3);
//        defeatLabel.setPosition(-defeatLabel.getWidth() / 2, 0);

        missionLabel.setPosition(-missionLabel.getWidth() / 2, -64);
        buttonsTable.setPosition(-buttonsTable.getWidth() / 2, -216);

        addActor(defeatLabel);
        addActor(missionLabel);
        addActor(buttonsTable);

//        // действие для надписи "DEFEAT"
//        MoveToAction moveDefeatLabel = new MoveToAction();
//        moveDefeatLabel.setPosition(-defeatLabel.getWidth() / 2, 0);
//        moveDefeatLabel.setDuration(2f);
//        moveDefeatLabel.setInterpolation(Interpolation.swingIn);

        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                buttonsTable.setVisible(true);
                buttonsTable.addAction(Actions.fadeIn(1));
//                isEndAction = true;
                return true;
            }
        };

        buttonsTable.addAction(Actions.fadeOut(2));

        defeatLabel.setScale(5);
        SequenceAction maDefeatLabel = new SequenceAction(
                Actions.moveTo(-defeatLabel.getWidth() / 2, 0, 1f, Interpolation.elasticOut),
//                Actions.scaleTo(0.5f, 0.5f, 2f),
//                Actions.moveTo(-2, 2, 0.1f),
                checkEndOfAction);

        defeatLabel.addAction(maDefeatLabel);
//        defeatLabel.addAction(moveDefeatLabel);
    }

//    @Override
//    public void act(float delta) {
//        super.act(delta);
//        if (isEndAction) {
//            buttonsTable.setVisible(true);
//            isEndAction = false;
//        }
//    }
}

package com.timgapps.warfare.screens.map.windows.reward_video_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.Utils.StringHolder;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.map.win_creator.ConstructedWindow;
import com.timgapps.warfare.screens.map.windows.upgrade_window.gui_elements.ColorButton;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.hide;

public class RewardVideoWindow extends Group {
    private ConstructedWindow constructedWindow;
    private ImageButton closeButton;
    private ColorButton watchButton;
    private Image speachBalloon;
    private Label speachLabel, rewardLabel, coinsCountLabel, loadLabel;
    private int coinsCount = 50;
    public static final int ON_REWARD = 1;
    public static final int ON_RESUME = 2;

    public RewardVideoWindow() {
        constructedWindow = new ConstructedWindow(610, 350, "");
        setSize(constructedWindow.getWidth(), constructedWindow.getHeight());

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.GRAY;
        labelStyle.font = Warfare.font30;

        speachLabel = new Label("", labelStyle);
        speachLabel.setText(Warfare.stringHolder.getString(StringHolder.SPEACH_VIDEO_REWARD) + ":");

        rewardLabel = new Label("", labelStyle);
        rewardLabel.setText(Warfare.stringHolder.getString(StringHolder.REWARD) + ":");
        rewardLabel.setPosition(getWidth() / 2 - rewardLabel.getWidth() / 2, getHeight() / 2);

        loadLabel = new Label("", labelStyle);
        loadLabel.setText("Loading ...");

        Label.LabelStyle coinsCountLabelStyle = new Label.LabelStyle();
        coinsCountLabelStyle.fontColor = Color.ORANGE;
        coinsCountLabelStyle.font = Warfare.font30;
        coinsCountLabel = new Label("50", coinsCountLabelStyle);
        coinsCountLabel.setText("" + coinsCount);

        Table rewardTable = new Table();
        Image coinImage = new Image(Warfare.atlas.findRegion("coin_icon"));
        rewardTable.add(coinsCountLabel);
        rewardTable.add(coinImage).width(48).height(48).padLeft(2);

        speachLabel = new Label("" + Warfare.stringHolder.getString(StringHolder.SPEACH_VIDEO_REWARD), labelStyle);
        watchButton = new ColorButton(Warfare.stringHolder.getString(StringHolder.WATCH), ColorButton.GREEN_BUTTON);
        speachLabel.setPosition((getWidth() - speachLabel.getWidth()) / 2, (getHeight() - speachLabel.getHeight()) / 2 + 48);
        rewardTable.setPosition((getWidth() - rewardTable.getWidth()) / 2, speachLabel.getY() - 32);
        watchButton.setPosition((getWidth() - watchButton.getWidth()) / 2, rewardTable.getY() - rewardTable.getHeight() - 48 - watchButton.getHeight());
        hide();

//        addActor(speachBalloon);
        addActor(constructedWindow);
        addActor(speachLabel);
        addActor(rewardTable);
        addActor(loadLabel);
        addActor(watchButton);

        // кнопка "ЗАКРЫТЬ" для закрытия окна с информацией о миссии
        closeButton = constructedWindow.getCloseButton();
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                fire(new MessageEvent(ON_RESUME));
            }
        });

        watchButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                fire(new MessageEvent(ON_REWARD));
            }
        });
    }

    public void loadRewardVideo() {
        loadLabel.setVisible(true);
        speachLabel.setVisible(false);
        rewardLabel.setVisible(false);
        watchButton.setVisible(false);
    }

    public void show() {
        loadLabel.setVisible(false);
        speachLabel.setVisible(true);
        rewardLabel.setVisible(true);
        watchButton.setVisible(true);
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }
}

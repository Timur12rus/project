package com.timgapps.warfare.Level.GUI.Screens.reward_for_stars;

import com.badlogic.gdx.math.Vector2;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Level.GUI.Screens.reward_for_stars.gui_elements.FlashEffect;
import com.timgapps.warfare.Level.GUI.Screens.reward_for_stars.interfaces.ScreenCloser;

public class GetRewardScreen extends StageGame implements ScreenCloser {
    private FlashEffect flashEffect;
    private BackButton backButton;
    public static final int ON_BACK = 1;

    public GetRewardScreen() {
        flashEffect = new FlashEffect(this, new Vector2(getWidth() / 2, getHeight() / 2));
        backButton = new BackButton(this);
        backButton.setPosition(64, 64);
        addOverlayChild(backButton);
    }

    @Override
    public void show() {
        super.show();
        flashEffect.start();
    }

    @Override
    public void dispose() {
        super.dispose();
        flashEffect.clear();
    }

    @Override
    public void hide() {
        super.hide();
        dispose();
    }

    @Override
    public void closeScreen() {
        hide();
        call(ON_BACK);
    }
}

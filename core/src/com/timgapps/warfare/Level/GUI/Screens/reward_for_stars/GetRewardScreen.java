package com.timgapps.warfare.Level.GUI.Screens.reward_for_stars;

import com.badlogic.gdx.math.Vector2;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Level.GUI.Screens.reward_for_stars.gui_elements.FlashEffect;

public class GetRewardScreen extends StageGame {
    private FlashEffect flashEffect;
    public GetRewardScreen() {
        flashEffect = new FlashEffect(this, new Vector2(getWidth() / 2, getHeight() / 2));
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
}

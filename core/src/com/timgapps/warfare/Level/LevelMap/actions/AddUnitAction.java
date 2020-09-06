package com.timgapps.warfare.Level.LevelMap.actions;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.timgapps.warfare.Level.GUI.Screens.reward_for_stars.RewardForStars;

public class AddUnitAction extends Group {
    Image image;
    public AddUnitAction(RewardForStars rewardForStars) {
        image = new Image();
        image = rewardForStars.getRewardImage();

    }
}

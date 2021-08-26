package com.timgapps.warfare.screens.map.helper;

public class MapScreenHelper {
    private boolean isShowStarsPanelFinger;

    public MapScreenHelper() {

    }

    public void showStarsPanelFinger() {
        isShowStarsPanelFinger = true;
    }

    public void hideStarsPanelFinger() {
        isShowStarsPanelFinger = false;
    }

    public boolean isShowStarsPanelFinger() {
        return isShowStarsPanelFinger;
    }
}

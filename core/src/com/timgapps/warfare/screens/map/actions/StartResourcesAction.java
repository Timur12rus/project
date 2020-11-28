package com.timgapps.warfare.screens.map.actions;

public interface StartResourcesAction {
    void startResourcesAction(int resourcesCount);

    boolean isEndResourcesAction();

    void setEndResourcesAction();
}

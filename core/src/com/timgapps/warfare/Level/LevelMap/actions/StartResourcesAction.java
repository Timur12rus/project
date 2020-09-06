package com.timgapps.warfare.Level.LevelMap.actions;

public interface StartResourcesAction {
    void startResourcesAction(int resourcesCount);

    boolean isEndResourcesAction();

    void setEndResourcesAction();
}

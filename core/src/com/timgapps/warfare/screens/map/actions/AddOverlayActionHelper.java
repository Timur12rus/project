package com.timgapps.warfare.screens.map.actions;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface AddOverlayActionHelper {
    void addChildOnOverlay(Actor actor);

    void startIconAction();

    void removeChildOnOverlay(Actor actor);

    void removeAllActionImages();

}

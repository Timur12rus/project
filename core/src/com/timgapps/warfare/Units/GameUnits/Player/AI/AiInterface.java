package com.timgapps.warfare.Units.GameUnits.Player.AI;

import com.timgapps.warfare.Units.GameUnits.GameUnitView;

public interface AiInterface<T extends GameUnitView> {
    void stay();

    void move();

    void die();

}

package com.timgapps.warfare.Units.GameUnits.Player.AI;

import com.timgapps.warfare.Units.GameUnits.GameUnit;

public interface AiInterface<T extends GameUnit> {
    void stay();

    void move();

    void die();

}

package com.timgapps.warfare.Utils.Helper;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface HelpInterface {
    void showBravery();     // показывает подсказку "очки храбрости"

    void hideBravery(Actor actor);     // закрывает подсказку "очки храбрости"

    void showCreateUnit();  // показывает подсказку "создать юнита" (стрелка с кнопкой)

    void hideCreateUnit();  // скрывает подсказку "создать юнита" (стрелка с кнопкой)

    void showCreateStone();  // показывает подсказку "передвинь камень на вражеского юнита"

    void hideCreateStone();  // скрывает подсказку "передвинь камень на вражеского юнита"

    void showFinalWave();    // показывает подсказку финальная волна


}

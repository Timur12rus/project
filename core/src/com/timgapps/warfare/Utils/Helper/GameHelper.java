package com.timgapps.warfare.Utils.Helper;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.screens.level.LevelScreen;

// класс помощ в игре
public class GameHelper implements HelpInterface {
    public static final int BRAVERY = 0;
    public static final int HELP_UNIT_CREATE = 1;
    public static final int HELP_STARS_PANEL = 2;
    public static final int HELP_TEAM_UPGRADE = 3;
    public static final int HELP_GET_GIFT = 4;
    public static final int FINAL_WAVE_MONSTERS = 5;        // финальная волна монстров
    public static final int NONE = 6;
    private int status = BRAVERY;
    private GameManager gameManager;
    private LevelScreen levelScreen;
    private boolean isShowBravey;
    private boolean isShowMassage;
    private boolean isShowStarsPanelFinger;
    private CreateUnitHelper createUnitHelper;
    private boolean isShowHelpCreateUnit;

    public GameHelper(LevelScreen levelScreen, GameManager gameManager) {
        this.levelScreen = levelScreen;
        this.gameManager = gameManager;
        status = getHelperStatus();
    }

    // возвращает сохраненный текущий статус
    private int getHelperStatus() {
        return gameManager.getHelpStatus();
    }

    public boolean isShowBravey() {
        return isShowBravey;
    }

    public boolean isShowMessage() {
        return isShowMassage;
    }

    // устанавливает следующий статус
    private void setNextStatus(int status) {
        gameManager.setHelpStatus(status);
    }

    // метод показывает подсказку "очки храбрости"
    @Override
    public void showBravery() {
        levelScreen.setState(LevelScreen.PAUSED);
        isShowMassage = true;       // флаг, сообщение показано
        isShowBravey = true;        // флаг, окно показано
        levelScreen.showFade();
        BraveryMessage braveryMessage = new BraveryMessage(levelScreen, this);
        levelScreen.addOverlayChild(braveryMessage);
        braveryMessage.setPosition((levelScreen.getWidth() - braveryMessage.getWidth()) / 2,
                (levelScreen.getHeight() - braveryMessage.getHeight()) / 2 + 64);
    }

    @Override
    public void hideBravery(Actor actor) {
        isShowMassage = false;       // флаг, сообщение показано
        levelScreen.hideFade();
        actor.remove();
//        levelScreen.removeOverlayChild(actor);
        levelScreen.setState(LevelScreen.PLAY);
        setNextStatus(HELP_UNIT_CREATE);
        showCreateUnit();
    }

    // метод показывает подсказку "создать юнита"
    @Override
    public void showCreateUnit() {
        System.out.println("Status Helper = " + status);
        if (createUnitHelper == null) {
            System.out.println("Create UnitHelper()");
            createUnitHelper = new CreateUnitHelper(levelScreen);
        }
        createUnitHelper.showFinger();
    }

    // удаляет подсказку о создании юнита (подсказка "палец")
    public void clearCreateUnit() {
        createUnitHelper.clear();
    }

    @Override
    public void hideCreateUnit() {
        if (gameManager.getHelpStatus() == HELP_UNIT_CREATE) {
            createUnitHelper.hideFinger();
        }
    }

    // метод показывает подсказку "передвинь камень на вражеского юнита"
    @Override
    public void showCreateStone() {

    }

    @Override
    public void hideCreateStone() {

    }

    // метод показывает подсказку "финальная волна"
    @Override
    public void showFinalWave() {

    }

    public void showStarsPanelFinger() {
        isShowStarsPanelFinger = true;
    }

    public boolean isShowStarsPanelFinger() {
        return isShowStarsPanelFinger;
    }
}

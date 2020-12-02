package com.timgapps.warfare.screens.get_reward_screen;

import com.badlogic.gdx.math.Vector2;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.screens.reward_for_stars.gui_elements.BackButton;
import com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData;
import com.timgapps.warfare.screens.reward_for_stars.interfaces.ScreenCloser;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;

public class GetRewardScreen extends StageGame implements ScreenCloser {
    private FlashEffect flashEffect;
    private BackButton backButton;
    public static final int ON_BACK = 1;
    private GameManager gameManager;
    private int indexOfReward = 0;

    public GetRewardScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        for (int i = 0; i < gameManager.getRewardForStarsDataList().size() - 1; i++) {
            if (gameManager.getStarsCount() >= gameManager.getRewardForStarsDataList().get(i).getStarsCount() &&
                    !gameManager.getRewardForStarsDataList().get(i).getIsReceived()) {     // если звезд больше, чем нужно для награды
                getRewardForStars(gameManager.getRewardForStarsDataList().get(i));
                gameManager.getRewardForStarsDataList().get(i).setReceived();
                indexOfReward = i;
            }
            gameManager.saveGame();
        }

        indexOfReward = 2;
//        if (indexOfReward != 2) {
//            flashEffect = new FlashEffect(this, gameManager.getRewardForStarsDataList().get(indexOfReward),
//                    new Vector2(getWidth() / 2, getHeight() / 2));
//        } else {
//
//        }
        backButton = new BackButton(this);
        backButton.setPosition(64, 64);
        addOverlayChild(backButton);
    }

    public void getRewardForStars(com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData data) {
        switch (data.getTypeOfReward()) {
            case com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData.REWARD_STONE:                           // если награда "КАМЕНЬ"
                for (int i = 0; i < gameManager.getCollection().size(); i++) {
                    if (gameManager.getCollection().get(i).getUnitId() == PlayerUnits.Rock) {
                        addRewardUnitToTeam(i);
                    }
                }
                break;
            case com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData.REWARD_ARCHER:
                for (int i = 0; i < gameManager.getCollection().size(); i++) {
                    if (gameManager.getCollection().get(i).getUnitId() == PlayerUnits.Archer) {
                        addRewardUnitToTeam(i);
                    }
                }
                break;
            case com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData.REWARD_BOX:
                gameManager.addCoinsCount(100);
                break;
            case com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData.REWARD_GNOME:
                for (int i = 0; i < gameManager.getCollection().size(); i++) {
                    if (gameManager.getCollection().get(i).getUnitId() == PlayerUnits.Gnome) {
                        addRewardUnitToTeam(i);
                    }
                }
                break;
            case RewardForStarsData.REWARD_KNIGHT:
                for (int i = 0; i < gameManager.getCollection().size(); i++) {
                    if (gameManager.getCollection().get(i).getUnitId() == PlayerUnits.Knight) {
                        addRewardUnitToTeam(i);
                    }
                }
                break;
        }
    }

    /**
     * метод добавляет полученного юнита в команду
     **/
    private void addRewardUnitToTeam(int i) {
//        unlockRewardForStars(i);                                               // разблокируем награду
        gameManager.getCollection().get(i).getUnitData().setUnlock();     // снимаем блокировку юнита
        gameManager.getCollection().get(i).getUnitImageButton().unlock();
        gameManager.getCollection().get(i).getUnitImageButton().redraw();

        if (gameManager.getTeam().size() < 5) {
            // добавим полученный юнит в команду
            gameManager.getTeam().add(gameManager.getCollection().get(i));  // добавляем в команду полученный юнит из коллекции
            gameManager.getSavedGame().getTeamDataList().add(gameManager.getSavedGame().getCollectionDataList().get(i));

            // удалим юнит из коллекции
            gameManager.getCollection().remove(i);
            gameManager.getSavedGame().getCollectionDataList().remove(i);
        } else {
            // TODO сделать чтобы юнит добавлялся в коллекцию, если в команде больше 5 юнитов
//            //
//            gameManager.getTeam().add(gameManager.getCollection().get(i));  // добавляем в команду полученный юнит из коллекции
//            gameManager.getSavedGame().getTeamDataList().add(gameManager.getSavedGame().getCollectionDataList().get(i));
        }
    }

    @Override
    public void show() {
        super.show();
        if (indexOfReward != 2) {
            flashEffect = new FlashEffect(this, gameManager.getRewardForStarsDataList().get(indexOfReward),
                    new Vector2(getWidth() / 2, getHeight() / 2));
            flashEffect.start();
        } else {
            flashEffect = new BoxFlashEffect(this, gameManager.getRewardForStarsDataList().get(indexOfReward),
                    new Vector2(getWidth() / 2, getHeight() / 2));
            flashEffect.start();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        flashEffect.clear();
    }

    @Override
    public void hide() {
        super.hide();
        dispose();
    }

    @Override
    public void closeScreen() {
//        hide();
        call(ON_BACK);
    }
}

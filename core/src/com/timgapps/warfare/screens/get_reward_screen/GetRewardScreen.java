package com.timgapps.warfare.screens.get_reward_screen;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.screens.map.gui_elements.CoinsPanel;
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
    private CoinsPanel coinsPanel;

    public GetRewardScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        coinsPanel = gameManager.getCoinsPanel();
        coinsPanel.redraw();

        // TODO
//
//        for (int i = 0; i < gameManager.getRewardForStarsDataList().size() - 1; i++) {
//            if (gameManager.getStarsCount() >= gameManager.getRewardForStarsDataList().get(i).getStarsCount() &&
//                    !gameManager.getRewardForStarsDataList().get(i).isReceived()) {     // если звезд больше, чем нужно для награды
//                getRewardForStars(gameManager.getRewardForStarsDataList().get(i));
//                gameManager.getRewardForStarsDataList().get(i).setReceived();
//                indexOfReward = i;
////                System.out.println("Index of reward in GetRewardScreen = " + indexOfReward);
//            }
//        }

        int indexOfRewardStars = 0;
        int index = 0;
        while (index <= gameManager.getRewardForStarsDataList().size() - 1) {
            // если текущее кол-во звезд >= кол-ва звезд награды с текущим индексом
            if (gameManager.getStarsCount() >= gameManager.getRewardForStarsDataList().get(indexOfRewardStars).getStarsCount()) {
                gameManager.getRewardForStarsDataList().get(indexOfRewardStars).setReceived();
                indexOfReward = indexOfRewardStars;
                getRewardForStars(gameManager.getRewardForStarsDataList().get(indexOfReward));
                indexOfRewardStars++;
                System.out.println("indexOfReward!!! = " + indexOfRewardStars);
            }
            index++;
        }
        int indexLastReward = gameManager.getRewardForStarsDataList().size() - 1;
        if (gameManager.getStarsCount() >= gameManager.getRewardForStarsDataList().get(indexLastReward).getStarsCount()) {
            indexOfReward = indexLastReward;
//            gameManager.isHaveFullRewardsForStars();
        }
        gameManager.saveGame();
        backButton = new BackButton(this);
        backButton.setPosition(64, 64);
        addOverlayChild(backButton);
    }

    // метод для получениия награды за звезды
    public void getRewardForStars(RewardForStarsData data) {
        switch (data.getTypeOfReward()) {
            case RewardForStarsData.REWARD_STONE:                           // если награда "КАМЕНЬ"
                for (int i = 0; i < gameManager.getCollection().size(); i++) {
                    if (gameManager.getCollection().get(i).getUnitId() == PlayerUnits.Rock) {
                        addRewardUnitToTeam(i);
                    }
                }
                break;
            case RewardForStarsData.REWARD_ARCHER:
                for (int i = 0; i < gameManager.getCollection().size(); i++) {
                    if (gameManager.getCollection().get(i).getUnitId() == PlayerUnits.Archer) {
                        addRewardUnitToTeam(i);
                    }
                }
                break;
            case RewardForStarsData.REWARD_BOX:
                gameManager.addCoinsCount(100);
                gameManager.addFoodCount(2);
                gameManager.addIronCount(2);
                gameManager.addWoodCount(2);
                gameManager.checkCanUpgrade();      // проверим, возможен ли апгрейд какого-либо юнита
                break;
            case RewardForStarsData.REWARD_FIREBOOSTER:
                gameManager.activateFireBooster();          // делаем активной "огненую атаку"
                break;
            case RewardForStarsData.REWARD_GNOME:
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
            case RewardForStarsData.REWARD_SHOOTER:
                for (int i = 0; i < gameManager.getCollection().size(); i++) {
                    if (gameManager.getCollection().get(i).getUnitId() == PlayerUnits.Shooter) {
                        addRewardUnitToTeam(i);
                    }
                }
                break;
            case RewardForStarsData.REWARD_VIKING:
                for (int i = 0; i < gameManager.getCollection().size(); i++) {
                    if (gameManager.getCollection().get(i).getUnitId() == PlayerUnits.Viking) {
                        addRewardUnitToTeam(i);
                    }
                }
                break;
        }
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        if (flashEffect.isEnd) {
            flashEffect.setIsEnd(false);
            backButton.addAction(Actions.fadeIn(0.7f));
        }
    }

    /**
     * метод добавляет полученного юнита в команду
     **/
    private void addRewardUnitToTeam(int i) {
//        unlockRewardForStars(i);                                               // разблокируем награду
        gameManager.getCollection().get(i).getUnitData().setUnlock();            // снимаем блокировку юнита
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
//            gameManager.getCollection().get(i).getUnitData().setUnlock();
//            gameManager.getTeam().add(gameManager.getCollection().get(i));  // добавляем в команду полученный юнит из коллекции
//            gameManager.getSavedGame().getTeamDataList().add(gameManager.getSavedGame().getCollectionDataList().get(i));
        }
    }

    @Override
    public void show() {
        super.show();
        backButton.addAction(Actions.fadeOut(0));
        if (gameManager.getRewardForStarsDataList().get(indexOfReward).getTypeOfReward() != RewardForStarsData.REWARD_BOX) {
//        if (indexOfReward != 2) {
            flashEffect = new FlashEffect(this, gameManager, indexOfReward,
                    new Vector2(getWidth() / 2, getHeight() / 2));
            flashEffect.start();
            System.out.println("Index of reward in GetRewardScreen = " + indexOfReward);
        } else {
            flashEffect = new BoxFlashEffect(this, gameManager, indexOfReward,
                    new Vector2(getWidth() / 2, getHeight() / 2));
            flashEffect.start();
        }
        coinsPanel.setPosition(getWidth() - coinsPanel.getWidth() - 32, getHeight() - coinsPanel.getHeight() - 32);
        addChild(coinsPanel);
    }

    @Override
    public void dispose() {
        super.dispose();
        flashEffect.clear();
        coinsPanel.remove();
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

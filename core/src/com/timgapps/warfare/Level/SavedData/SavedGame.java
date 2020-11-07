package com.timgapps.warfare.Level.SavedData;

import com.timgapps.warfare.Level.GUI.team_unit.TeamUnit;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.Level.LevelMap.LevelIcon;
import com.timgapps.warfare.Level.LevelMap.LevelIconData;
import com.timgapps.warfare.Level.GUI.Screens.reward_for_stars.RewardForStarsData;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;

import java.io.Serializable;
import java.util.ArrayList;

public class SavedGame implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<PlayerUnitData> teamDataList;         // массив данных юнитов из команды
    private ArrayList<PlayerUnitData> collectionDataList;     // массив данных юнитов из коллекции
    private ArrayList<LevelIconData> levelIconDataList;
    private ArrayList<RewardForStarsData> rewardForStarsDataList;

    private int foodCount;
    private int ironCount;
    private int woodCount;
    private int coinsCount;
    private int scoreCount;
    private int starsCount;
    private long giftTimeFirst;      // время необходимое для получения первого подарка (время которое нужно подождать)
    private long giftTimeSecond;      // время необходимое для получения второго подарка (время которое нужно подождать)
    private int indexOfRewardStars;
    private int rewardStarsCount;   // кол-во звезд для следующей награды за звезды
    private int helpStatus;         // статус обучения


    public SavedGame() {
//    public SavedGame(ArrayList<TeamEntity> team) {
//        this.team = team;
    }

    /**
     * метод создает массив "ДАННЫХ" уровней (LevelIconData)
     * в них содержатся данные каждого уровня: кол-во звезд за уровень, номер уровня, награда, пройден или нет
     */
    public void createLevelIconDataList() {
        levelIconDataList = new ArrayList<LevelIconData>();
        levelIconDataList.add(new LevelIconData(1, 15, 10, LevelIcon.EASY, true, false));
        levelIconDataList.add(new LevelIconData(2, 20, 30, LevelIcon.EASY, true, false));
        levelIconDataList.add(new LevelIconData(3, 10, 20, LevelIcon.EASY, true, false));
        levelIconDataList.add(new LevelIconData(4, 25, 30, LevelIcon.MEDIUM, true, false));
        levelIconDataList.add(new LevelIconData(5, 15, 10, LevelIcon.MEDIUM, true, false));
        levelIconDataList.add(new LevelIconData(6, 10, 20, LevelIcon.EASY, true, false));
        levelIconDataList.add(new LevelIconData(7, 15, 30, LevelIcon.MEDIUM, true, false));
        levelIconDataList.add(new LevelIconData(8, 15, 30, LevelIcon.MEDIUM, true, false));
        levelIconDataList.add(new LevelIconData(9, 15, 30, LevelIcon.MEDIUM, true, false));
        levelIconDataList.add(new LevelIconData(10, 15, 30, LevelIcon.MEDIUM, true, false));
        levelIconDataList.add(new LevelIconData(11, 15, 30, LevelIcon.MEDIUM, true, false));
        levelIconDataList.add(new LevelIconData(12, 15, 30, LevelIcon.MEDIUM, true, false));
        levelIconDataList.add(new LevelIconData(13, 15, 30, LevelIcon.MEDIUM, true, false));
        levelIconDataList.add(new LevelIconData(14, 15, 30, LevelIcon.MEDIUM, true, false));
        levelIconDataList.add(new LevelIconData(15, 15, 30, LevelIcon.MEDIUM, true, false));
        levelIconDataList.add(new LevelIconData(16, 15, 30, LevelIcon.MEDIUM, true, false));
        levelIconDataList.add(new LevelIconData(17, 15, 30, LevelIcon.MEDIUM, true, false));
        levelIconDataList.add(new LevelIconData(18, 15, 30, LevelIcon.MEDIUM, true, false));
//        levelIconDataList.add(new LevelIconData(19, 15, 30, LevelIcon.MEDIUM, true, false));
    }

    /**
     * метод устанавливает статус обучалки
     **/
    public void setHelpStatus(int status) {
        helpStatus = status;
    }

    /**
     * метод получает статус обучалки
     **/
    public int getHelpStatus() {
        return helpStatus;
    }

    // TODO сделать данные о количестве звезд для достижения награды в rewardStarsData

    /**
     * метод для создания списка с ДАННЫМИ наград за звезды
     * в этом методе и задаются награды за звёзды
     **/
    public void createRewardForStarsDataList() {
//        starsCount = 35;             // кол-во звезд у игрока для теста
        starsCount = 0;             // кол-во звезд у игрока
        indexOfRewardStars = 0;     // индекс следующей награды за звёзды
        rewardForStarsDataList = new ArrayList<RewardForStarsData>();
        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_STONE, 1, "block1_image"));
        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_ARCHER, 4, "archerStay0"));
        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_BOX, 15, "boxImage0"));
        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_GNOME, 30, "gnomeStay0"));
        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_BOX, 45, "boxImage0"));
        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_KNIGHT, 75, "knightUnitImage"));
        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_KNIGHT, 110, "knightUnitImage"));
        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_KNIGHT, 50, "knightUnitImage"));
//        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_GNOME, 45));
//        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_GNOME, 80));
//        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_GNOME, 100));
    }

    /**
     * метод задаёт индекс (номер) следующей награды
     */
    public void setIndexRewardStars(int index) {
        indexOfRewardStars = index;
    }

    /**
     * метод плучает индекс (номер) следующей награды за звезды /
     ***/
    public int getIndexOfRewardStars() {
        return indexOfRewardStars;
    }

    /**
     * метод для получения списка ДАННЫХ наград за звёзды
     */
    public ArrayList<RewardForStarsData> getRewardForStarsDataList() {
        return rewardForStarsDataList;
    }

    public ArrayList<LevelIconData> getLevelIconDataList() {
        return levelIconDataList;
    }

    /**
     * метод создает массив "ДАННЫХ" юнитов игровой команды
     **/
    public void createTeamEntityDataList() {
        teamDataList = new ArrayList<PlayerUnitData>();
        teamDataList.add(new PlayerUnitData(PlayerUnits.Thor));
        teamDataList.get(0).setDefaultData(PlayerUnits.Thor);

        // это для теста
//        teamDataList.add(new PlayerUnitData(PlayerUnits.Gnome));
//        teamDataList.get(1).setDefaultData(PlayerUnits.Gnome);
//        teamDataList.add(new PlayerUnitData(PlayerUnits.Knight));
//        teamDataList.get(2).setDefaultData(PlayerUnits.Knight);
//        teamDataList.add(new PlayerUnitData(PlayerUnits.Archer));
//        teamDataList.get(3).setDefaultData(PlayerUnits.Archer);
//
//        teamDataList.add(new PlayerUnitData(PlayerUnits.Barbarian));
//        teamDataList.get(4).setDefaultData(PlayerUnits.Barbarian);
//
//        teamDataList.add(new PlayerUnitData(PlayerUnits.Viking));
//        teamDataList.get(5).setDefaultData(PlayerUnits.Viking);
//
//        teamDataList.add(new PlayerUnitData(PlayerUnits.Shooter));
//        teamDataList.get(6).setDefaultData(PlayerUnits.Shooter);
    }

    /**
     * метод создает массив "ДАННЫХ" юнитов коллекции
     **/
    public void createCollectionDataList() {
        collectionDataList = new ArrayList<PlayerUnitData>();
        collectionDataList.add(new PlayerUnitData(PlayerUnits.Rock));
        collectionDataList.add(new PlayerUnitData(PlayerUnits.Archer));
        collectionDataList.add(new PlayerUnitData(PlayerUnits.Gnome));
        collectionDataList.add(new PlayerUnitData(PlayerUnits.Knight));

        collectionDataList.add(new PlayerUnitData(PlayerUnits.Barbarian));
        collectionDataList.add(new PlayerUnitData(PlayerUnits.Viking));
        collectionDataList.add(new PlayerUnitData(PlayerUnits.Shooter));


        for (PlayerUnitData collectionUnitData : collectionDataList) {
            collectionUnitData.setDefaultData(collectionUnitData.getUnitId());
        }
    }

    public ArrayList<PlayerUnitData> getCollectionDataList() {
        return collectionDataList;
    }

    public ArrayList<PlayerUnitData> getTeamDataList() {
        return teamDataList;
    }

    public void setTeam(ArrayList<PlayerUnitData> teamDataList) {
        this.teamDataList = teamDataList;
    }

    public void updateResourcesCount(int foodCount, int ironCount, int woodCount) {
        this.foodCount = foodCount;
        this.ironCount = ironCount;
        this.woodCount = woodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public void setIronCount(int ironCount) {
        this.ironCount = ironCount;
    }

    public void setWoodCount(int woodCount) {
        this.woodCount = woodCount;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public int getIronCount() {
        return ironCount;
    }

    public int getWoodCount() {
        return woodCount;
    }

    public void setCoinsCount(int coinsCount) {
        this.coinsCount = coinsCount;
    }

    public void addCoinsCount(int count) {
        this.coinsCount += count;
    }

    public int getCoinsCount() {
        return coinsCount;
    }

    public void setScoreCount(int scoreCount) {
        this.scoreCount = scoreCount;
    }

    public int getScoreCount() {
        return scoreCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void addStarsCount(int count) {
        starsCount += count;
    }

    public void updateTeamDataList(ArrayList<TeamUnit> team) {
        for (int i = 0; i < team.size(); i++) {
            if (i > teamDataList.size() - 1) {
                teamDataList.add(team.get(i).getUnitData());
            } else
                teamDataList.set(i, team.get(i).getUnitData());
        }
    }

    public void updateCollectionDataList(ArrayList<TeamUnit> collection) {
        collectionDataList.clear();
        for (int i = 0; i < collection.size(); i++) {
            collectionDataList.add(collection.get(i).getUnitData());
        }
        int i = 0;
        System.out.println("updateCollectionDataList()!!!!!!!!!!!!!!!!!!!!!!");
        for (TeamUnit collectionUnit : collection) {
            System.out.println("collectionUnit[" + i + "] = " + collectionUnit.getUnitData().getUnitId().toString());
            System.out.println("collectionUnitData[" + i + "] = " + collectionDataList.get(i).getUnitId().toString());
        }
    }

    public void setGiftTimeFirst(long giftTimeFirst) {
        this.giftTimeFirst = giftTimeFirst;
    }

    public void setGiftTimeSecond(long giftTimeSecond) {
        this.giftTimeSecond = giftTimeSecond;
    }

    public long getGiftTimeFirst() {
        return giftTimeFirst;
    }

    public long getGiftTimeSecond() {
        return giftTimeSecond;
    }
}

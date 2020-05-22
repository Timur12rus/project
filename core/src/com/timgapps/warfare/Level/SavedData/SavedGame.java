package com.timgapps.warfare.Level.SavedData;

import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntityData;
import com.timgapps.warfare.Level.LevelMap.LevelIcon;
import com.timgapps.warfare.Level.LevelMap.LevelIconData;
import com.timgapps.warfare.Level.GUI.Screens.RewardForStars.RewardForStarsData;

import java.io.Serializable;
import java.util.ArrayList;

public class SavedGame implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<TeamEntityData> teamDataList;
    private ArrayList<TeamEntityData> collectionDataList;
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
        levelIconDataList.add(new LevelIconData(1, 15, 10, LevelIcon.EASY, true));
        levelIconDataList.add(new LevelIconData(2, 20, 30, LevelIcon.EASY, false));
        levelIconDataList.add(new LevelIconData(3, 10, 20, LevelIcon.EASY, false));
        levelIconDataList.add(new LevelIconData(4, 25, 30, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(5, 15, 10, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(6, 10, 20, LevelIcon.EASY, false));
        levelIconDataList.add(new LevelIconData(7, 15, 30, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(8, 15, 30, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(9, 15, 30, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(10, 15, 30, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(11, 15, 30, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(12, 15, 30, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(13, 15, 30, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(14, 15, 30, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(15, 15, 30, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(16, 15, 30, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(17, 15, 30, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(18, 15, 30, LevelIcon.MEDIUM, false));
        levelIconDataList.add(new LevelIconData(19, 15, 30, LevelIcon.MEDIUM, false));
    }

    /**
     * метод для создания списка с ДАННЫМИ наград за звезды
     **/
    public void createRewardForStarsDataList() {
        starsCount = 0;             // кол-во звезд у игрока
        indexOfRewardStars = 0;     // индекс следующей награды за звёзды
        rewardForStarsDataList = new ArrayList<RewardForStarsData>();
        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_STONE, 1));
        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_ARCHER, 4));
        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_BOX, 15));
        rewardForStarsDataList.add(new RewardForStarsData(RewardForStarsData.REWARD_GNOME, 30));
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
        teamDataList = new ArrayList<TeamEntityData>();

        teamDataList.add(new TeamEntityData(TeamEntityData.THOR));
        teamDataList.get(0).setDefaultData(TeamEntityData.THOR);
//        teamDataList.add(new TeamEntityData(TeamEntityData.STONE));
//        teamDataList.get(1).setDefaultData(TeamEntityData.STONE);
//        teamDataList.add(new TeamEntityData(TeamEntityData.ARCHER));
//        teamDataList.get(2).setDefaultData(TeamEntityData.ARCHER);
//        teamDataList.add(new TeamEntityData(TeamEntityData.STONE));
//        teamDataList.get(2).setDefaultData(TeamEntityData.STONE);
    }

    /**
     * метод создает массив "ДАННЫХ" юнитов коллекции
     **/
    public void createCollectionDataList() {
        collectionDataList = new ArrayList<TeamEntityData>();

        collectionDataList.add(new TeamEntityData(TeamEntityData.STONE));
        collectionDataList.get(0).setDefaultData(TeamEntityData.STONE);
        collectionDataList.add(new TeamEntityData(TeamEntityData.ARCHER));
        collectionDataList.get(1).setDefaultData(TeamEntityData.ARCHER);
        collectionDataList.add(new TeamEntityData(TeamEntityData.GNOME));
        collectionDataList.get(2).setDefaultData(TeamEntityData.GNOME);
    }

    public ArrayList<TeamEntityData> getCollectionDataList() {
        return collectionDataList;
    }

    public ArrayList<TeamEntityData> getTeamDataList() {
        return teamDataList;
    }

    public void setTeam(ArrayList<TeamEntityData> teamDataList) {
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

    public void updateTeamDataList(ArrayList<TeamEntity> team) {
        for (int i = 0; i < team.size(); i++) {
            if (i > teamDataList.size() - 1) {
                teamDataList.add(team.get(i).getEntityData());
            } else
                teamDataList.set(i, team.get(i).getEntityData());
//           teamDataList.get(i) = (TeamEntityData)team.get(i).getEntityData();
        }
    }

    public void updateCollectionDataList(ArrayList<TeamEntity> collection) {
        for (int i = 0; i < collection.size(); i++) {
            collectionDataList.clear();
            collectionDataList.add(collection.get(i).getEntityData());
//            collectionDataList.set(i, collection.get(i).getEntityData());
//           teamDataList.get(i) = (TeamEntityData)team.get(i).getEntityData();
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

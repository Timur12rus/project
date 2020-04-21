package com.timgapps.warfare.Level.SavedData;

import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntityData;
import com.timgapps.warfare.Level.LevelMap.LevelIconData;

import java.io.Serializable;
import java.util.ArrayList;

public class SavedGame implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<TeamEntityData> teamDataList;
    private ArrayList<TeamEntityData> collectionDataList;
    private ArrayList<LevelIconData> levelIconDataList;
    private int foodCount;
    private int ironCount;
    private int woodCount;
    private int coinsCount;
    private int scoreCount;


    public SavedGame() {
//    public SavedGame(ArrayList<TeamEntity> team) {
//        this.team = team;
    }

    /** метод создает массив "ДАННЫХ" уровней (LevelIconData)
     * в них содержатся данные каждого уровня: кол-во звезд за уровень, номер уровня, награда, пройден или нет
     */
    public void createLevelIconDataList() {
        levelIconDataList = new ArrayList<LevelIconData>();

    }

    /** метод создает массив "ДАННЫХ" юнитов игровой команды **/
    public void createTeamEntityDataList() {
        teamDataList = new ArrayList<TeamEntityData>();
        teamDataList.add(new TeamEntityData(TeamEntityData.THOR));
        teamDataList.get(0).setDefaultData(TeamEntityData.THOR);
        teamDataList.add(new TeamEntityData(TeamEntityData.ARCHER));
        teamDataList.get(1).setDefaultData(TeamEntityData.ARCHER);
        teamDataList.add(new TeamEntityData(TeamEntityData.STONE));
        teamDataList.get(2).setDefaultData(TeamEntityData.STONE);
    }

    /** метод создает массив "ДАННЫХ" юнитов коллекции **/
    public void createCollectionDataList() {
        collectionDataList = new ArrayList<TeamEntityData>();
        collectionDataList.add(new TeamEntityData(TeamEntityData.GNOME));
        collectionDataList.get(0).setDefaultData(TeamEntityData.GNOME);
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

    public int getCoinsCount() {
        return coinsCount;
    }

    public void setScoreCount(int scoreCount) {
        this.scoreCount = scoreCount;
    }

    public int getScoreCount() {
        return scoreCount;
    }

    public void updateTeamDataList(ArrayList<TeamEntity> team) {
        for (int i = 0; i < team.size(); i++) {
            teamDataList.set(i, team.get(i).getEntityData());
//           teamDataList.get(i) = (TeamEntityData)team.get(i).getEntityData();
        }
    }

    public void updateCollectionDataList(ArrayList<TeamEntity> collection) {
        for (int i = 0; i < collection.size(); i++) {
            collectionDataList.set(i, collection.get(i).getEntityData());
//           teamDataList.get(i) = (TeamEntityData)team.get(i).getEntityData();
        }
    }
}

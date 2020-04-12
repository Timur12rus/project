package com.timgapps.warfare.Level.SavedData;

import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntityData;

import java.io.Serializable;
import java.util.ArrayList;

public class SavedGame implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<TeamEntityData> teamDataList;
    private ArrayList<TeamEntityData> collectionDataList;

    public SavedGame() {
//    public SavedGame(ArrayList<TeamEntity> team) {
//        this.team = team;
    }

    public void createTeamEntityDataList() {
        teamDataList = new ArrayList<TeamEntityData>();
        teamDataList.add(new TeamEntityData(TeamEntityData.THOR));
        teamDataList.get(0).setDefaultData(TeamEntityData.THOR);
        teamDataList.add(new TeamEntityData(TeamEntityData.ARCHER));
        teamDataList.get(1).setDefaultData(TeamEntityData.ARCHER);
        teamDataList.add(new TeamEntityData(TeamEntityData.STONE));
        teamDataList.get(2).setDefaultData(TeamEntityData.STONE);
    }

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

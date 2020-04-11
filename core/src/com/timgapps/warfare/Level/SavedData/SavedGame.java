package com.timgapps.warfare.Level.SavedData;

import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;

import java.io.Serializable;
import java.util.ArrayList;

public class SavedGame implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<TeamEntity> team;

    public SavedGame() {
//    public SavedGame(ArrayList<TeamEntity> team) {
//        this.team = team;
    }

    public ArrayList<TeamEntity> getTeam() {
        return team;
    }

    public void setTeam(ArrayList<TeamEntity> team) {
        this.team = team;
    }
}

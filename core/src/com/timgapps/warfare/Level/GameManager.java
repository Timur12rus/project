package com.timgapps.warfare.Level;

import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;
import com.timgapps.warfare.Units.GameUnits.Player.PlayerUnit;

import java.util.ArrayList;

/**
 * класс в котором будут содержаться все необходимые данные для игры:
 *
 * @currentLevelId - текущий уровень
 * @team - текущий состав команды
 */
public class GameManager {
    private ArrayList<TeamEntity> team;     // команда, массив объектов TeamEntity()
    private int currentLevelId;

    public GameManager() {
        team = new ArrayList<TeamEntity>();


        /** Добавляем бойцов в команду **/
        team.add(new TeamEntity(TeamEntity.GNOME));
        team.add(new TeamEntity(TeamEntity.ARCHER));
        team.add(new TeamEntity(TeamEntity.STONE));
        team.add(new TeamEntity(TeamEntity.NONE));
        team.add(new TeamEntity(TeamEntity.NONE));
    }

    public void addTeamEntity(int unitType) {
        team.add(new TeamEntity(unitType));
    }

    public ArrayList<TeamEntity> getTeam() {
        return team;
    }
}

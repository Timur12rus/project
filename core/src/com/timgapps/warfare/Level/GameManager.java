package com.timgapps.warfare.Level;

import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;

import java.util.ArrayList;

/** класс в котором будут содержаться все необходимые данные для игры:
 * @currentLevelId - текущий уровень
 * @team - текущий состав команды
 *
 */
public class GameManager {
    private ArrayList<TeamEntity> team;     // команда, массив объектов TeamEntity()
    private int currentLevelId;

    public GameManager() {
        team = new ArrayList<TeamEntity>();
        team.add(new TeamEntity(TeamEntity.GNOME));
    }

    public void addTeamEntity(int unitType) {
        team.add(new TeamEntity(unitType));

    }
}

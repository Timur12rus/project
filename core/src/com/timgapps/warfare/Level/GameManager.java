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

    private int coinsCount;

    private int foodCount;      // количество пищи
    private int ironCount;      // количестов железа
    private int woodCount;      // количество дерева

    public GameManager() {
        team = new ArrayList<TeamEntity>();

        /** количество монет у игрока **/
        coinsCount = 6000;

        foodCount = 10;
        ironCount = 5;
        woodCount = 4;

        /** Добавляем бойцов в команду **/
        team.add(new TeamEntity(TeamEntity.GNOME));
        team.add(new TeamEntity(TeamEntity.ARCHER));
        team.add(new TeamEntity(TeamEntity.STONE));
        team.add(new TeamEntity(TeamEntity.NONE));
        team.add(new TeamEntity(TeamEntity.NONE));
    }

    /**
     * метод для добавления количества пищи к общему количеству
     *
     * @param foodCount - количество пищи, которое добавим
     **/
    public void addFoodCount(int foodCount) {
        this.foodCount += foodCount;
    }


    /**
     * метод для добавления количества пищи к общему количеству
     *
     * @param ironCount - количество железа, которое добавим
     **/
    public void addIronCount(int ironCount) {
        this.ironCount += ironCount;
    }


    /**
     * метод для добавления количества пищи к общему количеству
     *
     * @param woodCount - количество  дерева, которое добавим
     **/
    public void addWoodCount(int woodCount) {
        this.woodCount += woodCount;
    }

    /**
     * метод для получения количства пищи
     **/
    public int getFoodCount() {
        return foodCount;
    }

    /**
     * метод для получения количства железа
     **/
    public int getIronCount() {
        return ironCount;
    }

    /**
     * метод для получения количства дерева
     **/
    public int getWoodCount() {
        return woodCount;
    }

    public void addTeamEntity(int unitType) {
        team.add(new TeamEntity(unitType));
    }


    /**
     * метод для получения команды игрока
     **/
    public ArrayList<TeamEntity> getTeam() {
        return team;
    }


    /**
     * метод для получения количества монет
     *
     * @return coinsCount
     */
    public int getCoinsCount() {
        return coinsCount;
    }
}

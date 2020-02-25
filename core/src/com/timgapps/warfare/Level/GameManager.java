package com.timgapps.warfare.Level;

import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;

import java.util.ArrayList;

/**
 * класс в котором будут содержаться все необходимые данные для игры:
 *
 * @currentLevelId - текущий уровень
 * @team - текущий состав команды
 */
public class GameManager {
    private ArrayList<TeamEntity> team;     // команда, массив объектов TeamEntity()
    private ArrayList<TeamEntity> collection;     // коллекция, массив объектов TeamEntity()
    private int currentLevelId;

    private int coinsCount;

    private int foodCount;      // количество пищи
    private int ironCount;      // количестов железа
    private int woodCount;      // количество дерева

    private float towerHealth;  // количество здоровья у ОСАДНОЙ БАШНИ

    private CoinsPanel coinsPanel;


    public GameManager() {
        /** создадим КОМАНДУ - массив юнитов в команде**/
        team = new ArrayList<TeamEntity>();

        /** создадим КОЛЛЕКЦИЮ - массив юнитов в коллекции  **/
        collection = new ArrayList<TeamEntity>();


        /** количество монет у игрока **/
        coinsCount = 1000;

        coinsPanel = new CoinsPanel(coinsCount);

        foodCount = 20;
        ironCount = 10;
        woodCount = 30;

        /** получи уровень здоровья ОСАДНОЙ БАШНИ **/
        towerHealth = 10;

        /** Добавляем бойцов в команду **/
        // TODO: 31.01.2020  Здесь нужно будет изменить код, так чтобы брать данные из сохранненного объекта
        team.add(new TeamEntity(TeamEntity.GNOME));
        team.add(new TeamEntity(TeamEntity.ARCHER));
        team.add(new TeamEntity(TeamEntity.STONE));
//        team.add(new TeamEntity(TeamEntity.NONE));
//        team.add(new TeamEntity(TeamEntity.NONE));

        /** Добавляем бойцов в коллекцию **/
        // TODO: 31.01.2020  Здесь нужно будет изменить код, так чтобы брать данные из сохранненного объекта
        collection.add(new TeamEntity(TeamEntity.THOR));
//        collection.add(new TeamEntity(TeamEntity.NONE));
//        collection.add(new TeamEntity(TeamEntity.NONE));
//
//
//        collection.add(new TeamEntity(TeamEntity.NONE));
//        collection.add(new TeamEntity(TeamEntity.NONE));
//        collection.add(new TeamEntity(TeamEntity.NONE));
//        collection.add(new TeamEntity(TeamEntity.NONE));
//
//        collection.add(new TeamEntity(TeamEntity.THOR));
//        collection.add(new TeamEntity(TeamEntity.NONE));
//        collection.add(new TeamEntity(TeamEntity.NONE));
//        collection.add(new TeamEntity(TeamEntity.NONE));
//        collection.add(new TeamEntity(TeamEntity.NONE));
//        collection.add(new TeamEntity(TeamEntity.NONE));
//        collection.add(new TeamEntity(TeamEntity.NONE));
    }

    /**
     * метод возвращает объект CoinsPanel
     **/
    public CoinsPanel getCoinsPanel() {
        return coinsPanel;
    }

    /**
     * метод добавляет количество монет к общему количеству монет
     *
     * @param coins - количество монет, на сколько прибавить
     **/
    public void addCoinsCount(int coins) {
        coinsCount += coins;
        coinsPanel.setCoinsCount(coinsCount);       // обновим количество монет в панели для монет (coinsPanel)
        // TODO: 29.01.2020 ЗДЕСЬ Нужно сохранять данные о количестве монет в файл

    }

    /**
     * метод возвращает общее количество монет
     **/
    public int getCoinsCount() {
        return coinsCount;
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

    /** метод для получения уровня здоровья ОСАДНОЙ БАШНИ **/
    public float getTowerHealth(){
        return towerHealth;
    }

    /**
     * метод для получения команды игрока
     **/
    public ArrayList<TeamEntity> getTeam() {
        return team;
    }

    /**
     * метод для получения коллекции юнитов игрока
     **/
    public ArrayList<TeamEntity> getCollection() {
        return collection;
    }

    /**
     * метод для обновления команды юнитов
     **/
    public void updateTeam() {

    }

    /**
     * метод для обновления коллекции юнитов
     **/
    public void updateCollection() {

    }
}

package com.timgapps.warfare.Level;

import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntityData;
import com.timgapps.warfare.Level.LevelMap.LevelIcon;
import com.timgapps.warfare.Level.SavedData.SavedGame;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * класс в котором будут содержаться все необходимые данные для игры:
 *
 * @currentLevelId - текущий уровень
 * @team - текущий состав команды
 */
public class GameManager {
    private ArrayList<TeamEntity> team;     // команда, массив объектов TeamEntity()
    private ArrayList<TeamEntityData> teamDataList;     // команда, массив объектов TeamEntity()
    private ArrayList<TeamEntityData> collectionDataList;     // команда, массив объектов TeamEntity()
    private ArrayList<TeamEntity> collection;     // коллекция, массив объектов TeamEntity()
    private int currentLevelId;

    private int coinsCount;

    private int foodCount;      // количество пищи
    private int ironCount;      // количестов железа
    private int woodCount;      // количество дерева

    private float towerHealth;  // количество здоровья у ОСАДНОЙ БАШНИ

    private CoinsPanel coinsPanel;

    private int scoreRewardforLevel = 0;
    private int coinsRewardForLevel = 0;

    private ArrayList<LevelIcon> levelIcons = new ArrayList<LevelIcon>();
    private SavedGame savedGame;

    public GameManager() {
        /** создадим массив уровней (LevelIcons) для хранения информации и данных уровней (кол-во звёзд, заблокировани или разблокирован **/

//        public LevelIcon(int id, int coinsCount, int scoreCount, String levelOfDifficulty, boolean isActive) {
        levelIcons.add(new LevelIcon(1, 15, 10, LevelIcon.EASY, true));
        levelIcons.add(new LevelIcon(2, 20, 30, LevelIcon.EASY, false));
        levelIcons.add(new LevelIcon(3, 10, 20, LevelIcon.EASY, false));
        levelIcons.add(new LevelIcon(4, 25, 30, LevelIcon.MEDIUM, false));
        levelIcons.add(new LevelIcon(5, 15, 10, LevelIcon.MEDIUM, false));
        levelIcons.add(new LevelIcon(6, 10, 20, LevelIcon.EASY, false));
        levelIcons.add(new LevelIcon(6, 15, 30, LevelIcon.MEDIUM, false));


        /** загрузим данные игры **/


        /** создадим КОМАНДУ - массив юнитов в команде**/

//        team = new ArrayList<TeamEntity>();

        // создаем объект для сохранения игры
        savedGame = getSavedGame();

        team = new ArrayList<TeamEntity>();
        collection = new ArrayList<TeamEntity>();

        // если сохранения нет(запускает игру впервый раз), null, то создадим объект для схранниения игры и создадим объект "КОМАНДА" team
        if (savedGame == null) {
            System.out.println("SavedGame null ");
            savedGame = new SavedGame();


            /** Добавляем бойцов в команду **/
            // TODO: 31.01.2020  Здесь нужно будет изменить код, так чтобы брать данные из сохранненного объекта
            savedGame.createTeamEntityDataList();
            savedGame.createCollectionDataList();

//            /** установим значения по умолчанию  для данных TeamEntity **/
//            savedGame.getTeamDataList().get(0).setDefaultData();

            team.add(new TeamEntity(savedGame.getTeamDataList().get(0)));
            team.add(new TeamEntity(savedGame.getTeamDataList().get(1)));
            team.add(new TeamEntity(savedGame.getTeamDataList().get(2)));

            /** создадим КОЛЛЕКЦИЮ - массив юнитов в коллекции  **/
            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(0)));
//            savedGame.setTeam(team);
        } else {
            team.add(new TeamEntity(savedGame.getTeamDataList().get(0)));
            team.add(new TeamEntity(savedGame.getTeamDataList().get(1)));
            team.add(new TeamEntity(savedGame.getTeamDataList().get(2)));

            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(0)));
        }
//        team = savedGame.getTeam();
//        if (team == null)
//            team = new ArrayList<TeamEntity>();
//
//        savedGame.setTeam(team);


        /** количество монет у игрока **/
        coinsCount = 1000;

        coinsPanel = new CoinsPanel(coinsCount);

        foodCount = 20;
        ironCount = 10;
        woodCount = 30;

        /** получи уровень здоровья ОСАДНОЙ БАШНИ **/
        towerHealth = 50;

//        /** Добавляем бойцов в команду **/
//        // TODO: 31.01.2020  Здесь нужно будет изменить код, так чтобы брать данные из сохранненного объекта
//        team.add(new TeamEntity(TeamEntity.GNOME));
//        team.add(new TeamEntity(TeamEntity.ARCHER));
//        team.add(new TeamEntity(TeamEntity.STONE));

        /** Добавляем бойцов в коллекцию **/
        // TODO: 31.01.2020  Здесь нужно будет изменить код, так чтобы брать данные из сохранненного объекта
//
//        collection.add(new TeamEntity(TeamEntity.THOR));
    }

    /**
     * метод для загрузки данных игры
     **/
    private SavedGame getSavedGame() {
        try {
            FileInputStream fileInputStream = new FileInputStream("save.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            savedGame = (SavedGame) objectInputStream.readObject();
            System.out.println("readObject = " + savedGame.toString());
        } catch (Exception e) {
            System.out.println("exception = " + e.toString());
            return null;
        }
        return savedGame;
    }

    /**
     * метод сохраняет данные игры
     **/
    public void saveGame() {
        try {
            //создаем 2 потока для сериализации объекта и сохранения его в файл
            FileOutputStream outputStream = new FileOutputStream("save.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            // сохраняем игру в файл
            objectOutputStream.writeObject(savedGame);

            //закрываем поток и освобождаем ресурсы
            objectOutputStream.close();
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    /**
     * метод возвращает массив уровней LevelIcons, в дальнейшем будем брать отдуда информацию о кол-ве звёзд и блокирован или нет
     **/
    public ArrayList<LevelIcon> getLevelIcons() {
        return levelIcons;
    }

    /**
     * метод устанавливает кол-во очков (награду за уровень)
     **/
    public void setScoreRewardforLevel(int score) {
        scoreRewardforLevel = score;
    }

    /**
     * метод устанавливает кол-во монет (награду за уровень)
     **/
    public void setCoinsRewardforLevel(int score) {
        coinsRewardForLevel = score;
    }


    /**
     * метод возвращает количество монет (награду за уровень)
     **/
    public int getCoinsRewardForLevel() {
        return coinsRewardForLevel;
    }

    /**
     * метод возвращает количество очков (награду за уровень)
     **/
    public int getScoreRewardForLevel() {
        return scoreRewardforLevel;
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

//    public void addTeamEntity(int unitType) {
//        team.add(new TeamEntity(unitType));
//    }

    /**
     * метод для получения уровня здоровья ОСАДНОЙ БАШНИ
     **/
    public float getTowerHealth() {
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
    public void updateTeam(ArrayList<TeamEntity> team) {
        this.team = team;
//        savedGame.setTeam(team);
        savedGame.updateTeamDataList(team);
//        saveGame();
    }

    /**
     * метод для обновления коллекции юнитов
     **/
    public void updateCollection(ArrayList<TeamEntity> collection) {
        this.collection = collection;
        savedGame.updateCollectionDataList(collection);
    }

}

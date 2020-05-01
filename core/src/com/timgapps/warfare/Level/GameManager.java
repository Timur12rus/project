package com.timgapps.warfare.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.boontaran.DataManager;
import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntityData;
import com.timgapps.warfare.Level.LevelMap.LevelIcon;
import com.timgapps.warfare.Level.GUI.Screens.RewardForStars.RewardForStarsData;
import com.timgapps.warfare.Level.LevelMap.ScorePanel;
import com.timgapps.warfare.Level.LevelMap.StarsPanel;
import com.timgapps.warfare.Level.SavedData.SavedGame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    private ArrayList<RewardForStarsData> rewardForStarsDataList;     // коллекция, массив объектов TeamEntity()
    private int currentLevelId;

    private int coinsCount;
    private int scoreCount;
    private int starsCount;

    private int foodCount;      // количество пищи
    private int ironCount;      // количестов железа
    private int woodCount;      // количество дерева

    private float towerHealth;  // количество здоровья у ОСАДНОЙ БАШНИ

    private CoinsPanel coinsPanel;
    protected ScorePanel scorePanel;
    protected StarsPanel starsPanel;

    private int scoreRewardforLevel = 0;
    private int coinsRewardForLevel = 0;

    private ArrayList<LevelIcon> levelIcons = new ArrayList<LevelIcon>();
    private SavedGame savedGame;

    private DataManager manager;

    private long giftTime;      // время до получения подарка
    private int indexOfRewardStars;

    public GameManager() {

        /** загрузим данные игры **/


        /** создадим КОМАНДУ - массив юнитов в команде**/

//        team = new ArrayList<TeamEntity>();

        // создаем объект для сохранения игры
        savedGame = loadSavedGame();

        /** создадим массив уровней (LevelIcons) для хранения информации и данных уровней (кол-во звёзд, заблокировани или разблокирован **/
        //TODO сделать сохранение и загрузку данных об уровнях в  массив <LevelIconData> в объекте сохранения игры savedGame()
//        public LevelIcon(int id, int coinsCount, int scoreCount, String levelOfDifficulty, boolean isActive) {
//        levelIcons.add(new LevelIcon(1, 15, 10, LevelIcon.EASY, true));
//        levelIcons.add(new LevelIcon(2, 20, 30, LevelIcon.EASY, false));
//        levelIcons.add(new LevelIcon(3, 10, 20, LevelIcon.EASY, false));
//        levelIcons.add(new LevelIcon(4, 25, 30, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(5, 15, 10, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(6, 10, 20, LevelIcon.EASY, false));
//        levelIcons.add(new LevelIcon(7, 15, 30, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(8, 15, 30, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(9, 15, 30, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(10, 15, 30, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(11, 15, 30, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(12, 15, 30, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(13, 15, 30, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(14, 15, 30, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(15, 15, 30, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(16, 15, 30, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(17, 15, 30, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(18, 15, 30, LevelIcon.MEDIUM, false));
//        levelIcons.add(new LevelIcon(19, 15, 30, LevelIcon.MEDIUM, false));

        team = new ArrayList<TeamEntity>();
        collection = new ArrayList<TeamEntity>();

        // если сохранения нет(запускает игру впервый раз), null, то создадим объект для схранниения игры и создадим объект "КОМАНДА" team
        if (savedGame == null) {
            System.out.println("SavedGame null ");
            savedGame = new SavedGame();

            /** создаем массив "ДАННЫХ" об уровнях (LevelIconData) **/
            savedGame.createLevelIconDataList();

            /** Добавляем бойцов в команду **/
            // TODO: 31.01.2020  Здесь нужно будет изменить код, так чтобы брать данные из сохранненного объекта
            savedGame.createTeamEntityDataList();
            savedGame.createCollectionDataList();

            /** создадим список с ДАННЫМИ наград за звезды **/
            savedGame.createRewardForStarsDataList();

            // установим кол-во ресурсов
            foodCount = 6;
            ironCount = 2;
            woodCount = 4;

            /** количество монет у игрока **/
            coinsCount = 100;
            scoreCount = 0;
            starsCount = 0;

            savedGame.setCoinsCount(coinsCount);
            savedGame.setFoodCount(foodCount);
            savedGame.setIronCount(ironCount);
            savedGame.setWoodCount(woodCount);


            /** создадим КОМАНДУ - массив юнитов в команде */

            /** установим значения по умолчанию  для данных TeamEntity **/
//            savedGame.getTeamDataList().get(0).setDefaultData();
            team.add(new TeamEntity(savedGame.getTeamDataList().get(0)));
//            team.add(new TeamEntity(savedGame.getTeamDataList().get(1)));
//            team.add(new TeamEntity(savedGame.getTeamDataList().get(2)));


            /** создадим КОЛЛЕКЦИЮ - массив юнитов в коллекции  **/
            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(0)));
            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(1)));
            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(2)));

            giftTime = 0;
            savedGame.setGiftTime(giftTime);

//            savedGame.setTeam(team);
        } else {


            teamDataList = savedGame.getTeamDataList();
            collectionDataList = savedGame.getCollectionDataList();

            for (int i = 0; i < teamDataList.size(); i++) {
                team.add(new TeamEntity(savedGame.getTeamDataList().get(i)));

            }
            for (int i = 0; i < collectionDataList.size(); i++) {
                collection.add(new TeamEntity(savedGame.getCollectionDataList().get(i)));
            }
//            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(0)));
//            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(1)));
//            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(2)));

            // получим кол-во ресурсов
            foodCount = savedGame.getFoodCount();
            ironCount = savedGame.getIronCount();
            woodCount = savedGame.getWoodCount();
            coinsCount = savedGame.getCoinsCount();
            scoreCount = savedGame.getScoreCount();
            starsCount = savedGame.getStarsCount();
        }

        /** получим массив с ДАННЫМИ о наградах за звезды */
        rewardForStarsDataList = savedGame.getRewardForStarsDataList();

        /** получим индекс следующей награды за звёзды */
        indexOfRewardStars = savedGame.getIndexOfRewardStars();

        /** получим кол-во звезд для следующей награды **/
        int rewardStarsCount = rewardForStarsDataList.get(indexOfRewardStars).getStarsCount();

        coinsPanel = new CoinsPanel(coinsCount);
        scorePanel = new ScorePanel(scoreCount);
        starsPanel = new StarsPanel(starsCount, rewardStarsCount, rewardForStarsDataList, indexOfRewardStars);

        giftTime = savedGame.getGiftTime();

        for (int i = 0; i < savedGame.getLevelIconDataList().size(); i++) {
            levelIcons.add(new LevelIcon(savedGame.getLevelIconDataList().get(i)));
        }

        /** получи кол-во здоровья ОСАДНОЙ БАШНИ **/
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
     * метод для получения ДАННЫХ о наградах за звезды
     **/
    public ArrayList<RewardForStarsData> getRewardForStarsDataList() {
        return rewardForStarsDataList;
    }

    /**
     * метод для получения времени до получнеия подарка
     **/
    public long getGiftTime() {
        return giftTime;
    }

    /**
     * метод для установки времени до получнеия подарка
     **/
    public void setGiftTime(long giftTime) {
        this.giftTime = giftTime;
        savedGame.setGiftTime(this.giftTime);
    }

    /**
     * метод для загрузки данных игры
     **/
    private SavedGame loadSavedGame() {
        if (Gdx.files.local("save.ser").exists()) {
            System.out.println("SavedGame Exists. Reading File ...");
            try {
//                FileInputStream fileInputStream = new FileInputStream(String.valueOf(Gdx.files.local("save.ser")));
                FileHandle file = Gdx.files.local("save.ser");

                ByteArrayInputStream byteArray = new ByteArrayInputStream(file.readBytes());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArray);
                savedGame = (SavedGame) objectInputStream.readObject();
                System.out.println("readObject = " + savedGame.toString());
            } catch (Exception e) {
                System.out.println("exception = " + e.toString());
                return null;
            }
        }
        return savedGame;
    }

    /**
     * метод сохраняет данные игры
     **/
    public void saveGame() {
        FileHandle file = Gdx.files.local("save.ser");
        try {
            //создаем 2 потока для сериализации объекта и сохранения его в файл
//            FileOutputStream outputStream = new FileOutputStream("save.ser");
//            FileOutputStream outputStream = new FileOutputStream(String.valueOf(Gdx.files.local("save.ser")));
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);


            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArray);

            // сохраняем игру в файл
            objectOutputStream.writeObject(savedGame);

            //закрываем поток и освобождаем ресурсы
            file.writeBytes(byteArray.toByteArray(), false);
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
     * метод возвращает объект ScorePanel
     **/
    public ScorePanel getScorePanel() {
        return scorePanel;
    }

    /**
     * метод возвращает объект StarsPanel
     **/
    public StarsPanel getStarsPanel() {
        return starsPanel;
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
     * метод для установки кол-ва монет в менеджере и в объекте сохранения игры savedGame()
     */
    public void setCoinsCount(int coinsCount) {
        this.coinsCount = coinsCount;
        savedGame.setCoinsCount(coinsCount);
    }

    /**
     * метод добавлет кол-во очков к общему кол-ву очков в менеджере и сохраняет кол-во очков в savedGame(объекте сохранения игры)
     **/
    public void addScoreCount(int scoreCount) {
        this.scoreCount += scoreCount;
        savedGame.setScoreCount(this.scoreCount);
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
        savedGame.setFoodCount(this.foodCount);
    }


    /**
     * метод для добавления количества пищи к общему количеству
     *
     * @param ironCount - количество железа, которое добавим
     **/
    public void addIronCount(int ironCount) {
        this.ironCount += ironCount;
        savedGame.setIronCount(this.ironCount);
    }


    /**
     * метод для добавления количества пищи к общему количеству
     *
     * @param woodCount - количество  дерева, которое добавим
     **/
    public void addWoodCount(int woodCount) {
        this.woodCount += woodCount;
        savedGame.setWoodCount(this.woodCount);
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

    /** метод для получения объекта savedGame() */
    public SavedGame getSavedGame() {
        return savedGame;
    }

}

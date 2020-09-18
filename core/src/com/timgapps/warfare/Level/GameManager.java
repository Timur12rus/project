package com.timgapps.warfare.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.boontaran.DataManager;
import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
import com.timgapps.warfare.Level.GUI.Screens.TeamUnit;
import com.timgapps.warfare.Level.GUI.Screens.PlayerUnitData;
import com.timgapps.warfare.Level.LevelMap.LevelIcon;
import com.timgapps.warfare.Level.GUI.Screens.reward_for_stars.RewardForStarsData;
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
    private ArrayList<TeamUnit> team;     // команда, массив объектов TeamEntity()
    //    private Map<UnitType, TeamUnit> team;      // команда, мап <тип юнита, модель юнита>
    //    private ArrayList<TeamUnit> team;     // команда, массив объектов TeamEntity()
    private ArrayList<PlayerUnitData> teamDataList;     // команда, массив объектов TeamEntity()
    private ArrayList<PlayerUnitData> collectionDataList;     // команда, массив объектов TeamEntity()
    private ArrayList<TeamUnit> collection;     // коллекция, массив объектов TeamEntity()
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

    private long giftTimeFirst;      // время до получения первого подарка
    private long giftTimeSecond;      // время до получения второго подарка
    private int indexOfRewardStars;

    public static final int HELP_UNIT_CREATE = 1;
    public static final int HELP_STARS_PANEL = 2;
    public static final int HELP_TEAM_UPGRADE = 3;
    public static final int HELP_GET_GIFT = 4;
    public static final int NONE = 5;
    private int helpStatus;

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

        team = new ArrayList<TeamUnit>();
//        team = new HashMap<UnitType, TeamUnit>();
        collection = new ArrayList<TeamUnit>();

        // если сохранения нет(запускает игру впервый раз), null, то создадим объект для схранниения игры и создадим объект "КОМАНДА" team
        if (savedGame == null) {
//            System.out.println("SavedGame null ");
            savedGame = new SavedGame();

            savedGame.setHelpStatus(GameManager.HELP_UNIT_CREATE);      // установим статус обучалки - "создание юнита"

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
            team.add(new TeamUnit(savedGame.getTeamDataList().get(0)));
//            team.add(new TeamEntity(savedGame.getTeamDataList().get(1)));
//            team.add(new TeamEntity(savedGame.getTeamDataList().get(2)));


            /** создадим КОЛЛЕКЦИЮ - массив юнитов в коллекции  **/
            collection.add(new TeamUnit(savedGame.getCollectionDataList().get(0)));
            collection.add(new TeamUnit(savedGame.getCollectionDataList().get(1)));

//            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(1)));
//            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(1)));
//            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(1)));
//            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(1)));
//            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(1)));
//            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(1)));
//            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(1)));
//            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(1)));
//            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(1)));

            collection.add(new TeamUnit(savedGame.getCollectionDataList().get(2)));
            collection.add(new TeamUnit(savedGame.getCollectionDataList().get(3)));
//            collection.add(new TeamEntity(savedGame.getCollectionDataList().get(4)));

            giftTimeFirst = 0;
            giftTimeSecond = 0;
            savedGame.setGiftTimeFirst(giftTimeFirst);
            savedGame.setGiftTimeSecond(giftTimeSecond);

//            savedGame.setTeam(team);
        } else {


            teamDataList = savedGame.getTeamDataList();
            collectionDataList = savedGame.getCollectionDataList();

            for (int i = 0; i < teamDataList.size(); i++) {
                team.add(new TeamUnit(savedGame.getTeamDataList().get(i)));

            }
            for (int i = 0; i < collectionDataList.size(); i++) {
                collection.add(new TeamUnit(savedGame.getCollectionDataList().get(i)));
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
        int rewardStarsCount;

        if (indexOfRewardStars < rewardForStarsDataList.size()) {
            rewardStarsCount = rewardForStarsDataList.get(indexOfRewardStars).getStarsCount();
        } else {
            rewardStarsCount = rewardForStarsDataList.get(rewardForStarsDataList.size() - 1).getStarsCount();
        }
        System.out.println("REWARDsTARScOUNT= " + rewardStarsCount);
        System.out.println("indexOfRewardStars = " + indexOfRewardStars);

        coinsPanel = new CoinsPanel(coinsCount);
        scorePanel = new ScorePanel(scoreCount);
        starsPanel = new StarsPanel(starsCount, rewardStarsCount, rewardForStarsDataList, indexOfRewardStars);

        giftTimeFirst = savedGame.getGiftTimeFirst();       // получим сохраненное время для получения первого подарка
        giftTimeSecond = savedGame.getGiftTimeSecond();     // получим сохраненное время для получения второго подарка

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
     * метод для получения статуса обучения
     **/
    public int getHelpStatus() {
        return savedGame.getHelpStatus();
    }

    public void setHelpStatus(int status) {
        savedGame.setHelpStatus(status);
    }

    /**
     * метод для получения ДАННЫХ о наградах за звезды
     **/
    public ArrayList<RewardForStarsData> getRewardForStarsDataList() {
        return rewardForStarsDataList;
    }

    /**
     * метод для получения времени до получнеия первого подарка
     **/
    public long getGiftTimeFirst() {
        return giftTimeFirst;
    }

    /**
     * метод для получения времени до получнеия второго подарка
     **/
    public long getGiftTimeSecond() {
        return giftTimeSecond;
    }

    /**
     * метод для установки времени до получнеия первого подарка
     **/
    public void setGiftTimeFirst(long giftTimeFirst) {
        this.giftTimeFirst = giftTimeFirst;
        savedGame.setGiftTimeFirst(this.giftTimeFirst);
    }


    /**
     * метод для установки времени до получнеия второго подарка
     **/
    public void setGiftTimeSecond(long giftTimeSecond) {
        this.giftTimeSecond = giftTimeSecond;
        savedGame.setGiftTimeSecond(this.giftTimeSecond);
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
            System.out.println(e);
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
     * @param count - количество монет, на сколько прибавить
     **/
    public void addCoinsCount(int count) {
//        coinsCount += coins;
//        coinsPanel.setCoinsCount(coinsCount);       // обновим количество монет в панели для монет (coinsPanel)

        savedGame.addCoinsCount(count);

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
    public ArrayList<TeamUnit> getTeam() {
        return team;
    }

    // метод получает UnitData юнита из команды
    public PlayerUnitData getUnitData(String id) {
        for (TeamUnit teamUnit : team) {
            if (id.equals(teamUnit.getUnitData().getUnitId().name())) {
                return teamUnit.getUnitData();
            }
        }
        return null;
    }

    /**
     * метод для получения коллекции юнитов игрока
     **/
    public ArrayList<TeamUnit> getCollection() {
        return collection;
    }

    /**
     * метод для обновления команды юнитов
     **/
    public void updateTeam(ArrayList<TeamUnit> team) {
        this.team = team;
//        savedGame.setTeam(team);
        savedGame.updateTeamDataList(team);
//        saveGame();
    }

    /**
     * метод для обновления коллекции юнитов
     **/
    public void updateCollection(ArrayList<TeamUnit> collection) {
        this.collection = collection;
        savedGame.updateCollectionDataList(collection);
    }

    /**
     * метод для получения объекта savedGame()
     */
    public SavedGame getSavedGame() {
        return savedGame;
    }

}

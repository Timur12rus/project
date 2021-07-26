package com.timgapps.warfare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boontaran.DataManager;
import com.timgapps.warfare.Utils.Helper.GameHelper;
import com.timgapps.warfare.Utils.Setting;
import com.timgapps.warfare.screens.map.gui_elements.CoinsPanel;
import com.timgapps.warfare.screens.map.gui_elements.Geom;
import com.timgapps.warfare.screens.map.gui_elements.GeomHolder;
import com.timgapps.warfare.screens.map.windows.team_window.team_unit.TeamUnit;
import com.timgapps.warfare.Units.GameUnits.Player.units.PlayerUnitData;
import com.timgapps.warfare.screens.map.gui_elements.LevelIcon;
import com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData;
import com.timgapps.warfare.screens.map.gui_elements.ScorePanel;
import com.timgapps.warfare.screens.map.gui_elements.StarsPanel;
import com.timgapps.warfare.saved_data.SavedGame;
import com.timgapps.warfare.Units.GameUnits.unitTypes.PlayerUnits;

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
    private ArrayList<PlayerUnitData> teamDataList;     // команда, массив объектов TeamEntity()
    private ArrayList<PlayerUnitData> collectionDataList;     // команда, массив объектов TeamEntity()
    private ArrayList<TeamUnit> collection;     // коллекция, массив объектов TeamEntity()
    private ArrayList<RewardForStarsData> rewardForStarsDataList;     // массив из данных апгрейдов за звезды
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
    private int indexOfNextRewardStars;     // индекс следующей награды за звезды
    public static final int HELP_UNIT_CREATE = 1;
    public static final int HELP_STARS_PANEL = 2;
    public static final int HELP_TEAM_UPGRADE = 3;
    public static final int HELP_GET_GIFT = 4;
    public static final int NONE = 5;
    private int helpStatus;
    private Vector2 cameraPosition;
    private boolean isHaveFullRewardsForStars;      // игрок получил все награды за звезды
    private GeomHolder geomHolder;
    private boolean isCanUpgrade;                   // индикатор, может ли быть апгрейд юнитов

    public GameManager() {
        /** загрузим данные игры **/
        /** создадим КОМАНДУ - массив юнитов в команде**/

        // создаем объект для сохранения игры
        savedGame = loadSavedGame();
        cameraPosition = new Vector2();

        geomHolder = new GeomHolder(); // объект с геомами

        /** создадим массив уровней (LevelIcons) для хранения информации и данных уровней (кол-во звёзд, заблокировани или разблокирован **/
        //TODO сделать сохранение и загрузку данных об уровнях в  массив <LevelIconData> в объекте сохранения игры savedGame()
//        public LevelIcon(int id, int coinsCount, int scoreCount, String levelOfDifficulty, boolean isActive) {
//        levelIcons.add(new LevelIcon(1, 15, 10, LevelIcon.EASY, true));

        team = new ArrayList<TeamUnit>();            // команда юнитов
        collection = new ArrayList<TeamUnit>();     // коллекция юнитов

        // если сохранения нет(запускает игру впервый раз), null, то создадим объект для схранниения игры и создадим объект "КОМАНДА" team
        if (savedGame == null) {
            savedGame = new SavedGame();
            setHelpStatus(GameHelper.HELP_UNIT_CREATE);

            // зададим положение камеры и сохраним ее значение в savedGame
//            cameraPosition.set(Warfare.V_WIDTH / 2, Warfare.V_HEIGHT / 2);
            setCameraPosition(new Vector2(Warfare.V_WIDTH / 2, Warfare.V_HEIGHT / 2));

            /** создаем массив "ДАННЫХ" об уровнях (LevelIconData) **/
            savedGame.createLevelIconDataList();
            savedGame.setIndexRewardStars(0);

            /** Добавляем бойцов в команду **/
            // TODO: 31.01.2020  Здесь нужно будет изменить код, так чтобы брать данные из сохранненного объекта
            savedGame.createTeamEntityDataList();
            savedGame.createCollectionDataList();

            /** создадим список с ДАННЫМИ наград за звезды **/
            savedGame.createRewardForStarsDataList();

            // установим кол-во ресурсов
//            foodCount = 2;
//            ironCount = 2;
//            woodCount = 1;

            foodCount = 12;
            ironCount = 34;
            woodCount = 8;

            /** количество монет у игрока **/
            coinsCount = 0;
//            coinsCount = 5000;           // это для теста

            // для теста
//            coinsCount = 100;
            scoreCount = 0;
            starsCount = 63;
//            starsCount = 0;

            savedGame.setCoinsCount(coinsCount);
            savedGame.setFoodCount(foodCount);
            savedGame.setIronCount(ironCount);
            savedGame.setWoodCount(woodCount);

            savedGame.setStarsCount(starsCount);

//            rewardStarsIndexCalculator = new RewardStarsIndexCalculator(this);
//            indexOfNextRewardStars = rewardStarsIndexCalculator.calculateIndexReward();

            /** создадим КОМАНДУ - массив юнитов в команде */

            /** установим значения по умолчанию  для данных TeamUnit **/
            for (PlayerUnitData savedUnitData : savedGame.getTeamDataList()) {
                team.add(new TeamUnit(savedUnitData));
                System.out.println("saved UnitId = " + savedUnitData.getUnitId());
            }

            /** создадим КОЛЛЕКЦИЮ - массив юнитов в коллекции  **/
            for (PlayerUnitData savedUnitData : savedGame.getCollectionDataList()) {
                collection.add(new TeamUnit(savedUnitData));
            }
            giftTimeFirst = 0;
            giftTimeSecond = 0;
            savedGame.setGiftTimeFirst(giftTimeFirst);
            savedGame.setGiftTimeSecond(giftTimeSecond);
        } else {
            teamDataList = savedGame.getTeamDataList();
            collectionDataList = savedGame.getCollectionDataList();
            for (PlayerUnitData playerUnitData : teamDataList) {
                team.add(new TeamUnit(playerUnitData));
            }
            for (PlayerUnitData playerUnitData : collectionDataList) {
                collection.add(new TeamUnit(playerUnitData));
            }
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

        /** обновим индекс следующей награды за звезды */
        updateIndexOfNextRewardStars();

        /** получил ли игрок все награды за звезды **/
        isHaveFullRewardsForStars = savedGame.isHaveFullRewardsForStars();


        /** получим индекс следующей награды за звёзды */
//        indexOfNextRewardStars = savedGame.getIndexOfRewardStars();
        System.out.println("IndexRewardForStars = " + indexOfNextRewardStars);

        coinsPanel = new CoinsPanel(this);
        scorePanel = new ScorePanel(scoreCount);
        starsPanel = new StarsPanel(this);
        giftTimeFirst = savedGame.getGiftTimeFirst();       // получим сохраненное время для получения первого подарка
        giftTimeSecond = savedGame.getGiftTimeSecond();     // получим сохраненное время для получения второго подарка
        for (int i = 0; i < savedGame.getLevelIconDataList().size(); i++) {
            levelIcons.add(new LevelIcon(savedGame.getLevelIconDataList().get(i)));
        }

        // Для теста
        if (Setting.DEBUG_GAME) {
            for (LevelIcon levelIcon : levelIcons) {
                levelIcon.getData().setActive();
                levelIcon.getData().setFinished();
            }
        }
        /** получи кол-во здоровья ОСАДНОЙ БАШНИ **/
        towerHealth = 50;
        checkCanUpgrade();
    }

    // устанавливает есть ли бустер "огненные шары"
    public void activateFireBooster() {
        savedGame.setIsHaveFireBooster(true);
    }

    public boolean isHaveFireBooster() {
        return savedGame.isHaveFireBooster();
    }

    // метод сохраняет позяцию камеры в savedGame
    public void setCameraPosition(Vector2 cameraPosition) {
        savedGame.setCameraPosition(cameraPosition);
//        System.out.println("Save Camera Position = " + cameraPosition);
    }

    public void setLastCompletedLevelNum(int lastCompletedLevelNum) {
        savedGame.setLastCompletedLevelNum(lastCompletedLevelNum);
    }

    public int getLastCompletedNum() {
        return savedGame.getLastCompletedLevelNum();
    }

    // метод проверяет стала ли доступна награда за звезды для получения (т.е. получили звезды за прохождение уровня, и проверяем, доступна ли награда)
    public boolean checkStarsCountForReward() {
        boolean isHaveReward = false;
        for (RewardForStarsData rewardForStarsData : rewardForStarsDataList)
            if (savedGame.getStarsCount() >= rewardForStarsData.getStarsCount() && !rewardForStarsData.isReceived()) {
                isHaveReward = true;
            }
        return isHaveReward;
    }

    // метод возвращает кол-во звезд у игрока
    public int getStarsCount() {
        return starsCount;
    }

    // метод возвращает необходимое кол-во звезд следующей награды
    public int getStarsCountForReward() {
        System.out.println("Index of Next Reward = " + indexOfNextRewardStars);
        return rewardForStarsDataList.get(indexOfNextRewardStars).getStarsCount();
    }

    // метод проверяет, имеется ли доступная награда за звезды
    public boolean isHaveReward() {
        for (RewardForStarsData rewardForStarsData : rewardForStarsDataList) {
            if (starsCount >= rewardForStarsData.getStarsCount() && !rewardForStarsData.isReceived()) {     // если звезд больше, чем нужно для награды
                rewardForStarsData.setReceived();
                return true;
            }
        }
        return false;
    }

    /**
     * метод для получения статуса обучения
     **/
    public int getHelpStatus() {
        return savedGame.getHelpStatus();
    }

    public void setHelpStatus(int status) {
        savedGame.setHelpStatus(status);
        saveGame();
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

    // метод добавляет количество звезд к имеющемуся кол-ву звезд у игрока
    public void addStarsCount(int starsCount) {
        this.starsCount += starsCount;
        savedGame.addStarsCount(starsCount);
        updateIndexOfNextRewardStars();     // обновим индекс следующей награды за звезды
        starsPanel.redraw();                // обновим значения панели с наградами за звезды
    }

    // обновим индекс следующей награды за звезды
    public void updateIndexOfNextRewardStars() {
        if (!savedGame.isHaveFullRewardsForStars()) {
            int indexOfRewardStars = 0;
            int index = 0;
            while (index < rewardForStarsDataList.size() - 1) {
                // если текущее кол-во звезд >= кол-ва звезд награды с текущим индексом
                if (starsCount >= rewardForStarsDataList.get(indexOfRewardStars).getStarsCount()) {
                    indexOfRewardStars++;
                    indexOfNextRewardStars = indexOfRewardStars;
//                    System.out.println("indexOfReward = " + indexOfRewardStars);
//                    System.out.println("indexOfNextRewardStars = " + indexOfNextRewardStars);
                }
                index++;
//                if (indexOfRewardStars >= (rewardForStarsDataList.size() - 1) && !savedGame.isHaveFullRewardsForStars()) {
//                    indexOfNextRewardStars = rewardForStarsDataList.size() - 1;
//                    savedGame.setIsHaveFullRewardsForStars(true);
//                }
                if (starsCount >= rewardForStarsDataList.get(rewardForStarsDataList.size() - 1).getStarsCount()) {
                    savedGame.setIsHaveFullRewardsForStars(true);
                }
            }
//            System.out.println("indexOfReward = " + indexOfRewardStars);
//            System.out.println("indexOfNextRewardStars = " + indexOfNextRewardStars);
//            savedGame.setIndexRewardStars(indexOfRewardStars);
        }
    }

    public boolean isHaveFullRewardsForStars() {
        return savedGame.isHaveFullRewardsForStars();
    }

    public int getIndexOfNextRewardStars() {
        return indexOfNextRewardStars;
    }

    /**
     * метод добавляет количество монет к общему количеству монет
     *
     * @param count - количество монет, на сколько прибавить
     **/
    public void addCoinsCount(int count) {
        coinsCount += count;
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
    public PlayerUnitData getUnitData(PlayerUnits id) {
        for (TeamUnit teamUnit : team) {
            if (id.equals(teamUnit.getUnitData().getUnitId())) {
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
        savedGame.updateTeamDataList(team);
    }

    /**
     * метод для обновления коллекции юнитов
     **/
    public void updateCollection() {
        int i = 0;
        for (TeamUnit collectionUnit : collection) {
            System.out.println("collectionUnit[" + i + "] = " + collectionUnit.getUnitData().getUnitId().toString());
            i++;
        }
        savedGame.updateCollectionDataList(collection);
    }

    /**
     * метод для получения объекта savedGame()
     */
    public SavedGame getSavedGame() {
        return savedGame;
    }

    /**
     * метод совершает покупку юнита (т.е. делаем его isHared = true, добавляет в команду если в коменде < 5 юнитов
     **/
    public void setUnitIsHared(PlayerUnits id) {
    }

    public void addGeom(Actor actor, String name) {
        geomHolder.addGeom(new Geom(actor, name, geomHolder));
    }

    public Geom getGeom(String geomName) {
        return geomHolder.getGeom(geomName);
    }

    // метод проверяет, возможен ли апгрейд юнита, при имеющемся кол-ве ресурсов
    public void checkCanUpgrade() {
        System.out.println("---------------------------------------------------");
        isCanUpgrade = false;
        for (TeamUnit teamUnit : team) {
            System.out.println("TeamUnit = " + teamUnit.getUnitData().getName());
            int upgradeFoodValue = teamUnit.getUnitData().getFoodValueForUpgrade();
            int upgradeIronValue = teamUnit.getUnitData().getIronValueForUpgrade();
            int upgradeWoodValue = teamUnit.getUnitData().getWoodValueForUpgrade();
            teamUnit.setCanUpgrade(false);
            if (((coinsCount >= teamUnit.getUpgradeCost()) && (foodCount >= upgradeFoodValue) && (ironCount >= upgradeIronValue) && (woodCount >= upgradeWoodValue))
                    && (teamUnit.getUnitData().getUnitLevel() < 10)) {
                // TODO сделать roundCircle.setVisible(true)
                // TODO сделать teamUnit.canUpgrade(true)
                isCanUpgrade = true;
                teamUnit.setCanUpgrade(true);
            }
//            else {
//                // TODO сделать roundCircle.setVisible(false)
//                // TODO сделать teamUnit.canUpgrade(true)
//                isCanUpgrade = false;
//            }
        }
    }

    public boolean isCanUpgrade() {
        return isCanUpgrade;
    }
}

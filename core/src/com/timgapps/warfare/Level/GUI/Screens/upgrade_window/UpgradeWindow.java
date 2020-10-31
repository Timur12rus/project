package com.timgapps.warfare.Level.GUI.Screens.upgrade_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.timgapps.warfare.Level.GUI.Screens.resources_view.ResourcesTable;
import com.timgapps.warfare.Level.GUI.Screens.upgrade_window.bottom_group.BottomGroup;
import com.timgapps.warfare.Level.GUI.Screens.upgrade_window.bottom_group.CoinButton;
import com.timgapps.warfare.Level.GUI.Screens.upgrade_window.info_table.InfoTable;
import com.timgapps.warfare.Level.GUI.team_unit.TeamUnit;
import com.timgapps.warfare.Level.GUI.Screens.team_upgrade_screen.TeamUpgradeScreen;
import com.timgapps.warfare.Level.GUI.Screens.win_creator.ConstructedWindow;
import com.timgapps.warfare.Level.GUI.team_unit.UnitLevelIcon;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

// экран с данными юнита и апгрейда
public class UpgradeWindow extends Group {
    public static final int ON_RESUME = 1;
    private ConstructedWindow constructedWindow;
    private ImageButton closeButton;
    private InfoTable infoTable;
    private Table container;     // основной верхний контейнер-таблица, в котором будут помещаться infoTable, imageContainer и resourceContainer
    //    private Label upgradeToLevelLabel;
    private Label toastLabel;
    //    private String upgradeToLevelText = "Upgrade to Level ";
    private float paddingLeft = 48;
    private Image foodIcon, ironIcon, woodIcon;
    private CoinButton upgradeButton;              // кнопка для апгрейда юнита
    private CoinButton hareButton;                // кнопка для найма(покупки) юнита
    private ArrayList<TeamUnit> team;
    private Group imageContainer;           // контейнер - Group для хранения изображения юнита со значком уровня и энергии
    private UnitImage unitImage;            // изображение юнита
    private int coinsCount;
    private int upgradeCost;
    private final int COST_UPGRADE = 50;
    private GameManager gameManager;
    private String noResources = "Not enought resources!";
    private String noCoins = "Not enought coins!";
    private boolean isStartToastAction = false;
    private boolean canBeUpgrade;
    private TeamUnit teamUnit;
    private ResourcesTable resourcesTable;
    private UnitLevelIcon unitLevelIcon;
    private TextureRegionDrawable textureRegionDrawableBg;
    private ColorButton selectUnitButton;  // кнопка "ВЫБРАТЬ" для игрового юнита,
    // если игровой юнит не состоит в коменде, а нах-ся в коллекции
    private TeamUpgradeScreen teamUpgradeScreen;
    private Label unitNameLabel;
    private String unitName;
    private BlockTable blockTable;
    private UpgradeCostTable upgradeCostTable;
    private Label maxLevelReached;  // надпись "максимальный уровнень достигнут"
    private BottomGroup bottomGroup;

    public UpgradeWindow(GameManager gameManager, TeamUpgradeScreen teamUpgradeScreen) {
        this.teamUpgradeScreen = teamUpgradeScreen;
        this.gameManager = gameManager;
        this.team = gameManager.getTeam();              // команда - массив типа TeamEntity
        coinsCount = gameManager.getCoinsCount();       // кол-во монет у игрока
        blockTable = new BlockTable();                  // таблица с надписью "соберите звезд для разблокрирвоки юнита"
        constructedWindow = new ConstructedWindow(840, 612, "Upgrade");
        constructedWindow.setX((Warfare.V_WIDTH - constructedWindow.getWidth()) / 2); // устанавливаем позицию заголовка
        constructedWindow.setY(Warfare.V_HEIGHT / 2 - constructedWindow.getHeight() / 2);
        addActor(constructedWindow);

        /** слушатель для КНОПКИ ЗАКРЫТИЯ ОКНА **/
        closeButton = constructedWindow.getCloseButton();
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                fire(new MessageEvent(ON_RESUME));
                if (toastLabel.isVisible()) toastLabel.setVisible(false);
                unitImage.clearActions();
                hideUpgradeScreen();
            }
        });

        upgradeButton = new CoinButton();        // зеленая кнопка для апгрейда
        hareButton = new CoinButton();        // зеленая кнопка для апгрейда

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;
        Label.LabelStyle greenLabelStyle = new Label.LabelStyle();
        greenLabelStyle.fontColor = Color.FOREST;
        greenLabelStyle.font = Warfare.font20;
        Label.LabelStyle redLabelStyle = new Label.LabelStyle();
        redLabelStyle.fontColor = Color.RED;
        redLabelStyle.font = Warfare.font20;

        // нижняя часть надписей (ниже таблицы с характеристиками)
        maxLevelReached = new Label("The unit has reached maximum level", labelStyle);
        toastLabel = new Label("", redLabelStyle);          // надпись с оповещением
        unitNameLabel = new Label("", labelStyle);

        // создадим таблицу с характеристиками юнита
        craetUpgradeTableContainer();

        //  добавим группу с кнопкой апгрейда и кнопкой "нанять" (купить)
        bottomGroup = new BottomGroup();
        bottomGroup.setPosition(container.getX() + (550 - bottomGroup.getWidth()) / 2, 154);
        addActor(bottomGroup);
//        bottomGroup.hideUpgradeButton();
//        bottomGroup.hideHireButton();
//        bottomGroup.redraw();

        createBlockTable();     // таблица : собрите n-ое кол-во звезд для разблокировки

        /** Добавляем панель (таблицу) с реурсами(ПИЩА, ЖЕЛЕЗО, ДЕРЕВО) **/
        resourcesTable = new ResourcesTable(gameManager.getFoodCount(),
                gameManager.getIronCount(),
                gameManager.getWoodCount());

        resourcesTable.setPosition(container.getX() + container.getWidth() + 48,
                container.getY() - (resourcesTable.getHeight() - container.getHeight()));
        addActor(resourcesTable);

        setVisible(false);
        toastLabel.setVisible(false);
        toastLabel.setPosition(Warfare.V_WIDTH / 2 - toastLabel.getWidth() - 200, Warfare.V_HEIGHT / 2);
        addActor(toastLabel);
        unitNameLabel.setPosition(container.getX() + container.getWidth() - unitNameLabel.getWidth(),
                constructedWindow.getY() + constructedWindow.getHeight() - unitNameLabel.getHeight());
        addActor(unitNameLabel);

        maxLevelReached.setPosition(constructedWindow.getX() + (constructedWindow.getWidth() - maxLevelReached.getWidth()) / 2, 272);
        maxLevelReached.setVisible(false);
        addActor(maxLevelReached);
    }

    // создает таблицу-контейнер с характеристиками юнита
    private void craetUpgradeTableContainer() {
        imageContainer = new Group();

        // таблица с информацией (характеристиками) юнита
        infoTable = new InfoTable();
        infoTable.debug();

        container = new Table();        // таблица "КОНТЕЙНЕР"
        container.debug();
        container.setHeight(600);
        container.setWidth(550);
        container.left().top();
        container.add(infoTable).left();

        /** добавим в общий контейнер изображение юнита и его значок со значением уровня юнита**/
        container.add(imageContainer).width(200).padLeft(32).left().padTop(32);

        /** Таблица СТОИМОСТЬ АПГРЕЙДА в режиме DEBUG **/
        upgradeCostTable = new UpgradeCostTable();
        Table line = new Table();
        line.setBackground(new TextureRegionDrawable(Warfare.atlas.findRegion("div_line")));
        container.row();

        /** добавим таблицу со стоимостью апгрейда **/
        container.add(upgradeCostTable).colspan(3);
        container.setPosition(constructedWindow.getX() + paddingLeft, constructedWindow.getY());
        container.setPosition(constructedWindow.getX() + paddingLeft, constructedWindow.getY() - 16);
        addActor(container);

    }

    // таблица : собрите n-ое кол-во звезд для разблокировки
    private void createBlockTable() {
        blockTable.setPosition(500, 260);
        blockTable.setVisible(false);
        addActor(blockTable);
    }

    // метод для найма юнита
    public void hireUnit() {
        if (coinsCount >= teamUnit.getUnitPrice()) {
            teamUnit.getUnitData().setIsCalled(true);
            show(teamUnit);
//            show(false, false, teamUnit);

            if (gameManager.getTeam().size() < 5) {
                // добавим полученный юнит в команду
                gameManager.getTeam().add(teamUnit);  // добавляем в команду полученный юнит из коллекции

//                gameManager.getSavedGame().getTeamDataList().add(gameManager.getSavedGame().getCollectionDataList().get(i));

                // удалим юнит из коллекции
                gameManager.getCollection().remove(teamUnit);
                gameManager.getSavedGame().getCollectionDataList().remove(teamUnit.getUnitData());
                gameManager.saveGame();
            }
        }
    }

    // метод показывает экран с апгрейдом юнита
    public void show(TeamUnit teamUnit) {
        this.teamUnit = teamUnit;
        // обновим кол-во ресурсов в таблице
        resourcesTable.updateResources(gameManager.getFoodCount(), gameManager.getIronCount(), gameManager.getWoodCount());
        // обновим количество монет
        coinsCount = gameManager.getCoinsPanel().getCoinsCount();
        imageContainer.clearChildren();
        imageContainer.clearActions();
        maxLevelReached.setVisible(false);      // скроем надпись "максимальный уровень достигнут"
        blockTable.setVisible(false);          // скроем сообщение "соберите кол-во звезд"
        bottomGroup.hideHireButton();
        bottomGroup.hideUpgradeButton();
//        bottomGroup.setVisible(false);         // скром кнопку "апгрейда" и надпись "улучшить до уровня"
        selectUnitButton.setVisible(false);
        upgradeCostTable.setVisible(false);
        // добавим объект - изображение юнита со значком уровня юнита
        imageContainer.addActor(unitImage);
        infoTable.hideUpgradeLabels();
        bottomGroup.hideHireButton();

        boolean isUnlock = teamUnit.getUnitData().isUnlock();
        if (isUnlock == true) {               // если юнит разблокирован
//            bottomGroup.setVisible(true);
            if (teamUnit.getUnitData().isHired()) {          // если юнит "призван" (нанят)
                int nextUnitLevel = teamUnit.getUnitLevel();
                nextUnitLevel++;
                if (teamUnit.getUnitLevel() >= teamUnit.getMaxUnitLevel()) {    // если уровень юнита >= максимальному уровню юнита
                    maxLevelReached.setVisible(true);
                } else {
                    upgradeCostTable.setVisible(true);
                    bottomGroup.setNextLevel(nextUnitLevel);
//                    bottomGroup.getUpgradeButton().setCost(upgradeCost);
                    bottomGroup.showUpgradeButton();
                    infoTable.showUpgradeLabels();
                }
            } else {
                bottomGroup.showHireButton();
            }
        } else {
            blockTable.setLabelStarsCount(teamUnit.getUnitData().getStarsCount());      // установим значение, кол-ва звезд для открытия юнита
            blockTable.setVisible(true);
        }
        setVisible(true);
    }

    // метод показывает окно с данными об апгрейде юнита
    public void show(boolean showSelectButton, boolean showCallLabel, TeamUnit teamUnit) {
        // если showSelectButton = true - значит показываем кнопку "Выбрать"
        // если showCallLabel = true - значит показываем надпись "Призвать", убираем таблицу со стоимостью апгрейда
        this.teamUnit = teamUnit;
        // если уровень текущего юнита максимальный, не показываем таблицу о стоимости апгрейда
        if (teamUnit.getUnitLevel() >= teamUnit.getMaxUnitLevel()) {
            upgradeButton.setVisible(false);
            upgradeCostTable.setVisible(false);
            maxLevelReached.setVisible(true);
        }
        // обновим количество ресурсов в таблице
        resourcesTable.updateResources(gameManager.getFoodCount(), gameManager.getIronCount(), gameManager.getWoodCount());

        // обновим количество монет
        coinsCount = gameManager.getCoinsPanel().getCoinsCount();
        setVisible(true);
        /** добавим объект - изображение юнита со значком уровня юнита**/
        imageContainer.clearChildren();
        imageContainer.clearActions();
        imageContainer.addActor(unitImage);
        boolean isUnlock = teamUnit.getUnitData().isUnlock();

        /** если юнит разблокирован, то делаем значок уровня юнита видимым **/
//        if (isCalled == true) {
        if (isUnlock == true) {
            upgradeButton.setVisible(true);
            if (showCallLabel) {         // если нужно показать надпись "призвать"
                teamUnit.getUnitImage().getUnitLevelIcon().setVisible(false);
                infoTable.hideUpgradeLabels();

                upgradeCostTable.setVisible(false);
                bottomGroup.showHireButton();
//                upgradeToLevelLabel.setText("Call this unit:"); // если юнит не призван (не куплен), выводим надипись "призвать" и стоимость **/
//                upgradeToLevelLabel.setVisible(true);
            } else {
                teamUnit.getUnitImage().getUnitLevelIcon().setVisible(true);
                infoTable.showUpgradeLabels();
                upgradeCostTable.setVisible(true);
                int nextUnitLevel = teamUnit.getUnitLevel() + 1;
                bottomGroup.showUpgradeButton();
//                upgradeToLevelLabel.setText(upgradeToLevelText + nextUnitLevel);
//                upgradeToLevelLabel.setVisible(true);
            }
        } else {
            infoTable.hideUpgradeLabels();
//            upgradeToLevelLabel.setVisible(false);
        }

        /** если юнит не состоит в команде, отображаем кнопку "ВЫБРАТЬ" **/
        if (showSelectButton) {
            unitImage.getSelectButton().setVisible(true);
//            upgradeToLevelLabel.setVisible(true);
        } else {
            unitImage.getSelectButton().setVisible(false);
        }
        showBlockTable(isUnlock);
    }

    /**
     * скрываем окно апгрейда
     **/
    public void hideUpgradeScreen() {
        imageContainer.removeActor(unitImage);
        maxLevelReached.setVisible(false);
        setVisible(false);
    }

    /**
     * метод устанавливает значения параметров для улучшения юнита и его изображение
     **/
    public void setUnitUpgradeData(TeamUnit teamUnit) {
        this.teamUnit = teamUnit;
        infoTable.redraw(teamUnit.getUnitData());       // обновляем данные в infoTable
        unitNameLabel.setText(teamUnit.getName());      // обновляем имя юнита
        unitNameLabel.setPosition(container.getX() + (container.getWidth() - unitNameLabel.getWidth()) / 2,
                constructedWindow.getY() + constructedWindow.getHeight() - unitNameLabel.getHeight() - 32);

        /** получим объект unitImage - изображение со значками (уровень юнита и стоимость энергии) **/
        unitImage = teamUnit.getUnitImage();
        unitImage.setLevelValue(teamUnit.getUnitLevel());

        /** получим кнопку "ВЫБРАТЬ ЮНИТА", если юнит не состоит в игровой команде, а находится в коллекции**/
        selectUnitButton = unitImage.getSelectButton();
        selectUnitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showReplace();
                /** скрываем экран апгрейда **/
                hideUpgradeScreen();
            }
        });
        int nextUnitLevel = teamUnit.getUnitLevel();
        nextUnitLevel++;
//        upgradeToLevelLabel.setText(upgradeToLevelText + nextUnitLevel);
        bottomGroup.setNextLevel(nextUnitLevel);
//        bottomGroup.showUpgradeButton();
//        upgradeToLevelLabel.setText(upgradeToLevelText + newUnitLevel); // надпись над зеленой кнопкой ("улучишть до уровня" или "призвать")
        upgradeCost = teamUnit.getUnitLevel() * COST_UPGRADE;
        checkRecourcesAndCoinsCount();
    }

    /**
     * метод показывет надпись : "соберите n-ое кол-во звёзд для разблокировки
     **/
    public void showBlockTable(boolean isUnlock) {
        teamUnit.getUnitImage().getUnitLevelIcon().setVisible(false);
        /** проверим, если юнит не разблокирован, то отобразим таблицу с информацией о необходимом кол-ве звёзд (blockTale) **/
        if (isUnlock == false) {
            upgradeCostTable.setVisible(false);
            upgradeButton.setVisible(false);
            maxLevelReached.setVisible(false);
            int countStars = teamUnit.getUnitData().getStarsCount();        // получим кол-во звезд, необходимы для разблокировки юнита
            if (countStars > 0) {
                blockTable.setLabelStarsCount(countStars);
                blockTable.setVisible(true);
            }
        } else {
            if (teamUnit.getUnitData().isHired()) {
                if (teamUnit.getUnitLevel() < teamUnit.getMaxUnitLevel()) {
                    upgradeCostTable.setVisible(true);
                    upgradeButton.setVisible(true);
                }
                blockTable.setVisible(false);
                teamUnit.getUnitImage().getUnitLevelIcon().setVisible(true);
            }
        }
    }

    /**
     * убираем коллекцию с экрана и отображаем выбранного для замены юнита
     **/
    private void showReplace() {
        teamUpgradeScreen.showReplaceUnit(teamUnit);
    }

    private void showReplaceUnit(TeamUnit teamUnit) {
        teamUpgradeScreen.showReplaceUnit(teamUnit);
    }

    /**
     * метод для проверки достаточно ли монет и ресурсов для апргрейда или "покупки юнита"
     **/
    private void checkRecourcesAndCoinsCount() {
        if (coinsCount >= upgradeCost) {
            if (gameManager.getFoodCount() >= upgradeCostTable.getFoodCostValue() &&
                    gameManager.getIronCount() >= upgradeCostTable.getIronCostValue() &&
                    gameManager.getWoodCount() >= upgradeCostTable.getWoodCostValue()) {
                canBeUpgrade = true;
                upgradeButton.setCost(upgradeCost, true);
            } else {
                toastLabel.setText(noResources);
                canBeUpgrade = false;
//                applyActionsToToast();
                upgradeButton.setCost(upgradeCost, false);
            }
        } else {
            toastLabel.setText(noCoins);
            canBeUpgrade = false;
//            applyActionsToToast();
            upgradeButton.setCost(upgradeCost, false);
        }
    }

    /**
     * метод для изменения значений данных (ресурсов, количества монет, характеристик юнита)
     * после нажатия кнопки upgrade
     **/
    private void upgradeTeamEntity(TeamUnit teamUnit) {
        /** сделаем невидимыми надпись "УЛУЧШИТЬ ДО УРОВНЯ" и кнопку апгрейда,
         * до тех пор пока не закончится действие перемещения значков ресурсов
         */
        bottomGroup.hideUpgradeButton();

        /** применим действия к значкам ресурсов (движение и мерцание картинки юнита) **/
        resourcesTable.startActions();
        /** обновим параметры юнита, которого прокачиваем **/
        int nextUnitLevel = teamUnit.getUnitData().getUnitLevel();
        nextUnitLevel++;
        teamUnit.setUnitLevel(nextUnitLevel);
        teamUnit.addHealth(infoTable.getAddHealthValue());
        teamUnit.addDamage(infoTable.getAddDamageValue());

        if (teamUnit.getUnitLevel() >= teamUnit.getMaxUnitLevel()) {
            bottomGroup.showUpgradeButton();
            upgradeCostTable.setVisible(false);
            upgradeButton.setVisible(false);
            maxLevelReached.setVisible(true);
        }

        /** обновим данные юнита и сохраним его данные **/
        teamUnit.updateTeamEntityData();

        /** обновим количество монет и установим новое кол-во монет в панели монет **/
        coinsCount -= upgradeCost;
        gameManager.getCoinsPanel().setCoinsCount(coinsCount);
        gameManager.setCoinsCount(coinsCount);


        /** вычтем количество ресурсов потраченных на апгрейд **/
        gameManager.addFoodCount(-upgradeCostTable.getFoodCostValue());
        gameManager.addIronCount(-upgradeCostTable.getIronCostValue());
        gameManager.addWoodCount(-upgradeCostTable.getWoodCostValue());

        /** обновим значения в таблице ресурсов **/
        resourcesTable.updateResources(gameManager.getFoodCount(), gameManager.getIronCount(), gameManager.getWoodCount());

        // сохраним состояние игры с новым кол-вом ресурсов
        gameManager.saveGame();

        /**
         * метод устанавливает значения параметров для улучшения юнита и его изображение
         **/
        setUnitUpgradeData(teamUnit);
    }

    /**
     * метод для старта action для сообщения о недостаточном количестве монет или ресурсов
     **/
    private void applyActionsToToast() {
        /** isStartToastAction - флаг, т.е. если уже показывается сообщение, повторно чтобы не появлялось, пока есть на экране **/
        if (!isStartToastAction) {
            isStartToastAction = true;

            toastLabel.setVisible(true);
            toastLabel.setPosition(Warfare.V_WIDTH / 2 - 200, Warfare.V_HEIGHT / 2);

            Action checkEndOfAction = new Action() {
                @Override
                public boolean act(float delta) {
                    isStartToastAction = false;
                    return true;
                }
            };

            AlphaAction alphaActionStart = new AlphaAction();
            alphaActionStart.setAlpha(1);
            alphaActionStart.setDuration(0.02f);
            MoveToAction mta = new MoveToAction();
            mta.setPosition(Warfare.V_WIDTH / 2 - 200, Warfare.V_HEIGHT / 2 + 100);
            mta.setDuration(0.7f);
            AlphaAction alphaActionEnd = new AlphaAction();
            alphaActionEnd.setAlpha(0);
            alphaActionEnd.setDuration(1f);
            SequenceAction sa = new SequenceAction(alphaActionStart, mta, alphaActionEnd, checkEndOfAction);
            toastLabel.addAction(sa);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        /** проверим, завершилось ли действие перемещения значков на изображение юнита, при апгрейде **/
        if (resourcesTable.getIsEndAction() == true) {
            resourcesTable.setIsEndAction(false);

            /** сделаем видимыми надпись "УЛУЧШИТЬ ДО УРОВНЯ" и кнопку апгрейда **/
            if (teamUnit.getUnitLevel() < teamUnit.getMaxUnitLevel()) {
//                upgradeToLevelLabel.setVisible(true);
//                upgradeButton.setVisible(true);
                bottomGroup.showUpgradeButton();
            }
//            unitImage.clearActions();
            unitImage.startAction();
        }
    }
}

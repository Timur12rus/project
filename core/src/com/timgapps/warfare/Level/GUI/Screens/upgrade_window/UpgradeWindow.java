package com.timgapps.warfare.Level.GUI.Screens.upgrade_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.timgapps.warfare.Level.GUI.Screens.resources_view.ResourcesTable;
import com.timgapps.warfare.Level.GUI.Screens.upgrade_window.bottom_group.BottomGroup;
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
    private Label toastLabel;
    private float paddingLeft = 48;
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
    private boolean canBeUpgrade, canHire;
    private TeamUnit teamUnit;
    private ResourcesTable resourcesTable;
    private UnitLevelIcon unitLevelIcon;
    private TextureRegionDrawable textureRegionDrawableBg;
    //    private ColorButton selectUnitButton;  // кнопка "ВЫБРАТЬ" для игрового юнита,
    // если игровой юнит не состоит в коменде, а нах-ся в коллекции
    private TeamUpgradeScreen teamUpgradeScreen;
    private Label unitNameLabel;
    private String unitName;
    private BlockTable blockTable;
    private UpgradeCostTable upgradeCostTable;
    private Label maxLevelReached;  // надпись "максимальный уровнень достигнут"
    private BottomGroup bottomGroup;
    private ColorButton selectButton;
    private int nextUnitLevel;

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
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;
        Label.LabelStyle greenLabelStyle = new Label.LabelStyle();
        greenLabelStyle.fontColor = Color.FOREST;
        greenLabelStyle.font = Warfare.font20;
        Label.LabelStyle redLabelStyle = new Label.LabelStyle();
        redLabelStyle.fontColor = Color.RED;
        redLabelStyle.font = Warfare.font20;

        selectButton = new ColorButton("Select", ColorButton.YELLOW_BUTTON);
        selectButton.setPosition(-16, -selectButton.getHeight() - 32);

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

        // нижняя часть надписей (ниже таблицы с характеристиками)
        maxLevelReached = new Label("The unit has reached maximum level", labelStyle);
        toastLabel = new Label("", redLabelStyle);          // надпись с оповещением
        unitNameLabel = new Label("", labelStyle);

        // создадим таблицу с характеристиками юнита
        createUpgradeTableContainer();

        //  добавим группу с кнопкой апгрейда и кнопкой "нанять" (купить)
        bottomGroup = new BottomGroup();
        bottomGroup.setPosition(container.getX() + (550 - bottomGroup.getWidth()) / 2, 154);
        addActor(bottomGroup);

        // слушатель для кнопки "Апгрейда"
        bottomGroup.getUpgradeButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (canBeUpgrade) {
                    upgradeTeamEntity(teamUnit);
                } else {
                    applyActionsToToast();
                }
            }
        });

        // слушатель для кнопки "нанять" (купить)
        bottomGroup.getHireButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (canHire) {
                    hireUnit();
                } else {
                    applyActionsToToast();
                }
            }
        });
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
    private void createUpgradeTableContainer() {
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
        addActor(blockTable);
    }

    // метод для найма юнита
    public void hireUnit() {
        if (coinsCount >= teamUnit.getUnitPrice()) {
            coinsCount -= teamUnit.getUnitPrice();
            gameManager.getCoinsPanel().setCoinsCount(coinsCount);
            gameManager.setCoinsCount(coinsCount);
            teamUnit.getUnitData().setIsHired(true);

            // добавим текущий юнит в команду, если есть свободные места, если нет - оставим в коллекции
            teamUpgradeScreen.addUnitToTeamFromCollection(teamUnit);
            teamUpgradeScreen.redrawTeamTable();
            teamUpgradeScreen.redrawCollectionTable();
            // перерисуем кнопку-изображение (значок) юнита
            teamUnit.getUnitImageButton().redraw();
            show(teamUnit);                         // перерисовываем окно апгрейда юнита (то, в котором сейчас находимся)
            gameManager.updateTeam(team);
            gameManager.updateCollection();
            gameManager.saveGame();
        }
    }


    // перерисовывает окно апгрейда юнита
    public void redraw(TeamUnit teamUnit) {
        this.teamUnit = teamUnit;
        nextUnitLevel = 0;
        System.out.println("Show UpgradeWindow!");
        infoTable.redraw(teamUnit.getUnitData());       // обновляем данные в infoTable
        unitNameLabel.setText(teamUnit.getName());      // обновляем имя юнита
        System.out.println("Name = " + teamUnit.getName());      // обновляем имя юнита
        unitNameLabel.setPosition(container.getX() + (container.getWidth() - unitNameLabel.getWidth()) / 2,
                constructedWindow.getY() + constructedWindow.getHeight() - unitNameLabel.getHeight() - 32);

        /** получим объект unitImage - изображение со значками (уровень юнита и стоимость энергии) **/
        unitImage = teamUnit.getUnitImage();
        unitImage.clearActions();
        unitImage.setLevelValue(teamUnit.getUnitLevel());

        // обновим кол-во ресурсов в таблице
        resourcesTable.updateResources(gameManager.getFoodCount(), gameManager.getIronCount(), gameManager.getWoodCount());
        // обновим количество монет
        coinsCount = gameManager.getCoinsPanel().getCoinsCount();

        maxLevelReached.setVisible(false);      // скроем надпись "максимальный уровень достигнут"
        blockTable.setVisible(false);          // скроем сообщение "соберите кол-во звезд"
        bottomGroup.hideHireButton();            // скрываем кнопку "нанять" и надпись "нанять этого юнита"
        bottomGroup.hideUpgradeButton();        // скрываем кнопку "апгрейда" и надпись "улучшить до уровня"
        upgradeCostTable.setVisible(false);
        // добавим объект - изображение юнита со значком уровня юнита
        imageContainer.clear();
        imageContainer.addActor(unitImage);
        imageContainer.addActor(selectButton);
        infoTable.hideUpgradeLabels();
        selectButton.setVisible(false);

        boolean isUnlock = teamUnit.getUnitData().isUnlock();
        if (isUnlock == true) {               // если юнит разблокирован
            if (teamUnit.getUnitData().isHired()) {          // если юнит "призван" (куплен, не заблокирован)
//                unitImage.showLevelIcon();
                if (teamUnit.getUnitData().isHired() && gameManager.getCollection().equals(teamUnit)) {
                    System.out.println("EQUALS !");
                    selectButton.setVisible(true);
                } else {
                    selectButton.setVisible(false);
                }
                System.out.println("IS HIRED  = " + teamUnit.getUnitData().isHired());
                nextUnitLevel = teamUnit.getUnitLevel();
                nextUnitLevel++;
                if (teamUnit.getUnitLevel() >= teamUnit.getMaxUnitLevel()) {    // если уровень юнита >= максимальному уровню юнита
                    maxLevelReached.setVisible(true);
                } else {
                    System.out.println("Show LABELS UPGRADE!!!!");
                    upgradeCost = teamUnit.getUnitLevel() * COST_UPGRADE;       // стоимость апгрейда (монет)
                    checkRecourcesAndCoinsCount();
                    infoTable.showUpgradeLabels();
                    upgradeCostTable.setVisible(true);
                    bottomGroup.setNextLevel(nextUnitLevel);
                    bottomGroup.hideHireButton();
                    bottomGroup.showUpgradeButton();
                }
            } else {
                int hireCost = teamUnit.getUnitData().getUnitPrice();
                if (coinsCount >= hireCost) {
                    canHire = true;
                } else {
                    canHire = false;
                    toastLabel.setText(noCoins);
                }
                bottomGroup.setHireCost(teamUnit.getUnitData().getUnitPrice(), canHire);
                bottomGroup.hideUpgradeButton();
                bottomGroup.showHireButton();
//                unitImage.hideLevelIcon();
            }
        } else {
            blockTable.setLabelStarsCount(teamUnit.getUnitData().getStarsCount());      // установим значение, кол-ва звезд для открытия юнита
            blockTable.setVisible(true);
        }
    }

    // метод показывает экран с апгрейдом юнита
    public void show(TeamUnit teamUnit) {
//    public void show(TeamUnit teamUnit) {
        redraw(teamUnit);
        setVisible(true);
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
                bottomGroup.setUpgradeCost(upgradeCost, canBeUpgrade);
//                upgradeButton.setCost(upgradeCost, true);
            } else {
                toastLabel.setText(noResources);
                canBeUpgrade = false;
//                applyActionsToToast();
                bottomGroup.setUpgradeCost(upgradeCost, canBeUpgrade);
//                upgradeButton.setCost(upgradeCost, false);
            }
        } else {
            toastLabel.setText(noCoins);
            canBeUpgrade = false;
            bottomGroup.setUpgradeCost(upgradeCost, canBeUpgrade);
        }
    }

    private void checkCanHire() {
        if (coinsCount >= teamUnit.getUnitData().getUnitPrice()) {
            canHire = true;
        } else {
            canHire = false;
            toastLabel.setText(noCoins);
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
        teamUnit.setUnitLevel(nextUnitLevel);
        teamUnit.getUnitData().setUnitLevel(nextUnitLevel);
        teamUnit.addHealth(infoTable.getAddHealthValue());
        teamUnit.addDamage(infoTable.getAddDamageValue());

        if (teamUnit.getUnitLevel() >= teamUnit.getMaxUnitLevel()) {
            bottomGroup.showUpgradeButton();
            upgradeCostTable.setVisible(false);
            maxLevelReached.setVisible(true);
        } else {
            teamUnit.getUnitImage().setLevelValue(nextUnitLevel);
        }
        /** обновим данные юнита и сохраним его данные **/
        teamUnit.updateTeamEntityData();
        teamUnit.getUnitImageButton().redraw();
        teamUpgradeScreen.redrawTeamTable();     // перерисуем таблицу с команой юнитов
//        teamUpgradeScreen.updateTeam();     // перерисуем таблицу с команой юнитов

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
        /** проверим, завершилось ли дgaействие перемещения значков на изображение юнита, при апгрейде **/
        if (resourcesTable.getIsEndAction() == true) {
            resourcesTable.setIsEndAction(false);
            /** сделаем видимыми надпись "УЛУЧШИТЬ ДО УРОВНЯ" и кнопку апгрейда **/
            redraw(teamUnit);
//            show(teamUnit);
            unitImage.startAction();
        }
    }
}

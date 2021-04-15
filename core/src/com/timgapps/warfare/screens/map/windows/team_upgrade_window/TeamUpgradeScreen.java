package com.timgapps.warfare.screens.map.windows.team_upgrade_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.screens.map.windows.upgrade_window.UpgradeWindow;
import com.timgapps.warfare.screens.map.win_creator.ConstructedWindow;
import com.timgapps.warfare.screens.map.windows.team_upgrade_window.team_unit.TeamUnit;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

/**
 * Класс ЭКРАНА АПГРЕЙДА ИГРОВОЙ КОМАНДЫ
 **/

// экран с командой и коллекцией юнитов
public class TeamUpgradeScreen extends Group {
    public static final int ON_RESUME = 1;
    private UpgradeWindow upgradeWindow;
    private ConstructedWindow constructedWindow;
    private ImageButton closeButton;
    private ArrayList<TeamUnit> team;                 // массив юнитов из КОМАНДЫ
    private ArrayList<TeamUnit> unitCollection;       // массив юнитов из КОЛЛЕКЦИИ
    private TeamUnit replaceUnit;                     // заменяющий юнит из коллекциии, заменяет юнита в команде при процессе замены
    private float paddingLeft = 48;
    private float paddingTop = 24;
    private Label replaceUnitLabel;
    private String replaceUnitText;
    private Image replacedUnitImage;
    private boolean isReplaceActive = false; // переменная - флаг, нужна для того, чтобы изменить поведение листнера,
    // при замене юнита из коллекции в команду
    private TeamTable teamTable;            // таблица с комендой юнитов
    private Table tableCollection;      // таблица-контейнер, в него добавляется scroll с коллекцией юнитов
    private CollectionTable collectionTable;
    private Label teamLabel;
    private String teamText;
    private GameManager gameManager;
    private boolean toRaplaceUnitFromCollectionToTeam = false;
    private TeamUnit clickedTeamUnit;

    /**
     * передаем в конструктор список team, который содержит в себе КОМАНДУ ЮНИТОВ
     **/
    public TeamUpgradeScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        // через manager получим массив юнитов каманды
        team = gameManager.getTeam();
        teamText = "TEAM";
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font40;
        Label.LabelStyle greenLabelStyle = new Label.LabelStyle();
        greenLabelStyle.fontColor = Color.FOREST;
        greenLabelStyle.font = Warfare.font20;
        replaceUnitText = "Select unit to replace";
        replaceUnitLabel = new Label(replaceUnitText, greenLabelStyle);
        /** Создадим фон для окна, где будет отображаться команда**/
        constructedWindow = new ConstructedWindow(700, 550, "team");
//        background = new Image(Warfare.atlas.findRegion("teamScreen"));
        constructedWindow.setX((Warfare.V_WIDTH - constructedWindow.getWidth()) / 2); // устанавливаем позицию заголовка
        constructedWindow.setY(Warfare.V_HEIGHT / 2 - constructedWindow.getHeight() / 2);

        /** upgradeScreen - экран апгрейда юнита
         * - характеристики юнита
         * - таблица с количеством ресурсов и т.д.
         */
        upgradeWindow = new UpgradeWindow(gameManager, this);
        addActor(constructedWindow);

        teamLabel = new Label(teamText, labelStyle);
        teamLabel.setPosition(constructedWindow.getX() + (constructedWindow.getWidth() - teamLabel.getWidth()) / 2,
                constructedWindow.getY() + constructedWindow.getHeight() - teamLabel.getHeight() - 6);
        addActor(teamLabel);

        closeButton = constructedWindow.getCloseButton();
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideReplaceUnit();
                fire(new MessageEvent(ON_RESUME));
            }
        });

        /** добавим таблицу с КОМАНДОЙ ЮНИТОВ ***/
        teamTable = new TeamTable(team);
        teamTable.debug();
        teamTable.setPosition(constructedWindow.getX() + paddingLeft,
                constructedWindow.getY() + constructedWindow.getHeight() - 60 - paddingTop - teamTable.getHeight());
        addActor(teamTable);

        /** создадим массив КОЛЛЕЦКИИ ЮНИТОВ **/
        unitCollection = gameManager.getCollection();

        /** Создадим таблицу КОЛЛЕКЦИИ ЮНИТОВ
         * @param unitCollection - массив юнитов в коллекции
         * **/
        collectionTable = new CollectionTable(unitCollection);
//        // убрал пока
        for (int i = 0; i < team.size(); i++) {
            addClickListener(team.get(i));
        }
        for (int i = 0; i < unitCollection.size(); i++) {
            addClickListener(unitCollection.get(i));
        }
        collectionTable.debug();
        final Table scrollTable = new Table();
        scrollTable.left().top();
        scrollTable.add(collectionTable).width(collectionTable.getWidth()).height(collectionTable.getHeight());

        /** scroller - это окно прокрутки, сама прокрутка **/
        final ScrollPane scroller = new ScrollPane(scrollTable);
//        scroller.setFlickScroll(false);           !!!
//        scroller.setFadeScrollBars(true);
//        scroller.setScrollBarPositions(true, false);
//        scroller.cancelTouchFocus();
//        scroller.setCancelTouchFocus(true);
//        scroller.cancel();
        /** таблица - "ОКНО", в котором будет размещен scroller**/
        tableCollection = new Table();

        tableCollection.left().top();
        //table = new Table().debug();

        tableCollection.setWidth(collectionTable.getWidth());
        tableCollection.setHeight(270);
        tableCollection.add(scroller).fill().expand();
        tableCollection.setPosition(teamTable.getX(), constructedWindow.getY() + 30);
        addActor(tableCollection);
        addActor(upgradeWindow);
        replaceUnitLabel.setPosition(constructedWindow.getX() + constructedWindow.getWidth() / 2 - replaceUnitLabel.getWidth() / 2,
                constructedWindow.getY() + constructedWindow.getHeight() / 2 - replaceUnitLabel.getHeight());
        replaceUnitLabel.setVisible(false);
        addActor(replaceUnitLabel);

        replacedUnitImage = new Image(new TextureRegionDrawable(Warfare.atlas.findRegion("emptyButtonActive")));
        replacedUnitImage.setVisible(false);
        replacedUnitImage.setPosition(replaceUnitLabel.getX() + (replaceUnitLabel.getWidth() - replacedUnitImage.getWidth()) / 2,
                replaceUnitLabel.getY() - 128);
        addActor(replacedUnitImage);
    }

    // перерисовывает таблицу с юнитами в команде
    public void redrawTeamTable() {
        for (TeamUnit unit : team) {
            unit.clearCanUpgradeAction();
        }
        gameManager.checkCanUpgrade();
        teamTable.redraw(team);
    }

    // перерисовывает таблицу с юнитами в коллекции
    public void redrawCollectionTable() {
        for (TeamUnit unit : unitCollection) {
            unit.clearCanUpgradeAction();
        }
        collectionTable.redraw(unitCollection);
        final Table scrollTable = new Table();
        scrollTable.left().top();
        scrollTable.add(collectionTable).width(collectionTable.getWidth()).height(collectionTable.getHeight());
        final ScrollPane scroller = new ScrollPane(scrollTable);
        tableCollection.clearChildren();
        tableCollection.add(scroller).fill().expand();
    }

    /**
     * метод показывает заменяющего юнита (нового), которого игрок хочет добавить в команду
     **/
    public void showReplaceUnit(TeamUnit teamUnit) {
        /** делаем таблицу с командой юнитов невидимой, скрываем её **/
        tableCollection.setVisible(false);
        replaceUnit = teamUnit;   // юнит из коллекции, который заменит юнита в команде
        /** назначаем изображение выбранного для замены юнита **/
        replacedUnitImage.setDrawable((teamUnit.getUnitImage().getImage()).getDrawable());
        /** делаем видимыми надпись "ВЫБЕРИТЕ ЮНИТ ДЛЯ ЗАМЕНЫ" и изображение заменяющего юнита **/
        replaceUnitLabel.setVisible(true);
        replacedUnitImage.setVisible(true);
        /** устанавливаем флаг - true для того чтобы обозначить, что происходит процесс замены юнита **/
        isReplaceActive = true;
    }

    /**
     * метод для скрытия заменяемого юнита
     **/
    public void hideReplaceUnit() {
        System.out.println("Hide replace unit");
        tableCollection.setVisible(true);
        replaceUnitLabel.setVisible(false);
        replacedUnitImage.setVisible(false);
        System.out.println("replacedUNitImage.isVisible() = " + replacedUnitImage.isVisible());
        isReplaceActive = false;
    }

    /**
     * метод показывает запускает экран UpgradeWindow (экран с характеристиками юнита и ресурсами для апгрейда)
     **/
    private void showUpgradeWindow(TeamUnit teamUnit) {             // selectButton - false или true, показать кнопку
        upgradeWindow.show(teamUnit);
    }

    /**
     * слушатель для кнопок-юнитов в команде и юнитов в коллекции
     **/
    private void addClickListener(final TeamUnit teamUnit) {
        teamUnit.getUnitImageButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                /** если флаг isReplaceActive - false, то вызываем окно апгрейда, если true - заменяем юнит в команде на юнит из коллекции **/
                if (!isReplaceActive)
                    showUpgradeWindow(teamUnit);
                else {
                    toRaplaceUnitFromCollectionToTeam = true;
                    clickedTeamUnit = teamUnit;
                }
            }
        });
    }

    // метод добавляет юнита в команду, если есть свободные ячейки
    public void addUnitToTeamFromCollection(TeamUnit teamUnit) {
        // если есть свободные ячейки
        if (team.size() < teamTable.getMaxNumOfUnits()) {
            team.add(teamUnit);                      // добавляем юнита в команду
            unitCollection.remove(teamUnit);        // удаляем этот юнит из коллекции
        }
    }

    /**
     * метод для замены выбранного юнита из коллекции на юнита в команде
     * т.е. меняем юнита из команды на юнита из коллекции
     *
     * @param teamUnit - юнит, который находится в команде, которого будем менять на юнита из коллекциии
     **/
    private void replaceUnitFromCollectionToTeam(TeamUnit teamUnit) {
        System.out.println("replace unit");
        System.out.println("Before ");
        for (int i = 0; i < team.size(); i++) {
            System.out.println("team [" + i + "] = " + team.get(i));
        }
        for (int i = 0; i < unitCollection.size(); i++) {
            System.out.println("unitCollection [" + i + "] = " + unitCollection.get(i));
        }
        if (team.contains(teamUnit)) {
            int index = team.indexOf(teamUnit);
            int indexReplacedUnitInCollection = unitCollection.indexOf(replaceUnit);
            team.set(index, replaceUnit);
            System.out.println("team Table contains replaceUnit = " + team.contains(replaceUnit));
            System.out.println("replaceUnit index in collection table = " + unitCollection.indexOf(replaceUnit));
            unitCollection.set(indexReplacedUnitInCollection, teamUnit);
            System.out.println("unitCollection contains teamEntity = " + unitCollection.contains(teamUnit));
            System.out.println("unitCollection teamEntity = " + unitCollection.indexOf(teamUnit));
            /** обновим команду(team) и коллекцию (collection) и сохраним игру **/
            gameManager.updateTeam(team);
            gameManager.updateCollection();
//            gameManager.updateCollection(unitCollection);
            // сохраним состояние игры
            gameManager.saveGame();
        }
        System.out.println("After ");
        for (int i = 0; i < team.size(); i++) {
            System.out.println("team [" + i + "] = " + team.get(i));
        }
        for (int i = 0; i < unitCollection.size(); i++) {
            System.out.println("unitCollection [" + i + "] = " + unitCollection.get(i));
        }
        isReplaceActive = false;
//                // TODO проверить 22.03.2020
        replaceUnit = null;
        /** обновим данные таблицы команды и перестроим её **/
        updateTeamTable();
        /** обновим данные таблицы коллекции и перестроим её **/
        updateCollectionTable();

        hideReplaceUnit();
//        System.out.println("teamTable.get(0) = " + teamTable.getCell(team.get(0)));
    }

    /**
     * метод для обновления таблицы команды юнитов
     **/
    private void updateTeamTable() {
        System.out.println("UpdateTeamTable()!!!!!!!!!!!!!!!!!!!!!!!!!!");
        teamTable.redraw(team);
        Array<Cell> cells = teamTable.getCells();
        for (int i = 0; i < team.size(); i++) {
            System.out.println("team[" + i + "] = " + team.get(i).toString());
//            teamTable.getCells().get(i).setActor(team.get(i)).getW(team.get(i).width).height(team.get(i).height);
//            add(unitTeam.get(i).getUnitImageButton()).width(unitTeam.get(i).getImageButtonWidth()).height(unitTeam.get(i).getImageButtonHeight()).padLeft(12).padRight(12).left();
        }
    }

    /**
     * метод для обновления таблицы коллекции юнитов
     **/
    private void updateCollectionTable() {
        collectionTable.redraw(unitCollection);
        Array<Cell> cells = collectionTable.getCells();
        System.out.println("UpdateCollectionTable()!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        for (int i = 0; i < unitCollection.size(); i++) {
            System.out.println("collection[" + i + "] = " + unitCollection.get(i).toString());
//            cells.get(i).setActor(unitCollection.get(i));
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (toRaplaceUnitFromCollectionToTeam) {
            replaceUnitFromCollectionToTeam(clickedTeamUnit);
            toRaplaceUnitFromCollectionToTeam = false;
        }
    }

    public boolean isUpgradeScreenVisible() {
        return upgradeWindow.isVisible();
    }

    // метод скрывает окно с таблицей юнитов  (команду и коллекцию)
    public void hide() {
        this.setVisible(false);
        upgradeWindow.hideUpgradeWindow();
    }

    @Override
    public void clear() {
        super.clear();
        constructedWindow.clear();
    }
}

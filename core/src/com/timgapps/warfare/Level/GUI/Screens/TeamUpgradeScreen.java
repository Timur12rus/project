package com.timgapps.warfare.Level.GUI.Screens;

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
import com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow.CollectionTable;
import com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow.TeamTable;
import com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow.UpgradeScreen;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

/**
 * Класс ЭКРАНА АПГРЕЙДА ИГРОВОЙ КОМАНДЫ
 **/
public class TeamUpgradeScreen extends Group {
    public static final int ON_RESUME = 1;
    public Label screenTitle; // отображаем текст заголовка

    private UpgradeScreen upgradeScreen;

    private Image background;
    private ImageButton closeButton;

    private ArrayList<TeamEntity> team;                 // массив юнитов из КОМАНДЫ
    private ArrayList<TeamEntity> unitCollection;       // массив юнитов из КОЛЛЕКЦИИ
    private TeamEntity replaceUnit;                     // заменяющий юнит из коллекциии, заменяет юнита в команде при процессе замены

    private float teamTableWidth;
    private float teamTableHeight;

    private float paddingLeft = 48;
    private float paddingTop = 24;

    private Label replaceUnitLabel;
    private String replaceUnitText;

    private Image replacedUnitImage;

    private boolean isReplaceActive = false; // переменная - флаг, нужна для того, чтобы изменить поведение листнера,
    // при замене юнита из коллекции в команду

    private Table teamTable;            // таблица с комендой юнитов
    private Table tableCollection;      // таблица-контейнер, в него добавляется scroll с коллекцией юнитов
    private CollectionTable collectionTable;

    private Label teamLabel;
    private String teamText;
    private GameManager gameManager;


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
        background = new Image(Warfare.atlas.findRegion("teamScreen"));
        background.setX((Warfare.V_WIDTH - background.getWidth()) / 2); // устанавливаем позицию заголовка
        background.setY(Warfare.V_HEIGHT / 2 - background.getHeight() / 2);


        /** upgradeScreen - экран апгрейда юнита
         * - характеристики юнита
         * - таблица с количество ресурсов и т.д.
         */
        upgradeScreen = new UpgradeScreen(gameManager, this);
        addActor(background);

        teamLabel = new Label(teamText, labelStyle);
        teamLabel.setPosition(background.getX() + (background.getWidth() - teamLabel.getWidth()) / 2,
                background.getY() + background.getHeight() - teamLabel.getHeight() - 6);
        addActor(teamLabel);

        /** рабочий код 14.01.2020 **/
        closeButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("button_close")),
                new TextureRegionDrawable(Warfare.atlas.findRegion("button_close_dwn")));

        closeButton.setX(background.getX() + background.getWidth() - closeButton.getWidth() - 28);
        closeButton.setY(background.getY() + background.getHeight() - closeButton.getHeight() - 12);
        addActor(closeButton);

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
        teamTable.setPosition(background.getX() + paddingLeft,
                background.getY() + background.getHeight() - 60 - paddingTop - teamTable.getHeight());
        addActor(teamTable);

        /** создадим массив КОЛЛЕЦКИИ ЮНИТОВ **/
        unitCollection = gameManager.getCollection();

        /** Создадим таблицу КОЛЛЕКЦИИ ЮНИТОВ
         * @param unitCollection - массив юнитов в коллекции
         * **/
        collectionTable = new CollectionTable(unitCollection);
//        Table collectionTable = new CollectionTable(unitCollection).debug();

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


        /** таблица - "ОКНО", в котором будет размещен scroller**/
        tableCollection = new Table();

        tableCollection.left().top();
        //table = new Table().debug();

        tableCollection.setWidth(collectionTable.getWidth());
        tableCollection.setHeight(270);
        tableCollection.add(scroller).fill().expand();

        tableCollection.setPosition(teamTable.getX(), background.getY() + 30);
        addActor(tableCollection);
        addActor(upgradeScreen);

        replaceUnitLabel.setPosition(background.getX() + background.getWidth() / 2 - replaceUnitLabel.getWidth() / 2,
                background.getY() + background.getHeight() / 2 - replaceUnitLabel.getHeight());

        replaceUnitLabel.setVisible(false);
        addActor(replaceUnitLabel);

        replacedUnitImage = new Image(new TextureRegionDrawable(Warfare.atlas.findRegion("emptyButtonActive")));
        replacedUnitImage.setVisible(false);
        replacedUnitImage.setPosition(replaceUnitLabel.getX() + (replaceUnitLabel.getWidth() - replacedUnitImage.getWidth()) / 2,
                replaceUnitLabel.getY() - 128);
        addActor(replacedUnitImage);
    }

    /**
     * метод показывает заменяющего юнита (нового), которого игрок хочет добавить в команду
     **/
    public void showReplaceUnit(TeamEntity teamEntity) {

        /** делаем таблицу с командой юнитов невидимой, скрываем её **/
        tableCollection.setVisible(false);
        replaceUnit = teamEntity;   // юнит из коллекции, который заменит юнита в команде

        /** назначаем изображение выбранного для замены юнита **/
        replacedUnitImage.setDrawable((teamEntity.getUnitImage().getImage()).getDrawable());

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

    public void hide() {
        this.setVisible(false);
    }

    /**
     * метод показывает запускает экран UpgradeScreen
     **/
    private void showUpgradeScreen(TeamEntity teamEntity) {             // selectButton - false или true, показать кнопку
        upgradeScreen.setUnitUpgradeData(teamEntity);
        boolean showSelectButton = false;       // показывать ли кнопеу "ВЫБРАТЬ" в окне информации о юните
        if (unitCollection.contains(teamEntity)) {      // если юнит находится в "КОЛЛЕКЦИИ", то покажем кнопку "ВЫБРАТЬ"
            showSelectButton = true;
        }
        upgradeScreen.showUpgradeScreen(showSelectButton);
//        upgradeScreen.showUpgradeScreen(teamEntity);
    }


    /**
     * слушатель для кнопок-юнитов в команде и юнитов в коллекции
     **/
    private void addClickListener(final TeamEntity teamEntity) {
        teamEntity.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                /** если флаг isReplaceActive - false, то вызываем окно апгрейда, если true - заменяем юнит в команде на юнит из коллекции **/
                if (!isReplaceActive)
                    showUpgradeScreen(teamEntity);
                else replaceUnitFromCollectionToTeam(teamEntity);
            }
        });
    }

    /**
     * метод для замены выбранного юнита из коллекции на юнита в команде
     * т.е. меняем юнита из команды на юнита из коллекции
     *
     * @param teamEntity - юнит, который находится в команде, которого будем менять на юнита из коллекциии
     **/
    private void replaceUnitFromCollectionToTeam(TeamEntity teamEntity) {
//        for (int i = 0; i < team.size(); i++) {
//            if (team.get(i).equals(teamEntity)) {
////            if (team.get(i).equals(teamEntity)) {
//                System.out.println("teamEntity = " + teamEntity.toString());
//                System.out.println("replaceUnit = " + replaceUnit.toString());
//                team.set(i, replaceUnit);
        System.out.println("replace unit");

        System.out.println("Before ");
        for (int i = 0; i < team.size(); i++) {
            System.out.println("team [" + i + "] = " + team.get(i));
        }
        for (int i = 0; i < unitCollection.size(); i++) {
            System.out.println("unitCollection [" + i + "] = " + unitCollection.get(i));
        }

        if (team.contains(teamEntity)) {
            int index = team.indexOf(teamEntity);
            team.set(index, replaceUnit);
            System.out.println("team Table contains replaceUnit = " + team.contains(replaceUnit));
            System.out.println("replaceUnit index in collection table = " + unitCollection.indexOf(replaceUnit));

            int indexReplacedUnitInCollection = unitCollection.indexOf(replaceUnit);
            unitCollection.set(indexReplacedUnitInCollection, teamEntity);
            System.out.println("unitCollection contains teamEntity = " + unitCollection.contains(teamEntity));
            System.out.println("unitCollection teamEntity = " + unitCollection.indexOf(teamEntity));

            gameManager.updateTeam(team);
            gameManager.updateCollection(unitCollection);
        }

        System.out.println("After ");
        for (int i = 0; i < team.size(); i++) {
            System.out.println("team [" + i + "] = " + team.get(i));
        }
        for (int i = 0; i < unitCollection.size(); i++) {
            System.out.println("unitCollection [" + i + "] = " + unitCollection.get(i));
        }


//                for (int j = 0; j < unitCollection.size(); j++) {
//                    if (unitCollection.get(j).equals(replaceUnit)) {
//                        unitCollection.set(j, teamEntity);
//                    }
//                }
        isReplaceActive = false;

//                // TODO проверить 22.03.2020
        replaceUnit = null;

        updateTeamTable();
        updateCollectionTable();

        hideReplaceUnit();

        System.out.println("teamTable.get(0) = " + teamTable.getCell(team.get(0)));


//        tableCollection.setVisible(true);
//        replacedUnitImage.setVisible(false);
//        replaceUnitLabel.setVisible(false);


//        collectionTable.setVisible(true);
    }

    /**
     * метод для обновления таблицы команды юнитов
     **/
    private void  updateTeamTable() {
//        teamTable.clearChildren();
        Array<Cell> cells = teamTable.getCells();
        for (int i = 0; i < team.size(); i++) {
//            Cell<TeamEntity> cell = teamTable.getCell(team.get(i));
//            cells.get(i).clearActor();
//            cells.get(i).setActor(team.get(i));
            teamTable.getCells().get(i).setActor(team.get(i));
        }
    }

    /**
     * метод для обновления таблицы коллекции юнитов
     **/
    private void updateCollectionTable() {
        Array<Cell> cells = collectionTable.getCells();
        System.out.println("Update Collection Table");
        for (int i = 0; i < unitCollection.size(); i++) {
//          Cell<TeamEntity> cell = collectionTable.getCell(unitCollection.get(i));

//            cells.get(i).clearActor();
            cells.get(i).setActor(unitCollection.get(i));
            System.out.println(unitCollection.get(i).toString());
//            collectionTable.getCell(unitCollection.get(i));
        }
    }

//    public void updateTeam() {
//        gameManager.updateTeam();
//    }
//
//    public void updateCollectionTeam() {
//        gameManager.updateCollection();
//    }
}

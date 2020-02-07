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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.boontaran.MessageEvent;
import com.timgapps.warfare.Level.GUI.Screens.ResourcesView.CollectionTable;
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

    private Table table;

    private static final String reallyLongString = "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n";
//            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
//            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"

    private Image replacedUnitImage;

    private boolean isReplaceActive = false; // переменная - флаг, нужна для того, чтобы изменить поведение листнера,
    // при замене юнита из коллекции команду

    private Table teamTable;
    private Table collectionTable;


    /**
     * передаем в конструктор список team, который содержит в себе КОМАНДУ ЮНИТОВ
     **/
    public TeamUpgradeScreen(GameManager gameManager) {

        team = gameManager.getTeam();
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

        upgradeScreen = new UpgradeScreen(gameManager, this);

        addActor(background);

        /** рабочий код 14.01.2020 **/
//        this.team = new ArrayList<TeamEntity>();
//        team.add(new TeamEntity(TeamEntity.ARCHER));

        closeButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("button_close")),
                new TextureRegionDrawable(Warfare.atlas.findRegion("button_close_dwn")));

        closeButton.setX(background.getX() + background.getWidth() - closeButton.getWidth() - 28);
        closeButton.setY(background.getY() + background.getHeight() - closeButton.getHeight() - 12);
        addActor(closeButton);

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new MessageEvent(ON_RESUME));
            }
        });


        teamTable = new Table();
        teamTable.debug();
        for (int i = 0; i < team.size(); i++) {
            teamTableWidth += team.get(i).getWidth() + 24;
            teamTable.setWidth(teamTableWidth);
            teamTable.add(team.get(i)).width(team.get(i).getWidth()).height(team.get(i).getHeight()).padLeft(12).padRight(12);
            addClickListener(team.get(i));

//            teamTable.add(team.get(i)).width(team.get(i).getWidth()).height(team.get(i).getHeight()).padLeft(12).padRight(12);
        }
        teamTableHeight = team.get(0).getHeight();  //  высота таблицы в px


        /** создадим массив КОЛЛЕЦКИИ ЮНИТОВ **/
        unitCollection = gameManager.getCollection();


        /** добавим горизонтальную серую черту-разделитель **/
        float deltaHeight = 0;
        teamTable.row();
        for (int i = 1; i < 6; i++) {
            Image line = new Image(Warfare.atlas.findRegion("line"));
            deltaHeight = line.getHeight();
            teamTable.add(line).width(line.getWidth()).height(line.getHeight()).padTop(16);
        }
        teamTableHeight += deltaHeight + 16;
        teamTable.setHeight(teamTableHeight);

//        teamTable.debug();

        teamTable.setPosition(background.getX() + paddingLeft,
                background.getY() + background.getHeight() - 60 - paddingTop - teamTableHeight);
        addActor(teamTable);


        /** Добавляем панель (таблицу) с реурсами(ПИЩА, ЖЕЛЕЗО, ДЕРЕВО) **/
//        ResourcesTable resourcesTable = new ResourcesTable(10, 5, 0);
//        resourcesTable.setPosition(teamTable.getX() + teamTableWidth + 24,
//                teamTable.getY() - (resourcesTable.getHeight() - teamTable.getHeight()));
//        addActor(resourcesTable);


        final Label text = new Label(reallyLongString, labelStyle);
        text.setAlignment(Align.center);
        text.setWrap(true);


        Image unitImage1 = new Image(Warfare.atlas.findRegion("thorActive"));
        Image unitImage2 = new Image(Warfare.atlas.findRegion("unitButtonDark"));
        Image unitImage3 = new Image(Warfare.atlas.findRegion("unitButtonDark"));
        Image unitImage4 = new Image(Warfare.atlas.findRegion("unitButtonDark"));
        Image unitImage5 = new Image(Warfare.atlas.findRegion("unitButtonDark"));

        Image unitImage6 = new Image(Warfare.atlas.findRegion("unitButtonDark"));
        Image unitImage7 = new Image(Warfare.atlas.findRegion("unitButtonDark"));
        Image unitImage8 = new Image(Warfare.atlas.findRegion("unitButtonDark"));
        Image unitImage9 = new Image(Warfare.atlas.findRegion("unitButtonDark"));
        Image unitImage10 = new Image(Warfare.atlas.findRegion("unitButtonDark"));


        /** Создадим таблицк КОЛЛЕКЦИИ ЮНИТОВ
         * @param unitCollection - массив юнитов в коллекции
         * **/
        collectionTable = new CollectionTable(unitCollection);
//        Table collectionTable = new CollectionTable(unitCollection).debug();

        for (int i = 0; i < unitCollection.size(); i++) {
            addClickListener(unitCollection.get(i));
        }

        final Table scrollTable = new Table();
//        final Table scrollTable = new Table().debug();
        scrollTable.add(collectionTable).left().expand().top();

//        scrollTable.add(unitImage).width(unitImage.getWidth()).height(unitImage.getHeight()).left();
//        scrollTable.add(unitImage).width(unitImage.getWidth()).height(unitImage.getHeight()).left();
        scrollTable.row();
//        scrollTable.add(text);

        final ScrollPane scroller = new ScrollPane(scrollTable);

        table = new Table();
//        final Table table = new Table().debug();
        table.setWidth(teamTableWidth);

        table.setHeight(270);
//        table.setHeight(96 + paddingTop);

        table.add(scroller).fill().expand();


        table.setPosition(teamTable.getX(), background.getY() + 30);
        addActor(table);

        addActor(upgradeScreen);
//        upgradeScreen.setPosition(table.getX(), table.getY());


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
        table.setVisible(false);
        replaceUnit = teamEntity;   // юнит из коллекции, который заменит юнита в команде

        /** назначаем изображение выбранного для замены юнита **/
        replacedUnitImage.setDrawable((teamEntity.getUnitImage().getImage()).getDrawable());

        /** делаем видимыми надпись "ВЫБЕРИТЕ ЮНИТ ДЛЯ ЗАМЕНЫ" и изображение заменяющего юнита **/
        replaceUnitLabel.setVisible(true);
        replacedUnitImage.setVisible(true);

        /** устанавливаем флаг - true для того чтобы обозначить, что происходит процесс замены юнита **/
        isReplaceActive = true;

//        System.out.println("Show Replace Unit");
//        if (replacedUnitImage == null)


    }

    public void hide() {
//        levelOfDifficulty = " ";
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
//        TeamEntity tempTeamEntity = teamEntity;

        for (int i = 0; i < team.size(); i++) {
            if (team.get(i).equals(teamEntity)) {
//                tempTeamEntity = team.get(i);
                team.set(i, replaceUnit);

                for (int j = 0; j < unitCollection.size(); j++) {
                    if (unitCollection.get(j).equals(replaceUnit)) {
                        unitCollection.set(j, teamEntity);
                    }
                }
                isReplaceActive = false;
                updateTeamTable();
                updateCollectionTable();
                table.setVisible(true);
            }
        }
        replacedUnitImage.setVisible(false);
        replaceUnitLabel.setVisible(false);
    }

    private void addClickUnitCollectionListener(final TeamEntity teamEntity) {
        teamEntity.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                showUpgradeScreen(teamEntity);
            }
        });
    }

    /**
     * метод для обновления таблицы команды юнитов
     **/
    private void updateTeamTable() {
//        teamTable.clearChildren();
        for (int i = 0; i < team.size(); i++) {
            Cell<TeamEntity> cell = teamTable.getCell(team.get(i));

            Array<Cell> cells = teamTable.getCells();
            cells.get(i).clearActor();
            cells.get(i).setActor(team.get(i));
        }
    }

    /**
     * метод для обновления таблицы коллекции юнитов
     **/
    private void updateCollectionTable() {
        for (int i = 0; i < unitCollection.size(); i++) {
            Cell<TeamEntity> cell = collectionTable.getCell(unitCollection.get(i));

            Array<Cell> cells = collectionTable.getCells();
            cells.get(i).clearActor();
            cells.get(i).setActor(unitCollection.get(i));
        }
    }
}

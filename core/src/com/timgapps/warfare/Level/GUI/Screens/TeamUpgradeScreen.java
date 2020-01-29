package com.timgapps.warfare.Level.GUI.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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

    private ArrayList<TeamEntity> team;                  // массив юнитов из КОМАНДЫ
    private ArrayList<TeamEntity> unitCollection;       // массив юнитов из КОЛЛЕКЦИИ

    private float teamTableWidth;
    private float teamTableHeight;

    private float paddingLeft = 48;
    private float paddingTop = 24;

    private static final String reallyLongString = "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n";
//            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
//            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n";


    /**
     * передаем в конструктор список team, который содержит в себе КОМАНДУ ЮНИТОВ
     **/
    public TeamUpgradeScreen(GameManager gameManager) {

        team = gameManager.getTeam();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font40;


        /** Создадим фон для окна, где будет отображаться команда**/
        background = new Image(Warfare.atlas.findRegion("teamScreen"));
        background.setX((Warfare.V_WIDTH - background.getWidth()) / 2); // устанавливаем позицию заголовка
        background.setY(Warfare.V_HEIGHT / 2 - background.getHeight() / 2);

        upgradeScreen = new UpgradeScreen(gameManager);

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


        final Table teamTable = new Table();
        for (int i = 0; i < team.size(); i++) {
            teamTableWidth += team.get(i).getWidth() + 24;
            teamTable.setWidth(teamTableWidth);
            teamTable.add(team.get(i)).width(team.get(i).getWidth()).height(team.get(i).getHeight()).padLeft(12).padRight(12);
            addClickListener(team.get(i));

//            teamTable.add(team.get(i)).width(team.get(i).getWidth()).height(team.get(i).getHeight()).padLeft(12).padRight(12);
        }
        teamTableHeight = team.get(0).getHeight();  //  высота таблицы в px


        /** создадим массив КОЛЛЕЦКИИ ЮНИТОВ **/
        unitCollection = new ArrayList<TeamEntity>();
        unitCollection.add(new TeamEntity(TeamEntity.THOR));

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
        Table collectionTable = new CollectionTable(unitCollection);
//        Table collectionTable = new CollectionTable(unitCollection).debug();


//        Table collectionTable = new Table().debug();

//        collectionTable.add(unitImage1).width(unitImage1.getWidth()).height(unitImage1.getHeight()).left().padLeft(12).padRight(12);
//        collectionTable.add(unitImage2).width(unitImage2.getWidth()).height(unitImage2.getHeight()).left().padLeft(12).padRight(12);
//        collectionTable.add(unitImage3).width(unitImage2.getWidth()).height(unitImage2.getHeight()).left().padLeft(12).padRight(12);
//        collectionTable.add(unitImage4).width(unitImage2.getWidth()).height(unitImage2.getHeight()).left().padLeft(12).padRight(12);
//        collectionTable.add(unitImage5).width(unitImage2.getWidth()).height(unitImage2.getHeight()).left().padLeft(12).padRight(12);
//
//        collectionTable.row();
//
//        collectionTable.add(unitImage6).width(unitImage1.getWidth()).height(unitImage1.getHeight()).left().padLeft(12).padRight(12).padTop(paddingTop);
//        collectionTable.add(unitImage7).width(unitImage2.getWidth()).height(unitImage2.getHeight()).left().padLeft(12).padRight(12).padTop(paddingTop);
//        collectionTable.add(unitImage8).width(unitImage2.getWidth()).height(unitImage2.getHeight()).left().padLeft(12).padRight(12).padTop(paddingTop);
//        collectionTable.add(unitImage9).width(unitImage2.getWidth()).height(unitImage2.getHeight()).left().padLeft(12).padRight(12).padTop(paddingTop);
//        collectionTable.add(unitImage10).width(unitImage2.getWidth()).height(unitImage2.getHeight()).left().padLeft(12).padRight(12).padTop(paddingTop);
//
//        collectionTable.row();
//
//        collectionTable.add(unitImage6).width(unitImage1.getWidth()).height(unitImage1.getHeight()).left().padLeft(12).padRight(12).padTop(paddingTop);
//        collectionTable.add(unitImage7).width(unitImage2.getWidth()).height(unitImage2.getHeight()).left().padLeft(12).padRight(12).padTop(paddingTop);
//        collectionTable.add(unitImage8).width(unitImage2.getWidth()).height(unitImage2.getHeight()).left().padLeft(12).padRight(12).padTop(paddingTop);
//        collectionTable.add(unitImage9).width(unitImage2.getWidth()).height(unitImage2.getHeight()).left().padLeft(12).padRight(12).padTop(paddingTop);
//        collectionTable.add(unitImage10).width(unitImage2.getWidth()).height(unitImage2.getHeight()).left().padLeft(12).padRight(12).padTop(paddingTop);


//        collectionTable.setHeight(96 + paddingTop);


        final Table scrollTable = new Table();
//        final Table scrollTable = new Table().debug();
        scrollTable.add(collectionTable).left().expand().top();

//        scrollTable.add(unitImage).width(unitImage.getWidth()).height(unitImage.getHeight()).left();
//        scrollTable.add(unitImage).width(unitImage.getWidth()).height(unitImage.getHeight()).left();
        scrollTable.row();
//        scrollTable.add(text);

        final ScrollPane scroller = new ScrollPane(scrollTable);

        final Table table = new Table();
//        final Table table = new Table().debug();
        table.setWidth(teamTableWidth);

        table.setHeight(270);
//        table.setHeight(96 + paddingTop);

        table.add(scroller).fill().expand();


        table.setPosition(teamTable.getX(), background.getY() + 30);
        addActor(table);

        addActor(upgradeScreen);
//        upgradeScreen.setPosition(table.getX(), table.getY());

    }

    public void hide() {
//        levelOfDifficulty = " ";
        this.setVisible(false);
    }

    private void showUpgradeScreen(TeamEntity teamEntity) {
        upgradeScreen.setUnitUpgradeData(teamEntity);
        upgradeScreen.showUpgradeScreen();
//        upgradeScreen.showUpgradeScreen(teamEntity);

    }

    private void addClickListener(final TeamEntity teamEntity) {
        teamEntity.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                showUpgradeScreen(teamEntity);
            }
        });
    }
}

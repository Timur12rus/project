package com.timgapps.warfare.Level.GUI.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Level.GUI.Screens.ResourcesView.ResourcesTable;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

/**
 * Класс ЭКРАНА АПГРЕЙДА ИГРОВОЙ КОМАНДЫ
 **/
public class TeamUpgradeScreen extends Group {
    public static final int ON_RESUME = 1;
    public Label screenTitle; // отображаем текст заголовка

    private Image background;

    private ArrayList<TeamEntity> team;

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
    public TeamUpgradeScreen(ArrayList<TeamEntity> team) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font40;

        /** Создадим фон для окна, где будет отображаться команда**/
        background = new Image(Warfare.atlas.findRegion("teamScreen"));
        background.setX((Warfare.V_WIDTH - background.getWidth()) / 2); // устанавливаем позицию заголовка
        background.setY(Warfare.V_HEIGHT / 2 - background.getHeight() / 2);

        addActor(background);

        /** рабочий код 14.01.2020 **/
//        this.team = new ArrayList<TeamEntity>();
//        team.add(new TeamEntity(TeamEntity.ARCHER));


        final Table teamTable = new Table();
        for (int i = 0; i < team.size(); i++) {
            teamTableWidth += team.get(i).getWidth() + 24;
            teamTable.setWidth(teamTableWidth);
            teamTable.add(team.get(i)).width(team.get(i).getWidth()).height(team.get(i).getHeight()).padLeft(12).padRight(12);
//            teamTable.add(team.get(i)).width(team.get(i).getWidth()).height(team.get(i).getHeight()).padLeft(12).padRight(12);
        }
        teamTableHeight = team.get(0).getHeight();  //  высота таблицы в px



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

        teamTable.debug();

        teamTable.setPosition(background.getX() + paddingLeft,
                background.getY() + background.getHeight() - 60 - paddingTop - teamTableHeight);
        addActor(teamTable);

        ResourcesTable resourcesTable = new ResourcesTable(10, 5, 0);
//        /** Таблица ресурсов **/
//        final Table resourcesTable = new Table();
//        resourcesTable.debug();
//        resourcesTable.add(new Image(Warfare.atlas.findRegion("food_icon"))).width(64).height(64);
//        resourcesTable.row();
////        resourcesTable.add(new Image(Warfare.atlas.findRegion("iron_icon"))).width(64).height(64);
//        resourcesTable.add(new Image(Warfare.atlas.findRegion("iron_icon"))).width(64).height(64).padTop(16);
//        resourcesTable.row();
////        resourcesTable.add(new Image(Warfare.atlas.findRegion("iron_icon"))).width(64).height(64);
//        resourcesTable.add(new Image(Warfare.atlas.findRegion("wood_icon"))).width(64).height(64).padTop(16);
//        resourcesTable.setHeight(224);
//        resourcesTable.setPosition(teamTable.getX() + teamTableWidth + 64,
//                teamTable.getY() - (resourcesTable.getHeight() - teamTable.getHeight()));
//
//        System.out.println("resourceTableHeight = " + resourcesTable.getHeight());

//        resourcesTable.debug();

        resourcesTable.setPosition(teamTable.getX() + teamTableWidth + 24,
                teamTable.getY() - (resourcesTable.getHeight() - teamTable.getHeight()));
        addActor(resourcesTable);



        final Label text = new Label(reallyLongString, labelStyle);
        text.setAlignment(Align.center);
        text.setWrap(true);


//        final Label text2 = new Label("This is a short string!", labelStyle);
//        text2.setAlignment(Align.center);
//        text2.setWrap(true);
//        final Label text3 = new Label(reallyLongString, labelStyle);
//        text3.setAlignment(Align.center);
//        text3.setWrap(true);


        final Table scrollTable = new Table();
        scrollTable.add(text);
        scrollTable.row();

        final ScrollPane scroller = new ScrollPane(scrollTable);
//        final Table table = new Table();
        final Table table = new Table().debug();
        table.setWidth(teamTableWidth);
        table.setHeight(270);

        table.add(scroller).fill().expand();
        table.setPosition(teamTable.getX(), background.getY() + 30);
        addActor(table);

    }
}

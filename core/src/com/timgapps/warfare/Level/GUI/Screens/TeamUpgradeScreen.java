package com.timgapps.warfare.Level.GUI.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class TeamUpgradeScreen extends Group {
    public static final int ON_RESUME = 1;
    public Label screenTitle; // отображаем текст заголовка

    private Image background;

    private Group teamEntity;
    private Group teamEntity2;

    private ArrayList<TeamEntity> team;

    private static final String reallyLongString = "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n";


    public TeamUpgradeScreen() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font40;

        /** Создадим фон для окна, где будет отображаться команда**/
        background = new Image(Warfare.atlas.findRegion("mission_start_bg"));
        background.setX((Warfare.V_WIDTH - background.getWidth()) / 2); // устанавливаем позицию заголовка
        background.setY(Warfare.V_HEIGHT / 2 - background.getHeight() / 2);

        addActor(background);


        team = new ArrayList<TeamEntity>();

        teamEntity = new TeamEntity(TeamEntity.GNOME);
        teamEntity2 = new TeamEntity(TeamEntity.ARCHER);
//        addActor(gnomeImage);


        final Label text = new Label(reallyLongString, labelStyle);
        text.setAlignment(Align.center);
        text.setWrap(true);
        final Label text2 = new Label("This is a short string!", labelStyle);
        text2.setAlignment(Align.center);
        text2.setWrap(true);
        final Label text3 = new Label(reallyLongString, labelStyle);
        text3.setAlignment(Align.center);
        text3.setWrap(true);

        final Table scrollTable = new Table();

        final Table teamTable = new Table();
        teamTable.add(teamEntity).width(teamEntity.getWidth()).height(teamEntity.getHeight());
        teamTable.add(teamEntity2).width(teamEntity2.getWidth()).height(teamEntity2.getHeight());
        teamTable.debug();

        scrollTable.add(teamTable);
        scrollTable.row();
        scrollTable.add(text);
        scrollTable.row();
//        scrollTable.add(text2);
//        scrollTable.row();
//        scrollTable.add(text3);

        final ScrollPane scroller = new ScrollPane(scrollTable);


        final Table table = new Table().debug();
        table.setWidth(400);
        table.setHeight(250);
//        table.setFillParent(true);
//        table.add(background);
        table.add(scroller).fill().expand();

        table.setPosition(background.getX() + 16, background.getY() + 32);
        addActor(table);

    }
}

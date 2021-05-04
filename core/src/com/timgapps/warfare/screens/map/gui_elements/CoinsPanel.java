package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Warfare;

public class CoinsPanel extends Group {
    private Table table;
    private int coinsCount;     // кол-во монет
    private Label coinsValueLabel;
    private Image coinIcon;
    private TextureRegionDrawable background;
    private Vector2 pos;            // "позиция"
    private GameManager gameManager;

    public CoinsPanel(GameManager gameManager) {
        this.gameManager = gameManager;
        /** Изображение ЗНАЧОК МОНЕТА **/
        coinIcon = new Image(Warfare.atlas.findRegion("coin_icon"));
        background = new TextureRegionDrawable(Warfare.atlas.findRegion("coinsPanel"));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.YELLOW;
        labelStyle.font = Warfare.font30;

        coinsValueLabel = new Label("", labelStyle);
        table = new Table().debug();
        table.align(Align.right);
        table.setWidth(background.getRegion().getRegionWidth());
        table.setHeight(46);
        table.setBackground(background);
        table.add(coinsValueLabel).expand().right();
        table.add(coinIcon).width(coinIcon.getWidth());
        setWidth(table.getWidth());
        setHeight(table.getHeight());
        addActor(table);
    }

    // метод обновляет кол-во монет у игрока
    public void redraw() {
        coinsCount = gameManager.getCoinsCount();
        coinsValueLabel.setText("" + coinsCount);
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Table getTable() {
        return table;
    }

    public void setCoinsCount(int coins) {
        coinsCount = coins;
        coinsValueLabel.setText("" + coinsCount);
    }

    public int getCoinsCount() {
        return coinsCount;
    }

    public Vector2 getPos() {
        return pos;
    }

}

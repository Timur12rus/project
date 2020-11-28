package com.timgapps.warfare.screens.map.gui_elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Warfare;

public class CoinsPanel extends Group {
    private Table table;
    private int coinsCount;
    private Label coinsValueLabel;
    private Image coinIcon;
    private TextureRegionDrawable background;

    public CoinsPanel(int coinsCount) {
        this.coinsCount = coinsCount;
        /** Изображение ЗНАЧОК МОНЕТА **/
        coinIcon = new Image(Warfare.atlas.findRegion("coin_icon"));
        background = new TextureRegionDrawable(Warfare.atlas.findRegion("coinsPanel"));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.YELLOW;
        labelStyle.font = Warfare.font20;
        coinsValueLabel = new Label("" + coinsCount, labelStyle);
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

    public Table getTable() {
        return table;
    }

    public void addCoins(int coins) {
        coinsCount += coins;
        coinsValueLabel.setText("" + coinsCount);
    }

    public void setCoinsCount(int coins) {
        coinsCount = coins;
        coinsValueLabel.setText("" + coinsCount);
    }

    public void startAddCoinsAction() {
        SequenceAction moveAction = new SequenceAction(Actions.fadeIn(0),
                Actions.moveTo(-4, -4, 0.2f),
                Actions.moveTo(0, 0, 0.2f)
        );
        SequenceAction sizeAction = new SequenceAction(
                Actions.sizeBy(8, 8, 0.2f),
                Actions.sizeBy(-8, -8, 0.2f)
        );

        ParallelAction parallelAction = new ParallelAction(moveAction, sizeAction);
        table.addAction(parallelAction);
    }

    public int getCoinsCount() {
        return coinsCount;
    }

}

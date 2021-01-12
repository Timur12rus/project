package com.timgapps.warfare.screens.map.windows.upgrade_window.info_table;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.timgapps.warfare.Warfare;

public class InfoTabe1 extends VerticalGroup {
    private Label.LabelStyle labelStyle;
    private Label.LabelStyle greenLabelStyle;
    public InfoTabe1() {
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(new Color(0xfde9c0ff));
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
//        setBackground(textureRegionDrawableBg);     // установим цветной фон

        labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;

        greenLabelStyle = new Label.LabelStyle();
        greenLabelStyle.fontColor = Color.FOREST;
        greenLabelStyle.font = Warfare.font20;
    }

    public Label.LabelStyle getLabelStyle() {
        return labelStyle;
    }

    public Label.LabelStyle getGreenLabelStyle() {
        return greenLabelStyle;
    }
}

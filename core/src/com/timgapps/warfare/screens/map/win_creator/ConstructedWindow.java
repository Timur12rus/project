package com.timgapps.warfare.screens.map.win_creator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.timgapps.warfare.screens.level.LevelWindows.ColorRectangle;
import com.timgapps.warfare.Warfare;

public class ConstructedWindow extends Group {
    private final float BOX_HEADER_SIZE = 36;
    private final float BOX_SUB_HEADER_SIZE = 20;
    private final float BOX_BG_SIZE = 36;
    private Label title;
    private ColorRectangle bgRectangle, subHeaderRectangle, headerRectangle;
    private ImageButton closeButton;

    public ConstructedWindow(float width, float height, String name) {
        int w = (int) (width / BOX_BG_SIZE);
        int h = (int) (height / BOX_BG_SIZE);
        setWidth(w * BOX_BG_SIZE);
        setHeight(h * BOX_BG_SIZE + BOX_HEADER_SIZE + BOX_SUB_HEADER_SIZE);
        bgRectangle = new ColorRectangle(0, 0, w * BOX_BG_SIZE, h * BOX_BG_SIZE, new Color(0xfbf4d5ff));
        subHeaderRectangle = new ColorRectangle(0, 0, w * BOX_BG_SIZE, BOX_SUB_HEADER_SIZE, new Color(0xe1b188ff));
        headerRectangle = new ColorRectangle(0, 0, w * BOX_BG_SIZE, BOX_HEADER_SIZE, new Color(0xe8ba93ff));
        bgRectangle.setPosition(0, 0);
        subHeaderRectangle.setPosition(0, bgRectangle.getHeight());
        headerRectangle.setPosition(0, bgRectangle.getHeight() + subHeaderRectangle.getHeight());
        addActor(bgRectangle);
        addActor(subHeaderRectangle);
        addActor(headerRectangle);

        closeButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("button_close")),
                new TextureRegionDrawable(Warfare.atlas.findRegion("button_close_dwn")));
        closeButton.setX(bgRectangle.getX() + bgRectangle.getWidth() - closeButton.getWidth() - 12);
        closeButton.setY(bgRectangle.getY() + bgRectangle.getHeight() + 3);
        addActor(closeButton);
    }

    //    public void clear() {
//        bgRectangle.clear();
//        subHeaderRectangle.clear();
//        headerRectangle.clear();
//    }

    public ImageButton getCloseButton() {
        return closeButton;
    }
}

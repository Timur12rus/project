package com.timgapps.warfare.Level.LevelMap;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class LevelIcon extends Group {
    private ArrayList<Image> activeStars;
    private ArrayList<Image> inactiveStars;
    private ImageButton levelButton;
    private Image inactiveLevelIcon, levelIcon, levelIconDown;

    private boolean isActive;
    private int id;


    public LevelIcon(int id, boolean isActive) {

        this.id = id;
        this.isActive = isActive;

        /** неактивный значок **/
        inactiveLevelIcon = new Image(Warfare.atlas.findRegion("levelIcon_inactive"));
        levelIcon = new Image(Warfare.atlas.findRegion("levelIcon_active"));
        levelIconDown = new Image(Warfare.atlas.findRegion("levelIcon_active_down"));

        /** активный значок (это кнопка) **/

        levelButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("levelIcon_active")),
                new TextureRegionDrawable(Warfare.atlas.findRegion("levelIcon_active_down")));

        /** добавим неактивный значок и активный **/


        addActor(levelIcon);
        addActor(levelIconDown);
        addActor(inactiveLevelIcon);

        levelIconDown.setVisible(false);

//        addActor(levelButton);

        /** создадим массив из трёх активных здвёзд **/
        createActiveStars();

        /** создадим массив из трёх неактивных здвёзд **/
        createInactiveStars();

        checkIsActive();

        addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
            @Override
            public boolean handle(Event event) {
                event.setTarget(LevelIcon.this);
                return true;
            }
        });

        addListener(new ClickListener() { // создаем слушателя события нажатия кнопки
            // переопределяем метод TouchDown(), который называется прикасание

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                levelIconDown.setVisible(true); // устанавливаем видимость для фона нажатой кнопки, а также оставим вызов метода суперкласса
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                levelIconDown.setVisible(false);
                super.touchUp(event, x, y, pointer, button);
            }
        });

    }

    private void createInactiveStars() {
        inactiveStars = new ArrayList<Image>();
        for (int i = 0; i < 3; i++) {
            inactiveStars.add(new Image(Warfare.atlas.findRegion("star_inactive")));
            addActor(inactiveStars.get(i));
            inactiveStars.get(i).setPosition(levelButton.getX() + i * inactiveStars.get(i).getWidth(), levelButton.getY() - 20);
//            inactiveStars.get(i).setPosition(i * 36, getY());
        }
    }

    private void createActiveStars() {
        activeStars = new ArrayList<Image>();
        for (int i = 0; i < 3; i++) {
            activeStars.add(new Image(Warfare.atlas.findRegion("star_active")));
            addActor(activeStars.get(i));
//            activeStars.get(i).setPosition(i * 36, getY());
            activeStars.get(i).setPosition(levelButton.getX() + i * activeStars.get(i).getWidth(), levelButton.getY() - 20);
        }
    }

    public void checkIsActive() {
        if (isActive) {
            inactiveLevelIcon.setVisible(false);
            levelButton.setVisible(true);
            activeStars.get(0).setVisible(true);
            activeStars.get(1).setVisible(true);
            activeStars.get(2).setVisible(true);
        } else {
            inactiveLevelIcon.setVisible(true);
            levelButton.setVisible(false);
            activeStars.get(0).setVisible(false);
            activeStars.get(1).setVisible(false);
            activeStars.get(2).setVisible(false);
            inactiveStars.get(0).setVisible(false);
            inactiveStars.get(1).setVisible(false);
            inactiveStars.get(2).setVisible(false);
        }
    }


    public int getId() {
        return id;
    }
}
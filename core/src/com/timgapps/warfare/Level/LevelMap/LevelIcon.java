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
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class LevelIcon extends Group {
    private ArrayList<Image> activeStars;
    private ArrayList<Image> inactiveStars;
    private Image inactiveLevelIcon, levelIcon, levelIconDown;

    private boolean isActive;
    private int id;

    private int levelOfDifficulty;
    private int coinsCount;
    private int scoreCount;

    private int starsCount;         // кол-во звезд полученных за уровень, изначально 0

    private LevelIconData data; // объект данных уровня

    public static final String EASY = "Easy";
    public static final String MEDIUM = "Medium";
    public static final String HARD = "Hard";


    public LevelIcon(LevelIconData data) {
//    public LevelIcon(int id, int coinsCount, int scoreCount, String levelOfDifficulty, final boolean isActive) {

        /** инициализируем объект данных уровня **/
        this.data = data;
        this.isActive = isActive;

        /** неактивный значок **/
        inactiveLevelIcon = new Image(Warfare.atlas.findRegion("levelIcon_inactive"));
        levelIcon = new Image(Warfare.atlas.findRegion("levelIcon_active"));
        levelIconDown = new Image(Warfare.atlas.findRegion("levelIcon_active_down"));

        /** добавим неактивный значок и активный **/

//        levelIcon.setVisible(false);
        addActor(levelIcon);
        levelIcon.setSize(levelIcon.getWidth(), levelIcon.getHeight());

        addActor(levelIconDown);
        levelIconDown.setX(levelIcon.getX() + (levelIcon.getWidth() - levelIconDown.getWidth()) / 2);
        levelIconDown.setY(levelIcon.getY() + (levelIcon.getHeight() - levelIconDown.getHeight()) / 2);


        addActor(inactiveLevelIcon);
        levelIconDown.setVisible(false);

        starsCount = data.getStarsCount();


        /** создадим массив из трёх неактивных звёзд **/
        createInactiveStars();

        /** создадим массив из трёх активных звёзд **/
        createActiveStars();

        /** првоерим сколько активных звезд за уровень (из трёх) **/
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
//                if (isActive) {
                if (!inactiveLevelIcon.isVisible())
                    levelIconDown.setVisible(true); // устанавливаем видимость для фона нажатой кнопки, а также оставим вызов метода суперкласса
//                    levelIcon.setVisible(false);
//                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                if (isActive) {
                levelIconDown.setVisible(false);
//                    levelIcon.setVisible(true);
//                }
                super.touchUp(event, x, y, pointer, button);
            }
        });

    }

    /**
     * метод для обновления кол-ва звезд за уровень (из трех)
     **/
    public void updateStarsCount() {
//        if (starsCount > getData().getStarsCount())  //если новое кол-во звезд больше, чем предыдущее то установим новое текущее кол-во
        starsCount = data.getStarsCount();
//        this.starsCount = starsCount;
        checkIsActive();
    }

    /**
     * метод создает неактивные звезды
     **/
    private void createInactiveStars() {
        inactiveStars = new ArrayList<Image>();
        for (int i = 0; i < 3; i++) {
            inactiveStars.add(new Image(Warfare.atlas.findRegion("star_inactive")));
            addActor(inactiveStars.get(i));
            inactiveStars.get(i).setPosition(levelIconDown.getX() + i * inactiveStars.get(i).getWidth(), levelIcon.getY() - 20);
//            inactiveStars.get(i).setPosition(i * 36, getY());
        }
    }

    /**
     * метод создает активные звезды
     **/
    private void createActiveStars() {
        activeStars = new ArrayList<Image>();
        for (int i = 0; i < 3; i++) {
            activeStars.add(new Image(Warfare.atlas.findRegion("star_active")));
            activeStars.get(i).setVisible(false);
            addActor(activeStars.get(i));
//            activeStars.get(i).setPosition(i * 36, getY());
            activeStars.get(i).setPosition(levelIconDown.getX() + i * activeStars.get(i).getWidth(), levelIcon.getY() - 20);
        }
    }

    /**
     * метод для проверки, активны ли звезды за уровень, если isActive = false - звезды неактивны, если isActive = true - активны
     **/
    public void checkIsActive() {
        if (data.isActiveIcon()) {
            // делаем значок "неактивный" levelIcon невидимым
            inactiveLevelIcon.setVisible(false);
            isActive = true;

            inactiveStars.get(0).setVisible(true);
            inactiveStars.get(1).setVisible(true);
            inactiveStars.get(2).setVisible(true);

            for (int i = 0; i < starsCount; i++) {
                activeStars.get(i).setVisible(true);
            }
        } else {
            isActive = false;
            inactiveLevelIcon.setVisible(true);
            activeStars.get(0).setVisible(false);
            activeStars.get(1).setVisible(false);
            activeStars.get(2).setVisible(false);
            inactiveStars.get(0).setVisible(false);
            inactiveStars.get(1).setVisible(false);
            inactiveStars.get(2).setVisible(false);
        }
    }

    /**
     * возвращает Id уровня от 1....и делее
     **/
    public int getId() {
        return data.getId();
    }

    /**
     * метод получает data (данные) об уровне
     **/
    public LevelIconData getData() {
        return data;
    }
}
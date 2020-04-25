package com.timgapps.warfare.Level.GUI.Screens.RewardForStars;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Level.LevelMap.LevelIcon;
import com.timgapps.warfare.Warfare;

public class RewardForStars extends Group {
    private Image rewardImage;
    private Image bg, bgYellow, bgOrange;
    private Image receivedImg;
    private int typeOfReward;
    private Label nameLabel;
    private String name;
    private int rewardCountStars; // кол-во звёзд для плучения текущей награды
    private int starsNum;
    private float deltaX;       // количество пикселей по Х, на сколько нужно сдвинуть изображение
    private RewardForStarsData data;
    private GameManager gameManager;

    public RewardForStars(RewardForStarsData data, GameManager gameManager) {
        this.gameManager = gameManager;
        this.data = data;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.WHITE;
        labelStyle.font = Warfare.font20;


        switch (data.getTypeOfReward()) {
            case RewardForStarsData.REWARD_STONE:
                rewardImage = new Image(Warfare.atlas.findRegion("block1"));
                name = "Rock";
//                starsNum = data.getStarsCount();
                deltaX = 0;
                break;
            case RewardForStarsData.REWARD_ARCHER:
                rewardImage = new Image(Warfare.atlas.findRegion("archer1Stay0"));
                name = "Archer";
//                starsNum = 4;
                deltaX = 42;
                break;
            case RewardForStarsData.REWARD_GNOME:
                rewardImage = new Image(Warfare.atlas.findRegion("gnomeStay0"));
                name = "Gnome";
//                starsNum = 15;
                deltaX = -16;
                break;
            case RewardForStarsData.REWARD_BOX:
                rewardImage = new Image(Warfare.atlas.findRegion("gnomeStay0"));
                name = "Box";
                deltaX = -16;
                break;
        }

        starsNum = data.getStarsCount();
        rewardCountStars = starsNum;

        bg = new Image(Warfare.atlas.findRegion("coinsPanel"));
        bgYellow = new Image(Warfare.atlas.findRegion("yellow_coinsPanel"));
        bgOrange = new Image(Warfare.atlas.findRegion("orange_coinsPanel"));
        receivedImg = new Image(Warfare.atlas.findRegion("isReceived"));

        receivedImg.setPosition(bg.getWidth() - receivedImg.getWidth() / 2, bg.getHeight() - receivedImg.getHeight() / 2);

        rewardImage.debug();

        setSize(bg.getWidth(), bg.getHeight());  // зададим размер группы
        addActor(bg);                             // добавим фон для группы (прямоугольник)
        addActor(bgYellow);                             // добавим фон для группы (Жедлтый прямоугольник)
        addActor(bgOrange);
        addActor(receivedImg);

        bgYellow.setVisible(false);
        bgOrange.setVisible(false);
        receivedImg.setVisible(false);

        rewardImage.setPosition((bg.getWidth() - rewardImage.getWidth()) / 2 - deltaX, 36);

        nameLabel = new Label("" + name, labelStyle);
        nameLabel.setPosition((bg.getWidth() - nameLabel.getWidth()) / 2, 0);
        System.out.println("nameLabelWidth = " + nameLabel.getWidth());

        addActor(rewardImage);                      // добавим изображение
        addActor(nameLabel);

        addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
            @Override
            public boolean handle(Event event) {
                event.setTarget(RewardForStars.this);
                return true;
            }
        });

        addListener(new ClickListener() { // создаем слушателя события нажатия кнопки
            // переопределяем метод TouchDown(), который называется прикасание

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (bgYellow.isVisible())
                    bgOrange.setVisible(true); // устанавливаем видимость для фона нажатой кнопки, а также оставим вызов метода суперкласса
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (bgYellow.isVisible()) {
                    setReceived();
                }
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                getRewardForStars();
            }
        });
    }

    public void getRewardForStars() {
        if (data.getTypeOfReward() == RewardForStarsData.REWARD_STONE) {
//            gameManager.getCollection().get(0).setActive();
        }
    }

    public void setChecked() {
        data.setChecked();
        bg.setVisible(false);
        bgYellow.setVisible(true);
    }

    public void setReceived() {
        bgOrange.setVisible(false);
        bg.setVisible(true);
        bgYellow.setVisible(false);
        receivedImg.setVisible(true);
    }

    public int getRewardCountStars() {
        return rewardCountStars;
    }
}

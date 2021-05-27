package com.timgapps.warfare.screens.reward_for_stars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Utils.StringHolder;
import com.timgapps.warfare.screens.level.Finger;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.reward_for_stars.gui_elements.Hilite;

// награда за звезды (изображение)
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
    private RewardForStarsScreen rewardForStarsScreen;
    private Finger finger;
    private StarsBar starsBar;
    private final int BG_PANEL_WIDTH = 140;
    private final int BAR_WIDTH = 224;
    //    BAR_WIDTH = 224
//    private final int barWidth = 184;
    private final int barHeight = 32;
    private int deltaCountStars;
    private int lastRewardCountStars;
    private int starsCount;                         // текущее кол-во звёзд у игрока
    private Hilite hilite;
    private float timeCount = 120;

    public RewardForStars(RewardForStarsScreen rewardForStarsScreen, RewardForStarsData data, GameManager gameManager,
                          int deltaCountStars, int lastRewardCountStars) {
        this.rewardForStarsScreen = rewardForStarsScreen;
        this.deltaCountStars = deltaCountStars;
        this.lastRewardCountStars = lastRewardCountStars;
        this.gameManager = gameManager;
        this.data = data;
        bg = new Image(Warfare.atlas.findRegion("coinsPanel"));
        receivedImg = new Image(Warfare.atlas.findRegion("isReceived"));
        receivedImg.setPosition(bg.getWidth() - receivedImg.getWidth() / 2, bg.getHeight() - receivedImg.getHeight() / 2);
        setSize(bg.getWidth(), bg.getHeight());  // зададим размер группы

        // текущее кол-во звезд
        starsCount = gameManager.getSavedGame().getStarsCount();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        if (data.isReceived()) {
            labelStyle.fontColor = Color.LIGHT_GRAY;
        } else {
            labelStyle.fontColor = Color.WHITE;
        }

        labelStyle.font = Warfare.font30;

        switch (data.getTypeOfReward()) {
            case RewardForStarsData.REWARD_STONE:
                typeOfReward = com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData.REWARD_STONE;
                rewardImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
                name = "Rock";
                deltaX = -16;
                break;
            case RewardForStarsData.REWARD_ARCHER:
                rewardImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
//                name = "Archer";
                deltaX = -12;
                break;
            case RewardForStarsData.REWARD_GNOME:
                rewardImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
//                name = "Gnome";
                deltaX = -10;
                break;
            case RewardForStarsData.REWARD_BOX:
                if (!data.isReceived()) {            // если награда (сундук) не получена
                    rewardImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
                } else {        // в противном случае
                    rewardImage = new Image(Warfare.atlas.findRegion("boxImage4"));
                }
//                name = "Box";
                deltaX = -16;
                break;
            case RewardForStarsData.REWARD_KNIGHT:
                rewardImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
                deltaX = -12;
                break;
            case RewardForStarsData.REWARD_SHOOTER:
                rewardImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
                deltaX = -12;
                break;
            case RewardForStarsData.REWARD_VIKING:
                rewardImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
                deltaX = -12;
                break;
            case RewardForStarsData.REWARD_FIREBOOSTER:
                rewardImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
                deltaX = -12;
                break;
        }
        name = data.getName();
        rewardImage.debug();

        starsNum = data.getStarsCount();        // кол-во звезд, необходимое для получения награды
        rewardCountStars = starsNum;
        addActor(bg);                             // добавим фон для группы (прямоугольник)

        if (typeOfReward == RewardForStarsData.REWARD_STONE) {
            finger = new Finger(rewardImage.getX() + (rewardImage.getWidth() / 2 - Finger.WIDTH / 2) + 108,
                    rewardImage.getY() + rewardImage.getHeight() + 42 + Finger.HEIGHT,
                    Finger.DOWN, new TextureRegion(Warfare.atlas.findRegion("fingerUpLeft")));
            finger.debug();
            float x = rewardImage.getX() + (rewardImage.getWidth() / 2 - Finger.WIDTH / 2) + 108;
            float y = rewardImage.getY() + rewardImage.getHeight() + 42 + Finger.HEIGHT;
            finger.setPosition(x, y);
            finger.setVisible(false);
            addActor(finger);
            if (gameManager.getHelpStatus() == GameManager.HELP_STARS_PANEL) {
                finger.show();
            }
        }

        if (data.isReceived()) {         // если награда получена
            receivedImg.setVisible(true);
        } else {
            receivedImg.setVisible(false);
        }
        rewardImage.setPosition((bg.getWidth() - rewardImage.getWidth()) / 2 - deltaX, 36);
        nameLabel = new Label("" + name, labelStyle);
        nameLabel.setPosition((bg.getWidth() - nameLabel.getWidth()) / 2, 0);
        hilite = new Hilite(this);
        addActor(rewardImage);                      // добавим изображение
        addActor(nameLabel);
        addActor(receivedImg);

        // создаем бары под изображениями наград за звезды
        starsBar = new StarsBar(getX() + BG_PANEL_WIDTH / 2 - BAR_WIDTH - 8,
                getY() - barHeight - 16,
                data,
                lastRewardCountStars    // кол-во звёзд за последнюю награду
        );
        addActor(starsBar);

        addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
            @Override
            public boolean handle(Event event) {
                event.setTarget(RewardForStars.this);
                return true;
            }
        });

        addListener(new ClickListener() { // создаем слушателя события нажатия кнопки
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                showToast();
            }
        });
        this.setDebug(true);
    }

    public void showNextRewardLabel(Label nextRewardLabel) {
        nextRewardLabel.setPosition((getWidth() - nextRewardLabel.getWidth()) / 2, getHeight() / 2 + rewardImage.getHeight());
        addActor(nextRewardLabel);
    }

    public void redraw() {
        if (data.isReceived()) {         // если награда получена
            receivedImg.setVisible(true);
        } else {
            receivedImg.setVisible(false);
        }
        hilite.setHilite(false);
        starsCount = gameManager.getSavedGame().getStarsCount();
        starsBar.redraw(starsCount);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        hilite.act(delta);
    }

    private void showToast() {
        if (!data.isReceived()) {     // если награда не доступна
            rewardForStarsScreen.showToast(data.getStarsCount());
        } else {
            rewardForStarsScreen.showToast(Warfare.stringHolder.getString(StringHolder.REWARD_ALREADY_RECIVED));
        }
    }

    public void setHilite(boolean isHilited) {
        hilite.setHilite(isHilited);
    }

    public int getRewardCountStars() {
        return rewardCountStars;
    }

    public Image getRewardImage() {
        return rewardImage;
    }
}
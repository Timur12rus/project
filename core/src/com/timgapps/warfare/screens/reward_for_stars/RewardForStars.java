package com.timgapps.warfare.screens.reward_for_stars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.screens.level.Finger;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.screens.map.actions.CoinsAction;
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
    private float xPos;     // позиция Х panelStarsSmall
    private Hilite hilite;
    private CoinsAction coinsAction;
    private boolean isGotReward;        // получени ли награда
    private SequenceAction getRewardAction;
    private float timeCount = 120;
    private boolean startCountTimer;

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
        if (data.getIsReceived()) {
            labelStyle.fontColor = Color.LIGHT_GRAY;
        } else {
            labelStyle.fontColor = Color.WHITE;
        }

        labelStyle.font = Warfare.font20;

        switch (data.getTypeOfReward()) {
            case RewardForStarsData.REWARD_STONE:
                typeOfReward = com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData.REWARD_STONE;
                rewardImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
                name = "Rock";
                deltaX = -16;
                break;
            case RewardForStarsData.REWARD_ARCHER:
                rewardImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
                name = "Archer";
                deltaX = -12;
                break;
            case RewardForStarsData.REWARD_GNOME:
                rewardImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
                name = "Gnome";
                deltaX = -10;
                break;
            case RewardForStarsData.REWARD_BOX:
                if (!data.getIsReceived()) {            // если награда (сундук) не получена
                    rewardImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
                } else {        // в противном случае
                    rewardImage = new Image(Warfare.atlas.findRegion("boxImage4"));
                }
                name = "Box";
                deltaX = -16;
                break;
            case RewardForStarsData.REWARD_KNIGHT:
                rewardImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
                name = "Knight";
                deltaX = -12;
                break;
        }

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

        if (data.getIsReceived()) {         // если награда получена
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

    public void hideNextRewardLabel() {
//        nextRewardLabel.setPosition();

    }


    public void redraw() {
        if (data.getIsReceived()) {         // если награда получена
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
        if (!data.getIsChecked()) {     // если награда не доступна
            rewardForStarsScreen.showToast(data.getStarsCount());
        }

        //TODO сделать надпсь "награда уже получена"
//        if (data.getIsReceived()) {
//            showToast();
//        }

    }

    public void setHilite(boolean isHilited) {
        hilite.setHilite(isHilited);
    }


    /**
     * метод для получения награды за звезды при клике на награду
     **/
//    public void getRewardForStars() {
//        setReceived();
//        nameLabel.setColor(Color.LIGHT_GRAY);
//        switch (data.getTypeOfReward()) {
//            case com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData.REWARD_STONE:                           // если награда "КАМЕНЬ"
//                for (int i = 0; i < gameManager.getCollection().size(); i++) {
//                    if (gameManager.getCollection().get(i).getUnitId() == PlayerUnits.Rock) {
//                        addRewardUnitToTeam(i);
//                        Image actorImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
//                        rewardForStarsScreen.startAddAction(actorImage, getX() + rewardForStarsScreen.getScrollTableX(), getY() + 240, deltaX);
//                        gameManager.setHelpStatus(GameManager.HELP_TEAM_UPGRADE);
//                    }
//                }
//                break;
//            case com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData.REWARD_ARCHER:
//                for (int i = 0; i < gameManager.getCollection().size(); i++) {
//                    if (gameManager.getCollection().get(i).getUnitId() == PlayerUnits.Archer) {
//                        addRewardUnitToTeam(i);
//                        Image actorImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
////                        setStartPosition(getX() + rewardForStarsScreen.getScrollTableX(),
////                                getY() + 240);
//                        rewardForStarsScreen.startAddAction(actorImage, getX() + rewardForStarsScreen.getScrollTableX(), getY() + 240, deltaX);
//                    }
//                }
//                break;
//            case com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData.REWARD_BOX:
//                rewardImage.setDrawable(new Image(Warfare.atlas.findRegion("boxImage4")).getDrawable());
//                gameManager.addCoinsCount(100);
//                com.timgapps.warfare.screens.reward_for_stars.GiftAnimation coinsAnimation = new com.timgapps.warfare.screens.reward_for_stars.GiftAnimation(rewardForStarsScreen,
//                        getX() + rewardForStarsScreen.getScrollTableX(),
//                        getY() + 240, com.timgapps.warfare.screens.reward_for_stars.GiftAnimation.COIN_GIFT);
////                        getY() + rewardForStarsScreen.getScrollTableY());
//
//                gameManager.setCoinsCount(gameManager.getCoinsCount() + 100);
//                coinsAction = new CoinsAction();
//                coinsAction.setStartPosition(getX() + rewardForStarsScreen.getScrollTableX(),
//                        getY() + 240);
//                coinsAction.setEndPosition(rewardForStarsScreen.getCoinsPanel().getX(), rewardForStarsScreen.getCoinsPanel().getY());
//                coinsAction.start();
//                rewardForStarsScreen.addChild(coinsAction);
//                com.timgapps.warfare.screens.reward_for_stars.GiftAnimation resoursesAnimation = new com.timgapps.warfare.screens.reward_for_stars.GiftAnimation(rewardForStarsScreen,
//                        getX() + rewardForStarsScreen.getScrollTableX(),
//                        getY() + 240, GiftAnimation.RESOURSES_GIFT);
//
////                coinsAnimation.start();
//                resoursesAnimation.start();
//                break;
//            case com.timgapps.warfare.screens.reward_for_stars.RewardForStarsData.REWARD_GNOME:
//                for (int i = 0; i < gameManager.getCollection().size(); i++) {
//                    if (gameManager.getCollection().get(i).getUnitId() == PlayerUnits.Gnome) {
//                        addRewardUnitToTeam(i);
//                        Image actorImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
//                        rewardForStarsScreen.startAddAction(actorImage, getX() + rewardForStarsScreen.getScrollTableX(), getY() + 240, deltaX);
//                    }
//                }
//                break;
//            case RewardForStarsData.REWARD_KNIGHT:
//                for (int i = 0; i < gameManager.getCollection().size(); i++) {
//                    if (gameManager.getCollection().get(i).getUnitId() == PlayerUnits.Knight) {
//                        addRewardUnitToTeam(i);
//                        Image actorImage = new Image(Warfare.atlas.findRegion(data.getImageString()));
//                        rewardForStarsScreen.startAddAction(actorImage, getX() + rewardForStarsScreen.getScrollTableX(), getY() + 240, deltaX);
//                    }
//                }
//                break;
//        }
//        starsBar.setIsReceived(true);
////         сохраним данные
//        data.setReceived();                                  // награда получена
//        gameManager.getStarsPanel().updateCountReward();
//        gameManager.saveGame();
//    }

    /**
     * метод добавляет полученного юнита в команду
     **/
    private void addRewardUnitToTeam(int i) {
//        unlockRewardForStars(i);                                               // разблокируем награду
        gameManager.getCollection().get(i).getUnitData().setUnlock();     // снимаем блокировку юнита
        gameManager.getCollection().get(i).getUnitImageButton().unlock();
        gameManager.getCollection().get(i).getUnitImageButton().redraw();

        if (gameManager.getTeam().size() < 5) {
            // добавим полученный юнит в команду
            gameManager.getTeam().add(gameManager.getCollection().get(i));  // добавляем в команду полученный юнит из коллекции
            gameManager.getSavedGame().getTeamDataList().add(gameManager.getSavedGame().getCollectionDataList().get(i));

            // удалим юнит из коллекции
            gameManager.getCollection().remove(i);
            gameManager.getSavedGame().getCollectionDataList().remove(i);
        } else {
            // TODO сделать чтобы юнит добавлялся в коллекцию, если в команде больше 5 юнитов
//            //
//            gameManager.getTeam().add(gameManager.getCollection().get(i));  // добавляем в команду полученный юнит из коллекции
//            gameManager.getSavedGame().getTeamDataList().add(gameManager.getSavedGame().getCollectionDataList().get(i));
        }
    }

    /**
     * метод разблокирует юнита и делает его доступным в коллекции
     **/
    private void unlockRewardForStars(int index) {
        gameManager.getCollection().get(index).getUnitData().setUnlock();     // снимаем блокировку юнита
        gameManager.getCollection().get(index).getUnitImageButton().unlock();
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

    public Image getRewardImage() {
        return rewardImage;
    }
}
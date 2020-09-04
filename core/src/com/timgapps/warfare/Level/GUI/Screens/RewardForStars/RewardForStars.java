package com.timgapps.warfare.Level.GUI.Screens.RewardForStars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.GUI.Finger;
import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Level.LevelMap.LevelIcon;
import com.timgapps.warfare.Level.LevelMap.actions.CoinsAction;
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
    private RewardForStarsScreen rewardForStarsScreen;
    private Finger finger;
    private StarsBar bar;
    private final int BG_PANEL_WIDTH = 140;
    private final int barWidth = 184;
    private final int barHeight = 32;
    private int deltaCountStars;
    private int lastRewardCountStars;
    private int starsCount;
    private float xPos;     // позиция Х panelStarsSmall
    private Hilite hilite;
    private CoinsAction coinsAction;

    @Override
    public void act(float delta) {
        super.act(delta);
        hilite.act(delta);
        if (coinsAction != null && coinsAction.isEndCoinsAction()) {
            coinsAction.setEndCoinsAction();
            rewardForStarsScreen.getCoinsPanel().startAddCoinsAction();
        }
    }

    public RewardForStars(final RewardForStarsScreen rewardForStarsScreen, final RewardForStarsData data, final GameManager gameManager,
                          int deltaCountStars, int lastRewardCountStars) {
        this.rewardForStarsScreen = rewardForStarsScreen;
        this.deltaCountStars = deltaCountStars;
        this.lastRewardCountStars = lastRewardCountStars;

        this.gameManager = gameManager;
        this.data = data;
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
                typeOfReward = RewardForStarsData.REWARD_STONE;
                rewardImage = new Image(Warfare.atlas.findRegion("block1_image"));
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
                if (!data.getIsReceived()) {
                    rewardImage = new Image(Warfare.atlas.findRegion("boxImage0"));
                } else {
                    rewardImage = new Image(Warfare.atlas.findRegion("boxImage4"));
                }

                name = "Box";
                deltaX = -16;
                break;
            case RewardForStarsData.REWARD_KNIGHT:
                rewardImage = new Image(Warfare.atlas.findRegion("knightUnitImage"));
                name = "Knight";
                deltaX = -16;
                break;
        }

        starsNum = data.getStarsCount();        // кол-во звезд, необходимое для получения награды
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


        if (typeOfReward == RewardForStarsData.REWARD_STONE) {
            finger = new Finger(rewardImage.getX() + (rewardImage.getWidth() / 2 - Finger.WIDTH / 2) + 108,
                    rewardImage.getY() + rewardImage.getHeight() + 42 + Finger.HEIGHT,
                    Finger.DOWN, new TextureRegion(Warfare.atlas.findRegion("fingerUpLeft")));
            finger.debug();

            float x = rewardImage.getX() + (rewardImage.getWidth() / 2 - Finger.WIDTH / 2) + 108;
//        float x = rewardImage.getX() + (rewardImage.getWidth() / 2 - Finger.WIDTH / 2) + 48 + 36;
            float y = rewardImage.getY() + rewardImage.getHeight() + 42 + Finger.HEIGHT;
            finger.setPosition(x, y);
            finger.setVisible(false);
            addActor(finger);
            if (gameManager.getHelpStatus() == GameManager.HELP_STARS_PANEL) {
                finger.show();
            }
        }

        bgYellow.setVisible(false);
        bgOrange.setVisible(false);
        if (data.getIsReceived()) {
            receivedImg.setVisible(true);
        } else {
            receivedImg.setVisible(false);
        }

        rewardImage.setPosition((bg.getWidth() - rewardImage.getWidth()) / 2 - deltaX, 36);

        nameLabel = new Label("" + name, labelStyle);
        nameLabel.setPosition((bg.getWidth() - nameLabel.getWidth()) / 2, 0);
//        System.out.println("nameLabelWidth = " + nameLabel.getWidth());


        hilite = new Hilite(this);
//        hilite.setPosition(100, 120);
//        hilite.debug();

        addActor(rewardImage);                      // добавим изображение
        addActor(nameLabel);
        addActor(receivedImg);

        // создаем бары под изображениями наград за звезды
        bar = new StarsBar(getX() + BG_PANEL_WIDTH / 2 - barWidth - 8,
                getY() - barHeight - 16,
                data.getIsReceived(),
                deltaCountStars,        // разница кол-во звезд у игрока и кол-вом звезд за последнюю награду
                lastRewardCountStars,   // кол-во звёзд за последнюю награду
                rewardCountStars // кол-во звёзд за награду
        );
        addActor(bar);

        addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
            @Override
            public boolean handle(Event event) {
                event.setTarget(RewardForStars.this);
                return true;
            }
        });

//        final RewardForStarsScreen finalRewardForStarsScreen = rewardForStarsScreen;
        addListener(new ClickListener() { // создаем слушателя события нажатия кнопки
            // переопределяем метод TouchDown(), который называется прикасание

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (bgYellow.isVisible())
                    bgOrange.setVisible(true); // устанавливаем видимость для фона нажатой кнопки, а также оставим вызов метода суперкласса
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (bgYellow.isVisible())
                    bgOrange.setVisible(true);
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (bgOrange.isVisible()) {
                    bgOrange.setVisible(false);
                }
//                if (bgYellow.isVisible()) {
//                    setReceived();
//                    if (finger != null) {
//                        finger.hide();
//                        gameManager.setHelpStatus(GameManager.HELP_UNIT_CREATE);
//                    }
//                }
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                getRewardForStars();
                // TODO нужно вернуть
//                if ((!data.getIsReceived()) && (data.getIsChecked())) {     // если награда доступна но не получена
//                    getRewardForStars();
//                }
                if (!data.getIsChecked()) {     // если награда не доступна
                    rewardForStarsScreen.showToast(data.getStarsCount());
                }

                if (bgYellow.isVisible()) {
                    setReceived();
                    if (finger != null) {
                        finger.hide();
                        gameManager.setHelpStatus(GameManager.HELP_UNIT_CREATE);
                    }
                }
            }
        });
    }

    public float getxPos() {
        return xPos;
    }

    public void setHilite() {
        hilite.setHilite();
    }

    // подсветка для значка награды
    class Hilite extends Actor {
        Image image;
        boolean isHilited = false;
        boolean alphaUp = false;

        public Hilite(Group group) {
            image = new Image(Warfare.atlas.findRegion("hiliteImage"));
            image.setVisible(false);
            setSize(image.getWidth(), image.getHeight());
            group.addActor(image);
        }

        void setHilite() {
            image.setVisible(true);
            isHilited = true;
        }

        @Override
        public void act(float delta) {
            super.act(delta);
//            System.out.println("act()");
            if (isHilited) {
//                System.out.println("XPOS = " + getX());
                float alpha = image.getColor().a;
//                System.out.println("alphaUP = " + alphaUp);
                if (alphaUp) {
                    alpha += delta;
//                    System.out.println("Alpha = " + alpha);
                    if (alpha >= 1) {
                        alpha = 1;
                        alphaUp = false;
                    }
                } else {
                    alpha -= delta;
//                    System.out.println("Alpha = " + alpha);
                    if (alpha < 0) {
                        alpha = 0;
                        alphaUp = true;
                    }
                }
                image.setColor(1, 1, 1, alpha);
            }
        }
    }

    // класс StarsBar - полоса, индикатор кол-во звдезд за награду
    class StarsBar extends Actor {
        Texture bgBarTexture, barTexture;
        Texture bgTexture;
        float x, y;
        boolean isReceived;
        Pixmap progressPixmap;
        protected final int barWidth = 184;
        protected final int barHeight = 32;

        /**
         * starsBar - объект, бар полосы на фоне
         *
         * @param deltaCountStars      - кол-во звезд между текущим кол-вом и кол-вом за последнюю награду
         * @param lastRewardCountStars - кол-во звёзд за последнюю награду
         * @param rewardStarsCount     - кол-во звёзд для награды
         **/
        StarsBar(float x, float y, boolean isReceived, int deltaCountStars, int lastRewardCountStars, int rewardStarsCount) {
            this.isReceived = isReceived;   // елси награда не получена (достигнута или нет, бар будет ЖЁЛТЫМ, если получена - ОРАНЖЕВЫМ
            createStarsBar(x, barWidth, barHeight, deltaCountStars, lastRewardCountStars, rewardStarsCount);
            setSize(bgBarTexture.getWidth(), bgBarTexture.getHeight());
            this.x = x;
            this.y = y;

        }

        // метод окрашивает бар в темный цвет, что означает что награда получена
        void setIsReceived(boolean flag) {
            isReceived = flag;
        }


        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            batch.draw(bgBarTexture, x, y);
            if (isReceived)
                batch.setColor(1, 0.5f, 0, 0.7f);
            batch.draw(barTexture, x + 1, y + 1);
            batch.setColor(1, 1, 1, 1);
        }


        private void createStarsBar(float x, int barWidth, int barHeight, int deltaCountStars, int lastRewardCountStars, int rewardStarsCount) {
            /** проеверим, если награ да получена, то окрасим темно-оранжевым цветом Bar*/
            if (!isReceived) {  // не получена, bar - желтый
                int calculatedWidth;
                // rewardStarsCount - кол-во здёзд необходимое для получения награды
                if (starsCount < rewardStarsCount) {
//                health * (healthBarWidth - 2) / fullHealth
                    if (deltaCountStars >= 0) {
                        calculatedWidth = deltaCountStars * (barWidth - 2) / (rewardStarsCount - lastRewardCountStars);
                        if (calculatedWidth <= 0) calculatedWidth = 2;
                    } else {
                        calculatedWidth = 2;
                    }
                } else {
                    calculatedWidth = barWidth;
                }

                if ((starsCount >= lastRewardCountStars) && (starsCount <= rewardStarsCount)) {
                    xPos = calculatedWidth + x;
                    rewardForStarsScreen.updateXposSmallStarsPanel(xPos);
                }
                progressPixmap = createProceduralPixmap(calculatedWidth - 2, barHeight - 2, new Color(0xf2d900ff));
            } else {    // получена - темно-оранжевый цвет
                progressPixmap = createProceduralPixmap(barWidth - 2, barHeight - 2, new Color(0xf2d900ff));
//                progressPixmap = createProceduralPixmap(barWidth - 2, barHeight - 2, new Color(0xa29100ff));
            }
            Pixmap backPixmap = createProceduralPixmap(barWidth, barHeight, new Color(0x464642));
            barTexture = new Texture(progressPixmap);
            bgBarTexture = new Texture(backPixmap);
        }


        private Pixmap createProceduralPixmap(int width, int height, Color color) {
            Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
            pixmap.setColor(color);
            pixmap.fill();
            return pixmap;
        }
    }


    public float getImageWidth() {
        return rewardImage.getWidth();
    }

    public float getImageHeight() {
        return rewardImage.getHeight();
    }

    public Image getRewardImage() {
        return rewardImage;
    }

    /**
     * метод для получения награды за звезды при клике на награду
     **/
    public void getRewardForStars() {
        nameLabel.setColor(Color.LIGHT_GRAY);
        switch (data.getTypeOfReward()) {
            case RewardForStarsData.REWARD_STONE:                           // если награда "КАМЕНЬ"
                for (int i = 0; i < gameManager.getCollection().size(); i++) {
                    if (gameManager.getCollection().get(i).getUnitType() == TeamEntity.STONE) {
                        addRewardUnitToTeam(i);
                        gameManager.setHelpStatus(GameManager.HELP_TEAM_UPGRADE);
                        System.out.println("getHelpStatus = " + gameManager.getHelpStatus());
                    }
                }
                break;
            case RewardForStarsData.REWARD_ARCHER:
                for (int i = 0; i < gameManager.getCollection().size(); i++) {
                    if (gameManager.getCollection().get(i).getUnitType() == TeamEntity.ARCHER) {
                        addRewardUnitToTeam(i);
                    }
                }
                break;
            case RewardForStarsData.REWARD_BOX:
                rewardImage.setDrawable(new Image(Warfare.atlas.findRegion("boxImage4")).getDrawable());
                gameManager.addCoinsCount(100);
                coinsAction = new CoinsAction();
                coinsAction.setStartPosition(getX() + rewardForStarsScreen.getScrollTableX(),
                        getY() + 240);
                coinsAction.setEndPosition(rewardForStarsScreen.getCoinsPanel().getX(), rewardForStarsScreen.getCoinsPanel().getY());
                coinsAction.start();
                rewardForStarsScreen.addChild(coinsAction);

//                GiftAnimation coinsAnimation = new GiftAnimation(rewardForStarsScreen,
//                        getX() + rewardForStarsScreen.getScrollTableX(),
//                        getY() + 240, GiftAnimation.COIN_GIFT);
////                        getY() + rewardForStarsScreen.getScrollTableY());
                GiftAnimation resoursesAnimation = new GiftAnimation(rewardForStarsScreen,
                        getX() + rewardForStarsScreen.getScrollTableX(),
                        getY() + 240, GiftAnimation.RESOURSES_GIFT);

//                coinsAnimation.start();
                resoursesAnimation.start();
                break;
        }
        bar.setIsReceived(true);

//         сохраним данные
        data.setReceived();                                  // награда получена

        gameManager.getStarsPanel().updateCountReward();
        gameManager.saveGame();


//        /** ---------------------------------------------------------------------------**/
////        if (data.getTypeOfReward() == RewardForStarsData.REWARD_STONE) {
////            for (int i = 0; i < gameManager.getCollection().size(); i++) {
////                if (gameManager.getCollection().get(i).getUnitType() == TeamEntity.ARCHER) {
////                    index = i;
////                    addRewardUnitToTeam(i);
////                }
////            }
//        gameManager.getCollection().get(0).getEntityData().setUnlock();     // снимаем блокировку юнита
//        gameManager.getCollection().get(0).getUnitImageButton().unlock();
//
//        if (gameManager.getTeam().size() < 5)
//            // добавим полученный юнит в команду
//            gameManager.getTeam().add(gameManager.getCollection().get(0));  // добавляем в команду полученный юнит из коллекции
//        gameManager.getSavedGame().getTeamDataList().add(gameManager.getSavedGame().getCollectionDataList().get(0));
//        // удалим юнит из коллекции
//        gameManager.getCollection().remove(0);
//        gameManager.getSavedGame().getCollectionDataList().remove(0);
//
//        System.out.println("gameManager.getCollection().get(0) = " + gameManager.getCollection().get(0).toString());
//        System.out.println("gameManager.getCollectionDataList().get(0) = " + gameManager.getSavedGame().getCollectionDataList().get(0).toString());
//        /** -------------------------------------------------------------------------------------------------------------------**/

//        data.setReceived();
//        gameManager.saveGame();
    }

    /**
     * метод добавляет полученного юнита в команду
     **/
    private void addRewardUnitToTeam(int i) {
//        unlockRewardForStars(i);                                               // разблокируем награду
        gameManager.getCollection().get(i).getEntityData().setUnlock();     // снимаем блокировку юнита
        gameManager.getCollection().get(i).getUnitImageButton().unlock();

        if (gameManager.getTeam().size() < 5) {
            // добавим полученный юнит в команду
            gameManager.getTeam().add(gameManager.getCollection().get(i));  // добавляем в команду полученный юнит из коллекции
            gameManager.getSavedGame().getTeamDataList().add(gameManager.getSavedGame().getCollectionDataList().get(i));

            // удалим юнит из коллекции
            gameManager.getCollection().remove(i);
            gameManager.getSavedGame().getCollectionDataList().remove(i);
        }

        System.out.println("gameManager.getCollection().get(0) = " + gameManager.getCollection().get(0).toString());
        System.out.println("gameManager.getCollectionDataList().get(0) = " + gameManager.getSavedGame().getCollectionDataList().get(0));

    }

    /**
     * метод разблокирует юнита и делает его доступным в коллекции
     **/
    private void unlockRewardForStars(int index) {
        gameManager.getCollection().get(index).getEntityData().setUnlock();     // снимаем блокировку юнита
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
}
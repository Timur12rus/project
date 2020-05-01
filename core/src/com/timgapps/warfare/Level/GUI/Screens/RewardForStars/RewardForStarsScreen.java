package com.timgapps.warfare.Level.GUI.Screens.RewardForStars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class RewardForStarsScreen extends StageGame {
    public static final int ON_BACK = 1;
    private ArrayList<RewardForStarsData> rewardForStarsDataList;
    private ArrayList<RewardForStars> rewardForStarsList;
    private BackButton backButton;
    private Texture bgTexture;
    protected final int barWidth = 184;
    protected final int barHeight = 32;
    private final int BG_PANEL_WIDTH = 140;
    private Label countLabel;
    private int starsCount;
    private float xPos;     // позиция Х panelStarsSmall
    private boolean isStartToastAction = false;

    public RewardForStarsScreen(GameManager gameManager) {
        createBackground();

        /** получим текущее кол-во звезд **/
        starsCount = 7;
//        starsCount = gameManager.getStarsPanel().getStarsCount();
        System.out.println("starsCount = " + starsCount);
//        starsCount = 14;
//        starsCount = 3;

        backButton = new BackButton();
        backButton.setPosition(64, 64);
        addOverlayChild(backButton);

        rewardForStarsDataList = gameManager.getRewardForStarsDataList();
        rewardForStarsList = new ArrayList<RewardForStars>();

        Label.LabelStyle countStarsLabelStyle = new Label.LabelStyle();
        countStarsLabelStyle.fontColor = Color.FOREST;
        countStarsLabelStyle.font = Warfare.font20;

//        rewardForStarsDataList.get(0).setReceived();
        StarsPanelSmall starsPanelSmall = new StarsPanelSmall();

//        ClickListener clickListener = new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
//            }
//        };

//        ClickListener rewardForStarsListener = new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
//                showMessage(get);
//            }
//        };

        /** создадим картинки и бары **/
        for (int i = 0; i < rewardForStarsDataList.size(); i++) {

            rewardForStarsList.add(new RewardForStars(this, rewardForStarsDataList.get(i), gameManager));
            rewardForStarsList.get(i).setPosition(100 + 190 * i + rewardForStarsList.get(i).getWidth(), 360);
            addChild(rewardForStarsList.get(i));

//            rewardForStarsList.get(i).addListener(rewardForStarsListener);

            /** обозначим доступные награды за звезды **/
            if (starsCount > rewardForStarsDataList.get(i).getStarsCount()) {
                rewardForStarsDataList.get(i).setChecked();
            }

            if ((starsCount >= rewardForStarsDataList.get(i).getStarsCount()) && (!rewardForStarsDataList.get(i).getIsReceived())) {
                rewardForStarsList.get(i).setChecked(); // установим доступной для получения (подсветим "ЖЕЛТЫМ" цветом)
            }

            /** количество звезд за предыдущую награду, нужно для вычисления разницы отрезка (кол-ва звезд) **/
            int deltaCountStars;
            int lastRewardCountStars;
            if (i > 0) {
                lastRewardCountStars = rewardForStarsList.get(i - 1).getRewardCountStars(); // кол-во звёзд за прошлую награду
                deltaCountStars = starsCount - lastRewardCountStars;    // разница между текущим кол-вом звёзд и кол-вом
                // звёзд за прошлую награду
            } else {
                lastRewardCountStars = 0;
                deltaCountStars = starsCount - lastRewardCountStars;

            }

            // создаем бары под юизображениями наград за звезды
            StarsBar bar = new StarsBar(rewardForStarsList.get(i).getX() + BG_PANEL_WIDTH / 2 - barWidth - 8,
                    rewardForStarsList.get(i).getY() - barHeight - 16,
                    rewardForStarsDataList.get(i).getIsReceived(),
                    deltaCountStars,        // разница кол-во звезд у игрока и кол-вом звезд за последнюю награду
                    lastRewardCountStars,   // кол-во звёзд за последнюю награду
                    rewardForStarsList.get(i).getRewardCountStars() // кол-во звёзд за награду
            );

            addChild(bar);


            /** добавим цифры - кол-во звёзд необходимое для получения награды **/
            countLabel = new Label("" + rewardForStarsList.get(i).getRewardCountStars(), countStarsLabelStyle);
            countLabel.setPosition(rewardForStarsList.get(i).getX() + BG_PANEL_WIDTH / 2 - countLabel.getWidth(),
                    rewardForStarsList.get(i).getY() - countLabel.getHeight() - 48);
            addChild(countLabel);
        }
        starsPanelSmall.setPosition(xPos - 8 - starsPanelSmall.getWidth() / 2, 216);
        addChild(starsPanelSmall);
    }

    public void showToast(int starsCount) {
        if (isStartToastAction == false) {
            System.out.println("show Toast!");
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.fontColor = Color.RED;
            labelStyle.font = Warfare.font40;
            Label toastLabel = new Label("Collect " + starsCount + " stars for reward", labelStyle);
            toastLabel.setPosition(Warfare.V_WIDTH / 2 - 200, Warfare.V_HEIGHT / 2);
            addChild(toastLabel);

            Action checkEndOfAction = new Action() {
                @Override
                public boolean act(float delta) {
                    isStartToastAction = false;
                    return true;
                }
            };

            AlphaAction alphaActionStart = new AlphaAction();
            alphaActionStart.setAlpha(1);
            alphaActionStart.setDuration(0.02f);

            MoveToAction mta = new MoveToAction();

            mta.setPosition(Warfare.V_WIDTH / 2 - 200, Warfare.V_HEIGHT / 2 + 150);
            mta.setDuration(0.7f);

            AlphaAction alphaActionEnd = new AlphaAction();
            alphaActionEnd.setAlpha(0);
            alphaActionEnd.setDuration(1f);

            SequenceAction sa = new SequenceAction(alphaActionStart, mta, alphaActionEnd, checkEndOfAction);
            toastLabel.addAction(sa);
            isStartToastAction = true;
        }
    }

    class StarsBar extends Actor {
        Texture bgBarTexture, barTexture;
        float x, y;
        boolean isReceived;

        /**
         * starsBar - объект, бар полосы на фоне
         *
         * @param deltaCountStars      - кол-во звезд между текущим кол-вом и кол-вом за последнюю награду
         * @param lastRewardCountStars - кол-во звёзд за последнюю награду
         * @param rewardStarsCount     - кол-во звёзд для награды
         **/
        public StarsBar(float x, float y, boolean isReceived, int deltaCountStars, int lastRewardCountStars, int rewardStarsCount) {
            this.isReceived = isReceived;   // елси награда не получена (достигнута или нет, бар будет ЖЁЛТЫМ, если получена - ОРАНЖЕВЫМ
            createStarsBar(x, barWidth, barHeight, deltaCountStars, lastRewardCountStars, rewardStarsCount);
            setSize(bgBarTexture.getWidth(), bgBarTexture.getHeight());
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            batch.draw(bgBarTexture, x, y);
            batch.draw(barTexture, x + 1, y + 1);
        }

        private void createStarsBar(float x, int barWidth, int barHeight, int deltaCountStars, int lastRewardCountStars, int rewardStarsCount) {
            Pixmap progressPixmap;
            /** проеверим, если награда получена, то окрасим темно-оранжевым цветом Bar*/
            if (!isReceived) {  // не получена, bar - желтый
                int calculatedWidth;
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
                }

                System.out.println("calculatedWidth = " + calculatedWidth);
                progressPixmap = createProceduralPixmap(calculatedWidth - 2, barHeight - 2, new Color(0xf2d900ff));
            } else {    // получена - темно-оранжевый цвет
                progressPixmap = createProceduralPixmap(barWidth - 2, barHeight - 2, new Color(0xa29100ff));
            }
            Pixmap backPixmap = createProceduralPixmap(barWidth, barHeight, new Color(0x464642));
            barTexture = new Texture(progressPixmap);
            bgBarTexture = new Texture(backPixmap);
        }
    }

    private void createBackground() {
        Pixmap bgPixmap = createProceduralPixmap((int) getWidth(), (int) getHeight(), new Color(0x6da86bff));
        bgTexture = new Texture(bgPixmap);
        addBackground(new Image(bgTexture), false, false);
    }

    private Pixmap createProceduralPixmap(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }

    class BackButton extends Group {
        Image bg;
        Image back;
        Image backDown;
        Label backLabel;

        public BackButton() {
            bg = new Image(Warfare.atlas.findRegion("coinsPanel"));
            back = new Image(Warfare.atlas.findRegion("backImage"));
            backDown = new Image(Warfare.atlas.findRegion("backImage_dwn"));
            backDown.setVisible(false);
//            setSize(backDown);

            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.fontColor = Color.WHITE;
            labelStyle.font = Warfare.font20;
            backLabel = new Label("Back", labelStyle);

            back.setPosition((bg.getWidth() - back.getWidth()) / 2 - 4, bg.getHeight() / 2);
            backDown.setPosition(back.getX() - (backDown.getWidth() - back.getWidth()) / 2,
                    back.getY() - (backDown.getHeight() - back.getHeight()) / 2);
            backLabel.setPosition((bg.getWidth() - backLabel.getWidth()) / 2, 0);

            addActor(bg);
            addActor(back);
            addActor(backDown);
            addActor(backLabel);

            addCaptureListener(new EventListener() { // добавляет слушателя события корневому элементу, отключая его для дочерних элементов
                @Override
                public boolean handle(Event event) {
                    event.setTarget(BackButton.this);
                    return true;
                }
            });

            addListener(new ClickListener() { // создаем слушателя события нажатия кнопки
                // переопределяем метод TouchDown(), который называется прикасание
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    backDown.setVisible(true); // устанавливаем видимость для фона нажатой кнопки, а также оставим вызов метода суперкласса
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    call(ON_BACK);
                    super.clicked(event, x, y);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    backDown.setVisible(false);
                    super.touchUp(event, x, y, pointer, button);
                }
            });
        }
    }

    class StarsPanelSmall extends Group {
        Image bg, star;
        Label starsCountLabel;

        public StarsPanelSmall() {
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.fontColor = Color.WHITE;
            labelStyle.font = Warfare.font20;
            starsCountLabel = new Label("" + starsCount, labelStyle);

            bg = new Image(Warfare.atlas.findRegion("star_panel_small"));
            star = new Image(Warfare.atlas.findRegion("star_icon"));

            star.setPosition(4, 4);
            starsCountLabel.setPosition(16 + star.getWidth() + 4, 0);

            setSize(bg.getWidth(), bg.getHeight());

            addActor(bg);
            addActor(star);
            addActor(starsCountLabel);


        }
    }
}

package com.timgapps.warfare.Level.GUI.Screens.RewardForStars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.Level.GUI.Finger;
import com.timgapps.warfare.Level.GUI.Screens.CoinsPanel;
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
    //    private Hilite hilite;      // подсветка на фоне награды за звёзды
    private Group group;
    private Table scrollTable;
    private GameManager gameManager;
    private CoinsPanel coinsPanel;

    public RewardForStarsScreen(GameManager gameManager) {
        createBackground();
        this.gameManager = gameManager;
        coinsPanel = gameManager.getCoinsPanel();
        coinsPanel.setVisible(true);
        addChild(coinsPanel);

        /** получим текущее кол-во звезд **/
//        starsCount = 7;
//        starsCount = gameManager.getStarsPanel().getStarsCount();
        starsCount = gameManager.getSavedGame().getStarsCount();
//        starsCount = 100;
        System.out.println("starsCount = " + starsCount);
//        starsCount = 15;
//        starsCount = 3;
//        starsCount = 30;

        backButton = new BackButton();
        backButton.setPosition(64, 64);
        addOverlayChild(backButton);
        rewardForStarsDataList = gameManager.getRewardForStarsDataList();  // список данных наград
        rewardForStarsList = new ArrayList<RewardForStars>();               // список наград
        Label.LabelStyle countStarsLabelStyle = new Label.LabelStyle();
        countStarsLabelStyle.fontColor = new Color(0x3c644eff);
//        countStarsLabelStyle.fontColor = Color.FOREST;
        countStarsLabelStyle.font = Warfare.font20;
        StarsPanelSmall starsPanelSmall = new StarsPanelSmall();
        float scrollTableWidth = getWidth();
        float groupWidth = 0;
        group = new Group();
//        Group group = new Group();
        int currentIndexSmallPanel = 0;
        int index = 0;  // индекс текущего количества звезд, используется в рассчете поз. х smallStarsPanel
        int lastCount = 0, rewardCount = 0;     // кол-во звезд за последнюю награду и текущую награду
        int calculatedWidth = 0;       // вычисленная координата для starsSmallPanel

        /** создадим картинки и бары **/
        for (int i = 0; i < rewardForStarsDataList.size(); i++) {
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
            rewardForStarsList.add(new RewardForStars(this,
                    rewardForStarsDataList.get(i), gameManager, deltaCountStars, lastRewardCountStars));

            rewardForStarsList.get(i).setPosition(190 * i + rewardForStarsList.get(i).getWidth(), 144);
            group.addActor(rewardForStarsList.get(i));      // добавляем RewardForStars в корневую группу
            /** обозначим доступные награды за звезды **/
            if (starsCount > rewardForStarsDataList.get(i).getStarsCount()) {
                rewardForStarsDataList.get(i).setChecked();
            }
            if ((starsCount >= rewardForStarsDataList.get(i).getStarsCount()) && (!rewardForStarsDataList.get(i).getIsReceived())) {
                rewardForStarsList.get(i).setChecked(); // установим доступной для получения (подсветим "ЖЕЛТЫМ" цветом)
            }
            /** добавим цифры - кол-во звёзд необходимое для получения награды **/
            countLabel = new Label("" + rewardForStarsList.get(i).getRewardCountStars(), countStarsLabelStyle);
            countLabel.setPosition(rewardForStarsList.get(i).getX() + BG_PANEL_WIDTH / 2 - countLabel.getWidth(),
                    rewardForStarsList.get(i).getY() - countLabel.getHeight() - 48);
            group.addActor(countLabel);
            // получим кол-во звезд, для достижения текущей награды
            int rewardStarsCount = rewardForStarsList.get(i).getRewardCountStars();
            if ((starsCount >= lastRewardCountStars) && (starsCount <= rewardStarsCount)) {
                lastCount = lastRewardCountStars;
                rewardCount = rewardStarsCount;
                index = i;
            }
        }
        rewardForStarsList.get(index).setHilite();
        // получим кол-во звезд, для достижения текущей награды
//        int rewardStarsCount = rewardForStarsList.get(i).getRewardCountStars();
        int deltaCountStars = starsCount - lastCount;
        if (starsCount < rewardCount) {
            if (deltaCountStars >= 0) {
                calculatedWidth = deltaCountStars * (barWidth - 2) / (rewardCount - lastCount);
                if (calculatedWidth <= 0) calculatedWidth = 2;
            } else {
                calculatedWidth = 2;
            }
        } else {
            calculatedWidth = barWidth;
        }

        xPos = (index) * (184 + 8) + calculatedWidth;
        groupWidth += 190 * rewardForStarsList.size() + rewardForStarsList.get(0).getWidth();
        starsPanelSmall.setPosition(xPos - starsPanelSmall.getWidth() / 2, 0);
        if (starsCount == 0) starsPanelSmall.setVisible(false);
        else {
            starsPanelSmall.setVisible(true);
        }
        group.addActor(starsPanelSmall);
        /** scroller - это окно прокрутки, сама прокрутка **/
        scrollTable = new Table();
//        Table scrollTable = new Table();
        scrollTable.debug();
        scrollTable.add(group).width(groupWidth).height(360);
        final ScrollPane scroller = new ScrollPane(scrollTable);
        scroller.debug();
        Table table = new Table();
        table.debug();
        table.left().top();
        table.setWidth(scrollTableWidth);
        table.setHeight(360);
        table.add(scroller).fill().expand();
        table.setPosition(0, 240);
        addChild(table);
    }

    public CoinsPanel getCoinsPanel() {
        return coinsPanel;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void updateXposSmallStarsPanel(float xPos) {
        this.xPos = xPos;
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
            mta.setPosition(Warfare.V_WIDTH / 2 - 200, Warfare.V_HEIGHT / 2 + 260);
            mta.setDuration(0.7f);
            AlphaAction alphaActionEnd = new AlphaAction();
            alphaActionEnd.setAlpha(0);
            alphaActionEnd.setDuration(1f);
            SequenceAction sa = new SequenceAction(alphaActionStart, mta, alphaActionEnd, checkEndOfAction);
            toastLabel.addAction(sa);
            isStartToastAction = true;
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

    public float getScrollTableX() {
        return scrollTable.getX();
    }

    public float getScrollTableY() {
        return scrollTable.getY();
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
    }

    public void startAddAction(Actor actor, float x, float y, float deltaX) {
        actor.setPosition(x, y);
        addChild(actor);
        final Image upgradeIconImage = new Image(Warfare.atlas.findRegion("teamButton"));
        upgradeIconImage.addAction(Actions.fadeOut(0));
        upgradeIconImage.setPosition(32, getHeight() / 3);
        addChild(upgradeIconImage);

        final SequenceAction upgradeIconAction = new SequenceAction(Actions.fadeIn(0),
                Actions.sizeBy(8, 8, 0.2f),
                Actions.sizeBy(-8, -8, 0.2f),
                Actions.fadeOut(0)
        );

        Action checkEndOfAction = new Action() {
            @Override
            public boolean act(float delta) {
                upgradeIconImage.addAction(upgradeIconAction);
                return true;
            }
        };

        float xPos;
        if (x - 32 < 260) {
            xPos = 260;
        } else {
            xPos = x;
        }
        ParallelAction pa = new ParallelAction(
                Actions.moveTo(32 - deltaX, getHeight() / 3, 1, Interpolation.pow3In),
//                Actions.moveTo(32 - deltaX, getHeight() / 3, 1, Interpolation.pow2Out),
//                Actions.moveTo(32 - deltaX, getHeight() / 3, 1, new Interpolation.SwingOut(1)),
                Actions.sizeTo(actor.getWidth() * 0.7f, actor.getHeight() * 0.7f, 1));
        SequenceAction sma = new SequenceAction(
                Actions.moveTo(xPos, y - 80, 0.5f, Interpolation.pow2Out),
//                Actions.moveTo(xPos, y - 80, 0.5f, Interpolation.smooth),
//                Actions.moveTo(xPos, y - 80, 0.5f, new Interpolation.SwingOut(1)),
                pa,
                Actions.fadeOut(0),
                checkEndOfAction
        );
//        actor.addAction(sma);
        actor.addAction(sma);
    }
}

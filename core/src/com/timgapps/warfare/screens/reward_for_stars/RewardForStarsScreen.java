package com.timgapps.warfare.screens.reward_for_stars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.boontaran.games.StageGame;
import com.timgapps.warfare.screens.map.gui_elements.CoinsPanel;
import com.timgapps.warfare.GameManager;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.reward_for_stars.gui_elements.BackButton;
import com.timgapps.warfare.screens.reward_for_stars.interfaces.ScreenCloser;

import java.util.ArrayList;

public class RewardForStarsScreen extends StageGame implements ScreenCloser {
    public static final int ON_BACK = 1;
    private ArrayList<RewardForStarsData> rewardForStarsDataList;           // массив данных наград
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
    private Group group;
    private Table scrollTable;
    private GameManager gameManager;
    private CoinsPanel coinsPanel;
    private StarsPanelSmall starsPanelSmall;

    @Override
    public void show() {
        super.show();
        redrawScreen();
    }

    public RewardForStarsScreen(GameManager gameManager) {
        createBackground();
        this.gameManager = gameManager;
        coinsPanel = gameManager.getCoinsPanel();
//        coinsPanel.setVisible(true);


        /** получим текущее кол-во звезд **/
//        starsCount = 7;
        starsCount = gameManager.getSavedGame().getStarsCount();
        // для теста
//        starsCount = 110;
        System.out.println("starsCount = " + starsCount);

        backButton = new BackButton(this);
        backButton.setPosition(64, 64);
        addOverlayChild(backButton);
        rewardForStarsDataList = gameManager.getRewardForStarsDataList();  // список данных наград
        rewardForStarsList = new ArrayList<RewardForStars>();               // список наград
        starsPanelSmall = new StarsPanelSmall();
        float scrollTableWidth = getWidth();
        float groupWidth = 0;
        group = new Group();

        scrollTable = new Table();
        scrollTable.debug();
        group.addActor(starsPanelSmall);


        /** scroller - это окно прокрутки, сама прокрутка **/
//        scrollTable = new Table();
//        scrollTable.debug();

//        scrollTable.add(group).width(groupWidth).height(360);
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

    private void redrawScreen() {
        starsCount = gameManager.getSavedGame().getStarsCount();
        addChild(coinsPanel);
        group.clearChildren();
        scrollTable.clearChildren();
        coinsPanel.setVisible(true);
        int index = 0;  // индекс текущего количества звезд, используется в рассчете поз. х smallStarsPanel
        int lastCount = 0, rewardCount = 0;     // кол-во звезд за последнюю награду и текущую награду
        int calculatedWidth = 0;       // вычисленная координата для starsSmallPanel
        float groupWidth = 0;
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
//            rewardForStarsList.get(i)
            group.addActor(rewardForStarsList.get(i));      // добавляем RewardForStars в корневую группу
            rewardForStarsList.get(i).redraw();

            /** добавим цифры - кол-во звёзд необходимое для получения награды **/

            Label.LabelStyle countStarsLabelStyle = new Label.LabelStyle();
            countStarsLabelStyle.fontColor = new Color(0x3c644eff);
//        countStarsLabelStyle.fontColor = Color.FOREST;
            countStarsLabelStyle.font = Warfare.font20;

            countLabel = new Label("" + rewardForStarsList.get(i).getRewardCountStars(), countStarsLabelStyle);
            countLabel.setText("" + rewardForStarsList.get(i).getRewardCountStars());
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
        rewardForStarsList.get(index).setHilite(true);
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

        starsPanelSmall.setStarsCount(starsCount);
        xPos = (index) * (184 + 8) + calculatedWidth;
//        groupWidth += 190 * rewardForStarsList.size() + rewardForStarsList.get(0).getWidth();
        groupWidth = 190 * rewardForStarsList.size() + rewardForStarsList.get(0).getWidth();
        group.setSize(groupWidth, 360);
        starsPanelSmall.setPosition(xPos - starsPanelSmall.getWidth() / 2, 0);
        if (starsCount == 0) {
            starsPanelSmall.setVisible(false);
        } else {
            starsPanelSmall.setVisible(true);
        }

        group.addActor(starsPanelSmall);
        scrollTable.add(group).width(groupWidth).height(360);
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

    public void closeScreen() {
        hide();
        removeChild(coinsPanel);
        call(ON_BACK);
    }

    @Override
    public void hide() {
        super.hide();
//        dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
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

        void setStarsCount(int count) {
            starsCountLabel.setText("" + count);
        }
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
    }
}

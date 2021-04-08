package com.timgapps.warfare.screens.reward_for_stars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
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
    protected final int BAR_WIDTH = 224;        // (184)
    //    protected final int barWidth = 184;
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
    private Label titleLabel, textLabel, nextRewardLabel;
    private final float SPACING_BETWEEN_REWARDS = 230; // (190)
    private int index;      // индекс следующей награды за звезды
    private float tableScrollPosX = 32;
    private ScrollPane scroller;

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

        Label.LabelStyle titleLabelStyle = new Label.LabelStyle();
        titleLabelStyle.fontColor = Color.WHITE;
        titleLabelStyle.font = Warfare.font20;

//        GREEN
//        CHARTREUSE
//        LIME
//        FOREST
//        OLIVE

        Label.LabelStyle textLabelStyle = new Label.LabelStyle();
        textLabelStyle.fontColor = new Color(0x3c644eff);
//        textLabelStyle.fontColor = Color.CHARTREUSE;
        textLabelStyle.font = Warfare.font20;

        Label.LabelStyle nextRewardLabelStyle = new Label.LabelStyle();
//        textLabelStyle.fontColor = new Color(0x3c644eff);
//        new Color(0xf2d900ff)
//        nextRewardLabelStyle.fontColor = Color.CHARTREUSE;
        nextRewardLabelStyle.fontColor = new Color(0xf2d900ff);
        nextRewardLabelStyle.font = Warfare.font20;

        String titleText = "Rewards for stars";
        String textLabelText = "Collect stars to get reward";
        String nextRewardText = "Next reward";

        // Надпись "награда за звезды"
        titleLabel = new Label(titleText, titleLabelStyle);
        titleLabel.setPosition((getWidth() - titleLabel.getWidth()) / 2, getHeight() - 64);
        // надпись "собирай звезды чтобы получить награду"
        textLabel = new Label(textLabelText, textLabelStyle);
        textLabel.setPosition((getWidth() - textLabel.getWidth()) / 2, getHeight() - 102);
        // надпись "следующая награда"
        nextRewardLabel = new Label(nextRewardText, nextRewardLabelStyle);
//        nextRewardLabel.setPosition((getWidth() - nextRewardLabel.getWidth()) / 2, getHeight() - 64);

        /** получим текущее кол-во звезд **/
//        starsCount = 7;
        starsCount = gameManager.getSavedGame().getStarsCount();
        // для теста
//        starsCount = 110;
//        System.out.println("starsCount = " + starsCount);

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
        scroller = new ScrollPane(scrollTable);
//        final ScrollPane scroller = new ScrollPane(scrollTable);
        scroller.debug();
        Table table = new Table();
        table.debug();
        table.left().top();
        table.setWidth(scrollTableWidth);
        table.setHeight(360);
        table.add(scroller).fill().expand();
        // установим позицию таблицы со скроллом со списком наград за звезды (
        table.setPosition(tableScrollPosX, 200);
//        table.setPosition(tableScrollPosX, 240);
        addChild(table);
        addChild(titleLabel);
        addChild(textLabel);

    }

    private void redrawScreen() {
        starsCount = gameManager.getSavedGame().getStarsCount();
        addChild(coinsPanel);
        group.clearChildren();
        rewardForStarsList.clear();
        scrollTable.clearChildren();
        coinsPanel.redraw();
        coinsPanel.setVisible(true);
        scroller.setScrollX(index * BAR_WIDTH - BAR_WIDTH / 2);
//        int index = 0;  // индекс текущего количества звезд, используется в рассчете поз. х smallStarsPanel
//        int index = 0;  // индекс текущего количества звезд, используется в рассчете поз. х smallStarsPanel
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

            rewardForStarsList.get(i).setPosition(SPACING_BETWEEN_REWARDS * i + rewardForStarsList.get(i).getWidth(), 144);
//            rewardForStarsList.get(i)
            group.addActor(rewardForStarsList.get(i));      // добавляем RewardForStars в корневую группу
            rewardForStarsList.get(i).redraw();

            /** добавим цифры - кол-во звёзд необходимое для получения награды **/
            Label.LabelStyle countStarsLabelStyle = new Label.LabelStyle();
            countStarsLabelStyle.fontColor = new Color(0x3c644eff);
            countStarsLabelStyle.font = Warfare.font20;

            countLabel = new Label("" + rewardForStarsList.get(i).getRewardCountStars(), countStarsLabelStyle);
            countLabel.setText("" + rewardForStarsList.get(i).getRewardCountStars());
            countLabel.setPosition(rewardForStarsList.get(i).getX() + BG_PANEL_WIDTH / 2 - countLabel.getWidth(),
                    rewardForStarsList.get(i).getY() - countLabel.getHeight() - 48);
            group.addActor(countLabel);
            // получим кол-во звезд, для достижения текущей награды
            int rewardStarsCount = rewardForStarsList.get(i).getRewardCountStars();
            // здесь определим индекс следующей награды за звезды
            if ((starsCount >= lastRewardCountStars) && (starsCount <= rewardStarsCount)) {
                lastCount = lastRewardCountStars;
                rewardCount = rewardStarsCount;
                index = i;
            }
        }
        // подсветим следующую награду
        if (!gameManager.getSavedGame().isHaveFullRewardsForStars()) {
            setNextRewardForStars(index);
        }
        // получим кол-во звезд, для достижения текущей награды
//        int rewardStarsCount = rewardForStarsList.get(i).getRewardCountStars();
        int deltaCountStars = starsCount - lastCount;
        if (starsCount < rewardCount) {
            if (deltaCountStars >= 0) {
                calculatedWidth = deltaCountStars * (BAR_WIDTH - 2) / (rewardCount - lastCount);
                // (= 1 * (240 - 2) / 3
                if (calculatedWidth <= 0) calculatedWidth = 2;
            } else {
                calculatedWidth = 2;
            }
        } else {
            calculatedWidth = BAR_WIDTH;
        }

        starsPanelSmall.setStarsCount(starsCount);
        xPos = (index) * (BAR_WIDTH + 8) + calculatedWidth - tableScrollPosX;
//        xPos = (index) * (BAR_WIDTH + 8) + calculatedWidth;
//        groupWidth += 190 * rewardForStarsList.size() + rewardForStarsList.get(0).getWidth();
        groupWidth = SPACING_BETWEEN_REWARDS * rewardForStarsList.size() + rewardForStarsList.get(0).getWidth();
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

    public void setNextRewardForStars(int index) {
        rewardForStarsList.get(index).setHilite(true);
        rewardForStarsList.get(index).showNextRewardLabel(nextRewardLabel);
    }

    private void hideHilite() {

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
        String toastText = "Collect " + starsCount + " stars for reward";
        showToast(toastText);
    }

    public void showToast(String toastText) {
        if (isStartToastAction == false) {
//            System.out.println("show Toast!");
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.fontColor = Color.RED;
            labelStyle.font = Warfare.font40;
//            String toastText = "Collect " + starsCount + " stars for reward";
            final Label toastLabel = new Label("" + toastText, labelStyle);
            toastLabel.setPosition((Warfare.V_WIDTH - toastLabel.getWidth()) / 2, Warfare.V_HEIGHT / 2);
            addChild(toastLabel);
            Action checkEndOfAction = new Action() {
                @Override
                public boolean act(float delta) {
                    isStartToastAction = false;
                    removeToastLabel(toastLabel);
                    return true;
                }
            };
            AlphaAction alphaActionStart = new AlphaAction();
            alphaActionStart.setAlpha(1);
            alphaActionStart.setDuration(0.02f);
            MoveToAction mta = new MoveToAction();
            mta.setPosition((getWidth() - toastLabel.getWidth()) / 2, getHeight() / 2 + 210);
//            mta.setPosition((Warfare.V_WIDTH - toastLabel.getWidth()) / 2, Warfare.V_HEIGHT / 2 + 210);
//            mta.setPosition(Warfare.V_WIDTH / 2 - 200, Warfare.V_HEIGHT / 2 + 210);
            mta.setDuration(0.7f);
            mta.setInterpolation(Interpolation.pow3Out);
            AlphaAction alphaActionEnd = new AlphaAction();
            alphaActionEnd.setAlpha(0);
            alphaActionEnd.setDuration(1f);
            SequenceAction sa = new SequenceAction(alphaActionStart, mta, alphaActionEnd, checkEndOfAction);
            toastLabel.addAction(sa);
            isStartToastAction = true;
        }
    }

    private void removeToastLabel(Label label) {
        removeChild(label);
    }

    private void createBackground() {
        Pixmap bgPixmap = createProceduralPixmap((int) getWidth(), (int) getHeight(), new Color(0x6da86bff));
        bgTexture = new Texture(bgPixmap);
        addBackground(new Image(bgTexture), false, false);

        Image starOne = new Image(Warfare.atlas.findRegion("star_vector"));
        Image starTwo = new Image(Warfare.atlas.findRegion("star_vector"));
        Image starThree = new Image(Warfare.atlas.findRegion("star_vector"));
        Image starFour = new Image(Warfare.atlas.findRegion("star_vector"));
        Image starFive = new Image(Warfare.atlas.findRegion("star_vector"));

        starOne.setPosition(240, 490);
        starTwo.setPosition(940, 540);
        starTwo.setScale(1.2f);
        starTwo.setRotation(25);
        starThree.setPosition(1000, 50);
        starThree.setScale(2f);
        starFour.setPosition(640, 170);
        starFour.setScale(1.1f);
        starFour.setRotation(-25);
        starFive.setPosition(300, 70);
        starFive.setRotation(-10);
        addChild(starOne);
        addChild(starTwo);
        addChild(starThree);
        addChild(starFour);
        addChild(starFive);
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

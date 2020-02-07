package com.timgapps.warfare.Level.GUI.Screens.UpgradeWindow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.timgapps.warfare.Level.GUI.Screens.ResourcesView.ResourcesTable;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;
import com.timgapps.warfare.Level.GUI.Screens.TeamUpgradeScreen;
import com.timgapps.warfare.Level.GameManager;
import com.timgapps.warfare.Warfare;

import java.util.ArrayList;

public class UpgradeScreen extends Group {

    public static final int ON_RESUME = 1;
    private Image background;
    //    private Image imageContainer;
    private ImageButton closeButton;
    private Table infoTable;        // таблица содержит данные о характеристиках юнита

    private Table container;

    private Label healthLabel;      // текст "ЗДОРОВЬЕ"
    private Label damageLabel;      // текст "УРОН"
    private Label speedLabel;       // текст "СКОРОСТЬ"
    private Label timePrepearLabel;       // текст "ВРЕМЯ ПОЯВЛЕНИЯ"
    private Label upgradeCostLabel;       // текст "СТОИМОСТЬ АПРГРЕЙДА"

    private Label healthValueLabel;      // текст количество "здоровья"
    private Label damageValueLabel;      // текст количество "урона"
    private Label speedValueLabel;       // текст количество "скорость"
    private Label timePrepearValueLabel;       // текст значение "время появления"

    private Label healthAddValueLabel;      // текст на сколько прибавится "здоровье"
    private Label damageAddValueLabel;      // текст на сколько прибавится "здоровье"

    private Label foodCostLabel, ironCostLabel, woodCostLabel, upgradeToLevelLabel;

    private int foodCostValue, ironCostValue, woodCostValue;        // значение количества ресурсов, необходимое для апргрейда

    private Label toastLabel;

    private int healthValue;
    private int damageValue;
    private int speedValue;
    private int timePrepearValue;

    private int addHealthValue, addDamageValue;

    private String healthText = "Health";
    private String damageText = "Damage";
    private String speedText = "Speed";
    private String timePrepearText = "Time prepear";
    private String upgradeCostText = "Upgrade cost";
    private String upgradeToLevelText = "Upgrade to Level ";

    private float containerX, containerY;
    private float paddingLeft = 48;

    private Image foodIcon, ironIcon, woodIcon;

    private UpgradeButton upgradeButton;
    private ArrayList<TeamEntity> team;

    private Group imageContainer;           // контейнер - Group для ханения изображения юнита со значком уровня и энергии
    private UnitImage unitImage;

    private int unitLevel;
    private int newUnitLevel;

    private int coinsCount;

    private int upgradeCost;

    private final int COST_UPGRADE = 50;

//    private Actor imageContainer;

    private GameManager gameManager;

    private String noResources = "Not enought resources!";
    private String noCoins = "Not enought coins!";

    private boolean isStartToastAction = false;

    private boolean canBeUpgrade;
    private TeamEntity teamEntity;

    private ResourcesTable resourcesTable;

    private UnitLevelIcon unitLevelIcon;

    private TextureRegionDrawable textureRegionDrawableBg;

    private YellowButton selectUnitButton;  // кнопка "ВЫБРАТЬ" для игрового юнита,
    // если игровой юнит не состоит в коменде, а нах-ся в коллекции


    private TeamUpgradeScreen teamUpgradeScreen;

    public UpgradeScreen(GameManager gameManager, TeamUpgradeScreen teamUpgradeScreen) {

        this.teamUpgradeScreen = teamUpgradeScreen;

        this.gameManager = gameManager;
        this.team = gameManager.getTeam();              // команда - массив типа TeamEntity
        coinsCount = gameManager.getCoinsCount();

        /** зададим значения характеристик юнита**/
//        containerX = x;
//        containerY = y;


        foodIcon = new Image(Warfare.atlas.findRegion("food_icon"));
        ironIcon = new Image(Warfare.atlas.findRegion("iron_icon"));
        woodIcon = new Image(Warfare.atlas.findRegion("wood_icon"));

        foodCostValue = 2;
        ironCostValue = 2;
        woodCostValue = 1;

        background = new Image(Warfare.atlas.findRegion("upgradeScreen"));
        background.setX((Warfare.V_WIDTH - background.getWidth()) / 2); // устанавливаем позицию заголовка
        background.setY(Warfare.V_HEIGHT / 2 - background.getHeight() / 2);

        addActor(background);

        closeButton = new ImageButton(new TextureRegionDrawable(Warfare.atlas.findRegion("button_close")),
                new TextureRegionDrawable(Warfare.atlas.findRegion("button_close_dwn")));

        closeButton.setX(background.getX() + background.getWidth() - closeButton.getWidth() - 28);
        closeButton.setY(background.getY() + background.getHeight() - closeButton.getHeight() - 8);
        addActor(closeButton);


        /** слушатель для КНОПКИ ЗАКРЫТИЯ ОКНА **/
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                fire(new MessageEvent(ON_RESUME));
                if (toastLabel.isVisible()) toastLabel.setVisible(false);
                hideUpgradeScreen();
            }
        });

        imageContainer = new Group();

//        /** Таблицы в режиме DEBUG **/
//        container = new Table().debug();        // таблица "КОНТЕЙНЕР"
////        infoTable = new Table().debug();       // таблица с характеристиками юнита
//
////        container = new Table();        // таблица "КОНТЕЙНЕР"
//        infoTable = new Table();       // таблица с характеристиками юнита

        upgradeButton = new UpgradeButton();
        upgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                /** проверяем, может ли быть сделан апгрейд, если да - делаем апгрейд, если нет проверяем чего не хватает и выводим сообщение **/
                if (canBeUpgrade)
                    upgradeTeamEntity(teamEntity);
                else
                    applyActionsToToast();
                checkRecourcesAndCoinsCount();
            }

        });

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.DARK_GRAY;
        labelStyle.font = Warfare.font20;

        Label.LabelStyle greenLabelStyle = new Label.LabelStyle();
        greenLabelStyle.fontColor = Color.FOREST;
        greenLabelStyle.font = Warfare.font20;

        Label.LabelStyle redLabelStyle = new Label.LabelStyle();
        redLabelStyle.fontColor = Color.RED;
        redLabelStyle.font = Warfare.font20;


        healthLabel = new Label(healthText, labelStyle);        // текст "ЗДОРОВЬЕ"
        damageLabel = new Label(damageText, labelStyle);        // текст "УРОН"
        speedLabel = new Label(speedText, labelStyle);          // текст "СКОРОСТЬ"
        timePrepearLabel = new Label(timePrepearText, labelStyle);          // текст "ВРЕМЯ ПОЯВЛЕНИЯ"

        healthValueLabel = new Label("" + healthValue, labelStyle);        // текст значения здоровья
        damageValueLabel = new Label("" + damageValue, labelStyle);        // текст значения урон
        speedValueLabel = new Label("" + speedValue, labelStyle);          // текст значения скорость
        timePrepearValueLabel = new Label("" + timePrepearValue, labelStyle);          // текст значения скорость

        healthAddValueLabel = new Label(" + " + addHealthValue, greenLabelStyle);  // текст на сколько прибавится здоровья
        damageAddValueLabel = new Label(" + " + addDamageValue, greenLabelStyle);  // текст на сколько прибавитмя урон

        upgradeCostLabel = new Label(upgradeCostText, labelStyle);          // текст "СТОИМОСТЬ УЛУЧШЕНИЯ"

        toastLabel = new Label("", redLabelStyle);          // текст "СТОИМОСТЬ УЛУЧШЕНИЯ"

        healthLabel.setAlignment(Align.right);
        damageLabel.setAlignment(Align.right);
        speedLabel.setAlignment(Align.right);
        timePrepearLabel.setAlignment(Align.right);

        healthValueLabel.setAlignment(Align.left);
        damageValueLabel.setAlignment(Align.left);
        speedValueLabel.setAlignment(Align.left);
        timePrepearValueLabel.setAlignment(Align.left);

        upgradeCostLabel.setAlignment(Align.center);

        foodCostLabel = new Label("" + foodCostValue, labelStyle);
        ironCostLabel = new Label("" + ironCostValue, labelStyle);
        woodCostLabel = new Label("" + woodCostValue, labelStyle);
        upgradeToLevelLabel = new Label(upgradeToLevelText + newUnitLevel, labelStyle);

        /** Таблицы в режиме DEBUG **/
//        container = new Table().debug();        // таблица "КОНТЕЙНЕР"
        infoTable = new Table().debug();       // таблица с характеристиками юнита
//        infoTable = new Table();       // таблица с характеристиками юнита

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(new Color(0xfde9c0ff));
        bgPixmap.fill();
        textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

        infoTable.setBackground(textureRegionDrawableBg);

        container = new Table();        // таблица "КОНТЕЙНЕР"
        container.debug();

        container.setHeight(600);
        container.setWidth(550);
        container.left().top();
        container.add(infoTable).left();

        infoTable.add(healthLabel).width(200).padRight(8);
        infoTable.add(new Image(Warfare.atlas.findRegion("heart_icon"))).width(56).height(56);
        infoTable.add(healthValueLabel).width(48).padLeft(8);
        infoTable.add(healthAddValueLabel);


//        infoTable.add(healthValueLabel).fillX().padLeft(8);
//        infoTable.add(healthAddValueLabel);


        infoTable.row();
        infoTable.add(damageLabel).width(200).padRight(8);
        infoTable.add(new Image(Warfare.atlas.findRegion("damage_icon"))).width(56).height(56);
        infoTable.add(damageValueLabel).fillX().padLeft(8);
        infoTable.add(damageAddValueLabel);

        infoTable.row();
        infoTable.add(speedLabel).width(200).padRight(8);
        infoTable.add(new Image(Warfare.atlas.findRegion("speed_icon"))).width(56).height(56);
        infoTable.add(speedValueLabel).fillX().padLeft(8);
        infoTable.row();

        infoTable.add(timePrepearLabel).width(200).padRight(8);
        infoTable.add(new Image(Warfare.atlas.findRegion("clock_icon"))).width(56).height(56);
//        infoTable.add(timePrepearValueLabel).expandX().padLeft(8);
        infoTable.add(timePrepearValueLabel).fillX().padLeft(8);

        /** добавим в общий контейнер изображение юнита и его значок со значением уровня юинта**/
        container.add(imageContainer).width(200).padLeft(32).left().padTop(32);


        /** Таблица СТОИМОСТЬ АПГРЕЙДА в режиме DEBUG **/
//        Table costUpgradeTable = new Table().debug();

        Table costUpgradeTable = new Table();

        Table line = new Table();
        line.setBackground(new TextureRegionDrawable(Warfare.atlas.findRegion("div_line")));
        line.add(upgradeCostLabel).width(500);

        costUpgradeTable.add(line).colspan(6).padTop(24).padBottom(16);
        costUpgradeTable.row();
        costUpgradeTable.add(foodIcon).width(64).left().padLeft(16);
        costUpgradeTable.add(foodCostLabel).expandX().left().padLeft(16);

        costUpgradeTable.add(ironIcon).width(64).left();
        costUpgradeTable.add(ironCostLabel).expandX().left().padLeft(16);

        costUpgradeTable.add(woodIcon).width(64).left();
        costUpgradeTable.add(woodCostLabel).padLeft(16).padRight(16);
        costUpgradeTable.row();
        costUpgradeTable.add(upgradeToLevelLabel).colspan(6).padTop(32);

        container.row();
        container.add(costUpgradeTable).colspan(3);
        container.row();

        container.add(upgradeButton).height(upgradeButton.getHeight()).width(upgradeButton.getWidth()).colspan(3).padTop(8);

        container.setPosition(background.getX() + paddingLeft, background.getY());
        container.setPosition(background.getX() + paddingLeft, background.getY() - 16);
        addActor(container);

        /** Добавляем панель (таблицу) с реурсами(ПИЩА, ЖЕЛЕЗО, ДЕРЕВО) **/
        resourcesTable = new ResourcesTable(gameManager.getFoodCount(),
                gameManager.getIronCount(),
                gameManager.getWoodCount());

        resourcesTable.setPosition(container.getX() + container.getWidth() + 48,
                container.getY() - (resourcesTable.getHeight() - container.getHeight()));
        addActor(resourcesTable);
        setVisible(false);

        toastLabel.setVisible(false);
        toastLabel.setPosition(Warfare.V_WIDTH / 2 - toastLabel.getWidth() - 200, Warfare.V_HEIGHT / 2);
        addActor(toastLabel);


    }

    public void showUpgradeScreen(boolean showSelectButton) {

        setVisible(true);
        /** добавим объект - изображение юнита со значком уровня юнита**/
        imageContainer.addActor(unitImage);

        /** если юнит не состоит в команде, отображаем кнопку "ВЫБРАТЬ" **/
        if (showSelectButton) {
            unitImage.getSelectButton().setVisible(true);
        } else {
            unitImage.getSelectButton().setVisible(false);
        }
    }

    /**
     * скрываем окно апгрейда
     **/
    public void hideUpgradeScreen() {
        imageContainer.removeActor(unitImage);
        setVisible(false);
    }

    /**
     * метод устанавливает значения параметров для улучшения юнита и его изображение
     **/
    public void setUnitUpgradeData(TeamEntity teamEntity) {
        this.teamEntity = teamEntity;
        addHealthValue = teamEntity.getAddHealthValue();
        addDamageValue = teamEntity.getAddDamageValue();
        healthValue = teamEntity.getHEALTH();
        damageValue = teamEntity.getDAMAGE();
        speedValue = teamEntity.getSPEED();
        timePrepearValue = teamEntity.getTimePrepare();


        /** получим объект unitImage - изображение со значками (уровень юнита и стоимость энергии) **/
        unitImage = teamEntity.getUnitImage();
        unitImage.setLevelValue(teamEntity.getUnitLevel());


        /** получим кнопку "ВЫБРАТЬ ЮНИТА", если юнит не состоит в игровой команде, а находится в коллекции**/
        selectUnitButton = unitImage.getSelectButton();

        selectUnitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                fire(new MessageEvent(ON_RESUME));
//                if (toastLabel.isVisible()) toastLabel.setVisible(false);

//                showReplaceUnit(teamEntity);


                showReplace();

                /** скрываем экран апгрейда **/
                hideUpgradeScreen();

            }
        });

        newUnitLevel = teamEntity.getUnitLevel() + 1;

        upgradeToLevelLabel.setText(upgradeToLevelText + newUnitLevel);
        upgradeCost = teamEntity.getUnitLevel() * COST_UPGRADE;
        checkRecourcesAndCoinsCount();

        /** обновим значения всех текстовых значений в информации об апргейде с помощью метода **/
        updateLabelsText();
    }

    /**
     * убираем коллекцию с экрана и отображаем выбранного для замены юнита
     **/
    private void showReplace() {
        teamUpgradeScreen.showReplaceUnit(teamEntity);
    }

    private void showReplaceUnit(TeamEntity teamEntity) {
        teamUpgradeScreen.showReplaceUnit(teamEntity);
    }

    /**
     * метод для проверки достаточно ли монет и ресурсов для апргрейда
     **/
    private void checkRecourcesAndCoinsCount() {
        if (coinsCount >= upgradeCost) {
            if (gameManager.getFoodCount() >= foodCostValue &&
                    gameManager.getIronCount() >= ironCostValue &&
                    gameManager.getWoodCount() >= woodCostValue) {
                canBeUpgrade = true;
                upgradeButton.setUpgradeCost(upgradeCost, true);
            } else {
                toastLabel.setText(noResources);
                canBeUpgrade = false;
//                applyActionsToToast();
                upgradeButton.setUpgradeCost(upgradeCost, false);
            }
        } else {
            toastLabel.setText(noCoins);
            canBeUpgrade = false;
//            applyActionsToToast();
            upgradeButton.setUpgradeCost(upgradeCost, false);
        }
    }

    /**
     * метод обновляет значения текста в информации параметрах юнита и об апгрейде
     **/
    private void updateLabelsText() {
        healthValueLabel.setText("" + healthValue);
        damageValueLabel.setText("" + damageValue);
        speedValueLabel.setText("" + speedValue);
        timePrepearValueLabel.setText("" + timePrepearValue);
        healthAddValueLabel.setText(" + " + addHealthValue);
        damageAddValueLabel.setText(" + " + addDamageValue);

//        ScaleToAction sa = new ScaleToAction();
//        sa.setScale(3);
//        sa.setDuration(3);
//
//        healthAddValueLabel.addAction(sa);
//
//        System.out.println("scale");

//        Color color = new Color(Color.WHITE);
//        ColorAction ca = new ColorAction();
//        ca.setColor(color);
//        ca.setDuration(2f);
//        ca.setEndColor(Color.RED);
//        healthAddValueLabel.addAction(ca);


    }


    /**
     * метод для изменения значений данных (ресурсов, количества монет, характеристик юнита)
     * после нажатия кнопки upgrade
     **/

    private void upgradeTeamEntity(TeamEntity teamEntity) {

        unitImage.startAction();

        /** обновим параметры юнита, которого прокачиваем **/
        teamEntity.setUnitLevel(newUnitLevel);
        teamEntity.addHEALTH(addHealthValue);
        teamEntity.addDAMAGE(addDamageValue);

        /** обновим количество монет и установим новое кол-во монет в панели монет **/
        coinsCount -= upgradeCost;
        gameManager.getCoinsPanel().setCoinsCount(coinsCount);

        /** вычтем количество ресурсов потраченных на апгрейд **/
        gameManager.addFoodCount(-foodCostValue);
        gameManager.addIronCount(-ironCostValue);
        gameManager.addWoodCount(-woodCostValue);

        /** обновим значения в таблице ресурсов **/
        resourcesTable.updateResources(gameManager.getFoodCount(), gameManager.getIronCount(), gameManager.getWoodCount());


        /**
         * метод устанавливает значения параметров для улучшения юнита и его изображение
         **/
        setUnitUpgradeData(teamEntity);

//        /** обновим значение текста следующего уровня апгрейда **/
//        upgradeToLevelLabel.setText(upgradeToLevelText + newUnitLevel);

    }

    /**
     * метод для старта action для сообщения о недостаточном количестве монет или ресурсов
     **/
    private void applyActionsToToast() {
        /** isStartToastAction - флаг, т.е. если уже показывается сообщение, повторно чтобы не появлялось, пока есть на экране **/
        if (!isStartToastAction) {
            isStartToastAction = true;

            toastLabel.setVisible(true);
            toastLabel.setPosition(Warfare.V_WIDTH / 2 - 200, Warfare.V_HEIGHT / 2);

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

            mta.setPosition(Warfare.V_WIDTH / 2 - 200, Warfare.V_HEIGHT / 2 + 100);
            mta.setDuration(1f);

            AlphaAction alphaActionEnd = new AlphaAction();
            alphaActionEnd.setAlpha(0);
            alphaActionEnd.setDuration(1f);

            SequenceAction sa = new SequenceAction(alphaActionStart, mta, alphaActionEnd, checkEndOfAction);

            toastLabel.addAction(sa);
        }
    }


}

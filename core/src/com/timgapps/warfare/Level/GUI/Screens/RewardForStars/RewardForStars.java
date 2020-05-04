package com.timgapps.warfare.Level.GUI.Screens.RewardForStars;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.timgapps.warfare.Level.GUI.Screens.TeamEntity;
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
    private RewardForStarsScreen rewardForStarsScreen;

    public RewardForStars(final RewardForStarsScreen rewardForStarsScreen, final RewardForStarsData data, GameManager gameManager) {
        this.rewardForStarsScreen = rewardForStarsScreen;

        this.gameManager = gameManager;
        this.data = data;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        if (data.getIsReceived()) {
            labelStyle.fontColor = Color.LIGHT_GRAY;
        } else {
            labelStyle.fontColor = Color.WHITE;
        }
        labelStyle.font = Warfare.font20;


        switch (data.getTypeOfReward()) {
            case RewardForStarsData.REWARD_STONE:
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
                rewardImage = new Image(Warfare.atlas.findRegion("gnomeStay0"));
                name = "Box";
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
        System.out.println("nameLabelWidth = " + nameLabel.getWidth());

        addActor(rewardImage);                      // добавим изображение
        addActor(nameLabel);
        addActor(receivedImg);

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
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (bgYellow.isVisible()) {
                    setReceived();
                }
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if ((!data.getIsReceived()) && (data.getIsChecked())) {
                    getRewardForStars();
                }

                if (!data.getIsChecked()) {
                    rewardForStarsScreen.showToast(data.getStarsCount());
                }
            }
        });
    }

    public Image getRewardImage() {
        return rewardImage;
    }

    /**
     * метод для получения награды за звезды при клике на награду
     **/
    public void getRewardForStars() {
        nameLabel.setColor(Color.LIGHT_GRAY);
        int index;
        switch (data.getTypeOfReward()) {
            case RewardForStarsData.REWARD_STONE:                           // если наград "КАМЕНЬ"
                for (int i = 0; i < gameManager.getCollection().size(); i++) {
                    if (gameManager.getCollection().get(i).getUnitType() == TeamEntity.STONE) {
                        addRewardUnitToTeam(i);
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
        }

//         сохраним данные
        data.setReceived();                                  // награда получена
        gameManager.getStarsPanel().updateCountReward();
//        gameManager.saveGame();


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

package com.timgapps.warfare.Level.GUI.Screens.RewardForStars;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.timgapps.warfare.Warfare;

public class RewardForStars extends Group {
    private Image rewardImage;
    private Image bg;
    private Image check;
    private int typeOfReward;
    private Label nameLabel;
    private String name;
    public RewardForStars(RewardForStarsData data) {

        Label.LabelStyle labelStyle = new Label.LabelStyle();
//        labelStyle.fontColor = Color.LIGHT_GRAY;
        labelStyle.fontColor = Color.WHITE;
        labelStyle.font = Warfare.font20;


        switch (data.getTypeOfReward()) {
            case RewardForStarsData.REWARD_STONE:
                rewardImage = new Image(Warfare.atlas.findRegion("block1"));
                name = "Rock";
                break;
            case RewardForStarsData.REWARD_ARCHER:
                rewardImage = new Image(Warfare.atlas.findRegion("archer1Stay0"));
                name = "Archer";
                break;
             case RewardForStarsData.REWARD_BOX:
                rewardImage = new Image(Warfare.atlas.findRegion("gnomeStay0"));
                 name = "Gnome";
                break;
        }
        bg = new Image(Warfare.atlas.findRegion("coinsPanel"));
        rewardImage.debug();

        setSize(bg.getWidth(), bg.getHeight());  // зададим размер группы
        addActor(bg);                             // добавим фон для группы (прямоугольник)
        rewardImage.setPosition((bg.getWidth() - rewardImage.getWidth()) / 2, 36);

        nameLabel = new Label("" + name, labelStyle);
        nameLabel.setPosition((bg.getWidth() - nameLabel.getWidth()) / 2, 0);
        System.out.println("nameLabelWidth = " + nameLabel.getWidth());

        addActor(rewardImage);                      // добавим изображение
        addActor(nameLabel);
    }
}

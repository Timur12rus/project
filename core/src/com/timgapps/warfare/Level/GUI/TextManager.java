package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class TextManager {
    public BitmapFont fontEnergy; // отображаем текст на экране через эту переменную
    public BitmapFont fontCoin; // отображаем текст на экране через эту переменную
    //    private Level level;
    private GlyphLayout glyphLayout;

    public TextManager(Level level) {
        fontEnergy = new BitmapFont();
        fontCoin = new BitmapFont();
        fontEnergy.setColor(Color.BLUE);
        fontCoin.setColor(Color.YELLOW);

        fontEnergy.getData().setScale(1.6f, 2.2f);
        fontCoin.getData().setScale(1.6f, 2.2f);
        glyphLayout = new GlyphLayout();
    }

    public void displayMessage(Batch batch, int energyCount, int coinCount) {

        // объект класса GlyphLayout хранит в себе информацию о шрифте и содержании текста

        glyphLayout.setText(fontEnergy, "" + energyCount);

        // отображаем результат в левом верхнем углу
//        fontEnergy.setColor(Color.BLUE);
        fontEnergy.draw(batch, glyphLayout, 84, Warfare.V_HEIGHT - 32);

        // отображаем результат в правом верхнем углу
//        fontEnergy.setColor(Color.YELLOW);
        glyphLayout.setText(fontCoin, "" + coinCount);
        fontCoin.draw(batch, glyphLayout, Warfare.V_WIDTH - 128, Warfare.V_HEIGHT - 32);
    }

    public void dispose() {
        fontEnergy.dispose();
        fontCoin.dispose();
    }


}

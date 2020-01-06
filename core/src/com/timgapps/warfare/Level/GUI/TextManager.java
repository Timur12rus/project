package com.timgapps.warfare.Level.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.timgapps.warfare.Level.Level;
import com.timgapps.warfare.Warfare;

public class TextManager {
    static BitmapFont font; // отображаем текст на экране через эту переменную
//    private Level level;

    public TextManager(Level level) {
        font = new BitmapFont();
        font.setColor(Color.BLUE);
        font.getData().setScale(1.6f, 2.2f);
    }

    public static void displayMessage(Batch batch, int energyCount) {

        // объект класса GlyphLayout хранит в себе информацию о шрифте и содержании текста
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "" + energyCount);

        // отображаем результат в правом верхнем углу
        font.draw(batch, glyphLayout, 84, Warfare.V_HEIGHT - 32);
    }


}

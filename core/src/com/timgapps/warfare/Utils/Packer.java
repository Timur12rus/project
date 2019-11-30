package com.timgapps.warfare.Utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class Packer {
    public static void main(String[] args) {
        TexturePacker.Settings set = new TexturePacker.Settings();
        set.filterMin = Texture.TextureFilter.MipMapLinearNearest;
        set.filterMag = Texture.TextureFilter.Linear;
        set.paddingX = 2;
        set.paddingY = 2;
        set.maxHeight = 2048;
        set.maxWidth = 2048;

        // Создаем сам атлас текстур (метод process класса TexturePacker с параметрами set(настройки), путь к папке с исходными изображениями
        // и путь к папке с результирующим атласом текстур, и имя атласа
        TexturePacker.process(set, "raw_images", "android/assets/images", "pack");
//        TexturePacker.process(set, "raw_images_en", "android/assets/images_en", "pack");



    }

}
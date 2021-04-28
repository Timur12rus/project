package com.timgapps.warfare.Utils;

import java.util.HashMap;
import java.util.Locale;

// обьект для хранения текстовых строк, выбора строк в зависимости от локали
public class StringHolder {
    private Lang lang;
    public static final String LOADING = "loading";
    public static final String TEAM = "team";
    public static final String COLLECTION = "collection";
    public static final String THOR = "thor";
    public static final String BARBARIAN = "barbarian";
    public static final String STONE = "stone";
    public static final String ARCHER = "archer";
    public static final String GNOME = "gnome";
    public static final String KNIGHT = "knight";
    public static final String VIKING = "viking";
    public static final String SHOOTER = "shooter";
    public static final String HEALTH = "health";
    public static final String DAMAGE = "damage";
    public static final String SPEED = "speed";
    public static final String TIME_TO_APPEARANCE = "time of appearance";
    public static final String UPGRADE_COST = "upgrade cost";
    public static final String UPGRADE_TO_LEVEL = "upgrade to level";
    public static final String NOT_ENOUGHTH_COINS = "not enoughth coins";
    public static final String NOT_ENOUGHTH_RESOURCES = "not enoughth resources";
    public static final String COLLECT = "collect";
    public static final String FOR_UNLOCK_UNIT = "for unlock unit";
    public static final String REWARD_FOR_STARS = "reward for stars";
    public static final String COLLECT_STARS_TO_GET_REWARD = "collect stars to get reward";
    public static final String BACK = "back";
    public static final String NEXT_REWARD = "next reward";
    public static final String MISSION = "mission";
    public static final String MEDIUM = "medium";
    public static final String EASY = "easy";
    public static final String HARD = "hard";
    public static final String REWARD = "reward";
    public static final String START = "start";
    public static final String PAUSE = "pause";
    public static final String RETRY = "retry";
    public static final String VICTORY = "victory";
    public static final String TOWER_SAVED = "tower saved";
    public static final String MAP = "map";
    public static final String CONTINUE = "continue";
    public static final String DEFEAT = "defeat";
    public static final String DAILY_GIFTS = "daily gifts";
    public static final String CLAIM = "claim";
    public static final String GIFTS = "gifts";
    public static final String FIRESTONE = "firestone";
    public static final String HIRE_THIS_UNIT = "hire this unit";

    private HashMap<String, String> strings;

    public StringHolder(Locale locale) {
//        this.lang = lang;
//        createStrings("ru_RU");
        strings = new HashMap<String, String>();
        createStrings(locale);
    }

    // возвращает строку по имени
    public String getString(String name) {
        return strings.get(name);
    }

    public void createStrings(Locale locale) {
        lang = Lang.en;
        for (Lang language : Lang.values()) {
            System.out.println("Local = " + locale.toString());
            System.out.println("Language = " + language);
            if (locale.toString().contains(language.name())) {
                lang = language;
                System.out.println("!!!!!!lang = " + language);
                break;
            }
        }

        switch (lang) {
            case en:
                strings.put(LOADING, "loading...");
                strings.put(TEAM, "team");
                strings.put(COLLECTION, "collection");
                strings.put(THOR, "thor");
                strings.put(BARBARIAN, "barbarian");
                strings.put(STONE, "stone");
                strings.put(ARCHER, "archer");
                strings.put(GNOME, "gnome");
                strings.put(KNIGHT, "knight");
                strings.put(VIKING, "viking");
                strings.put(SHOOTER, "shooter");
                strings.put(HEALTH, "health");
                strings.put(DAMAGE, "damage");
                strings.put(SPEED, "speed");
                strings.put(TIME_TO_APPEARANCE, "time of appearance");
                strings.put(UPGRADE_COST, "upgrade cost");
                strings.put(UPGRADE_TO_LEVEL, "upgrade to level");
                strings.put(NOT_ENOUGHTH_COINS, "not enoughth coins!");
                strings.put(NOT_ENOUGHTH_RESOURCES, "not enoughth resources!");
                strings.put(COLLECT, "collect");
                strings.put(FOR_UNLOCK_UNIT, "for unlock unit");
                strings.put(REWARD_FOR_STARS, "reward for stars");
                strings.put(COLLECT_STARS_TO_GET_REWARD, "collect stars to get reward");
                strings.put(BACK, "back");
                strings.put(NEXT_REWARD, "next reward");
                strings.put(MISSION, "mission");
                strings.put(MEDIUM, "medium");
                strings.put(EASY, "easy");
                strings.put(HARD, "hard");
                strings.put(REWARD, "reward");
                strings.put(START, "start");
                strings.put(PAUSE, "pause");
                strings.put(RETRY, "retry");
                strings.put(VICTORY, "victory");
                strings.put(TOWER_SAVED, "tower saved");
                strings.put(MAP, "map");
                strings.put(CONTINUE, "continue");
                strings.put(DEFEAT, "defeat");
                strings.put(DAILY_GIFTS, "daily gifts");
                strings.put(CLAIM, "claim");
                strings.put(GIFTS, "gifts");
                strings.put(FIRESTONE, "firestone");
                strings.put(HIRE_THIS_UNIT, "hire this unit:");
                break;
            case ru:
                strings.put(LOADING, "загрузка...");
                strings.put(TEAM, "команда");
                strings.put(COLLECTION, "коллекция");
                strings.put(THOR, "тор");
                strings.put(BARBARIAN, "варвар");
                strings.put(STONE, "камень");
                strings.put(ARCHER, "лучник");
                strings.put(GNOME, "гном");
                strings.put(KNIGHT, "рыцарь");
                strings.put(VIKING, "викинг");
                strings.put(SHOOTER, "стрелок");
                strings.put(HEALTH, "здоровье");
                strings.put(DAMAGE, "урон");
                strings.put(SPEED, "скорость");
                strings.put(TIME_TO_APPEARANCE, "время появлен.");
                strings.put(UPGRADE_COST, "стоимость улучшения");
                strings.put(UPGRADE_TO_LEVEL, "улучшить до уровня");
                strings.put(NOT_ENOUGHTH_COINS, "не хватает монет!");
                strings.put(NOT_ENOUGHTH_RESOURCES, "не хватает ресурсов!");
                strings.put(COLLECT, "собери");
                strings.put(FOR_UNLOCK_UNIT, "чтобы разблокировать юнита");
                strings.put(REWARD_FOR_STARS, "награда за звезды");
                strings.put(COLLECT_STARS_TO_GET_REWARD, "собирай звезды чтобы получить награду");
                strings.put(BACK, "назад");
                strings.put(NEXT_REWARD, "следующая награда");
                strings.put(MISSION, "миссия");
                strings.put(MEDIUM, "нормально");
                strings.put(EASY, "легко");
                strings.put(HARD, "сложно");
                strings.put(REWARD, "награда");
                strings.put(START, "старт");
                strings.put(PAUSE, "пауза");
                strings.put(RETRY, "заново");
                strings.put(VICTORY, "победа");
                strings.put(TOWER_SAVED, "башня сохранена");
                strings.put(MAP, "карта");
                strings.put(CONTINUE, "продолжить");
                strings.put(DEFEAT, "поражение");
                strings.put(DAILY_GIFTS, "ежедневные подарки");
                strings.put(CLAIM, "взять");
                strings.put(GIFTS, "подарки");
                strings.put(FIRESTONE, "огонь");
                strings.put(HIRE_THIS_UNIT, "нанять:");
                break;
        }
    }
}

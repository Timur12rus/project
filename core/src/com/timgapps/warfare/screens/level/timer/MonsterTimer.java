package com.timgapps.warfare.screens.level.timer;

import com.timgapps.warfare.screens.level.LevelScreen;

// таймер до запуска волны монстров
public class MonsterTimer {
    private LevelScreen levelScreen;
    private final float TIME_TO_WAVE = 150;     // время до начала отсчета волны
    //    private final float TIME_TO_WAVE = 150;     // время до начала отсчета волны
    private TimerIcon timerIcon;            // счетчик для запуска анимации значака "волны монстров"
    private float time;         // время, которое прошло с момента начала уровня
    private boolean waveIsStarted;

    public MonsterTimer(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
        timerIcon = new TimerIcon(levelScreen);
    }

    public void update(float delta) {
        if (!waveIsStarted) {
            time += delta;
            timerIcon.update(delta);
            if (time > TIME_TO_WAVE && !timerIcon.isStarted() && !timerIcon.isEnd()) {
                timerIcon.start();
            }
            if (timerIcon.isEnd()) {
                waveIsStarted = true;
                new MonsterWave(levelScreen).start();
                timerIcon.clear();
            }
        }
    }

    public boolean isTimerIconStarted() {
        return timerIcon.isStarted();
    }

    public void hide() {
        waveIsStarted = false;
        timerIcon.clear();
    }
}

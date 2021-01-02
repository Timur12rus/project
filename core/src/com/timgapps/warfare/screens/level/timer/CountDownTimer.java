package com.timgapps.warfare.screens.level.timer;

import com.timgapps.warfare.screens.level.LevelScreen;

// счетчик для отсета времени до начала "волны моснтров"
public class CountDownTimer {
    private float count;
    private LevelScreen levelScreen;
    private boolean waveIsStarted;
    private final float TIME_TO_WAVE = 150;     // время до начала отсчета волны
    private TimerIcon timerIcon;            // счетчик для запуска анимации значака "волны монстров"

    public CountDownTimer(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
        timerIcon = new TimerIcon(levelScreen);
    }

    public void update(float delta) {
        count += delta;
        System.out.println("count = " + count);
        if (count > TIME_TO_WAVE && !timerIcon.isStarted()) {
            timerIcon.start();
            count = 0;
//            waveIsStarted = true;
//            new MonsterWave(levelScreen).start();
        }
        timerIcon.update(count);
        if (timerIcon.isStop() && !waveIsStarted && !levelScreen.isCompleted()) {
            waveIsStarted = true;
            new MonsterWave(levelScreen).start();
        }
    }

    // останавливаем счётчик и убираем с экрана
    public void stop() {
        timerIcon.stop();
    }

    public void reset() {
        count = 0;
        waveIsStarted = false;
        timerIcon.reset();
    }
}

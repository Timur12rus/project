package com.timgapps.warfare.screens.level.timer;

import com.timgapps.warfare.screens.level.LevelScreen;

public class CountDownTimer {
    private float count;
    private LevelScreen levelScreen;
    private boolean waveIsStarted;
    private final float TIME_TO_WAVE = 10;
    private TimerIcon timerIcon;

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
        if (timerIcon.isStop() && !waveIsStarted) {
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

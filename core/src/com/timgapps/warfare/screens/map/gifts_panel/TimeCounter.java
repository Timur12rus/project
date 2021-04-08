package com.timgapps.warfare.screens.map.gifts_panel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCounter {
    private float counter;
    private Date date;
    private SimpleDateFormat formatForDate;

    public TimeCounter() {
        date = new Date();      // получим текущее время
    }

    public void update(float delta) {
        counter++;
    }

    public void reset() {
        counter = 0;
    }

    public void redraw() {

    }

}

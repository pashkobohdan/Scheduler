package com.pashkobohdan.scheduler.library.activityHalper;

import android.view.View;

import com.pashkobohdan.scheduler.library.Event;

/**
 * Created by pashk on 06.05.2016.
 */
public interface MyRun {
    void change(Event event);

    void delete(Event event);

    void refresh(Event event);
}

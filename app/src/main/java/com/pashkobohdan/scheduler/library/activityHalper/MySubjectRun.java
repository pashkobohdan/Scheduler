package com.pashkobohdan.scheduler.library.activityHalper;

import com.pashkobohdan.scheduler.library.Event;
import com.pashkobohdan.scheduler.library.timeWorker.Subject;

/**
 * Created by pashk on 11.05.2016.
 */
public interface MySubjectRun {
    void change(Subject subject);

    void delete(Subject subject);

    void refresh(Subject subject);
}

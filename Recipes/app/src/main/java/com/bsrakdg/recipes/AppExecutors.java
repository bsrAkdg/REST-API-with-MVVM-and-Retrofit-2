package com.bsrakdg.recipes;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    private static AppExecutors instance;

    // newScheduledThreadPool : Creates a thread pool that can schedule commands to run after a
    // given delay, or to execute periodically.
    private final ScheduledExecutorService networkIO = Executors.newScheduledThreadPool(3);

    public static AppExecutors getInstance() {
        if (instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }

    public ScheduledExecutorService getNetworkIO() {
        return networkIO;
    }
}

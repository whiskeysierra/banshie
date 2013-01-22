package org.whiskeysierra.banshie.execution.monitor;

import java.util.concurrent.ThreadFactory;

final class LoggingThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable runnable) {
        final Thread thread = new Thread(runnable);
        thread.setUncaughtExceptionHandler(new LoggingUncaughtExceptionHandler());
        return thread;
    }

}

package org.whiskeysierra.banshie.execution.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Thread.UncaughtExceptionHandler;

final class LoggingUncaughtExceptionHandler implements UncaughtExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingUncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        LOG.error("Uncaught exception in " + thread, throwable);
    }

}

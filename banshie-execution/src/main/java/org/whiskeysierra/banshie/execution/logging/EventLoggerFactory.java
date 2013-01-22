package org.whiskeysierra.banshie.execution.logging;

import java.io.File;

public interface EventLoggerFactory {

    EventLogger newLogger(File logFile);

}

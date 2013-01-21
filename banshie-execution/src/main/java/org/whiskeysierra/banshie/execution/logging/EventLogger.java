package org.whiskeysierra.banshie.execution.logging;

import javax.management.MBeanServerConnection;
import java.io.File;
import java.io.IOException;

public interface EventLogger {

    void start(MBeanServerConnection connection, File logFile) throws IOException;

    void log() throws IOException;

    void finish();
}

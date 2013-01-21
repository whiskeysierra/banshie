package org.whiskeysierra.banshie.execution.monitor;

import java.io.File;
import java.io.IOException;

public interface ProcessMonitor {

    void start(int port, File logFile) throws IOException;

    void stop();

}

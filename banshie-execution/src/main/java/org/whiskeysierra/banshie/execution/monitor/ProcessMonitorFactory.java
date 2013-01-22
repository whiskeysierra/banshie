package org.whiskeysierra.banshie.execution.monitor;

import java.io.File;

public interface ProcessMonitorFactory {

    ProcessMonitor newMonitor(int port, File logFile);

}

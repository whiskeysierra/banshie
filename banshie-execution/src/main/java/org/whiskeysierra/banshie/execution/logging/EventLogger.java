package org.whiskeysierra.banshie.execution.logging;

import javax.management.MBeanServerConnection;
import java.io.IOException;

public interface EventLogger {

    void log(MBeanServerConnection connection) throws IOException;

}

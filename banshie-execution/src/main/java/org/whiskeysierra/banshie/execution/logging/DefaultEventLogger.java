package org.whiskeysierra.banshie.execution.logging;

import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.io.IOException;

final class DefaultEventLogger implements EventLogger {

    @Override
    public void log(MBeanServerConnection connection) throws IOException {
        try {
            tryLog(connection);
        } catch (JMException e) {
            throw new IOException(e);
        }
    }

    private void tryLog(MBeanServerConnection connection) throws JMException, IOException {
        final ObjectName memory = new ObjectName("java.lang:type=Memory");

        final CompositeData memoryUsage = (CompositeData) connection.getAttribute(memory, "HeapMemoryUsage");

        System.out.println("HeamMemoryUsage committed: " + memoryUsage.get("committed"));
    }

}

package org.whiskeysierra.banshie.execution.logging;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.inject.Inject;
import org.whiskeysierra.banshie.execution.io.EventWriter;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;
import java.util.Date;

final class DefaultEventLogger implements EventLogger {

    private final EventWriter writer;

    // TODO make these final and move initialization to constructor
    private MBeanServerConnection connection;
    private MemoryMXBean memory;
    private ThreadMXBean threading;

    @Inject
    DefaultEventLogger(EventWriter writer) {
        this.writer = writer;
    }

    @Override
    public void start(MBeanServerConnection connection, File logFile) throws IOException {
        Preconditions.checkState(this.connection == null, "Already connected");

        writer.start(logFile);

        this.connection = connection;

        this.memory = proxy(ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
        this.threading = proxy(ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
    }

    private <T> T proxy(String name, Class<T> type) throws IOException {
        try {
            return JMX.newMXBeanProxy(connection, new ObjectName(name), type);
        } catch (MalformedObjectNameException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void log() throws IOException {
        Preconditions.checkState(connection != null, "Not connected");

        try {
            log("HeapMemoryUsage/committed", memory.getHeapMemoryUsage().getCommitted());
            log("HeapMemoryUsage/used", memory.getHeapMemoryUsage().getUsed());

            log("NonHeapMemoryUsage/committed", memory.getNonHeapMemoryUsage().getCommitted());
            log("NonHeapMemoryUsage/used", memory.getNonHeapMemoryUsage().getUsed());

            log("CurrentThreadCpuTime", threading.getCurrentThreadCpuTime());
            log("CurrentThreadUserTime", threading.getCurrentThreadUserTime());
            log("ThreadCount", threading.getThreadCount());
        } catch (UndeclaredThrowableException e) {
            if (Throwables.getRootCause(e) instanceof ConnectException) {
                // discard, as the jmx connection might have been closed in the meantime
                // TODO add log statement
            } else {
                throw e;
            }
        }
    }

    private void log(String key, long value) throws IOException {
        final Event event = new Event();

        event.setDate(new Date());
        event.setKey(key);
        event.setValue(value);

        writer.write(event);
    }

    @Override
    public void finish() {
        writer.finish();
    }

}

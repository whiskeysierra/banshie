package org.whiskeysierra.banshie.execution.event;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.whiskeysierra.banshie.execution.logging.EventLogger;
import org.whiskeysierra.banshie.execution.logging.EventLoggerFactory;

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

final class DefaultEventProducer implements EventProducer {

    // TODO make these final and move initialization to constructor
    private final MBeanServerConnection connection;
    private final MemoryMXBean memory;
    private final ThreadMXBean threading;

    private final EventLogger logger;

    @Inject
    DefaultEventProducer(EventLoggerFactory factory,
        @Assisted MBeanServerConnection connection, @Assisted File logFile) throws MalformedObjectNameException {

        this.connection = connection;
        this.memory = proxy(ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
        this.threading = proxy(ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);

        this.logger = factory.newLogger(logFile);
    }

    // TODO make static?
    private <T> T proxy(String name, Class<T> type) throws MalformedObjectNameException {
        return JMX.newMXBeanProxy(connection, new ObjectName(name), type);
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
        final DefaultEvent event = new DefaultEvent();

        event.setDate(new Date());
        event.setKey(key);
        event.setValue(value);

        logger.write(event);
    }

    @Override
    public void stop() {
        logger.stop();
    }

}

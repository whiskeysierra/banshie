package org.whiskeysierra.banshie.execution.event;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.sun.management.OperatingSystemMXBean;
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
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

final class DefaultEventProducer implements EventProducer {

    // TODO make these final and move initialization to constructor
    private final MBeanServerConnection connection;
    private final MemoryMXBean memory;
    private final OperatingSystemMXBean os;
    private final RuntimeMXBean runtime;

    private final EventLogger logger;

    @Inject
    DefaultEventProducer(EventLoggerFactory factory,
        @Assisted MBeanServerConnection connection, @Assisted File logFile) throws MalformedObjectNameException {

        this.connection = connection;
        this.memory = proxy(ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
        this.os = proxy(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
        this.runtime = proxy(ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);

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
            logCpuTime();
            logMemoryUsage();
        } catch (UndeclaredThrowableException e) {
            if (Throwables.getRootCause(e) instanceof ConnectException) {
                // discard, as the jmx connection might have been closed in the meantime
                // TODO add log statement
            } else {
                throw e;
            }
        }
    }

    private void logCpuTime() throws IOException {
        final CpuTimeEvent event = new CpuTimeEvent();

        event.setTime(System.currentTimeMillis());
        event.setValue(os.getProcessCpuTime());
        event.setSystemTime(TimeUnit.MILLISECONDS.toNanos(runtime.getUptime()));
        event.setAvailableProcessors(os.getAvailableProcessors());

        logger.write(event);
    }

    private void logMemoryUsage() throws IOException {
        final MemoryUsageEvent event = new MemoryUsageEvent();

        event.setTime(System.currentTimeMillis());
        final MemoryUsage heap = memory.getHeapMemoryUsage();
        final MemoryUsage nonHeap = memory.getNonHeapMemoryUsage();
        event.setValue(heap.getUsed() + nonHeap.getUsed());

        logger.write(event);
    }

    @Override
    public void stop() {
        logger.stop();
    }

}

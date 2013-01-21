package org.whiskeysierra.banshie.execution.monitor;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import org.whiskeysierra.banshie.execution.logging.EventLogger;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

final class DefaultProcessMonitor implements ProcessMonitor, Runnable {

    private final EventLogger logger;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private JMXConnector connector;

    @Inject
    DefaultProcessMonitor(EventLogger logger) {
        this.logger = logger;
    }

    @Override
    public void start(int port, File logFile) throws IOException {
        Preconditions.checkState(connector == null, "Already connected");

        try {
            // TODO find a better way to wait for the jvm to start?!
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }

        // TODO handle a certain amount of retry errors gracefully
        final String url = "service:jmx:rmi:///jndi/rmi://localhost:" + port + "/jmxrmi";
        final JMXServiceURL serviceUrl = new JMXServiceURL(url);
        connector = JMXConnectorFactory.connect(serviceUrl, null);

        // TODO make configurable
        executor.scheduleAtFixedRate(this, 0L, 100L, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        Preconditions.checkState(connector != null, "Not connected to jmx server");

        try {
            logger.log(connector.getMBeanServerConnection());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void stop() {
        executor.shutdown();

        if (connector == null) return;

        try {
            connector.close();
        } catch (IOException e) {
            // TODO handle (log) or ignore?
        } finally {
            connector = null;
        }
    }

}
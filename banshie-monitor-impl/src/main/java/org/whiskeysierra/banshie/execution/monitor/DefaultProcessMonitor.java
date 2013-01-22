package org.whiskeysierra.banshie.execution.monitor;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.whiskeysierra.banshie.execution.event.EventProducer;
import org.whiskeysierra.banshie.execution.event.EventProducerFactory;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

final class DefaultProcessMonitor implements ProcessMonitor, Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultProcessMonitor.class);

    private final EventProducer producer;
    private final JMXConnector connector;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(
        new LoggingThreadFactory());

    @Inject
    DefaultProcessMonitor(EventProducerFactory factory,
        @Assisted int port, @Assisted File logFile) throws IOException {

        // TODO make configurable
        this.connector = tryConnect(port, 5);
        this.producer = factory.newProducer(connector.getMBeanServerConnection(), logFile);

        // TODO make configurable
        executor.scheduleAtFixedRate(this, 0L, 1L, TimeUnit.SECONDS);
    }

    private static JMXConnector tryConnect(int port, int maxRetries) throws IOException {
        int attempts = 0;

        IOException thrown = null;

        while (attempts++ < maxRetries) {
            // TODO make configurable
            waitFor(1000);

            try {
                final String url = "service:jmx:rmi:///jndi/rmi://localhost:" + port + "/jmxrmi";
                final JMXServiceURL serviceUrl = new JMXServiceURL(url);
                LOG.info("Trying to connect to {}, attempt #{}", url, attempts);
                return JMXConnectorFactory.connect(serviceUrl, null);
            } catch (IOException e) {
                thrown = Objects.firstNonNull(thrown, e);
                continue;
            }
        }

        Preconditions.checkState(thrown != null, "Expected exception to be thrown");
        throw thrown;
    }

    private static void waitFor(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void run() {
        try {
            producer.log();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void stop() {
        executor.shutdown();
        producer.stop();

        try {
            connector.close();
        } catch (IOException e) {
            LOG.debug("Failed to close jmx connector, probably because the process already died", e);
        }
    }

}

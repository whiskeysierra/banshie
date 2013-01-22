package org.whiskeysierra.banshie.execution.monitor;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.whiskeysierra.banshie.execution.event.EventProducer;
import org.whiskeysierra.banshie.execution.event.EventProducerFactory;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

final class DefaultProcessMonitor implements ProcessMonitor, Runnable {

    private final EventProducer producer;
    private final JMXConnector connector;

    // TODO inject
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(
        new ThreadFactory() {

            @Override
            public Thread newThread(Runnable runnable) {
                final Thread thread = new Thread(runnable);

                thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread thread, Throwable throwable) {
                        // TODO log exception
                    }
                });

                return thread;
            }

        });

    @Inject
    DefaultProcessMonitor(EventProducerFactory factory,
        @Assisted int port, @Assisted File logFile) throws IOException {

        try {
            // TODO find a better way to wait for the jvm to start!
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }

        // TODO handle a certain amount of retry errors gracefully
        final String url = "service:jmx:rmi:///jndi/rmi://localhost:" + port + "/jmxrmi";
        final JMXServiceURL serviceUrl = new JMXServiceURL(url);
        this.connector = JMXConnectorFactory.connect(serviceUrl, null);

        final MBeanServerConnection connection = connector.getMBeanServerConnection();
        this.producer = factory.newProducer(connection, logFile);

        // TODO make configurable
        executor.scheduleAtFixedRate(this, 0L, 1L, TimeUnit.SECONDS);
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
        producer.finish();

        try {
            connector.close();
        } catch (IOException e) {
            // TODO handle (log) or ignore?
        }
    }

}

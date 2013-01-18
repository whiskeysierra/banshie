package org.whiskeysierra.banshie.execution;

import com.google.common.io.Files;
import org.whiskeysierra.banshie.corpora.Corpus;
import org.whiskeysierra.banshie.execution.process.DefaultProcessService;
import org.whiskeysierra.banshie.execution.process.ManagedProcess;
import org.whiskeysierra.banshie.execution.process.ProcessService;
import org.whiskeysierra.banshie.execution.process.RunningProcess;
import org.whiskeysierra.banshie.extractors.Extractor;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

final class DefaultEngine implements Engine {

    private final ProcessService service = new DefaultProcessService();

    private File basePath = new File("extractors");

    public void setBasePath(File basePath) {
        this.basePath = basePath;
    }

    @Override
    public ExecutionResult execute(Extractor extractor, Corpus corpus) {
        final UUID uuid = UUID.randomUUID();

        final File workingDirectory = new File(basePath, uuid.toString());

        workingDirectory.mkdirs();

        final File logFile = new File(workingDirectory, "events.log");
        final File output = new File(workingDirectory, "stdout.txt");

        // TODO generate randomPort or keep track of ports in use?!
        final int port = 9600;

        // TODO allow extractors to define specific jvm options?
        final ManagedProcess managed = service.prepare(
            "java",
            "-Dcom.sun.management.jmxremote",
            "-Dcom.sun.management.jmxremote.port=" + port,
            "-Dcom.sun.management.jmxremote.authenticate=false",
            "-Dcom.sun.management.jmxremote.ssl=false",
            "-jar", extractor.getPath()
        );

        try {
            final RunningProcess process = managed.call();

            try {
                // TODO find a better way to wait for the jvm to start?!
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }

            // TODO do that in a loop
            monitor(port);

            // TODO measure time of input
            // copy corpus input to stdin
            Files.copy(corpus.getInput(), process);

            // TODO measure time of first output after input was given
            // dump stdout to output file
            Files.copy(process, output);

            process.await();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return new DefaultExecutionResult(uuid, logFile, output);
    }

    private void monitor(int port) throws IOException {
        final String url = "service:jmx:rmi:///jndi/rmi://localhost:" + port + "/jmxrmi";
        final JMXServiceURL serviceUrl = new JMXServiceURL(url);
        final JMXConnector connector = JMXConnectorFactory.connect(serviceUrl, null);

        try {
            final MBeanServerConnection connection = connector.getMBeanServerConnection();

            // TODO collect log event (i.e. cpu time, memory usage) via jmx until process exits
            final Set<ObjectName> names = connection.queryNames(null, null);

            for (ObjectName name : names) {
                System.out.println(name.getCanonicalName());
            }

        } finally {
            connector.close();
        }
    }

}

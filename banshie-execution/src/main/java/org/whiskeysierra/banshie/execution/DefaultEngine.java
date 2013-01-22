package org.whiskeysierra.banshie.execution;

import com.google.common.io.Files;
import com.google.inject.Inject;
import org.ops4j.peaberry.activation.Start;
import org.whiskeysierra.banshie.corpora.Corpus;
import org.whiskeysierra.banshie.execution.monitor.ProcessMonitor;
import org.whiskeysierra.banshie.execution.monitor.ProcessMonitorFactory;
import org.whiskeysierra.banshie.execution.process.ManagedProcess;
import org.whiskeysierra.banshie.execution.process.ProcessService;
import org.whiskeysierra.banshie.execution.process.RunningProcess;
import org.whiskeysierra.banshie.extractors.Extractor;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

final class DefaultEngine implements Engine {

    private final ProcessService service;
    private final ProcessMonitorFactory factory;

    private File basePath = new File("extractors");

    @Inject
    DefaultEngine(ProcessService service, ProcessMonitorFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Start
    public void onStart() {
        basePath.mkdirs();
    }

    @Override
    public ExecutionResult execute(Extractor extractor, Corpus corpus) {
        final UUID uuid = UUID.randomUUID();

        final File directory = new File(basePath, uuid.toString());

        directory.mkdirs();

        final File input = corpus.getInput();
        final File output = new File(directory, "output.txt");
        final File logFile = new File(directory, "events.log");

        // TODO generate random port or keep track of ports in use?!
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

            final ProcessMonitor monitor = factory.newMonitor(port, logFile);

            try {
                Files.copy(input, process);
                Files.copy(process, output);

                process.await();
            } finally {
                monitor.stop();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return new DefaultExecutionResult(uuid, logFile, output);
    }

}

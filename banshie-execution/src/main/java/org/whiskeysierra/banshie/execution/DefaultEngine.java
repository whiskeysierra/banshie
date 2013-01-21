package org.whiskeysierra.banshie.execution;

import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.whiskeysierra.banshie.corpora.Corpus;
import org.whiskeysierra.banshie.execution.monitor.ProcessMonitor;
import org.whiskeysierra.banshie.execution.process.ManagedProcess;
import org.whiskeysierra.banshie.execution.process.ProcessService;
import org.whiskeysierra.banshie.execution.process.RunningProcess;
import org.whiskeysierra.banshie.extractors.Extractor;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

final class DefaultEngine implements Engine {

    private final ProcessService service;
    private final Provider<ProcessMonitor> provider;

    private File basePath = new File("extractors");

    @Inject
    DefaultEngine(ProcessService service, Provider<ProcessMonitor> provider) {
        this.service = service;
        this.provider = provider;
    }

    @Override
    public ExecutionResult execute(Extractor extractor, Corpus corpus) {
        final UUID uuid = UUID.randomUUID();

        final File directory = new File(basePath, uuid.toString());

        directory.mkdirs();

        final File input = corpus.getInput();
        final File output = new File(directory, "stdout.txt");
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

        final ProcessMonitor monitor = provider.get();

        try {
            final RunningProcess process = managed.call();

            monitor.start(port, logFile);

            Files.copy(input, process);
            Files.copy(process, output);

            process.await();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            monitor.stop();
        }

        return new DefaultExecutionResult(uuid, logFile, output);
    }

}

package org.whiskeysierra.banshie.execution;

import com.google.common.io.Files;
import org.whiskeysierra.banshie.corpora.Corpus;
import org.whiskeysierra.banshie.execution.process.DefaultProcessService;
import org.whiskeysierra.banshie.execution.process.ManagedProcess;
import org.whiskeysierra.banshie.execution.process.ProcessService;
import org.whiskeysierra.banshie.execution.process.RunningProcess;
import org.whiskeysierra.banshie.extractors.Extractor;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

final class DefaultEngine implements Engine {

    private final ProcessService service = new DefaultProcessService();

    @Override
    public ExecutionResult execute(Extractor extractor, Corpus corpus) {
        final UUID uuid = UUID.randomUUID();

        // TODO define working directory (maybe based on the uuid?)
        final File logFile = new File("");
        final File output = new File("");

        // TODO call jmx-aware java environment
        final ManagedProcess managed = service.prepare("java", "-jar", extractor.getPath());

        try {
            final RunningProcess process = managed.call();

            // copy corpus input to stdin
            Files.copy(corpus.getInput(), process);

            // dump stdout to output file
            Files.copy(process, output);

            // TODO collect log event (i.e. cpu time, memory usage) via jmx until process exits

            process.await();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return new DefaultExecutionResult(uuid, logFile, output);
    }

}

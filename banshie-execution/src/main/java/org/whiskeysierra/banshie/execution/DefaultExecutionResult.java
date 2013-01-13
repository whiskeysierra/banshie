package org.whiskeysierra.banshie.execution;

import java.io.File;
import java.util.UUID;

final class DefaultExecutionResult implements ExecutionResult {

    private final UUID uuid;
    private final File logFile;
    private final File output;

    DefaultExecutionResult(UUID uuid, File logFile, File output) {
        this.uuid = uuid;
        this.logFile = logFile;
        this.output = output;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public File getLogFile() {
        return logFile;
    }

    @Override
    public File getOutput() {
        return output;
    }

}

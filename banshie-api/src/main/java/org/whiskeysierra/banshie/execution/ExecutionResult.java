package org.whiskeysierra.banshie.execution;

import java.io.File;
import java.util.UUID;

public interface ExecutionResult {

    UUID getUuid();

    File getLogFile();

    File getOutput();

}

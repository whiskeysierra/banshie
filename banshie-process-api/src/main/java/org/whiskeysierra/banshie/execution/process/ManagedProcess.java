package org.whiskeysierra.banshie.execution.process;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface ManagedProcess {

    ManagedProcess in(File directory);

    ManagedProcess with(String variable, String value);

    ManagedProcess with(Map<String, String> properties);

    RunningProcess call() throws IOException;
}

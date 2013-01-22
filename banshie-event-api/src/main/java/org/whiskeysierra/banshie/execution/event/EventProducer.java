package org.whiskeysierra.banshie.execution.event;

import java.io.IOException;

public interface EventProducer {

    void log() throws IOException;

    void stop();
}

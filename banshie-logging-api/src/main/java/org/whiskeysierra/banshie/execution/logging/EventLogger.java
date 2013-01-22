package org.whiskeysierra.banshie.execution.logging;

import org.whiskeysierra.banshie.execution.event.Event;

import java.io.IOException;

public interface EventLogger {

    void write(Event event) throws IOException;

    void finish();

}

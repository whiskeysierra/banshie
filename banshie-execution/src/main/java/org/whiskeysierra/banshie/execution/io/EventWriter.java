package org.whiskeysierra.banshie.execution.io;

import org.whiskeysierra.banshie.execution.logging.Event;

import java.io.File;
import java.io.IOException;

public interface EventWriter {

    void start(File output) throws IOException;

    void write(Event event) throws IOException;

    void finish();

}

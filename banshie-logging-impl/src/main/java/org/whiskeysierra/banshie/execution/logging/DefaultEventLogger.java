package org.whiskeysierra.banshie.execution.logging;

import com.google.common.base.Charsets;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.codehaus.jackson.map.ObjectMapper;
import org.whiskeysierra.banshie.execution.event.Event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

final class DefaultEventLogger implements EventLogger {

    private final ObjectMapper mapper;
    private final BufferedWriter writer;

    @Inject
    DefaultEventLogger(ObjectMapper mapper, @Assisted File output) throws FileNotFoundException {
        this.mapper = mapper;
        this.writer = Files.newWriter(output, Charsets.UTF_8);
    }

    @Override
    public void write(Event event) throws IOException {
        writer.append(mapper.writeValueAsString(event) + "\n");
    }

    @Override
    public void stop() {
        Closeables.closeQuietly(writer);
    }

}

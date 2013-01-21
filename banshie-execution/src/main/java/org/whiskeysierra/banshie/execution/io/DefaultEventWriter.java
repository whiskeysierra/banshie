package org.whiskeysierra.banshie.execution.io;

import com.google.common.base.Charsets;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.gson.Gson;
import org.whiskeysierra.banshie.execution.logging.Event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

final class DefaultEventWriter implements EventWriter {

    private final Gson gson = new Gson();
    private BufferedWriter writer;

    @Override
    public void start(File output) throws IOException {
        writer = Files.newWriter(output, Charsets.UTF_8);
    }

    @Override
    public void write(Event event) throws IOException {
        writer.append(gson.toJson(event) + "\n");
    }

    @Override
    public void finish() {
        Closeables.closeQuietly(writer);
        writer = null;
    }

}

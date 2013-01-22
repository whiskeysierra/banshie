package org.whiskeysierra.banshie.evaluation;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import com.google.gson.Gson;
import com.google.inject.Inject;
import org.whiskeysierra.banshie.execution.event.Event;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

final class StatefulPerformanceEvaluator implements PerformanceEvaluator, LineProcessor<Object> {

    private final Gson gson;
    private final Map<Dimension, Processor> processors;

    @Inject
    StatefulPerformanceEvaluator(Gson gson, Map<Dimension, Processor> processors) {
        this.gson = gson;
        this.processors = processors;
    }

    @Override
    public Map<Dimension, Value> evaluate(File logFile) {
        final Map<Dimension, Value> results = Maps.newHashMap();

        try {
            Files.readLines(logFile, Charsets.UTF_8, this);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        for (Entry<Dimension, Processor> entry : processors.entrySet()) {
            results.put(entry.getKey(), entry.getValue().getResult());
        }

        return results;
    }

    @Override
    public boolean processLine(String line) throws IOException {
        final Event event = gson.fromJson(line, Event.class);

        for (Processor processor : processors.values()) {
            processor.process(event);
        }

        return true;
    }

    @Override
    public Object getResult() {
        // ignored
        return null;
    }

}

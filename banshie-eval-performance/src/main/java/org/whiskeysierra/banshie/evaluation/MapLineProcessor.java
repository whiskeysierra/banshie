package org.whiskeysierra.banshie.evaluation;

import com.google.common.collect.Maps;
import com.google.common.io.LineProcessor;
import com.google.inject.Inject;
import org.codehaus.jackson.map.ObjectMapper;
import org.whiskeysierra.banshie.execution.event.Event;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

class MapLineProcessor implements LineProcessor<Map<Dimension, Value>> {

    private final Map<Dimension, Processor> processors;
    private final ObjectMapper mapper;

    @Inject
    public MapLineProcessor(Map<Dimension, Processor> processors, ObjectMapper mapper) {
        this.processors = processors;
        this.mapper = mapper;
    }

    @Override
    public boolean processLine(String line) throws IOException {
        final Event event = mapper.readValue(line, Event.class);

        for (Processor processor : processors.values()) {
            processor.process(event);
        }

        return true;
    }

    @Override
    public Map<Dimension, Value> getResult() {
        final Map<Dimension, Value> results = Maps.newHashMap();

        for (Entry<Dimension, Processor> entry : processors.entrySet()) {
            results.put(entry.getKey(), entry.getValue().getResult());
        }

        return results;
    }

}

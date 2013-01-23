package org.whiskeysierra.banshie.evaluation;

import com.google.common.collect.Maps;
import com.google.common.io.LineProcessor;
import com.google.inject.Inject;
import org.codehaus.jackson.map.ObjectMapper;
import org.whiskeysierra.banshie.execution.event.Event;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

class LogFileProcessor implements LineProcessor<Map<Dimension, Value>> {

    private final Map<Dimension, Calculator> calculators;
    private final ObjectMapper mapper;

    @Inject
    public LogFileProcessor(Map<Dimension, Calculator> calculators, ObjectMapper mapper) {
        this.calculators = calculators;
        this.mapper = mapper;
    }

    @Override
    public boolean processLine(String line) throws IOException {
        final Event event = mapper.readValue(line, Event.class);

        for (Calculator calculator : calculators.values()) {
            calculator.process(event);
        }

        return true;
    }

    @Override
    public Map<Dimension, Value> getResult() {
        final Map<Dimension, Value> results = Maps.newHashMap();

        for (Entry<Dimension, Calculator> entry : calculators.entrySet()) {
            results.put(entry.getKey(), entry.getValue().getResult());
        }

        return results;
    }

}

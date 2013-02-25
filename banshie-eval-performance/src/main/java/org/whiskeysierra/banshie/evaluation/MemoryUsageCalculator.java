package org.whiskeysierra.banshie.evaluation;

import org.whiskeysierra.banshie.execution.event.Event;
import org.whiskeysierra.banshie.execution.event.MemoryUsageEvent;

/**
 * Calculates the average memory usage in megabytes.
 */
final class MemoryUsageCalculator implements Calculator {

    private long used;
    private long count;

    @Override
    public void process(Event e) {
        if (e instanceof MemoryUsageEvent) {
            final MemoryUsageEvent event = MemoryUsageEvent.class.cast(e);

            used += event.getValue() / 1024L / 1024L;
            count++;
        }
    }

    @Override
    public Value getResult() {
        return new SimpleValue(used / count);
    }

}

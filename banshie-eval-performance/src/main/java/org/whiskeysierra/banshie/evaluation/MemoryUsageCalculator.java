package org.whiskeysierra.banshie.evaluation;

import org.whiskeysierra.banshie.execution.event.Event;
import org.whiskeysierra.banshie.execution.event.MemoryUsageEvent;

final class MemoryUsageCalculator implements Calculator {

    private long used;
    private long count;

    @Override
    public void process(Event e) {
        if (e instanceof MemoryUsageEvent) {
            final MemoryUsageEvent event = MemoryUsageEvent.class.cast(e);

            used += event.getValue();
            count++;
        }
    }

    @Override
    public Value getResult() {
        return new SimpleValue(used / count);
    }

}

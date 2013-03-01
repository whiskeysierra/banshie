package org.whiskeysierra.banshie.evaluation;

import org.whiskeysierra.banshie.execution.event.CpuTimeEvent;
import org.whiskeysierra.banshie.execution.event.Event;

import java.util.concurrent.TimeUnit;

/**
 * Calculates the maximum cpu time in milliseconds.
 */
final class CpuUsageCalculator implements Calculator {

    private long time = Long.MIN_VALUE;

    @Override
    public void process(Event e) {
        if (e instanceof CpuTimeEvent) {
            final CpuTimeEvent event = CpuTimeEvent.class.cast(e);

            this.time = Math.max(time, event.getValue());
        }
    }

    @Override
    public Value getResult() {
        return new SimpleValue(TimeUnit.NANOSECONDS.toMillis(time));
    }

}

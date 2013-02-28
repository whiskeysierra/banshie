package org.whiskeysierra.banshie.evaluation;

import org.whiskeysierra.banshie.execution.event.CpuTimeEvent;
import org.whiskeysierra.banshie.execution.event.Event;

/**
 * Calculates the average cpu time as a percentage, expressed as a double between 0.0 and 1.0.
 */
final class CpuUsageCalculator implements Calculator {

    private int availableProcessors;

    private long lastCpuTime;
    private long lastSystemTime;

    private double usages;
    private long count;

    @Override
    public void process(Event e) {
        if (e instanceof CpuTimeEvent) {
            final CpuTimeEvent event = CpuTimeEvent.class.cast(e);

            this.availableProcessors = Math.max(availableProcessors, event.getAvailableProcessors());

            if (lastCpuTime == 0L) {
                this.lastCpuTime = event.getValue();
                this.lastSystemTime = event.getSystemTime();
            } else {
                final double usage = (double) (event.getValue() - lastCpuTime) / (event.getSystemTime() - lastSystemTime);

                this.usages += usage / availableProcessors;
                this.count++;
                this.lastCpuTime = event.getValue();
                this.lastSystemTime = event.getSystemTime();
            }
        }
    }

    @Override
    public Value getResult() {
        return new SimpleValue(Math.min(usages / count, 100d));
    }

}

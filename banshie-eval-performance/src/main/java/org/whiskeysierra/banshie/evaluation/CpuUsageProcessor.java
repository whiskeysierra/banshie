package org.whiskeysierra.banshie.evaluation;

import org.whiskeysierra.banshie.execution.event.CpuTimeEvent;
import org.whiskeysierra.banshie.execution.event.Event;

public final class CpuUsageProcessor implements Processor {

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
                final double normalized = usage / availableProcessors;


                this.usages += normalized;
                this.count++;
                this.lastCpuTime = event.getValue();
                this.lastSystemTime = event.getSystemTime();
            }
        }
    }

    @Override
    public Value getResult() {
        return new SimpleValue(usages / count);
    }

}

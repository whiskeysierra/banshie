package org.whiskeysierra.banshie.evaluation;

import org.whiskeysierra.banshie.execution.event.Event;

final class TimeCalculator implements Calculator {

    private long first = Long.MAX_VALUE;
    private long last = Long.MIN_VALUE;

    @Override
    public void process(Event event) {
        this.first = Math.min(first, event.getTime());
        this.last = Math.max(last, event.getTime());
    }

    @Override
    public Value getResult() {
        return new SimpleValue(last - first);
    }

}

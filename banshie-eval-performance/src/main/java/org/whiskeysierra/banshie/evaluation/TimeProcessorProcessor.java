package org.whiskeysierra.banshie.evaluation;

import com.google.common.base.Objects;
import org.whiskeysierra.banshie.execution.event.Event;

import java.util.Date;

public final class TimeProcessorProcessor implements Processor {

    private Date first;
    private Date last;

    @Override
    public void process(Event event) {
        this.first = Objects.firstNonNull(first, event.getDate());
        this.last = Objects.firstNonNull(event.getDate(), last);
    }

    @Override
    public Value getResult() {
        return new SimpleValue(last.getTime() - first.getTime());
    }

}

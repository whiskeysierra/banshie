package org.whiskeysierra.banshie.evaluation;

import org.whiskeysierra.banshie.execution.event.Event;

interface Processor {

    void process(Event event);

    Value getResult();

}

package org.whiskeysierra.banshie.evaluation;

import org.whiskeysierra.banshie.execution.event.Event;

interface Calculator {

    void process(Event event);

    Value getResult();

}

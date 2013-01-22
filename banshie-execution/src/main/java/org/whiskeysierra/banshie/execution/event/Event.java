package org.whiskeysierra.banshie.execution.event;

import java.util.Date;

public interface Event {

    Date getDate();

    String getKey();

    long getValue();

}

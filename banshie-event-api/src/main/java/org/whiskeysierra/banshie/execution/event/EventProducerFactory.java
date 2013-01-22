package org.whiskeysierra.banshie.execution.event;

import javax.management.MBeanServerConnection;
import java.io.File;

public interface EventProducerFactory {

    EventProducer newProducer(MBeanServerConnection connection, File logFile);

}

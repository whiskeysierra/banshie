package org.whiskeysierra.banshie.execution.event;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public final class EventModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().
            implement(EventProducer.class, DefaultEventProducer.class).
            build(EventProducerFactory.class));
    }

}

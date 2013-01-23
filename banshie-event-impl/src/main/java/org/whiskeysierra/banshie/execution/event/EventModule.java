package org.whiskeysierra.banshie.execution.event;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.whiskeysierra.banshie.execution.logging.EventLoggerFactory;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class EventModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().
            implement(EventProducer.class, DefaultEventProducer.class).
            build(EventProducerFactory.class));

        bind(export(EventProducerFactory.class)).toProvider(service(EventProducerFactory.class).export());
        bind(EventLoggerFactory.class).toProvider(service(EventLoggerFactory.class).single());
    }

}

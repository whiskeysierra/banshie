package org.whiskeysierra.banshie.execution.event;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.whiskeysierra.banshie.common.BundleModule;
import org.whiskeysierra.banshie.common.InstallMode;
import org.whiskeysierra.banshie.execution.logging.EventLoggerFactory;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class EventModule extends BundleModule {

    public EventModule() {

    }

    public EventModule(InstallMode mode) {
        super(mode);
    }

    @Override
    protected void configureStandalone() {
        install(new FactoryModuleBuilder().
            implement(EventProducer.class, DefaultEventProducer.class).
            build(EventProducerFactory.class));
    }

    @Override
    protected void configureBundle() {
        bind(export(EventProducerFactory.class)).toProvider(service(EventProducerFactory.class).export());

        bind(EventLoggerFactory.class).toProvider(service(EventLoggerFactory.class).single());
    }

}

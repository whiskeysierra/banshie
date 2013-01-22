package org.whiskeysierra.banshie.execution.logging;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class LoggingModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().
            implement(EventLogger.class, DefaultEventLogger.class).
            build(EventLoggerFactory.class));

        bind(export(EventLoggerFactory.class)).toProvider(service(EventLoggerFactory.class).export());
    }

}

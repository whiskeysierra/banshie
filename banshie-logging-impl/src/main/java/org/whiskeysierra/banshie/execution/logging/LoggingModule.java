package org.whiskeysierra.banshie.execution.logging;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public final class LoggingModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().
            implement(EventLogger.class, DefaultEventLogger.class).
            build(EventLoggerFactory.class));
    }

}

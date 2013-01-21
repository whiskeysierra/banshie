package org.whiskeysierra.banshie.execution.logging;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public final class LoggingModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EventLogger.class).to(DefaultEventLogger.class).in(Singleton.class);
    }

}

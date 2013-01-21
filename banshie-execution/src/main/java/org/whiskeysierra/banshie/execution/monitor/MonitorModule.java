package org.whiskeysierra.banshie.execution.monitor;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public final class MonitorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ProcessMonitor.class).to(DefaultProcessMonitor.class).in(Scopes.NO_SCOPE);
    }

}

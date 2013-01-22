package org.whiskeysierra.banshie.execution.monitor;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public final class MonitorModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().
            implement(ProcessMonitor.class, DefaultProcessMonitor.class).
            build(ProcessMonitorFactory.class));
    }

}

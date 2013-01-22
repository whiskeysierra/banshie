package org.whiskeysierra.banshie.execution.monitor;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class MonitorModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().
            implement(ProcessMonitor.class, DefaultProcessMonitor.class).
            build(ProcessMonitorFactory.class));

        bind(export(ProcessMonitorFactory.class)).toProvider(service(ProcessMonitorFactory.class).export());
    }

}

package org.whiskeysierra.banshie.execution.monitor;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.whiskeysierra.banshie.common.BundleModule;
import org.whiskeysierra.banshie.common.InstallMode;
import org.whiskeysierra.banshie.execution.event.EventProducerFactory;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class MonitorModule extends BundleModule {

    public MonitorModule() {

    }

    public MonitorModule(InstallMode mode) {
        super(mode);
    }

    @Override
    protected void configureStandalone() {
        install(new FactoryModuleBuilder().
            implement(ProcessMonitor.class, DefaultProcessMonitor.class).
            build(ProcessMonitorFactory.class));
    }

    @Override
    protected void configureBundle() {
        bind(export(ProcessMonitorFactory.class)).toProvider(service(ProcessMonitorFactory.class).export());

        bind(EventProducerFactory.class).toProvider(service(EventProducerFactory.class).single());
    }

}

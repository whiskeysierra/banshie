package org.whiskeysierra.banshie.execution;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.whiskeysierra.banshie.execution.monitor.ProcessMonitorFactory;
import org.whiskeysierra.banshie.execution.process.ProcessService;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class ExecutionModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Engine.class).to(DefaultEngine.class).in(Singleton.class);
        bind(export(Engine.class)).toProvider(service(Engine.class).export());

        bind(ProcessService.class).toProvider(service(ProcessService.class).single());
        bind(ProcessMonitorFactory.class).toProvider(service(ProcessMonitorFactory.class).single());
    }

}

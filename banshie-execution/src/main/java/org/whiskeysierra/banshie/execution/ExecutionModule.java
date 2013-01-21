package org.whiskeysierra.banshie.execution;

import com.google.inject.AbstractModule;
import org.whiskeysierra.banshie.execution.logging.LoggingModule;
import org.whiskeysierra.banshie.execution.process.ProcessModule;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class ExecutionModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new LoggingModule());
        install(new ProcessModule());

        bind(export(Engine.class)).toProvider(service(DefaultEngine.class).export());
    }

}
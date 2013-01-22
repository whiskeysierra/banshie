package org.whiskeysierra.banshie.execution.process;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class ProcessModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ProcessService.class).to(DefaultProcessService.class).in(Singleton.class);
        bind(export(ProcessService.class)).toProvider(service(DefaultProcessService.class).export());
    }

}

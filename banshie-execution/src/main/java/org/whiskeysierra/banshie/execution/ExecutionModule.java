package org.whiskeysierra.banshie.execution;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class ExecutionModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Engine.class).to(DefaultEngine.class).in(Singleton.class);
        bind(export(Engine.class)).toProvider(service(DefaultEngine.class).export());
    }

}

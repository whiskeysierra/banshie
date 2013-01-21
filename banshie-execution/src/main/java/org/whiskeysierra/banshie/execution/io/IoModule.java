package org.whiskeysierra.banshie.execution.io;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public final class IoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EventWriter.class).to(DefaultEventWriter.class).in(Scopes.NO_SCOPE);
    }

}

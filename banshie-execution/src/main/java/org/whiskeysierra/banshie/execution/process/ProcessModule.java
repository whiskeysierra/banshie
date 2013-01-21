package org.whiskeysierra.banshie.execution.process;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public final class ProcessModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ProcessService.class).to(DefaultProcessService.class).in(Singleton.class);
    }

}

package org.whiskeysierra.banshie.evaluation.counter;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public final class CounterModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Counter.class).to(DefaultCounter.class).in(Singleton.class);
    }

}

package org.whiskeysierra.banshie.evaluation;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.MapBinder;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class PerformanceEvaluationModule extends AbstractModule {

    @Override
    protected void configure() {
        final MapBinder<Dimension, Processor> binder = MapBinder.newMapBinder(binder(),
            Dimension.class, Processor.class);

        binder.addBinding(Dimension.CPU_USAGE).to(CpuUsageProcessor.class);
        binder.addBinding(Dimension.MEMORY_USAGE).to(MemoryUsageProcessor.class);

        bind(PerformanceEvaluator.class).to(DefaultPerformanceEvaluator.class).in(Singleton.class);
        bind(export(PerformanceEvaluator.class)).toProvider(service(DefaultPerformanceEvaluator.class).export());
    }

}

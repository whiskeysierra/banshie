package org.whiskeysierra.banshie.evaluation;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.MapBinder;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class PerformanceEvaluationModule extends AbstractModule {

    @Override
    protected void configure() {
        final MapBinder<Dimension, Calculator> binder = MapBinder.newMapBinder(binder(),
            Dimension.class, Calculator.class);

        binder.addBinding(Dimension.CPU_TIME).to(CpuUsageCalculator.class);
        binder.addBinding(Dimension.MEMORY_CONSUMPTION).to(MemoryUsageCalculator.class);
        binder.addBinding(Dimension.TIME).to(TimeCalculator.class);

        bind(PerformanceEvaluator.class).to(DefaultPerformanceEvaluator.class).in(Singleton.class);
        bind(export(PerformanceEvaluator.class)).toProvider(service(PerformanceEvaluator.class).export());
    }

}

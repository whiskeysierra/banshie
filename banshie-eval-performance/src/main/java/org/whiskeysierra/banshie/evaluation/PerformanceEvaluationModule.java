package org.whiskeysierra.banshie.evaluation;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

public final class PerformanceEvaluationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PerformanceEvaluator.class).to(DefaultPerformanceEvaluator.class).in(Singleton.class);
        bind(export(PerformanceEvaluator.class)).toProvider(service(DefaultPerformanceEvaluator.class).export());
    }

}
